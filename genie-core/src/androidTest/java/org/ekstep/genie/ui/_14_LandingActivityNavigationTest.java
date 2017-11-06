package org.ekstep.genie.ui;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.ekstep.genie.GenieTestBase;
import org.ekstep.genie.R;
import org.ekstep.genie.custom.action.ViewAction;
import org.ekstep.genie.custom.matcher.Matcher;
import org.ekstep.genie.model.ExpectedEventList;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.ui.landing.LandingActivity;
import org.ekstep.genie.util.CommonUtil;
import org.ekstep.genie.util.MockServer;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by swayangjit_gwl on 8/18/2016.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
// TODO: 20/7/17 Changes are made to this test class, check if it requies re-implmentation

public class _14_LandingActivityNavigationTest extends GenieTestBase {
    @Rule
    public ActivityTestRule<LandingActivity> mActivityTestRule = new ActivityTestRule<>(LandingActivity.class);
    private MockServer mMockServer;

    @Override
    public void setup() throws IOException {
        super.setup();
    }

    @Test
    public void test3ShouldComeBacktoAboutFromPrivacyPolicyFragment() {
        //Navigate to Settings
        ViewAction.navaigateToDrawerItems(5);

        //Navigate to Settings
        ViewAction.clickonView(R.id.layout_about);

        //Navigate to Settings
        ViewAction.clickonView(R.id.layout_privacy_policy);

        //Press device back
        Espresso.pressBack();

        //Asserts for About string
        Matcher.check(R.id.txt_header, R.string.title_about);

    }

//    @Test
//    public void test4ShouldComeBacktoHomeFromManageContentFragment() {
//        //Navigate to Settings
//        ViewAction.navaigateToDrawerItems(1);
//
//        //Press device back
//        Espresso.pressBack();
//
//
//        //Asserts for the library icon
//        Matcher.isVisibleWithEffectiveVisibility(R.id.img_library);
//
//    }

    @Test
    public void test5ShouldComeBacktoAdvanceSettingsFromAddNewTagFragment() {
        //Navigate to Settings
        ViewAction.navaigateToDrawerItems(5);

        //Navigate to AdvancedSettings Fragment
        ViewAction.clickonView(R.id.layout_advanced_settings);

        //click on delete button of particular item.. Manually add 1 tag here otherwise test failed
//        ViewAction.clickOnRecylerViewItem(R.id.recylerview_tags, 0, R.id.layout_delete);


        //Navigate to AddNewTag
        ViewAction.clickonView(R.id.fab_add_tagCenter);

        //Press device back
        Espresso.pressBack();

        //Asserts for Advanced Settings string
        Matcher.check(R.id.txt_header, R.string.title_advanced_settings);

    }

    @Test
    public void test6ShouldNavigateToManageUserFromImportProfile() {
        //Navigate to Settings
        ViewAction.navaigateToDrawerItems(2);

        //Navigate to More icon
        ViewAction.clickonView(R.id.layout_more, R.id.layout_more_parent);



        //Press device back
        Espresso.pressBack();

        //Asserts for Children string
        Matcher.check(R.id.txt_children, R.string.title_children);

    }

    @Test
    public void test7ShouldNavigateToMyContentFromImportContent() {
        //Navigate to Settings
        ViewAction.navaigateToDrawerItems(3);

        //Navigate to More icon
        ViewAction.clickonView(R.id.menu_import_btn);

        //Press device back
        Espresso.pressBack();

        //Asserts for My Backpack string
        Matcher.check(R.id.txt_header, R.string.title_downloads);

    }

    @Test
    public void test8ShouldShowEndGenieDialog() {
        //Press device back
        Espresso.pressBack();

        //Asserts for Ok string
        Matcher.check(R.id.txt_yes, R.string.label_dialog_ok);

        //Click on No Button
        ViewAction.clickonView(R.id.txt_no);

        //Press device back
        Espresso.pressBack();

        //Click on Yes Button
        ViewAction.clickonView(R.id.txt_yes);

    }

    @Test
    public void test9ShouldShareGenieAsFile() {

//        DBUtil.clearTelemetryTable();

        //Click on Berger menu
        ViewAction.clickonView(R.id.img_berger_menu);

        //Click on Share Genie Icon
        ViewAction.navaigateToDrawerItems(0);


        //Asserts for "Share Genie" string
        Matcher.check(R.id.txt_title, R.string.label_nav_share_genie);

        int gmailIconPosition = CommonUtil.getGmailPosition(mActivityTestRule.getActivity(), true);

        ViewAction.clickOnRecylerViewChild(R.id.resolver_recyclerview_file, gmailIconPosition, R.id.title);

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, TelemetryAction.SHARE_GENIE_INITIATED, InteractionType.TOUCH.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, TelemetryAction.SHARE_GENIE_SUCCESS, InteractionType.OTHER.getValue(), null, true)
                .build();

//        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }

    @Test
    public void test91ShouldShareGenieAsLink() {

        //Click on Berger menu
        ViewAction.clickonView(R.id.img_berger_menu);

        //Click on Share Genie Icon
        ViewAction.navaigateToDrawerItems(0);

        //Asserts for "Share Genie" string
        Matcher.check(R.id.txt_title, R.string.label_nav_share_genie);

        ViewAction.swipeUp(R.id.scroll_share);

        onView(withId(R.id.resolver_recyclerview_link)).perform(scrollTo());

//        onView(withRecyclerView(R.id.resolver_recyclerview_link).atPositionOnView(CommonUtil.getGmailPosition(mActivityTestRule.getActivity(), false), R.id.title)).perform(click());
        int gmailIconPosition = CommonUtil.getGmailPosition(mActivityTestRule.getActivity(), true);

        ViewAction.clickOnRecylerViewChild(R.id.resolver_recyclerview_link, gmailIconPosition, R.id.title);

    }
}
