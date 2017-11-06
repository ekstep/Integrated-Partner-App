package org.ekstep.genie.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
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
import org.ekstep.genie.util.EcarCopyUtil;
import org.ekstep.genie.util.GenieDBHelper;
import org.ekstep.genie.util.SampleData;
import org.ekstep.genie.util.Session;
import org.ekstep.genie.util.TelemetryVerifier;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.junit.After;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;

/**
 * Created by Sneha on 3/13/2017.
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class _34_CollectionFragmentTest extends GenieTestBase {
    private static final String COLLECTION_FILE_PATH = Environment.getExternalStorageDirectory().toString() + "/Download/Collection_Sequence_v1_0.ecar";
    private static final String COLLECTION_ASSET_PATH = "Download/Collection_Sequence_v1_0.ecar";
    private static final String DESTINATION = Environment.getExternalStorageDirectory().toString() + "/Download";

    private static final String CONTENT_NAME = "Collection Sequence";
    private static final String CONTENT_IDENTIFIER = "do_212277571964256256160";
    private static final String CONTENT_CHILD1_NAME = "Comp";
    private static final String CONTENT_CHILD1_IDENTIFIER = "do_212240782217428992134";
    private static final String CONTENT_CHILD2_NAME = "Test: Dependecny";
    private static final String CONTENT_CHILD2_IDENTIFIER = "do_212240663763992576113";
    private static final int LOCAL_CONTENT_COUNT = 1;
    private static final int TOTAL_CONTENT_COUNT = 2;
    private static final int OFFSET = 0;

    private Context mTargetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

    @Rule
    public ActivityTestRule<LandingActivity> mActivityRule = new ActivityTestRule<LandingActivity>(LandingActivity.class, true, false);

    public void mockLaunchActivity() {

        startMockServer();

        //Mock the BFF response
        mMockServer.mockHttpResponse(SampleData.getPageAssembleResponseData(), 200);
        mMockServer.mockHttpResponse(SampleData.getLiveContentDetailsResponse("content_identifier"), 200);

        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, LandingActivity.class);

        mActivityRule.launchActivity(intent);
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

    private void openLocallyAvailableCollectionFromHome() {
        //check if my lessons section is visible
        Matcher.check(R.id.tv_my_lessons, R.string.title_home_my_lessons);

        //click on the first item of the recycler view
        ViewAction.clickOnRecylerViewItemText(CONTENT_NAME);

        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        //check if the textbook name is visible
        Matcher.checkWithText(R.id.tv_collection_name, CONTENT_NAME);

        clearResource();
    }

    /**
     * click on the more option of the content
     */
    private void openLayoutMore() {
        //click on navigation drawer icon
        ViewAction.clickonView(R.id.img_berger_menu);

        //check if of drawer items are visible
        Matcher.isVisible(R.id.list_drawer);

        ViewAction.clickOnRecylerViewItemText(CommonUtil.Constants.DOWNLOADS);

        //check if Downloads view is visible
        Matcher.check(R.id.txt_header, R.string.title_downloads);

        //click on the first item of the recycler view
        ViewAction.clickOnRecylerViewItemText(CONTENT_NAME);

        //check if the collection name is visible
        Matcher.checkWithText(R.id.tv_collection_name, CONTENT_NAME);

        //check if the first section is visible
        Matcher.checkInRecyclerView(R.id.rv_collections, 0, CONTENT_CHILD1_NAME);

        //click on layout more of the  collection content
        ViewAction.clickOnRecylerViewItem(R.id.rv_collections, 0, R.id.ib_more);
    }

    private void pressDeviceBack() {
        // Initialize UiDevice instance
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        Assert.assertNotNull(uiDevice);

        uiDevice.pressBack();
    }

    /**
     * Scenario: User launches the app for the first time and he should be seeing the Add Child on boarding
     */
    @Test
    public void test0UserShouldSeeHomeScreenOnLaunch() {
        //setting this session to false is for first time user
        Session.getInstance().setFirstTime("false");

        //setting this session to 0 is for showing the add child
        Session.getInstance().setOnBoardingState(2);

        mockLaunchActivity();

        //remove the imported content if present
        removeContent(CONTENT_IDENTIFIER);
    }

    /**
     * Import collection.
     */
    @Test
    public void test11ImportCollection() {
        //remove if the collection already exists
        removeContent(CONTENT_IDENTIFIER);

        EcarCopyUtil.createFileFromAsset(COLLECTION_ASSET_PATH, DESTINATION);

        importContent(mTargetContext, COLLECTION_FILE_PATH);
    }

    /**
     * Scenario - User is in the home screen, clicks on the collections, collections screen is displayed.
     */
    @Test
    public void test12openCollection() {
        mockLaunchActivity();

        GenieDBHelper.clearTelemetryTable();

        openLocallyAvailableCollectionFromHome();

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGELaunchGameEvent(CONTENT_IDENTIFIER, "INTENT", "", null).
                addGEInteractEvent(TelemetryStageId.COLLECTION_HOME, EntityType.CONTENT_ID, InteractionType.SHOW.getValue(), CONTENT_IDENTIFIER, false)
                .build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }

    /**
     * Scenario : When user in the collections screen, user clicks on the details button, content details screen is displayed.
     */
    @Test
    public void test2ClickOnCollectionDetailsButton() {
        mockLaunchActivity();

        GenieDBHelper.clearTelemetryTable();

        openLocallyAvailableCollectionFromHome();

        //click on the details text
        ViewAction.clickonView(R.id.tv_collection_details);

        //check if content name text view is visible
        Matcher.checkWithText(R.id.txt_content_name, CONTENT_NAME);
    }

    @Test
    /**
     *  Scenario : When user clicks on the available content, the content plays.
     */
    public void test3ClickOnAvailableCollectionContent() {
        mockLaunchActivity();

        openLocallyAvailableCollectionFromHome();

        //check if the first section is visible
        Matcher.checkInRecyclerView(R.id.rv_collections, 0, CONTENT_CHILD1_NAME);

        //click on downloaded collection content
        ViewAction.clickOnRecylerViewItem(R.id.rv_collections, 0);

        pressDeviceBack();
    }

    /**
     * Scenario : When user is in the collection screen, user clicks on the back button,
     * user gets back to the home screen.
     */
    @Test
    public void test4ClickOnBackButtonInCollectionScreen() {
        mockLaunchActivity();

        openLocallyAvailableCollectionFromHome();

        ViewAction.clickonView(R.id.back_btn);

        Matcher.isVisible(R.id.img_berger_menu);
    }

    /**
     * Scenario : Deleting the collection content.
     */
    @Test
    public void test5checkIfTheIndividualContentIsPossibleToDelete() {
        mockLaunchActivity();

        //click on the content's more option.
        openLayoutMore();

        // Wait for some time
        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        //Click on Delete
        ViewAction.clickonString(R.id.layout_delete, R.string.action_dialog_delete);

        clearResource();

        //Assert if Yes layout is showing or not
        Matcher.isVisible(R.id.delete_yes);

        //Assert if No layout is showing or not
        Matcher.isVisible(R.id.delete_no);

        //Click on Yes
        ViewAction.clickonString(R.id.delete_yes, R.string.label_dialog_yes);

        //Assert for content deletion toast
        Matcher.isToastMessageDisplayed(R.string.msg_content_deletion_sucessfull);
    }

    /**
     * Scenario : When user clicks on the unavailable collection content,
     * content details screen is displayed.
     */
    @Test
    public void test6ClickOnUnAvailableSingleCollectionContent() {
        mockLaunchActivity();

        openLocallyAvailableCollectionFromHome();

        //check if the first section is visible
        Matcher.checkInRecyclerView(R.id.rv_collections, 1, CONTENT_CHILD2_NAME);

        //click on layout more of the  collection content
        ViewAction.clickOnRecylerViewItem(R.id.rv_collections, 1);

        // Wait for some time
        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        //check if content name text view is visible
        Matcher.checkWithText(R.id.txt_content_name, CONTENT_CHILD2_NAME);

        //Check for the download button visibility
        Matcher.isVisibleText(R.string.label_contentdetail_download);

        ViewAction.clickonView(R.id.tv_download);

        Matcher.isVisible(R.id.layout_downnload_progress);

        // Wait for some time
        waitFor(DateUtils.SECOND_IN_MILLIS * 2000);

//        // check open is available
        Matcher.isVisible(R.id.txt_play);

        clearResource();
    }

    /**
     * Scenario : Check for download all button visibility
     * Scenario : When user clicks on the download all button,
     * all the contents get downloaded
     */
    @Test
    public void test71ClickOnDownloadAllButton() {
        mockLaunchActivity();

        openLocallyAvailableCollectionFromHome();

        Matcher.isVisible(R.id.layout_download_all);

        ViewAction.clickonView(R.id.layout_download_all);

        //wait for sometime
        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        //check downloading layout visibility
        Matcher.isVisible(R.id.txt_downloading);

        clearResource();

        //check downloading layout visibility
        Matcher.isNotVisible(R.id.txt_downloading);
    }

    /**
     * Scenario : When user clicks on download all, contents get downloaded,
     * Download all view visibility is gone.
     */
    @Test
    public void test72AllCollectionContentsAvailable() {
        mockLaunchActivity();

        openLocallyAvailableCollectionFromHome();

        //check download all visibility
        Matcher.isNotVisible(R.id.layout_download_all);
    }


//    @Test
//    /**
//     * Scenario : When user is in the collection screen, user clicks on the back button,
//     * user gets back to the home screen.
//     */
//    public void test91shouldGeneratedProperTelemetryForNestedCollection() {
//
//        mockLaunchActivity();
//
//        //remove if the collection already exists
//        removeContent(TIMES_TABLE_ECAR_IDENTIFIER);
//
//        //remove if the collection already exists
//        removeContent(NESTED_COLLECTION_IDENTIFIER);
//
//        EcarCopyUtil.createFileFromAsset(NESTED_COLLECTION_ASSET_PATH, DESTINATION);
//
////        idlingResource = startTiming(5000);
//
//        importContent(mActivityRule.getActivity(), NESTED_COLLECTION_FILE_PATH);
//
//        GenieDBHelper.clearTelemetryTable();
//
////        stopTiming(idlingResource);
//
//        //click on the first item of the recycler view
//        ViewAction.clickOnRecylerViewItem(R.id.rv_my_lessons, 0);
//
//        //check if the collection name is visible
//        Matcher.checkWithText(R.id.tv_collection_name, NESTED_COLLECTION_NAME);
//
//        //click on the 2nd item of the recycler view
//        ViewAction.clickOnRecylerViewItem(R.id.rv_collections, 1);
//
//        //check if the collection name is visible
//        Matcher.checkWithText(R.id.tv_collection_name, NESTED_COLLECTION_CHILD1_NAME);
//
//        //click on the 1st item of the recycler view
//        ViewAction.clickOnRecylerViewItem(R.id.rv_collections, 0);
//
//        //check if the collection name is visible
//        Matcher.checkWithText(R.id.tv_collection_name, NESTED_COLLECTION_CHILD2_NAME);
//
//        ViewAction.clickonView(R.id.back_btn);
//
//        ViewAction.clickonView(R.id.back_btn);
//
//        ViewAction.clickonView(R.id.back_btn);
//
//        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
//                addGELaunchGameEvent(NESTED_COLLECTION_IDENTIFIER, "INTENT", "", "").
//                addGELaunchGameEvent(NESTED_COLLECTION_CHILD1_IDENTIFIER, "INTENT", NESTED_COLLECTION_IDENTIFIER, Constants.CONTENT_TYPE_COLLECTION).
//                addGELaunchGameEvent(NESTED_COLLECTION_CHILD2_IDENTIFIER, "INTENT", NESTED_COLLECTION_IDENTIFIER + "/" + NESTED_COLLECTION_CHILD2_IDENTIFIER, Constants.CONTENT_TYPE_COLLECTION).
//                addGEInteractEvent(TelemetryStageId.IMPORT_CONTENT, TelemetryAction.CONTENT_IMPORT_INITIATED, InteractionType.TOUCH.getValue(), null, false).
//                addGEInteractEvent(TelemetryStageId.IMPORT_CONTENT, TelemetryAction.CONTENT_IMPORT_SUCCESS, InteractionType.OTHER.getValue(), null, false).
//                addGEInteractEvent(TelemetryStageId.COLLECTION_HOME, null, InteractionType.SHOW.getValue(), NESTED_COLLECTION_IDENTIFIER, false).
//                addGEInteractEvent(TelemetryStageId.COLLECTION_HOME, null, InteractionType.SHOW.getValue(), NESTED_COLLECTION_CHILD1_IDENTIFIER, false).
//                addGEInteractEvent(TelemetryStageId.COLLECTION_HOME, null, InteractionType.SHOW.getValue(), NESTED_COLLECTION_CHILD2_IDENTIFIER, false).
//                addGEGameEndEvent(NESTED_COLLECTION_CHILD2_IDENTIFIER, 0).
//                addGEGameEndEvent(NESTED_COLLECTION_CHILD1_IDENTIFIER, 0).
//                addGEGameEndEvent(NESTED_COLLECTION_IDENTIFIER, 0).build();
//
//        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);

//    }

}
