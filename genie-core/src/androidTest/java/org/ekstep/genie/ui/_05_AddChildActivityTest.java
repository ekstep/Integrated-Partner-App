package org.ekstep.genie.ui;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.GenieTestBase;
import org.ekstep.genie.R;
import org.ekstep.genie.custom.action.ViewAction;
import org.ekstep.genie.custom.matcher.Matcher;
import org.ekstep.genie.customview.FancyCoverFlow;
import org.ekstep.genie.model.ExpectedEventList;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.ui.addchild.AddChildActivity;
import org.ekstep.genie.ui.addchild.agegendernclass.AgeGenderClassFragment;
import org.ekstep.genie.ui.addchild.avatar.AvatarFragment;
import org.ekstep.genie.ui.addchild.mediumnboard.MediumnBoardFragment;
import org.ekstep.genie.ui.addchild.nickname.NickNameFragment;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.DeviceAnimationTestRule;
import org.ekstep.genie.util.GenieDBHelper;
import org.ekstep.genie.util.TelemetryVerifier;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.Profile;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by swayangjit on 14/1/17.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class _05_AddChildActivityTest extends GenieTestBase {

    public static final String ADD_CHILD_NAME = "Addchild";
    public static final String ADD_GROUP_NAME = "Addgroup";

    @ClassRule
    static public DeviceAnimationTestRule mDeviceAnimationTestRule = new DeviceAnimationTestRule();
    @Rule
    public ActivityTestRule<AddChildActivity> mActivityRule = new ActivityTestRule<>(AddChildActivity.class, true, false);

    @Override
    public void setup() throws IOException {
        super.setup();
        GenieDBHelper.clearTelemetryTable();
    }

    @Override
    public void tearDown() throws IOException {
        super.tearDown();
    }

    public void mockIntent(boolean isGroup) {

        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, AddChildActivity.class);
        GenieDBHelper.clearTelemetryTable();

        intent.putExtra(Constant.BUNDLE_GROUP_MODE, isGroup);

        mActivityRule.launchActivity(intent);
    }

    private void clickNext() {
        //Click on "Next" button
        ViewAction.clickonView(R.id.img_next);
    }

    private void clickGenieBackButton() {
        //Click on "Next" button
        ViewAction.clickonView(R.id.img_skip);
    }

    private void fillUpScreen1(String name) {
        //Type on "Addchild" in edittext and close keyboard
        onView(withId(R.id.edt_nickname)).perform(typeText(name));

        pressBack();

        clickNext();
    }

    private void fillUpAge() {
        //Click on "Age" button
        ViewAction.clickonView(R.id.txt_age);

        //Click on "Age" button and set the age
        ViewAction.clickOnRecylerViewItem(R.id.recycler_view_age, 0);
    }

    private void fillUpGender(String gender) {

        if (gender.equalsIgnoreCase("male")) {
            //Set Gender male
            ViewAction.clickonView(R.id.gender_boy_btn);
        } else if (gender.equalsIgnoreCase("female")) {
            //Set Gender male
            ViewAction.clickonView(R.id.gender_girl_btn);
        }

    }

    private void fillUpClass() {
        //Click on Class and set class
        //Click on "Medium" button
        ViewAction.clickonView(R.id.txt_class);

        //Select the medium
        ViewAction.clickOnRecylerViewItem(R.id.recycler_view_class, 1);
    }

    private void fillUpScreen2(String gender) {

        fillUpAge();

        fillUpGender(gender);

        fillUpClass();

        clickNext();

    }

    private void fillUpMedium() {
        //Click on "Medium" button
        ViewAction.clickonView(R.id.txt_medium);

        //Select the medium
        ViewAction.clickOnRecylerViewItem(R.id.recycler_view_medium, 1);
    }

    private void fillUpBoard() {
        //Click on "Board" button
        ViewAction.clickonView(R.id.txt_board);

        //Select the board
        ViewAction.clickOnRecylerViewItem(R.id.recycler_view_board, 2);
    }

    private void fillUpScreen3() {

        fillUpMedium();

        fillUpBoard();

        //Click on "Next" button
        ViewAction.clickonView(R.id.img_next);
    }

    private void fillUpScreen4() {
        //Click on first item in avatar list
        ViewAction.clickOnAdapterView(R.id.coverflow_avatar, 1);
    }

    private void assertForScreen2(String age) {

        //Assert if previuosly selected age persisted or not
        Matcher.checkWithText(R.id.txt_age, age);

        Assert.assertTrue(mActivityRule.getActivity().getRunningFragment() instanceof AgeGenderClassFragment);
    }

    private void assertForScreen3(String medium, String board) {

        //Assert if previuosly selected medium persisted or not
        Matcher.checkWithText(R.id.txt_medium, medium);

        //Assert if previuosly selected board persisted or not
        Matcher.checkWithText(R.id.txt_board, board);

        Assert.assertTrue(mActivityRule.getActivity().getRunningFragment() instanceof MediumnBoardFragment);
    }

    private void assertForScreen1(String name) {

        //Assert if previuosly selected name persisted or not
        Matcher.checkWithText(R.id.edt_nickname, name);

        Assert.assertTrue(mActivityRule.getActivity().getRunningFragment() instanceof NickNameFragment);

    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
    }

    private void verifyTelemetryEvents() {
        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.MANAGE_CHILDREN, TelemetryAction.ADD_CHILD_INITIATE, InteractionType.TOUCH.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_NAME, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_NAME, null, InteractionType.TOUCH.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_AGE_GENDER_CLASS, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_AGE_GENDER_CLASS, null, InteractionType.TOUCH.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_MEDIUM_BOARD, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_MEDIUM_BOARD, null, InteractionType.TOUCH.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_AVATAR, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_AVATAR, null, InteractionType.TOUCH.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.MANAGE_CHILDREN, TelemetryAction.ADD_CHILD_SUCCESS, InteractionType.OTHER.getValue(), null, false).
                addCreateUserEvent().
                addGESessionEndEvent().
                addGESessionStartEvent().
                addCreateProfileEvent(ADD_CHILD_NAME, false).build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);

    }

    private void verifyTelemetryEvents1() {
        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.MANAGE_CHILDREN, TelemetryAction.ADD_CHILD_INITIATE, InteractionType.TOUCH.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_NAME, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_NAME, null, InteractionType.TOUCH.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_AGE_GENDER_CLASS, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_AGE_GENDER_CLASS, null, InteractionType.TOUCH.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_AGE_GENDER_CLASS, null, InteractionType.TOUCH.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_AGE_GENDER_CLASS, null, InteractionType.TOUCH.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_AGE_GENDER_CLASS, null, InteractionType.TOUCH.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_MEDIUM_BOARD, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_MEDIUM_BOARD, null, InteractionType.TOUCH.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_MEDIUM_BOARD, null, InteractionType.TOUCH.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_AVATAR, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_AVATAR, null, InteractionType.TOUCH.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.MANAGE_CHILDREN, TelemetryAction.ADD_CHILD_SUCCESS, InteractionType.OTHER.getValue(), null, false).
                addCreateUserEvent().
                addGESessionEndEvent().
                addGESessionStartEvent().
                addCreateProfileEvent(ADD_CHILD_NAME, false).build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);

    }

    private void verifyTelemetryEvents2() {
        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.MANAGE_CHILDREN, TelemetryAction.ADD_CHILD_INITIATE, InteractionType.TOUCH.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_NAME, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_NAME, null, InteractionType.TOUCH.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_AGE_GENDER_CLASS, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_AGE_GENDER_CLASS, null, InteractionType.TOUCH.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_MEDIUM_BOARD, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_MEDIUM_BOARD, null, InteractionType.TOUCH.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_AVATAR, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_MEDIUM_BOARD, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_AGE_GENDER_CLASS, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_NAME, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_NAME, null, InteractionType.TOUCH.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_AGE_GENDER_CLASS, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_AGE_GENDER_CLASS, null, InteractionType.TOUCH.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_MEDIUM_BOARD, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_MEDIUM_BOARD, null, InteractionType.TOUCH.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_AVATAR, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_AVATAR, null, InteractionType.TOUCH.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.MANAGE_CHILDREN, TelemetryAction.ADD_CHILD_SUCCESS, InteractionType.OTHER.getValue(), null, false).
                addCreateUserEvent().
                addGESessionEndEvent().
                addGESessionStartEvent().
                addCreateProfileEvent(ADD_CHILD_NAME, false).build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);

    }

    /**
     * Scenario:User gives all valid input.
     * Then:It should create a profile successfully.
     */
    @Test
    public void test1ShouldAddChildwithName() {

        mockIntent(false);

        fillUpScreen1(ADD_CHILD_NAME);

        fillUpScreen2("male");

        fillUpScreen3();

        GenieDBHelper.deleteAllUserProfile();

        fillUpScreen4();

        sleep();

        verifyTelemetryEvents();

        CoreApplication.getGenieAsyncService().getUserService().getAllUserProfile(new IResponseHandler<List<Profile>>() {
            @Override
            public void onSuccess(GenieResponse<List<Profile>> genieResponse) {
                List<Profile> profileList = genieResponse.getResult();
                Assert.assertEquals(ADD_CHILD_NAME, profileList.get(0).getHandle());
                Assert.assertEquals(5, profileList.get(0).getAge());
                Assert.assertEquals("male", profileList.get(0).getGender());
                Assert.assertEquals(1, profileList.get(0).getStandard());
                Assert.assertEquals("Hindi", profileList.get(0).getMedium());
                Assert.assertEquals("ICSE", profileList.get(0).getBoard());
                Assert.assertEquals("@drawable/ic_avatar2", profileList.get(0).getAvatar());
            }

            @Override
            public void onError(GenieResponse<List<Profile>> genieResponse) {
            }
        });

    }

    /**
     * Scenario:User gives all invalid  input and corrects it after seeing validation errors
     * Then:It should create a profile successfully.
     */
    @Test
    public void test2ShouldShowValidationErrors() {

        mockIntent(false);

        fillUpScreen1(ADD_CHILD_NAME);

        clickNext();

        Assert.assertFalse(mActivityRule.getActivity().getRunningFragment() instanceof MediumnBoardFragment);

        fillUpAge();

        clickNext();

        Assert.assertFalse(mActivityRule.getActivity().getRunningFragment() instanceof MediumnBoardFragment);

        fillUpGender("male");

        clickNext();

        Assert.assertFalse(mActivityRule.getActivity().getRunningFragment() instanceof MediumnBoardFragment);

        fillUpClass();

        clickNext();

        Assert.assertTrue(mActivityRule.getActivity().getRunningFragment() instanceof MediumnBoardFragment);

        clickNext();

        Assert.assertFalse(mActivityRule.getActivity().getRunningFragment() instanceof AvatarFragment);

        fillUpMedium();

        fillUpBoard();

        clickNext();

        Assert.assertTrue(mActivityRule.getActivity().getRunningFragment() instanceof AvatarFragment);

        GenieDBHelper.deleteAllUserProfile();

        fillUpScreen4();

        sleep();

        verifyTelemetryEvents1();

        CoreApplication.getGenieAsyncService().getUserService().getAllUserProfile(new IResponseHandler<List<Profile>>() {
            @Override
            public void onSuccess(GenieResponse<List<Profile>> genieResponse) {
                List<Profile> profileList = genieResponse.getResult();
                Assert.assertEquals(ADD_CHILD_NAME, profileList.get(0).getHandle());
                Assert.assertEquals(5, profileList.get(0).getAge());
                Assert.assertEquals("male", profileList.get(0).getGender());
                Assert.assertEquals(1, profileList.get(0).getStandard());
                Assert.assertEquals("Hindi", profileList.get(0).getMedium());
                Assert.assertEquals("ICSE", profileList.get(0).getBoard());
                Assert.assertEquals("@drawable/ic_avatar2", profileList.get(0).getAvatar());
            }

            @Override
            public void onError(GenieResponse<List<Profile>> genieResponse) {
            }
        });
    }

    /**
     * Scenario:User gives all valid  input and in the fourth screen it click back until it reached to first screen and then clicks on next button until it   reached final screen
     * Then:It should create a profile successfully with given input.
     */
    @Test
    public void test3ShouldNavigateProperlyWhenGenieBackButtonClicked() {

        mockIntent(false);

        fillUpScreen1(ADD_CHILD_NAME);

        fillUpScreen2("male");

        fillUpScreen3();

//        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
//                addGEInteractEvent(TelemetryStageId.MANAGE_CHILDREN, TelemetryAction.ADD_CHILD_INITIATE, InteractionType.TOUCH.getValue(), null, false).
//                addGEInteractEvent(TelemetryStageId.ADD_CHILD_NAME, null, InteractionType.SHOW.getValue(), null, false).
//                addGEInteractEvent(TelemetryStageId.ADD_CHILD_NAME, null, InteractionType.TOUCH.getValue(), null, true).
//                addGEInteractEvent(TelemetryStageId.ADD_CHILD_AGE_GENDER_CLASS, null, InteractionType.SHOW.getValue(), null, false).
//                addGEInteractEvent(TelemetryStageId.ADD_CHILD_AGE_GENDER_CLASS, null, InteractionType.TOUCH.getValue(), null, true).
//                addGEInteractEvent(TelemetryStageId.ADD_CHILD_MEDIUM_BOARD, null, InteractionType.SHOW.getValue(), null, false).
//                addGEInteractEvent(TelemetryStageId.ADD_CHILD_MEDIUM_BOARD, null, InteractionType.TOUCH.getValue(), null, true).
//                addGEInteractEvent(TelemetryStageId.ADD_CHILD_AVATAR, null, InteractionType.SHOW.getValue(), null, false).
//                build();
//
//        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
        GenieDBHelper.clearTelemetryTable();

        clickGenieBackButton();

        assertForScreen3("Hindi", "ICSE");


        clickGenieBackButton();

        assertForScreen2("5 Years");

        clickGenieBackButton();

        assertForScreen1(ADD_CHILD_NAME);

        ExpectedEventList expectedGEInteractEventList1 = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_MEDIUM_BOARD, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_AGE_GENDER_CLASS, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.ADD_CHILD_NAME, null, InteractionType.SHOW.getValue(), null, false).
                build();
        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList1);
        GenieDBHelper.clearTelemetryTable();
        clickNext();

        assertForScreen2("5 Years");

        clickNext();

        assertForScreen3("Hindi", "ICSE");

        clickNext();

        GenieDBHelper.deleteAllUserProfile();

        fillUpScreen4();

        sleep();

        ExpectedEventList expectedGEInteractEventList2 = new ExpectedEventList.ExpectedEventListBuilder().
                addCreateUserEvent().
                addGESessionEndEvent().
                addGESessionStartEvent().
                addCreateProfileEvent(ADD_CHILD_NAME, false).
                build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList2);


        CoreApplication.getGenieAsyncService().getUserService().getAllUserProfile(new IResponseHandler<List<Profile>>() {
            @Override
            public void onSuccess(GenieResponse<List<Profile>> genieResponse) {
                List<Profile> profileList = genieResponse.getResult();
                Assert.assertEquals(ADD_CHILD_NAME, profileList.get(0).getHandle());
                Assert.assertEquals(5, profileList.get(0).getAge());
                Assert.assertEquals("male", profileList.get(0).getGender());
                Assert.assertEquals(1, profileList.get(0).getStandard());
                Assert.assertEquals("Hindi", profileList.get(0).getMedium());
                Assert.assertEquals("ICSE", profileList.get(0).getBoard());
                Assert.assertEquals("@drawable/ic_avatar2", profileList.get(0).getAvatar());
            }

            @Override
            public void onError(GenieResponse<List<Profile>> genieResponse) {
            }
        });

    }

    /**
     * Scenario:User gives all valid  input for group
     * Then:It should navigate properly to first screen.
     */
    @Test
    public void test4ShouldNavigateProperlyForGroup() {

        mockIntent(true);

        fillUpScreen1(ADD_GROUP_NAME);

        clickGenieBackButton();

        assertForScreen1(ADD_GROUP_NAME);

    }

    /**
     * Scenario:User selects gender
     * Then:It should show avatars according to gender.
     */
    @Test
    public void test5ShouldShouldShowGenderSpecificAvatars() {

        mockIntent(false);

        fillUpScreen1(ADD_GROUP_NAME);

        fillUpScreen2("male");

        fillUpScreen3();

        Assert.assertEquals(7, ((FancyCoverFlow) mActivityRule.getActivity().getRunningFragment().getView().findViewById(R.id.coverflow_avatar)).getCount());

        clickGenieBackButton();

        clickGenieBackButton();

        fillUpGender("female");

        clickNext();

        clickNext();

        Assert.assertEquals(7, ((FancyCoverFlow) mActivityRule.getActivity().getRunningFragment().getView().findViewById(R.id.coverflow_avatar)).getCount());

        clickGenieBackButton();

        clickGenieBackButton();

        clickGenieBackButton();

        ViewAction.clickonView(R.id.layout_toggle);

        clickNext();


        Assert.assertEquals(10, ((FancyCoverFlow) mActivityRule.getActivity().getRunningFragment().getView().findViewById(R.id.coverflow_avatar)).getCount());


    }


    @Test
    public void test6ShouldAddaGroup() {

        mockIntent(true);

        fillUpScreen1(ADD_GROUP_NAME);

        GenieDBHelper.deleteAllUserProfile();

        fillUpScreen4();

        sleep();

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.MANAGE_GROUPS, TelemetryAction.ADD_GROUP_INITIATE, InteractionType.TOUCH.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.ADD_GROUP_NAME, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.ADD_GROUP_NAME, null, InteractionType.TOUCH.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.ADD_GROUP_AVATAR, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.ADD_GROUP_AVATAR, null, InteractionType.TOUCH.getValue(), null, true).
                addGEInteractEvent(TelemetryStageId.MANAGE_GROUPS, TelemetryAction.ADD_GROUP_SUCCESS, InteractionType.OTHER.getValue(), null, false).
                addCreateUserEvent().
                addGESessionEndEvent().
                addGESessionStartEvent().
                addCreateProfileEvent(ADD_GROUP_NAME, true).build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);

        CoreApplication.getGenieAsyncService().getUserService().getAllUserProfile(new IResponseHandler<List<Profile>>() {
            @Override
            public void onSuccess(GenieResponse<List<Profile>> genieResponse) {
                List<Profile> profileList = genieResponse.getResult();
                Assert.assertEquals(ADD_GROUP_NAME, profileList.get(0).getHandle());
                Assert.assertEquals(-1, profileList.get(0).getAge());
                Assert.assertEquals("", profileList.get(0).getGender());
                Assert.assertEquals(-1, profileList.get(0).getStandard());
                Assert.assertEquals("", profileList.get(0).getMedium());
                Assert.assertEquals("", profileList.get(0).getBoard());
                Assert.assertEquals("@drawable/ic_badge2", profileList.get(0).getAvatar());
            }

            @Override
            public void onError(GenieResponse<List<Profile>> genieResponse) {
            }
        });


    }
}
