package org.ekstep.genie.ui;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.ekstep.genie.GenieTestBase;
import org.ekstep.genie.R;
import org.ekstep.genie.custom.action.ViewAction;
import org.ekstep.genie.ui.landing.LandingActivity;
import org.ekstep.genie.util.GenieDBHelper;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static android.support.test.espresso.Espresso.onView;


/**
 * Created on 8/8/2016.
 *
 * @author anil
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@LargeTest
@RunWith(AndroidJUnit4.class)

public class _17_TransferFragmentTest extends GenieTestBase {
    private static final int TRANSFER_POSITION = 4;

    @Rule
    public ActivityTestRule<LandingActivity> mActivityTestRule = new ActivityTestRule<>(LandingActivity.class);

    @Override
    public void setup() throws IOException {
        super.setup();
        GenieDBHelper.clearTelemetryTable();
    }

    /**
     * Navigate to transfer screen.
     */
    private void navigateToTransferFragment() {
        // Click on menu berger
        onView(ViewMatchers.withId(R.id.img_berger_menu)).perform(ViewActions.click());

        // Click on Transfer
        onView(ViewMatchers.withId(R.id.list_drawer)).perform(RecyclerViewActions.actionOnItemAtPosition(TRANSFER_POSITION, ViewActions.click()));
    }

    private void assertRowState(int checkedRowId, boolean isClicked) {

        // Click on Lessons row
        if (isClicked) {
            onView(ViewMatchers.withId(checkedRowId)).perform(ViewActions.click());
        }

        switch (checkedRowId) {
            case R.id.chk_txt_telemetry:
                // Telemetry row is checked
                onView(ViewMatchers.withId(R.id.chk_txt_telemetry)).check(ViewAssertions.matches(ViewMatchers.isChecked()));

                // Profile row is not checked
                onView(ViewMatchers.withId(R.id.chk_txt_profile)).check(ViewAssertions.matches(ViewMatchers.isNotChecked()));

                // Lessons row is not checked
                onView(ViewMatchers.withId(R.id.chk_txt_content)).check(ViewAssertions.matches(ViewMatchers.isNotChecked()));
                break;

            case R.id.chk_txt_profile:
                // Telemetry row should not checked
                onView(ViewMatchers.withId(R.id.chk_txt_telemetry)).check(ViewAssertions.matches(ViewMatchers.isNotChecked()));

                // Profile row should checked
                onView(ViewMatchers.withId(R.id.chk_txt_profile)).check(ViewAssertions.matches(ViewMatchers.isChecked()));

                // Lessons row should not checked
                onView(ViewMatchers.withId(R.id.chk_txt_content)).check(ViewAssertions.matches(ViewMatchers.isNotChecked()));
                break;

            case R.id.chk_txt_content:
                // Telemetry row should not checked
                onView(ViewMatchers.withId(R.id.chk_txt_telemetry)).check(ViewAssertions.matches(ViewMatchers.isNotChecked()));

                // Profile row should not checked
                onView(ViewMatchers.withId(R.id.chk_txt_profile)).check(ViewAssertions.matches(ViewMatchers.isNotChecked()));

                // Lessons row should checked
                onView(ViewMatchers.withId(R.id.chk_txt_content)).check(ViewAssertions.matches(ViewMatchers.isChecked()));
                break;

            default:
                break;
        }
    }

    @Test
    public void test2ShouldNavigateToTransferFragment() {
        navigateToTransferFragment();

        // Check header text
        onView(ViewMatchers.withId(R.id.txt_header)).check(ViewAssertions.matches(ViewMatchers.withText(R.string.label_nav_transfer)));

        // Transfer button
        onView(ViewMatchers.withId(R.id.layout_export)).check(ViewAssertions.matches(ViewMatchers.withText(R.string.label_nav_transfer)));

        // Telemetry row
        onView(ViewMatchers.withId(R.id.chk_txt_telemetry)).check(ViewAssertions.matches(ViewMatchers.withText(R.string.title_trasfer_telemtry)));

        // Profile row
        onView(ViewMatchers.withId(R.id.chk_txt_profile)).check(ViewAssertions.matches(ViewMatchers.withText(R.string.title_trasfer_profile)));

        // Lessons row
        onView(ViewMatchers.withId(R.id.chk_txt_content)).check(ViewAssertions.matches(ViewMatchers.withText(R.string.title_trasfer_content)));

        assertRowState(R.id.chk_txt_telemetry, false);
    }

    @Test
    public void test3ShouldTransferTelemetry() {
        navigateToTransferFragment();

        assertRowState(R.id.chk_txt_telemetry, true);

//        onView(ViewMatchers.withId(R.id.layout_export)).perform(ViewActions.click());

//        onView(ViewMatchers.withText(R.string.share)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

//        Intents.intended(IntentMatchers.hasAction(Intent.ACTION_SEND));
//        Espresso.pressBack();

//        onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack());


    }

    @Test
    public void test4ShouldTransferProfile() {

        addSingleChild();

        addSingleChild();

        navigateToTransferFragment();

        assertRowState(R.id.chk_txt_profile, true);
        //Click on Recyclerview item 0
        ViewAction.clickOnRecylerViewItem(R.id.recyclerview_profile, 0);
        //Click on Recyclerview item 1
        ViewAction.clickOnRecylerViewItem(R.id.recyclerview_profile, 1);
        //Click on Transfer icon
        onView(ViewMatchers.withId(R.id.layout_export)).perform(ViewActions.click());

//        int gmailIconPosition = CommonUtil.getGmailPosition(mActivityTestRule.getActivity(), true);
//
//        ViewAction.clickOnRecylerViewChild(R.id.resolver_recyclerview_file, gmailIconPosition, R.id.title);


//
//        ExpectedEventList expectedEventList = new ExpectedEventList.ExpectedEventListBuilder().
//                addGEInteractEvent(TelemetryStageId.SETTINGS_TRANSFER, TelemetryAction.SHARE_PROFILE_SUCCESS, InteractionType.OTHER.getValue(), null, true).
//                build();
//
//        TelemetryVerifier.verifyGEEvents(expectedEventList);


        //delete all user profiles...
        GenieDBHelper.deleteAllUserProfile();

    }

    @Test
    public void test5ShouldTransferLessons() {

        navigateToTransferFragment();

        assertRowState(R.id.chk_txt_content, true);

        //Click on Recyclerview item 0
        ViewAction.clickOnRecylerViewItem(R.id.recyclerview_content, 0);

        //Click on Transfer icon
        onView(ViewMatchers.withId(R.id.layout_export)).perform(ViewActions.click());

        //Click in Gmail app to start transfer
//        onView(withRecyclerView(R.id.resolver_recyclerview_file).atPositionOnView(CommonUtil.getGmailPosition(mActivityTestRule.getActivity(), true), R.id.title)).perform(click());

//        ExpectedEventList expectedEventList = new ExpectedEventList.ExpectedEventListBuilder().
//                addGEInteractEvent(TelemetryStageId.SETTINGS_TRANSFER, TelemetryAction.SHARE_CONTENT_INITIATED, InteractionType.TOUCH.getValue(), null, false).
//                addGEInteractEvent(TelemetryStageId.SETTINGS_TRANSFER, TelemetryAction.SHARE_CONTENT_SUCCESS, InteractionType.OTHER.getValue(), null, false).
//                addGETransfer(1, CommonUtil.Constants.IMPORT_DATATYPE, CommonUtil.Constants.EXPORT_DIRECTION)
//                .build();
//
//        TelemetryVerifier.verifyGEEvents(expectedEventList);


    }
}
