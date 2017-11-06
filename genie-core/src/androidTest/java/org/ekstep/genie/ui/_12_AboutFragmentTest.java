package org.ekstep.genie.ui;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.ekstep.genie.GenieTestBase;
import org.ekstep.genie.R;
import org.ekstep.genie.custom.action.ViewAction;
import org.ekstep.genie.custom.matcher.Matcher;
import org.ekstep.genie.model.ExpectedEventList;
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
 * Created by GoodWorkLabs on 09-08-2016.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class _12_AboutFragmentTest extends GenieTestBase {

    @Rule
    public ActivityTestRule<LandingActivity> mActivityRule = new ActivityTestRule<>(LandingActivity.class);

    @Override
    public void setup() throws IOException {
        super.setup();
        GenieDBHelper.clearTelemetryTable();
    }

    private void navigateToAboutSettings() {

        //Navigate to Settings
        ViewAction.navaigateToDrawerItems(5);

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.label_nav_settings);

        //click on About Settings
        ViewAction.clickonView(R.id.layout_about);

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.title_about);
    }

    private void clickBackButton() {
        //click on Toolbar back button
        ViewAction.clickonView(R.id.back_btn);
    }

    @Test
    public void test1ShouldGotoAboutFragmentAndComeBack() {
        navigateToAboutSettings();

        clickBackButton();

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.label_nav_settings);

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, false).
                build();
        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }

    @Test
    public void test2ShouldGotoPrivacyPolicyAndComeBack() {
        navigateToAboutSettings();

        //click on privacy policy
        ViewAction.clickonView(R.id.layout_privacy_policy);
        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.label_about_privacy_policy);

        clickBackButton();

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.title_about);

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, false).
                build();
        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }

    @Test
    public void test3ShouldGotoTermsOfServiceAndComeBack() {
        navigateToAboutSettings();

        //click on terms of service
        ViewAction.clickonView(R.id.layout_terms_condition);

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.label_about_tos);

        clickBackButton();

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.title_about);

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, false).
                build();
        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }

    @Test
    public void test4ShouldGotoAboutUsAndComeBack() {
        navigateToAboutSettings();

        //click on About us
        ViewAction.scrollANDClickView(R.id.layout_about_us);
        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.label_about_us);

        clickBackButton();

        //asserting the title text
        Matcher.check(R.id.txt_header, R.string.title_about);

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, false).
                build();
        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }

    @Test
    public void test5ShouldGoToDummyTestFragmentAndComeBack() {
        navigateToAboutSettings();

        //click on Genie Version 5 times
        ViewAction.clickonView(R.id.layout_genie_version);
        ViewAction.clickonView(R.id.layout_genie_version);
        ViewAction.clickonView(R.id.layout_genie_version);
        ViewAction.clickonView(R.id.layout_genie_version);
        ViewAction.clickonView(R.id.layout_genie_version);

        //Asserting the toast message displayed
        Matcher.isToastMessageDisplayed(R.string.msg_dummy_language_test_success);

        //Asserting the title text
        Matcher.check(R.id.txt_header, R.string.title_language_test);

        clickBackButton();

        //Asserting the title text
        Matcher.check(R.id.txt_header, R.string.title_about);

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, null, InteractionType.SHOW.getValue(), null, false).
                build();
        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }

    @Test
    public void test6ShouldGoToDummyTestFragmentAndComeBack() {
        //Navigate to Settings page
        navigateToAboutSettings();

        //click on Genie Version 5 times
        ViewAction.clickonView(R.id.layout_genie_version);
        ViewAction.clickonView(R.id.layout_genie_version);
        ViewAction.clickonView(R.id.layout_genie_version);
        ViewAction.clickonView(R.id.layout_genie_version);
        ViewAction.clickonView(R.id.layout_genie_version);

        //Click on More button
        ViewAction.clickonView(R.id.more_btn);

        //Click on Hindi language
        ViewAction.clickonView(R.id.layout_hindi);

        //click on Back twice
        clickBackButton();
        clickBackButton();

        //asserting the set Locale
        Matcher.checkWithText(R.id.txt_selected_language, "हिंदी");

        //Navigate to Settings page
        ViewAction.clickonView(R.id.layout_about);


        //click on Genie Version 5 times
        ViewAction.clickonView(R.id.layout_genie_version);
        ViewAction.clickonView(R.id.layout_genie_version);
        ViewAction.clickonView(R.id.layout_genie_version);
        ViewAction.clickonView(R.id.layout_genie_version);
        ViewAction.clickonView(R.id.layout_genie_version);

        //Click on More button
        ViewAction.clickonView(R.id.more_btn);

        //Change Locale back to english
        ViewAction.clickonView(R.id.layout_english);

    }
}
