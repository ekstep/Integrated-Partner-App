package org.ekstep.genie.telemetry;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.telemetry.Telemetry;

/**
 * Created on 5/16/2016.
 *
 * @author anil
 */
public class TelemetryHandler {

    private static final String TAG = TelemetryHandler.class.getSimpleName();

    public static void saveTelemetry(Telemetry event, IResponseHandler handler) {
        CoreApplication.getGenieSdkInstance().getAsyncService().getTelemetryService().saveTelemetry(event, handler);
    }

    public static void saveTelemetry(Telemetry event) {
        CoreApplication.getGenieSdkInstance().getAsyncService().getTelemetryService().saveTelemetry(event, new IResponseHandler<Void>() {
            @Override
            public void onSuccess(GenieResponse<Void> genieResponse) {
                LogUtil.i(TAG, "TelemetryEvent sent successfully");
            }

            @Override
            public void onError(GenieResponse<Void> genieResponse) {
                LogUtil.e(TAG, "TelemetryEvent sending Failed");
            }
        });
    }

}
