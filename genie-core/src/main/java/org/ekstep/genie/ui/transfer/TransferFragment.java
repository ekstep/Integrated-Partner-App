package org.ekstep.genie.ui.transfer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import org.ekstep.genie.R;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.customview.EkStepBalooCheckedTextView;
import org.ekstep.genie.fragment.BaseFragment;
import org.ekstep.genie.ui.landing.LandingActivity;
import org.ekstep.genie.ui.share.ShareActivity;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.Profile;
import org.ekstep.genieservices.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Map;

public class TransferFragment extends BaseFragment
        implements TransferContract.View, OnClickListener {

    private EkStepBalooCheckedTextView mChked_Txt_Telemetry = null;
    private EkStepBalooCheckedTextView mChked_Txt_Content = null;
    private EkStepBalooCheckedTextView mChked_Txt_Profile = null;

    private RecyclerView mRecyclerView_Content = null;
    private RecyclerView mRecyclerView_Profile = null;

    private ExportableContentGridAdapter mExportableContentAdapter = null;
    private ExportableProfileAdapter mExportableProfileAdapter;

    private TransferContract.Presenter mPresenter;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mPresenter.onReceiveBroadCast(context, intent);
        }
    };

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onContentExportSuccess(String exportSuccess) throws InterruptedException {
        mPresenter.manageContentExportSuccess(exportSuccess);
    }

    @Override
    public void onStart() {
        super.onStart();

        EventBus.registerSubscriber(this);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.INTENT_ACTION_REFRESH_PROFILE_ADAPTER);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mBroadcastReceiver, intentFilter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = (TransferPresenter)presenter;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transfer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new TransferPresenter();
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

    private void initViews(View view) {
        view.findViewById(R.id.back_btn).setOnClickListener(this);

        mChked_Txt_Telemetry = (EkStepBalooCheckedTextView) view.findViewById(R.id.chk_txt_telemetry);
        mChked_Txt_Telemetry.setOnClickListener(this);

        mChked_Txt_Profile = (EkStepBalooCheckedTextView) view.findViewById(R.id.chk_txt_profile);
        mChked_Txt_Profile.setOnClickListener(this);

        mChked_Txt_Content = (EkStepBalooCheckedTextView) view.findViewById(R.id.chk_txt_content);
        mChked_Txt_Content.setOnClickListener(this);

        mRecyclerView_Content = (RecyclerView) view.findViewById(R.id.recyclerview_content);
        mRecyclerView_Content.setHasFixedSize(true);
        mRecyclerView_Content.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        mRecyclerView_Profile = (RecyclerView) view.findViewById(R.id.recyclerview_profile);
        mRecyclerView_Profile.setHasFixedSize(true);
        mRecyclerView_Profile.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        view.findViewById(R.id.layout_export).setOnClickListener(this);

        PreferenceUtil.setCoRelationIdContext(null);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.layout_export) {
            mPresenter.export();

        } else if (i == R.id.back_btn) {
            ((LandingActivity) mActivity).showHome(false);

        } else if (i == R.id.chk_txt_telemetry) {
            mPresenter.selectTelemetry();

        } else if (i == R.id.chk_txt_content) {
            mPresenter.selectContent();

        } else if (i == R.id.chk_txt_profile) {
            mPresenter.selectProfile();

        } else {
        }
    }

    @Override
    public void hideContentRecyclerView() {
        mRecyclerView_Content.setVisibility(View.GONE);
    }

    @Override
    public void checkContentText(boolean isChecked) {
        mChked_Txt_Content.setChecked(isChecked);
    }

    @Override
    public void checkTelemetryText(boolean isChecked) {
        mChked_Txt_Telemetry.setChecked(isChecked);
    }

    @Override
    public void checkProfileText(boolean isChecked) {
        mChked_Txt_Profile.setChecked(isChecked);
    }

    @Override
    public void hideProfileRecyclerView() {
        mRecyclerView_Profile.setVisibility(View.GONE);
    }


    @Override
    public boolean isTelemetryTextChecked() {
        return mChked_Txt_Telemetry.isChecked();
    }

    @Override
    public boolean isContentChecked() {
        return mChked_Txt_Content.isChecked();
    }

    @Override
    public boolean isProfileChecked() {
        return mChked_Txt_Profile.isChecked();
    }

    @Override
    public void showContentRecyclerView() {
        mRecyclerView_Content.setVisibility(View.VISIBLE);
    }

    @Override
    public void showContentList(List<Content> contentList) {
        mExportableContentAdapter = new ExportableContentGridAdapter(mActivity, mPresenter);
        mExportableContentAdapter.setData(contentList);
        mRecyclerView_Content.setAdapter(mExportableContentAdapter);
    }

    @Override
    public void profileToggleSelected(String uid) {
        mExportableProfileAdapter.toggleSelected(uid);
    }

    @Override
    public void contentToggleSelected(String identifier) {
        mExportableContentAdapter.toggleSelected(identifier);
    }

    @Override
    public boolean isProfileItemsSelected() {
        return mExportableProfileAdapter != null && (mExportableProfileAdapter.getSelectedCount() > 0);
    }

    @Override
    public void startProfileExport(Map<String, String> values) {
        ShareActivity.startProfileExportIntent(getActivity(), mExportableProfileAdapter.getSelectedItems(), values);
    }

    @Override
    public boolean isContentItemsSelected() {
        return mExportableContentAdapter != null && mExportableContentAdapter.getSelectedCount() > 0;
    }

    @Override
    public List<String> getContentSelectedItems() {
        return mExportableContentAdapter.getSelectedItems();
    }

    @Override
    public void showProfileRecyclerView() {
        mRecyclerView_Profile.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateProfileAdapter(List<Profile> profileList) {
        mExportableProfileAdapter = new ExportableProfileAdapter(mActivity, mPresenter);
        mExportableProfileAdapter.setData(profileList);
        mRecyclerView_Profile.setAdapter(mExportableProfileAdapter);
    }

    @Override
    public void clearContentAdapter() {
        mExportableContentAdapter.clearSelected();
    }

    @Override
    public void clearProfileAdapter() {
        mExportableProfileAdapter.clearSelected();
    }

    @Override
    public void showCustomToast(String message) {
        Util.showCustomToast(message);
    }

    @Override
    public void startContentExportIntent(Map<String, String> values, List<String> identifierList) {
        ShareActivity.startContetExportIntent(getActivity(), false, values, identifierList);
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mBroadcastReceiver);
        EventBus.unregisterSubscriber(this);
    }

}
