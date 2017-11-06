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
public class _08_LanguageFragmentTest extends GenieTestBase {

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


    private void navigateToLanguageFragment() {
        //Navigate to Settings
        ViewAction.navaigateToDrawerItems(5);

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.label_nav_settings);

        //click on Language
        ViewAction.clickonView(R.id.layout_language);

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.title_select_language);
    }

    private void clickBackButton() {
        //click on Toolbar back button
        ViewAction.clickonView(R.id.back_btn);
    }

    @Test
    public void test1ShouldChangeAppLanguagetoHindi() {
        navigateToLanguageFragment();

        //selecting Hindi
        ViewAction.clickOnAdapterView(R.id.fancyCoverFlow, 1);

        //click on OK button
        ViewAction.clickonView(R.id.language_ok_btn);

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, TelemetryAction.LANGUAGE_SETTINGS_INITIATE, InteractionType.TOUCH.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_LANGUAGE, TelemetryAction.LANGUAGE_SETTINGS_SUCCESS, InteractionType.OTHER.getValue(), "hi", false).
                build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);

        //navigate to Settings fragment
        navigateToLanguageFragment();

        clickBackButton();

        //asserting the set Locale
        Matcher.checkWithText(R.id.txt_selected_language, "हिंदी");

    }

    @Test
    public void test2ShouldChangeAppLanguagetoKannada() {
        navigateToLanguageFragment();

        //selecting Kannada
        ViewAction.clickOnAdapterView(R.id.fancyCoverFlow, 2);

        //click on OK button
        ViewAction.clickonView(R.id.language_ok_btn);

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, TelemetryAction.LANGUAGE_SETTINGS_INITIATE, InteractionType.TOUCH.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_LANGUAGE, TelemetryAction.LANGUAGE_SETTINGS_SUCCESS, InteractionType.OTHER.getValue(), "kn", false).
                build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);

        //navigate to Settings fragment
        navigateToLanguageFragment();

        clickBackButton();

        //asserting the set Locale
        Matcher.checkWithText(R.id.txt_selected_language, "ಕನ್ನಡ");
    }

    @Test
    public void test3ShouldChangeAppLanguagetoTelegu() {
        navigateToLanguageFragment();

        //selecting Telugu

        ViewAction.clickOnAdapterView(R.id.fancyCoverFlow, 3);

        //click on OK button
        ViewAction.clickonView(R.id.language_ok_btn);

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, TelemetryAction.LANGUAGE_SETTINGS_INITIATE, InteractionType.TOUCH.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_LANGUAGE, TelemetryAction.LANGUAGE_SETTINGS_SUCCESS, InteractionType.OTHER.getValue(), "te", false).
                build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);

        //navigate to Settings fragment
        navigateToLanguageFragment();
        clickBackButton();

        //asserting the set Locale
        Matcher.checkWithText(R.id.txt_selected_language, "తెలుగు");
    }

    @Test
    public void test4ShouldChangeAppLanguagetoMarathi() {
        navigateToLanguageFragment();

        //selecting Telugu

        ViewAction.clickOnAdapterView(R.id.fancyCoverFlow, 4);

        //click on OK button
        ViewAction.clickonView(R.id.language_ok_btn);

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, TelemetryAction.LANGUAGE_SETTINGS_INITIATE, InteractionType.TOUCH.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_LANGUAGE, TelemetryAction.LANGUAGE_SETTINGS_SUCCESS, InteractionType.OTHER.getValue(), "mr", false).
                build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);

        //navigate to Settings fragment
        navigateToLanguageFragment();
        clickBackButton();

        //asserting the set Locale
        Matcher.checkWithText(R.id.txt_selected_language, "मराठी");
    }

    @Test
    public void test5ShouldChangeAppLanguagetoEnglish() {
        navigateToLanguageFragment();

        //selecting Telugu

        ViewAction.clickOnAdapterView(R.id.fancyCoverFlow, 0);

        //click on OK button
        ViewAction.clickonView(R.id.language_ok_btn);

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, TelemetryAction.LANGUAGE_SETTINGS_INITIATE, InteractionType.TOUCH.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_LANGUAGE, TelemetryAction.LANGUAGE_SETTINGS_SUCCESS, InteractionType.OTHER.getValue(), "en", false).
                build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);

        //navigate to Settings fragment
        navigateToLanguageFragment();
        clickBackButton();

        //asserting the set Locale
        Matcher.checkWithText(R.id.txt_selected_language, "English");
    }

}
