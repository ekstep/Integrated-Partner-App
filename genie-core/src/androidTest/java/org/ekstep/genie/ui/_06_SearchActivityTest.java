package org.ekstep.genie.ui;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.format.DateUtils;

import org.ekstep.genie.GenieTestBase;
import org.ekstep.genie.R;
import org.ekstep.genie.custom.action.ViewAction;
import org.ekstep.genie.custom.matcher.Matcher;
import org.ekstep.genie.custom.matcher.RecyclerViewMatcher;
import org.ekstep.genie.ui.search.SearchActivity;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.GenieDBHelper;
import org.ekstep.genie.util.SampleData;
import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.pressBack;

/**
 * Created on 5/23/2016.
 *
 * @author swayangjit_gwl
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class _06_SearchActivityTest extends GenieTestBase {


    @Rule
    public ActivityTestRule<SearchActivity> mActivityRule = new ActivityTestRule<SearchActivity>(SearchActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            startMockServer();
            GenieDBHelper.clearTelemetryTable();
            Intent result = getIntentData();
            return result;
        }
    };

    public Intent getIntentData() {
        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
        Intent intent = new Intent();
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_SEARCH_QUERY, "digit");
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_IS_EXPLICIT, true);
        return intent;
    }

    @Override
    public void setup() throws IOException {
        super.setup();
    }

    @After
    public void tearDown() throws IOException {
        super.tearDown();
        shutDownMockServer();
    }

    @Test
    public void test1ShouldSearchIfSearchTextCameFromIntent() throws IOException {

        //Assert the searched text on header
        Matcher.checkWithText(R.id.txt_header, "digit");

        //Assert sort and filter button visibility
        Matcher.isViewEnabled(R.id.filter_btn);
        Matcher.isViewEnabled(R.id.sort_btn);
        RecyclerViewMatcher.assertRecyclerViewCount(R.id.recyclerview_content, 4);
    }

    @Test
    public void test2ShouldShowSearchEditTextonSearchButtonClick() throws IOException {

        //Assert the searched text on header
        Matcher.checkWithText(R.id.txt_header, "digit");

        //Click on Search buton
        ViewAction.clickonView(R.id.layout_search);

        Matcher.isVisible(R.id.edt_tool_search);
        Matcher.isVisible(R.id.rl_disabled_view);

        //Sort n Filter button should be disabled
        Matcher.isViewDisabled(R.id.sort_btn);
        Matcher.isViewDisabled(R.id.filter_btn);

        pressBack();
        closeSoftKeyboard();

        //Assert sort and filter button visibility
        Matcher.isViewEnabled(R.id.filter_btn);
        Matcher.isViewEnabled(R.id.sort_btn);

        Matcher.isNotVisible(R.id.edt_tool_search);
        Matcher.isNotVisible(R.id.rl_disabled_view);
    }

    @Test
    public void test3ShouldSearchByClickingonSearcButton() throws IOException {

        //Assert the searched text on header
        Matcher.checkWithText(R.id.txt_header, "digit");

        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
        //Click on Search buton
        ViewAction.clickonView(R.id.layout_search);

        ViewAction.typeText(R.id.edt_tool_search, "digit");
        ViewAction.clickonView(R.id.btn_tool_search);
        closeSoftKeyboard();
        closeSoftKeyboard();
        RecyclerViewMatcher.assertRecyclerViewCount(R.id.recyclerview_content, 4);
    }

    @Test
    public void test3ShouldShowSortDialognSearch() throws IOException {

        //Assert the searched text on header
        Matcher.checkWithText(R.id.txt_header, "digit");

        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
        //Click on Search buton
        ViewAction.clickonView(R.id.sort_btn);

        Matcher.isCompoundButtonChecked(R.id.rg_relevant);

        ViewAction.clickonString(R.id.rg_newest, R.string.label_sort_newest);
        RecyclerViewMatcher.assertRecyclerViewCount(R.id.recyclerview_content, 4);

        ViewAction.clickonView(R.id.sort_btn);
        Matcher.isCompoundButtonChecked(R.id.rg_newest);

        ViewAction.clickonString(R.id.rg_relevant, R.string.label_sort_relevant);
        RecyclerViewMatcher.assertRecyclerViewCount(R.id.recyclerview_content, 4);

        ViewAction.clickonView(R.id.sort_btn);
        Matcher.isCompoundButtonChecked(R.id.rg_relevant);

        ViewAction.clickonString(R.id.rg_most_popular, R.string.label_sort_most_popular);
        RecyclerViewMatcher.assertRecyclerViewCount(R.id.recyclerview_content, 4);

        ViewAction.clickonView(R.id.sort_btn);
        Matcher.isCompoundButtonChecked(R.id.rg_most_popular);

        ViewAction.clickonString(R.id.rg_name, R.string.label_sort_name);
        RecyclerViewMatcher.assertRecyclerViewCount(R.id.recyclerview_content, 4);

    }

    @Test
    public void test4ShouldShowFilterActivity() throws IOException {

        //Assert the searched text on header
        Matcher.checkWithText(R.id.txt_header, "digit");

        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);

        //Click on Search buton
        ViewAction.clickonView(R.id.filter_btn);

        Matcher.check(R.id.txt_header, R.string.label_search_filter);

        ViewAction.clickonString(R.id.checkedText, "Kindergarten");

        ViewAction.clickOnRecylerViewItemWithAllof(R.id.recyclerview_filter, 1);

        ViewAction.clickonString(R.id.checkedText, "Numeracy");

        ViewAction.clickonView(R.id.apply_btn);

        Matcher.checkWithText(R.id.txt_header, "digit");

        RecyclerViewMatcher.assertRecyclerViewCount(R.id.recyclerview_content, 4);

        ViewAction.clickonView(R.id.filter_btn);

        Matcher.isCompoundButtonCheckedWithText(R.id.checkedText, "Kindergarten");

        ViewAction.clickOnRecylerViewItemWithAllof(R.id.recyclerview_filter, 1);

        Matcher.isCompoundButtonCheckedWithText(R.id.checkedText, "Numeracy");

    }

    @Test
    public void test5ShouldSelectAllFilters() throws IOException {

        //Assert the searched text on header
        Matcher.checkWithText(R.id.txt_header, "digit");

        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);

        //Click on Search buton
        ViewAction.clickonView(R.id.filter_btn);

        Matcher.check(R.id.txt_header, R.string.label_search_filter);

        ViewAction.clickonString(R.id.checkedText, "All");
        Matcher.isCompoundButtonCheckedWithText(R.id.checkedText, "All");
        Matcher.isCompoundButtonCheckedWithText(R.id.checkedText, "Kindergarten");
        Matcher.isCompoundButtonCheckedWithText(R.id.checkedText, "Grade 1");
        Matcher.isCompoundButtonCheckedWithText(R.id.checkedText, "Grade 2");
        Matcher.isCompoundButtonCheckedWithText(R.id.checkedText, "Grade 3");
        Matcher.isCompoundButtonCheckedWithText(R.id.checkedText, "Grade 4");
        Matcher.isCompoundButtonCheckedWithText(R.id.checkedText, "Grade 5");
        Matcher.isCompoundButtonCheckedWithText(R.id.checkedText, "Other");

        ViewAction.clickonView(R.id.apply_btn);

        ViewAction.clickonView(R.id.filter_btn);

        Matcher.isCompoundButtonCheckedWithText(R.id.checkedText, "All");
        Matcher.isCompoundButtonCheckedWithText(R.id.checkedText, "Kindergarten");
        Matcher.isCompoundButtonCheckedWithText(R.id.checkedText, "Grade 1");
        Matcher.isCompoundButtonCheckedWithText(R.id.checkedText, "Grade 2");
        Matcher.isCompoundButtonCheckedWithText(R.id.checkedText, "Grade 3");
        Matcher.isCompoundButtonCheckedWithText(R.id.checkedText, "Grade 4");
        Matcher.isCompoundButtonCheckedWithText(R.id.checkedText, "Grade 5");
        Matcher.isCompoundButtonCheckedWithText(R.id.checkedText, "Other");


    }


    @Test
    public void test6ShouldClearAllFilters() throws IOException {

        //Assert the searched text on header
        Matcher.checkWithText(R.id.txt_header, "digit");

        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);

        //Click on Search buton
        ViewAction.clickonView(R.id.filter_btn);

        Matcher.check(R.id.txt_header, R.string.label_search_filter);

        ViewAction.clickonString(R.id.checkedText, "Kindergarten");

        ViewAction.clickOnRecylerViewItemWithAllof(R.id.recyclerview_filter, 1);

        ViewAction.clickonString(R.id.checkedText, "Numeracy");

        ViewAction.clickonView(R.id.apply_btn);

        Matcher.checkWithText(R.id.txt_header, "digit");

        RecyclerViewMatcher.assertRecyclerViewCount(R.id.recyclerview_content, 4);

        ViewAction.clickonView(R.id.filter_btn);

        Matcher.isCompoundButtonCheckedWithText(R.id.checkedText, "Kindergarten");

        ViewAction.clickOnRecylerViewItemWithAllof(R.id.recyclerview_filter, 1);

        Matcher.isCompoundButtonCheckedWithText(R.id.checkedText, "Numeracy");

        ViewAction.clickonView(R.id.clear_btn);

        Matcher.isCompoundButtonNotCheckedWithText(R.id.checkedText, "Numeracy");

        ViewAction.clickOnRecylerViewItemWithAllof(R.id.recyclerview_filter, 0);

        Matcher.isCompoundButtonNotCheckedWithText(R.id.checkedText, "Kindergarten");

        ViewAction.clickonView(R.id.apply_btn);

        ViewAction.clickonView(R.id.filter_btn);

        Matcher.isCompoundButtonNotCheckedWithText(R.id.checkedText, "Kindergarten");

        ViewAction.clickOnRecylerViewItemWithAllof(R.id.recyclerview_filter, 1);

        Matcher.isCompoundButtonNotCheckedWithText(R.id.checkedText, "Numeracy");

    }

    @Test
    public void test7ShouldNavigatetoSerachActivityWhenBackbuttonisClicked() throws IOException {

        //Assert the searched text on header
        Matcher.checkWithText(R.id.txt_header, "digit");

        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);

        ViewAction.clickonView(R.id.filter_btn);

        ViewAction.clickonView(R.id.back_btn);

        //Assert the searched text on header
        Matcher.checkWithText(R.id.txt_header, "digit");

    }

    @Test
    public void test8ShouldNavigatetoSerachActivityWhenDeviceBackbuttonisClicked() throws IOException {

        //Assert the searched text on header
        Matcher.checkWithText(R.id.txt_header, "digit");

        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);

        ViewAction.clickonView(R.id.filter_btn);

        pressBack();

        //Assert the searched text on header
        Matcher.checkWithText(R.id.txt_header, "digit");
    }

    @Test
    public void test9ShouldShowTickbuttonIfContentisDownloaded() throws IOException {
        //Assert the searched text on header
        Matcher.checkWithText(R.id.txt_header, "digit");

        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);

        ViewAction.clickOnRecylerViewItem(R.id.recyclerview_content, 0);

        mMockServer.mockHttpResponse(SampleData.responseForContentDownload(), 200);
        mMockServer.mockHttpResponse(SampleData.responseForContentDownload(), 200);

        ViewAction.clickonView(R.id.tv_download);

        waitFor(DateUtils.SECOND_IN_MILLIS * 15);

        Matcher.check(R.id.txt_play, R.string.label_contentdetail_play);

        clearResource();

        ViewAction.clickonView(R.id.back_btn);

        Matcher.checkInRecyclerViewItemVisibility(R.id.rv_my_lessons_downloaded, 0, R.id.iv_normal_tick_mark);

        deleteContent("do_30013062");
        deleteContent("do_30013147");
        deleteContent("do_30013083");
        deleteContent("do_30013148");

    }


//    @Test
//    public void test1FilterSearchAction() throws IOException {
//        //click on Filter
//        ViewAction.clickonString(R.id.filter_btn, R.string.label_search_filter);
//
//        ViewAction.clickOnRecylerViewItemWithAllof(R.id.recyclerview_filter, 1);
//
//        //click on Filter
//        ViewAction.clickonString(R.id.checkedText, numeracy);
//
//        //click on apply_btn
//        ViewAction.clickonView(R.id.back_btn);
//
//        //click on Filter
//        ViewAction.clickonString(R.id.filter_btn, R.string.label_search_filter);
//
//        pressBack();
//        //click on Filter
//        ViewAction.clickonString(R.id.filter_btn, R.string.label_search_filter);
//
//        // click on  All list
//        ViewAction.clickOnRecylerViewItemWithAllof(R.id.recyclerview_filter, 0);
//        //click on text
//        ViewAction.clickonString(R.id.checkedText, R.string.title_filter_all);
//
//        ViewAction.clickOnRecylerViewItemWithAllof(R.id.recyclerview_filter, 1);
//        //click on text
//        ViewAction.clickonString(R.id.checkedText, R.string.title_filter_all);
//        ViewAction.clickOnRecylerViewItemWithAllof(R.id.recyclerview_filter, 3);
//        //click on text
//        ViewAction.clickonString(R.id.checkedText, R.string.title_filter_all);
//        ViewAction.clickOnRecylerViewItemWithAllof(R.id.recyclerview_filter, 4);
//        //click on text
//        ViewAction.clickonString(R.id.checkedText, R.string.title_filter_all);
//
//        ViewAction.clickOnRecylerViewItemWithAllof(R.id.recyclerview_filter, 2);
//        //click on text
//        ViewAction.clickonString(R.id.checkedText,"Hindi");
//        //click on text
//        ViewAction.clickonString(R.id.checkedText, R.string.title_filter_all);
//
//        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//
//        //click on apply_btn
//        ViewAction.clickonView(R.id.apply_btn, R.id.layout_toolbar);
//
//        //click on sort_btn
//        ViewAction.clickonView(R.id.sort_btn);
//
//        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//
//        //click on name
//        ViewAction.clickonString(R.id.rg_name, R.string.label_sort_name);
//
//        //click on sort_btn
//        ViewAction.clickonView(R.id.sort_btn);
//
//        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//
//        //click on most_popular
//        ViewAction.clickonString(R.id.rg_most_popular, R.string.label_sort_most_popular);
//
//        //click on sort_btn
//        ViewAction.clickonView(R.id.sort_btn);
//
//        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//
//        //click on newest
//        ViewAction.clickonString(R.id.rg_newest, R.string.label_sort_newest);
//
//        mMockServer.shutDown();
//
//        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
//                addGEInteractEvent(TelemetryStageId.CONTENT_SEARCH, EntityType.SEARCH_PHRASE, InteractionType.SHOW.getValue(), null, false).
//                addGEInteractEvent(TelemetryStageId.FILTER, null, InteractionType.SHOW.getValue(), null, false).
//                addGEInteractEvent(TelemetryStageId.FILTER, null, InteractionType.SHOW.getValue(), null, false).
//                addGEInteractEvent(TelemetryStageId.FILTER, null, InteractionType.SHOW.getValue(), null, false).
//                addGEInteractEvent(TelemetryStageId.CONTENT_LIST, EntityType.SEARCH_PHRASE, InteractionType.SHOW.getValue(), null, false).
//                addGEInteractEvent(TelemetryStageId.CONTENT_LIST, EntityType.SEARCH_PHRASE, InteractionType.SHOW.getValue(), null, false).
//                addGEInteractEvent(TelemetryStageId.CONTENT_LIST, EntityType.SEARCH_PHRASE, InteractionType.SHOW.getValue(), null, false).
//                addGEInteractEvent(TelemetryStageId.CONTENT_LIST, EntityType.SEARCH_PHRASE, InteractionType.SHOW.getValue(), null, false).
//                addGEServiceApiCall(13).
//                build();
//
//        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
//    }

//    @Test
//    public void test2DownloadPlayContent() throws IOException {
//
//
//        // Click on SearchedResult 0th Item
//        ViewAction.clickOnRecylerViewItem(R.id.recyclerview_content, 0);
//
//        //asserting for contentDetail header
//        Matcher.check(R.id.txt_header, R.string.lession_details);
//
//        //Download the content
//        downloadOrupdateContent();
//
//        //asserting for download, play and delete button
//        Matcher.isVisible(R.id.txt_play);
//
//
//        Matcher.isVisible(R.id.txt_delete);
//
//        //PlayerUtil the content
//        playContent();
//
//        //Press Back
//        pressBack();
//
//        //asserting for contentDetail header
//        Matcher.check(R.id.txt_header, R.string.lession_details);
//
//        //click on Delete
//        ViewAction.clickonString(R.id.txt_delete, R.string.delete);
//
//        //asserting for contentDetail header
//        Matcher.isVisibleText(R.string.delete_confirmation);
//
//        //click on Delete No btn
//        ViewAction.clickonString(R.id.delete_no, R.string.no);
//
//        //asserting for download, play and delete button
//        Matcher.isVisible(R.id.txt_play);
//        Matcher.isVisible(R.id.txt_delete);
//
//        //click on Delete
//        ViewAction.clickonString(R.id.txt_delete, R.string.delete);
//
//        //click on Delete yes
//        ViewAction.clickonString(R.id.delete_yes, R.string.yes);
//
//        //assert for delete success toast msg
//        Matcher.isToastMessageDisplayed(R.string.content_deletion_sucessfull);
//
//        //asserting for download, play and delete button
//        Matcher.isVisible(R.id.download_and_update_btn);
//
//        //click on back_btn
//        ViewAction.clickonView(R.id.back_btn, R.id.toolbar_searched_result);
//
//        //click on Filter
//        ViewAction.clickonString(R.id.filter_btn, R.string.filter);
//
//        //click on clear_btn
//        ViewAction.clickonView(R.id.clear_btn, R.id.layout_toolbar);
//
//        mockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//        mockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//        mockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//        mockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//
//        //Click on Apply
//        ViewAction.clickonView(R.id.apply_btn, R.id.layout_toolbar);
//
//        //click on text
//        ViewAction.clickonView(R.id.back_btn);
//
//        mockServer.shutDown();
//
//        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
//                addGEInteractEvent(TelemetryStageId.CONTENT_SEARCH, EntityType.SEARCH_PHRASE, InteractionType.SHOW, null, false).
//                addGEInteractEvent(TelemetryStageId.CONTENT_LIST, TelemetryAction.CONTENT_CLICKED, InteractionType.TOUCH, null, false).
//                addGEInteractEvent(TelemetryStageId.CONTENT_DETAIL, EntityType.CONTENT, InteractionType.SHOW, null, false).
//                addGEInteractEvent(TelemetryStageId.CONTENT_DETAIL, TelemetryAction.CONTENT_DOWNLOAD_INITIATE, InteractionType.TOUCH, null, false).
//                addGEInteractEvent(TelemetryStageId.CONTENT_DETAIL, TelemetryAction.CONTENT_DOWNLOAD_SUCCESS, InteractionType.OTHER, null, false).
//                addGEInteractEvent(TelemetryStageId.IMPORT_CONTENT, TelemetryAction.CONTENT_IMPORT_INITIATED, InteractionType.TOUCH, null, false).
//                addGEInteractEvent(TelemetryStageId.IMPORT_CONTENT, TelemetryAction.CONTENT_IMPORT_SUCCESS, InteractionType.OTHER, null, false).
//                addGELaunchGameEvent("domain_4048", "INTENT", "api-ekstep.composite-search.search", null).
//                addGEGameEndEvent("domain_4048", 0).
//                addGEInteractEvent(TelemetryStageId.CONTENT_DETAIL, TelemetryAction.DELETE_CONTENT_INITIATED, InteractionType.TOUCH, null, false).
//                addGEInteractEvent(TelemetryStageId.FILTER, null, InteractionType.SHOW, null, false).
//                addGEInteractEvent(TelemetryStageId.CONTENT_LIST, EntityType.SEARCH_PHRASE, InteractionType.SHOW, null, false).
//                addGETransfer(1, CommonUtil.Constants.IMPORT_DATATYPE, CommonUtil.Constants.IMPORT_DIRECTION).
//                build();
//
//        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
//    }

//    @Test
//    public void test3ClickOnSearchContent() {
//        //click on title
//        ViewAction.clickonView(R.id.layout_searh_title);
//
//        //asserting for toolbar search layout
//        Matcher.isVisible(R.id.layout_toolbar_search);
//
//        ViewAction.clickonView(R.id.layout_tool_back);
//
//        //asserting for SearchResult activity header layout
//        Matcher.isVisible(R.id.layout_searh_title);
//
//        mockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//        mockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//        mockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//        mockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//
//        //click on title
//        ViewAction.clickonView(R.id.layout_searh_title);
//
//        //click on title
//        ViewAction.typeText(R.id.edt_tool_search, searchContent);
//
//        ViewAction.clickonView(R.id.btn_tool_search);
//
//        //Assert the Headings
//        Matcher.checkWithText(R.id.txt_header, searchContent);
//
//        closeSoftKeyboard();
//
//        //ViewAction.clickonView(R.id.layout_tool_back);
//
//        mockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//        mockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//        mockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//        mockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//
//        //Search content wrong
//        searchContent(wrongText);
//
//        //close soft keyboard back button
////        closeSoftKeyboard();
//
//        ViewAction.clickonView(R.id.layout_tool_back);
//
//        //asserting for no result
//        Matcher.check(R.id.txt_no_result, R.string.no_content_found);
//        //Matcher.isVisible(R.id.layout_no_content);
//
//        //Search content right
//        mockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//        mockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//        mockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//        mockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//
//        searchContent(searchContent);
//
//        ViewAction.clickonView(R.id.layout_tool_back);
//
//        //asserting for results visibility
//        Matcher.isVisible(R.id.recyclerview_content);
//
//        //click on Result
//        ViewAction.clickOnRecylerViewItem(R.id.recyclerview_content, 0);
//
//
//        closeSoftKeyboard();
//
//        //Assert the Headings
//        Matcher.check(R.id.txt_header, R.string.lession_details);
//
//        downloadOrupdateContent();
//
//        //click on share
//        ViewAction.clickonView(R.id.item_share);
//
//        //Scroll to Link sharing apps
//        onView(withId(R.id.resolver_recyclerview_link)).perform(scrollTo());
//
//        //Click on Gmail app
//        onView(withRecyclerView(R.id.resolver_recyclerview_link).atPosition(2)).perform(click());
//
//        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
//                addGEInteractEvent(TelemetryStageId.CONTENT_SEARCH, EntityType.SEARCH_PHRASE, InteractionType.SHOW, searchText, true).
//                addGEInteractEvent(TelemetryStageId.CONTENT_SEARCH, EntityType.SEARCH_PHRASE, InteractionType.SHOW, searchContent, true).
//                addGEInteractEvent(TelemetryStageId.CONTENT_LIST, TelemetryAction.CONTENT_CLICKED, InteractionType.TOUCH, null, true).
//                addGEInteractEvent(TelemetryStageId.CONTENT_DETAIL, EntityType.CONTENT, InteractionType.SHOW, null, false).
//                addGEInteractEvent(TelemetryStageId.CONTENT_DETAIL, TelemetryAction.CONTENT_DOWNLOAD_INITIATE, InteractionType.TOUCH, null, false).
//                addGEInteractEvent(TelemetryStageId.CONTENT_DETAIL, TelemetryAction.CONTENT_DOWNLOAD_SUCCESS, InteractionType.OTHER, null, false).
//                addGEInteractEvent(TelemetryStageId.IMPORT_CONTENT, TelemetryAction.CONTENT_IMPORT_INITIATED, InteractionType.TOUCH, null, false).
//                addGEInteractEvent(TelemetryStageId.IMPORT_CONTENT, TelemetryAction.CONTENT_IMPORT_SUCCESS, InteractionType.OTHER, null, false).
//                addGEInteractEvent(TelemetryStageId.CONTENT_DETAIL, TelemetryAction.SHARE_CONTENT_LINK, InteractionType.TOUCH, null, false).
//                addGETransfer(1, CommonUtil.Constants.IMPORT_DATATYPE, CommonUtil.Constants.IMPORT_DIRECTION).
//                build();
//
//        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
//
//        deleteContent("domain_4048");
//    }

//    @Test
//    public void test4ShouldGenerateGEINTERACTWithPresentOnDeviceTag() {
//        //click on title
//        ViewAction.clickonView(R.id.layout_searh_title);
//
//        //asserting for toolbar search layout
//        Matcher.isVisible(R.id.layout_toolbar_search);
//
//        ViewAction.clickonView(R.id.layout_tool_back);
//
//        //asserting for SearchResult activity header layout
//        Matcher.isVisible(R.id.layout_searh_title);
//
//        //Search content right
//        mockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//        mockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//        mockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//        mockServer.mockHttpResponse(SampleData.searchResponse(), 200);
//
//        searchContent(searchContent);
//
//        ViewAction.clickonView(R.id.layout_tool_back);
//
//        //asserting for results visibility
//        Matcher.isVisible(R.id.recyclerview_content);
//
//        //click on Result
//        ViewAction.clickOnRecylerViewItem(R.id.recyclerview_content, 0);
//
////         pressBack();
//
//        //Assert the Headings
//        Matcher.check(R.id.txt_header, R.string.lession_details);
//
//        downloadOrupdateContent();
//
//        Espresso.pressBack();
//
//        //click on Result
//        ViewAction.clickOnRecylerViewItem(R.id.recyclerview_content, 0);
//
//        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
//                addGEInteractEvent(TelemetryStageId.CONTENT_SEARCH, EntityType.SEARCH_PHRASE, InteractionType.SHOW, "elephant", true).
//                addGEInteractEvent(TelemetryStageId.CONTENT_LIST, TelemetryAction.CONTENT_CLICKED, InteractionType.TOUCH, null, true, false).
//                addGEInteractEvent(TelemetryStageId.CONTENT_DETAIL, EntityType.CONTENT, InteractionType.SHOW, null, false).
//                addGEInteractEvent(TelemetryStageId.CONTENT_DETAIL, TelemetryAction.CONTENT_DOWNLOAD_INITIATE, InteractionType.TOUCH, null, false).
//                addGEInteractEvent(TelemetryStageId.CONTENT_DETAIL, TelemetryAction.CONTENT_DOWNLOAD_SUCCESS, InteractionType.OTHER, null, false).
//                addGEInteractEvent(TelemetryStageId.IMPORT_CONTENT, TelemetryAction.CONTENT_IMPORT_INITIATED, InteractionType.TOUCH, null, false).
//                addGEInteractEvent(TelemetryStageId.IMPORT_CONTENT, TelemetryAction.CONTENT_IMPORT_SUCCESS, InteractionType.OTHER, null, false).
//                addGEInteractEvent(TelemetryStageId.CONTENT_LIST, TelemetryAction.CONTENT_CLICKED, InteractionType.TOUCH, null, true, true).
//                addGEInteractEvent(TelemetryStageId.CONTENT_DETAIL, EntityType.CONTENT, InteractionType.SHOW, null, false).
//                addGETransfer(1, CommonUtil.Constants.IMPORT_DATATYPE, CommonUtil.Constants.IMPORT_DIRECTION).
//                build();
//        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
//        deleteContent("domain_4048");
//
//    }

//    private void deleteContent(String identifier) {
//        new ContentService(CoreApplication.getInstance()).delete(identifier, IMPORT_OR_DELETE_STARTED_FROM_OUTSIDE_COLLECTION_OR_TEXTBOOK, new IResponseHandler() {
//            @Override
//            public void onSuccess(GenieResponse genieResponse) {
//
//            }
//
//            @Override
//            public void onFailure(GenieResponse genieResponse) {
//
//            }
//        });
//    }

//    private void searchContent(String searchBy) {
//        //click on title
//        ViewAction.clickonView(R.id.layout_searh_title);
//        //click on title
//        ViewAction.typeText(R.id.edt_tool_search, searchBy);
//        //wait for some time
//        idlingResource = startTiming(4000);
//
//        stopTiming(idlingResource);
//
//        closeSoftKeyboard();
//
//    }
//
//    private void downloadOrupdateContent() {
//        ViewAction.clickonString(R.id.download_and_update_btn, R.string.download);
//
//        //click on download
//        idlingResource = startTiming(10000);
//
//        stopTiming(idlingResource);
//
//        idlingResource = startTiming(16000);
//    }
//
//    private void playContent() {
//        //click on play
//        ViewAction.clickonView(R.id.txt_play);
//        stopTiming(idlingResource);
//    }
//
//    public IdlingResource startTiming(long time) {
//        IdlingResource idlingResource = new ElapsedTimeIdlingResource(time);
//        Espresso.registerIdlingResources(idlingResource);
//        return idlingResource;
//    }
//
//    public void stopTiming(IdlingResource idlingResource) {
//        Espresso.unregisterIdlingResources(idlingResource);
//    }

}
