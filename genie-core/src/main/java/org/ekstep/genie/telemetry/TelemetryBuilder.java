package org.ekstep.genie.telemetry;

import android.content.Context;
import android.text.TextUtils;

import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.GenieService;
import org.ekstep.genieservices.commons.ILocationInfo;
import org.ekstep.genieservices.commons.bean.CorrelationData;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.ekstep.genieservices.commons.bean.telemetry.DeviceSpecification;
import org.ekstep.genieservices.commons.bean.telemetry.End;
import org.ekstep.genieservices.commons.bean.telemetry.Error;
import org.ekstep.genieservices.commons.bean.telemetry.Impression;
import org.ekstep.genieservices.commons.bean.telemetry.Interact;
import org.ekstep.genieservices.commons.bean.telemetry.Interrupt;
import org.ekstep.genieservices.commons.bean.telemetry.Log;
import org.ekstep.genieservices.commons.bean.telemetry.Start;

import java.util.List;
import java.util.Map;

/**
 * Created by anil on 5/17/2016.
 * <p/>
 * All the events related to interaction.
 */
public class TelemetryBuilder {
    /**
     * Start event
     *
     * @param context
     * @return
     */
    public static Start buildStartEvent(Context context, String pageId, String type, String mode, String objectId, String objectType, String objectVersion) {

        DeviceSpecification deviceSpec = new DeviceSpecification();
        deviceSpec.setOs("Android " + org.ekstep.genieservices.utils.DeviceSpec.getOSVersion());
        deviceSpec.setMake(org.ekstep.genieservices.utils.DeviceSpec.getDeviceName());
        deviceSpec.setId(org.ekstep.genieservices.utils.DeviceSpec.getAndroidId(context));

        String internalMemory = Util.bytesToHuman(org.ekstep.genieservices.utils.DeviceSpec.getTotalInternalMemorySize());
        if (!TextUtils.isEmpty(internalMemory)) {
            deviceSpec.setIdisk(Double.valueOf(internalMemory));
        }

        String externalMemory = Util.bytesToHuman(org.ekstep.genieservices.utils.DeviceSpec.getTotalExternalMemorySize());
        if (!TextUtils.isEmpty(externalMemory)) {
            deviceSpec.setEdisk(Double.valueOf(externalMemory));
        }

        String screenSize = org.ekstep.genieservices.utils.DeviceSpec.getScreenInfoinInch(context);
        if (!TextUtils.isEmpty(screenSize)) {
            deviceSpec.setScrn(Double.valueOf(screenSize));
        }

        String[] cameraInfo = org.ekstep.genieservices.utils.DeviceSpec.getCameraInfo(context);
        String camera = "";
        if (cameraInfo != null) {
            camera = TextUtils.join(",", cameraInfo);
        }
        deviceSpec.setCamera(camera);

        deviceSpec.setCpu(org.ekstep.genieservices.utils.DeviceSpec.getCpuInfo());
        deviceSpec.setSims(-1);

        ILocationInfo locationInfo = GenieService.getService().getLocationInfo();

        Start start = new Start.Builder()
                .environment(EnvironmentId.HOME)
                .deviceSpecification(deviceSpec)
                .loc(locationInfo.getLocation())
                .pageId(pageId)
                .type(type)
                .mode(mode)
                .objectId(objectId)
                .objectType(objectType)
                .objectVersion(objectVersion)
                .build();

        return start;
    }

    /**
     * End event.
     *
     * @return
     */
    public static End buildEndEvent(String type, String mode, String pageId, String objectId, String objectType, String objectVersion) {
        long timeInSeconds = 0;
        String genieStartTime = PreferenceUtil.getGenieStartTimeStamp();

        if (!TextUtils.isEmpty(genieStartTime)) {
            long timeDifference = System.currentTimeMillis() - Long.valueOf(genieStartTime);
            timeInSeconds = (timeDifference / 1000);
        }

        End end = new End.Builder()
                .environment(EnvironmentId.HOME)
                .duration(timeInSeconds)
                .pageId(pageId)
                .type(type)
                .mode(mode)
                .objectId(objectId)
                .objectType(objectType)
                .objectVersion(objectVersion)
                .build();

        return end;
    }

    /**
     * Impression Event
     */
    public static Impression buildImpressionEvent(String env, String pageId, String type) {
        Impression impression = new Impression.Builder().environment(env).pageId(pageId).type(type).build();
        return impression;
    }

    public static Impression buildImpressionEvent(String env, String pageId, String type, String subType) {
        Impression impression = new Impression.Builder().environment(env).pageId(pageId).type(type).subType(subType).build();
        return impression;
    }

    public static Impression buildImpressionEvent(String env, String pageId, String type, String subType, String id, String objType) {
        Impression impression = new Impression.Builder().environment(env).pageId(pageId).type(type).subType(subType).objectId(id).objectType(objType).build();
        return impression;
    }

    public static Impression buildImpressionEvent(String env, String pageId, String type, String subType, String id, String objType, String objVer) {
        Impression impression = new Impression.Builder().environment(env).pageId(pageId).type(type).subType(subType).objectId(id).objectType(objType).objectVersion(objVer).build();
        return impression;
    }

    public static Impression buildImpressionEvent(String env, String pageId, String type, String subType, String id, String objType, List<CorrelationData> cdata) {
        Impression impression = new Impression.Builder().environment(env).pageId(pageId).type(type).subType(subType).objectId(id).objectType(objType).correlationData(cdata).build();
        return impression;
    }

    public static Impression buildImpressionEvent(String env, String pageId, String type, String subType, List<CorrelationData> cdata) {
        Impression impression = new Impression.Builder().environment(env).pageId(pageId).type(type).subType(subType).correlationData(cdata).build();
        return impression;
    }

    public static Impression buildImpressionEvent(String env, String pageId, String type, String subType, String id, String objType, String objVersion, List<CorrelationData> cdata) {
        Impression impression = new Impression.Builder().environment(env).pageId(pageId).type(type).subType(subType).objectId(id).objectType(objType).objectVersion(objVersion).correlationData(cdata).build();
        return impression;
    }

    /**
     * Log Event
     *
     * @param pageId
     * @param type
     * @param params
     * @return
     */
    public static Log buildLogEvent(String env, String pageId, String type, String message, Map<String, Object> params) {
        Log.Builder log = new Log.Builder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            log.addParam(entry.getKey(), entry.getValue()).environment(env).pageId(pageId).type(type).level(Log.Level.INFO).message(message);
        }
        return log.build();
    }

    /**
     * TODO: in all the interact events resource id value is pageId, should be replaced with actual expected value later.
     *
     * @param type
     * @param subType
     * @param pageId
     * @return
     */
    public static Interact buildInteractEvent(String env, InteractionType type, String subType, String pageId) {
        Interact interact = new Interact.Builder().environment(env).interactionType(type).subType(subType).pageId(pageId).resourceId(pageId).build();
        return interact;
    }


    public static Interact buildInteractEvent(String env, InteractionType type, String subType, String pageId, List<CorrelationData> cdata) {
        Interact interact = new Interact.Builder().environment(env).interactionType(type).subType(subType).pageId(pageId).correlationData(cdata).resourceId(pageId).build();
        return interact;
    }

    public static Interact buildInteractEvent(String env, InteractionType type, String subType, String pageId, String key, Object value) {
        Interact.Builder interact = new Interact.Builder().environment(env).interactionType(type)
                .subType(subType)
                .pageId(pageId)
                .resourceId(pageId).addValue(key, value);
        return interact.build();
    }

    public static Interact buildInteractEvent(String env, InteractionType type, String subType, String pageId, String objectId, String objectType, String key, Object value) {
        Interact.Builder interact = new Interact.Builder().environment(env).interactionType(type)
                .subType(subType)
                .pageId(pageId)
                .resourceId(pageId).addValue(key, value).objectId(objectId).objectType(objectType);
        return interact.build();
    }

    public static Interact buildInteractEvent(String env, InteractionType type, String subType, String pageId, Map<String, Object> values) {
        Interact.Builder interact = new Interact.Builder().environment(env).interactionType(type).subType(subType).pageId(pageId).resourceId(pageId);
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            interact.addValue(entry.getKey(), entry.getValue());
        }
        return interact.build();
    }

    public static Interact buildInteractEvent(String env, InteractionType type, String subType, String pageId, String id, String objectType) {
        Interact interact = new Interact.Builder().environment(env).interactionType(type).subType(subType).pageId(pageId).objectId(id).objectType(objectType).resourceId(pageId).build();
        return interact;
    }

    public static Interact buildInteractEvent(String env, InteractionType type, String subType, String pageId, String id, String objectType, Map<String, Object> values) {

        Interact.Builder interact = new Interact.Builder().environment(env).interactionType(type).subType(subType).pageId(pageId).objectId(id).objectType(objectType).resourceId(pageId);
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            interact.addValue(entry.getKey(), entry.getValue());
        }
        return interact.build();
    }

    public static Interact buildInteractEvent(String env, InteractionType type, String subType, String pageId, String id, String objectType, Map<String, Object> values, List<CorrelationData> cdata) {
        Interact.Builder interact = new Interact.Builder().environment(env).interactionType(type).subType(subType).pageId(pageId).objectId(id).objectType(objectType).correlationData(cdata).resourceId(pageId);
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            interact.addValue(entry.getKey(), entry.getValue());
        }
        return interact.build();
    }

    public static Interact buildInteractEvent(String env, InteractionType type, String subType, String pageId, String id, String objType, String objVersion) {
        Interact interact = new Interact.Builder().environment(env).interactionType(type).subType(subType).pageId(pageId).objectId(id).objectType(objType).objectVersion(objVersion).resourceId(pageId).build();
        return interact;
    }

    public static Interrupt buildInterruptEvent(String type, String pageId) {
        Interrupt interrupt = new Interrupt.Builder().environment(EnvironmentId.HOME).type(type).pageId(pageId).build();
        return interrupt;
    }

    /**
     * build error event
     *
     * @param errorCode
     * @param stackTrace
     * @return
     */
    public static Error buildErrorEvent(String errorCode, String stackTrace) {
        Error error = new Error.Builder().environment(EnvironmentId.GENIE).errorCode(errorCode).
                errorType(Error.Type.MOBILE_APP).stacktrace(stackTrace).build();
        return error;
    }
}
