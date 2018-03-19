package org.ekstep.genie.ui.contentdetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.customview.EkStepGenericDialog;
import org.ekstep.genie.customview.HorizontalSpacingDecoration;
import org.ekstep.genie.customview.ViewMoreTextViewUtil;
import org.ekstep.genie.fragment.BaseFragment;
import org.ekstep.genie.util.GlideImageUtil;
import org.ekstep.genie.util.ShowProgressDialog;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.geniesdk.ContentUtil;
import org.ekstep.genieservices.commons.bean.ContentImportResponse;
import org.ekstep.genieservices.commons.bean.DownloadProgress;
import org.ekstep.genieservices.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;


/**
 * Created on 20/12/16.
 *
 * @author swayangjit
 */
public class ContentDetailFragment extends BaseFragment
        implements ContentDetailContract.View, View.OnClickListener {
    private static final String TAG = ContentDetailFragment.class.getSimpleName();
    private ContentDetailContract.Presenter mPresenter;

    private TextView mNameTv;
    private TextView mDescriptionTv;
    private TextView mContentTypeTv;
    private TextView mLanguageTv;
    private TextView mSizeTv;
    private TextView mPublisherTv;
    private TextView mOwnerTv;
    private ImageView mContentIv;
    private ImageView mUserIv;
    private TextView mDownloadProgressTv;

    private RatingBar mAvarageRatingBar;
    private RatingBar mUserRatingBar;

    private TextView mDownloadAndUpdateBtn;
    private TextView mUpdateBtn;
    private TextView mPlayBtn;
    private ImageView mDeleteBtn;

    private TextView mAvgRatingTv;
    private TextView mTotalRatingTv;

    private TextView mCopyRightTv;
    private TextView mLicenseTv;
    private LinearLayout mPublisherLayout;
    private LinearLayout mOwnerLayout;
    private LinearLayout mDownloadProgressLayout;
    private LinearLayout mLayout_FeedBack;
    private ProgressBar mDownloadProgressBar;
    private ImageView mItemShareImg;
    private RecyclerView mRecyclerViewScreenshots;
    private ScreenshotAdapter mScreenshotAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private ImageView mPreviewIv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = (ContentDetailPresenter) presenter;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDownloadProgress(DownloadProgress downloadProgress) throws InterruptedException {
        mPresenter.updateProgressBar(downloadProgress);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onContentImportResponse(ContentImportResponse contentImportResponse) throws InterruptedException {
        mPresenter.manageImportSuccess(contentImportResponse);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.registerSubscriber(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content_detail, container, false);
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
                return new ContentDetailPresenter();
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
        mRecyclerViewScreenshots = (RecyclerView) view.findViewById(R.id.rv_screenshots);
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewScreenshots.setLayoutManager(mLinearLayoutManager);
        mRecyclerViewScreenshots.addItemDecoration(new HorizontalSpacingDecoration((int) getResources().getDimension(R.dimen.dp_25)));

        mNameTv = (TextView) view.findViewById(R.id.txt_content_name);
        mDescriptionTv = (TextView) view.findViewById(R.id.txt_content_description);
        mContentTypeTv = (TextView) view.findViewById(R.id.txt_content_type);
        mLanguageTv = (TextView) view.findViewById(R.id.txt_langauge);
        mPublisherTv = (TextView) view.findViewById(R.id.txt_publisher);
        mPublisherLayout = (LinearLayout) view.findViewById(R.id.layout_publisher);
        mOwnerTv = (TextView) view.findViewById(R.id.txt_owner);
        mOwnerLayout = (LinearLayout) view.findViewById(R.id.layout_owner);
        mSizeTv = (TextView) view.findViewById(R.id.txt_content_size);
        mTotalRatingTv = (TextView) view.findViewById(R.id.txt_total_ratings);
        mAvgRatingTv = (TextView) view.findViewById(R.id.txt_avg_rating);
        mDownloadAndUpdateBtn = (TextView) view.findViewById(R.id.tv_download);
        mDownloadAndUpdateBtn.setOnClickListener(this);

        mUpdateBtn = (TextView) view.findViewById(R.id.txt_update);
        mUpdateBtn.setOnClickListener(this);

        mPlayBtn = (TextView) view.findViewById(R.id.txt_play);
        mPlayBtn.setOnClickListener(this);

        mDeleteBtn = (ImageView) view.findViewById(R.id.iv_delete);
        mDeleteBtn.setOnClickListener(this);

        mContentIv = (ImageView) view.findViewById(R.id.img_content);
        mUserIv = (ImageView) view.findViewById(R.id.img_user);

        view.findViewById(R.id.back_btn).setOnClickListener(this);

        mItemShareImg = (ImageView) view.findViewById(R.id.item_share);
        mItemShareImg.setOnClickListener(this);

        mLayout_FeedBack = (LinearLayout) view.findViewById(R.id.layout_content_feedback);
        mLayout_FeedBack.setOnClickListener(this);
        view.findViewById(R.id.layout_cancel_download).setOnClickListener(this);

        mAvarageRatingBar = (RatingBar) view.findViewById(R.id.rating_avg);
        mUserRatingBar = (RatingBar) view.findViewById(R.id.rating_user);

        mDownloadProgressLayout = (LinearLayout) view.findViewById(R.id.layout_downnload_progress);
        mDownloadProgressBar = (ProgressBar) view.findViewById(R.id.download_progress_bar);
        mDownloadProgressTv = (TextView) view.findViewById(R.id.txt_downloading_progress);

        mCopyRightTv = (TextView) view.findViewById(R.id.txt_copyright);
        mLicenseTv = (TextView) view.findViewById(R.id.txt_licence);
        mPreviewIv = (ImageView) view.findViewById(R.id.iv_preview);
        mPreviewIv.setOnClickListener(this);
    }

    @Override
    public void setProgressIndicator(boolean active) {
        if (active) {
            ShowProgressDialog.showProgressDialog(getActivity(), getResources().getString(R.string.msg_contentdetail_fetching));
        } else {
            ShowProgressDialog.dismissDialog();
        }
    }

    @Override
    public void showTitle(String title) {

    }

    @Override
    public void showName(String name) {
        mNameTv.setText(name);
    }

    @Override
    public void showIcon(String icon) {
        if (isActive()) {
            GlideImageUtil.loadImageUrl(mActivity, icon, mContentIv);
        }
    }

    @Override
    public void showLocalIcon(File appIcon) {
        if (isActive()) {
            GlideImageUtil.loadImageUrl(mActivity, appIcon, mContentIv);
        }

    }

    @Override
    public void showDefaultIcon(int appIcon) {
        if (isActive()) {
            GlideImageUtil.loadImageUrl(mActivity, mContentIv);
        }

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
    public void showType(String type) {
        mContentTypeTv.setText(type);
    }

    @Override
    public void hideType() {
        mContentTypeTv.setVisibility(View.GONE);
    }

    @Override
    public void showSize(String size) {
        mSizeTv.setVisibility(View.VISIBLE);
        mSizeTv.setText(size);
    }

    @Override
    public void hideSize() {
        mSizeTv.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showPublisher(String publisher) {
        mPublisherTv.setText(publisher);
    }

    @Override
    public void hidePublisher() {
        mPublisherLayout.setVisibility(View.GONE);
    }

    @Override
    public void showCreator(String creator) {
        mOwnerTv.setText(creator);
    }

    @Override
    public void hideCreator() {
        mOwnerLayout.setVisibility(View.GONE);
    }

    @Override
    public void showDescription(String description) {
        mDescriptionTv.setText(description);

        mDescriptionTv.post(new Runnable() {
            @Override
            public void run() {
                int lineCount = mDescriptionTv.getLineCount();
                mPresenter.showMoreDescription(lineCount);
            }
        });
    }

    @Override
    public void showMoreDescription() {
        ViewMoreTextViewUtil.makeTextViewResizable(getActivity(), mDescriptionTv, 3, getResources().getString(R.string.label_contentdetail_read_more), true, getResources().getColor(R.color.read_more));
    }

    @Override
    public void showLicense(String license) {
        if (getActivity() == null) {
            return;
        }
        mLicenseTv.setText(getString(R.string.label_contentdetail_license) + license);
    }

    @Override
    public void showCopyRight(String copyRight) {
        if (isActive()) {
            mCopyRightTv.setText(getString(R.string.label_contentdetail_copyright) + copyRight);
        }

    }

    @Override
    public void hideCopyRight() {
        mCopyRightTv.setVisibility(View.GONE);
    }

    @Override
    public void showUserIcon() {
        mUserIv.setVisibility(View.VISIBLE);
    }

    @Override
    public void showUserRating(String rating) {
        mTotalRatingTv.setVisibility(View.VISIBLE);
        mTotalRatingTv.setText("(" + rating + ")");
    }

    @Override
    public void showUserRatingBar(float rating) {
        mUserRatingBar.setRating(rating);
    }

    @Override
    public void showFeedBackLayout() {
        mLayout_FeedBack.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFeedBackLayout() {
        mLayout_FeedBack.setVisibility(View.GONE);
    }

    @Override
    public void showAverageRating(String rating) {
        mAvgRatingTv.setText(rating);
        mAvgRatingTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void showAverageRatingBar(float rating) {
        mAvarageRatingBar.setVisibility(View.VISIBLE);
        mAvarageRatingBar.setRating(rating);
    }

    @Override
    public void showDownload() {
        if (getActivity() == null) {
            return;
        }
        mDownloadAndUpdateBtn.setText(getResources().getString(R.string.label_contentdetail_download));
        mDownloadAndUpdateBtn.setEnabled(true);
        mDownloadAndUpdateBtn.setBackgroundResource(R.drawable.bg_download_content);
        mDownloadAndUpdateBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void showUpdate() {
        mUpdateBtn.setText(getResources().getString(R.string.label_contentdetails_update));
        mUpdateBtn.setEnabled(true);
        mUpdateBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideUpdate() {
        mUpdateBtn.setVisibility(View.GONE);
    }

    @Override
    public void showDownloadProgress() {
        mDownloadProgressLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDownloadProgress() {
        mDownloadProgressLayout.setVisibility(View.GONE);
    }

    @Override
    public void showDownloadProgressText() {
        mDownloadProgressTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void setDownloadProgressText(String text) {
        mDownloadProgressTv.setText(text);
    }

    @Override
    public void setDownloadProgress(int progress) {
        mDownloadProgressBar.setProgress(progress);
    }

    @Override
    public void hideDownloadProgressText() {
        mDownloadProgressTv.setVisibility(View.GONE);
    }

    @Override
    public void hideDownload() {
        mDownloadAndUpdateBtn.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showPlay() {
        mPlayBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePlay() {
        mPlayBtn.setVisibility(View.GONE);
    }

    @Override
    public void showDelete() {
        mDeleteBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDelete() {
        mDeleteBtn.setVisibility(View.GONE);
    }

    @Override
    public void showSuccessMessage(int message) {
        Util.showCustomToast(message);
    }

    @Override
    public void showFailureMessage(int message) {
        Util.showCustomToast(message);
    }

    @Override
    public void handleMissingContent() {
        mPresenter.handleBackButtonClick();
    }

    @Override
    public void showShareOption() {
        mItemShareImg.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideShareOption() {
        mItemShareImg.setVisibility(View.GONE);
    }

    @Override
    public void showPreview() {
        mPreviewIv.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePreview() {
        mPreviewIv.setVisibility(View.GONE);
    }

    @Override
    public void showOpen() {
        if (getActivity() == null) {
            return;
        }
        mPlayBtn.setText(getText(R.string.action_contentdetail_open));
        mPlayBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDeleteConfirmationDialog(int refCount, String identifier) {
        ContentUtil.showDeleteContentConfirmationDialog(mActivity, mPresenter, refCount, identifier);
    }

    @Override
    public void showFeedBackSuccessfulMessage() {
        Util.showCustomToast(R.string.msg_feedback_successfull);
    }

    @Override
    public void updateRating(RatingBar ratingBar, float rating) {
        ratingBar.setRating(rating);
    }

    @Override
    public void showContentScreenshots(List<String> screenshotUrlList) {
        if (mScreenshotAdapter != null) {
            mScreenshotAdapter = null;
        }

        mScreenshotAdapter = new ScreenshotAdapter(getActivity(), screenshotUrlList, mPresenter);
        mRecyclerViewScreenshots.setAdapter(mScreenshotAdapter);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.back_btn) {
            mPresenter.handleBackButtonClick();

        } else if (i == R.id.tv_download || i == R.id.txt_update) {
            mPresenter.downloadContent();

        } else if (i == R.id.layout_cancel_download) {
            mPresenter.cancelDownload();

        } else if (i == R.id.txt_play) {
            mPresenter.playContent();

        } else if (i == R.id.item_share) {
            mPresenter.shareContent();

        } else if (i == R.id.iv_delete) {
            mPresenter.handleDeleteClick();

        } else if (i == R.id.layout_content_feedback) {
            mPresenter.rateContent();

        } else if (i == R.id.iv_preview) {
            mPresenter.previewContent();

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.unregisterSubscriber(this);
    }

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
}
