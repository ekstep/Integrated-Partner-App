package org.ekstep.genie.ui.notification;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.telemetry.enums.EntityType;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genieservices.GenieService;
import org.ekstep.genieservices.async.NotificationService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.Notification;
import org.ekstep.genieservices.commons.bean.NotificationFilterCriteria;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.ekstep.genieservices.commons.bean.enums.NotificationStatus;
import org.ekstep.genieservices.commons.utils.GsonUtil;
import org.ekstep.genieservices.commons.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shriharsh on 9/1/17.
 */

public class NotificationPresenter implements NotificationContract.Presenter {
    private static final String TAG = NotificationPresenter.class.getSimpleName();

    private NotificationContract.View mNotificationView;
    List<Notification> mNotificationList;
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
                        LogUtil.e(TAG, "notificationList Size : " + mNotificationList.size());
                        mNotificationView.initAdapter();
                        mNotificationView.setNotificationAdapter();
                        mNotificationView.setNotificationAdapterData(mNotificationList);
                        mNotificationView.notifyAdapterDataSetChanged();

                        // Generate GE_INTERCAT event
                        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.SHOW, TelemetryStageId.NOTIFICATION, EntityType.NOTIFICATION, String.valueOf(mNotificationList.size())));
                    } else {
                        LogUtil.i(TAG, "notificationList is empty");
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
