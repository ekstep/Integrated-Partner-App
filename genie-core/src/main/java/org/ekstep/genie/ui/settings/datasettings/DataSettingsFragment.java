package org.ekstep.genie.ui.settings.datasettings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.ekstep.genie.R;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.customview.EkStepRadioGroup;
import org.ekstep.genie.fragment.BaseFragment;
import org.ekstep.genie.model.enums.StageCode;
import org.ekstep.genie.model.enums.SyncConfiguration;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.ShowProgressDialog;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.geniesdk.SyncServiceUtil;

/**
 * Created by Sneha on 9/14/2017.
 */

public class DataSettingsFragment extends BaseFragment implements DataSettingsContract.View, View.OnClickListener {
    private TextView mLastSyncDateTimeTv;
    private TextView mLastSyncDateTimeTv2;
    private EkStepRadioGroup mManualSync;
    private EkStepRadioGroup mWifiSync;
    private EkStepRadioGroup mWifiAndCellularSync;

    private DataSettingsPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = (DataSettingsPresenter) presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_data_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        mPresenter.getLastSyncTime();

        displayCurrentSyncConfiguration();

        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(TelemetryStageId.SETTINGS_DATA_USAGE));
    }

    private void initViews(View view) {
        mManualSync = (EkStepRadioGroup) view.findViewById(R.id.radio_sync_manual);
        setSpannableText(mManualSync, getString(R.string.label_sync_manual), getString(R.string.label_sync_manual_desc));
        mWifiSync = (EkStepRadioGroup) view.findViewById(R.id.radio_sync_using_wifi);
        setSpannableText(mWifiSync, getString(R.string.label_auto_sync_using_wifi), getString(R.string.label_auto_sync_using_wifi_desc));
        mWifiAndCellularSync = (EkStepRadioGroup) view.findViewById(R.id.radio_sync_using_wifi_and_cellular);
        setSpannableText(mWifiAndCellularSync, getString(R.string.label_auto_sync_using_wifi_and_cellular), getString(R.string.label_auto_sync_using_wifi_and_cellular_desc));

        mManualSync.setOnClickListener(this);
        mWifiSync.setOnClickListener(this);
        mWifiAndCellularSync.setOnClickListener(this);

        view.findViewById(R.id.btn_sync_now).setOnClickListener(this);
        mLastSyncDateTimeTv = (TextView) view.findViewById(R.id.txt_last_sync_time);
        mLastSyncDateTimeTv2 = (TextView) view.findViewById(R.id.txt_last_sync_time2);
    }

    private void setSpannableText(EkStepRadioGroup radioGroup, String title, String subTitle) {
        Spannable titleSpan = new SpannableString(title);
        titleSpan.setSpan(new TextAppearanceSpan(mActivity, R.style.RadioGroupTitle), 0, titleSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        radioGroup.setText(titleSpan);
        Spannable subtitleSpan = new SpannableString("\n" + subTitle);
        subtitleSpan.setSpan(new TextAppearanceSpan(mActivity, R.style.RadioGroupSubTitle), 0, subtitleSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        radioGroup.append(subtitleSpan);
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new DataSettingsPresenter();
            }
        };
    }

    @Override
    protected String getTAG() {
        return getClass().getName();
    }

    @Override
    protected BaseView getBaseView() {
        return this;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_sync_now) {//                doSyncNow();
            mPresenter.handleSyncNow();

        } else if (i == R.id.radio_sync_manual) {
            SyncServiceUtil.setConfiguration(SyncConfiguration.MANUAL);

        } else if (i == R.id.radio_sync_using_wifi) {
            SyncServiceUtil.setConfiguration(SyncConfiguration.OVER_WIFI_ONLY);

        } else if (i == R.id.radio_sync_using_wifi_and_cellular) {
            SyncServiceUtil.setConfiguration(SyncConfiguration.OVER_ANY_MODE);

        } else {
        }
    }

    @Override
    public void displayCurrentSyncConfiguration() {
        switch (SyncServiceUtil.getConfiguration()) {
            case MANUAL:
                mManualSync.setChecked(true);
                break;

            case OVER_WIFI_ONLY:
                mWifiSync.setChecked(true);
                break;

            case OVER_ANY_MODE:
                mWifiAndCellularSync.setChecked(true);
                break;

            default:
                mWifiSync.setChecked(true);
                break;
        }
    }

    @Override
    public void displayLastSyncTime(String lastSyncTime) {
        mLastSyncDateTimeTv.setText(getString(R.string.label_sync_last_synced_time) + " " + lastSyncTime);
        mLastSyncDateTimeTv2.setText(getString(R.string.label_sync_last_synced_time) + " " + lastSyncTime);
    }

    @Override
    public void showSyncDialog() {
        showSyncDialog(getResources().getString(R.string.msg_sync_successfull));
    }

    @Override
    public void showSyncFailedMsg(String errorMsg) {
        LogUtil.i(getTAG(), "manualSync onFailure.");
        ShowProgressDialog.dismissDialog();
        Util.showCustomToast(Util.getGenieSpecificMessage(errorMsg, StageCode.SYNC));
    }

    @Override
    public void showInternetConnectivityError() {
        Toast.makeText(getActivity(), getResources().getString(R.string.error_network), Toast.LENGTH_LONG).show();
    }
}
