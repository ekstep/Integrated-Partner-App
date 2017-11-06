package org.ekstep.genie.ui;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.ekstep.genie.GenieTestBase;
import org.ekstep.genie.R;
import org.ekstep.genie.custom.action.ViewAction;
import org.ekstep.genie.custom.matcher.Matcher;
import org.ekstep.genie.ui.settings.SettingsActivity;
import org.ekstep.genie.util.GenieDBHelper;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;

/**
 * TODO: telemetry events.
 * Created by Sneha on 10/17/2017.
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class _65_StorageSettingsTest extends GenieTestBase {

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

        ViewAction.clickOnRecylerViewItem(R.id.rv_settings_master, 3);

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.title_storage_settings);

//        ExpectedEventList expectedEventList = new ExpectedEventList.ExpectedEventListBuilder().
//                addGEInteractEvent(TelemetryStageId.SETTINGS_ADVANCED, null, InteractionType.SHOW.getValue(), null, false).
//                build();
//
//        TelemetryVerifier.verifyGEEvents(expectedEventList);

    }

    @Test
    public void _1_testShouldCheckForDefaultSelectedStorage() {

        navigateToDataSettingsScreen();

        Matcher.check(R.id.txt_header, R.string.title_storage_settings);

        Matcher.isCompoundButtonChecked(R.id.mobile_btn);
    }

    @Test
    public void _2_testShouldCancelMovingContentToSdcard() {

        navigateToDataSettingsScreen();

        ViewAction.clickonView(R.id.sdcard_btn);

        Matcher.isVisibleText(R.string.title_sdcard_default_storage);

        ViewAction.clickonString(R.string.label_dialog_cancel);
    }

    @Test
    public void _3_testShouldCheckForSdcardAsDefaultStorage() {

        navigateToDataSettingsScreen();

        ViewAction.clickonView(R.id.sdcard_btn);

        Matcher.isVisibleText(R.string.title_sdcard_default_storage);

        ViewAction.clickonString(R.string.title_content_player_continue);

        Matcher.isNotVisibleText(R.string.title_move_content);

        Matcher.isCompoundButtonChecked(R.id.sdcard_btn);
    }

    @Test
    public void _4_testShouldCheckForMobileAsDefaultStorage() {
        navigateToDataSettingsScreen();

        //click on mobile button
        ViewAction.clickonView(R.id.mobile_btn);

        //check text
        Matcher.isVisibleText(R.string.title_device_default_storage);

        //click on continue
        ViewAction.clickonString(R.string.title_content_player_continue);

        // check text visibility
        Matcher.isNotVisibleText(R.string.title_move_content);

        //check if mobile is selected
        Matcher.isCompoundButtonChecked(R.id.mobile_btn);
    }

    @Test
    public void _5_testShouldClickOnManageOthers() {

        navigateToDataSettingsScreen();

        //click on manage others button
        ViewAction.clickonView(R.id.manage_others);

        //check for the "storage settings" text in mobile settings screen.
//        Matcher.check(R.id.txt_header, R.string.title_storage_settings);
    }

    @Test
    public void _6_testShouldClickOnManageGenie() {

        navigateToDataSettingsScreen();

        //click on manage genie button
        ViewAction.clickonView(R.id.manage_genie);

//        //check download screen visible
//        Matcher.check(R.id.txt_header, R.string.title_downloads);

    }
}
