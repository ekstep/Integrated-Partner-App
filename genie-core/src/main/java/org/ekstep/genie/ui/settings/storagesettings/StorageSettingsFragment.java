package org.ekstep.genie.ui.settings.storagesettings;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import org.ekstep.genie.R;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.customview.EkStepCustomTextView;
import org.ekstep.genie.customview.EkStepGenericDialog;
import org.ekstep.genie.customview.EkStepRadioGroup;
import org.ekstep.genie.fragment.BaseFragment;
import org.ekstep.genie.ui.landing.LandingActivity;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.FileHandler;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.preference.PreferenceKey;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.commons.bean.MoveContentProgress;
import org.ekstep.genieservices.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

/**
 * Created by Sneha on 9/14/2017.
 */

public class StorageSettingsFragment extends BaseFragment implements StorageSettingsContract.View, View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private StorageSettingsPresenter mPresenter;

    private EkStepCustomTextView mTitleMobile;
    private EkStepCustomTextView mMobileAvailableSpace;
    private EkStepCustomTextView mMobileUsedSpace;
    private EkStepCustomTextView mMobileGenieSpace;

    private EkStepCustomTextView mTitleSdcard;
    private EkStepCustomTextView mSdcardAvailableSpace;
    private EkStepCustomTextView mSdcardUsedSpace;
    private EkStepCustomTextView mSdcardGenieSpace;
    private EkStepCustomTextView mLabelSdcardAvailableSpace;
    private EkStepCustomTextView mLabelSdcardUsedSpace;
    private EkStepCustomTextView mLabelSdcardGenieSpace;

    private EkStepCustomTextView mManageOthers;
    private EkStepCustomTextView mManageGenie;

    private EkStepRadioGroup mRbMobile;
    private EkStepRadioGroup mRbExternal;
    private int mSelectedRadioButtonId;

    private EkStepGenericDialog moveContentDialog = null;

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.isSubscriberRegistered(this)) {
            EventBus.registerSubscriber(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.unregisterSubscriber(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMoveContentProgress(MoveContentProgress moveContentProgress) throws InterruptedException {
        mPresenter.handleOnMoveContentProgress(moveContentProgress);
        if (moveContentDialog != null) {
            moveContentDialog.makeProgress(moveContentProgress);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = (StorageSettingsPresenter) presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_storage_setting, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
    }

    private void initViews(View view) {
        mTitleMobile = (EkStepCustomTextView) view.findViewById(R.id.title_mobile);
        mMobileAvailableSpace = (EkStepCustomTextView) view.findViewById(R.id.mobile_available_space);
        mMobileUsedSpace = (EkStepCustomTextView) view.findViewById(R.id.mobile_used_space);
        mMobileGenieSpace = (EkStepCustomTextView) view.findViewById(R.id.mobile_genie_space);

        mTitleSdcard = (EkStepCustomTextView) view.findViewById(R.id.title_SD_card);
        mSdcardAvailableSpace = (EkStepCustomTextView) view.findViewById(R.id.available_sdcard_space);
        mSdcardUsedSpace = (EkStepCustomTextView) view.findViewById(R.id.others_sdcard_space);
        mSdcardGenieSpace = (EkStepCustomTextView) view.findViewById(R.id.genie_sdcard_space);
        mLabelSdcardAvailableSpace = (EkStepCustomTextView) view.findViewById(R.id.label_sdcard_available_space);
        mLabelSdcardUsedSpace = (EkStepCustomTextView) view.findViewById(R.id.label_sdcard_genie);
        mLabelSdcardGenieSpace = (EkStepCustomTextView) view.findViewById(R.id.label_sdcard_others);

        mRbMobile = (EkStepRadioGroup) view.findViewById(R.id.mobile_btn);
        mRbMobile.setOnCheckedChangeListener(this);
        mRbExternal = (EkStepRadioGroup) view.findViewById(R.id.sdcard_btn);
        mRbExternal.setOnCheckedChangeListener(this);

        mManageOthers = (EkStepCustomTextView) view.findViewById(R.id.manage_others);
        mManageOthers.setOnClickListener(this);

        mManageGenie = (EkStepCustomTextView) view.findViewById(R.id.manage_genie);
        mManageGenie.setOnClickListener(this);

        mPresenter.calculateSdcardStorageValue(getActivity());
        mPresenter.calculateMobileStorageValue(getActivity());

        mSelectedRadioButtonId = mPresenter.selectAptDefaultStorageOption();
        mPresenter.checkExternalStorageAvaibility();

    }


    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new StorageSettingsPresenter();
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
        if (i == R.id.manage_others) {
            Intent intent = new Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS);
            startActivity(intent);

        } else if (i == R.id.manage_genie) {
            Intent myContentIntent = new Intent(mActivity, LandingActivity.class);
            myContentIntent.putExtra(Constant.EXTRA_DEEP_LINK_MY_CONTENT, true);
            startActivity(myContentIntent);
            mActivity.finish();

        } else {
        }
    }

    @Override
    public void showDialogtoSetExternalDeviceAsDefault() {

        File file = new File(String.valueOf(getActivity().getExternalFilesDir(null)) + File.separator + "content");
        String internalGenieSpace = Util.humanReadableByteCount(FileHandler.folderSize(file), true);

        final EkStepGenericDialog ekStepGenericDialog = new EkStepGenericDialog.Builder(mActivity).
                setTitle(R.string.title_sdcard_default_storage).
                setDescription(getString(R.string.msg_sdcard_default_storage, internalGenieSpace)).
                setPositiveButtonText(R.string.title_content_player_continue).
                setNegativeButtonText(R.string.label_dialog_cancel).onPositiveClick(new EkStepGenericDialog.Callback() {
            @Override
            public void onClick(@NonNull final EkStepGenericDialog dialog, @NonNull EkStepGenericDialog.Action action) {
                dialog.dismiss();
                mPresenter.setExternalDeviceAsDefault();
            }
        }).onNegativeClick(new EkStepGenericDialog.Callback() {
            @Override
            public void onClick(@NonNull EkStepGenericDialog dialog, @NonNull EkStepGenericDialog.Action action) {
                setPreviouslySelectedRadioButton();
                dialog.cancel();
            }
        }).onDismiss(new EkStepGenericDialog.DismissCallback() {
            @Override
            public void onDismiss() {
                setPreviouslySelectedRadioButton();
            }
        }).build();
        ekStepGenericDialog.show();
    }

    @Override
    public void showExternalDeviceAsSelected() {
        mSelectedRadioButtonId = R.id.sdcard_btn;
        mRbExternal.setChecked(true, false);
        mTitleSdcard.setTextColor(ContextCompat.getColor(mActivity, R.color.app_blue_theme_color));
        mTitleSdcard.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_sdcard_active, 0, 0, 0);
        mRbExternal.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_sdcard_active, 0, 0, 0);
        mTitleMobile.setTextColor(ContextCompat.getColor(mActivity, R.color.header_text_color));
        mRbMobile.setTextColor(ContextCompat.getColor(mActivity, R.color.header_text_color));
        mTitleMobile.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_mobile_inactive, 0, 0, 0);
        mRbMobile.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_mobile_inactive, 0, 0, 0);
    }

    @Override
    public void showMobileDeviceAsSelected() {
        mRbMobile.setChecked(true, false);
        mSelectedRadioButtonId = R.id.mobile_btn;
        mTitleMobile.setTextColor(ContextCompat.getColor(mActivity, R.color.app_blue_theme_color));
        mTitleSdcard.setTextColor(ContextCompat.getColor(mActivity, R.color.header_text_color));
        mRbExternal.setTextColor(ContextCompat.getColor(mActivity, R.color.header_text_color));
        mTitleMobile.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_mobile_active, 0, 0, 0);
        mRbMobile.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_mobile_active, 0, 0, 0);
        mTitleSdcard.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_sdcard_inactive, 0, 0, 0);
        mRbExternal.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_sdcard_inactive, 0, 0, 0);
    }

    @Override
    public void showDialogToSetMobileDeviceAsDefault() {
        final EkStepGenericDialog ekStepGenericDialog = new EkStepGenericDialog.Builder(mActivity).
                setTitle(R.string.title_device_default_storage).
                setDescription(getString(R.string.msg_device_default_storage, mPresenter.getExternalGenieUsedSpace())).
                setPositiveButtonText(R.string.title_content_player_continue).
                setNegativeButtonText(R.string.label_dialog_cancel).onPositiveClick(new EkStepGenericDialog.Callback() {
            @Override
            public void onClick(@NonNull EkStepGenericDialog dialog, @NonNull EkStepGenericDialog.Action action) {
                dialog.dismiss();
                mPresenter.setMobileDeviceAsDefault();
            }
        }).onNegativeClick(new EkStepGenericDialog.Callback() {
            @Override
            public void onClick(@NonNull EkStepGenericDialog dialog, @NonNull EkStepGenericDialog.Action action) {
                setPreviouslySelectedRadioButton();
                dialog.dismiss();
            }
        }).
                onDismiss(new EkStepGenericDialog.DismissCallback() {
                    @Override
                    public void onDismiss() {
                        setPreviouslySelectedRadioButton();
                    }
                }).build();
        ekStepGenericDialog.show();
    }

    private void setPreviouslySelectedRadioButton() {
        ((EkStepRadioGroup) getView().findViewById(mSelectedRadioButtonId)).setChecked(true, false);
    }

    @Override
    public void displaySdcardAvailableSpace(String sdcardSize) {
        mSdcardAvailableSpace.setText(sdcardSize);
    }

    @Override
    public void displaySdcardUsedSpace(String usedSize) {
        mSdcardUsedSpace.setText(usedSize);
    }

    @Override
    public void displaySdcardGenieSpace(String genieSize) {
        mSdcardGenieSpace.setText(genieSize);
    }

    @Override
    public void displaySdcardNotAvailable() {

    }

    @Override
    public void displayMobileAvailableSpace(String availableSize) {
        mMobileAvailableSpace.setText(availableSize);
    }

    @Override
    public void displayMobileUsedSpace(String usedSize) {
        mMobileUsedSpace.setText(usedSize);
    }

    @Override
    public void displayMobileGenieSpace(String genieSize) {
        mMobileGenieSpace.setText(genieSize);
    }

    @Override
    public void showLowSpaceInDeviceDialog() {
        final EkStepGenericDialog ekStepGenericDialog = new EkStepGenericDialog.Builder(mActivity).
                setTitle(R.string.title_low_space_device).
                setDescription(R.string.msg_low_space_device).
                setPositiveButtonText(R.string.label_dialog_ok).
                onPositiveClick(new EkStepGenericDialog.Callback() {
                    @Override
                    public void onClick(@NonNull EkStepGenericDialog dialog, @NonNull EkStepGenericDialog.Action action) {
                        dialog.dismiss();
                    }
                }).build();
        ekStepGenericDialog.show();
    }

    @Override
    public void showLowSpaceInSdcardDialog() {
        final EkStepGenericDialog ekStepGenericDialog = new EkStepGenericDialog.Builder(mActivity).
                setTitle(R.string.title_low_space_sdcard).
                setDescription(R.string.msg_low_space_sdcard).
                setPositiveButtonText(R.string.label_dialog_ok).
                onPositiveClick(new EkStepGenericDialog.Callback() {
                    @Override
                    public void onClick(@NonNull EkStepGenericDialog dialog, @NonNull EkStepGenericDialog.Action action) {
                        dialog.dismiss();
                    }
                }).build();
        ekStepGenericDialog.show();
    }

    @Override
    public void disableExternalDevice() {
        mRbExternal.setEnabled(false);
        int disabledColor = ContextCompat.getColor(mActivity, R.color.disable_grey_color);
        mTitleSdcard.setTextColor(disabledColor);
        mRbExternal.setTextColor(disabledColor);
        mSdcardAvailableSpace.setTextColor(disabledColor);
        mSdcardUsedSpace.setTextColor(disabledColor);
        mSdcardGenieSpace.setTextColor(disabledColor);
        mLabelSdcardAvailableSpace.setTextColor(disabledColor);
        mLabelSdcardUsedSpace.setTextColor(disabledColor);
        mLabelSdcardGenieSpace.setTextColor(disabledColor);
    }

    @Override
    public void showMoveContentProgressDialog(MoveContentProgress moveContentProgress) {
        moveContentDialog = new EkStepGenericDialog.Builder(mActivity).
                setTitle(R.string.title_move_content).
                setDescription(getMoveContentDialogDesc()).
                setPositiveButtonText(R.string.label_dialog_cancel).
                setProgressBar(true).
                setProgressLimit(moveContentProgress.getTotalCount()).
                onPositiveClick(new EkStepGenericDialog.Callback() {
                    @Override
                    public void onClick(@NonNull EkStepGenericDialog dialog, @NonNull EkStepGenericDialog.Action action) {
                        dialog.dismiss();
                    }
                }).build();
        moveContentDialog.show();
    }

    @Override
    public void dismissMoveContentDialog() {
        moveContentDialog.dismiss();
    }

    private int getMoveContentDialogDesc() {
        boolean isExternalDefault = PreferenceUtil.getPreferenceWrapper().
                getBoolean(PreferenceKey.KEY_SET_EXTERNAL_STORAGE_DEFAULT, false);
        if (isExternalDefault) {
            return R.string.msg_move_content_internal_storage;
        } else {
            return R.string.msg_move_content_sdcard;
        }
    }

    @Override
    public void showMergeContentDialog() {
        final EkStepGenericDialog ekStepGenericDialog = new EkStepGenericDialog.Builder(mActivity).
                setTitle(R.string.title_merge_content).
                setDescription(R.string.msg_merge_content).
                setPositiveButtonText(R.string.label_dialog_yes).
                setNegativeButtonText(R.string.label_dialog_cancel).onPositiveClick(new EkStepGenericDialog.Callback() {
            @Override
            public void onClick(@NonNull EkStepGenericDialog dialog, @NonNull EkStepGenericDialog.Action action) {
                dialog.dismiss();
            }
        }).onNegativeClick(new EkStepGenericDialog.Callback() {
            @Override
            public void onClick(@NonNull EkStepGenericDialog dialog, @NonNull EkStepGenericDialog.Action action) {
                dialog.dismiss();
            }
        }).build();
        ekStepGenericDialog.show();
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            int i = buttonView.getId();
            if (i == R.id.mobile_btn) {
                showDialogToSetMobileDeviceAsDefault();

            } else if (i == R.id.sdcard_btn) {
                showDialogtoSetExternalDeviceAsDefault();

            }
        }

    }
}
