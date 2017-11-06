package org.ekstep.genie.util.geniesdk;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.model.enums.SyncConfiguration;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryConstant;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.async.SyncService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.SyncStat;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.ekstep.genieservices.commons.network.IConnectionInfo;

import java.util.HashMap;

/**
 * This helper class is used to call the Sync service methods
 * <p/>
 * Created by Kannappan on 8/19/2016.
 */
public class SyncServiceUtil {
    private static final String TAG = SyncServiceUtil.class.getSimpleName();


    public static SyncService getSyncService() {
        return CoreApplication.getGenieSdkInstance().getAsyncService().getSyncService();
    }

    public static void syncWithConfig(String stageId) {
        if (getConfiguration().canSync(CoreApplication.getGenieSdkInstance().getConnectionInfo())) {
            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, stageId, TelemetryAction.AUTO_SYNC_INITIATED));
            getSyncService().sync(new IResponseHandler<SyncStat>() {
                @Override
                public void onSuccess(GenieResponse<SyncStat> genieResponse) {
                    LogUtil.i(TAG, "Auto Sync Success");
                    TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.OTHER, TelemetryStageId.TELEMETRY_SYNC, TelemetryAction.AUTO_SYNC_SUCCESS, getFileSizeMap(genieResponse.getResult())));
                }

                @Override
                public void onError(GenieResponse<SyncStat> genieResponse) {
                    LogUtil.e(TAG, "Auto Sync Failure");
                }
            });
        }
    }

    public static SyncConfiguration getConfiguration() {
        String syncConfig = PreferenceUtil.getSyncConfiguration();
        return SyncConfiguration.valueOf(syncConfig);
    }

    public static void setConfiguration(SyncConfiguration configuration) {
        PreferenceUtil.setSyncConfiguration(configuration.toString());
    }

    public static HashMap<String, Object> getFileSizeMap(SyncStat syncStat) {
        HashMap<String, Object> map = new HashMap();
        if (syncStat != null && syncStat.getSyncedFileSize() != null) {
            map.put(TelemetryConstant.SIZE_OF_FILE_IN_KB, syncStat.getSyncedFileSize());
        }
        return map;
    }

    /**
     * @return TRUE - 1. Ionly if the user has configured setting  to MANUAL an internet is connected.
     */
    public static boolean shouldShowSyncPrompt() {
        boolean syncPrompt = false;
        IConnectionInfo connectionInfo = CoreApplication.getGenieSdkInstance().getConnectionInfo();
        switch (getConfiguration()) {
            case MANUAL:
                if (connectionInfo.isConnected()) {
                    syncPrompt = true;
                }
                break;
            case OVER_WIFI_ONLY:
                syncPrompt = false;
                break;
            case OVER_ANY_MODE:
                syncPrompt = false;
                break;
            default:
                break;
        }
        return syncPrompt;
    }

    /**
     * @return TRUE - 1. If the sync setting is set to “Manual” and the user is connected to internet Or
     * 2. If the sync setting is set to “Automatic over Wifi” and the user is connected to 2G/3G/4G.
     * False - if the user is not connected to the internet or  connected to other mode.
     */
    private static boolean isInternetConnected(IConnectionInfo connectionInfo) {
        boolean syncPrompt = false;
        switch (getConfiguration()) {
            case MANUAL:
                syncPrompt = connectionInfo.isConnected();
                break;
            case OVER_WIFI_ONLY:
                if (connectionInfo.isConnectedOverWifi()) {
                    syncPrompt = false;
                } else if (connectionInfo.isConnected()) {
                    syncPrompt = true;
                }
                break;
            case OVER_ANY_MODE:
                syncPrompt = false;
                break;
            default:
                break;
        }
        return syncPrompt;
    }

}
