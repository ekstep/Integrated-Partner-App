package org.ekstep.genie.ui;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.LinearLayout;

import org.ekstep.genie.GenieTestBase;
import org.ekstep.genie.R;
import org.ekstep.genie.custom.action.ViewAction;
import org.ekstep.genie.custom.matcher.Matcher;
import org.ekstep.genie.model.ExpectedEventList;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.ui.contentdetail.ContentDetailActivity;
import org.ekstep.genie.util.CommonUtil;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.GenieDBHelper;
import org.ekstep.genie.util.MockServer;
import org.ekstep.genie.util.SampleData;
import org.ekstep.genie.util.TelemetryVerifier;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;

/**
 * Created on 11/17/2016.
 *
 * @author swayangjit
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

// TODO: 27/7/17 add other required testcases

public class _16_ContentDetialsActivityTest extends GenieTestBase {
    IdlingResource idlingResource;
    private String numeracy = "Literacy";
    private String wrongText = "dfhlajds";
    private String searchContent = "Multiplication";
    private String searchText = "elephant";

    private MockServer mockServer;

    @Rule
    public ActivityTestRule<ContentDetailActivity> mActivityRule = new ActivityTestRule<ContentDetailActivity>(ContentDetailActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            GenieDBHelper.clearTelemetryTable();
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent result = getIntentData(targetContext);
            return result;
        }
    };

    public Intent getIntentData(Context context) {
        mockServer = new MockServer();
        try {
            mockServer.start();
        } catch (IOException e) {

        }
        mockServer.mockHttpResponse(SampleData.getContentResponseWithHugeEcar("org.ekstep.money.worksheet"), 200);
        Intent intent = new Intent(context, ContentDetailActivity.class);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_CONTENT_DATA, CommonUtil.stubContentData("org.ekstep.money.worksheet"));
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_IS_SPINE_AVAILABLE, true);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_LOCAL_PATH, "");
        return intent;
    }

    @Override
    public void setup() throws IOException {
        super.setup();
    }

    @Override
    public void tearDown() throws IOException {
        super.tearDown();
    }

    @Test
    public void test1clickOnPlayButton() {
        ViewAction.clickonView(R.id.tv_download);

        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        Matcher.isVisible(R.id.txt_play);

        ViewAction.clickonView(R.id.txt_play);

        waitFor(DateUtils.SECOND_IN_MILLIS * 1000);

        // Initialize UiDevice instance
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        Assert.assertNotNull(uiDevice);

        uiDevice.pressBack();
    }

    @Test
    public void test2clickOnShareButton() {
        Matcher.isVisible(R.id.item_share);

        ViewAction.clickonView(R.id.item_share);

        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        //Assert for "Share the file" layout
        Matcher.isVisible(R.id.layout_share_file);

        ViewAction.clickOnRecylerViewItem(R.id.resolver_recyclerview_file, 2);

        waitFor(DateUtils.SECOND_IN_MILLIS * 2000);

        // Initialize UiDevice instance
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        Assert.assertNotNull(uiDevice);

        uiDevice.pressBack();
    }

    @Test
    public void test3clickOnDeleteButton() {
        //Click on Delete
        ViewAction.clickonView(R.id.iv_delete);

        // Wait for some time
        waitFor(DateUtils.SECOND_IN_MILLIS * 10);

        clearResource();

        //Assert if Yes layout is showing or not
        Matcher.isVisible(R.id.delete_yes);

        //Assert if No layout is showing or not
        Matcher.isVisible(R.id.delete_no);

        //Click on Yes
        ViewAction.clickonString(R.id.delete_yes, R.string.label_dialog_yes);

        //Assert for content deletion toast
        Matcher.isToastMessageDisplayed(R.string.msg_content_deletion_sucessfull);

        //Assert if Yes layout is showing
        Matcher.isNotVisible(R.id.delete_yes);

        ExpectedEventList expectedGEInteractEventList = new ExpectedEventList.ExpectedEventListBuilder().
                addGEInteractEvent(TelemetryStageId.MY_CONTENT, null, InteractionType.SHOW.getValue(), null, false).
                addGEInteractEvent(TelemetryStageId.MY_CONTENT, TelemetryAction.DELETE_CONTENT_INITIATED, InteractionType.TOUCH.getValue(), null, false).
                build();

        TelemetryVerifier.verifyGEEvents(expectedGEInteractEventList);
    }

    @Test
    public void test4ShouldCanceltheDownload() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ViewAction.clickonView(R.id.tv_download);
            }
        }).start();

        // Initialize UiDevice instance
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        Assert.assertNotNull(uiDevice);

        uiDevice.wait(Until.hasObject(By.desc("CancelDownload")), 10000);

        UiObject descriptionElement = uiDevice.findObject(new UiSelector().className(LinearLayout.class.getName()).description("CancelDownload"));
        try {
            descriptionElement.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        uiDevice.wait(Until.hasObject(By.desc("Download")), 10000);
        Assert.assertEquals(View.GONE, mActivityRule.getActivity().findViewById(R.id.layout_downnload_progress).getVisibility());
        Assert.assertEquals(View.VISIBLE, mActivityRule.getActivity().findViewById(R.id.tv_download).getVisibility());
    }

    @Test
    public void test5clickOnPreviewButton() {
        Matcher.isVisible(R.id.iv_preview);

        ViewAction.clickonView(R.id.iv_preview);
    }

}
