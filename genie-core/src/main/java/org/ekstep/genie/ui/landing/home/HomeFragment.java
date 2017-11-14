package org.ekstep.genie.ui.landing.home;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.adapters.PopUpWindowAdapter;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.callback.IMenuItemClick;
import org.ekstep.genie.customview.EkStepCustomAutoCompleteTextView;
import org.ekstep.genie.customview.EkStepCustomTextView;
import org.ekstep.genie.customview.EkStepGenericDialog;
import org.ekstep.genie.customview.FancyCoverFlow;
import org.ekstep.genie.customview.HorizontalSpacingDecoration;
import org.ekstep.genie.customview.InteractiveScrollView;
import org.ekstep.genie.fragment.BaseFragment;
import org.ekstep.genie.model.ContentDeleteResponse;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.telemetry.enums.EntityType;
import org.ekstep.genie.ui.addchild.AddChildActivity;
import org.ekstep.genie.ui.landing.LandingActivity;
import org.ekstep.genie.ui.landing.home.download.DownloadQueueFragment;
import org.ekstep.genie.ui.mycontent.MyContentFragment;
import org.ekstep.genie.ui.mylessons.MyLessonsActivity;
import org.ekstep.genie.ui.progressreports.ProgressReportsActivity;
import org.ekstep.genie.ui.search.SearchActivity;
import org.ekstep.genie.ui.search.SearchHistoryAdapter;
import org.ekstep.genie.util.AnimationUtils;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.DeviceUtility;
import org.ekstep.genie.util.FontConstants;
import org.ekstep.genie.util.KeyboardUtil;
import org.ekstep.genie.util.PlayerUtil;
import org.ekstep.genie.util.PopUpWindowUtil;
import org.ekstep.genie.util.ThemeUtility;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.geniesdk.ContentUtil;
import org.ekstep.genie.util.geniesdk.UserProfileUtil;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.ContentData;
import org.ekstep.genieservices.commons.bean.ContentImportResponse;
import org.ekstep.genieservices.commons.bean.ContentListingSection;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.Profile;
import org.ekstep.genieservices.commons.bean.ScanStorageResponse;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.ekstep.genieservices.commons.bean.enums.ScanStorageStatus;
import org.ekstep.genieservices.commons.utils.StringUtil;
import org.ekstep.genieservices.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is Home Fragment, where all the contents will be shown section wise, and also can perform search
 * <p>
 * <p>
 * Created by shriharsh on 4/1/17.
 */

public class HomeFragment extends BaseFragment
        implements HomeContract.View, View.OnClickListener,
        IMenuItemClick, InteractiveScrollView.OnInteractListener,
        EkStepCustomAutoCompleteTextView.KeyImeChange {

    public static final String SEARCH_QUERY = "search_query";
    public static final String IS_EXPLICIT = "is_explicit";

    private static final String TAG = HomeFragment.class.getSimpleName();

    private HomeContract.Presenter mHomePresenter;

    private InteractiveScrollView mScroll = null;
    private EkStepCustomTextView mChangeSubjectBtn;
    private ImageView mAddChildOrAvatarImage;
    private LinearLayout mSectionLayout = null;
    private Dialog mDialog;
    private RelativeLayout mSearchLayout = null;
    private EkStepCustomAutoCompleteTextView mSearchEditText;
    private View mDisabledView = null;
    private View mCardView = null;
    private RecyclerView mRecyclerViewMyLessons;
    private LinearLayout mLinearLayoutInlineProgressBar;
    private RelativeLayout mRelativeLayoutDownloadContent;
    private EkStepCustomTextView mTextViewSubjectName;
    private RelativeLayout mDummyHeader;
    private View mHeader;
    private ImageView mDummyAddChild;
    private EkStepCustomTextView mDummyChangeSubject;
    private RelativeLayout mRelativeLayoutDummyChangeSubject;
    private View mTransparentView;
    private EkStepCustomTextView mSkipTextView;
    private LinearLayout mDummyWelcomeDialog;
    private EkStepCustomTextView mTextViewMyTextBook;
    private RecyclerView mRecyclerViewMyTextBook;
    private EkStepCustomTextView mTextViewMyLessons;
    private EkStepCustomTextView mTextViewOfflineStatus;
    private EkStepCustomTextView mTextViewFooter;
    private LinearLayout mLinearLayoutSectionAndOfflineStatus;
    private RelativeLayout mLayoutOverlay;
    private View mViewLessonsViewAll;
    private View mViewBookssViewAll;
    private EkStepCustomTextView mTxtDeviceMemoryLow;
    private int mScrollEndReachCount = 0;
    private List<View> mViewList = new ArrayList<>();
    private RelativeLayout mRelativeLayoutChangeSubject;
    private ImageView ivShowDownloadQueue;
    private AnimationDrawable mDownloadingFrameAnimation;
    private View mLinearLayout_Genie_Offline;
    private long mLastClickTime = 0;
    private boolean isChangeSubjectClicked = false;
    private boolean isSearchBarVisible = false;
    private DownloadQueueFragment downloadQueueFragment;

    private BroadcastReceiver mNetworkConnectedBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mHomePresenter.manageNetworkConnectivity(context);

        }
    };

    public static HomeFragment newInstance(boolean scrollToBestofGenie) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putBoolean(Constant.EXTRAS_SCROLL_TO_BEST_OF_GENIE, scrollToBestofGenie);
        fragment.setArguments(args);
        return fragment;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDiscrepancyFound(List<ScanStorageResponse> scanStorageResponseList) throws InterruptedException {
        if (scanStorageResponseList != null && scanStorageResponseList.size() > 0) {
            int addedContents = 0;
            int deletedContents = 0;

            //remove all the identifiers from the local cache
            for (ScanStorageResponse storageResponse : scanStorageResponseList) {
                if (storageResponse.getStatus().equals(ScanStorageStatus.DELETED)) {
                    ContentUtil.removeFromLocalCache(storageResponse.getIdentifier());
                    deletedContents++;
                } else if (storageResponse.getStatus().equals(ScanStorageStatus.ADDED)) {
                    addedContents++;
                }
            }

            mHomePresenter.getLocalContents();

            //show the discrepancy dialog
            showDiscrepancyDialog(deletedContents, addedContents);
        }
    }

    private void showDiscrepancyDialog(int deletedContents, int addedContents) {
        StringBuilder discrepancyMessage = new StringBuilder();

        if (deletedContents > 1) {
            discrepancyMessage.append(deletedContents + " " + getString(R.string.label_discrepancy_deleted_plural));
        } else if (deletedContents == 1) {
            discrepancyMessage.append(deletedContents + " " + getString(R.string.label_discrepancy_deleted_singular));
        }

        if (addedContents > 1) {
            discrepancyMessage.append("\n" + addedContents + " " + getString(R.string.label_discrepancy_added_plural));
        } else if (addedContents == 1) {
            discrepancyMessage.append("\n" + addedContents + " " + getString(R.string.label_discrepancy_added_singular));
        }

        EkStepGenericDialog ekStepGenericDialog = new EkStepGenericDialog.Builder(mActivity).
                setTitle(R.string.label_discrepancy_found).
                setDescription(discrepancyMessage.toString()).
                setPositiveButtonText(R.string.label_dialog_ok).
                onPositiveClick(new EkStepGenericDialog.Callback() {
                    @Override
                    public void onClick(@NonNull EkStepGenericDialog dialog, @NonNull EkStepGenericDialog.Action action) {
                        dialog.dismiss();
                    }
                }).build();

        ekStepGenericDialog.show();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onRefreshProfile(String refreshProfile) throws InterruptedException {
        mHomePresenter.manageRefreshProfile(refreshProfile);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLocalContentImportSuccess(String importSuccess) throws InterruptedException {
        mHomePresenter.manageLocalContentImportSuccess(importSuccess);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onContentImport(ContentImportResponse importResponse) throws InterruptedException {
        mHomePresenter.manageImportResponse(importResponse);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onContentDelete(ContentDeleteResponse importResponse) throws InterruptedException {
        mHomePresenter.manageImportnDeleteSuccess(importResponse);
    }

//    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
//    public void onEditChildSuccess(String editChildSuccess) throws InterruptedException {
//        mHomePresenter.editChildSuccess(editChildSuccess);
//    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.isSubscriberRegistered(this)) {
            EventBus.registerSubscriber(this);
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.unregisterSubscriber(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mNetworkConnectedBroadcastReceiver);
        mHomePresenter.onPause();
        mHomePresenter.setLocalContentsCalledAlreadyToFalse();
        stopDownloadAnimation();
    }

    @Override
    public void onResume() {
        super.onResume();
        mScrollEndReachCount = 0;
        mHomePresenter.checkIfUserHasOnBoardedChangeSubjectStep();
        registerNetworkBroadcastReceiver();
        mHomePresenter.getCurrentUser();
        mHomePresenter.handleDownloadingAnimation(null);
    }

    private void registerNetworkBroadcastReceiver() {
        getActivity().registerReceiver(mNetworkConnectedBroadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    public void showFooter() {
        mTextViewFooter.setVisibility(View.VISIBLE);
    }

    public void hideFooter() {
        mTextViewFooter.setVisibility(View.GONE);
    }

    @Override
    public void setChosenSubjectName(String subjectName) {
        mTextViewSubjectName.setText(subjectName);
    }

    @Override
    public void showMyLessonsLocalContentAdapter(List<Content> contentList, Profile profile, boolean isKnownUserProfile) {
        LocalContentAdapter adapter = new LocalContentAdapter(mActivity, profile, mHomePresenter);
        adapter.setData(contentList);
        mRecyclerViewMyLessons.setAdapter(adapter);
    }

    @Override
    public void showMyTextbooksLocalContentAdapter(List<Content> textbookList, Profile profile, boolean isKnownUserProfile) {
        LocalContentAdapter adapter = new LocalContentAdapter(mActivity, profile, mHomePresenter);
        adapter.setData(textbookList);
        mRecyclerViewMyTextBook.setAdapter(adapter);
    }

    @Override
    public void showMyTextbooksVisibility() {
        mTextViewMyTextBook.setVisibility(View.VISIBLE);
        mRecyclerViewMyTextBook.setVisibility(View.VISIBLE);
    }

    @Override
    public void generateAddChildSkippedTelemetry() {
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.GENIE_HOME_ONBOARDING_SCREEN, TelemetryAction.ADD_CHILD_SKIPPED));
    }

    @Override
    public void generateSubjectSkippedTelemetry() {
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.GENIE_HOME_ONBOARDING_SCREEN, TelemetryAction.CHANGE_SUBJECT_SKIPPED));
    }

    @Override
    public void generateOnboardingCompletedTelemetry() {
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.SHOW, TelemetryStageId.GENIE_HOME_ONBOARDING_SCREEN, EntityType.ONBOARDING));
    }

    @Override
    public void generateSubjectChangedTelemetry(String subject) {
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.GENIE_HOME, TelemetryAction.SUBJECT_CHANGED, subject));
    }

    @Override
    public void generateViewMoreClickedTelemetry(String sectionName) {
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.GENIE_HOME, TelemetryAction.VIEW_MORE_CLICKED, sectionName));
    }

    @Override
    public void playContent(Content content) {
        PlayerUtil.startContent(getActivity(), content, TelemetryStageId.GENIE_HOME, false, false);
    }

    @Override
    public void showTransparentView() {
        mLayoutOverlay.setVisibility(View.VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomePresenter = (HomeContract.Presenter) presenter;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //initialise all the view
        initView(view);

        mHomePresenter.openHome(getArguments());

        mHomePresenter.getLocalContents();

        mHomePresenter.checkForOnBoarding();

        mHomePresenter.checkForDeviceMemoryLow();

//        mHomePresenter.checkForLowInternalMemory();

        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search(mSearchEditText);
                    return true;
                }
                return false;
            }
        });

        mSearchEditText.setKeyImeChangeListener(this);

        mHomePresenter.manageDrawer();
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new HomePresenter();
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

    private void initView(View view) {
        mDummyHeader = (RelativeLayout) view.findViewById(R.id.dummy_layout_header);
        mCardView = view.findViewById(R.id.card_view);
        mHeader = (RelativeLayout) view.findViewById(R.id.layout_header);
        mDummyAddChild = (ImageView) view.findViewById(R.id.dummy_add_child_or_avatar_image);
        mDummyAddChild.setOnClickListener(this);
        mDummyChangeSubject = (EkStepCustomTextView) view.findViewById(R.id.dummy_txt_change_subject);

        mRelativeLayoutDummyChangeSubject = (RelativeLayout) view.findViewById(R.id.dummy_rl_change_subject);
        mRelativeLayoutDummyChangeSubject.setOnClickListener(this);

        mTransparentView = view.findViewById(R.id.transparent_view);
        mSkipTextView = (EkStepCustomTextView) view.findViewById(R.id.layout_skip);
        mSkipTextView.setOnClickListener(this);
        mDummyWelcomeDialog = (LinearLayout) view.findViewById(R.id.welcome_dialog);

        mTextViewSubjectName = (EkStepCustomTextView) view.findViewById(R.id.txt_subject_name);
        mHomePresenter.setSubjectName();

        mScroll = (InteractiveScrollView) view.findViewById(R.id.scroll_home);
        mScroll.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {

                    mScroll.startScrollerTask();
                }

                return false;
            }
        });
        mScroll.setOnBottomReachedListener(this);

        mTextViewOfflineStatus = (EkStepCustomTextView) view.findViewById(R.id.tv_offline_status);

        mTextViewFooter = (EkStepCustomTextView) view.findViewById(R.id.tv_footer);
        mTextViewFooter.setOnClickListener(this);

        mLinearLayoutSectionAndOfflineStatus = (LinearLayout) view.findViewById(R.id.ll_dynamic_and_offline_content);

        mChangeSubjectBtn = (EkStepCustomTextView) view.findViewById(R.id.txt_change_subject_name);

        view.findViewById(R.id.img_berger_menu).setOnClickListener(this);

        mAddChildOrAvatarImage = (ImageView) view.findViewById(R.id.add_child_or_avatar_image);
        mAddChildOrAvatarImage.setOnClickListener(this);

        view.findViewById(R.id.search_content_btn).setOnClickListener(this);

        ivShowDownloadQueue = (ImageView) view.findViewById(R.id.iv_show_download_queue);
        ivShowDownloadQueue.setVisibility(View.INVISIBLE);
        ivShowDownloadQueue.setOnClickListener(this);

        mSectionLayout = (LinearLayout) view.findViewById(R.id.home_section_layout);

        mSearchLayout = (RelativeLayout) view.findViewById(R.id.layout_toolbar_search);
        mSearchEditText = (EkStepCustomAutoCompleteTextView) view.findViewById(R.id.edt_tool_search);

        mDisabledView = view.findViewById(R.id.rl_disabled_view);
        mDisabledView.setOnClickListener(this);

        view.findViewById(R.id.layout_tool_back).setOnClickListener(this);
        view.findViewById(R.id.btn_tool_search).setOnClickListener(this);

        mTextViewMyTextBook = (EkStepCustomTextView) view.findViewById(R.id.tv_my_text_book);
        mRecyclerViewMyTextBook = (RecyclerView) view.findViewById(R.id.rv_my_text_books);
        mRecyclerViewMyTextBook.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewMyTextBook.addItemDecoration(new HorizontalSpacingDecoration((int) getResources().getDimension(R.dimen.section_divider)));

        mTextViewMyLessons = (EkStepCustomTextView) view.findViewById(R.id.tv_my_lessons);
        mRecyclerViewMyLessons = (RecyclerView) view.findViewById(R.id.rv_my_lessons);
        mRecyclerViewMyLessons.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewMyLessons.addItemDecoration(new HorizontalSpacingDecoration((int) getResources().getDimension(R.dimen.section_divider)));

        mLinearLayoutInlineProgressBar = (LinearLayout) view.findViewById(R.id.ll_progress_header);

        mRelativeLayoutDownloadContent = (RelativeLayout) view.findViewById(R.id.rl_download_new_content);

        mLayoutOverlay = (RelativeLayout) view.findViewById(R.id.layout_transparent_bg);

        mRelativeLayoutChangeSubject = (RelativeLayout) view.findViewById(R.id.rl_change_subject);
        mRelativeLayoutChangeSubject.setOnClickListener(this);

        mLinearLayout_Genie_Offline = view.findViewById(R.id.ll_genie_offline);
        mViewLessonsViewAll = view.findViewById(R.id.layout_more_lessons);
        mViewLessonsViewAll.setOnClickListener(this);
        mViewBookssViewAll = view.findViewById(R.id.layout_more_textbook);
        mViewBookssViewAll.setOnClickListener(this);

        mTxtDeviceMemoryLow = (EkStepCustomTextView) view.findViewById(R.id.manage_memory);
        mTxtDeviceMemoryLow.setOnClickListener(this);
    }

    @Override
    public void startDownloadAnimation() {
        ivShowDownloadQueue.setVisibility(View.VISIBLE);
        ivShowDownloadQueue.setBackgroundResource(R.drawable.iv_download_queue_anim);
        if (mDownloadingFrameAnimation != null && mDownloadingFrameAnimation.isRunning()) {
            return;
        }
        mDownloadingFrameAnimation = (AnimationDrawable) ivShowDownloadQueue.getBackground();
        ivShowDownloadQueue.post(new Runnable() {
            public void run() {
                mDownloadingFrameAnimation.start();
            }
        });
    }

    @Override
    public void stopDownloadAnimation() {
        ivShowDownloadQueue.post(new Runnable() {
            public void run() {
                if (mDownloadingFrameAnimation != null && mDownloadingFrameAnimation.isRunning()) {
                    mDownloadingFrameAnimation.stop();
                    mDownloadingFrameAnimation = null;
                    ivShowDownloadQueue.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    /**
     * Search content in Search Result activity by passing the string query searched
     *
     * @param mSearchEditText
     */
    public void search(EkStepCustomAutoCompleteTextView mSearchEditText) {
        mHomePresenter.searchContent(this.mSearchEditText.getText().toString(), mSearchEditText);
    }

    public void hideKeyboard(EditText editText) {
        KeyboardUtil.hideSoftKeyboard(getActivity(), editText);
    }

    @Override
    public void openSearchActivity(String query, boolean isExplicit, Profile profile) {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_SEARCH_QUERY, query);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_IS_EXPLICIT, isExplicit);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_CURRENT_PROFILE, profile);
        startActivity(intent);
    }

    @Override
    public void popUpSubjects(List<String> subjectsList) {
        PopUpWindowAdapter popUpWindowAdapter = new PopUpWindowAdapter(subjectsList, this);
        PopUpWindowUtil.PopupWindowArgBuilder popupWindowArgBuilder = new PopUpWindowUtil.PopupWindowArgBuilder().
                setAnchor(mRelativeLayoutChangeSubject).
                setTransparentView(mLayoutOverlay).
                setContext(getActivity()).
                setAdapter(popUpWindowAdapter).
                setDrawable(R.drawable.layer_dialog_with_border).
                setId(R.id.recycler_view_pop_up_dialog).
                setScrollbarEnabled(false).
                setFadingScrollBarEnabled(true).
                isOnBoarding(PreferenceUtil.isFirstTime());

        if (PopUpWindowUtil.mPopupWindow == null)
            PopUpWindowUtil.showPopUpWindow(popupWindowArgBuilder);
    }

    @Override
    public void showAddChildOnBoarding() {
        //first show all the on boarding views required
        showOnBoardingView();

        //show only add child on boarding
        showOnlyAddChildView();

        //add balloon text
        showAddChildBalloonText();
    }

    /**
     * This method adds add child balloon and text in it
     */
    private void showAddChildBalloonText() {
        mDummyWelcomeDialog.removeAllViews();

        RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //calculate margins
        int marginLeft = (int) (DeviceUtility.getDeviceWidth(getActivity()) / 2);
        int marginRight = getActivity().getResources().getDimensionPixelOffset(R.dimen.spacing_normal);
        int marginTop = getActivity().getResources().getDimensionPixelOffset(R.dimen.avatar_header_height);
        int marginBottom = 0;
        parms.setMargins(marginLeft, marginTop, marginRight, marginBottom);

        final View dialog = getActivity().getLayoutInflater().inflate(R.layout.layout_balloon_on_board_add_child, null);
        mDummyWelcomeDialog.setLayoutParams(parms);

        mDummyWelcomeDialog.addView(dialog);
    }

    /**
     * This method adds add child balloon and text in it
     */
    private void showChangeSubjectBalloonText() {
        mDummyWelcomeDialog.removeAllViews();
        RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //calculate margins
        int marginLeft = getActivity().getResources().getDimensionPixelOffset(R.dimen.spacing_normal);
        int marginRight = getActivity().getResources().getDimensionPixelOffset(R.dimen.spacing_normal);
        int marginTop = getActivity().getResources().getDimensionPixelOffset(R.dimen.avatar_header_height);
        int marginBottom = 0;
        parms.setMargins(marginLeft, marginTop, marginRight, marginBottom);

        final View dialog = getActivity().getLayoutInflater().inflate(R.layout.layout_balloon_on_board_change_subject, null);
        mDummyWelcomeDialog.setLayoutParams(parms);

        mDummyWelcomeDialog.addView(dialog);

        //Fix for Onboarding view disappearing on android N devices.
        if (Build.VERSION.SDK_INT >= 24) {
            mDummyWelcomeDialog.invalidate();
        }

    }

    @Override
    public void showChangeSubjectOnBoarding() {
        if (!isChangeSubjectClicked) {
            //first show all the on boarding views required
            showOnBoardingView();

            //show only change subject on boarding
            showOnlyChangeSubjectView();

            //add change subject balloon
            showChangeSubjectBalloonText();
        }
    }

    private void showOnlyChangeSubjectView() {
        //hide visibility of dummy add child
        mDummyAddChild.setVisibility(View.INVISIBLE);

        //show visibility of change subject
        mRelativeLayoutDummyChangeSubject.setVisibility(View.VISIBLE);
    }

    /**
     * Shows only add child view while onboarding
     */
    private void showOnlyAddChildView() {
        //hide the change subject view
        mRelativeLayoutDummyChangeSubject.setVisibility(View.INVISIBLE);
    }

    /**
     * Enable all the onboarding views
     */
    private void showOnBoardingView() {
        mDummyHeader.setVisibility(View.VISIBLE);
        mDummyAddChild.setVisibility(View.VISIBLE);
        mRelativeLayoutDummyChangeSubject.setVisibility(View.VISIBLE);
        mTransparentView.setVisibility(View.VISIBLE);
        mDummyWelcomeDialog.setVisibility(View.VISIBLE);
        mSkipTextView.setVisibility(View.VISIBLE);
    }

    /**
     * Disables/Hides all onboarding views
     */
    public void disableOnBoardingViews() {
        mDummyHeader.setVisibility(View.GONE);
        mTransparentView.setVisibility(View.GONE);
        mDummyWelcomeDialog.setVisibility(View.GONE);
        mSkipTextView.setVisibility(View.GONE);

        mHomePresenter.manageDrawer();

        //Generate onboarding completed event.
        generateOnboardingCompletedTelemetry();
    }

    private void removeChangeSubjectBaloon() {
        mDummyWelcomeDialog.setVisibility(View.GONE);
    }

    @Override
    public void showSectionsLayout() {
        mSectionLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoDataAvailableView() {
        mTextViewOfflineStatus.setVisibility(View.GONE);
    }

    @Override
    public void showNoDataAvailableView(GenieResponse genieResponse) {
        mTextViewOfflineStatus.setVisibility(View.VISIBLE);
        Util.processSearchFailure(mTextViewOfflineStatus, genieResponse);
    }

    @Override
    public void hideSectionLayout() {
        mSectionLayout.setVisibility(View.GONE);
    }

    @Override
    public void inflateEmptySection(ContentListingSection section, int sectionCount) {
        inflateSection(section, sectionCount, null);
    }

    @Override
    public void generateSectionViewdTelemetryEvent(List<Map<String, Object>> sectionViewMap) {
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.OTHER, TelemetryStageId.GENIE_HOME, TelemetryAction.SECTION_VIEWED, null, sectionViewMap));
    }

    @Override
    public void generateGenieHomeTelemetry(String uid) {
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.SHOW, TelemetryStageId.GENIE_HOME, EntityType.UID, uid));
    }

    @Override
    public void shakeLayoutMore(TextView layout_More) {
        AnimationUtils.showShakeAnimation(layout_More);
    }

    @Override
    public void lockDrawer() {
        ((LandingActivity) mActivity).getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void unLockDrawer() {
        ((LandingActivity) mActivity).getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public void showGenieOfflineLayout() {
        mLinearLayout_Genie_Offline.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideGenieOfflineLayout() {
        mLinearLayout_Genie_Offline.setVisibility(View.GONE);
    }

    @Override
    public void scrollToBestofGenie() {
        ValueAnimator realSmoothScrollAnimation = ValueAnimator.ofInt(mScroll.getScrollY(), mLinearLayoutSectionAndOfflineStatus.getTop());
        realSmoothScrollAnimation.setDuration(500);
        realSmoothScrollAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int scrollTo = (Integer) animation.getAnimatedValue();
                mScroll.scrollTo(0, scrollTo);
            }
        });

        realSmoothScrollAnimation.start();
    }

    @Override
    public void inflateSectionWithContents(ContentListingSection section, int sectionCount, List<ContentData> contentDataList) {
        inflateSection(section, sectionCount, contentDataList);
    }

    private void inflateSection(ContentListingSection section, int sectionCount, final List<ContentData> contentDataList) {
        if (getActivity() != null) {
            View view = View.inflate(getActivity(), R.layout.layout_home_section_item, null);

            //check if the sectionCount is odd or even, if even apply light yellow, if odd app lighter yellow
            int oddOrEvenSection = (sectionCount % 2);
            if (oddOrEvenSection == 0) {
                view.setBackgroundColor(ThemeUtility.getColor(mActivity, new int[]{R.attr.sectionBackgroundLight}));
            } else {
                view.setBackgroundColor(ThemeUtility.getColor(mActivity, new int[]{R.attr.sectionBackgroundDark}));
            }

            TextView txt_Section = (TextView) view.findViewById(R.id.txt_section);
            String sectionName = section.getSectionName();
            txt_Section.setText(sectionName);

            final TextView layout_More = (TextView) view.findViewById(R.id.layout_more);

            TextView tvNotEnoughContent = (TextView) view.findViewById(R.id.txt_section_not_enough_content);

            RecyclerView rv = (RecyclerView) view.findViewById(R.id.section_content_rv);


            if (contentDataList == null) {
                //hide layout more
                layout_More.setVisibility(View.INVISIBLE);

                //show not enough content text
                tvNotEnoughContent.setVisibility(View.VISIBLE);

                //hide recycler view
                rv.setVisibility(View.GONE);

            } else {

                view.setTag(sectionName);
                mViewList.add(view);
                //hide not enough content
                tvNotEnoughContent.setVisibility(View.GONE);

                //show recycler view
                rv.setVisibility(View.VISIBLE);

                layout_More.setTag(section);
                layout_More.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ContentListingSection taggedSection = (ContentListingSection) v.getTag();
                        mHomePresenter.navigateToSearchResultActivity(taggedSection);
                    }
                });

                final LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                rv.setLayoutManager(llm);
                rv.addItemDecoration(new HorizontalSpacingDecoration((int) getResources().getDimension(R.dimen.section_divider)));

                rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        mHomePresenter.onScrollChanged(recyclerView, newState, layout_More);
                    }
                });

                Map<String, String> sectionMap = new HashMap<>();
                sectionMap.put(Constant.SECTION_NAME, sectionName);
                sectionMap.put(Constant.SECTION_REMSGID, section.getResponseMessageId());
                sectionMap.put(Constant.SECTION_APIID, section.getApiId());

                SectionAdapter sectionAdapter = new SectionAdapter(getActivity(), contentDataList, sectionMap, mHomePresenter);
                rv.setAdapter(sectionAdapter);
            }

            mSectionLayout.addView(view);
        }

    }

    // TODO: 27/6/17   try to make this as a single method
    @Override
    public void navigateToSearchWithSearchSortFilterCriteria(ContentListingSection section, Profile profile) {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_CONTENT_SEARCH_CRITERIA, section.getContentSearchCriteria());
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_DEEP_LINK_SEARCH_QUERY, section.getSectionName());
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_DEEP_LINK_SEARCH_QUERY, section.getSectionName());
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_CURRENT_PROFILE, profile);
        getActivity().startActivity(intent);
    }

    @Override
    public void navigateToSearchWithRecommendationCriteria(Profile profile) {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_IS_RECOMMENDED, true);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_CURRENT_PROFILE, profile);
        getActivity().startActivity(intent);
    }

    @Override
    public void hideDownloadContent() {
        mRelativeLayoutDownloadContent.setVisibility(View.GONE);
    }

    @Override
    public void showMyLessonsVisibility() {
        mTextViewMyLessons.setVisibility(View.VISIBLE);
        mRecyclerViewMyLessons.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideMyLessons() {
        mTextViewMyLessons.setVisibility(View.GONE);
        mRecyclerViewMyLessons.setVisibility(View.GONE);
    }

    @Override
    public void hideMyTextBooks() {
        mTextViewMyTextBook.setVisibility(View.GONE);
        mRecyclerViewMyTextBook.setVisibility(View.GONE);
    }

    @Override
    public void showDownloadContent() {
        mRelativeLayoutDownloadContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void openNavigationDrawer() {
        ((LandingActivity) this.getActivity()).openDrawer();
    }

    @Override
    public void setSubject(String subjectName) {
        mTextViewSubjectName.setText(subjectName);
    }

    @Override
    public void changeToAddChildActivity() {
        showAddChildActivity();
    }

    @Override
    public void showProgressDialog() {
        mLinearLayoutInlineProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressDialog() {
        mLinearLayoutInlineProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void removeAllSections() {
        mSectionLayout.removeAllViews();
    }

    @Override
    public void showSwitchProfileDialog(final List<Profile> profileList, final Profile currentProfile) {
        final Profile[] selectedProfile = {currentProfile};

        if (getActivity() != null) {
            mDialog = new Dialog(getActivity(), R.style.FullScreenDialog);

            mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                }
            });

            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            mDialog.setContentView(R.layout.dialog_switch_child);

            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
            int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);
            mDialog.getWindow().setLayout(width, height);

            adjustDialogUIAccordingLanguage(mDialog);

            int selectedProfilePos = profileList.size() - 1;

            if (currentProfile != null) {
                for (int i = 0; i < profileList.size(); i++) {
                    if (profileList.get(i).getAvatar().equalsIgnoreCase(currentProfile.getAvatar())) {
                        selectedProfilePos = i;
                    }
                }
            }

            final FancyCoverFlow mFancyCoverFlow = (FancyCoverFlow) mDialog.findViewById(R.id.fancyCoverFlow);
            final ImageView profileIv = (ImageView) mDialog.findViewById(R.id.img_player);
            final EkStepCustomTextView nameTv = (EkStepCustomTextView) mDialog.findViewById(R.id.kid_name);
            final LinearLayout layout_Class = (LinearLayout) mDialog.findViewById(R.id.layout_class);
            final RelativeLayout layout_Age = (RelativeLayout) mDialog.findViewById(R.id.layout_age);
            final TextView txt_Class = (TextView) mDialog.findViewById(R.id.txt_class);
            final TextView txt_Age = (TextView) mDialog.findViewById(R.id.txt_age);

            mDialog.findViewById(R.id.add_child).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    mHomePresenter.switchToChildActivity();
                }
            });

            mDialog.findViewById(R.id.img_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });

            profileIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!selectedProfile[0].getHandle().equalsIgnoreCase(getResources().getString(R.string.label_all_anonymous))) {
                        mDialog.dismiss();

                        Intent intent = new Intent(getActivity(), ProgressReportsActivity.class);
                        intent.putExtra(Constant.PROFILE_SUMMARY, selectedProfile[0]);
                        getActivity().startActivity(intent);
                    }
                }
            });

            mFancyCoverFlow.setUnselectedAlpha(1.0f);
            mFancyCoverFlow.setUnselectedSaturation(0.0f);
            mFancyCoverFlow.setUnselectedScale(0.5f);
            mFancyCoverFlow.setMaxRotation(0);
            mFancyCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);

            fetchCurrentProfileDetails(profileList, currentProfile, profileIv, nameTv, layout_Class, layout_Age, txt_Class, txt_Age);

            final PlayerListAdapter mPlayerListAdapter = new PlayerListAdapter(getActivity(), profileList, selectedProfilePos);
            mFancyCoverFlow.setAdapter(mPlayerListAdapter);
            mFancyCoverFlow.setSelection(selectedProfilePos, true);
            mFancyCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    fetchSelectedProfileDetails(position, profileList, selectedProfile, profileIv, nameTv, layout_Class, txt_Class, layout_Age, txt_Age, mPlayerListAdapter);
                }
            });

            mDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            mDialog.setCancelable(true);
            DeviceUtility.displayFullScreenDialog(mDialog, getActivity());
        }
    }

    private void fetchSelectedProfileDetails(int position, List<Profile> profileList, Profile[] selectedProfile, ImageView profileIv, EkStepCustomTextView nameTv, LinearLayout layout_Class, TextView txt_Class, RelativeLayout layout_Age, TextView txt_Age, PlayerListAdapter mPlayerListAdapter) {
        Profile profile = profileList.get(position);

        selectedProfile[0] = profile;

        UserProfileUtil.setProfileAvatar(profileIv, profile.getAvatar(), profile.isGroupUser());

        nameTv.setText(profile.getHandle());

        layout_Class.setVisibility(View.VISIBLE);
        if (profile.getStandard() == -1) {
            if (profile.isGroupUser() || StringUtil.isNullOrEmpty(profile.getHandle())
                    || profile.getHandle().equalsIgnoreCase(getString(R.string.label_all_anonymous))) {
                layout_Class.setVisibility(View.INVISIBLE);
            } else {
                txt_Class.setText(getString(R.string.label_addchild_others));
            }
        } else if (profile.getStandard() == 0) {
            txt_Class.setText(getString(R.string.label_addchild_kg));
        } else {
            txt_Class.setText(String.valueOf(profile.getStandard()));
        }

        layout_Age.setVisibility(View.VISIBLE);
        if (profile.getAge() == -1 && profile.getAge() != 0) {
            if (profile.isGroupUser() || StringUtil.isNullOrEmpty(profile.getHandle())
                    || profile.getHandle().equalsIgnoreCase(getString(R.string.label_all_anonymous))) {
                layout_Age.setVisibility(View.INVISIBLE);
            } else {
                txt_Age.setText(getString(R.string.label_children_other));
            }
        } else {
            txt_Age.setText(String.valueOf(profile.getAge()));
        }

        mPlayerListAdapter.setSelectedPosition(position);
        mPlayerListAdapter.notifyDataSetChanged();

        //set this profile details in Presenter and save it
        mHomePresenter.setSwitchedKidDetails(profile, profileList.size() - 1, false);
    }

    private void fetchCurrentProfileDetails(List<Profile> profileList, Profile currentProfile, ImageView profileIv, EkStepCustomTextView nameTv, LinearLayout layout_Class, RelativeLayout layout_Age, TextView txt_Class, TextView txt_Age) {
        String kidName = null;
        if (currentProfile != null) {

            if (TextUtils.isEmpty(currentProfile.getHandle())) {
                kidName = mActivity.getString(R.string.label_all_anonymous);
            } else {
                kidName = currentProfile.getHandle();
            }

            UserProfileUtil.setProfileAvatar(profileIv, currentProfile.getAvatar(), currentProfile.isGroupUser());

            layout_Class.setVisibility(View.VISIBLE);
            if (currentProfile.getStandard() == -1) {
                if (currentProfile.isGroupUser() || StringUtil.isNullOrEmpty(currentProfile.getHandle())
                        || currentProfile.getHandle().equalsIgnoreCase(getString(R.string.label_all_anonymous))) {
                    layout_Class.setVisibility(View.INVISIBLE);
                } else {
                    txt_Class.setText(getString(R.string.label_addchild_others));
                }
            } else if (currentProfile.getStandard() == 0) {
                txt_Class.setText(getString(R.string.label_addchild_kg));
            } else {
                txt_Class.setText(String.valueOf(currentProfile.getStandard()));
            }

            layout_Age.setVisibility(View.VISIBLE);
            if (currentProfile.getAge() == -1 && currentProfile.getAge() != 0) {
                if (currentProfile.isGroupUser() || StringUtil.isNullOrEmpty(currentProfile.getHandle())
                        || currentProfile.getHandle().equalsIgnoreCase(getString(R.string.label_all_anonymous))) {
                    layout_Age.setVisibility(View.INVISIBLE);
                } else {
                    txt_Age.setText(getString(R.string.label_children_other));
                }
            } else {
                txt_Age.setText(String.valueOf(currentProfile.getAge()));
            }

        } else {
            if (profileList.size() > 0) {
                kidName = profileList.get(0).getHandle();

                UserProfileUtil.setProfileAvatar(profileIv, profileList.get(0).getAvatar(), false);
            }
        }

        nameTv.setText(kidName);
    }

    @Override
    public void showChildOrGroupProfileAvatar(int resId) {
        mAddChildOrAvatarImage.setImageResource(resId);
    }

    @Override
    public void showAddChildImage() {
        mAddChildOrAvatarImage.setImageResource(R.drawable.ic_home_add_child);
    }

    private void adjustDialogUIAccordingLanguage(Dialog dialog) {
        if (!PreferenceUtil.getLanguage().equalsIgnoreCase(FontConstants.LANG_ENGLISH)) {
            if (PreferenceUtil.getLanguage().equalsIgnoreCase(FontConstants.LANG_TELUGU)) {
                adjustDialogUIForTelugu(dialog);
            }
        }
    }

    private void adjustDialogUIForTelugu(Dialog dialog) {
        dialog.findViewById(R.id.kid_name).setPadding(DeviceUtility.dipToPx(getActivity(), 40), DeviceUtility.dipToPx(getActivity(), 8), 0, -(DeviceUtility.dipToPx(getActivity(), 0)));
    }

    /**
     * Move the user from current fragment to Add Child Activity
     */
    private void showAddChildActivity() {
        PreferenceUtil.setFromManageUser(false);
        Intent intent = new Intent(getActivity(), AddChildActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_footer) {
            AnimationUtils.showShakeAnimation(mRelativeLayoutChangeSubject);

        } else if (i == R.id.layout_tool_back) {
            KeyboardUtil.hideSoftKeyboard(getActivity(), mSearchEditText);
            setSearchBarVisibility(View.GONE);

        } else if (i == R.id.img_berger_menu) {
            mHomePresenter.openNavigationDrawer();
            KeyboardUtil.hideSoftKeyboard(getActivity(), mSearchEditText);
            setSearchBarVisibility(View.GONE);

        } else if (i == R.id.rl_change_subject) {
            showTransparentView();
            mHomePresenter.getSubjectsList();

        } else if (i == R.id.dummy_add_child_or_avatar_image) {//if user is going in on boarding of add child, then user has seen it
            mHomePresenter.checkIfPartnerHasSkippedChangeSubject(false);
            changeToAddChildActivity();

        } else if (i == R.id.dummy_rl_change_subject) {
            isChangeSubjectClicked = true;
            removeChangeSubjectBaloon();

            mHomePresenter.getSubjectsList();

        } else if (i == R.id.add_child_or_avatar_image) {// preventing double click and multiple dialog creation, using threshold of 1000 ms
            if ((SystemClock.elapsedRealtime() - mLastClickTime < 1000) || isSearchBarVisible) {
                return;
            }

            mLastClickTime = SystemClock.elapsedRealtime();

            mHomePresenter.addOrSwitchChild();

        } else if (i == R.id.iv_show_download_queue) {
            downloadQueueFragment = new DownloadQueueFragment();
            downloadQueueFragment.show(getFragmentManager(), downloadQueueFragment.getTag());
            downloadQueueFragment.setOnDismissListener(new DownloadQueueFragment.OnDismissListener() {
                @Override
                public void onDismiss(DownloadQueueFragment downloadQueueFragment, boolean isDownloadCancelled) {
                    if (isDownloadCancelled) {
                        mHomePresenter.handleDownloadingAnimation(null);
                    }
                }
            });

        } else if (i == R.id.search_content_btn) {
            setSearchBarVisibility(View.VISIBLE);

        } else if (i == R.id.btn_tool_search) {
            search(mSearchEditText);

        } else if (i == R.id.layout_skip) {
            mHomePresenter.skipCurrentOnBoarding();

        } else if (i == R.id.layout_more_lessons) {
            mHomePresenter.handleViewAllClick(Constant.CONTENT_TYPE_LESSON);

        } else if (i == R.id.layout_more_textbook) {
            mHomePresenter.handleViewAllClick(Constant.CONTENT_TYPE_TEXTBOOK);

        } else if (i == R.id.manage_memory) {
            ((LandingActivity) this.getActivity()).setFragment(new MyContentFragment(), false);

        } else {
        }
    }

    /**
     * This sets the search visibility layout accordingly when the search icon is clicked
     *
     * @param visibility
     */
    public void setSearchBarVisibility(int visibility) {
        if (View.VISIBLE == visibility) {
            isSearchBarVisible = true;
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, mHeader.getHeight());
            layoutParams.rightMargin = DeviceUtility.getNavigationBarHeight(getContext());
            mCardView.setLayoutParams(layoutParams);

            // Show the search bar
            AnimationUtils.showSearchBar(mSearchLayout);
            mSearchEditText.setFocusableInTouchMode(true);
            mSearchEditText.requestFocus();

            SearchHistoryAdapter searchAdapter = new SearchHistoryAdapter(getActivity(), R.layout.layout_autocomplete, ContentUtil.getSearchHistoryList(), mHomePresenter);
            mSearchEditText.setThreshold(0);
            mSearchEditText.setAdapter(searchAdapter);

            mSearchEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    KeyboardUtil.showSoftKeyboard(getActivity(), mSearchEditText);
                }
            });

            // Display the soft keyboard
            KeyboardUtil.showSoftKeyboard(getActivity(), mSearchEditText);
        } else {
            isSearchBarVisible = false;
            AnimationUtils.hideSearchBar(mSearchLayout, null);
            mSearchEditText.setText("");
        }

        mDisabledView.setVisibility(visibility);
    }

    @Override
    public void hideSoftKeyboard() {
        KeyboardUtil.hideSoftKeyboard(mActivity, mSearchEditText);
    }

    @Override
    public void navigateToMyLessonsActivity(String lessonType, Profile profile, boolean isNotAnonymousProfile) {
        Intent textbookIntent = new Intent(mActivity, MyLessonsActivity.class);
        textbookIntent.putExtra(Constant.BundleKey.BUNDLE_KEY_LESSON_TYPE, lessonType);
        textbookIntent.putExtra(Constant.BundleKey.BUNDLE_KEY_CURRENT_PROFILE, profile);
        textbookIntent.putExtra(Constant.BundleKey.BUNDLE_KEY_IS_NOT_ANONYMOUS, isNotAnonymousProfile);
        startActivity(textbookIntent);
    }

    @Override
    public void showLessonsViewAll() {
        mViewLessonsViewAll.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLessonsViewAll() {
        mViewLessonsViewAll.setVisibility(View.GONE);
    }

    @Override
    public void showBooksViewAll() {
        mViewBookssViewAll.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBooksViewAll() {
        mViewBookssViewAll.setVisibility(View.GONE);
    }

    @Override
    public void showDeviceMemoryLowView() {
        mTxtDeviceMemoryLow.setVisibility(View.VISIBLE);
    }

    @Override
    public void showAddMemoryCardDialog() {
        final EkStepGenericDialog ekStepGenericDialog = new EkStepGenericDialog.Builder(mActivity).
                setTitle(R.string.title_add_memory_card).
                setCustomView(R.layout.dialog_add_memory_card).
                setPositiveButtonText(R.string.label_dialog_ok).
                setCheckBox(true).
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
    public void onMenuItemClick(int position) {
        PopUpWindowUtil.dismissPopUpWindow();
        mHomePresenter.changeSubject(position);
    }

    @Override
    public void onBottomReached() {
        mScrollEndReachCount++;
        if (!(mScrollEndReachCount == 1)) {
            AnimationUtils.showShakeAnimation(mRelativeLayoutChangeSubject);
        }
    }

    @Override
    public void onScrollStopped() {
        mHomePresenter.checkViewVisibility(mViewList, mScroll);
    }

    @Override
    public void onKeyIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            KeyboardUtil.hideSoftKeyboard(mActivity, mSearchEditText);
            setSearchBarVisibility(View.GONE);
        }
    }
}
