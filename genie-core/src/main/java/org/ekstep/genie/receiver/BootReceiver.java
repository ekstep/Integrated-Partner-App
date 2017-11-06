package org.ekstep.genie.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.ekstep.genie.notification.NotificationManagerUtil;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.commons.bean.Notification;
import org.ekstep.genieservices.commons.utils.DateUtil;

/**
 * Created on 8/3/2016.
 * <p>
 * The broadcast receiver which receives the events about the device.
 *
 * @author anil
 */
public class BootReceiver extends BroadcastReceiver {

    private static final int THRESHOLD_TIME = 6;    // in hours

    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;

        String intentAction = intent.getAction();
        if (intentAction == null) {
            return;
        }

        if (intentAction.equals(Intent.ACTION_BOOT_COMPLETED)) {
            // device finishes booting
            onDeviceBootCompleted();
        }
    }

    private void onDeviceBootCompleted() {
        registerOnboardNotification(DateUtil.getTimeDifferenceInHours(PreferenceUtil.getGenieFirstLaunchTime(), System.currentTimeMillis()));
    }

    private void registerOnboardNotification(long diffHours) {
        NotificationManagerUtil notificationManagerUtil = new NotificationManagerUtil(mContext);

        for (Notification genieNotification : notificationManagerUtil.getOnboardNotificationList()) {
            if (diffHours > PreferenceUtil.getOnBoardingNotificationState()
                    && diffHours < (genieNotification.getRelativetime() + THRESHOLD_TIME)) {

                notificationManagerUtil.handleNotification(genieNotification);
            }
        }
    }

}
