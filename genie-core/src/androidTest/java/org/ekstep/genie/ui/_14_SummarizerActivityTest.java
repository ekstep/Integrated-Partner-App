package org.ekstep.genie.ui;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.format.DateUtils;

import org.ekstep.genie.GenieTestBase;
import org.ekstep.genie.R;
import org.ekstep.genie.custom.action.ViewAction;
import org.ekstep.genie.custom.matcher.Matcher;
import org.ekstep.genie.model.ExpectedEventList;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.telemetry.enums.EntityType;
import org.ekstep.genie.ui.landing.LandingActivity;
import org.ekstep.genie.ui.progressreportdetail.ProgressReportDetailActivity;
import org.ekstep.genie.ui.progressreports.ProgressReportsActivity;
import org.ekstep.genie.util.EcarCopyUtil;
import org.ekstep.genie.util.GenieDBHelper;
import org.ekstep.genie.util.TelemetryVerifier;
import org.ekstep.genie.util.geniesdk.ImportExportUtil;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

/**
 * Created on 25-08-2016.
 *
 * @author swayangjit_gwl
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class _14_SummarizerActivityTest extends GenieTestBase {

    private static final String MULTIPLICATION4 = "Multiplication4.ecar";
    private static final String SUMMERIZED_CHILD = "Summarizedchild";
    private static final String SUMMERIZED_CHILD_EPAR = "Summarizedchild.epar";

    private static final String MULTIPLICATION4_TITLE = "Multiplication - 4 Times Table";
    private static final String MULTIPLICATION4_AVG_TIME = "00:00:41";

    private static final String SAMPLE_ECAR = "Download/" + MULTIPLICATION4;
    private static final String SAMPLE_EPAR = "Download/" + SUMMERIZED_CHILD_EPAR;

    @Rule
    public IntentsTestRule<LandingActivity> mActivityRule = new IntentsTestRule<>(LandingActivity.class, true, false);


    @Override
    public void setup() throws IOException {
        super.setup();

    }

    @Override
    public void tearDown() throws IOException {
        super.tearDown();
    }

    public void mocknLaunchLandingActivity() {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, LandingActivity.class);
        mActivityRule.launchActivity(intent);

    }

    private void goBack() {
        //click on content progress back
        ViewAction.clickonView(R.id.back_btn);
    }

    @Test
    public void test0CopyNecessoryFiles() {
        EcarCopyUtil.createFileFromAsset(SAMPLE_ECAR, DESTINATION);
        EcarCopyUtil.createFileFromAsset(SAMPLE_EPAR, DESTINATION);
    }

    @Test
    public void test1ShouldNavigateToChildSummarizer() {

        mocknLaunchLandingActivity();

        importContent();

        GenieDBHelper.deleteAllUserProfile();

        ImportExportUtil.importProfile(DESTINATION + File.separator + SUMMERIZED_CHILD_EPAR);

        ViewAction.navaigateToDrawerItems(2);

        waitFor(DateUtils.SECOND_IN_MILLIS * 5);
        GenieDBHelper.clearTelemetryTable();

        //Click on Recyclerview item "More"
        ViewAction.clickOnRecylerViewItem(R.id.recyclerview_user_list, 0, R.id.layout_more);

        //Click on "Progress" option
        ViewAction.clickonView(R.id.layout_report_progress);
        intended(hasComponent(ProgressReportsActivity.class.getName()));


        //Assert for "Child Details & Progress" string
        Matcher.check(R.id.txt_header, R.string.title_summarizer_profile_details);

        //Assert for "Summarizedchild"
        Matcher.checkWithText(R.id.txt_kid_name, SUMMERIZED_CHILD);

        //Assert the two items in Recyclerview
        Matcher.checkInRecyclerView(R.id.recycler_summerizer, 0, MULTIPLICATION4_TITLE);

        //click on Multiplication4 lesson
        ViewAction.clickOnRecylerViewItem(R.id.recycler_summerizer, 0);

        waitFor(DateUtils.SECOND_IN_MILLIS * 10);
        intended(hasComponent(ProgressReportDetailActivity.class.getName()));
        Matcher.isVisible(R.id.txt_content_name);

        //assert for content title and average time
        Matcher.checkWithText(R.id.txt_content_name, MULTIPLICATION4_TITLE);
        Matcher.checkWithText(R.id.txt_total_time, MULTIPLICATION4_AVG_TIME);

        //navigate back to content summarizer
        goBack();

        clearResource();

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SUMMARIZER_CHILD_SUMMARY, EntityType.CHILD_ID, InteractionType.SHOW.getValue(), "8834f91d-74ad-45f6-b276-d11058d099d8", true).
                addGEInteractEvent(TelemetryStageId.SUMMARIZER_CHILD_CONTENT_DETAILS, EntityType.CONTENT_ID, InteractionType.SHOW.getValue(), "do_30013504", true).
                build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);

    }

    @Test
    public void test2ShouldNavigateToContentSummarizer() {

        mocknLaunchLandingActivity();

        //open Lesson
        ViewAction.navaigateToDrawerItems(3);

        //Check text
        Matcher.check(R.id.txt_header, R.string.label_nav_downloads);

        //click on More item of content
        ViewAction.clickOnRecylerViewItem(R.id.rv_my_lessons_downloaded, 1, R.id.ib_more);

        // Wait for some time
        waitFor(DateUtils.SECOND_IN_MILLIS * 5);
        GenieDBHelper.clearTelemetryTable();

        //Click on progress
        ViewAction.clickonString(R.id.layout_report_progress, R.string.action_dialog_progress);

        //Assert for "Content details & Progress" string
        Matcher.check(R.id.txt_header, R.string.title_summarizer_content_details);

        //Assert for "Multiplication4"
        Matcher.checkWithText(R.id.txt_kid_name, MULTIPLICATION4_TITLE);

        //Assert the two items in Recyclerview
        Matcher.checkInRecyclerView(R.id.recycler_summerizer, 0, SUMMERIZED_CHILD);

        //click on Multiplication4 lesson
        ViewAction.clickOnRecylerViewItem(R.id.recycler_summerizer, 0);

        waitFor(DateUtils.SECOND_IN_MILLIS * 10);

        //assert for child name and average time
        Matcher.checkWithText(R.id.txt_content_name, SUMMERIZED_CHILD);
        Matcher.checkWithText(R.id.txt_total_time, MULTIPLICATION4_AVG_TIME);

        clearResource();

        //navigate back to content summarizer
        goBack();

        //Assert for "Content details & Progress" string
        Matcher.check(R.id.txt_header, R.string.title_summarizer_content_details);

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SUMMARIZER_CONTENT_SUMMARY, EntityType.CONTENT_ID, InteractionType.SHOW.getValue(), "do_30013504", true).
                addGEInteractEvent(TelemetryStageId.SUMMARIZER_CHILD_CONTENT_DETAILS, EntityType.CONTENT_ID, InteractionType.SHOW.getValue(), "8834f91d-74ad-45f6-b276-d11058d099d8", true).
                build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }

    @Test
    public void test3ShouldNavigateToEditChild() {

        mocknLaunchLandingActivity();

        // Navigate to ManageUser;01
        ViewAction.navaigateToDrawerItems(2);

        waitFor(DateUtils.SECOND_IN_MILLIS * 5);
        GenieDBHelper.clearTelemetryTable();

        //Click on Recyclerview item "More"
        ViewAction.clickOnRecylerViewItem(R.id.recyclerview_user_list, 0, R.id.layout_more);

        //Click on "Progress" option
        ViewAction.clickonView(R.id.layout_report_progress);

        //Assert for "Child Details & Progress" string
        Matcher.check(R.id.txt_header, R.string.title_summarizer_profile_details);

        //Assert for "Summarizedchild"
        Matcher.checkWithText(R.id.txt_kid_name, SUMMERIZED_CHILD);

        //Click on "Edit" option
        ViewAction.clickonView(R.id.txt_edit);

        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        //assert for edit child header text
        Matcher.check(R.id.txt_add_child, R.string.title_addchild_edit);

        clearResource();
        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SUMMARIZER_CHILD_SUMMARY, EntityType.CHILD_ID, InteractionType.SHOW.getValue(), "8834f91d-74ad-45f6-b276-d11058d099d8", true).
                addGEInteractEvent(TelemetryStageId.MANAGE_CHILDREN, TelemetryAction.ADD_CHILD_INITIATE, InteractionType.TOUCH.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_NAME, null, InteractionType.SHOW.getValue(), null, false).
                build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }

    @Test
    public void test4ShouldDeleteChild() {

        mocknLaunchLandingActivity();

        // Navigate to ManageUser;01
        ViewAction.navaigateToDrawerItems(2);

        waitFor(DateUtils.SECOND_IN_MILLIS * 5);
        GenieDBHelper.clearTelemetryTable();

        //Click on Recyclerview item "More"
        ViewAction.clickOnRecylerViewItem(R.id.recyclerview_user_list, 0, R.id.layout_more);

        //Click on "Progress" option
        ViewAction.clickonView(R.id.layout_report_progress);

        //Assert for "Child Details & Progress" string
        Matcher.check(R.id.txt_header, R.string.title_summarizer_profile_details);

        //Assert for "Summarizedchild"
        Matcher.checkWithText(R.id.txt_kid_name, SUMMERIZED_CHILD);

        //Click on "Delete" option
        ViewAction.clickonView(R.id.txt_delete);

        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        //Assert if Yes layout is showing or not
        Matcher.isVisible(R.id.delete_yes);

        //Assert if No layout is showing or not
        Matcher.isVisible(R.id.delete_no);

        //Click on Yes
        ViewAction.clickonString(R.id.delete_yes, R.string.label_dialog_yes);

        //Assert for content deletion toast
        Matcher.isToastMessageDisplayed(R.string.msg_children_profile_deletion_successful);


        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        //assert for edit child header text
        Matcher.isVisible(R.id.layout_no_User);

        clearResource();
    }

    private void importContent() {
        ImportExportUtil.importContent(mActivityRule.getActivity(), DESTINATION + File.separator + MULTIPLICATION4, new ImportExportUtil.IImport() {
            @Override
            public void onImportSuccess() {
                Assert.assertTrue(true);
            }

            @Override
            public void onImportFailure() {
            }

            @Override
            public void onOutDatedEcarFound() {

            }
        });
    }

}
