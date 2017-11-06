package org.ekstep.genie;

import android.content.Context;

import org.ekstep.genieservices.commons.bean.ContentImportResponse;
import org.ekstep.genieservices.commons.bean.DownloadProgress;
import org.ekstep.genieservices.commons.bean.ImportContentProgress;
import org.ekstep.genieservices.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by swayangjit on 16/6/17.
 */

public class GenieSdkEventListner {
    private static GenieSdkEventListner instance = null;
    private static int randomNumber;
    private final String TAG = GenieSdkEventListner.class.getSimpleName();
    private Context context = null;

    private GenieSdkEventListner(Context context) {
        this.context = context;
        register();
    }

    public static void init(Context context) {
        instance = new GenieSdkEventListner(context);
    }

    public static void destroy() {
        instance.unregister();
    }

    private void register() {
        EventBus.registerSubscriber(this);
    }

    private void unregister() {
        EventBus.unregisterSubscriber(this);
    }

    @Subscribe()
    public void onContentImportResponse(ContentImportResponse contentImportResponse) throws InterruptedException {
//        NotificationManagerUtil notificationManagerUtil = new NotificationManagerUtil(context);
//        if (contentImportResponse.getStatus() == IDownloadManager.STARTED) {
//            randomNumber = Util.getRandomInteger(100, 10);
//            notificationManagerUtil.handleNotificationAction(new GenieNotification("Content", "Content" + " is importing into Genie", randomNumber, DO_NOTHING.getValue()));
//        } else {
//            notificationManagerUtil.handleNotificationAction(new GenieNotification("Content", "Content"+ " is successfully imported into Genie.", randomNumber, MY_CONTENT_SCREEN.getValue()));
//        }
    }

    @Subscribe()
    public void onImportContentProgress(ImportContentProgress importContentProgress) throws InterruptedException {
    }


    @Subscribe()
    public void onDownloadProgress(DownloadProgress downloadProgress) throws InterruptedException {
    }
}
