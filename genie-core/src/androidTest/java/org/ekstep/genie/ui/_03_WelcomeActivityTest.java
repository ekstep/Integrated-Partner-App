package org.ekstep.genie.ui;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.ekstep.genie.GenieTestBase;
import org.ekstep.genie.R;
import org.ekstep.genie.activity.WelcomeActivity;
import org.ekstep.genie.custom.action.ViewAction;
import org.ekstep.genie.model.ExpectedEventList;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.telemetry.enums.CoRelationType;
import org.ekstep.genie.util.CommonUtil;
import org.ekstep.genie.util.DeviceAnimationTestRule;
import org.ekstep.genie.util.FontConstants;
import org.ekstep.genie.util.GenieDBHelper;
import org.ekstep.genie.util.MockServer;
import org.ekstep.genie.util.SampleData;
import org.ekstep.genie.util.TelemetryVerifier;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by swayangjit_gwl on 8/10/2016.
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class _03_WelcomeActivityTest extends GenieTestBase {

    @ClassRule
    static public DeviceAnimationTestRule deviceAnimationTestRule = new DeviceAnimationTestRule();
    @Rule
    public ActivityTestRule<WelcomeActivity> mActivityRule = new ActivityTestRule<WelcomeActivity>(WelcomeActivity.class, true, false);

    private MockServer mockServer;

    public void mocknLaunchWelcomeActivity() {
        startMockServer();
        //Mock the BFF response
        mMockServer.mockHttpResponse(SampleData.getPageAssembleResponseData(), 200);
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, WelcomeActivity.class);
        mActivityRule.launchActivity(intent);

    }

    @Override
    public void setup() throws IOException {
        super.setup();
        GenieDBHelper.clearTelemetryTable();
    }

    @Override
    public void tearDown() throws IOException {
        super.tearDown();
        shutDownMockServer();
    }

    @Test
    public void test1ShouldSuccessfullyNavigateToHomeFragment() {
        mocknLaunchWelcomeActivity();

        //selecting English
        ViewAction.clickOnAdapterView(R.id.fancyCoverFlow, 0);

        //Click on language OK button
        onView(ViewMatchers.withId(R.id.language_ok_btn)).perform(click());

//        Assert for "Skip" text
        onView(withId(R.id.layout_skip)).check(matches(withText(R.string.action_home_skip)));

        ExpectedEventList expectedEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, TelemetryAction.LANGUAGE_SETTINGS_INITIATE, InteractionType.TOUCH.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_LANGUAGE, TelemetryAction.LANGUAGE_SETTINGS_SUCCESS, InteractionType.OTHER.getValue(), FontConstants.LANG_ENGLISH, false).
                addGEInteractEvent(TelemetryStageId.IMPORT_CONTENT, TelemetryAction.CONTENT_IMPORT_INITIATED, InteractionType.TOUCH.getValue(), "", false).
                addGEInteractEvent(TelemetryStageId.IMPORT_CONTENT, TelemetryAction.CONTENT_IMPORT_SUCCESS, InteractionType.OTHER.getValue(), "do_30074541", false).
                addGEInteractEvent(TelemetryStageId.IMPORT_CONTENT, TelemetryAction.CONTENT_IMPORT_INITIATED, InteractionType.TOUCH.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.IMPORT_CONTENT, TelemetryAction.CONTENT_IMPORT_SUCCESS, InteractionType.OTHER.getValue(), "do_30076072", false).
                addGEInteractEvent(TelemetryStageId.IMPORT_CONTENT, TelemetryAction.CONTENT_IMPORT_INITIATED, InteractionType.TOUCH.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.IMPORT_CONTENT, TelemetryAction.CONTENT_IMPORT_SUCCESS, InteractionType.OTHER.getValue(), "do_30094761", false).
                addGEInteractEvent(TelemetryStageId.GENIE_HOME_ONBOARDING_SCREEN, null, InteractionType.OTHER.getValue(), null, false, CoRelationType.ONBRDNG).
                addGETransfer(3, CommonUtil.Constants.IMPORT_DATATYPE, CommonUtil.Constants.IMPORT_DIRECTION).
                build();

        TelemetryVerifier.verifyGEEvents(expectedEventList);

    }

}
