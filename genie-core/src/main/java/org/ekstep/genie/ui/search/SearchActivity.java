package org.ekstep.genie.ui.search;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.activity.BaseActivity;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.customview.EkStepCustomAutoCompleteTextView;
import org.ekstep.genie.model.enums.SortType;
import org.ekstep.genie.model.enums.StageCode;
import org.ekstep.genie.ui.contentdetail.ContentDetailActivity;
import org.ekstep.genie.ui.filter.FilterActivity;
import org.ekstep.genie.util.AnimationUtils;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.DeviceUtility;
import org.ekstep.genie.util.KeyboardUtil;
import org.ekstep.genie.util.ThemeUtility;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.geniesdk.ContentUtil;
import org.ekstep.genieservices.commons.bean.ContentData;
import org.ekstep.genieservices.commons.bean.ContentImportResponse;
import org.ekstep.genieservices.commons.bean.ContentSearchCriteria;
import org.ekstep.genieservices.commons.bean.ContentSearchFilter;
import org.ekstep.genieservices.commons.bean.ContentSearchResult;
import org.ekstep.genieservices.commons.bean.ContentSortCriteria;
import org.ekstep.genieservices.commons.bean.Profile;
import org.ekstep.genieservices.commons.bean.enums.SortOrder;
import org.ekstep.genieservices.commons.utils.CollectionUtil;
import org.ekstep.genieservices.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Set;

import static org.ekstep.genie.ui.search.SearchContract.Presenter.ACTION_FILTER;
import static org.ekstep.genie.ui.search.SearchContract.Presenter.ACTION_NONE;

/**
 * Created on 7/9/17.
 * shriharsh
 */

public class SearchActivity extends BaseActivity implements SearchContract.View, View.OnClickListener, EkStepCustomAutoCompleteTextView.KeyImeChange, TextWatcher {

    private SearchPresenter mPresenter;
    private TextView mHeaderText;
    private View mSortBtn;
    private View mFilterBtn;
    private RelativeLayout mLayoutNoContent;
    private TextView mTxtNoContent1;
    private TextView mTxtNoContent2;
    private RelativeLayout mLayoutToolbarSearch;
    private CardView mCardSearchTitle;
    private RecyclerView mRecyclerViewContent;
    private RelativeLayout mRelativeLayoutDisabled;
    private EkStepCustomAutoCompleteTextView mEditSearch;
    private ProgressBar mProgressBar;
    private SearchResultAdapter mSearchResultAdapter;
    private ContentSearchCriteria mContentSearchCriteria;
    private boolean mIsSearchedExplicitly = false;
    private ContentSearchResult mContentSearchResult = null;
    private List<ContentData> mContentDataList;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onContentImport(ContentImportResponse importResponse) throws InterruptedException {
        mPresenter.manageImportResponse(importResponse);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.isSubscriberRegistered(this)) {
            EventBus.registerSubscriber(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.unregisterSubscriber(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_serach);

        final View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                DeviceUtility.displayFullScreen(SearchActivity.this);
            }
        });

        decorView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                DeviceUtility.displayFullScreen(SearchActivity.this);
            }
        });

        mPresenter = (SearchPresenter) presenter;

        initViews();

        extractBundle(getIntent());
    }

    private void extractBundle(Intent intent) {
        Bundle bundle = intent.getExtras();

        String searchedQuery = bundle.getString(Constant.BundleKey.BUNDLE_KEY_SEARCH_QUERY);
        Profile currentProfile = null;
        if (bundle.containsKey(Constant.BundleKey.BUNDLE_KEY_CURRENT_PROFILE)) {
            currentProfile = (Profile) bundle.getSerializable(Constant.BundleKey.BUNDLE_KEY_CURRENT_PROFILE);
        }

        disableSortAndFilterButton();

        if (bundle.containsKey(Constant.BundleKey.BUNDLE_KEY_IS_RECOMMENDED)) {
            //Shows Recommended content
            showHeaderWithTitle(getString(R.string.recommended_content));
            mPresenter.fetchRecommendedContent(searchedQuery);
        } else if (bundle.containsKey(Constant.BundleKey.BUNDLE_KEY_IS_RELATED)) {
            //Shows Related content.The deeplink info will come from content player.
            String contentId = bundle.getString(Constant.BundleKey.BUNDLE_KEY_DEEP_LINK_IDENTIFIER);
            String header = bundle.getString(Constant.BundleKey.BUNDLE_KEY_DEEP_LINK_SEARCH_QUERY);
            showHeaderWithTitle(header);
            mPresenter.fetchRelatedContent(contentId);
        } else if (bundle.containsKey(Constant.BundleKey.BUNDLE_KEY_CONTENT_SEARCH_CRITERIA)) {
            //Content searchCriteria will come from the BFF
            mContentSearchCriteria = (ContentSearchCriteria) bundle.getSerializable(Constant.BundleKey.BUNDLE_KEY_CONTENT_SEARCH_CRITERIA);
            String header = bundle.getString(Constant.BundleKey.BUNDLE_KEY_DEEP_LINK_SEARCH_QUERY);
            showHeaderWithTitle(header);
            mPresenter.searchContent(mContentSearchCriteria, ACTION_NONE, false);
        } else if (bundle.containsKey(Constant.BundleKey.BUNDLE_KEY_IS_EXPLICIT)) {
            //Search from home
            mIsSearchedExplicitly = true;
            searchedQuery = bundle.getString(Constant.BundleKey.BUNDLE_KEY_SEARCH_QUERY);
            showHeaderWithTitle(searchedQuery);
            ContentSearchCriteria.SearchBuilder searchBuilder = new ContentSearchCriteria.SearchBuilder();
            searchBuilder.query(searchedQuery);
            mPresenter.applyFilters(searchBuilder, currentProfile);
            searchBuilder.softFilters();
            mContentSearchCriteria = searchBuilder.build();
            mPresenter.searchContent(mContentSearchCriteria, ACTION_NONE, mIsSearchedExplicitly);
        }

    }

    private void showHeaderWithTitle(String text) {
        mHeaderText.setText(text);
    }

    private void initViews() {
        mHeaderText = (TextView) findViewById(R.id.txt_header);
        mSortBtn = findViewById(R.id.sort_btn);
        mFilterBtn = findViewById(R.id.filter_btn);
        mLayoutNoContent = (RelativeLayout) findViewById(R.id.layout_no_content);
        mTxtNoContent1 = (TextView) findViewById(R.id.txt_no_result);
        mTxtNoContent2 = (TextView) findViewById(R.id.txt_no_result1);
        mLayoutToolbarSearch = (RelativeLayout) findViewById(R.id.layout_toolbar_search);
        mCardSearchTitle = (CardView) findViewById(R.id.layout_searh_title);

        mRecyclerViewContent = (RecyclerView) findViewById(R.id.recyclerview_content);
        mRecyclerViewContent.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerViewContent.setHasFixedSize(true);

        mRelativeLayoutDisabled = (RelativeLayout) findViewById(R.id.rl_disabled_view);
        mRelativeLayoutDisabled.setOnClickListener(this);

        mEditSearch = (EkStepCustomAutoCompleteTextView) findViewById(R.id.edt_tool_search);
        mEditSearch.setKeyImeChangeListener(this);
        mEditSearch.addTextChangedListener(this);

        mEditSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    handleSearchClick();
                    return true;
                }
                return false;
            }
        });

        mSortBtn.setOnClickListener(this);
        mFilterBtn.setOnClickListener(this);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        findViewById(R.id.layout_searh_title).setOnClickListener(this);
        findViewById(R.id.layout_tool_back).setOnClickListener(this);
        findViewById(R.id.back_btn).setOnClickListener(this);
        findViewById(R.id.btn_tool_search).setOnClickListener(this);
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new SearchPresenter();
            }
        };
    }

    @Override
    protected String getTAG() {
        return getLocalClassName();
    }

    @Override
    protected BaseView getBaseView() {
        return this;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.back_btn) {
            finish();

        } else if (i == R.id.layout_tool_back) {
            handleToolbarBackClick();

        } else if (i == R.id.layout_searh_title) {
            handleSearchTitleClick();

        } else if (i == R.id.sort_btn) {
            showSortDialog(mIsSearchedExplicitly);

        } else if (i == R.id.filter_btn) {
            handleFilterClick();

        } else if (i == R.id.btn_tool_search) {
            handleSearchClick();

        }
    }

    @Override
    public void onKeyIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mLayoutNoContent.getVisibility() == View.VISIBLE) {
                disableSortAndFilterButton();
            } else {
                enableSortAndFilterButton();
            }

            hideOverlayLayer();
            hideSoftKeyBoard();
            manageSearchBarVisibility();
        }
    }

    private void disableSortAndFilterButton() {
        mSortBtn.setEnabled(false);
        mFilterBtn.setEnabled(false);
        mSortBtn.setBackground(getResources().getDrawable(R.drawable.selector_disabled_grey));
        mFilterBtn.setBackground(getResources().getDrawable(R.drawable.selector_disabled_grey));
    }

    private void enableSortAndFilterButton() {
        mSortBtn.setEnabled(true);
        mFilterBtn.setEnabled(true);
        mSortBtn.setBackground(ThemeUtility.getDrawable(this, new int[]{R.attr.searchActionButtonBackground}));
        mFilterBtn.setBackground(ThemeUtility.getDrawable(this, new int[]{R.attr.searchActionButtonBackground}));
    }

    private void hideOverlayLayer() {
        mRelativeLayoutDisabled.setVisibility(View.GONE);
    }

    public void hideSoftKeyBoard() {
        KeyboardUtil.hideSoftKeyboard(this, mEditSearch);
    }

    private void manageSearchBarVisibility() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            showTitleHideSearchBarPostLollipop();
        } else {
            showTitleHideSearchBarPreLollipop();
        }
    }

    private void showTitleHideSearchBarPostLollipop() {
        AnimationUtils.hideSearchBar(mLayoutToolbarSearch, mCardSearchTitle);
    }

    private void showTitleHideSearchBarPreLollipop() {
        mCardSearchTitle.setVisibility(View.VISIBLE);
        mLayoutToolbarSearch.setVisibility(View.GONE);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (getSearchedQuery().length() == 0) {
            showSearchHistory(ContentUtil.getSearchHistoryList());
        }
    }

    private String getSearchedQuery() {
        return mEditSearch.getText().toString();
    }

    private void showSearchHistory(List<String> searchHistoryList) {
        SearchHistoryAdapter searchAdapter = new SearchHistoryAdapter(this, R.layout.layout_autocomplete, searchHistoryList, mPresenter);
        mEditSearch.setThreshold(0);
        mEditSearch.setAdapter(searchAdapter);
        mEditSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showSoftKeyBoard();
            }
        });
    }

    private void showSoftKeyBoard() {
        KeyboardUtil.showSoftKeyboard(this, mEditSearch);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void processSearchResult(List<ContentData> contentDataList, List<ContentSearchFilter> appliedFilters) {
        hideProgressBar();
        if (contentDataList.size() > 0) {
            if (CollectionUtil.isNullOrEmpty(appliedFilters)) {
                disableSortnFilterButton();
            } else {
                enableSortnFilterButton();
            }
            hideEmptyContentLayout();
            showSearchedContentList(contentDataList);

        } else {
            hideContentList();
            showEmptyContentLayout();
            disableSortnFilterButton();
        }
    }

    public void processSearchFailure(String errorCode) {
        hideProgressBar();
        showEmptyContentLayout();
        hideContentList();
        hideEmptyContentText2();
        showEmptyContentText1(Util.getGenieSpecificMessage(errorCode, StageCode.SEARCH));
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void disableSortnFilterButton() {
        mSortBtn.setEnabled(false);
        mFilterBtn.setEnabled(false);
        mSortBtn.setBackground(getResources().getDrawable(R.drawable.selector_disabled_grey));
        mFilterBtn.setBackground(getResources().getDrawable(R.drawable.selector_disabled_grey));
    }

    private void enableSortnFilterButton() {
        mSortBtn.setEnabled(true);
        mFilterBtn.setEnabled(true);
        mSortBtn.setBackground(ThemeUtility.getDrawable(this, new int[]{R.attr.searchActionButtonBackground}));
        mFilterBtn.setBackground(ThemeUtility.getDrawable(this, new int[]{R.attr.searchActionButtonBackground}));
    }

    private void hideEmptyContentLayout() {
        mLayoutNoContent.setVisibility(View.GONE);
    }

    public void showSearchedContentList(List<ContentData> contentDataList) {
        mRecyclerViewContent.setVisibility(View.VISIBLE);
        mSearchResultAdapter = new SearchResultAdapter(this, this);
        mSearchResultAdapter.setData(contentDataList);
        mRecyclerViewContent.setAdapter(mSearchResultAdapter);
    }

    private void hideContentList() {
        mRecyclerViewContent.setVisibility(View.GONE);
    }

    private void showEmptyContentLayout() {
        mLayoutNoContent.setVisibility(View.VISIBLE);
    }

    private void hideEmptyContentText2() {
        mTxtNoContent2.setVisibility(View.GONE);
    }

    private void showEmptyContentText1(String text) {
        mTxtNoContent1.setVisibility(View.VISIBLE);
        mTxtNoContent1.setText(text);
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void navigateToContentDetailsActivity(ContentData contentData) {
        Intent intent = new Intent(this, ContentDetailActivity.class);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_CONTENT_DATA, contentData);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_IS_FROM_SEARCH, true);
        startActivityForResult(intent, REQUEST_GET_REFRESH_INTENT);
    }

    @Override
    public void showSearchingContent() {
        hideEmptyContentLayout();
        showProgressBar();
        hideContentList();
    }

    private void handleToolbarBackClick() {
        if (getOverlayLayerVisibility() == View.VISIBLE) {
            if (mContentSearchCriteria != null && CollectionUtil.isNullOrEmpty(mContentSearchCriteria.getFacetFilters())) {
                disableSortnFilterButton();
            } else {
                enableSortnFilterButton();
            }
        }
        hideSoftKeyBoard();
        manageSearchBarVisibility();
        hideOverlayLayer();
    }

    private int getOverlayLayerVisibility() {
        return mRelativeLayoutDisabled.getVisibility();
    }

    private void handleSearchTitleClick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            hideTitleShowSearchBarPostLollipop();
        } else {
            hideTitleShowSearchBarPreLollipop();
        }
        setFocusSearchBar();
        showSoftKeyBoard();
        showOverlayLayer();
        disableSortnFilterButton();
    }

    private void hideTitleShowSearchBarPostLollipop() {
        AnimationUtils.hideSearchBar(mCardSearchTitle, mLayoutToolbarSearch);
    }

    private void hideTitleShowSearchBarPreLollipop() {
        mCardSearchTitle.setVisibility(View.GONE);
        mLayoutToolbarSearch.setVisibility(View.VISIBLE);
    }

    private void setFocusSearchBar() {
        mEditSearch.setFocusableInTouchMode(true);
        mEditSearch.requestFocus();
        mEditSearch.setSelection(mEditSearch.getText().length());
    }

    private void showOverlayLayer() {
        mRelativeLayoutDisabled.setVisibility(View.VISIBLE);
    }

    public void showSortDialog(final boolean isSearchedExplicitly) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_sort);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        final RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.rg_sort);
        RadioButton selectedRadioButton = (RadioButton) dialog.findViewById(getSelectedSort());
        selectedRadioButton.setChecked(true);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rg_relevant) {
                    dialog.dismiss();
                    mPresenter.applySortNSearch(SortType.RELEVANCE, SortOrder.ASC, isSearchedExplicitly);

                } else if (checkedId == R.id.rg_name) {
                    dialog.dismiss();
                    mPresenter.applySortNSearch(SortType.NAME, SortOrder.ASC, isSearchedExplicitly);

                } else if (checkedId == R.id.rg_most_popular) {
                    dialog.dismiss();
                    mPresenter.applySortNSearch(SortType.POPULARITY, SortOrder.DESC, isSearchedExplicitly);

                } else if (checkedId == R.id.rg_newest) {
                    dialog.dismiss();
                    mPresenter.applySortNSearch(SortType.NEWEST, SortOrder.DESC, isSearchedExplicitly);

                }

            }
        });

        DeviceUtility.displayFullScreenDialog(dialog, SearchActivity.this);
    }


    private int getSelectedSort() {
        int selectedRadioButtonId = R.id.rg_relevant;
        List<ContentSortCriteria> contentSearchCriteria = mContentSearchCriteria.getSortCriteria();
        if (contentSearchCriteria != null && !contentSearchCriteria.isEmpty()) {
            if (SortType.NAME.equals(mContentSearchCriteria.getSortCriteria().get(0).getSortAttribute())) {
                selectedRadioButtonId = R.id.rg_name;
            } else if (SortType.POPULARITY.equals(mContentSearchCriteria.getSortCriteria().get(0).getSortAttribute())) {
                selectedRadioButtonId = R.id.rg_most_popular;
            } else if (SortType.NEWEST.equals(mContentSearchCriteria.getSortCriteria().get(0).getSortAttribute())) {
                selectedRadioButtonId = R.id.rg_newest;
            }
        }
        return selectedRadioButtonId;
    }

    public void handleFilterClick() {
        if (mContentSearchResult != null) {
            navigateToFilterActivity(mContentSearchResult.getFilterCriteria());
        }
    }

    public void navigateToFilterActivity(ContentSearchCriteria searchCriteria) {
        Intent intent = new Intent(this, FilterActivity.class);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_CONTENT_FILTER_FACETS, searchCriteria);
        startActivityForResult(intent, REQUEST_GET_FILTER_CRITERIA);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

    public void handleSearchClick() {
        if (getSearchedQuery().length() > 2) {
            mIsSearchedExplicitly = true;
            hideSoftKeyBoard();
            manageSearchBarVisibility();
            hideEmptyContentLayout();
            showProgressBar();
            hideContentList();
            hideOverlayLayer();
            showHeaderWithTitle(getSearchedQuery());
            moveSearchCursortoEnd();

            ContentSearchCriteria.SearchBuilder searchBuilder = new ContentSearchCriteria.SearchBuilder();
            searchBuilder.query(getSearchedQuery())
                    .limit(200);
            mContentSearchCriteria = searchBuilder.build();
            mPresenter.searchContent(mContentSearchCriteria, ACTION_NONE, true);
        } else {
            showSearchFailureError(R.string.error_search);
        }
    }

    private void moveSearchCursortoEnd() {
        mEditSearch.setSelection(mEditSearch.getText().length());
    }

    private void showSearchFailureError(int messageId) {
        Util.showCustomToast(messageId);
    }

    @Override
    public void setContentSearchResult(ContentSearchResult contentSearchResult) {
        mContentSearchResult = contentSearchResult;
    }

    @Override
    public void setContentSearchCriteria(ContentSearchCriteria contentSearchCriteria) {
        mContentSearchCriteria = contentSearchCriteria;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GET_FILTER_CRITERIA) {
                mContentSearchCriteria = (ContentSearchCriteria) data.getSerializableExtra(Constant.BundleKey.BUNDLE_KEY_CONTENT_FILTER_FACETS);
                showProgressBar();
                hideContentList();
                disableSortnFilterButton();
                mPresenter.searchContent(mContentSearchCriteria, ACTION_FILTER, mIsSearchedExplicitly);
            } else if (requestCode == REQUEST_GET_REFRESH_INTENT) {
                refreshContentListAdapter(mContentDataList);
            }
        }
    }

    @Override
    public void setContentsList(List<ContentData> contentDataList) {
        mContentDataList = contentDataList;
    }

    @Override
    public void refreshContentListAdapter(List<ContentData> contentDataList) {
        mSearchResultAdapter.refreshAdapter(contentDataList);
    }

    @Override
    public Set<String> getIdentifierList() {
        return mSearchResultAdapter.getIdentifierList();
    }
}
