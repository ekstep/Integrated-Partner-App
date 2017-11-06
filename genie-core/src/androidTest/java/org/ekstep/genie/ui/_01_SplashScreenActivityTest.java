package org.ekstep.genie.ui;

import android.content.Intent;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;

import org.ekstep.genie.BuildConfig;
import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.GenieTestBase;
import org.ekstep.genie.model.ExpectedEventList;
import org.ekstep.genie.ui.splash.SplashActivity;
import org.ekstep.genie.util.GenieDBHelper;
import org.ekstep.genie.util.TelemetryVerifier;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;

/**
 * Created by Kannappan on 11/15/2016.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class _01_SplashScreenActivityTest extends GenieTestBase {

    private static final String ACTION = "INSTALL";
    private static final String UTMSOURCE = "test_source";
    private static final String UTMMEDIUM = "test_medium";
    private static final String UTMTERM = "test_term";
    private static final String UTMCONTENT = "test_content";
    private static final String UTMCAMPAIGN = "test_name";
    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);
    private UiDevice mDevice;

    @Override
    public void setup() throws IOException {
        super.setup();
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

    }

    @Override
    public void tearDown() throws IOException {
        GenieDBHelper.clearTelemetryTable();
    }

    @Test
    public void test1ShouldGenerateGEGenieStartEvent() throws Exception {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mDevice.wait(Until.hasObject(By.text(TEXT_ALLOW)), 5000);
            for (int i = 0; i < mPermissions.length; i++) {
                // grant permissions
                grantPermission(mDevice);
            }
        }

        mDevice.wait(Until.hasObject(By.text(SELECT_LANGUAGE)), 5000);
        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEStartEvent(1).
                build();
        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
        CoreApplication.getInstance().setGenieAlive(-1);
    }

    @Test
    public void test2ShouldNotGenerateGEStartWhenAppinBGnLaunched() throws Exception {

        mDevice.wait(Until.hasObject(By.text(SELECT_LANGUAGE)), 5000);
        ExpectedEventList expectedGEInteractEventList1 = new ExpectedEventList.ExpectedEventListBuilder().
                addGEStartEvent(1).
                build();
        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList1);
        mDevice.pressHome();
        launchAppUsingPkgName(BuildConfig.APPLICATION_ID, mDevice);
        mDevice.wait(Until.hasObject(By.text(SELECT_LANGUAGE)), 5000);
        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEStartEvent(0).
                build();
        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }



    @Test
    public void test3ShouldGenerateGEUpdateEvent() {

        Intent intent = new Intent();
        intent.setAction("com.android.vending.INSTALL_REFERRER");
        intent.putExtra("referrer", "utm_source=test_source&utm_medium=test_medium&utm_term=test_term&utm_content=test_content&utm_campaign=test_name");
        mActivityTestRule.getActivity().sendBroadcast(intent);
        mDevice.wait(Until.hasObject(By.text(SELECT_LANGUAGE)), 5000);
        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEGenieUpdateEvent(ACTION, UTMSOURCE, UTMMEDIUM, UTMTERM, UTMCONTENT, UTMCAMPAIGN).
                build();
        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);

    }


}
