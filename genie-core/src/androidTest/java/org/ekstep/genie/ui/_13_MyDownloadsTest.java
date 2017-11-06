package org.ekstep.genie.ui;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.format.DateUtils;

import org.ekstep.genie.GenieTestBase;
import org.ekstep.genie.R;
import org.ekstep.genie.custom.Assertion;
import org.ekstep.genie.custom.action.ViewAction;
import org.ekstep.genie.custom.matcher.Matcher;
import org.ekstep.genie.custom.matcher.RecyclerViewMatcher;
import org.ekstep.genie.model.ExpectedEventList;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.telemetry.enums.EntityType;
import org.ekstep.genie.ui.contentdetail.ContentDetailActivity;
import org.ekstep.genie.ui.landing.LandingActivity;
import org.ekstep.genie.ui.landing.home.HomeFragment;
import org.ekstep.genie.ui.progressreports.ProgressReportsActivity;
import org.ekstep.genie.util.GenieDBHelper;
import org.ekstep.genie.util.TelemetryVerifier;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

/**
 * Created by swayangjit on 8/8/2016.
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class _13_MyDownloadsTest extends GenieTestBase {

    private static final String SLATE_IDENTIFIER = "do_30094761";
    private static final String WELCOME_TO_GENIE_IDENTIFIER = "do_30074541";
    private static final String PLAY_WITH_TIPU_IDENTIFIER = "do_30076072";
    @Rule
    public IntentsTestRule<LandingActivity> mActivityTestRule = new IntentsTestRule<>(LandingActivity.class);
    private String mFeedbacktext = "Test feedback";

    @Override
    public void setup() throws IOException {
        super.setup();
        GenieDBHelper.clearTelemetryTable();
    }

    @Override
    public void tearDown() throws IOException {
        super.tearDown();
    }

    @Test
    public void test1ShouldNavigatetoHomeonClickingBackButton() {

        //Open Drawer in MyBackpack
        ViewAction.navaigateToDrawerItems(3);

        //Check Text
        Matcher.check(R.id.txt_header, R.string.label_nav_downloads);
        ViewAction.clickonView(R.id.back_btn);
        Assert.assertTrue(mActivityTestRule.getActivity().getRunningFragment() instanceof HomeFragment);
        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.MY_CONTENT, null, InteractionType.SHOW.getValue(), null, false).
                build();
        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }

    @Test
    public void test2ShouldNavigatetoHomeonClickingDeviceBackButton() {

        //Open Drawer in MyBackpack
        ViewAction.navaigateToDrawerItems(3);

        //Check Text
        Matcher.check(R.id.txt_header, R.string.label_nav_downloads);
        pressBack();
        Assert.assertTrue(mActivityTestRule.getActivity().getRunningFragment() instanceof HomeFragment);
    }

    @Test
    public void test3ShouldNavigatetoImportContentonClickingImport() {

        //Open Drawer in MyBackpack
        ViewAction.navaigateToDrawerItems(3);

        //Check Text
        Matcher.check(R.id.txt_header, R.string.label_nav_downloads);
        ViewAction.clickonView(R.id.menu_import_btn);
        Matcher.check(R.id.txt_header, R.string.title_downloads_import_content);
    }

    @Test
    public void test41ShouldShowOverlayonTilesonClickingShare() {

        //Open Drawer in MyBackpack
        ViewAction.navaigateToDrawerItems(3);

        //Check Text
        Matcher.check(R.id.txt_header, R.string.label_nav_downloads);
        ViewAction.clickonView(R.id.btn_share);
        Matcher.isVisible(R.id.btn_done);
        Matcher.checkInRecyclerViewItemVisibility(R.id.rv_my_lessons_downloaded, 0, R.id.vw_selector_layer);
        Matcher.checkInRecyclerViewItemVisibility(R.id.rv_my_lessons_downloaded, 1, R.id.vw_selector_layer);
        Matcher.checkInRecyclerViewItemVisibility(R.id.rv_my_lessons_downloaded, 2, R.id.vw_selector_layer);
        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.MY_CONTENT, null, InteractionType.SHOW.getValue(), null, false).
                build();
        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }

    @Test
    public void test42ShouldShowErrorMessageOnClickingShareIfNothingisSelected() {

        //Open Drawer in MyBackpack
        ViewAction.navaigateToDrawerItems(3);

        //Check Text
        Matcher.check(R.id.txt_header, R.string.label_nav_downloads);
        ViewAction.clickonView(R.id.btn_share);
        ViewAction.clickonView(R.id.btn_done);
        Matcher.isToastMessageDisplayed(R.string.msg_transfer_select_content);
    }

    @Test
    public void test43ShouldShowShareDialogonClickDone() {

        //Open Drawer in MyBackpack
        ViewAction.navaigateToDrawerItems(3);

        //Check Text
        Matcher.check(R.id.txt_header, R.string.label_nav_downloads);
        ViewAction.clickonView(R.id.btn_share);
        ViewAction.clickOnRecylerViewItem(R.id.rv_my_lessons_downloaded, 0);
        ViewAction.clickonView(R.id.btn_done);
        Matcher.check(R.id.txt_title, R.string.title_share_lesson);
    }

    @Test
    public void test44ShouldHidetheOverlayOnClickingBack() {

        //Open Drawer in MyBackpack
        ViewAction.navaigateToDrawerItems(3);

        //Check Text
        Matcher.check(R.id.txt_header, R.string.label_nav_downloads);
        ViewAction.clickonView(R.id.btn_share);
        pressBack();
        Matcher.checkInRecyclerViewItemVisibilityGone(R.id.rv_my_lessons_downloaded, 0, R.id.vw_selector_layer);
        Matcher.checkInRecyclerViewItemVisibilityGone(R.id.rv_my_lessons_downloaded, 1, R.id.vw_selector_layer);
        Matcher.checkInRecyclerViewItemVisibilityGone(R.id.rv_my_lessons_downloaded, 2, R.id.vw_selector_layer);

    }

    @Test
    public void test5ShouldOpenContentPlayerSuccesfully() {

        //Open Drawer in MyBackpack
        ViewAction.navaigateToDrawerItems(3);

        //Check Text
        Matcher.check(R.id.txt_header, R.string.label_nav_downloads);
        //Click on content item
        ViewAction.clickOnRecylerViewItem(R.id.rv_my_lessons_downloaded, 0);

        Assertion.assertContentPlayerIntent();

        pressBack();

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.MY_CONTENT, null, InteractionType.SHOW.getValue(), null, false).
                addGELaunchGameEvent(WELCOME_TO_GENIE_IDENTIFIER, "INTENT").
                addGEGameEndEvent(WELCOME_TO_GENIE_IDENTIFIER).
                build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }


    @Test
    public void test61shouldNavigateToContentDetailsOnClickingMoreViewDetails() {

        //Show More dialog
        openLayoutMore();

        // Wait for some time
        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        //Click on View Details
        ViewAction.clickonString(R.id.layout_view_details, R.string.action_dialog_view_details);
        intended(hasComponent(ContentDetailActivity.class.getName()));

        // Wait for some time
        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        // Asserts for the Lesson details string
        Matcher.check(R.id.txt_header, R.string.title_contentdetail);

        clearResource();

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.MY_CONTENT, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.CONTENT_DETAIL, EntityType.CONTENT, InteractionType.SHOW.getValue(), "do_30074541", false).
                build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);

    }

    @Test
    public void test62clickshouldShowShareDialogOnClickMoreShare() {
        //Show More dialog
        openLayoutMore();

        // Wait for some time
        waitFor(DateUtils.SECOND_IN_MILLIS * 10);

        //Click on View Details
        ViewAction.clickonString(R.id.layout_share, R.string.action_dialog_share);

        clearResource();

        //Assert for "Share as Link " layout
        Matcher.isVisible(R.id.layout_share_link);

        //Assert for "Share the file" layout
        Matcher.isVisible(R.id.layout_share_file);

        pressBack();
    }

    @Test
    public void test63shouldShowProgressReportActivityOnClickMoreProgress() {
        //Show More dialog
        openLayoutMore();

        // Wait for some time
        waitFor(DateUtils.SECOND_IN_MILLIS * 10);

        //Click on progress
        ViewAction.clickonString(R.id.layout_report_progress, R.string.action_dialog_progress);
        intended(hasComponent(ProgressReportsActivity.class.getName()));
        clearResource();

        //Assert for "Content details & Progress" string
        Matcher.check(R.id.txt_header, R.string.title_summarizer_content_details);

        //click on back
        ViewAction.clickonView(R.id.back_btn, R.id.toolbar_summerizer);

        //Assert for "My backpack" string
        Matcher.check(R.id.txt_header, R.string.label_nav_downloads);

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.MY_CONTENT, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SUMMARIZER_CONTENT_SUMMARY, EntityType.CONTENT_ID, InteractionType.SHOW.getValue(), WELCOME_TO_GENIE_IDENTIFIER, true).
                build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }

    @Test
    public void test63shouldShowFeedbackOptioninMoreAfterContentisPlayed() {
        GenieDBHelper.clearTelemetryTable();

        //Open MyDownloads
        ViewAction.navaigateToDrawerItems(3);

        //click on More item of content
        ViewAction.clickOnRecylerViewItem(R.id.rv_my_lessons_downloaded, 2, R.id.ib_more);

        // Wait for some time
        waitFor(DateUtils.SECOND_IN_MILLIS * 10);
        Matcher.isNotVisible(R.id.layout_feedback);
        clearResource();
        pressBack();
        ViewAction.clickOnRecylerViewItem(R.id.rv_my_lessons_downloaded, 2);
        Assertion.assertContentPlayerIntent();
        pressBack();
        ViewAction.clickOnRecylerViewItem(R.id.rv_my_lessons_downloaded, 2, R.id.ib_more);
        Matcher.isVisible(R.id.layout_feedback);
        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.MY_CONTENT, null, InteractionType.SHOW.getValue(), null, false).
                addGELaunchGameEvent(SLATE_IDENTIFIER, "INTENT").
                addGEGameEndEvent(SLATE_IDENTIFIER).
                build();
        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }

    @Test
    public void test64shouldGiveFeedbaclSuccessfully() {
        GenieDBHelper.clearTelemetryTable();

        //Open MyDownloads
        ViewAction.navaigateToDrawerItems(3);

        //click on More item of content
        ViewAction.clickOnRecylerViewItem(R.id.rv_my_lessons_downloaded, 2, R.id.ib_more);

        // Wait for some time
        waitFor(DateUtils.SECOND_IN_MILLIS * 10);

        //Click on feedback
        ViewAction.clickonString(R.id.layout_feedback, R.string.title_feedback);

        clearResource();

        //Assert for "Feedback" string
        Matcher.check(R.id.share, R.string.title_feedback);

        //Assert for "Feedback" string
        Matcher.isVisible(R.id.ratingFeedback);

        //click On comments
        ViewAction.clickonView(R.id.edt_comments);

        //Enter text
        ViewAction.typeText(R.id.edt_comments, mFeedbacktext);
        ViewAction.setRating(1);

        closeSoftKeyboard();

        //Click on submit
        ViewAction.clickonString(R.id.txt_submit, R.string.action_feedback_submit);
        Matcher.isToastMessageDisplayed(R.string.msg_feedback_successfull);

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.MY_CONTENT, null, InteractionType.SHOW.getValue(), null, false).
                addGEFeedbackEvent(SLATE_IDENTIFIER, TelemetryStageId.MY_CONTENT, "Content", mFeedbacktext, 1, "RATING").
                build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }

    @Test
    public void test65ShouldDeleteContentSuccessfully() {
        //Show More Dialog
        openLayoutMore();

        // Wait for some time
        waitFor(DateUtils.SECOND_IN_MILLIS * 10);

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


        RecyclerViewMatcher.assertRecyclerViewCount(R.id.rv_my_lessons_downloaded, 2);

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.MY_CONTENT, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.MY_CONTENT, TelemetryAction.DELETE_CONTENT_INITIATED, InteractionType.TOUCH.getValue(), WELCOME_TO_GENIE_IDENTIFIER, false).
                build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }

    @Test
    public void test65ShouldRefreshContentListIfDeletedFromContentDetails() {
        //Show More Dialog
        openLayoutMore();

        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        //Click on View Details
        ViewAction.clickonString(R.id.layout_view_details, R.string.action_dialog_view_details);
        intended(hasComponent(ContentDetailActivity.class.getName()));

        // Wait for some time
        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        // Asserts for the Lesson details string
        Matcher.check(R.id.txt_header, R.string.title_contentdetail);

        // Wait for some time
        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        ViewAction.clickonView(R.id.iv_delete);

        clearResource();

        //Assert if Yes layout is showing or not
        Matcher.isVisible(R.id.delete_yes);

        //Assert if No layout is showing or not
        Matcher.isVisible(R.id.delete_no);

        //Click on Yes
        ViewAction.clickonView(R.id.delete_yes);

        //Assert for content deletion toast
        Matcher.isToastMessageDisplayed(R.string.msg_content_deletion_sucessfull);

        pressBack();

        RecyclerViewMatcher.assertRecyclerViewCount(R.id.rv_my_lessons_downloaded, 1);

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.MY_CONTENT, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.CONTENT_DETAIL, EntityType.CONTENT, InteractionType.SHOW.getValue(), PLAY_WITH_TIPU_IDENTIFIER, false).
                addGEInteractEvent(TelemetryStageId.CONTENT_DETAIL, TelemetryAction.DELETE_CONTENT_INITIATED, InteractionType.TOUCH.getValue(), PLAY_WITH_TIPU_IDENTIFIER, false).
                build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }


    private void openLayoutMore() {
        //setTelemetryData();

        GenieDBHelper.clearTelemetryTable();
        //open Lesson
        ViewAction.navaigateToDrawerItems(3);
        // setTelemetryContentData();
        //Check text
        Matcher.check(R.id.txt_header, R.string.label_nav_downloads);
        //click on More item of content
        ViewAction.clickOnRecylerViewItem(R.id.rv_my_lessons_downloaded, 0, R.id.ib_more);
    }


}
