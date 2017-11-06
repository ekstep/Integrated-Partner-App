package org.ekstep.genie.ui.mycontent;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.customview.BreadCrumbHorizontalScrollView;
import org.ekstep.genie.customview.EkStepGenericDialog;
import org.ekstep.genie.fragment.BaseFragment;
import org.ekstep.genie.model.ContentDeleteResponse;
import org.ekstep.genie.model.SelectedContent;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.ui.landing.LandingActivity;
import org.ekstep.genie.ui.landing.home.HomeFragment;
import org.ekstep.genie.ui.progressreports.ProgressReportsActivity;
import org.ekstep.genie.ui.settings.SettingsActivity;
import org.ekstep.genie.ui.share.ShareActivity;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.DialogUtils;
import org.ekstep.genie.util.ShowProgressDialog;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.geniesdk.ContentUtil;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.ContentImportResponse;
import org.ekstep.genieservices.commons.bean.ContentSortCriteria;
import org.ekstep.genieservices.commons.bean.HierarchyInfo;
import org.ekstep.genieservices.commons.bean.ScanStorageResponse;
import org.ekstep.genieservices.commons.bean.enums.SortOrder;
import org.ekstep.genieservices.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This fragment is for the lessons shown in the content page.
 * <p>
 * Created by Sneha on 1/24/2017.
 */

public class MyContentFragment extends BaseFragment
        implements MyContentContract.View, View.OnClickListener {

    private MyContentPresenter mPresenter;
    private RecyclerView mMyLessonsRecyclerView;
    private LinearLayout mNoContentCard;
    private View mCardGetMoreContent;
    private RelativeLayout mRelativeLayoutNormalOptions;
    private RelativeLayout mRelativeLayoutSelectedContentOptions;
    private TextView mTxtSelectedContentSize;
    private TextView mTxtSelectedContentCount;

    private View mMenuShareBtn;
    private View mMenuDoneBtn;
    private View mMenuImportBtn;
    private View mMenuContentSort;

    private View mViewContentHeader;
    private View mViewNestedContentHeader;

    private MyContentAdapter mMyContentAdapter;
    private int mRBId = -1;
    private int mSelectedRadioButtonId = -1;
    private boolean mIsSizeSortApplied;
    private boolean mIsTimeSortApplied;
    private BreadCrumbHorizontalScrollView mBreadcrumbscrollView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = (MyContentPresenter) presenter;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDiscrepancyFound(List<ScanStorageResponse> scanStorageResponseList) throws InterruptedException {
        if (scanStorageResponseList != null && scanStorageResponseList.size() > 0) {
            mPresenter.renderLocalContents();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLocalContentImportSuccess(String importSuccess) throws InterruptedException {
        mPresenter.manageLocalContentImportSuccess(importSuccess);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onContentExportSuccess(String exportSuccess) throws InterruptedException {
        mPresenter.manageContentExportSuccess(exportSuccess);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onContentImport(ContentImportResponse importResponse) throws InterruptedException {
        mPresenter.manageImportAndDeleteSuccess(importResponse);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onContentDelete(ContentDeleteResponse deleteResponse) throws InterruptedException {
        mPresenter.manageImportAndDeleteSuccess(deleteResponse);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.registerSubscriber(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_content, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMyLessonsRecyclerView = (RecyclerView) view.findViewById(R.id.download_rv_my_contents);
        mMyLessonsRecyclerView.setHasFixedSize(true);
        mMyLessonsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mNoContentCard = (LinearLayout) view.findViewById(R.id.card_no_content);
        mNoContentCard.setOnClickListener(this);

        mMenuShareBtn = view.findViewById(R.id.btn_share);
        mMenuShareBtn.setOnClickListener(this);
        mMenuShareBtn.setVisibility(View.VISIBLE);

        mMenuDoneBtn = view.findViewById(R.id.btn_done);
        mMenuDoneBtn.setOnClickListener(this);

        mMenuImportBtn = view.findViewById(R.id.btn_import);
        mMenuImportBtn.setOnClickListener(this);

        mMenuContentSort = view.findViewById(R.id.btn_menu_sort);
        mMenuContentSort.setOnClickListener(this);
        view.findViewById(R.id.btn_menu_settings).setOnClickListener(this);
        view.findViewById(R.id.btn_delete).setOnClickListener(this);

        mCardGetMoreContent = view.findViewById(R.id.card_get_more_content);
        mCardGetMoreContent.setOnClickListener(this);
        view.findViewById(R.id.back_btn).setOnClickListener(this);

        mRelativeLayoutNormalOptions = (RelativeLayout) view.findViewById(R.id.download_rl_normal_options);
        mRelativeLayoutSelectedContentOptions = (RelativeLayout) view.findViewById(R.id.download_rl_selected_options);

        mViewContentHeader = view.findViewById(R.id.download_rl_header);
        mViewNestedContentHeader = view.findViewById(R.id.layout_nested_content_header);
        mBreadcrumbscrollView = (BreadCrumbHorizontalScrollView) view.findViewById(R.id.hs_breadcrumb);
        view.findViewById(R.id.layout_back_header).setOnClickListener(this);

        view.findViewById(R.id.txt_download_clear_all).setOnClickListener(this);
        mTxtSelectedContentSize = (TextView) view.findViewById(R.id.txt_download_selected_content_size);
        mTxtSelectedContentCount = (TextView) view.findViewById(R.id.txt_download_selected_content_count);

        mPresenter.renderMyContent();
        view.setFocusableInTouchMode(true);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (getSelectedItems().size() > 0) {
                        showDownloadsExitConfirmationDialog();
                        return true;
                    } else {
                        return false;
                    }

                }
                return false;
            }
        });
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new MyContentPresenter();
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
        if (i == R.id.back_btn) {
            mPresenter.postDeleteSuccessEvent();
            dismissProgressDialog();
            ((LandingActivity) mActivity).showHome(false);

        } else if (i == R.id.btn_share) {
            mPresenter.shareContent();

        } else if (i == R.id.btn_menu_sort) {
            mPresenter.handleSortButtonClick();

        } else if (i == R.id.btn_menu_settings) {
            Intent intent = new Intent(mActivity, SettingsActivity.class);
            intent.putExtra(Constant.BundleKey.BUNDLE_KEY_SETTINGS_POSITION, 3);
            startActivity(intent);

        } else if (i == R.id.btn_delete) {
            showDeleteContentConfirmationDialog(getContentAdapter().getSelectedItems());

        } else if (i == R.id.btn_done) {
            mPresenter.shareContent();

        } else if (i == R.id.btn_import) {
            mPresenter.importContent();

        } else if (i == R.id.card_get_more_content) {
            HomeFragment homeFragment = HomeFragment.newInstance(true);
            ((LandingActivity) mActivity).setFragment(homeFragment);

        } else if (i == R.id.layout_back_header) {
            mPresenter.handleHeaderBackClick();

        } else if (i == R.id.txt_download_clear_all) {
            clearSelected();
            mMyContentAdapter.notifyDataSetChanged();

        } else {
        }
    }

    @Override
    public void dismissProgressDialog() {
        ShowProgressDialog.dismissDialog();
    }

    @Override
    public void showDoneBtn() {
        mMenuDoneBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDoneBtn() {
        mMenuDoneBtn.setVisibility(View.GONE);
    }

    @Override
    public int getMenuBtnVisibility() {
        return mMenuDoneBtn.getVisibility();
    }

    @Override
    public void enableMenuImportBtn() {
        mMenuImportBtn.setEnabled(true);
    }

    @Override
    public void disableMenuImportBtn() {
        mMenuImportBtn.setEnabled(false);
    }

    @Override
    public void notifyAdapterDataSetChanged() {
        mMyContentAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSelectorLayer() {
        mMyContentAdapter.showSelctorLayer(true);
    }

    @Override
    public void hideSelectorLayer() {
        mMyContentAdapter.showSelctorLayer(false);
    }

    @Override
    public void clearSelected() {
        mMyContentAdapter.clearSelected();
    }

    @Override
    public void showMyContentRecyclerView() {
        mMyLessonsRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideMyContentRecyclerView() {
        mMyLessonsRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void toggleSelected(SelectedContent selectedContent) {
        mMyContentAdapter.toggleSelected(selectedContent);
    }

    @Override
    public List<SelectedContent> getSelectedItems() {
        return mMyContentAdapter.getSelectedItems();
    }

    @Override
    public int getSelectedCount() {
        return mMyContentAdapter.getSelectedCount();
    }

    @Override
    public int getItemCount() {
        return mMyContentAdapter.getItemCount();
    }

    @Override
    public void showCustomToast(int msg) {
        Util.showCustomToast(msg);
    }

    @Override
    public void hideNoContentCard() {
        mNoContentCard.setVisibility(View.GONE);
    }

    @Override
    public void showNoContentCard() {
        mNoContentCard.setVisibility(View.VISIBLE);
        enableMenuImportBtn();
        hideDoneBtn();
    }

    @Override
    public void showLocalContents(List<Content> contentList) {
        mMyContentAdapter = new MyContentAdapter(getActivity(), mPresenter);
        mMyContentAdapter.setData(contentList);
        mMyLessonsRecyclerView.setAdapter(mMyContentAdapter);
    }

    @Override
    public void updateLocalContentList(List<Content> updatedContentList) {
        mMyContentAdapter.refresh(updatedContentList);
    }

    @Override
    public void showShareBtn() {
        mMenuShareBtn.setVisibility(View.VISIBLE);
        mMenuShareBtn.setOnClickListener(this);
    }

    @Override
    public void hideShareBtn() {
        mMenuShareBtn.setVisibility(View.GONE);
    }

    @Override
    public void showMoreActionDialog(final Content content) {
        DialogUtils.showDownloadsMoreDialog(getActivity(), content, mPresenter);
    }

    @Override
    public MyContentAdapter getContentAdapter() {
        return mMyContentAdapter;
    }

    @Override
    public void showContentisDraftMessage() {
        Util.showCustomToast(R.string.error_share_cant_share_draft_content);
    }

    @Override
    public void showContentDetailsActivity(Content content) {
        ContentUtil.navigateToContentDetails(mActivity, content, new ArrayList<HierarchyInfo>(), true, true, false, false);
    }

    @Override
    public void showShareOptionsLayout(Content content) {
        List<String> identifierList = new ArrayList<>();
        identifierList.add(content.getContentData().getIdentifier());
        Map<String, String> values = new HashMap<>();
        values.put(Constant.SHARE_NAME, content.getContentData().getName());
        values.put(Constant.SHARE_SCREEN_NAME, TelemetryStageId.MY_CONTENT);
        ShareActivity.startContetExportIntent(mActivity, true, values, identifierList);
    }

    @Override
    public void showProgressReportActivity(Content content) {
        Intent intent = new Intent(mActivity, ProgressReportsActivity.class);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_CONTENT, content);
        mActivity.startActivity(intent);
    }

    @Override
    public void showFeedBackDialog(final String identifier, float previousRating) {
        DialogUtils.showFeedbackDialog(mActivity, mPresenter, identifier, previousRating);
    }

    @Override
    public void showDeleteConfirmationLayout(long size, int refCount, String identifier) {
        showContentDeletedSuccessfullyMessage(size, refCount, identifier);
    }

    @Override
    public void showSelectedContentOptions() {
        mRelativeLayoutSelectedContentOptions.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNormalOptions() {
        mRelativeLayoutNormalOptions.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSortDialog() {

        final EkStepGenericDialog ekStepGenericDialog = new EkStepGenericDialog.Builder(mActivity).
                setTitle(R.string.label_downloads_sort_content_by).
                setCustomView(R.layout.dialog_content_sort).
                setPositiveButtonText(R.string.label_dialog_ok).
                setNegativeButtonText(R.string.label_dialog_cancel).
                onPositiveClick(new EkStepGenericDialog.Callback() {
                    @Override
                    public void onClick(@NonNull EkStepGenericDialog dialog, @NonNull EkStepGenericDialog.Action action) {
                        dialog.dismiss();
                        mSelectedRadioButtonId = mRBId;
                        if (mSelectedRadioButtonId == R.id.rb_file_size) {
                            if (!mIsSizeSortApplied) {
                                mIsSizeSortApplied = true;
                                mPresenter.renderLocalContents(new ContentSortCriteria("sizeOnDevice", SortOrder.DESC));
                            } else {
                                mPresenter.renderLocalContents(new ContentSortCriteria("sizeOnDevice", SortOrder.ASC));
                            }
                            mIsTimeSortApplied = false;


                        } else if (mSelectedRadioButtonId == R.id.rb_last_used) {
                            if (!mIsTimeSortApplied) {
                                mIsTimeSortApplied = true;
                                mPresenter.renderLocalContents(new ContentSortCriteria("lastUsedOn", SortOrder.ASC));
                            } else {
                                mPresenter.renderLocalContents(new ContentSortCriteria("lastUsedOn", SortOrder.DESC));
                            }
                            mIsSizeSortApplied = false;


                        }
                        dialog.dismiss();
                    }
                }).
                onNegativeClick(new EkStepGenericDialog.Callback() {
                    @Override
                    public void onClick(@NonNull EkStepGenericDialog dialog, @NonNull EkStepGenericDialog.Action action) {
                        dialog.dismiss();
                    }
                })
                .build();
        ekStepGenericDialog.show();

        View customView = ekStepGenericDialog.getCustomView();
        RadioGroup radioGroup = (RadioGroup) customView.findViewById(R.id.rg_content_sort);
        if (mSelectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = (RadioButton) customView.findViewById(mSelectedRadioButtonId);
            selectedRadioButton.setChecked(true);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mRBId = checkedId;

            }
        });
    }

    @Override
    public void showContentHeader() {
        mViewContentHeader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideContentHeader() {
        mViewContentHeader.setVisibility(View.GONE);
    }

    @Override
    public void showNestedContentHeader() {
        mViewNestedContentHeader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNestedContentHeader() {
        mViewNestedContentHeader.setVisibility(View.GONE);
    }

    @Override
    public void showHeaderBreadCrumb(List<Map<String, String>> contentHeader) {
        mBreadcrumbscrollView.addBreadcrumbs(contentHeader);
    }

    @Override
    public void smoothScrollTo(int position) {
        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(mActivity) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }

            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                return null;
            }
        };
        smoothScroller.setTargetPosition(position);
        mMyLessonsRecyclerView.getLayoutManager().startSmoothScroll(smoothScroller);
    }

    @Override
    public void setSelectedContentSize(long size) {
        mTxtSelectedContentSize.setText(Util.humanReadableByteCount(size, true));
    }

    @Override
    public void setSelectedContentCount(int count) {
        mTxtSelectedContentCount.setText(String.valueOf(count) + " " + getString(R.string.label_downloads_items));
    }

    @Override
    public void showDeleteContentConfirmationDialog(final List<SelectedContent> selectedContents) {
        String size = Util.humanReadableByteCount(mPresenter.getSelectedContentSize(selectedContents), true);
        EkStepGenericDialog ekStepGenericDialog = new EkStepGenericDialog.Builder(mActivity).
                setTitle(R.string.title_download_confirm_delete).
                setDescription(getString(R.string.msg_download_confirm_delete_desc, size)).
                setPositiveButtonText(R.string.label_dialog_ok).
                setNegativeButtonText(R.string.label_dialog_cancel).
                onPositiveClick(new EkStepGenericDialog.Callback() {
                    @Override
                    public void onClick(@NonNull EkStepGenericDialog dialog, @NonNull EkStepGenericDialog.Action action) {
                        dialog.dismiss();
                        mPresenter.deleteContent(selectedContents);
                    }
                }).
                onNegativeClick(new EkStepGenericDialog.Callback() {
                    @Override
                    public void onClick(@NonNull EkStepGenericDialog dialog, @NonNull EkStepGenericDialog.Action action) {
                        dialog.dismiss();
                    }
                })
                .build();
        ekStepGenericDialog.show();
    }

    @Override
    public void showContentDeletedSuccessfullMessage(String size) {
        Util.showCustomToast(getString(R.string.msg_download_delete_successfull, size));
    }

    @Override
    public void showContentSort() {
        mMenuContentSort.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideContentSort() {
        mMenuContentSort.setVisibility(View.GONE);
    }

    @Override
    public void showContentDeletedSuccessfullyMessage(long size, final int refCount, final String identifier) {

        final SelectedContent selectedContent = new SelectedContent(identifier, size, false);
        final List<SelectedContent> selectedContentList = new ArrayList<>();
        selectedContentList.add(selectedContent);

        String contentSize = Util.humanReadableByteCount(size, true);

        EkStepGenericDialog ekStepGenericDialog = new EkStepGenericDialog.Builder(mActivity).
                setTitle(R.string.title_download_confirm_delete).
                setDescription(getDialogDesc(refCount, contentSize)).
                setPositiveButtonText(R.string.label_dialog_ok).
                setNegativeButtonText(R.string.label_dialog_cancel).
                onPositiveClick(new EkStepGenericDialog.Callback() {
                    @Override
                    public void onClick(@NonNull EkStepGenericDialog dialog, @NonNull EkStepGenericDialog.Action action) {
                        dialog.dismiss();
                        mPresenter.deleteContent(selectedContentList);
                    }
                }).
                onNegativeClick(new EkStepGenericDialog.Callback() {
                    @Override
                    public void onClick(@NonNull EkStepGenericDialog dialog, @NonNull EkStepGenericDialog.Action action) {
                        dialog.dismiss();
                    }
                })
                .build();
        ekStepGenericDialog.show();
    }

    private String getDialogDesc(int refCount, String contentSize) {
        if (refCount > 1) {
            return getString(R.string.msg_dialog_delete_confirmation_for_content_multiple_existence, contentSize);
        } else {
            return getString(R.string.msg_download_confirm_delete_desc, contentSize);
        }
    }

    @Override
    public void hideSelectedContentOptions() {
        mRelativeLayoutSelectedContentOptions.setVisibility(View.GONE);
    }

    @Override
    public void hideNormalOptions() {
        mRelativeLayoutNormalOptions.setVisibility(View.GONE);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.unregisterSubscriber(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void showDownloadsExitConfirmationDialog() {
        EkStepGenericDialog ekStepGenericDialog = new EkStepGenericDialog.Builder(mActivity).
                setDescription(R.string.msg_downlod_exit).
                setPositiveButtonText(R.string.label_dialog_yes).
                setNegativeButtonText(R.string.label_dialog_no).
                onPositiveClick(new EkStepGenericDialog.Callback() {
                    @Override
                    public void onClick(@NonNull EkStepGenericDialog dialog, @NonNull EkStepGenericDialog.Action action) {
                        dialog.dismiss();
                        ((LandingActivity) mActivity).showHome(false);
                    }
                }).
                onNegativeClick(new EkStepGenericDialog.Callback() {
                    @Override
                    public void onClick(@NonNull EkStepGenericDialog dialog, @NonNull EkStepGenericDialog.Action action) {
                        dialog.dismiss();
                    }
                })
                .build();
        ekStepGenericDialog.show();
    }

}
