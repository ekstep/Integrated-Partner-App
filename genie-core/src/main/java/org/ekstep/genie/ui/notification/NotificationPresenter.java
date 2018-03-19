package org.ekstep.genie.ui.notification;

import android.app.Activity;
import android.content.Context;

import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.telemetry.EnvironmentId;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryConstant;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.telemetry.enums.EntityType;
import org.ekstep.genie.telemetry.enums.ImpressionType;
import org.ekstep.genieservices.GenieService;
import org.ekstep.genieservices.async.NotificationService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.Notification;
import org.ekstep.genieservices.commons.bean.NotificationFilterCriteria;
import org.ekstep.genieservices.commons.bean.enums.NotificationStatus;
import org.ekstep.genieservices.commons.utils.GsonUtil;
import org.ekstep.genieservices.commons.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shriharsh on 9/1/17.
 */

public class NotificationPresenter implements NotificationContract.Presenter {
    private static final String TAG = NotificationPresenter.class.getSimpleName();
    List<Notification> mNotificationList;
    private NotificationContract.View mNotificationView;
    private NotificationService mNotificationService;
    private Activity mActivity;

    public NotificationPresenter() {
        mNotificationService = GenieService.getAsyncService().getNotificationService();
    }

    @Override
    public List<Notification> getAllNotificationList() {
        NotificationFilterCriteria.Builder criteria = new NotificationFilterCriteria.Builder();
        criteria.notificationStatus(NotificationStatus.ALL);
        mNotificationService.getAllNotifications(criteria.build(), new IResponseHandler<List<Notification>>() {

            @Override
            public void onSuccess(GenieResponse<List<Notification>> genieResponse) {
                if (genieResponse != null) {
                    // List<Notification> notificationList = genieResponse.getResult();
                    mNotificationList = genieResponse.getResult();
                    if (mNotificationList != null && mNotificationList.size() > 0) {
                        List<Notification> notificationList = new ArrayList<>();
                        for (Notification notification : mNotificationList) {
                            Notification newNotification = null;
                            if (!StringUtil.isNullOrEmpty(notification.getNotificationJson()))
                                newNotification = GsonUtil.fromJson(notification.getNotificationJson(), Notification.class);
                            else
                                newNotification = notification;
                            notificationList.add(newNotification);
                        }
                        mNotificationList = notificationList;

                        mNotificationView.hideEmptyNotificationLayout();
                        mNotificationView.initAdapter();
                        mNotificationView.setNotificationAdapter();
                        mNotificationView.setNotificationAdapterData(mNotificationList);
                        mNotificationView.notifyAdapterDataSetChanged();

                        // Generate GE_INTERCAT event
                        Map<String, Object> params = new HashMap<>();
                        params.put(TelemetryConstant.NOTIFICATION_COUNT, String.valueOf(mNotificationList.size()));
                        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildImpressionEvent(EnvironmentId.HOME,TelemetryStageId.NOTIFICATION, ImpressionType.LIST, EntityType.NOTIFICATION));
                        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildLogEvent(EnvironmentId.HOME,TelemetryStageId.NOTIFICATION, ImpressionType.LIST, TelemetryStageId.NOTIFICATION, params));
                    } else {
                        Map<String, Object> params = new HashMap<>();
                        params.put(TelemetryConstant.NOTIFICATION_COUNT, "0");
                        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildImpressionEvent(EnvironmentId.HOME,TelemetryStageId.NOTIFICATION, ImpressionType.LIST, EntityType.NOTIFICATION));
                        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildLogEvent(EnvironmentId.HOME,TelemetryStageId.NOTIFICATION, ImpressionType.LIST, TelemetryStageId.NOTIFICATION, params));
                        mNotificationView.showEmptyNotificationLayout();
                    }
                }
            }

            @Override
            public void onError(GenieResponse<List<Notification>> genieResponse) {

            }
        });
        return mNotificationList;
    }

    @Override
    public void bindView(BaseView view, Context context) {
        mNotificationView = (NotificationContract.View) view;
        mActivity = (Activity) context;
    }

    @Override
    public void unbindView() {
        mNotificationView = null;
        mActivity = null;
    }
}
