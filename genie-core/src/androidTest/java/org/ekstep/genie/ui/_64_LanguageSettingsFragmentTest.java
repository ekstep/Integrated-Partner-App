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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * TODO: should check for telemetry
 * Created by Sneha on 10/17/2017.
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class _64_LanguageSettingsFragmentTest extends GenieTestBase {

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

    public void navigateToSettingsScreen() {

        Matcher.check(R.id.txt_header, R.string.label_language);

//        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
//                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, true).
//                build();
//
//        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);

    }

    /**
     * Scenario: When user is in the Settings screen, user selects a language and clicks on the apply button,
     * language is set to the selected language.
     * Given: When user is in the Settings screen.
     * When: User selects a language and clicks on the apply button.
     * Then: Language is set to the selected language.
     */
    @Test
    public void _01_testShouldApplyLanguageChange() {

        navigateToSettingsScreen();

        //click on hindi radio button
        ViewAction.clickonView(R.id.radio_hindi);

        //click on apply button
        onView(withId(R.id.txt_apply_language)).perform(scrollTo()).perform(click());

//        Matcher.isVisibleText(R.string.msg_setting_changing_language);

    }

    /**
     * Scenario: When user sets a language, the language has to remain selected in the language screen.
     * Given: When user is in the settings screen.
     * When: User sets a language.
     * Then: The language has to remain selected in the language screen
     */
    @Test
    public void _02_testShouldVerifySelectedLanguage() {

        navigateToSettingsScreen();

        //click on english language
        ViewAction.clickonView(R.id.radio_english);

        //scroll and click on apply button
        onView(withId(R.id.txt_apply_language)).perform(scrollTo()).perform(click());

        //click on burger menu icon
        ViewAction.clickonView(R.id.img_berger_menu);

        //click on settings
        ViewAction.clickOnRecylerViewItem(R.id.list_drawer, 5);

        Matcher.isCompoundButtonChecked(R.id.radio_english);
    }
}


