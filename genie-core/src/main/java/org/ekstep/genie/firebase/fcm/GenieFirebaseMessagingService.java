package org.ekstep.genie.firebase.fcm;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.reflect.TypeToken;

import org.ekstep.genie.notification.NotificationManagerUtil;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.commons.bean.Notification;
import org.ekstep.genieservices.commons.utils.GsonUtil;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 7/28/2016.
 *
 * @author anil
 */
public class GenieFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "GenieFirebaseMessagingService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        LogUtil.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            LogUtil.d(TAG, "Message data payload: " + remoteMessage.getData());

            Map<String, String> data = remoteMessage.getData();
            if (data.containsKey("notificationpayload")) {
                String geniepayload = data.get("notificationpayload");

                NotificationManagerUtil notificationManagerUtil = new NotificationManagerUtil(GenieFirebaseMessagingService.this);
                notificationManagerUtil.handleNotification(GsonUtil.fromJson(geniepayload, Notification.class));
            } else if (data.containsKey("configpayload")) {
                String configpayload = data.get("configpayload");

                Type type = new TypeToken<HashMap>() {
                }.getType();
                Map mapData = GsonUtil.getGson().fromJson(configpayload, type);

                LogUtil.e(TAG, "mapData: " + mapData.toString());

                if (mapData.containsKey("supportedGenieVersions")) {
                    String supportedGenieVersions = GsonUtil.getGson().toJson(mapData.get("supportedGenieVersions"));

                    PreferenceUtil.setSupportedGenieVersions(supportedGenieVersions);
                }

                if (mapData.containsKey("telemetrySyncInterval")) {
                    String telemetrySyncInterval = GsonUtil.getGson().toJson(mapData.get("telemetrySyncInterval"));

                    PreferenceUtil.setTelemetrySyncInterval(telemetrySyncInterval);
                }

                if (mapData.containsKey("logLevel")) {
                    double logLevel = (double) mapData.get("logLevel");

                    //// TODO: 31/5/17  Add log level
//                    APILogger.appLoggingLevel = (int) logLevel;
                }
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            LogUtil.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

}
