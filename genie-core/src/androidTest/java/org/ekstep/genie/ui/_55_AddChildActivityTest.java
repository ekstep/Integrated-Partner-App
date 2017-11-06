package org.ekstep.genie.ui;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.format.DateUtils;

import junit.framework.Assert;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.GenieTestBase;
import org.ekstep.genie.R;
import org.ekstep.genie.custom.action.ViewAction;
import org.ekstep.genie.custom.matcher.Matcher;
import org.ekstep.genie.ui.landing.LandingActivity;
import org.ekstep.genie.util.GenieDBHelper;
import org.ekstep.genieservices.async.UserService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.Profile;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.List;

/**
 * TODO : 1) Telemetry events should be added. 2) DBUTil methods to clear telemetry and clear all user profile crashes. Uncomment in setup() when fixed.
 * Created by Sneha on 7/21/2017.
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class _55_AddChildActivityTest extends GenieTestBase {
    public UserService mUserService = null;

    @Rule
    public ActivityTestRule<LandingActivity> mActivityTestRule = new ActivityTestRule<>(LandingActivity.class);

    @Override
    public void setup() throws IOException {
        mUserService = CoreApplication.getGenieAsyncService().getUserService();
        GenieDBHelper.clearTelemetryTable();
        GenieDBHelper.deleteAllUserProfile();
        super.setup();
    }

    /**
     * Scenario: When user is in the manage child screen, user creates a profile by providing the child information, child profile is successfully created.
     * Given: User is in the manage child screen.
     * When: User creates a profile by providing the child information,
     * And
     * Then: Child profile is successfully created.
     */
    @Test
    public void test1shouldCreateUserProfile() {

        ViewAction.navaigateToDrawerItems(2);

        ViewAction.clickonView(R.id.fab_add_userCenter);

        addChildNickNameInfo();

        addAgeClassAndGenderInfo();

        addMediumAndBoardInfo();

        ViewAction.clickOnAdapterView(R.id.coverflow_avatar, 1);

        waitFor(DateUtils.SECOND_IN_MILLIS * 10);

        assertCreatedProfileValues();
    }

    /**
     * Scenario: When user is in the manage child screen, user creates a group profile by providing the group information, group profile is successfully created.
     * Given: User is in the manage child screen.
     * When: User creates a group profile by providing the group information,
     * And
     * Then: Group profile is successfully created.
     */
    @Test
    public void test2shouldCreateGroupProfile() {

        ViewAction.navaigateToDrawerItems(2);

        ViewAction.clickonView(R.id.rl_group);

        ViewAction.clickonView(R.id.fab_add_userCenter);

        ViewAction.typeText(R.id.edt_nickname, "TestGroup");

        ViewAction.clickonView(R.id.img_next);

        ViewAction.clickOnAdapterView(R.id.coverflow_avatar, 1);

        assertCreatedGroupProfileValues();
    }

    /**
     * Scenario: User should be able to move back and forth properly and the provided data should exists, during the process of file creation.
     * Given: User is in the process of creating a profile.
     * When: User clicks on back button during the process.
     * And
     * Then: User should be able to move back and forth, provided profile info should exist.
     */
    @Test
    public void test3shouldNavigateProperlyWhenGenieBackButtonClicked() {

        ViewAction.navaigateToDrawerItems(2);

        ViewAction.clickonView(R.id.rl_children);

        ViewAction.clickonView(R.id.fab_add_userCenter);

        addChildNickNameInfo();

        addAgeClassAndGenderInfo();

        addMediumAndBoardInfo();

        ViewAction.clickonView(R.id.img_skip);

        Matcher.checkWithText(R.id.txt_medium, "Hindi");

        Matcher.checkWithText(R.id.txt_board, "CBSE");

        ViewAction.clickonView(R.id.img_skip);

        Matcher.checkWithText(R.id.txt_age, "6 Years");

        Matcher.checkWithText(R.id.txt_class, "Grade 1");

        ViewAction.clickonView(R.id.img_next);

        ViewAction.clickonView(R.id.img_next);

        ViewAction.clickOnAdapterView(R.id.coverflow_avatar, 1);

    }

    /**
     * Scenario: User should be able to move back and forth properly and the provided data should exists, during the process of group creation.
     * Given: User is in the process of creating a group profile.
     * When: User clicks on back button during the process.
     * And
     * Then: User should be able to move back and forth, provided group profile info should exist.
     */
    @Test
    public void test4ShouldNavigateBackProperlyForGroup() {

        ViewAction.navaigateToDrawerItems(2);

        ViewAction.clickonView(R.id.rl_group);

        ViewAction.clickonView(R.id.fab_add_userCenter);

        ViewAction.typeText(R.id.edt_nickname, "TestGroup");

        ViewAction.clickonView(R.id.img_next);

        ViewAction.clickonView(R.id.img_skip);

        Matcher.checkWithText(R.id.edt_nickname, "TestGroup");

        ViewAction.clickonView(R.id.img_next);

        ViewAction.clickOnAdapterView(R.id.coverflow_avatar, 1);

    }

    /**
     * Adding Medium and Board information while creating a profile.
     */
    private void addMediumAndBoardInfo() {

        ViewAction.clickonView(R.id.txt_medium);

        ViewAction.clickOnRecylerViewItem(R.id.recycler_view_medium, 1);

        ViewAction.clickonView(R.id.txt_board);

        ViewAction.clickOnRecylerViewItem(R.id.recycler_view_board, 1);

        ViewAction.clickonView(R.id.img_next);
    }

    /**
     * Adding child name while creating a profile.
     */
    private void addChildNickNameInfo() {

        ViewAction.typeText(R.id.edt_nickname, "Test");

        ViewAction.clickonView(R.id.img_next);
    }

    /**
     * Adding age, class and gender information while creating a profile.
     */
    public void addAgeClassAndGenderInfo() {

        Matcher.isVisible(R.id.txt_age_label);

        ViewAction.clickonView(R.id.img_age_drop_down);

        ViewAction.clickOnRecylerViewItem(R.id.recycler_view_age, 1);

        ViewAction.clickonView(R.id.img_class_drop_down);

        ViewAction.clickOnRecylerViewItem(R.id.recycler_view_class, 1);

        ViewAction.clickonView(R.id.gender_boy_btn);

        ViewAction.clickonView(R.id.img_next);
    }


    /**
     * Assertions for the user profile creation.
     */
    private void assertCreatedProfileValues() {
        mUserService.getAllUserProfile(new IResponseHandler<List<Profile>>() {
            @Override
            public void onSuccess(GenieResponse<List<Profile>> profileList) {
                Assert.assertEquals("Test", profileList.getResult().get(0).getHandle());
                Assert.assertEquals(6, profileList.getResult().get(0).getAge());
                Assert.assertEquals(1, profileList.getResult().get(0).getStandard());
                Assert.assertEquals("Hindi", profileList.getResult().get(0).getMedium());
                Assert.assertEquals("CBSE", profileList.getResult().get(0).getBoard());
            }

            @Override
            public void onError(GenieResponse<List<Profile>> genieResponse) {
            }
        });
    }

    /**
     * Assertions for the group creation.
     */
    private void assertCreatedGroupProfileValues() {
        mUserService.getAllUserProfile(new IResponseHandler<List<Profile>>() {
            @Override
            public void onSuccess(GenieResponse<List<Profile>> profileList) {
                Assert.assertEquals("TestGroup", profileList.getResult().get(0).getHandle());
                Assert.assertEquals(-1, profileList.getResult().get(0).getAge());
                Assert.assertEquals("", profileList.getResult().get(0).getGender());
                Assert.assertEquals(-1, profileList.getResult().get(0).getStandard());
                Assert.assertEquals("", profileList.getResult().get(0).getMedium());
                Assert.assertEquals("", profileList.getResult().get(0).getBoard());
            }

            @Override
            public void onError(GenieResponse<List<Profile>> genieResponse) {
            }
        });
    }
}
