package org.ekstep.genie.ui;

import android.support.test.rule.ActivityTestRule;
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
import org.ekstep.genie.util.EcarCopyUtil;
import org.ekstep.genie.util.GenieDBHelper;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.SampleData;
import org.ekstep.genie.util.TelemetryVerifier;
import org.ekstep.genieservices.async.UserService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.Profile;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static org.ekstep.genie.util.FontConstants.LANG_ENGLISH;

/**
 * Created by swayangjit_gwl on 8/11/2016.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class _16_ManageUserFragmentTest extends GenieTestBase {
    private static final String PROFILE = "profile.epar";
    private static final String PROFILE_ECAR_ASSET_PATH = "Download/" + PROFILE;

    @Rule
    public ActivityTestRule<LandingActivity> mActivityTestRule = new ActivityTestRule<>(LandingActivity.class);
    private UserService mUserService = null;

    @Override
    public void setup() throws IOException {
        super.setup();
        mUserService = CoreApplication.getGenieAsyncService().getUserService();
        GenieDBHelper.clearTelemetryTable();
        GenieDBHelper.deleteAllUserProfile();
    }

    @Test
    public void test0ShouldCopyNecessoryFiles() {
        EcarCopyUtil.createFileFromAsset(PROFILE_ECAR_ASSET_PATH, DESTINATION);
    }

    /**
     * Scenario: When user is in the manage child screen and no child profile exists,
     * "no user yet" text is displayed in the manage child screen.
     * Given: User in the manage child screen of the application.
     * When: No child profile exists.
     * And
     * Then: "No user yet" text is displayed in the manage child screen.
     */
    @Test
    public void test1ShouldShowNoChildLayoutWhenNoChildPresent() {

        //navigate to manage child screen.
        ViewAction.navaigateToDrawerItems(2);

        //check for the no user layout visibility.
        Matcher.isVisible(R.id.layout_no_User);
    }

    @Test
    public void test2ShouldOpenAddchildLayout() {

        // Navigate to manage child screen.
        ViewAction.navaigateToDrawerItems(2);

        //Click on add child icon
        ViewAction.clickonView(R.id.fab_add_userCenter);

        //Assert for "Addchild" string
        Matcher.check(R.id.txt_add_child, R.string.title_addchild);
    }

    @Test
    public void test3ShouldGotoImportProfileFragment() {

        // Navigate to ManageUser
        ViewAction.navaigateToDrawerItems(2);

        //Click on More icon
        ViewAction.clickonView(R.id.layout_more, R.id.layout_more_parent);

        //Assert for "ImportProfile" string
        Matcher.check(R.id.txt_header, R.string.title_children_import_profile);

        ExpectedEventList expectedEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.MANAGE_CHILDREN, null, InteractionType.SHOW.getValue(), null, false).build();

        TelemetryVerifier.verifyGEEvents(expectedEventList);
    }

    /**
     * Scenario: When user is in the manage child screen, user clicks on the progress option in the more dialog, the profile's progress is displayed to the user.
     * Given: User is in the manage child screen.
     * When: User clicks on the progress option that appears in the dialog on click of the profile's more view.
     * And
     * Then: User profile progress is displayed to the user.
     */
    @Test
    public void test3ShouldNavigateToProgressReportActivity() {

        createUserProfile();

        // Navigate to ManageUser
        ViewAction.navaigateToDrawerItems(2);

        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        //Click on Recyclerview item "More"
        ViewAction.clickOnRecylerViewItem(R.id.recyclerview_user_list, 0, R.id.layout_more);

        clearResource();

        //Click on "Progress" option
        ViewAction.clickonView(R.id.layout_report_progress);

        //Assert for "Child Details & Progress" string
        Matcher.check(R.id.txt_header, R.string.title_summarizer_profile_details);
    }

    /**
     * Scenario: When user clicks on the share option in the dialog, the list of options to share the profile should be displayed to the user.
     * Given: User is the manage child screen of the application.
     * When: User clicks on the share profile option in the profile's more view.
     * And
     * Then: List of options to share the profile should be displayed to the user.
     */
    @Test
    public void test4ShouldShareProfileSuccessfully() {

        createUserProfile();

        // Navigate to ManageUser
        ViewAction.navaigateToDrawerItems(2);

        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        //Click ChildrenTab
        ViewAction.clickonView(R.id.rl_children);

        //Click on RecyclerView item "More"
        ViewAction.clickOnRecylerViewItem(R.id.recyclerview_user_list, 0, R.id.layout_more);

        clearResource();

        //Click on "Progress" option
        ViewAction.clickonView(R.id.layout_share);

        //Asserts for "Share Profile" string
        Matcher.check(R.id.txt_title, R.string.title_share_profile);

//        int gmailIconPosition = CommonUtil.getGmailPosition(mActivityTestRule.getActivity(), true);
//
//        ViewAction.clickOnRecylerViewChild(R.id.resolver_recyclerview_file, gmailIconPosition, R.id.title);

        ExpectedEventList expectedEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.MANAGE_CHILDREN, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.MANAGE_CHILDREN, null, InteractionType.SHOW.getValue(), null, false).
               /*
                addGEInteractEvent(TelemetryStageId.MANAGE_CHILDREN, TelemetryAction.SHARE_PROFILE_SUCCESS, InteractionType.OTHER.getValue(), null, false).
                addGETransfer(1, CommonUtil.Constants.IMPORT_DATATYPE_PROFILE, CommonUtil.Constants.EXPORT_DIRECTION).*/build();

        TelemetryVerifier.verifyGEEvents(expectedEventList);

    }

    /**
     * Scenario: When user is in the manage child screen, user clicks on the edit option in the more dialog, user should be able to edit profile
     * Given: User is in the manage child screen.
     * When: User clicks on the edit option that appears in the dialog on click of the profile's more view.
     * And
     * Then: User should be able to edit the profile information.
     */
    @Test
    public void test5ShouldEditUserProfileInfo() {

        createUserProfile();

        // Navigate to ManageUser
        ViewAction.navaigateToDrawerItems(2);

        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        //Click ChildrenTab
        ViewAction.clickonView(R.id.rl_children);

        //Click on RecyclerView item "More"
        ViewAction.clickOnRecylerViewItem(R.id.recyclerview_user_list, 0, R.id.layout_more);

        ViewAction.clickonView(R.id.layout_edit);

        editUserNameInfo();

        editAgeClassAndGenderInfo();

        editMediumBoardInfo();

        ViewAction.clickOnAdapterView(R.id.coverflow_avatar, 2);
    }

    /**
     * Scenario: When user is in the manage child screen, user clicks on the delete option in the more dialog, the profile is successfully deleted.
     * Given: User is in the manage child screen.
     * When: User clicks on delete option that appears in the dialog on click of profile's more view.
     * And
     * Then: User profile is successfully deleted.
     */
    @Test
    public void test6ShouldDeleteProfileSuccessfully() {

        createUserProfile();

        // Navigate to ManageUser
        ViewAction.navaigateToDrawerItems(2);

        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        //Click on Recyclerview item "More"
        ViewAction.clickOnRecylerViewItem(R.id.recyclerview_user_list, 0, R.id.layout_more);

        clearResource();

        //Click on "Progress" option
        ViewAction.clickonView(R.id.layout_delete);

        //Click on "No" button
        ViewAction.clickonView(R.id.delete_no);

        //Click on Recyclerview item "More"
        ViewAction.clickOnRecylerViewItem(R.id.recyclerview_user_list, 0, R.id.layout_more);

        //Click on "Progress" option
        ViewAction.clickonView(R.id.layout_delete);

        //Click on "Yes" button
        ViewAction.clickonView(R.id.delete_yes);

        //Assert for "Child deleted successfully" string
        Matcher.isToastMessageDisplayed(R.string.msg_children_profile_deletion_successful);

    }

    /**
     * Scenario: When user is in the manage child screen, user should be able to switch to the "group" mode from the "children" mode.
     * Given: When user is in the manage child screen.
     * When: User clicks on the "group" mode.
     * And
     * Then: User should navigate to the group mode.
     */
    @Test
    public void test7ShouldNavigateToAddChildWithGroupModeEnabled() {

        //delete all profiles
        GenieDBHelper.deleteAllUserProfile();

        // Navigate to ManageUser
        ViewAction.navaigateToDrawerItems(2);

        //Click GroupTab
        ViewAction.clickonView(R.id.rl_group);

        //Click on Add child icon
        ViewAction.clickonView(R.id.fab_add_userCenter);

        //Assert for any menu item string
        Matcher.check(R.id.txt_add_child, R.string.title_addchild_group);

    }

    /**
     * Scenario: When user is in the group view manage child screen, user clicks on the delete option in the more dialog, the group profile is successfully deleted.
     * Given: User is in the manage child screen.
     * When: User clicks on delete option that appears in the dialog on click of group profile's more view.
     * And
     * Then: Group profile is successfully deleted.
     */
    @Test
    public void test8ShouldDeleteGroupSuccessfully() {

        //create a group user
        createGroupProfile();

        // Navigate to ManageUser
        ViewAction.navaigateToDrawerItems(2);

        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        //Click GroupTab
        ViewAction.clickonView(R.id.rl_group);

        //Click on RecyclerView item "More"
        ViewAction.clickOnRecylerViewItem(R.id.recyclerview_user_list, 0, R.id.layout_more);

        clearResource();

        //Click on "Progress" option
        ViewAction.clickonView(R.id.layout_delete);

        //Click on "Yes" button
        ViewAction.clickonView(R.id.delete_yes);

        //Assert for "Child deleted successfully" string
        Matcher.isToastMessageDisplayed(R.string.msg_children_profile_deletion_successful);

        ExpectedEventList expectedEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.MANAGE_CHILDREN, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.MANAGE_CHILDREN, TelemetryAction.DELETE_CHILD_INITIATED, InteractionType.TOUCH.getValue(), null, false).build();


        TelemetryVerifier.verifyGEEvents(expectedEventList);
    }

    /**
     * Scenario: When user clicks on any of the child profile in the manage child screen,
     * user should go to home screen and that profile should be set as current profile.
     * Given: When user is in the manage child screen.
     * When: User clicks on one of the profile in the manage child screen.
     * And
     * Then: User should go to the home screen and that profile should set as current profile.
     */
    @Test
    public void test9ShouldSetCurrentChild() {

        createUserProfile();

        // Navigate to ManageUser
        ViewAction.navaigateToDrawerItems(2);

        //Click ChildrenTab
        ViewAction.clickonView(R.id.rl_children);

        startMockServer();

        //Click on first row
        ViewAction.clickOnRecylerViewItem(R.id.recyclerview_user_list, 0, R.id.layout_main);

        mMockServer.mockHttpResponse(SampleData.getPageAssembleResponseData(), 200);

        //Assert for the drawable in Landing Content fragment
        Matcher.checkDrawable(R.id.add_child_or_avatar_image, R.drawable.avatar_normal_1);

        ExpectedEventList expectedEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.MANAGE_CHILDREN, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.MANAGE_CHILDREN, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.MANAGE_CHILDREN, TelemetryAction.SWITCH_CHILD, InteractionType.TOUCH.getValue(), "4261346c-f66c-46a5-a06e-d8f2f476974b", false).
                addGESessionEndEvent().
                addGESessionStartEvent().build();

        TelemetryVerifier.verifyGEEvents(expectedEventList);
    }

    /**
     * To create a user profile.
     */
    private void createUserProfile() {

        Profile profile = new Profile("test", "@drawable/ic_avatar1", LANG_ENGLISH, 1, "English");
        profile.setUid("4261346c-f66c-46a5-a06e-d8f2f476974b");

        mUserService.createUserProfile(profile, new IResponseHandler<Profile>() {
            @Override
            public void onSuccess(GenieResponse<Profile> genieResponse) {
                LogUtil.e("ManageUserFragmentTest", "onSuccess: ");
            }

            @Override
            public void onError(GenieResponse<Profile> genieResponse) {
                LogUtil.e("ManageUserFragmentTest", "onError: ");
            }
        });
    }


    /**
     * To create a group profile.
     */
    private void createGroupProfile() {
        Profile profile = new Profile("testGroup", "@drawable/ic_avatar1", LANG_ENGLISH, -1, "");
        profile.setGroupUser(true);

        mUserService.createUserProfile(profile, new IResponseHandler<Profile>() {
            @Override
            public void onSuccess(GenieResponse<Profile> genieResponse) {
                LogUtil.e("ManageUserFragment", "onSuccess: ");
            }

            @Override
            public void onError(GenieResponse<Profile> genieResponse) {
                LogUtil.e("ManageUserFragment", "onFailure: ");
            }
        });
    }


    private void editMediumBoardInfo() {
        ViewAction.clickonView(R.id.txt_medium);

        ViewAction.clickOnRecylerViewItem(R.id.recycler_view_medium, 3);

        ViewAction.clickonView(R.id.txt_board);

        ViewAction.clickOnRecylerViewItem(R.id.recycler_view_board, 3);

        ViewAction.clickonView(R.id.img_next);
    }

    private void editAgeClassAndGenderInfo() {
        Matcher.isVisible(R.id.txt_age_label);

        ViewAction.clickonView(R.id.img_age_drop_down);

        ViewAction.clickOnRecylerViewItem(R.id.recycler_view_age, 3);

        ViewAction.clickonView(R.id.img_class_drop_down);

        ViewAction.clickOnRecylerViewItem(R.id.recycler_view_class, 3);

        ViewAction.clickonView(R.id.gender_girl_btn);

        ViewAction.clickonView(R.id.img_next);
    }

    private void editUserNameInfo() {

        ViewAction.typeText(R.id.edt_nickname, "GenieGirl");

        ViewAction.clickonView(R.id.img_next);
    }

}
