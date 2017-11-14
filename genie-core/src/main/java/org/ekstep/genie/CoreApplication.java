package org.ekstep.genie;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryOperation;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.FileHandler;
import org.ekstep.genie.util.ForegroundService;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.GenieService;
import org.ekstep.genieservices.async.GenieAsyncService;
import org.ekstep.genieservices.async.SyncService;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.ScanStorageRequest;
import org.ekstep.genieservices.commons.bean.ScanStorageResponse;
import org.ekstep.genieservices.commons.utils.StringUtil;
import org.ekstep.genieservices.eventbus.EventBus;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.ekstep.genieservices.GenieService.getAsyncService;

public class CoreApplication extends Application implements ForegroundService.OnForegroundChangeListener {
    private static CoreApplication instance;
    private int mGenieRunningStatus = -1;

    public static CoreApplication getInstance() {
        return instance;
    }

    public static GenieService getGenieSdkInstance() {
        return GenieService.getService();
    }

    public static GenieAsyncService getGenieAsyncService() {
        return getAsyncService();
    }

    public static SyncService getSyncService() {
        return getAsyncService().getSyncService();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initGenieSdk();
        PreferenceUtil.initPreference(this);
        registerActivityLifecycleCallbacks(ForegroundService.getInstance());
        ForegroundService.getInstance().registerListener(this);
        GenieSdkEventListner.init(this);
        TelemetryOperation.startSyncingTelemetry();
        setMobileasDefaultStorageOption();
        appInit();

    }

    protected void appInit() {

    }

    protected String getConfigPackageName() {
        return "org.ekstep.genie";
    }


    private void initGenieSdk() {
        GenieService.init(this, getConfigPackageName());
    }

    public String getClientPackageName() {
        return getConfigPackageName();
    }

    /**
     * Setter method to set Genie is running
     */
    public void setGenieAlive(int status) {
        mGenieRunningStatus = status;
    }

    /**
     * Check if Genie is Running or not
     */
    public int isGenieAlive() {
        return mGenieRunningStatus;
    }


    private void setMobileasDefaultStorageOption() {
        if (PreferenceUtil.getDefaultStorageOption() == null) {
            Map<String, String> map = new HashMap();
            map.put(Constant.DEFAULT_STORAGE, Constant.DEFAULT_STORAGE_MOBILE);
            map.put(Constant.DEFAULT_STORAGE_PATH, FileHandler.getExternalFilesDir(this).toString());
            PreferenceUtil.setDefaultStorageOption(map);
        }

    }

    @Override
    public void onSwitchBackground() {
        if (mGenieRunningStatus == 1) {
            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildInturrptEvent());
        }
        TelemetryOperation.shutDownSchedulers();
    }

    @Override
    public void onSwitchForeground() {
        TelemetryOperation.startSyncingTelemetry();
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildResumeEvent());

        ScanStorageRequest.Builder builder = new ScanStorageRequest.Builder();

        String filepath = FileHandler.getDefaultStoragePath(this);
        if (!StringUtil.isNullOrEmpty(filepath)) {
            File file = new File(filepath);
            if (file.exists()) {
                builder.storagePath(filepath);
                GenieResponse<List<ScanStorageResponse>> response = getGenieSdkInstance().getContentService().scanStorage(builder.build());
                if (response != null && response.getResult() != null && response.getResult().size() > 0) {
                    EventBus.postEvent(response.getResult());
                }
            } else {
            }
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
