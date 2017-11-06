package org.ekstep.genie.ui;

import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.text.format.DateUtils;
import android.widget.DatePicker;

import org.ekstep.genie.GenieTestBase;
import org.ekstep.genie.R;
import org.ekstep.genie.custom.action.ViewAction;
import org.ekstep.genie.custom.matcher.Matcher;
import org.ekstep.genie.model.ExpectedEventList;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.ui.landing.LandingActivity;
import org.ekstep.genie.util.GenieDBHelper;
import org.ekstep.genie.util.TelemetryVerifier;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.hamcrest.Matchers;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by GoodWorkLabs on 10-08-2016.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class _11_AdvancedSettingsFragmentTest extends GenieTestBase {

    @Rule
    public ActivityTestRule<LandingActivity> mActivityRule = new ActivityTestRule<>(LandingActivity.class);

    @Override
    public void setup() throws IOException {
        super.setup();
        GenieDBHelper.clearTelemetryTable();
    }

    private void navigateToAdvancedSettings() {

        // Navigate to Settings
        ViewAction.navaigateToDrawerItems(5);

        //Asserting the title text
        Matcher.check(R.id.txt_header, R.string.label_nav_settings);

        //click on Advanced Settings
        ViewAction.clickonView(R.id.layout_advanced_settings);

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.title_advanced_settings);
    }

    private void clickBackButton() {
        //click on Toolbar back button
        ViewAction.clickonView(R.id.back_btn);
    }


    @Test
    public void test1ShouldNavigateToAdvancedSettingsAndComeBack() {

        navigateToAdvancedSettings();

        clickBackButton();

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.label_nav_settings);

        ExpectedEventList expectedEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_ADVANCED, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, false).
                build();

        TelemetryVerifier.verifyGEEvents(expectedEventList);
    }

    @Test
    public void test2ShouldShowNoTagScreen() {
        navigateToAdvancedSettings();

        //asserting the no tag layout
        Matcher.isVisible(R.id.layout_no_tag);

        //click on Center FAB button
        ViewAction.clickonView(R.id.fab_add_tagCenter);

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.title_tag_add_new);

        clickBackButton();

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.title_advanced_settings);

        ExpectedEventList expectedEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_ADVANCED, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_ADVANCED, null, InteractionType.SHOW.getValue(), null, false).
                build();

        TelemetryVerifier.verifyGEEvents(expectedEventList);
    }

    @Test
    public void test3ShouldShowAllAddNewTagValidations() {
        navigateToAdvancedSettings();

        //click on Center FAB button
        ViewAction.clickonView(R.id.fab_add_tagCenter);

        //click on save button
        ViewAction.clickonView(R.id.layout_save);

        //assert for Tag name null validation
        Matcher.isError(R.id.edt_tag_name, "Program code can't be empty");

        //enter text in Tag name
        onView(withId(R.id.edt_tag_name)).perform(typeText("Prath")).perform(closeSoftKeyboard());

        //click on save button
        ViewAction.clickonView(R.id.layout_save);

        //assert for Tag name limit validation
        Matcher.isError(R.id.edt_tag_name, "Program code should be of 6 to 12 characters");

        //enter text in Tag name
        ViewAction.typeText(R.id.edt_tag_name, "dsdsd@!*");

        //click on save button
        ViewAction.clickonView(R.id.layout_save);

        //assert for Tag name limit validation
        Matcher.isError(R.id.edt_tag_name, "Program code should be alphanumeric");

        //enter text in Tag name
        ViewAction.typeText(R.id.edt_tag_name, "Pratham2016");

        //click on save button
        ViewAction.clickonView(R.id.layout_save);

        //assert for start date empty validation
//        Matcher.isError(R.id.txt_start_date, "Start date can't be empty");
        Matcher.isToastMessageDisplayed(R.string.error_tag_start_date_empty);

        //set start date
//        onView(withId(R.id.txt_start_date)).perform(scrollTo(), ViewAction.setTextInTextView("2016-10-15"));
        //Click on Start date Layout
        onView(ViewMatchers.withId(R.id.layout_start_date)).perform(scrollTo(), click());

        //Select start date
        onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2016, 10, 15));

        //Click on Ok button
        ViewAction.clickonView(android.R.id.button1);

        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        //click on save button
        ViewAction.clickonView(R.id.layout_save);

        clearResource();

        //assert for start date empty validation
//        Matcher.isError(R.id.txt_end_date, "End date can't be empty");
        Matcher.isToastMessageDisplayed(R.string.error_tag_end_date_empty);

        ExpectedEventList expectedEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_ADVANCED, null, InteractionType.SHOW.getValue(), null, false).
                build();

        TelemetryVerifier.verifyGEEvents(expectedEventList);
    }

    @Test
    public void test4ShouldAddTagSuccefully() {
        navigateToAdvancedSettings();

        //click on Center FAB button
        ViewAction.clickonView(R.id.fab_add_tagCenter);

        //enter text in Tag name
        onView(withId(R.id.edt_tag_name)).perform(typeText("Pratham2016")).perform(closeSoftKeyboard());

        //enter text in Tag desc
        onView(withId(R.id.edt_tag_description)).perform(typeText("Pratham")).perform(closeSoftKeyboard());

        //Click on Start date Layout
        onView(withId(R.id.layout_start_date)).perform(scrollTo(), click());

        //Select start date
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2016, 10, 15));

        //Click on Ok button
        ViewAction.clickonView(android.R.id.button1);

        //Click on Start date Layout
        onView(withId(R.id.layout_start_date)).perform(scrollTo(), click());

        //Select start date
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2016, 9, 15));

        //Click on Ok button
        ViewAction.clickonView(android.R.id.button1);


        //Select end date
        onView(withId(R.id.layout_end_date)).perform(scrollTo(), click());

        //Select end date
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2016, 10, 30));

        //Click on Ok button
        ViewAction.clickonView(android.R.id.button1);

        //Select end date
        onView(withId(R.id.layout_end_date)).perform(scrollTo(), click());

        //Select end date
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2016, 9, 30));

        //Click on Ok button
        ViewAction.clickonView(android.R.id.button1);

        //click on save
        ViewAction.clickonView(R.id.layout_save);

        //assert tag added successfully msg
        Matcher.isToastMessageDisplayed(R.string.msg_tag_added_sussessfully);

        ExpectedEventList expectedEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_ADVANCED, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.ADD_NEW_TAG, TelemetryAction.ADD_TAG_MANUAL, InteractionType.TOUCH.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_ADVANCED, null, InteractionType.SHOW.getValue(), null, false).
                build();

        TelemetryVerifier.verifyGEEvents(expectedEventList);

    }

    @Test
    public void test5ShouldAddTag2() {
        navigateToAdvancedSettings();

        //click on FAB button
        ViewAction.clickonView(R.id.fab_add_tag);

        //enter text in Tag name
        onView(withId(R.id.edt_tag_name)).perform(typeText("Akshara2016")).perform(closeSoftKeyboard());

        //enter text in Tag desc
        onView(withId(R.id.edt_tag_description)).perform(typeText("Akshara")).perform(closeSoftKeyboard());

        //set start date
        onView(withId(R.id.txt_start_date)).perform(scrollTo(), ViewAction.setTextInTextView("2016-10-15"));

        //set end date
        onView(withId(R.id.txt_end_date)).perform(scrollTo(), ViewAction.setTextInTextView("2016-09-30"));

        //click on save
        onView(withId(R.id.layout_save)).perform(click());

        ViewAction.clickonView(R.id.layout_save);
        //asserting for invalid date range

        Matcher.isToastMessageDisplayed(R.string.error_tag_invalid_date_range);

        //set start date
        onView(withId(R.id.txt_start_date)).perform(scrollTo(), ViewAction.setTextInTextView("2016-10-15"));

        //set end date
        onView(withId(R.id.txt_end_date)).perform(scrollTo(), ViewAction.setTextInTextView("2016-10-30"));

        //click on save
        ViewAction.clickonView(R.id.layout_save);

        ExpectedEventList expectedEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_ADVANCED, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.ADD_NEW_TAG, TelemetryAction.ADD_TAG_MANUAL, InteractionType.TOUCH.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_ADVANCED, null, InteractionType.SHOW.getValue(), null, false).
                build();

        TelemetryVerifier.verifyGEEvents(expectedEventList);
    }


    @Test
    public void test7ShouldShouldDeleteTagSuccessfully() {
        navigateToAdvancedSettings();

        //click on edit button
        ViewAction.clickonView(R.id.layout_edit);

        //Assert for Done button visibility
        Matcher.isVisible(R.id.layout_done);

        //click on done button
        ViewAction.clickonView(R.id.layout_done);

        //Assert for Edit button visibility
        Matcher.isVisible(R.id.layout_edit);

        //click on Edit button
        ViewAction.clickonView(R.id.layout_edit);

        //click on delete button of particular item
        ViewAction.clickOnRecylerViewItem(R.id.recylerview_tags, 0, R.id.layout_delete);

        ViewAction.clickOnRecylerViewItem(R.id.recylerview_tags, 0, R.id.layout_delete);

        //Assert for Edit button visibility
        Matcher.isVisible(R.id.fab_add_tagCenter);

        ExpectedEventList expectedEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_ADVANCED, null, InteractionType.SHOW.getValue(), null, false).
                build();

        TelemetryVerifier.verifyGEEvents(expectedEventList);

    }
}
