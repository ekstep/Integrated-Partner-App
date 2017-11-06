package org.ekstep.genie.ui;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.format.DateUtils;

import org.ekstep.genie.GenieTestBase;
import org.ekstep.genie.R;
import org.ekstep.genie.custom.action.ViewAction;
import org.ekstep.genie.custom.matcher.Matcher;
import org.ekstep.genie.notification.NotificationManagerUtil;
import org.ekstep.genie.ui.landing.LandingActivity;
import org.ekstep.genie.util.RawUtil;
import org.ekstep.genieservices.commons.bean.Notification;
import org.ekstep.genieservices.commons.utils.GsonUtil;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

/**
 * Created by Sneha on 2/2/2017.
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class _33_NotificationFragmentTest extends GenieTestBase {
    @Rule
    public ActivityTestRule<LandingActivity> mActivityRule = new ActivityTestRule<>(LandingActivity.class);

    @Override
    public void setup() {
    }

    @Override
    public void tearDown() {
    }

    /**
     * Navigate to the notification screen.
     */
    private void navigateToNotificationScreen() {
        ViewAction.navaigateToDrawerItems(1);
        Matcher.check(R.id.txt_header, R.string.label_nav_notifications);
    }

    @Test
    /**
     * Add notification in the screen
     */
    public void test0AddNotification() {
        navigateToNotificationScreen();
        String response = RawUtil.readRawFile(mActivityRule.getActivity(), R.raw.dummy_notification);
        NotificationManagerUtil notificationManagerUtil = new NotificationManagerUtil(mActivityRule.getActivity());
        notificationManagerUtil.handleNotification(GsonUtil.fromJson(response, Notification.class));
    }

//    @Test
    /**
     * Scenario : To check if there are unread notification.
     */
    public void test1CheckIfUnreadNotification() {

        // Wait for some time
        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        //click on navigation drawer icon
        ViewAction.clickonView(R.id.img_berger_menu);

        clearResource();

        //check if of drawer items are visible
        Matcher.isVisible(R.id.list_drawer);

        //check if the notification counter is visible
        Matcher.isVisible(R.id.ectv_notification_counter);

    }

    @Test
    /**
     * Scenario : When user clicks on the navigation in menu, navigation screen opens.
     */
    public void test2ShouldNavigateToNotificationFragment() {
        navigateToNotificationScreen();
    }

//    @Test
    /**
     * Scenario : Check if all the notifications are read.
     */
    public void test3CheckIfReadNotification() {

        // Wait for some time
        waitFor(DateUtils.SECOND_IN_MILLIS * 5);

        //click on navigation drawer icon
        ViewAction.clickonView(R.id.img_berger_menu);

        clearResource();

        //check if of drawer items are visible
        Matcher.isVisible(R.id.list_drawer);

        //check if the notification counter is visible
        Matcher.isNotVisible(R.id.ectv_notification_counter);

    }

    @Test
    /**
     * Scenario : On click of the notification in the notification screen, goes to the home activity.
     */
    public void test4OnClickNotificationView() {
        navigateToNotificationScreen();
        Matcher.isVisible(R.id.rv_notification);
        ViewAction.clickOnRecylerViewItem(R.id.rv_notification, 0);

    }

    @Test
    /**
     * Scenario : On click of the delete option in the notification screen, notification gets deleted.
     */
    public void test5OnClickNotificationDeleteOption() {
        navigateToNotificationScreen();
        Matcher.isVisible(R.id.parent);

        //Check if the delete option is available
        Matcher.isVisible(R.id.delete_img);

        //Click on the delete option
        ViewAction.clickonView(R.id.delete_img);

    }
}
