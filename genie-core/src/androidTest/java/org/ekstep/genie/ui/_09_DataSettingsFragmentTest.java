package org.ekstep.genie.ui;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.text.format.DateUtils;

import org.ekstep.genie.CoreApplication;
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
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.TelemetryStat;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.ekstep.genieservices.commons.utils.DateUtil;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;


/**
 * Created by GoodWorkLabs on 08-08-2016.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class _09_DataSettingsFragmentTest extends GenieTestBase {

    @Rule
    public ActivityTestRule<LandingActivity> mActivityRule = new ActivityTestRule<>(LandingActivity.class);

    @Override
    public void setup() throws IOException {
        super.setup();

        GenieDBHelper.clearTelemetryTable();
        startMockServer();
    }

    @Override
    public void tearDown() throws IOException {
        super.tearDown();
        shutDownMockServer();
    }

    private void shouldNavigateToDataSettings() {

        //Navigate to Settings
        ViewAction.navaigateToDrawerItems(5);

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.label_nav_settings);

        //click on Data Settings
        ViewAction.clickonView(R.id.sync_options);

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.title_sync_data_setting);

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

        //click on Sync Now
        ViewAction.clickonView(R.id.layout_sync_now);


    }

    @Test
    public void test1ShouldUpdateLastSync() {
        String lastSync = getLastSyncTime();

        shouldNavigateToDataSettings();

        //check for never
        Matcher.checkWithText(R.id.last_sync_date_time, lastSync);

        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        shouldClickSyncNow();

        //click on OK button
        ViewAction.clickonString(R.string.label_dialog_ok);

        lastSync = getLastSyncTime();

        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        //check for updated time
        Matcher.checkWithText(R.id.last_sync_date_time, lastSync);

        clearResource();

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_DATA_USAGE, TelemetryAction.MANUAL_SYNC_SUCCESS, InteractionType.OTHER.getValue(), null, true).
                build();


        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);

    }

    @Test
    public void test2ShouldGotoDataSettingsAndBack() {
        shouldNavigateToDataSettings();

        shouldClickBackButton();

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.label_nav_settings);

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, true).
                build();
//        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }

    @Test
    public void test3ShouldSyncNow() {
        shouldNavigateToDataSettings();

        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        shouldClickSyncNow();

        clearResource();

        //asserting sync success message in success dialog
        Matcher.isVisibleText(R.string.msg_sync_successfull);

        //click on OK button
        ViewAction.clickonString(R.string.label_dialog_ok);

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_DATA_USAGE, TelemetryAction.MANUAL_SYNC_SUCCESS, InteractionType.OTHER.getValue(), null, true).
                build();
        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);


    }

    @Test
    public void test4ShouldGoToSyncSettingsAndComeBack() {

        shouldNavigateToDataSettings();

        //click on Sync Settings
        ViewAction.clickonView(R.id.layout_sync_options);

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.title_sync_settings);

        shouldClickBackButton();

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.title_sync_data_setting);
        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.SETTINGS_DATA_USAGE, null, InteractionType.SHOW.getValue(), null, true).
                build();
        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);

    }

    private String getLastSyncTime() {
        GenieResponse<TelemetryStat> genieResponse = CoreApplication.getGenieSdkInstance().getTelemetryService().getTelemetryStat();
        TelemetryStat telemetryStat = genieResponse.getResult();
        if (telemetryStat != null) {
            return getFormattedTime(telemetryStat.getLastSyncTime());
        }
        return null;
    }

    private String getFormattedTime(long timeInMillis) {
        String syncTime;
        if (timeInMillis == 0) {
            syncTime = "NEVER";
        } else {
            syncTime = DateUtil.format(timeInMillis, DateUtil.DATE_TIME_AM_PM_FORMAT);
        }
        return syncTime;
    }


}
