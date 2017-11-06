package org.ekstep.genie.ui;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.text.format.DateUtils;

import org.ekstep.genie.DeeplinkTestActivity;
import org.ekstep.genie.GenieTestBase;
import org.ekstep.genie.R;
import org.ekstep.genie.custom.action.ViewAction;
import org.ekstep.genie.custom.matcher.Matcher;
import org.ekstep.genie.model.ExpectedEventList;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.telemetry.enums.EntityType;
import org.ekstep.genie.util.CommonUtil;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.EcarCopyUtil;
import org.ekstep.genie.util.GenieDBHelper;
import org.ekstep.genie.util.SampleData;
import org.ekstep.genie.util.Session;
import org.ekstep.genie.util.TelemetryVerifier;
import org.ekstep.genieservices.commons.bean.HierarchyInfo;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.intent.matcher.BundleMatchers.hasEntry;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by swayangjit_gwl on 8/17/2016.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class _02_DeepLinkActivityTest extends GenieTestBase {

    @Rule
    public IntentsTestRule<DeeplinkTestActivity> mActivityTestRule = new IntentsTestRule<>(DeeplinkTestActivity.class);

    @Override
    public void setup() throws IOException {
        super.setup();
        GenieDBHelper.clearTelemetryTable();
        Session.getInstance().setFirstTime("true");
    }

    @Override
    public void tearDown() throws IOException {
        shutDownMockServer();
    }

    @Test
    public void test0CopyAllNecessoryFiles() {
        EcarCopyUtil.createFileFromAsset(SAMPLE_ECAR, DESTINATION);
    }

    @Test
    public void test1ShouldNavigateToContentDetialsActivity() {
        startMockServer();

        //Mockcontent details live API
        mMockServer.mockHttpResponse(SampleData.getContentResponse(SAMPLE_IDENTIFIER), 200);
        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        //Click on language Deeplink button 1
        ViewAction.scrollANDClickView(R.id.btn_deeplink9);
        clearResource();

        //Assert whether it is navigated to LessonDetails activity or not
        Matcher.check(R.id.txt_header, R.string.title_contentdetail);
        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.CONTENT_DETAIL, EntityType.CONTENT, InteractionType.SHOW.getValue(), SAMPLE_IDENTIFIER, false).
                build();
        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }

    @Test
    public void test2ShouldNavigateToManageContentFragment() {
        startMockServer();

        mMockServer.mockHttpResponse(SampleData.getPageAssembleResponseData(), 200);

        //Click on language Deeplink button 3
        Espresso.onView(ViewMatchers.withId(R.id.btn_deeplink3)).perform(ViewActions.click());

        //Assert whether it is navigated to LessonDetails activity or not
        Matcher.check(R.id.layout_skip, R.string.action_home_skip);

    }


    @Test
    public void test31ShouldShowProgramCodeNullError() {

        ViewAction.clickonView(R.id.btn_deeplink4);

        //Assert for error message
        Matcher.isToastMessageDisplayed(R.string.error_tag_cant_be_empty);
        waitFor(DateUtils.SECOND_IN_MILLIS * 4);
        Matcher.isVisible(R.id.fancyCoverFlow);
        clearResource();
        ExpectedEventList expectedEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, TelemetryAction.LANGUAGE_SETTINGS_INITIATE, InteractionType.TOUCH.getValue(), null, false).
                build();
        TelemetryVerifier.verifyGEEvents(expectedEventList);


    }

    @Test
    public void test32ShouldShowAlphaNumericProgramCodeError() {

        //Click on language Deeplink button 5
        ViewAction.scrollANDClickView(R.id.btn_deeplink5);

        //Assert for error message
        Matcher.isToastMessageDisplayed(R.string.error_tag_should_be_alpha_numeric);
        waitFor(DateUtils.SECOND_IN_MILLIS * 4);
        Matcher.isVisible(R.id.fancyCoverFlow);
        clearResource();
        ExpectedEventList expectedEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, TelemetryAction.LANGUAGE_SETTINGS_INITIATE, InteractionType.TOUCH.getValue(), null, false).
                build();
        TelemetryVerifier.verifyGEEvents(expectedEventList);
    }

    @Test
    public void test33ShouldShowInvalidTagError() {
        //Click on language Deeplink button 6
        ViewAction.scrollANDClickView(R.id.btn_deeplink6);

        //Assert for error message
        Matcher.isToastMessageDisplayed(R.string.error_tag_inavlid);

        waitFor(DateUtils.SECOND_IN_MILLIS * 4);
        Matcher.isVisible(R.id.fancyCoverFlow);
        clearResource();
        ExpectedEventList expectedEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, TelemetryAction.LANGUAGE_SETTINGS_INITIATE, InteractionType.TOUCH.getValue(), null, false).
                build();
        TelemetryVerifier.verifyGEEvents(expectedEventList);

    }

    @Test
    public void test34ShouldSuccessfullyAddTag() {

        //Click on  Deeplink button 7
        ViewAction.scrollANDClickView(R.id.btn_deeplink7);
        Matcher.isToastMessageDisplayed(R.string.msg_tag_added_sussessfully);
        waitFor(DateUtils.SECOND_IN_MILLIS * 4);
        Matcher.isVisible(R.id.fancyCoverFlow);
        clearResource();
        ExpectedEventList expectedEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.SPLASH, TelemetryAction.ADD_TAG_DEEP_LINK, InteractionType.TOUCH.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.SETTINGS_HOME, TelemetryAction.LANGUAGE_SETTINGS_INITIATE, InteractionType.TOUCH.getValue(), null, false).
                build();
        TelemetryVerifier.verifyGEEvents(expectedEventList);
    }


    @Test
    public void test41ShouldNavigateToContentDetialsActivityForCanvasDeeplink() {

        startMockServer();

        //Mock content live api response
        mMockServer.mockHttpResponse(SampleData.getContentResponse(SAMPLE_IDENTIFIER), 200);

        //Click on  Deeplink button 9
        ViewAction.scrollANDClickView(R.id.btn_deeplink9);
        hasExtras(allOf(
                hasEntry(equalTo(Constant.BundleKey.BUNDLE_KEY_DEEP_LINK_IDENTIFIER), equalTo(SAMPLE_IDENTIFIER))));
        waitFor(DateUtils.SECOND_IN_MILLIS * 4);

        //Assert whether it is navigated to LessonDetails activity or not
        Matcher.check(R.id.txt_header, R.string.title_contentdetail);
        clearResource();
        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.CONTENT_DETAIL, EntityType.CONTENT, InteractionType.SHOW.getValue(), SAMPLE_IDENTIFIER, false).
                build();


        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);

    }

    @Test
    public void test42ShouldNavigateToContentDetialsActivityWithHierarchyInfo() {
        startMockServer();
        mMockServer.mockHttpResponse(SampleData.getContentResponse(SAMPLE_IDENTIFIER), 200);
        ViewAction.scrollANDClickView(R.id.btn_deeplink8);

        List<HierarchyInfo> hierarchyInfoList = new ArrayList<>();
        hierarchyInfoList.add(new HierarchyInfo("do_2121925679111454721253", "Collection"));
        hierarchyInfoList.add(new HierarchyInfo("do_30022230", "Collection"));
        hasExtras(allOf(
                hasEntry(equalTo(Constant.BundleKey.BUNDLE_KEY_DEEP_LINK_IDENTIFIER), equalTo("do_30014516")),
                hasEntry(equalTo(Constant.BundleKey.BUNDLE_KEY_HIERARCHY_INFO), equalTo(hierarchyInfoList))));
        waitFor(DateUtils.SECOND_IN_MILLIS * 4);
        Matcher.check(R.id.txt_header, R.string.title_contentdetail);
        clearResource();
        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.CONTENT_DETAIL, EntityType.CONTENT, InteractionType.SHOW.getValue(), "do_30014516", false).
                build();
        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);

    }

    @Test
    public void test4ShouldImportContent() {
        GenieDBHelper.deleteAllContent();

        //Click on Import content button
        ViewAction.scrollANDClickView(R.id.btn_deeplink10);
        Matcher.isToastMessageDisplayed(R.string.msg_import_success);
        ExpectedEventList expectedEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.IMPORT_CONTENT, TelemetryAction.CONTENT_IMPORT_INITIATED, InteractionType.TOUCH.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.IMPORT_CONTENT, TelemetryAction.CONTENT_IMPORT_SUCCESS, InteractionType.OTHER.getValue(), CONTENT_IDENTIFIER, false).
                addGETransfer(1, CommonUtil.Constants.IMPORT_DATATYPE, CommonUtil.Constants.IMPORT_DIRECTION).
                build();
        TelemetryVerifier.verifyGEEvents(expectedEventList);
        GenieDBHelper.clearTelemetryTable();
        deleteContent(CONTENT_IDENTIFIER);


    }

}
