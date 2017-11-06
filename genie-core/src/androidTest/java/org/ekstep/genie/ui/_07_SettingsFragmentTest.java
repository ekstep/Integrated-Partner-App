package org.ekstep.genie.ui;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.ekstep.genie.GenieTestBase;
import org.ekstep.genie.R;
import org.ekstep.genie.custom.action.ViewAction;
import org.ekstep.genie.custom.matcher.Matcher;
import org.ekstep.genie.custom.matcher.RecyclerViewMatcher;
import org.ekstep.genie.model.ExpectedEventList;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.ui.landing.LandingActivity;
import org.ekstep.genie.util.GenieDBHelper;
import org.ekstep.genie.util.TelemetryVerifier;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;

/**
 * Created by swayangjit_gwl on 08-08-2016.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class _07_SettingsFragmentTest extends GenieTestBase {

    @Rule
    public ActivityTestRule<LandingActivity> mActivityRule = new ActivityTestRule<>(LandingActivity.class);

    @Override
    public void setup() throws IOException {
        super.setup();
        GenieDBHelper.clearTelemetryTable();
    }

    @Override
    public void tearDown() throws IOException {
        super.tearDown();
    }

    private void navigateToSettings() {

        //Navigate to Settings
        ViewAction.navaigateToDrawerItems(5);

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.label_nav_settings);

    }

    private void clickBackButton() {
        //click on Toolbar back button
        ViewAction.clickonView(R.id.back_btn);
    }


    @Test
    public void test1ShouldNavigateToSettingsFragment() {
        //Clear Telemetry table
        GenieDBHelper.clearTelemetryTable();
        navigateToSettings();
        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, true).
                build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }


    @Test
    public void test3ShouldNavigateToLanguageFragment() {
        navigateToSettings();

        //click on Language
        ViewAction.clickonView(R.id.layout_language);

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.title_select_language);

        clickBackButton();

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.label_nav_settings);

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, TelemetryAction.LANGUAGE_SETTINGS_INITIATE, InteractionType.TOUCH.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, true).
                build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }

    @Test
    public void test4ShouldNavigateToDataSettingsFragment() {
        navigateToSettings();

        //click on Data Settings
        ViewAction.clickonView(R.id.sync_options);
        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.title_sync_data_setting);

        clickBackButton();

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.label_nav_settings);

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, true).
                build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }

    @Test
    public void test5ShouldNavigateToAdvancedSettingsFragment() {
        navigateToSettings();

        //click on Advanced settings
        ViewAction.clickonView(R.id.layout_advanced_settings);

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.title_advanced_settings);

        clickBackButton();

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.label_nav_settings);

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.SETTINGS_ADVANCED, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, true).
                build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }

    @Test
    public void test6ShouldNavigateToAboutFragment() {
        navigateToSettings();

        //click on About
        ViewAction.clickonView(R.id.layout_about);

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.title_about);

        clickBackButton();

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.label_nav_settings);
    }

    @Test
    /**
     * Scenario - Click on the item in the list of options shown in the app list
     */
    public void test7ClickOnItemInShareAppsOptionsList() throws InterruptedException {
        navigateToSettings();

        ViewAction.clickonView(R.id.settings_btn_share);

//        //asserting the title text
//        Matcher.check(R.id.txt_title, R.string.title_share_details);
//
//        File genieSupportDirectory = FileHandler.getRequiredDirectory(Environment.getExternalStorageDirectory(), SharePresenter.GENIE_SUPPORT_DIRECTORY);
//        String filePath = genieSupportDirectory + "/" + SharePresenter.GENIE_SUPPORT_FILE;
//        String versionsAndOpenTimes = FileHandler.readFile(filePath);
//
//        verifyIntentDataIsSameAsPassedData(versionsAndOpenTimes);


        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, true).
                build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }

    private void verifyIntentDataIsSameAsPassedData(String versionsAndOpenTimes) {
        RecyclerViewMatcher.checkTheDataPassedToTheItem(R.id.resolver_recyclerview_file, versionsAndOpenTimes);
    }



}
