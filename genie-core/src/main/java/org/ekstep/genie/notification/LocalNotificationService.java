package org.ekstep.genie.notification;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryConstant;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.SerializableUtil;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.commons.bean.Notification;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.ekstep.genieservices.commons.utils.GsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 8/2/2016.
 *
 * @author anil
 */
public class LocalNotificationService extends IntentService {

    private static final String TAG = LocalNotificationService.class.getSimpleName();

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public LocalNotificationService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LogUtil.d(TAG, "onHandleIntent");

        if (intent != null) {
            Notification genieNotification = null;

            byte[] bytes = intent.getByteArrayExtra(Constant.BUNDLE_KEY_NOTIFICATION_DATA_MODEL);
            if (bytes != null) {
                genieNotification = (Notification) SerializableUtil.deserialize(bytes);
            }

            if (genieNotification != null) {
                Map<String, Object> eksMap = new HashMap<>();
                eksMap.put(TelemetryConstant.NOTIFICATION_DATA, GsonUtil.toJson(genieNotification));
                if (genieNotification.getRelativetime() > 0) {
                    PreferenceUtil.setOnBoardingNotificationState(genieNotification.getRelativetime());

                    //        TODO: (s)to be implemented
//                    TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.OTHER, TelemetryStageId.LOCAL_NOTIFICATION, TelemetryAction.NOTIFICATION_DISPLAYED, String.valueOf(genieNotification.getMsgid()), eksMap));
                    LocalBroadcastManager.getInstance(this).sendBroadcast(Util.getRefreshNotificationsIntent());
                } else {

                    //        TODO: (s)to be implemented
//                    TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.OTHER, TelemetryStageId.SERVER_NOTIFICATION, TelemetryAction.NOTIFICATION_DISPLAYED, String.valueOf(genieNotification.getMsgid()), eksMap));
                }

                NotificationManagerUtil notificationManagerUtil = new NotificationManagerUtil(LocalNotificationService.this);
                notificationManagerUtil.handleNotificationAction(genieNotification);
            }
        }
    }

}
