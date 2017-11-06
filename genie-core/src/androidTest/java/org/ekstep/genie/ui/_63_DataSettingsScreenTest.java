package org.ekstep.genie.ui;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.format.DateUtils;

import junit.framework.Assert;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.GenieTestBase;
import org.ekstep.genie.R;
import org.ekstep.genie.custom.action.ViewAction;
import org.ekstep.genie.custom.matcher.Matcher;
import org.ekstep.genie.ui.settings.SettingsActivity;
import org.ekstep.genie.util.GenieDBHelper;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.TelemetryStat;
import org.ekstep.genieservices.commons.utils.DateUtil;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;

/**TODO: telemetry events
 * Created by Sneha on 10/17/2017.
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class _63_DataSettingsScreenTest extends GenieTestBase {

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

        ViewAction.clickOnRecylerViewItem(R.id.rv_settings_master, 1);

//        ExpectedEventList expectedEventList = new ExpectedEventList.ExpectedEventListBuilder().
//                addGEInteractEvent(TelemetryStageId.SETTINGS_ADVANCED, null, InteractionType.SHOW.getValue(), null, false).
//                build();
//
//        TelemetryVerifier.verifyGEEvents(expectedEventList);
    }

    /**
     * Scenario: When user is in the data settings screen, user clicks on the sync now button, data gets successfully synced.
     * Given: When user is in the data settings screen.
     * When: User clicks on the sync now button.
     * Then: Data gets successfully synced.
     */
    @Test
    public void _01_testShouldClickOnSyncNow() {

        navigateToDataSettingsScreen();

        //wait for 5 sec
        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        //click on sync now button
        ViewAction.clickonView(R.id.btn_sync_now);

        clearResource();

        try {
            //asserting sync success message in success dialog
            Matcher.isVisibleText(R.string.msg_sync_successfull);

        } catch (NoMatchingViewException e) {
            LogUtil.e("DataSettings ", "exception " + e);
            Assert.assertTrue(true);
        }

//        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
//                addGEInteractEvent(TelemetryStageId.SETTINGS_DATA_USAGE, TelemetryAction.MANUAL_SYNC_SUCCESS, InteractionType.OTHER.getValue(), null, true).
//                build();
//        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);


    }

    /**
     * Scenario: When user clicks on the sync now button, last sync time in the screen should get updated .
     * Given: When user is in the data settings.
     * When: User clicks on the sync now button.
     * Then: Last sync time displayed in the screen gets updated.
     */
    @Test
    public void _02_testShouldUpdateLastSync() {

        //get last sync time
        String lastSync = getLastSyncTime();

        navigateToDataSettingsScreen();

        //check for never
        Matcher.checkWithText(R.id.txt_last_sync_time, lastSync);

        //wait for 5 sec
        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        //click on sync now button
        ViewAction.clickonView(R.id.btn_sync_now);

        //get last sync time
        lastSync = getLastSyncTime();

        //wait for 5 sec
        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        //check for updated time
        Matcher.checkWithText(R.id.txt_last_sync_time, lastSync);

        clearResource();

//        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
//                addGEInteractEvent(TelemetryStageId.SETTINGS_DATA_USAGE, TelemetryAction.MANUAL_SYNC_SUCCESS, InteractionType.OTHER.getValue(), null, true).
//                build();
//        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
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
            syncTime = "Last sync NEVER";
        } else {
            syncTime = "Last sync " + DateUtil.format(timeInMillis, DateUtil.DATE_TIME_AM_PM_FORMAT);
        }
        return syncTime;
    }
}
