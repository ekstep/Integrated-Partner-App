package org.ekstep.genie.ui.textbook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.ekstep.genie.R;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.customview.EkStepCustomTextView;
import org.ekstep.genie.customview.HorizontalSpacingDecoration;
import org.ekstep.genie.fragment.BaseFragment;
import org.ekstep.genie.model.TextbookSection;
import org.ekstep.genie.ui.collection.CollectionContentAdapter;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.DialogUtils;
import org.ekstep.genie.util.GlideImageUtil;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.geniesdk.ContentUtil;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.ContentImportResponse;
import org.ekstep.genieservices.commons.bean.HierarchyInfo;
import org.ekstep.genieservices.commons.bean.enums.ContentImportStatus;
import org.ekstep.genieservices.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

/**
 * Created by shriharsh on 27/2/17.
 */

public class DownloadedTextbooksFragment extends BaseFragment
        implements DownloadedTextbooksContract.View, View.OnClickListener {

    private static final String TAG = DownloadedTextbooksFragment.class.getSimpleName();
    private EkStepCustomTextView mTextViewTextbookName;
    private EkStepCustomTextView mTextViewTextbookDetails;
    private ImageView mImageViewTextbookPoster;
    private DownloadedTextbooksContract.Presenter mPresenter;
    private RecyclerView mRecyclerViewDownloadedTextbooks;
    private boolean mIsFromDownloadsScreen;
    private CollectionContentAdapter mDownloadedTextbooksAdapter;
    private List<TextbookSection> mTextbookSectionList;
    private EkStepCustomTextView mLanguageTv;
    private EkStepCustomTextView mGradeTv;
    private View mGradeDivider;
    private RelativeLayout mRelativeLayoutGetContentNow;
    private RelativeLayout mRelativeLayoutDownloadedLessons;
    private EkStepCustomTextView mTextViewGetContentNow;

    public static DownloadedTextbooksFragment newInstance(Content content, String path, boolean isFromDownload) {
        LogUtil.d(TAG, "Content " + content);
        DownloadedTextbooksFragment fragment = new DownloadedTextbooksFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constant.BundleKey.BUNDLE_KEY_CONTENT, content);
        args.putString(Constant.BundleKey.BUNDLE_KEY_LOCAL_PATH, path);
        args.putBoolean(Constant.BundleKey.BUNDLE_KEY_IS_FROM_DOWNLOADS, isFromDownload);
        fragment.setArguments(args);
        return fragment;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onContentImportResponse(ContentImportResponse importResponse) throws InterruptedException {
        if (importResponse.getStatus().getValue() == ContentImportStatus.IMPORT_COMPLETED.getValue()) {
            LogUtil.e(TAG + "@onContentImportResponse", "Id - " + importResponse.getIdentifier() + " Status - " + importResponse.getStatus());
            mPresenter.manageImportSuccess(mTextbookSectionList, importResponse);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_downloaded_textbook, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        //get all the textBookSectionList from activity
        mTextbookSectionList = ((TextbookActivity) getActivity()).getTextbookSectionList();

        mPresenter.getAllDownloadedContentsFromTextbookSectionList(mTextbookSectionList);
        mPresenter.fetchBundleExtras(getArguments());
        mPresenter.showAllDownloadedContents(true);
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new DownloadedTextbooksPresenter();
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = (DownloadedTextbooksContract.Presenter) presenter;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.registerSubscriber(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.unregisterSubscriber(this);
        mPresenter.removeContentToBeDeletedValue();
    }

    private void initViews(View view) {
        view.findViewById(R.id.back_btn).setOnClickListener(this);
        mTextViewTextbookDetails = (EkStepCustomTextView) view.findViewById(R.id.tv_textbook_details);
        mTextViewTextbookDetails.setOnClickListener(this);
        mTextViewTextbookName = (EkStepCustomTextView) view.findViewById(R.id.tv_textbook_name);
        mImageViewTextbookPoster = (ImageView) view.findViewById(R.id.iv_textbook_poster);
        mRecyclerViewDownloadedTextbooks = (RecyclerView) view.findViewById(R.id.rv_downloaded_textbooks);
        mRecyclerViewDownloadedTextbooks.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewDownloadedTextbooks.addItemDecoration(new HorizontalSpacingDecoration(10));
        mLanguageTv = (EkStepCustomTextView) view.findViewById(R.id.tv_textbook_language);
        mGradeTv = (EkStepCustomTextView) view.findViewById(R.id.tv_textbook_grade);
        mGradeDivider = (View) view.findViewById(R.id.textbook_grade_divider);
        mRelativeLayoutDownloadedLessons = (RelativeLayout) view.findViewById(R.id.rl_downloaded_lessons);
        mRelativeLayoutGetContentNow = (RelativeLayout) view.findViewById(R.id.rl_downloaded_get_new_content);
        mTextViewGetContentNow = (EkStepCustomTextView) view.findViewById(R.id.tv_downloaded_get_content_now);
        mTextViewGetContentNow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.back_btn) {
            getActivity().getSupportFragmentManager().popBackStack();

        } else if (i == R.id.tv_textbook_details) {
            mPresenter.showTextbookDetails();

        } else if (i == R.id.tv_downloaded_get_content_now) {
            getActivity().finish();

        }
    }

    @Override
    public void setIsFromDownload(boolean mIsFromDownloadsScreen) {
        this.mIsFromDownloadsScreen = mIsFromDownloadsScreen;
    }

    @Override
    public void setTextbookName(String name) {
        mTextViewTextbookName.setText(name);
    }

    @Override
    public void showAllDownloadedTextbookContents(List<Content> textbookList, boolean mIsFromDownloadsScreen) {
        mDownloadedTextbooksAdapter = new CollectionContentAdapter(getActivity(), textbookList, mIsFromDownloadsScreen, mPresenter);
        mRecyclerViewDownloadedTextbooks.setAdapter(mDownloadedTextbooksAdapter);
    }

    @Override
    public void showLanguage(String language) {
        mLanguageTv.setText(language);
    }

    @Override
    public void hideLanguage() {
        mLanguageTv.setVisibility(GONE);
    }

    @Override
    public void showGrade(String type) {
        mGradeTv.setText(type);
    }

    @Override
    public void hideGrade() {
        mGradeTv.setVisibility(GONE);
    }

    @Override
    public void hideGradeDivider() {
        mGradeDivider.setVisibility(GONE);
    }

    @Override
    public void showDetails(Content content, ArrayList<HierarchyInfo> contentInfoList, boolean isFromDownloads, boolean isSpineAvailable, boolean isFromTextbook, boolean isParentTextbook) {
        ContentUtil.navigateToContentDetails(getContext(), content, contentInfoList, isFromDownloads, isSpineAvailable, isFromTextbook, isParentTextbook);
    }

    @Override
    public void showCustomToast(String msg) {
        Util.showCustomToast(msg);
    }

    @Override
    public void refreshAdapter(List<Content> downloadedTextbookList) {
        mDownloadedTextbooksAdapter.refresh(downloadedTextbookList, mIsFromDownloadsScreen);
    }

    @Override
    public void showDownloadedLessonsView() {
        mRelativeLayoutDownloadedLessons.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideGetContentNowView() {
        mRelativeLayoutGetContentNow.setVisibility(View.GONE);
    }

    @Override
    public void showGetContentNowView() {
        mRelativeLayoutGetContentNow.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDownloadedLessonsView() {
        mRelativeLayoutDownloadedLessons.setVisibility(View.GONE);
    }

    @Override
    public void showMoreDialog(Content content) {
        DialogUtils.showMoreDialog(mActivity, content, mPresenter);
    }

    @Override
    public void showIcon(String appIcon) {
        GlideImageUtil.loadImageUrl(mActivity, appIcon, mImageViewTextbookPoster);
    }

    @Override
    public void showLocalIcon(File file) {
        GlideImageUtil.loadImageUrl(mActivity, file, mImageViewTextbookPoster);
    }

    @Override
    public void showDefaultIcon(int appIcon) {
        GlideImageUtil.loadImageUrl(mActivity, mImageViewTextbookPoster);
    }

}
