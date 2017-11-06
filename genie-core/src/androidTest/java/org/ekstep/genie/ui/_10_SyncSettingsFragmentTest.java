package org.ekstep.genie.ui;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.ekstep.genie.GenieTestBase;
import org.ekstep.genie.R;
import org.ekstep.genie.custom.action.ViewAction;
import org.ekstep.genie.custom.matcher.Matcher;
import org.ekstep.genie.model.ExpectedEventList;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.ui.landing.LandingActivity;
import org.ekstep.genie.util.GenieDBHelper;
import org.ekstep.genie.util.SampleData;
import org.ekstep.genie.util.TelemetryVerifier;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;


/**
 * Created by swayangjit on 09-08-2016.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class _10_SyncSettingsFragmentTest extends GenieTestBase {

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

    private void navigateToSyncSettings() {
        //Navigate to Settings
        ViewAction.navaigateToDrawerItems(5);

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.label_nav_settings);

        //click on Data Settings
        ViewAction.clickonView(R.id.sync_options);
        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.title_sync_data_setting);

        //click on Sync Settings
        ViewAction.clickonView(R.id.layout_sync_options);

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.title_sync_settings);
    }

    private void shouldClickBackButton() {
        //click on Toolbar back button
        ViewAction.clickonView(R.id.back_btn);
    }

    private void shouldClickSyncNow() {
        mMockServer.mockHttpResponse(SampleData.emptyResponse("ekstep.telemetry"), 200);
        mMockServer.mockHttpResponse(SampleData.emptyResponse("ekstep.telemetry"), 200);
        mMockServer.mockHttpResponse(SampleData.emptyResponse("ekstep.telemetry"), 200);
        mMockServer.mockHttpResponse(SampleData.emptyResponse("ekstep.telemetry"), 200);
        mMockServer.mockHttpResponse(SampleData.emptyResponse("ekstep.telemetry"), 200);
        mMockServer.mockHttpResponse(SampleData.emptyResponse("ekstep.telemetry"), 200);

        //clicking "sync now" on sync now dialog
        ViewAction.clickonString(R.string.action_sync_now);

    }

    @Test
    public void test1ShouldGotoSyncSettingsAndGoBack() {
        navigateToSyncSettings();

        Matcher.isCompoundButtonChecked(R.id.auto_sync_using_wifi_and_cellular);

        shouldClickBackButton();

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.title_sync_data_setting);

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_DATA_USAGE, null, InteractionType.SHOW.getValue(), null, false).
                build();
        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);

    }

    @Test
    public void test2ShouldSelectManualSync() {
        navigateToSyncSettings();

        //select manual sync
        ViewAction.clickonView(R.id.sync_manual);

        //navigate to settings fragment
        shouldClickBackButton();
        shouldClickBackButton();

        //asserting the current sync setting displayed in settings fragment
        Matcher.check(R.id.selected_sync_setting, R.string.label_sync_manual);

        ViewAction.clickonView(R.id.sync_options);
        ViewAction.clickonView(R.id.layout_sync_options);
        Matcher.isCompoundButtonChecked(R.id.sync_manual);
        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_DATA_USAGE, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_DATA_USAGE, null, InteractionType.SHOW.getValue(), null, false).
                build();
        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);

    }

    @Test
    public void test3AutomaticOverInternetSyncShouldbeSelected() {

        ViewAction.clickonView(R.id.is_sync_now);

        //click on OK button
        ViewAction.clickonString(R.string.label_home_sync_later);

        navigateToSyncSettings();

        Matcher.isCompoundButtonChecked(R.id.auto_sync_using_wifi_and_cellular);

        //select Automatic over Internet sync
        ViewAction.clickonView(R.id.sync_manual);

        //navigate to settings fragment
        shouldClickBackButton();
        shouldClickBackButton();

        //asserting the current sync setting displayed in settings fragment
        Matcher.check(R.id.selected_sync_setting, R.string.label_sync_manual);

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_DATA_USAGE, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, false).
                build();
        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);

    }

    @Test
    public void test4ShouldSelectAutomaticOverInternetSync() {

        startMockServer();

        shouldClickSyncNow();

        //asserting sync success message in success dialog
        Matcher.isVisibleText(R.string.msg_sync_successfull);

        //click on OK button
        ViewAction.clickonString(R.string.label_dialog_ok);

        navigateToSyncSettings();


        //select Automatic over Internet sync
        ViewAction.scrollANDClickView(R.id.auto_sync_using_wifi_and_cellular);


        //navigate to settings fragment
        shouldClickBackButton();
        shouldClickBackButton();

        //asserting the current sync setting displayed in settings fragment
        Matcher.check(R.id.selected_sync_setting, R.string.label_auto_sync_using_wifi_and_cellular);

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.GENIE_HOME, TelemetryAction.MANUAL_SYNC_SUCCESS, InteractionType.OTHER.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_DATA_USAGE, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, false).
                build();
        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);

        shutDownMockServer();


    }

    @Test
    public void test5ShouldSelectAutomaticOverWifiSync() {
        navigateToSyncSettings();

        //select Automatic over WIFI sync
        ViewAction.clickonView(R.id.auto_sync_using_wifi);

        //navigate to settings fragment
        shouldClickBackButton();
        shouldClickBackButton();

        //asserting the current sync setting displayed in settings fragment
        Matcher.check(R.id.selected_sync_setting, R.string.label_auto_sync_using_wifi);

        ViewAction.clickonView(R.id.sync_options);

        //click on Sync Settings
        ViewAction.clickonView(R.id.layout_sync_options);

        Matcher.isCompoundButtonChecked(R.id.auto_sync_using_wifi);

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_DATA_USAGE, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_DATA_USAGE, null, InteractionType.SHOW.getValue(), null, false).
                build();
        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);

        //Reset it
        ViewAction.clickonView(R.id.auto_sync_using_wifi_and_cellular);

    }

}
