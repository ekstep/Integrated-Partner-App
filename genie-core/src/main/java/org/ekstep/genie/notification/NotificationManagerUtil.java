package org.ekstep.genie.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.reflect.TypeToken;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryConstant;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.ui.splash.SplashActivity;
import org.ekstep.genie.util.AlarmManagerUtil;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.RawUtil;
import org.ekstep.genie.util.SerializableUtil;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.GenieService;
import org.ekstep.genieservices.async.NotificationService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.Notification;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.ekstep.genieservices.commons.utils.DateUtil;
import org.ekstep.genieservices.commons.utils.GsonUtil;
import org.ekstep.genieservices.commons.utils.StringUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.ekstep.genie.notification.enums.NotificationActionId.DO_NOTHING;

/**
 * Singleton class for Notification manager
 *
 * @author anil
 */
public class NotificationManagerUtil {

    private static final String TAG = NotificationManagerUtil.class.getSimpleName();

    private static final int _1_SEC = 1000;
    private static final int _1_MIN = _1_SEC * 60;
    private static final int _1_HOUR = _1_MIN * 60;

    private Context mContext;

    public NotificationManagerUtil(Context context) {
        mContext = context;
    }

    public List<Notification> getOnboardNotificationList() {
        List<Notification> genieNotifications;
        String response = RawUtil.readRawFile(mContext, R.raw.genie_onboard_notification);
        if (!StringUtil.isNullOrEmpty(response)) {
            Type type = new TypeToken<ArrayList<Notification>>() {
            }.getType();
            genieNotifications = GsonUtil.fromJson(response, type);
        } else {
            genieNotifications = new ArrayList<>();
        }

        return genieNotifications;
    }

    /**
     * start driver location alarm service
     */
    public void handleNotification(final Notification notification) {
        boolean isSchedule = false;

        long triggerAtMillis;
        if (notification.getRelativetime() > 0) {

            // Local notification
            long time = _1_HOUR * notification.getRelativetime();
            triggerAtMillis = PreferenceUtil.getGenieFirstLaunchTime() + time;
            String triggerTime = DateUtil.format(triggerAtMillis, DateUtil.DATETIME_FORMAT);
            notification.setTime(triggerTime);
            isSchedule = true;

        } else {
            //Generate GE_INTERACT event for Server notification received.
            Map<String, Object> eksMap = new HashMap<>();
            eksMap.put(TelemetryConstant.NOTIFICATION_DATA, GsonUtil.getGson().toJson(notification));
            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.OTHER, TelemetryStageId.SERVER_NOTIFICATION, TelemetryAction.NOTIFICATION_RECEIVED, eksMap));
            // Server notification
            triggerAtMillis = DateUtil.parse(notification.getTime(), DateUtil.DATETIME_FORMAT).getMillis();
            long currentTime = DateUtil.getEpochTime();
            if (currentTime < triggerAtMillis + (notification.getValidity() * _1_MIN)) {
                isSchedule = true;
            }
        }
        NotificationService notificationService = GenieService.getAsyncService().getNotificationService();
        notificationService.addNotification(notification, new IResponseHandler<Void>() {
            @Override
            public void onSuccess(GenieResponse<Void> genieResponse) {
                if (notification.getRelativetime() == 0) {
                    LocalBroadcastManager.getInstance(CoreApplication.getInstance()).sendBroadcast(Util.getRefreshNotificationsIntent());
                }
            }

            @Override
            public void onError(GenieResponse<Void> genieResponse) {
                Util.showCustomToast(genieResponse.getErrorMessages().get(0));
            }
        });
        // Adding notifications.
//        NotificationUtil.addNotification(genieNotification);


        AlarmManagerUtil alarmManagerUtil = new AlarmManagerUtil(mContext);

        Intent intent = new Intent(mContext, LocalNotificationService.class);
        intent.setAction("" + Math.random());

        if (isSchedule) {
            Bundle extras = new Bundle();
            extras.putByteArray(Constant.BUNDLE_KEY_NOTIFICATION_DATA_MODEL, SerializableUtil.serialize(notification));
            intent.putExtras(extras);
            alarmManagerUtil.scheduleAlarm(intent, notification.getMsgid(), triggerAtMillis);
        }
    }

    /**
     * Create and show a simple notification
     *
     * @param genieNotification
     */
    public void handleNotificationAction(Notification genieNotification) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setSmallIcon(R.drawable.ic_ekstep_silhouette);
        } else {
            builder.setSmallIcon(R.drawable.ic_launcher);
        }

        builder.setContentTitle(genieNotification.getTitle());
        builder.setContentText(genieNotification.getMsg());
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(genieNotification.getMsg()));
        builder.setAutoCancel(true);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(defaultSoundUri);

        if (genieNotification.getActionid() != DO_NOTHING) {
            builder.setContentIntent(getPendingIntent(genieNotification));
        }

        android.app.Notification notification = builder.build();
//        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(genieNotification.getMsgid() /* ID of notification */, notification);
    }

    private PendingIntent getPendingIntent(Notification genieNotification) {
//        ParcelableUtil.marshall(genieNotification);

        Intent intent = new Intent(mContext, SplashActivity.class);

        intent.putExtra(Constant.BUNDLE_KEY_NOTIFICATION_DATA_MODEL, genieNotification);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, (int) System.currentTimeMillis() /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        return pendingIntent;
    }

}
