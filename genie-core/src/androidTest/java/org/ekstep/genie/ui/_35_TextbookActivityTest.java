package org.ekstep.genie.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.format.DateUtils;

import org.ekstep.genie.GenieTestBase;
import org.ekstep.genie.R;
import org.ekstep.genie.custom.action.ViewAction;
import org.ekstep.genie.custom.matcher.Matcher;
import org.ekstep.genie.model.ExpectedEventList;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.telemetry.enums.EntityType;
import org.ekstep.genie.ui.landing.LandingActivity;
import org.ekstep.genie.util.CommonUtil;
import org.ekstep.genie.util.DeviceAnimationTestRule;
import org.ekstep.genie.util.EcarCopyUtil;
import org.ekstep.genie.util.GenieDBHelper;
import org.ekstep.genie.util.SampleData;
import org.ekstep.genie.util.Session;
import org.ekstep.genie.util.TelemetryVerifier;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;


/**
 * This is integration tests for Textbooks
 * <p>
 * Created by shriharsh on 15/3/17.
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class _35_TextbookActivityTest extends GenieTestBase {
    // TODO change TB_LOCAL_CONTENT_COUNT value according to the text book content. ex: For new book local content 0 and for Marygold it is 3
    private static int TB_LOCAL_CONTENT_COUNT = 2;

    // TODO change TB_TOTAL_CONTENT_COUNT according to the text book content. ex: For new book total content is 14 and for Marygold it is 8
    private static int TB_TOTAL_CONTENT_COUNT = 4;

    private static int TB_OFFSET = 1;

    private static final String TEXTBOOK_ASSET_PATH = "Download/New_book_v1_0.ecar";
    private static final String TEXTBOOK_FILE_PATH = Environment.getExternalStorageDirectory().toString() + "/Download/New_book_v1_0.ecar";
    private static final String DESTINATION = Environment.getExternalStorageDirectory().toString() + "/Download";
    private static final String CONTENT_NAME = "New book";
    private static final String CONTENT_IDENTIFIER = "do_212277875261800448169";
    private static final String CHILD1 = "Test";
    //    private static final String CHILD2 = "";
    private static final String TABLE_OF_CONTENTS = "Table of contents";
    private Context mTargetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

    @ClassRule
    static public DeviceAnimationTestRule mDeviceAnimationTestRule = new DeviceAnimationTestRule();
    @Rule
    public ActivityTestRule<LandingActivity> mActivityRule = new ActivityTestRule<>(LandingActivity.class, true, false);

    public void mockLaunchActivity() {
        startMockServer();
        //Mock the BFF response
        mMockServer.mockHttpResponse(SampleData.getPageAssembleResponseData(), 200);

        mMockServer.mockHttpResponse(SampleData.getContentResponse("content_identifier"), 200);

        Intent intent = new Intent(mTargetContext, LandingActivity.class);

        mActivityRule.launchActivity(intent);
    }

    @Override
    public void setup() throws IOException {
        super.setup();
    }

    @Override
    public void tearDown() throws IOException {
        super.tearDown();
        shutDownMockServer();
    }

    @Test
    /**
     *  Scenario: User launches the app for the first time and he should be seeing the Add Child on boarding
     */
    public void test0UserShouldSeeHomeScreenOnLaunch() {
        //setting this session to false is for first time user
        Session.getInstance().setFirstTime("false");

        //setting this session to 0 is for showing the add child
        Session.getInstance().setOnBoardingState(2);

        mockLaunchActivity();

        //remove the imported content if present
        removeContent(CONTENT_IDENTIFIER);
    }

    @Test
    public void test1CopyEcarFileContent() {
        EcarCopyUtil.createFileFromAsset(TEXTBOOK_ASSET_PATH, DESTINATION);
    }

    @Test
    public void test2ImportEcarFileContent() {
        importContent(mTargetContext, TEXTBOOK_FILE_PATH);
    }

    @Test
    public void test3openTextBook() {
        mockLaunchActivity();

        //check if textbook section is visible
        Matcher.isVisibleText(R.string.title_home_my_text_books);

        GenieDBHelper.clearTelemetryTable();

        //click on the first item of the recycler view
        ViewAction.clickOnRecylerViewItem(R.id.rv_my_text_books, 0);

        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        //check if the textbook name is visible
        Matcher.checkWithText(R.id.tv_textbook_name, CONTENT_NAME);

        clearResource();

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGELaunchGameEvent(CONTENT_IDENTIFIER, "INTENT", "", null).
                addGEInteractEvent(TelemetryStageId.TEXTBOOK_HOME, EntityType.CONTENT_ID, InteractionType.SHOW.getValue(), CONTENT_IDENTIFIER, true, TB_LOCAL_CONTENT_COUNT, TB_TOTAL_CONTENT_COUNT, TB_OFFSET)
                .build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }

    @Test
    public void test4checkIfTocIsSeen() {
        mockLaunchActivity();

        //check if textbook section is visible
        Matcher.isVisibleText(R.string.title_home_my_text_books);

        GenieDBHelper.clearTelemetryTable();

        //click on the first item of the recycler view
        ViewAction.clickOnRecylerViewItem(R.id.rv_my_text_books, 0);

        //check if the textbook name is visible
        Matcher.checkWithText(R.id.tv_textbook_name, CONTENT_NAME);

        //see if table of contents is visible
        Matcher.checkWithText(R.id.node_value, TABLE_OF_CONTENTS);

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGELaunchGameEvent(CONTENT_IDENTIFIER, "INTENT", "", null).
                addGEInteractEvent(TelemetryStageId.TEXTBOOK_HOME, EntityType.CONTENT_ID, InteractionType.SHOW.getValue(), CONTENT_IDENTIFIER, true, TB_LOCAL_CONTENT_COUNT, TB_TOTAL_CONTENT_COUNT, TB_OFFSET)
                .build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }

    @Test
    public void test51checkIfFirstSectionIsSeen() {
        mockLaunchActivity();

        //check if textbook section is visible
        Matcher.isVisibleText(R.string.title_home_my_text_books);

        //click on the first item of the recycler view
        ViewAction.clickOnRecylerViewItem(R.id.rv_my_text_books, 0);

        //check if the textbook name is visible
        Matcher.checkWithText(R.id.tv_textbook_name, CONTENT_NAME);

        //see if table of contents is visible
        Matcher.checkWithText(R.id.node_value, TABLE_OF_CONTENTS);

        //check if the first section is visible
        Matcher.checkInRecyclerView(R.id.rv_textbooks, 0, CHILD1);
    }

    @Test
    public void test52checkIfDownloadedLessonsAreAvailable() {
        mockLaunchActivity();

        //check if textbook section is visible
        Matcher.isVisibleText(R.string.title_home_my_text_books);

        //click on the first item of the recycler view
        ViewAction.clickOnRecylerViewItem(R.id.rv_my_text_books, 0);

        //check if the textbook name is visible
        Matcher.checkWithText(R.id.tv_textbook_name, CONTENT_NAME);

        //see if table of contents is visible
        Matcher.checkWithText(R.id.node_value, TABLE_OF_CONTENTS);

        //check if the first section is visible
        Matcher.checkInRecyclerView(R.id.rv_textbooks, 0, CHILD1);

        //click on the downloaded lessons
        ViewAction.clickonView(R.id.tv_textbook_download);

        //check if downloaded lesson view is present
        Matcher.isVisibleText(R.string.title_textbook_downloaded_lessons);
    }

    @Test
    public void test6checkIfTheIndividualContentIsPossibleToDeleteFromTextbookFragment() {
        mockLaunchActivity();

        //click on navigation drawer icon
        ViewAction.clickonView(R.id.img_berger_menu);

        //check if of drawer items are visible
        Matcher.isVisible(R.id.list_drawer);

        ViewAction.clickOnRecylerViewItemText(CommonUtil.Constants.DOWNLOADS);

        //check if Downloads view is visible
        Matcher.check(R.id.txt_header, R.string.title_downloads);

        //click on the first item of the recycler view
        ViewAction.clickOnRecylerViewItemText(CONTENT_NAME);

        //check if the textbook name is visible
        Matcher.checkWithText(R.id.tv_textbook_name, CONTENT_NAME);

        //see if table of contents is visible
        Matcher.checkWithText(R.id.node_value, TABLE_OF_CONTENTS);

        //check if the first section is visible
        Matcher.checkInRecyclerView(R.id.rv_textbooks, 0, CHILD1);

        waitFor(DateUtils.SECOND_IN_MILLIS * 10);

        //check if downloaded lesson view is present
        Matcher.isVisibleText(R.string.title_textbook_downloaded_lessons);

        clearResource();

        ViewAction.clickonView(R.id.tv_textbook_download);

        //click on layout more of the  textbook content
        ViewAction.clickOnRecylerViewItem(R.id.rv_downloaded_textbooks, 0, R.id.ib_more);

        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        //Click on Delete
        ViewAction.clickonString(R.id.layout_delete, R.string.action_dialog_delete);

        clearResource();

        //Assert for content deletion toast
        Matcher.isToastMessageDisplayed(R.string.msg_content_deletion_sucessfull);
    }

    @Test
    public void test7checkIfDetailsPageIsSeen() {
        mockLaunchActivity();

        //check if textbook section is visible
        Matcher.isVisibleText(R.string.title_home_my_text_books);

        //click on the first item of the recycler view
        ViewAction.clickOnRecylerViewItem(R.id.rv_my_text_books, 0);

        //check if the textbook name is visible
        Matcher.checkWithText(R.id.tv_textbook_name, CONTENT_NAME);

        //see if table of contents is visible
        Matcher.checkWithText(R.id.node_value, TABLE_OF_CONTENTS);

        //check if the first section is visible
        Matcher.checkInRecyclerView(R.id.rv_textbooks, 0, CHILD1);

        //click on the details text
        ViewAction.clickonView(R.id.tv_textbook_details);

        //check if content name text view is visible
        Matcher.checkWithText(R.id.txt_content_name, CONTENT_NAME);
    }

    // TODO 28/07/2016 enable below test cases if required
//    @Test
//    public void test8clickOnDownloadButton() {
//        mockLaunchActivity();
//
//        //check if textbook section is visible
//        Matcher.isVisibleText(R.string.title_home_my_text_books);
//
//        //click on the first item of the recycler view
//        ViewAction.clickOnRecylerViewItem(R.id.rv_my_text_books, 0);
//
//        //check if the textbook name is visible
//        Matcher.checkWithText(R.id.tv_textbook_name, CONTENT_NAME);
//
//        //see if table of contents is visible
//        Matcher.checkWithText(R.id.node_value, TABLE_OF_CONTENTS);
//
//        //check if the first section is visible
//        Matcher.checkInRecyclerView(R.id.rv_textbooks, 0, CHILD1);
//
//        Matcher.isVisible(R.id.ib_chapter_download);
//
//        //click on the first item of the recycler view
//        ViewAction.clickOnRecylerViewItem(R.id.rv_textbooks, 0, R.id.ib_chapter_download);
//
//        //check if content name text view is visible
//        Matcher.checkWithText(R.id.chapter_name_dialog, "Download " + CHILD1);
//
//        //Click on Download
//        ViewAction.clickonString(R.id.txt_download, R.string.label_contentdetail_download);
//    }
//
//    @Test
//    public void test6checkIfTheContentCanBeDeletedAndIsSuccessful() {
//        mockLaunchActivity();
//
//        //click on navigation drawer icon
//        ViewAction.clickonView(R.id.img_berger_menu);
//
//        //check if of drawer items are visible
//        Matcher.isVisible(R.id.list_drawer);
//
//        ViewAction.clickOnRecylerViewItemText(CommonUtil.Constants.DOWNLOADS);
//
//        //check if Downloads view is visible
//        Matcher.check(R.id.txt_header, R.string.title_downloads);
//
//        //click on the first item of the recycler view
//        ViewAction.clickOnRecylerViewItemText(R.id.rv_my_lessons_downloaded, CONTENT_NAME, R.id.ib_more);
//
//        //check if the textbook name is visible
//        Matcher.checkWithText(R.id.tv_textbook_name, CONTENT_NAME);
//
//        //see if table of contents is visible
//        Matcher.checkWithText(R.id.node_value, TABLE_OF_CONTENTS);
//
//        //Click on Delete
//        ViewAction.clickonString(R.id.layout_delete, R.string.action_dialog_delete);
//
//        clearResource();
//
//        //Assert for content deletion toast
//        Matcher.isToastMessageDisplayed(R.string.msg_content_deletion_sucessfull);
//    }
//
//    @Test
//    public void test93openTextBookWithNestedCollection() {
//        mockLaunchActivity();
//
//        // Click on menu berger
//        onView(withId(R.id.img_berger_menu)).perform(ViewActions.click());
//
//        // Click on Transfer
//        onView(withId(R.id.list_drawer)).perform(RecyclerViewActions.actionOnItemAtPosition(3, ViewActions.click()));
//
//
//        //click on the first item of the recycler view
//        ViewAction.clickOnRecylerViewItem(R.id.rv_my_lessons_downloaded, 0);
//
//
//        //click on the first item of the recycler view
//        ViewAction.clickOnRecylerViewItem(R.id.rv_textbooks, 0, ViewAction.clickNestedChildViewWithId(R.id.ll_chapter_lessons, 2, R.id.ib_more));
//
//        onView(withId(R.id.txt_content_title))
//                .inRoot(RootMatchers.isDialog()).
//                check(matches(withText("Fruits")))
//                .check(ViewAssertions.matches(isDisplayed()));
//    }

}
