package org.ekstep.genie.ui.collection;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.makeramen.roundedimageview.RoundedImageView;

import org.ekstep.genie.R;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.customview.EkStepCustomTextView;
import org.ekstep.genie.customview.EkStepGenericDialog;
import org.ekstep.genie.customview.HorizontalSpacingDecoration;
import org.ekstep.genie.fragment.BaseFragment;
import org.ekstep.genie.ui.progressreports.ProgressReportsActivity;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.DialogUtils;
import org.ekstep.genie.util.GlideImageUtil;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.geniesdk.ContentUtil;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.ContentImportResponse;
import org.ekstep.genieservices.commons.bean.HierarchyInfo;
import org.ekstep.genieservices.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;


/**
 * Created on 8/2/17.
 *
 * @author shriharsh
 */
public class CollectionFragment extends BaseFragment
        implements CollectionContract.View, View.OnClickListener {

    private CollectionContract.Presenter mPresenter;
    private EkStepCustomTextView mTextViewCollectionName;
    private EkStepCustomTextView mTextViewCollectionSize;
    private RoundedImageView mImageViewCollectionPoster;
    private RecyclerView mRecyclerViewCollections;
    private View mRelativeLayoutDownload;
    private View mRelativeLayoutDownloadAll;
    private View mRelativeLayoutDownloading;
    private EkStepCustomTextView mLanguageTv;
    private EkStepCustomTextView mGradeTv;
    private View mGradeDivider;
    private CollectionContentAdapter mCollectionContentAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = (CollectionPresenter) presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_collections, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        mPresenter.fetchBundleExtras(getArguments());
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new CollectionPresenter();
            }
        };
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected BaseView getBaseView() {
        return this;
    }


    private void initViews(View view) {
        view.findViewById(R.id.back_btn).setOnClickListener(this);
        view.findViewById(R.id.tv_collection_details).setOnClickListener(this);

        mTextViewCollectionName = (EkStepCustomTextView) view.findViewById(R.id.tv_collection_name);
        mTextViewCollectionSize = (EkStepCustomTextView) view.findViewById(R.id.tv_collection_size);
        mImageViewCollectionPoster = (RoundedImageView) view.findViewById(R.id.iv_collection_poster);
        mRecyclerViewCollections = (RecyclerView) view.findViewById(R.id.rv_collections);
        mRecyclerViewCollections.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewCollections.addItemDecoration(new HorizontalSpacingDecoration((int) getResources().getDimension(R.dimen.section_divider)));
        mRelativeLayoutDownload = view.findViewById(R.id.rl_download);
        mRelativeLayoutDownloadAll = view.findViewById(R.id.layout_download_all);
        mRelativeLayoutDownloading = view.findViewById(R.id.layout_downloading);
        mLanguageTv = (EkStepCustomTextView) view.findViewById(R.id.tv_collection_language);
        mGradeTv = (EkStepCustomTextView) view.findViewById(R.id.tv_collection_grade);
        mGradeDivider = view.findViewById(R.id.collection_grade_divider);

        mRelativeLayoutDownloadAll.setOnClickListener(this);

        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.pb_download);
        mPresenter.colorProgressBar(progressBar);
    }

    public void handleTelemetryEndEvent() {
        mPresenter.sendTelemetryEndEvent();
    }

    @Override
    public void showCollectionShelf(List<Content> contentList, boolean isFromDownloadsScreen) {
        mCollectionContentAdapter = new CollectionContentAdapter(getActivity(), mPresenter.getModifiedContentList(contentList), isFromDownloadsScreen, mPresenter);
        mRecyclerViewCollections.setAdapter(mCollectionContentAdapter);
    }

    @Override
    public void refreshAdapter(List<Content> contentList, boolean isFromDownloadsScreen) {
        mCollectionContentAdapter.refresh(mPresenter.getModifiedContentList(contentList), isFromDownloadsScreen);
    }

    @Override
    public void showContentDetails(Content content, List<HierarchyInfo> contentInfoList, boolean isFromDownloads, boolean isSpineAvailable, boolean isParentCollection) {
        ContentUtil.navigateToContentDetails(getContext(), content, contentInfoList, isFromDownloads, isSpineAvailable, true, isParentCollection);
    }

    @Override
    public void showIcon(String icon) {
        GlideImageUtil.loadImageUrl(mActivity, icon, mImageViewCollectionPoster);
    }

    @Override
    public void showLocalIcon(File appIcon) {
        GlideImageUtil.loadImageUrl(mActivity, appIcon, mImageViewCollectionPoster);
    }

    @Override
    public void showDefaultIcon(int appIcon) {
        GlideImageUtil.loadImageUrl(mActivity, mImageViewCollectionPoster);
    }

    @Override
    public void setCollectionName(String name) {
        mTextViewCollectionName.setText(name);
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public void setCollectionSize(String size) {
        mTextViewCollectionSize.setText(size);
    }

    @Override
    public void notifyAdapterDataSetChanged() {
        mCollectionContentAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCustomToast(String msg) {
        Util.showCustomToast(msg);
    }

    @Override
    public void showDownloadLayout() {
        mRelativeLayoutDownload.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDownloadLayout() {
        mRelativeLayoutDownload.setVisibility(View.GONE);
    }

    @Override
    public void disableDownloadLayout() {
        mRelativeLayoutDownload.setEnabled(false);
    }

    @Override
    public void showDownloadAllLayout() {
        mRelativeLayoutDownloadAll.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDownloadAllLayout() {
        mRelativeLayoutDownloadAll.setVisibility(View.GONE);
    }

    @Override
    public void showDownloadingLayout() {
        mRelativeLayoutDownloading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDownloadingLayout() {
        mRelativeLayoutDownloading.setVisibility(View.GONE);
    }

    @Override
    public void showNoNetworkError() {
        Util.showCustomToast(R.string.error_network_1);
    }

    @Override
    public void showLanguage(String language) {
        mLanguageTv.setText(language);
    }

    @Override
    public void hideLanguage() {
        mLanguageTv.setVisibility(View.GONE);
    }

    @Override
    public void showGrade(String grade) {
        mGradeTv.setText(grade);
    }

    @Override
    public void hideGrade() {
        mGradeTv.setVisibility(View.GONE);
    }

    @Override
    public void hideGradeDivider() {
        mGradeDivider.setVisibility(View.GONE);
    }

    @Override
    public void showDeleteConfirmationLayout(String identifier, int refCount) {
        ContentUtil.showDeleteContentConfirmationDialog(mActivity, mPresenter, refCount, identifier);
    }

    @Override
    public void showFeedbackDialog(String identifier, float previuosRating) {
        DialogUtils.showFeedbackDialog(mActivity, mPresenter, identifier, previuosRating);
    }

    @Override
    public void showMoreDialog(Content content) {
        DialogUtils.showMoreDialog(mActivity, content, mPresenter);
    }

    @Override
    public void showProgressReportActivity(Content content) {
        Intent intent = new Intent(mActivity, ProgressReportsActivity.class);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_CONTENT, content);
        mActivity.startActivity(intent);
    }

    @Override
    public void showDeviceMemoryLowDialog() {
        final EkStepGenericDialog ekstepGenericDialog = new EkStepGenericDialog.Builder(mActivity).
                setTitle(R.string.title_low_space_device).
                setDescription(R.string.msg_low_space_device).
                setPositiveButtonText(R.string.label_dialog_ok).
                onPositiveClick(new EkStepGenericDialog.Callback() {
                    @Override
                    public void onClick(@NonNull EkStepGenericDialog dialog, @NonNull EkStepGenericDialog.Action action) {
                        dialog.dismiss();
                    }
                }).build();
        ekstepGenericDialog.show();
    }

    @Override
    public void showSdcardMemoryLowDialog() {
        final EkStepGenericDialog ekstepGenericDialog = new EkStepGenericDialog.Builder(mActivity).
                setTitle(R.string.title_low_space_sdcard).
                setDescription(R.string.msg_low_space_sdcard).
                setPositiveButtonText(R.string.label_dialog_ok).
                onPositiveClick(new EkStepGenericDialog.Callback() {
                    @Override
                    public void onClick(@NonNull EkStepGenericDialog dialog, @NonNull EkStepGenericDialog.Action action) {
                        dialog.dismiss();
                    }
                }).build();
        ekstepGenericDialog.show();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.back_btn) {
            mPresenter.handleBackButtonClick();

        } else if (i == R.id.layout_download_all) {
            mPresenter.handleDownloadAllClick();

        } else if (i == R.id.tv_collection_details) {
            mPresenter.showCollectionDetails();

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.registerSubscriber(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.showChildContents(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.unregisterSubscriber(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onContentImportResponse(ContentImportResponse importResponse) throws InterruptedException {
        LogUtil.d("CollectionFragment", "onContentImportResponse");
        mPresenter.manageImportSuccess(importResponse);
    }
}
