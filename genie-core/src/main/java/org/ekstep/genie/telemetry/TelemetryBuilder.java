package org.ekstep.genie.telemetry;

import android.content.Context;
import android.text.TextUtils;

import org.ekstep.genie.model.DeviceSpecification;
import org.ekstep.genie.model.UTMData;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.GenieService;
import org.ekstep.genieservices.commons.ILocationInfo;
import org.ekstep.genieservices.commons.bean.CorrelationData;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.ekstep.genieservices.commons.bean.telemetry.GEEvent;
import org.ekstep.genieservices.commons.bean.telemetry.GEInteract;
import org.ekstep.genieservices.utils.DeviceSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anil on 5/17/2016.
 * <p/>
 * All the events related to interaction.
 */
public class TelemetryBuilder {

    public static GEInteract buildGEInteract(InteractionType interactionType, String stageId, String subType, String id, List<Map<String, Object>> values, List<CorrelationData> coRelationList) {
        GEInteract geInteract = new GEInteract.Builder()
                .interActionType(interactionType)
                .stageId(stageId)
                .subType(subType)
                .id(id)
                .correlationData(coRelationList)
                .values(values)
                .build();
        LogUtil.i("GE_INTERACT", geInteract.toString());
        return geInteract;
    }

    public static GEInteract buildGEInteract(InteractionType interactionType, String stageId, String subType, String id, Map<String, Object> values, List<CorrelationData> coRelationList) {
        return buildGEInteract(interactionType, stageId, subType, id, buildEksList(values), coRelationList);
    }

    public static GEInteract buildGEInteract(InteractionType interactionType, String stageId, String subType, String id, Map<String, Object> values) {
        return buildGEInteract(interactionType, stageId, subType, id, buildEksList(values), new ArrayList<CorrelationData>());
    }

    public static GEInteract buildGEInteract(InteractionType interactionType, String stageId, String subType, String id, List<Map<String, Object>> values) {
        return buildGEInteract(interactionType, stageId, subType, id, values, new ArrayList<CorrelationData>());
    }

    public static GEInteract buildGEInteract(InteractionType interactionType, String stageId, String subType, Map<String, Object> values) {
        return buildGEInteract(interactionType, stageId, subType, null, buildEksList(values), new ArrayList<CorrelationData>());
    }

    public static GEInteract buildGEInteract(String stageId) {
        return buildGEInteract(InteractionType.SHOW, stageId, null, null, new ArrayList<Map<String, Object>>(), new ArrayList<CorrelationData>());
    }

    public static GEInteract buildGEInteract(InteractionType interactionType, String stageId, String subType, String id) {
        return buildGEInteract(interactionType, stageId, subType, id, new ArrayList<Map<String, Object>>(), new ArrayList<CorrelationData>());
    }

    public static GEInteract buildGEInteract(InteractionType interactionType, String stageId, String subType) {
        return buildGEInteract(interactionType, stageId, subType, null, new ArrayList<Map<String, Object>>(), new ArrayList<CorrelationData>());
    }

    public static GEInteract buildGEInteractWithCoRelation(InteractionType interactionType, String stageId, String subType, String id, List<CorrelationData> coRelationList) {
        return buildGEInteract(interactionType, stageId, subType, id, new ArrayList<Map<String, Object>>(), coRelationList);
    }

    public static GEEvent buildInturrptEvent() {
        HashMap<String, Object> eks = new HashMap<>();
        eks.put("id", "");
        eks.put("type", "BACKGROUND");
        GEEvent geEvent = new GEEvent.Builder(TelemetryEvent.GE_INTERRUPT).eks(eks).build();
        LogUtil.i("GE_INTERRUPT", geEvent.toString());
        return geEvent;
    }

    public static GEEvent buildResumeEvent() {
        HashMap<String, Object> eks = new HashMap<>();
        ILocationInfo locationInfo = GenieService.getService().getLocationInfo();
        eks.put("loc", locationInfo != null ? locationInfo.getLocation() : "");
        GEEvent geEvent = new GEEvent.Builder(TelemetryEvent.GE_GENIE_RESUME).eks(eks).build();
        LogUtil.i("GE_GENIE_RESUME", geEvent.toString());
        return geEvent;
    }

    public static GEEvent buildGenieStartEvent(Context context) {
        HashMap<String, Object> eks = new HashMap<>();

        DeviceSpecification deviceSpecification = new DeviceSpecification();
        deviceSpecification.setOs("Android " + DeviceSpec.getOSVersion());
        deviceSpecification.setMake(DeviceSpec.getDeviceName());
        deviceSpecification.setId(DeviceSpec.getAndroidId(context));

        String internalMemory = Util.bytesToHuman(DeviceSpec.getTotalInternalMemorySize());
        if (!TextUtils.isEmpty(internalMemory)) {
            deviceSpecification.setIdisk(Double.valueOf(internalMemory));
        }

        String externalMemory = Util.bytesToHuman(DeviceSpec.getTotalExternalMemorySize());
        if (!TextUtils.isEmpty(externalMemory)) {
            deviceSpecification.setEdisk(Double.valueOf(externalMemory));
        }

        String screenSize = DeviceSpec.getScreenInfoinInch(context);
        if (!TextUtils.isEmpty(screenSize)) {
            deviceSpecification.setScrn(Double.valueOf(screenSize));
        }

        String[] cameraInfo = DeviceSpec.getCameraInfo(context);
        String camera = "";
        if (cameraInfo != null) {
            camera = TextUtils.join(",", cameraInfo);
        }
        deviceSpecification.setCamera(camera);
        deviceSpecification.setCpu(DeviceSpec.getCpuInfo());
        deviceSpecification.setSims(-1);

        Map<String, Object> dspectMap = deviceSpecification.toMap();
        eks.put("dspec", dspectMap);
        ILocationInfo locationInfo = GenieService.getService().getLocationInfo();
        eks.put("loc", locationInfo != null ? locationInfo.getLocation() : "");

        GEEvent geEvent = new GEEvent.Builder(TelemetryEvent.GE_GENIE_START).eks(eks).build();
        LogUtil.i("GE_GENIE_START", geEvent.toString());
        return geEvent;
    }

    public static GEEvent buildGenieEndEvent() {
        HashMap<String, Object> eks = new HashMap<>();

        String genieStartTime = PreferenceUtil.getGenieStartTimeStamp();

        if (!TextUtils.isEmpty(genieStartTime)) {
            long timeDifference = System.currentTimeMillis() - Long.valueOf(genieStartTime);
            long timeInSeconds = timeDifference / 1000;
            eks.put("length", timeInSeconds);
        }
        GEEvent geEvent = new GEEvent.Builder(TelemetryEvent.GE_GENIE_END).eks(eks).build();
        LogUtil.i("GE_GENIE_END", geEvent.toString());
        return geEvent;
    }

    private static List<Map<String, Object>> buildEksList(Map<String, Object> eks) {
        List<Map<String, Object>> eksList = new ArrayList<>();
        for (String key : eks.keySet()) {
            Map<String, Object> eksMap = new HashMap<>();
            eksMap.put(key, eks.get(key));
            eksList.add(eksMap);
        }
        return eksList;
    }

    public static GEEvent buildGEUpdate(UTMData utmData) {
        List<UTMData> referrer = new ArrayList<>();
        referrer.add(utmData);
        HashMap<String, Object> eks = new HashMap<>();
        eks.put("referrer", referrer);
        GEEvent geEvent = new GEEvent.Builder(TelemetryEvent.GE_GENIE_UPDATE).eks(eks).build();
        LogUtil.i("GE_GENIE_UPDATE", geEvent.toString());
        return geEvent;
    }

}
