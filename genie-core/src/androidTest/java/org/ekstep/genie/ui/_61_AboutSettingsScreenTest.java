package org.ekstep.genie.ui;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.ekstep.genie.GenieTestBase;
import org.ekstep.genie.R;
import org.ekstep.genie.custom.action.ViewAction;
import org.ekstep.genie.ui.settings.SettingsActivity;
import org.ekstep.genie.util.GenieDBHelper;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**TODO: telemetry events
 * Created by Sneha on 10/17/2017.
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class _61_AboutSettingsScreenTest extends GenieTestBase {

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

    private void navigateToAboutScreen() {

        ViewAction.clickOnRecylerViewItem(R.id.rv_settings_master, 4);
    }

    @Test
    public void _01_testShouldGotoAboutScreen() {

        navigateToAboutScreen();

//        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
//                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, false).
//                build();
//        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }

    /**
     * Scenario: When user clicks on the Privacy Policy option in the settings view, Privacy Policy screen is displayed.
     * When: User clicks on the Privacy Policy option in the settings view.
     * Then: Privacy Policy screen is displayed.
     */
    @Test
    public void _02_testShouldClickPrivacyPolicy() {

        navigateToAboutScreen();

        //click on privacy policy
        ViewAction.clickonView(R.id.layout_privacy_policy);

        //click on back button
        ViewAction.clickonView(R.id.btn_back);

//        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
//                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, false).
//                build();
//        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }

    /**
     * Scenario: When user clicks on the Terms of service option in the settings view, Terms of service screen is displayed.
     * When: User clicks on the Terms of service option in the settings view.
     * Then: Terms of service screen is displayed.
     */
    @Test
    public void _03_testShouldClickTermsOfService() {

        navigateToAboutScreen();

        //click on terms and condition
        ViewAction.clickonView(R.id.layout_terms_condition);

        //click on back button
        ViewAction.clickonView(R.id.btn_back);

//        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
//                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, false).
//                build();
//        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }

    /**
     * Scenario: When user clicks on the about us option in the settings view, About Us screen is displayed.
     * When: User clicks on the about us option in the settings view.
     * Then: About Us screen is displayed.
     */
    @Test
    public void _04_testShouldClickAboutUs() {

        navigateToAboutScreen();

        //scroll the screen and click on about us.
        onView(withId(R.id.layout_about_us)).perform(scrollTo()).perform(click());

        //click on back button.
        ViewAction.clickonView(R.id.btn_back);

//        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
//                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, false).
//                build();
//        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }
}
