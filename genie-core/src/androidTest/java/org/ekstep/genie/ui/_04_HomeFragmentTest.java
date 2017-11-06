package org.ekstep.genie.ui;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;
import android.text.format.DateUtils;

import org.ekstep.genie.BuildConfig;
import org.ekstep.genie.GenieTestBase;
import org.ekstep.genie.R;
import org.ekstep.genie.custom.action.ViewAction;
import org.ekstep.genie.custom.matcher.Matcher;
import org.ekstep.genie.model.ExpectedEventList;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.telemetry.enums.EntityType;
import org.ekstep.genie.ui.landing.LandingActivity;
import org.ekstep.genie.util.CommonUtil;
import org.ekstep.genie.util.DeviceAnimationTestRule;
import org.ekstep.genie.util.GenieDBHelper;
import org.ekstep.genie.util.SampleData;
import org.ekstep.genie.util.Session;
import org.ekstep.genie.util.TelemetryVerifier;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;

/**
 * This is the integration tests class for the Landing Activity seeing possible scenarios
 * <p>
 * Created by shriharsh on 18/1/17.
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class _04_HomeFragmentTest extends GenieTestBase {

    @ClassRule
    static public DeviceAnimationTestRule mDeviceAnimationTestRule = new DeviceAnimationTestRule();
    @Rule
    public ActivityTestRule<LandingActivity> mActivityTestRule = new ActivityTestRule<LandingActivity>(LandingActivity.class, true, false);

    public void mockLaunchActivity() {

        startMockServer();

        //Mock the BFF response
//        mMockServer.mockHttpResponse(SampleData.getPageAssembleResponseData(), 200);
        //mock the search content response
        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);
        mMockServer.mockHttpResponse(SampleData.searchResponse(), 200);

        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, LandingActivity.class);

        mActivityTestRule.launchActivity(intent);
    }

    @Override
    public void setup() throws IOException {
        super.setup();

    }

    @Override
    public void tearDown() {
        shutDownMockServer();
    }

//    @Test
//    /**
//     * Dummy Test to Turn off the wifi
//     */
//    public void test0_0turnOffWifi() throws InterruptedException {
//
//        mockLaunchActivity();
//
//        WifiManager wifiManager = (WifiManager) mActivityRule.getActivity().getSystemService(Context.WIFI_SERVICE);
//        wifiManager.setWifiEnabled(false);
//
//        Thread.sleep(10000);
//
//    }
//
//    @Test
//    /**
//     * Scenario - When the user launches the app for the first time and thier is no internet connected
//     */
//    public void test0_1ClickOnTheSettingsItemInNavigationDrawer() throws InterruptedException {
//        //setting this session to true is for first time user
//        Session.getInstance().setFirstTime("false");
//
//        //setting this session to 0 is for showing the add child
//        Session.getInstance().setOnBoardingState(2);
//
//        mockLaunchActivity();
//
//        ViewAction.swipeUpView(R.id.scroll_home);
//
//        Matcher.isVisibleText(R.string.connect_to_internet_to_get_more_content);
//
//    }

    @Test
    /**
     *  Scenario: User launches the app for the first time and he should be seeing the Add Child on boarding
     */
    public void test1UserShouldSeeOnBoardingOnLaunch() {

        // Delete all profile for first testcase.DONT REMOVE THIS LINE.
        GenieDBHelper.deleteAllUserProfile();

        //setting this session to true is for first time user
        Session.getInstance().setFirstTime("true");

        //setting this session to 0 is for showing the add child
        Session.getInstance().setOnBoardingState(0);

        mockLaunchActivity();

        Matcher.isVisible(R.id.transparent_view);

        Matcher.isVisible(R.id.dummy_add_child_or_avatar_image);
    }

    @Test
    /**
     * Scenario: User is in the Add Child screen of on-boarding, and had killed previously the app and comes back again
     */
    public void test2ToClickOnSkipButtonWhileOnBoardingForAddChild() {
        mockLaunchActivity();

        Matcher.isVisible(R.id.transparent_view);

        Matcher.isVisible(R.id.dummy_add_child_or_avatar_image);

        GenieDBHelper.clearTelemetryTable();

        //click on skip button
        ViewAction.clickonView(R.id.layout_skip);

        Matcher.isVisibleText(R.string.label_home_choose_subject);

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.GENIE_HOME_ONBOARDING_SCREEN, TelemetryAction.ADD_CHILD_SKIPPED, InteractionType.TOUCH.getValue(), null, false)
                .build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
        shutDownMockServer();

    }

    @Test
    /**
     * Scenario: User is in the Change Subject screen of on-boarding, and has killed the app and comes back again
     */
    public void test3ToClickOnSkipButtonWhileOnBoardingForChangeSubject() {
        //relaunch the app again
        mockLaunchActivity();

        GenieDBHelper.clearTelemetryTable();

        Matcher.isVisibleText(R.string.label_home_choose_subject);

        //click on skip button
        ViewAction.clickonView(R.id.layout_skip);

        Matcher.isVisible(R.id.img_berger_menu);

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.GENIE_HOME_ONBOARDING_SCREEN, TelemetryAction.CHANGE_SUBJECT_SKIPPED, InteractionType.TOUCH.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.GENIE_HOME_ONBOARDING_SCREEN, EntityType.ONBOARDING, InteractionType.SHOW.getValue(), null, false)
                .build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
        shutDownMockServer();
    }

    @Test
    /**
     * Scenario: User has skipped both the on-boarding and landed on the home page
     */
    public void test4ToClickOnAddChildButton() {
        //relaunch the app again
        mockLaunchActivity();

        //click on skip button
        ViewAction.clickonView(R.id.add_child_or_avatar_image);
        shutDownMockServer();
    }

    @Test
    /**
     * Scenario: User is in the Add Child screen of on-boarding, clicks on Add Child button
     */
    public void test5ClickOnTheAddChildButtonWhileOnBoarding() {
        //setting this session to true is for first time user
        Session.getInstance().setFirstTime("true");

        //setting this session to 0 is for showing the add child
        Session.getInstance().setOnBoardingState(0);

        mockLaunchActivity();

        Matcher.isVisible(R.id.transparent_view);

        Matcher.isVisible(R.id.dummy_add_child_or_avatar_image);

        //click on add child button
        ViewAction.clickonView(R.id.dummy_add_child_or_avatar_image);

        Matcher.isVisibleText(R.string.title_addchild);

        shutDownMockServer();
    }


    @Test
    /**
     * Scenario: User is in the Change Subject screen of on-boarding, clicks Change Subject button
     *
     *  Since we are making PopUpWindowUtil focusable false, this test case fails when doing some action on Popup window recycler view
     */
    public void test6ClickOnTheChangeSubjectButtonWhileOnBoarding() throws InterruptedException {
        mockLaunchActivity();

        Matcher.isVisibleText(R.string.label_home_choose_subject);

        ViewAction.clickonView(R.id.dummy_txt_change_subject);

        //click on skip button
        ViewAction.clickonView(R.id.layout_skip);
        shutDownMockServer();
    }

//    @Test
//    /**
//     * Scenario: User clicks on the change subject button after on-boarding and he should
//     * see the list of subjects
//     *
//     *  Since we are making PopUpWindowUtil focusable false, this test case fails when doing some action on Popup window recycler view
//     */
//    public void test7ClickOnTheChangeSubjectButtonAfterOnBoarding() {
//        mockLaunchActivity();
//
//        //click on change subject
//        ViewAction.clickonView(R.id.txt_change_subject_name);
//
//        Matcher.checkInRecyclerView(R.id.recycler_view_pop_up_dialog, 0, "All Subjects");
//    }

    @Test
    /**
     * Scenario: User clicks on the change subject button after on-boarding and he clicks on the any of the available subjects
     * then the same subject will be set in place AlSubjects
     */
    public void test8ChooseThirdSubjectInTheSubjectList() {

        mockLaunchActivity();

        GenieDBHelper.clearTelemetryTable();

        //click on change subject
        ViewAction.clickonView(R.id.txt_change_subject_name);

        ViewAction.clickOnRecylerViewItem(R.id.recycler_view_pop_up_dialog, 1);

        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        Matcher.checkWithText(R.id.txt_subject_name, "Maths");

        clearResource();

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.GENIE_HOME, TelemetryAction.SUBJECT_CHANGED, InteractionType.TOUCH.getValue(), "Maths", false)
                .build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }

    @Test
    /**
     * Scenario: User Clicks on the Add Child button and the it should open up the add child activity
     */
    public void test9ClickOnAddChildButtonAfterOnBoarding() {
        mockLaunchActivity();

        //click on change subject
        ViewAction.clickonView(R.id.add_child_or_avatar_image);

        Matcher.isVisibleText(R.string.title_addchild);
    }

    @Test
    /**
     *Scenario: User Clicks on the Search button and then types in some text and user should be taken to explore content
     */
    public void test9_0ClickOnSearchAndTypeTextAndSearchForTheText() {
        mockLaunchActivity();

        //click on search button
        ViewAction.clickonView(R.id.search_content_btn);

        //check if disabled view is visible
        Matcher.isVisible(R.id.rl_disabled_view);

        //check if edit text is visible
        Matcher.isVisible(R.id.edt_tool_search);

        //type in edit text
        ViewAction.typeText(R.id.edt_tool_search, "Hello");

        //click for search
        ViewAction.clickonView(R.id.btn_tool_search);

        //check if the entered text is present in search edit text
        Matcher.isVisible(R.id.filter_btn);

        //close soft keyboard back button
        closeSoftKeyboard();

        //check if the text is available in the edit text
        Matcher.checkWithText(R.id.txt_header, "Hello");
    }

    @Test
    /**
     *Scenario: User Clicks on the Search button and then types in only 2 characters, but user should type in at least 3 characters to search content
     * and if not user will be displayed a toast with message
     */
    public void test9_1ClickOnSearchAndTypeNothingAndSearchText() {
        mockLaunchActivity();

        //click on search button
        ViewAction.clickonView(R.id.search_content_btn);

        //check if disabled view is visible
        Matcher.isVisible(R.id.rl_disabled_view);

        //check if edit text is visible
        Matcher.isVisible(R.id.edt_tool_search);

        //type in edit text
        ViewAction.typeText(R.id.edt_tool_search, "");

        //click for search
        ViewAction.clickonView(R.id.btn_tool_search);

        //check if the entered text is present in search edit text
        Matcher.isToastMessageDisplayed(R.string.error_search);

        //close soft keyboard back button
        closeSoftKeyboard();
    }

    @Test
    /**
     * Scenario: User clicks on the navigation drawer icons and the navigation drawer should be visible
     */
    public void test9_2ClickOnNavigationDrawerIconShouldOpenNavigationDrawer() {
        mockLaunchActivity();

        //click on navigation drawer icon
        ViewAction.clickonView(R.id.img_berger_menu);

        //check if of drawer items are visible
        Matcher.isVisible(R.id.list_drawer);
    }

    @Test
    /**
     * Scenario: User is first time user, when user skips the on-boarding and reaches the home screen, he should see MY LESSONS section with by default 2 content
     * 1. PlayerUtil with Tipu
     * 2. Welcome to Genie!
     *
     */
    public void test9_3FirstTimeUserWithTwoDefaultContents() {

        //setting this session to true is for first time user
        Session.getInstance().setFirstTime("true");

        //setting this session to 0 is for showing the add child
        Session.getInstance().setOnBoardingState(0);

        mockLaunchActivity();

//        CommonUtil.importContent(mActivityTestRule.getActivity(), Constant.CONTENT1_FILE_PATH, false);

        Matcher.isVisible(R.id.transparent_view);

        Matcher.isVisible(R.id.dummy_add_child_or_avatar_image);

        //click on skip button
        ViewAction.clickonView(R.id.layout_skip);

        Matcher.isVisibleText(R.string.label_home_choose_subject);

        //click on skip button
        ViewAction.clickonView(R.id.layout_skip);

        //check if the MY LESSONS text is visible
        Matcher.checkWithText(R.id.tv_my_lessons, "My Lessons");

        //check if recycler view is visible
        Matcher.isVisible(R.id.rv_my_lessons);

    }

    @Test
    /**
     * Scenario: When the user clicks on the navigation drawer and then clicks on Share item, SHare Genie activity should be seen
     */
    public void test9_4ClickOnTheShareItemInTheNavigationDrawer() {
        mockLaunchActivity();

        //click on navigation drawer icon
        ViewAction.clickonView(R.id.img_berger_menu);

        //check if of drawer items are visible
        Matcher.isVisible(R.id.list_drawer);

        ViewAction.clickOnRecylerViewItemText("Share");

        Matcher.isVisibleText(R.string.label_nav_share_genie);
    }

    @Test
    /**
     * Scenario - When the user clicks on Children, manage children screen should open up
     */
    public void test9_5ClickOnTheChildrenItemInNavigationDrawer() {
        mockLaunchActivity();

        //click on navigation drawer icon
        ViewAction.clickonView(R.id.img_berger_menu);

        //check if of drawer items are visible
        Matcher.isVisible(R.id.list_drawer);

        ViewAction.clickOnRecylerViewItemText("Children");

        //children text view has children
        Matcher.check(R.id.txt_children, R.string.title_children);

        //group text view has groups
        Matcher.check(R.id.txt_groups, R.string.title_children_groups);
    }

    @Test
    /**
     * Scenario - When the user clicks on the Lessons item in Navigation Drawer, Downloads screen should open up
     */
    public void test9_7ClickOnTheLessonsItemInNavigationDrawer() {
        mockLaunchActivity();

        //click on navigation drawer icon
        ViewAction.clickonView(R.id.img_berger_menu);

        //check if of drawer items are visible
        Matcher.isVisible(R.id.list_drawer);

        ViewAction.clickOnRecylerViewItemText("Downloads");

        //check if Downloads view is visible
        Matcher.check(R.id.txt_header, R.string.title_downloads);
    }

    @Test
    /**
     * Scenario - When the user clicks on the Transfer item in Navigation Drawer, Transfer screen should open up
     */
    public void test9_8ClickOnTheTransferItemInNavigationDrawer() {
        mockLaunchActivity();

        //click on navigation drawer icon
        ViewAction.clickonView(R.id.img_berger_menu);

        //check if of drawer items are visible
        Matcher.isVisible(R.id.list_drawer);

        ViewAction.clickOnRecylerViewItemText("Transfer");

        //check if Downloads view is visible
        Matcher.check(R.id.txt_header, R.string.label_nav_transfer);
    }

    @Test
    /**
     * Scenario - When the user clicks on the Settings item in Navigation Drawer, Settings screen should open up
     */
    public void test9_9ClickOnTheSettingsItemInNavigationDrawer() {
        mockLaunchActivity();

        //click on navigation drawer icon
        ViewAction.clickonView(R.id.img_berger_menu);

        //check if of drawer items are visible
        Matcher.isVisible(R.id.list_drawer);

        ViewAction.clickOnRecylerViewItemText("Settings");

        //check if Downloads view is visible
        Matcher.check(R.id.txt_header, R.string.label_nav_settings);
    }

    @Test
    /**
     * Scenario - When the user clicks on the device back button and kills the app, then re-opens the app
     * which when the subjects should be by default set as All Subjects
     */
    public void test9_91ClickOnDeviceBackButtonAndKillTheApp() {
        mockLaunchActivity();

        // Initialize UiDevice instance
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        Assert.assertNotNull(uiDevice);

        uiDevice.pressBack();

        //check if Close Genie dialog is visible
        Matcher.isVisible(R.id.txt_name_dialog);

        //click on Yes OK button in close dialog
        ViewAction.clickonView(R.id.txt_yes);

        GenieDBHelper.clearTelemetryTable();

        launchAppUsingPkgName(BuildConfig.APPLICATION_ID, uiDevice);

        CommonUtil.stopAutoSyncing();

        uiDevice.wait(Until.hasObject(By.text("Change Subject")), TIMEOUT);

        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        //check if ALL SUBJECTS is set
        Matcher.checkWithText(R.id.txt_subject_name, "All Subjects");

        clearResource();

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.GENIE_HOME, TelemetryAction.SUBJECT_RESET, InteractionType.OTHER.getValue(), null, false)
                .build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }


}
