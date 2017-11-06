package org.ekstep.genie.ui;

import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.format.DateUtils;
import android.widget.DatePicker;

import org.ekstep.genie.GenieTestBase;
import org.ekstep.genie.R;
import org.ekstep.genie.custom.action.ViewAction;
import org.ekstep.genie.custom.matcher.Matcher;
import org.ekstep.genie.ui.settings.SettingsActivity;
import org.ekstep.genie.util.GenieDBHelper;
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
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**TODO: telemetry events.
 * Created by Sneha on 10/17/2017.
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class _62_AdvancedSettingsScreenTest extends GenieTestBase {

    @Rule
    public ActivityTestRule<SettingsActivity> mActivityRule = new ActivityTestRule<>(SettingsActivity.class);

    @Override
    public void setup() throws IOException {
        super.setup();
        GenieDBHelper.clearTelemetryTable();
    }

    @Override
    public void tearDown() throws IOException {
        super.tearDown();
    }

    private void navigateToDataSettingsScreen() {

        ViewAction.clickOnRecylerViewItem(R.id.rv_settings_master, 2);

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.title_advanced_settings);

//        ExpectedEventList expectedEventList = new ExpectedEventList.ExpectedEventListBuilder().
//                addGEInteractEvent(TelemetryStageId.SETTINGS_ADVANCED, null, InteractionType.SHOW.getValue(), null, false).
//                build();
//
//        TelemetryVerifier.verifyGEEvents(expectedEventList);

    }

    /**
     * Scenario: When user navigates to the advanced settings screen, and there are no tags in the screen, no tags view is displayed.
     * Given: When user navigates to the advanced settings screen.
     * When: And there are no tags in the screen.
     * Then: No tags view is displayed.
     */
    @Test
    public void _01_testShouldCheckForNoTagsScreen() {

        navigateToDataSettingsScreen();

        //check add tag fab visible
        Matcher.isVisible(R.id.fab_add_tag);

        //check no programs text visible
        Matcher.isVisibleText(R.string.msg_tag_not_available);

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.title_advanced_settings);
    }

    /**
     * Scenario: When user tries to create a tag, the tag name and date of the tag has to be validated. (should be alphanumeric)
     * Given: When user is in the advanced settings screen.
     * When: User tries to create a tag.
     * Then: Tag name and Date range of the tag has to be validated.
     */
    @Test
    public void _02_testShouldValidateTagForAllRequiredFields() {

        navigateToDataSettingsScreen();

        //check add tag fab visible
        ViewAction.clickonView(R.id.fab_add_tag);

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
        Matcher.isToastMessageDisplayed(R.string.error_tag_start_date_empty);

        //set start date
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
        Matcher.isToastMessageDisplayed(R.string.error_tag_end_date_empty);

        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        clearResource();

    }

    /**
     * Scenario: When user is in the advanced settings screen, user tries to create a tag with valid data, tag is successfully created.
     * Given: When user is in the advanced settings screen.
     * When: User tries to create a tag with valid data.
     * Then: Tag is successfully created.
     */
    @Test
    public void _03_testShouldAddNewTagSuccessfully() {

        navigateToDataSettingsScreen();

        //check add tag fab visible
        Matcher.isVisible(R.id.fab_add_tag);

        //click on fab add tag
        ViewAction.clickonView(R.id.fab_add_tag);

        //type text for tag name
        onView(withId(R.id.edt_tag_name)).perform(typeText("Pratham2016")).perform(closeSoftKeyboard());

        //enter text in Tag desc
        onView(withId(R.id.edt_tag_description)).perform(typeText("Pratham")).perform(closeSoftKeyboard());

        //Click on Start date Layout
        onView(withId(R.id.layout_start_date)).perform(scrollTo(), click());

        //Select start date
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2017, 10, 1));

        //Click on Ok button
        ViewAction.clickonView(android.R.id.button1);

        //Select end date
        onView(withId(R.id.layout_end_date)).perform(scrollTo(), click());

        //Select end date
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2017, 10, 23));

        //Click on Ok button
        ViewAction.clickonView(android.R.id.button1);

        //click on save
        ViewAction.clickonView(R.id.layout_save);

        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        clearResource();

        //assert tag added successfully msg
        Matcher.isToastMessageDisplayed(R.string.msg_tag_added_sussessfully);

        waitFor(DateUtils.SECOND_IN_MILLIS * 2);

        clearResource();

//        ExpectedEventList expectedEventList = new ExpectedEventList.ExpectedEventListBuilder().
//                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, false).
//                addGEInteractEvent(TelemetryStageId.SETTINGS_ADVANCED, null, InteractionType.SHOW.getValue(), null, false).
//                addGEInteractEvent(TelemetryStageId.ADD_NEW_TAG, TelemetryAction.ADD_TAG_MANUAL, InteractionType.TOUCH.getValue(), null, false).
//                addGEInteractEvent(TelemetryStageId.SETTINGS_ADVANCED, null, InteractionType.SHOW.getValue(), null, false).
//                build();
//
//        TelemetryVerifier.verifyGEEvents(expectedEventList);
    }

    /**
     * Scenario: When user clicks on the edit option of the tag and edits the tag with valid data, tag is successfully updated.
     * Given: When user in the advanced settings screen.
     * When: User clicks on the edit option of the tag and edits the tag with valid data.
     * Then: Tag is successfully updated.
     */
    @Test
    public void _04_testShouldEditTag() {

        navigateToDataSettingsScreen();

        ViewAction.clickOnRecylerViewItem(R.id.recylerview_tags, 0, R.id.label_edit);

        //enter text in Tag desc
        onView(withId(R.id.edt_tag_description)).perform(replaceText("Pratham description")).perform(closeSoftKeyboard());

        //Click on Start date Layout
        onView(withId(R.id.layout_start_date)).perform(scrollTo(), click());

        //Select start date
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2016, 9, 17));

        //Click on Ok button
        ViewAction.clickonView(android.R.id.button1);

        //click on save
        ViewAction.clickonView(R.id.layout_save);

        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        clearResource();

        //assert tag updated successfully msg
        Matcher.isToastMessageDisplayed(R.string.msg_tag_updated_successfully);

        waitFor(DateUtils.SECOND_IN_MILLIS * 2);

        clearResource();
    }

    /**
     * Scenario: When user clicks on the delete option of the tag, tag gets deleted.
     * Given: When user in the advanced settings screen.
     * When: User clicks on the delete option of the tag.
     * Then: Tag gets deleted.
     */
    @Test
    public void _05_testShouldDeleteTag() {

        navigateToDataSettingsScreen();

        //wait for 5 sec
        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        //click on delete in tags
        ViewAction.clickOnRecylerViewItem(R.id.recylerview_tags, 0, R.id.label_delete);

        //wait for 5 sec
        waitFor(DateUtils.SECOND_IN_MILLIS * 2);

        clearResource();

//        ExpectedEventList expectedEventList = new ExpectedEventList.ExpectedEventListBuilder().
//                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, false).
//                addGEInteractEvent(TelemetryStageId.SETTINGS_ADVANCED, null, InteractionType.SHOW.getValue(), null, false).
//                build();
//
//        TelemetryVerifier.verifyGEEvents(expectedEventList);
    }

}
