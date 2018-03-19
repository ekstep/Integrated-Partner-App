package org.ekstep.genie.ui.textbook;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.customview.EkStepCustomTextView;
import org.ekstep.genie.customview.EkStepGenericDialog;
import org.ekstep.genie.customview.HorizontalSpacingDecoration;
import org.ekstep.genie.customview.treeview.TreeItemHolder;
import org.ekstep.genie.customview.treeview.model.TreeNode;
import org.ekstep.genie.customview.treeview.view.AndroidTreeView;
import org.ekstep.genie.fragment.BaseFragment;
import org.ekstep.genie.model.ContentDeleteResponse;
import org.ekstep.genie.model.TextbookSection;
import org.ekstep.genie.telemetry.EnvironmentId;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryConstant;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.telemetry.enums.EntityType;
import org.ekstep.genie.telemetry.enums.ImpressionType;
import org.ekstep.genie.telemetry.enums.ObjectType;
import org.ekstep.genie.ui.progressreports.ProgressReportsActivity;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.DeviceUtility;
import org.ekstep.genie.util.DialogUtils;
import org.ekstep.genie.util.GlideImageUtil;
import org.ekstep.genie.util.NetworkUtil;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.geniesdk.ContentUtil;
import org.ekstep.genie.util.preference.PreferenceKey;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.ContentImportResponse;
import org.ekstep.genieservices.commons.bean.HierarchyInfo;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.ekstep.genieservices.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;

/**
 * Created by shriharsh on 8/2/17.
 */

public class TextbookFragment extends BaseFragment
        implements TextbookContract.View, View.OnClickListener {

    private TextbookContract.Presenter mPresenter;
    private EkStepCustomTextView mTextViewTextbookName;
    private EkStepCustomTextView mTextViewTextbookDetails;
    private ImageView mImageViewTextbookPoster;
    private ViewGroup mTreeViewContainer;
    private AndroidTreeView mAndroidTreeView;
    private RecyclerView mRecyclerViewTextbooks;
    private boolean mIsFromDownloadsScreen;
    private EkStepCustomTextView mLanguageTv;
    private EkStepCustomTextView mGradeTv;
    private View mGradeDivider;
    private TextbookAdapter mTextbookAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private RelativeLayout mRelativeLayoutNoTextbooks;
    private boolean isRequiredToCallApi;
    private RadioButton mRbViewAllTextbook;
    private RadioButton mRbDownloadedTextbook;
    private EkStepCustomTextView mTextViewTextbookSize;
    private RelativeLayout mRelativeLayoutDownloadAll;
    private RelativeLayout mRelativeLayoutDownloading;

    private TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener() {
        @Override
        public void onClick(TreeNode node, Object value) {
            mPresenter.handleTreeNodeClick(node, value);
        }
    };

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onContentImport(ContentImportResponse importResponse) throws InterruptedException {
        Log.e("TextbookFragment", "onContentImport: ");
        mPresenter.manageImportAndDeleteSuccess(importResponse);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onContentDelete(ContentDeleteResponse deleteResponse) throws InterruptedException {
        mPresenter.manageImportAndDeleteSuccess(deleteResponse);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = (TextbookContract.Presenter) presenter;
        isRequiredToCallApi = true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_textbook, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        mPresenter.fetchTextbooks(getArguments());

        mPresenter.checkAndGetTableOfContentsAndLessonsFromApi(isRequiredToCallApi);

    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new TextbookPresenter();
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
    public void onStart() {
        super.onStart();
        EventBus.registerSubscriber(this);
    }

    @Override
    public void showTableOfContents(TreeNode root) {
        mAndroidTreeView = new AndroidTreeView(getActivity(), root);
        mAndroidTreeView.setDefaultAnimation(true);
        mAndroidTreeView.setAnimationOffWhenCollapseAll(true);
        mAndroidTreeView.setDefaultContainerStyle(R.style.TreeNodeStyleDivided);
        mAndroidTreeView.setDefaultViewHolder(TreeItemHolder.class);
        mAndroidTreeView.setDefaultNodeClickListener(nodeClickListener);
        mTreeViewContainer.addView(mAndroidTreeView.getView());
    }

    private void initViews(View view) {
        view.findViewById(R.id.back_btn).setOnClickListener(this);
        mTreeViewContainer = (ViewGroup) view.findViewById(R.id.tree_view_container);
        mTextViewTextbookDetails = (EkStepCustomTextView) view.findViewById(R.id.tv_textbook_details);
        mTextViewTextbookDetails.setOnClickListener(this);
        mTextViewTextbookName = (EkStepCustomTextView) view.findViewById(R.id.tv_textbook_name);
        mImageViewTextbookPoster = (ImageView) view.findViewById(R.id.iv_textbook_poster);
        mRecyclerViewTextbooks = (RecyclerView) view.findViewById(R.id.rv_textbooks);
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewTextbooks.setLayoutManager(mLinearLayoutManager);
        mRecyclerViewTextbooks.addItemDecoration(new HorizontalSpacingDecoration(40));
        mGradeTv = (EkStepCustomTextView) view.findViewById(R.id.tv_textbook_grade);
        mGradeDivider = (View) view.findViewById(R.id.textbook_grade_divider);
        mLanguageTv = (EkStepCustomTextView) view.findViewById(R.id.tv_textbook_language);
        mRelativeLayoutNoTextbooks = (RelativeLayout) view.findViewById(R.id.rl_no_textbook);
        mRelativeLayoutDownloadAll = (RelativeLayout) view.findViewById(R.id.layout_download_all);
        mRelativeLayoutDownloadAll.setOnClickListener(this);
        mRelativeLayoutDownloading = (RelativeLayout) view.findViewById(R.id.layout_downloading);
        mRbDownloadedTextbook = (RadioButton) view.findViewById(R.id.downloaded);
        mRbDownloadedTextbook.setOnClickListener(this);
        mRbViewAllTextbook = (RadioButton) view.findViewById(R.id.view_all);
        mRbViewAllTextbook.setOnClickListener(this);
        mTextViewTextbookSize = (EkStepCustomTextView) view.findViewById(R.id.tv_textbook_size);
        mRecyclerViewTextbooks.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mPresenter.handleScrollChanged(recyclerView, newState);
            }
        });
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public void setTextbookName(String name) {
        mTextViewTextbookName.setText(name);
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

    @Override
    public void setIsFromDownload(boolean mIsFromDownloadsScreen) {
        this.mIsFromDownloadsScreen = mIsFromDownloadsScreen;
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
    public void showTextBookShelf(List<TextbookSection> textbookSectionsList, boolean mIsFromDownloadsScreen) {
        if (mTextbookAdapter != null) {
            mTextbookAdapter = null;
        }

        mTextbookAdapter = new TextbookAdapter(getActivity(), textbookSectionsList, mIsFromDownloadsScreen, mPresenter);
        mRecyclerViewTextbooks.setAdapter(mTextbookAdapter);
    }

    @Override
    public void showDetails(Content content, List<HierarchyInfo> contentInfoList, boolean isFromDownloads, boolean isSpineAvailable, boolean isFromTextbook, boolean isParentTextbook) {
        ContentUtil.navigateToContentDetails(getContext(), content, contentInfoList, isFromDownloads, isSpineAvailable, isFromTextbook, isParentTextbook);
    }

    @Override
    public void showCustomToast(String msg) {
        Util.showCustomToast(msg);
    }

    @Override
    public void refreshAdapter(List<TextbookSection> textbookSectionsList) {
        mTextbookAdapter.refresh(textbookSectionsList);
    }

    @Override
    public void generateTOCInteractEvent(String identifier) {
//        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.SHOW, TelemetryStageId.TEXTBOOK_TOC, EntityType.CONTENT_ID.toString(), identifier));
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildImpressionEvent(EnvironmentId.HOME, TelemetryStageId.TEXTBOOK_TOC, ImpressionType.VIEW, EntityType.CONTENT_ID.toString(), identifier, ObjectType.CONTENT));
    }

    @Override
    public void collapseAllLeaf() {
        mAndroidTreeView.collapseAll();
    }

    @Override
    public void scrollTo(int position) {
        mLinearLayoutManager.scrollToPositionWithOffset(position, 0);
    }

    @Override
    public AndroidTreeView getAndroidTreeView() {
        return mAndroidTreeView;
    }

    @Override
    public LinearLayoutManager getLinearLayoutManager() {
        return mLinearLayoutManager;
    }

    public void hideNoTextBooksView() {
        mRelativeLayoutNoTextbooks.setVisibility(GONE);
    }

    @Override
    public void showTextBooksRecyclerView() {
        mRecyclerViewTextbooks.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoTextBooksView() {
        mRelativeLayoutNoTextbooks.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTextBooksRecyclerView() {
        mRecyclerViewTextbooks.setVisibility(GONE);
    }

    @Override
    public void generateSectionViewedEvent(String identifier, List<Map<String, Object>> mSectionMapList) {
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildInteractEvent(EnvironmentId.HOME, InteractionType.OTHER, TelemetryAction.SECTION_VIEWED, TelemetryStageId.TEXTBOOK_HOME, identifier, ObjectType.CONTENT, TelemetryConstant.SECTIONS, mSectionMapList));
    }

    @Override
    public void setIsRequiredToCallApi(boolean isRequiredToCallApi) {
        this.isRequiredToCallApi = isRequiredToCallApi;
    }

    @Override
    public void showProgressReportActivity(Content content) {
        Intent intent = new Intent(mActivity, ProgressReportsActivity.class);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_CONTENT, content);
        mActivity.startActivity(intent);
    }

    @Override
    public void showDownloadedTextbookView() {
        mRbDownloadedTextbook.setTextColor(getResources().getColor(R.color.white));
        mRbViewAllTextbook.setTextColor(getResources().getColor(R.color.text_color_button));
    }

    @Override
    public void showViewAllTextbookView() {
        mRbViewAllTextbook.setTextColor(getResources().getColor(R.color.white));
        mRbDownloadedTextbook.setTextColor(getResources().getColor(R.color.text_color_button));
    }

    /**
     * This method shows Download dialog
     *
     * @param contentsToBeDownloaded
     * @param totalContents
     * @param sectionName
     * @param size
     * @param chapterDownloadButton
     * @param progressBarChapterDownload
     */
    @Override
    public void showDownloadDialog(final List<Content> contentsToBeDownloaded, int totalContents, final String sectionName, String size, final ImageButton chapterDownloadButton, final ProgressBar progressBarChapterDownload) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_chapter_download);
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_pressed}, // pressed
                new int[]{android.R.attr.state_focused}, // focused
                new int[]{}
        };
        int[] colors = new int[]{
                getContext().getResources().getColor(R.color.white), // green
                getContext().getResources().getColor(R.color.white), // green
                getContext().getResources().getColor(R.color.app_black_theme_color)  // white
        };
        ColorStateList list = new ColorStateList(states, colors);
        //dialog name
        EkStepCustomTextView dialogTitle = (EkStepCustomTextView) dialog.findViewById(R.id.chapter_name_dialog);
        dialogTitle.setText("Download " + sectionName);

        //total contents
        EkStepCustomTextView numberOfContents = (EkStepCustomTextView) dialog.findViewById(R.id.tv_download_number_of_lessons);
        numberOfContents.setText(Integer.toString(totalContents));

        //download size
        EkStepCustomTextView downloadSize = (EkStepCustomTextView) dialog.findViewById(R.id.tv_download_size);
        downloadSize.setText(size);

        dialog.findViewById(R.id.txt_download).setFocusable(true);
        dialog.findViewById(R.id.txt_download).setClickable(true);
        ((TextView) dialog.findViewById(R.id.txt_download)).setTextColor(list);
        // if button is clicked, close the custom dialog
        dialog.findViewById(R.id.txt_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.txt_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                if (NetworkUtil.isNetworkAvailable(getContext())) {

                    boolean isExternalStorage = PreferenceUtil.getPreferenceWrapper()
                            .getBoolean(PreferenceKey.KEY_SET_EXTERNAL_STORAGE_DEFAULT, false);
                    if (!isExternalStorage && DeviceUtility.isDeviceMemoryLow()) {
                        showDeviceMemoryLowDialog();
                    } else if (isExternalStorage && DeviceUtility.isSdCardMemoryLow()) {
                        showSdcardMemoryLowDialog();
                    } else {
                        chapterDownloadButton.setVisibility(View.INVISIBLE);
                        progressBarChapterDownload.setVisibility(View.VISIBLE);
                        mPresenter.downloadAll(contentsToBeDownloaded);
                    }
                } else {
                    Util.showCustomToast(R.string.error_network_1);
                }
            }
        });

        dialog.show();
    }

    private void showSdcardMemoryLowDialog() {
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

    private void showDeviceMemoryLowDialog() {
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
    public void showFeedbackDialog(String identifier, float previuosRating) {
        DialogUtils.showFeedbackDialog(mActivity, mPresenter, identifier, previuosRating);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.unregisterSubscriber(this);
        mPresenter.setLearnerState(mLinearLayoutManager);
        mPresenter.removeContentToBeDeletedValue();
    }

    public void handleTelemetryEndEvent() {
        mPresenter.sendTelemetryEndEvent();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.back_btn) {
            mPresenter.handleBackButtonClick();

        } else if (i == R.id.tv_textbook_details) {
            mPresenter.handleDetailsClick();

        } else if (i == R.id.layout_download_all) {
            mPresenter.handleDownloadAllClick();

        } else if (i == R.id.downloaded) {
            mPresenter.showDownloadedLessons();

        } else if (i == R.id.view_all) {
            mPresenter.fetchTableOfContentsAndLessons(true);
        }
    }

    @Override
    public void setTextbookSize(String size) {
        mTextViewTextbookSize.setText(size);
    }

    @Override
    public void hideDownloadAllView() {
        mRelativeLayoutDownloadAll.setVisibility(GONE);
    }

    @Override
    public void showDownloadAllView() {
        mRelativeLayoutDownloadAll.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDownloadingView() {
        mRelativeLayoutDownloading.setVisibility(GONE);
    }

    @Override
    public void showDownloadingView() {
        mRelativeLayoutDownloading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
