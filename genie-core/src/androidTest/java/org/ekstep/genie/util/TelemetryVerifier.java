package org.ekstep.genie.util;

//import android.support.test.espresso.core.deps.guava.base.Strings;
//import android.support.test.espresso.core.deps.guava.reflect.TypeToken;

import com.google.common.base.Strings;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.ekstep.genie.BuildConfig;
import org.ekstep.genie.model.DeviceSpecification;
import org.ekstep.genie.model.ExpectedEventList;
import org.ekstep.genie.model.UTMData;
import org.ekstep.genie.telemetry.TelemetryConstant;
import org.ekstep.genie.telemetry.TelemetryEvent;
import org.ekstep.genieservices.commons.utils.GsonUtil;
import org.ekstep.genieservices.telemetry.model.EventModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Assert;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.text.IsEmptyString.isEmptyString;

/**
 * Verifies all telemetry events
 *
 * Created by swayangjit_gwl on 9/20/2016.
 */
// TODO: 20/7/17 Changes are made to this test class, check if it requies re-implmentation

public class TelemetryVerifier {

    public static final String EVENT_SCREEN = "event_type";
    public static final String EVENT_ACTION = "event_action";
    public static final String INTERACTION_TYPE = "interaction_type";
    public static final String EID = "eid";
    public static final String QUERY = "query";
    public static final String EVENT_ID = "id";
    public static final String GID = "gid";
    public static final String TMSCHM = "tmschm";
    public static final String LENGTH = "length";


    /**
     * Verifies the list of events provided with the actual events
     *
     * @param expectedGeInteractEvnetList expected event list object.
     * @return void.
     */
    public static void verifyGEEvents(ExpectedEventList expectedGeInteractEvnetList) {

        Map<String, List<ExpectedEvent>> expectedEventMap = expectedGeInteractEvnetList.getExpectedEventMap();

        if (expectedEventMap.containsKey(TelemetryEvent.GE_INTERACT)) {
            assertGEInteract(expectedEventMap);
        }

        if (expectedEventMap.containsKey(CommonUtil.Constants.GE_CREATE_USER)) {
            List<EventModel> actualGeCreateUserEventList = GenieDBHelper.findEventByEid(CommonUtil.Constants.GE_CREATE_USER);
            assertEquals(expectedEventMap.get(CommonUtil.Constants.GE_CREATE_USER).size(), actualGeCreateUserEventList.size());
        }

        if (expectedEventMap.containsKey(CommonUtil.Constants.GE_CREATE_PROFILE)) {
            assertGECreateProfile(expectedEventMap);
        }

        if (expectedEventMap.containsKey(CommonUtil.Constants.GE_SERVICE_API_CALL)) {
            List<EventModel> actualServiceApiEventList = GenieDBHelper.findEventByEid(CommonUtil.Constants.GE_SERVICE_API_CALL);
            assertEquals(expectedEventMap.get(CommonUtil.Constants.GE_SERVICE_API_CALL).size(), actualServiceApiEventList.size());
        }

        if (expectedEventMap.containsKey(TelemetryEvent.GE_INTERRUPT)) {
            List<EventModel> actualGeInterruptEventList = GenieDBHelper.findEventByEid(TelemetryEvent.GE_INTERRUPT);
            assertEquals(expectedEventMap.get(TelemetryEvent.GE_INTERRUPT).size(), actualGeInterruptEventList.size());
        }

        if (expectedEventMap.containsKey(TelemetryEvent.GE_TRANSFER)) {
            List<EventModel> actualGeTransferEventList = GenieDBHelper.findEventByEid(TelemetryEvent.GE_TRANSFER);
            assertEquals(expectedEventMap.get(TelemetryEvent.GE_TRANSFER).size(), actualGeTransferEventList.size());
            assertGETransferEvnet(expectedEventMap);
        }

        if (expectedEventMap.containsKey(TelemetryEvent.GE_LAUNCH_GAME)) {
            assertGELaunchGameEvnet(expectedEventMap);
        }

        if (expectedEventMap.containsKey(TelemetryEvent.GE_GAME_END)) {
            assertGEGameEndEvnet(expectedEventMap);
        }

        if (expectedEventMap.containsKey(TelemetryEvent.GE_GENIE_UPDATE)) {
            assertGEGenieUpdateEvnet(expectedEventMap);
        }

        if (expectedEventMap.containsKey(TelemetryEvent.GE_GENIE_START)) {
            assertGEGenieStartEvent(expectedEventMap);
        }

        if (expectedEventMap.containsKey(TelemetryEvent.GE_FEEDBACK)) {
            assertGEFeedback(expectedEventMap);
        }

    }

    private static void assertGEGenieStartEvent(Map<String, List<ExpectedEvent>> expectedEventMap) {
        List<EventModel> dbGEStartEventList = GenieDBHelper.findEventByEid(CommonUtil.Constants.GE_GENIE_START);

        assertEquals(expectedEventMap.get(CommonUtil.Constants.GE_GENIE_START).size(), dbGEStartEventList.size());

        for (int i = 0; i < dbGEStartEventList.size(); i++) {
            EventModel actualEvent = dbGEStartEventList.get(i);
            assertOtherFields(actualEvent.getEventMap());
            Map geEventMap = getEksFromEvent(actualEvent, Map.class);
            DeviceSpecification deviceSpecification = GsonUtil.fromMap((Map) geEventMap.get("dspec"), DeviceSpecification.class);
            assertNotNull(deviceSpecification.getId());
            assertNotNull(deviceSpecification.getCpu());
            assertNotNull(deviceSpecification.getCamera());
            assertNotNull(deviceSpecification.getMake());
            assertNotNull(deviceSpecification.getOs());
            assertNotNull(deviceSpecification.getEdisk());

        }
    }

    /**
     * Asserts all GE_INTERACT events
     *
     * @param expectedEventMap expected event map.
     * @return void.
     */
    private static void assertGEInteract(Map<String, List<ExpectedEvent>> expectedEventMap) {
        List<ExpectedEvent> expectedGeInteractEventList = expectedEventMap.get(TelemetryEvent.GE_INTERACT);

        List<EventModel> actualGeInteractEvent = GenieDBHelper.findEventByEid(TelemetryEvent.GE_INTERACT);

        assertEquals(expectedGeInteractEventList.size(), actualGeInteractEvent.size());

        for (int i = 0; i < expectedGeInteractEventList.size(); i++) {
            ExpectedEvent expectedEvent = expectedGeInteractEventList.get(i);
            String event = SampleData.getGeInteractEvent(expectedEvent.getId(), expectedEvent.getStageId(), expectedEvent.getSubType(), expectedEvent.getType());
            Map expectedGEInteractEvent = new Gson().fromJson(event, Map.class);
            EventModel actualEvent = actualGeInteractEvent.get(i);
            assertOtherFields(actualEvent.getEventMap());
            Map actualEksMap = getEksFromEvent(actualEvent, Map.class);
            assertValues(expectedGEInteractEvent.get("stageid").toString(), actualEksMap.get("stageid").toString());
            //check type
            assertValues(expectedGEInteractEvent.get("type") != null ? expectedGEInteractEvent.get("type").toString() : "", actualEksMap.get("type") != null ? actualEksMap.get("type").toString() : "");
            //check subtype
            assertValues(expectedGEInteractEvent.get("subtype") != null ? expectedGEInteractEvent.get("subtype").toString() : "", actualEksMap.get("subtype") != null ? actualEksMap.get("subtype").toString() : "");

            //check id
            String id = expectedGEInteractEvent.get("id").toString();
            if (null != id) {
                assertEquals(id, actualEksMap.get("id").toString());
            }

            if (expectedEvent.isValuesAvailable()) {
                assertValues(actualEksMap, expectedEvent);
            }

            //Check cData
            String cData = expectedEvent.getCdataType();

            assertCData(cData, actualEvent, expectedEvent);

        }
    }

    private static void assertOtherFields(Map event) {
        Assert.assertThat(event.get("eid").toString(), is(not(isEmptyString())));
        Assert.assertThat(event.get("sid").toString(), is(not(isEmptyString())));
        Assert.assertThat(event.get("uid").toString(), is(not(isEmptyString())));
        Assert.assertThat(event.get("did").toString(), is(not(isEmptyString())));
        Assert.assertThat(event.get("ets").toString(), is(not(isEmptyString())));
        assertEquals(event.get("ver").toString(), CommonUtil.Constants.TELEMETRY_VERSION);
        assertEquals(event.get("channel").toString(), BuildConfig.CHANNEL_ID);
        assertEquals(((Map) event.get("pdata")).get("id"), BuildConfig.PRODUCER_ID);
        assertEquals(((Map) event.get("pdata")).get("ver"), BuildConfig.VERSION_NAME);
        assertEquals(((Map) event.get("gdata")).get("id"), BuildConfig.APPLICATION_ID);
        assertEquals(((Map) event.get("gdata")).get("ver"), BuildConfig.VERSION_NAME);
    }

    private static void assertValues(Map actualGEInteract, ExpectedEvent expectedEvent) {

        Type valuesType = new TypeToken<List<Map<String, Object>>>() {
        }.getType();
        List<Map<String, Object>> valueList = GsonUtil.fromJson(GsonUtil.toJson(actualGEInteract.get("values")), valuesType);
        assertNotNull(valueList);
        for (int i = 0; i < valueList.size(); i++) {
            Map<String, Object> map = valueList.get(i);
            if (map.containsKey(TelemetryConstant.PRESENT_ON_DEVICE)) {
                assertContentAvaibilityStatus(expectedEvent.isContentAvailable(), map.get(TelemetryConstant.PRESENT_ON_DEVICE).toString());
            }
            if (map.containsKey(TelemetryConstant.CONTENT_LOCAL_COUNT)) {
                assertEquals(String.valueOf(expectedEvent.getTbLocalContentCount()), map.get(TelemetryConstant.CONTENT_LOCAL_COUNT).toString());
            }
            if (map.containsKey(TelemetryConstant.CONTENT_TOTAL_COUNT)) {
                assertEquals(String.valueOf(expectedEvent.getTbTotalContentCount()), map.get(TelemetryConstant.CONTENT_TOTAL_COUNT).toString());
            }

        }
    }

    private static void assertContentAvaibilityStatus(boolean isContentAvailable, String value) {

        if (isContentAvailable) {
            assertValues("Y", value);
        } else {
            assertValues("N", value);
        }
    }


    private static void assertCData(String cData, EventModel actualEvent, ExpectedEvent expectedEvent) {
        if (cData != null) {
            if (!Strings.isNullOrEmpty(cData)) {
                try {
                    JSONArray jsonArray = new JSONArray(actualEvent.getEventMap().get("cdata").toString());
                    String cid = jsonArray.getJSONObject(0).getString("id");
                    String type = jsonArray.getJSONObject(0).getString("type");

                    //Assert id is null or not
                    assertNotNull(cid);

                    if (cid != null && expectedEvent.getCdataId() != null) {
                        //Assert cData id
                        assertEquals(expectedEvent.getCdataId().toLowerCase(), cid.toLowerCase());
                    }

                    //Assert cData type

                    assertEquals(expectedEvent.getCdataType().toLowerCase(), type.toLowerCase());
                } catch (JSONException e) {

                }
            }

        }
    }

    /**
     * Asserts all GE_CREATE_PROFILE events
     *
     * @param expectedEventMap expected event map.
     * @return void.
     */
    private static void assertGECreateProfile(Map<String, List<ExpectedEvent>> expectedEventMap) {

        List<EventModel> actualCreateProfileEventList = GenieDBHelper.findEventByEid(CommonUtil.Constants.GE_CREATE_PROFILE);
        List<ExpectedEvent> expectedGeCreateProfileEventList = expectedEventMap.get(CommonUtil.Constants.GE_CREATE_PROFILE);
        assertEquals(expectedEventMap.get(CommonUtil.Constants.GE_CREATE_PROFILE).size(), actualCreateProfileEventList.size());
        for (int i = 0; i < expectedGeCreateProfileEventList.size(); i++) {
            ExpectedEvent expectedEvent = expectedGeCreateProfileEventList.get(i);
            EventModel actualEvent = actualCreateProfileEventList.get(i);
            assertOtherFields(actualEvent.getEventMap());
            Map<String, Object> actualMap = (Map<String, Object>) getGeMap(actualEvent);

            assertValues(expectedEvent.getHandle(), actualMap.get("handle").toString());

            assertEquals(expectedEvent.isGroupUser(), actualMap.get("is_group_user"));
        }

    }

    /**
     * Asserts all GE_LAUNCH_GAME events
     *
     * @param expectedEventMap expected event map.
     * @return void.
     */
    private static void assertGELaunchGameEvnet(Map<String, List<ExpectedEvent>> expectedEventMap) {

        List<ExpectedEvent> expectedGeLaunchGameEventList = expectedEventMap.get(TelemetryEvent.GE_LAUNCH_GAME);

        List<EventModel> actualGeInteractEvent = GenieDBHelper.findEventByEid(TelemetryEvent.GE_LAUNCH_GAME);

        assertEquals(expectedGeLaunchGameEventList.size(), actualGeInteractEvent.size());

        for (int i = 0; i < expectedGeLaunchGameEventList.size(); i++) {
            ExpectedEvent expectedEvent = expectedGeLaunchGameEventList.get(i);
            String event = SampleData.getGeLaunchGameEvent(expectedEvent.getGid(), expectedEvent.getTmschm());

            Map expectedGELaunchGame = new Gson().fromJson(event, Map.class);

            EventModel actualEvent = actualGeInteractEvent.get(i);

            assertOtherFields(actualEvent.getEventMap());
            //get GELaunchGame
            Map actualEksMap = getEksFromEvent(actualEvent, Map.class);

            assertValues(expectedGELaunchGame.get("gid").toString(), actualEksMap.get("gid").toString());
            //check Tmschm
            assertValues(expectedGELaunchGame.get("tmschm").toString(), actualEksMap.get("tmschm").toString());
            //Check cData
            String cData = expectedEvent.getCdataType();

            assertCData(cData, actualEvent, expectedEvent);
        }

    }

    /**
     * Asserts all GE_GAME_END events
     *
     * @param expectedEventMap expected event map.
     * @return void.
     */
    private static void assertGEGameEndEvnet(Map<String, List<ExpectedEvent>> expectedEventMap) {


        List<ExpectedEvent> expectedGeGameEndEventList = expectedEventMap.get(TelemetryEvent.GE_GAME_END);

        List<EventModel> actualGeInteractEvent = GenieDBHelper.findEventByEid(TelemetryEvent.GE_GAME_END);

        assertEquals(expectedGeGameEndEventList.size(), actualGeInteractEvent.size());

        for (int i = 0; i < expectedGeGameEndEventList.size(); i++) {
            ExpectedEvent expectedEvent = expectedGeGameEndEventList.get(i);
            String event = SampleData.getGeGameEndEvent(expectedEvent.getGid(), expectedEvent.getLength());

            EventModel actualEvent = actualGeInteractEvent.get(i);
            assertOtherFields(actualEvent.getEventMap());
            //get GEGameEnd
            Map eks = getEksFromEvent(actualEvent, Map.class);

            assertValues(expectedEvent.getGid(), eks.get("gid").toString());
            //check tmschm
            double length = Double.valueOf(eks.get("length").toString());
//            Assert.assertThat((long)length, Matchers.greaterThan(Long.valueOf(0)));
        }

    }

    /**
     * Asserts all GE_LAUNCH_GAME events
     *
     * @param expectedEventMap expected event map.
     * @return void.
     */
    private static void assertGETransferEvnet(Map<String, List<ExpectedEvent>> expectedEventMap) {

        List<ExpectedEvent> expectedGeTransferGameEventList = expectedEventMap.get(TelemetryEvent.GE_TRANSFER);

        List<EventModel> actualGeTransfer = GenieDBHelper.findEventByEid(TelemetryEvent.GE_TRANSFER);

        assertEquals(expectedGeTransferGameEventList.size(), actualGeTransfer.size());

        for (int i = 0; i < expectedGeTransferGameEventList.size(); i++) {
            ExpectedEvent expectedEvent = expectedGeTransferGameEventList.get(i);
            String event = SampleData.getGeLaunchGameEvent(expectedEvent.getGid(), expectedEvent.getTmschm());

            EventModel actualEvent = actualGeTransfer.get(i);
            assertOtherFields(actualEvent.getEventMap());
            //get GELaunchGame
            Map<String, Object> actualMap = (Map<String, Object>) getGeMap(actualEvent);

            assertValues(expectedEvent.getImportDataType(), actualMap.get("datatype").toString());

            assertEquals(expectedEvent.getImportDirection(), actualMap.get("direction"));
        }

    }

    /**
     * Compares two objects
     *
     * @param expected expected object.
     * @param actual   actual object.
     * @return void.
     */
    private static void assertValues(String expected, String actual) {
        if (null != expected) {
            assertEquals(expected, actual);
        }
    }


    /**
     * Asserts all GE_GENIE_UPDATE events
     *
     * @param expectedEventMap expected event map.
     * @return void.
     */
    private static void assertGEGenieUpdateEvnet(Map<String, List<ExpectedEvent>> expectedEventMap) {


        List<ExpectedEvent> expectedGeGenieUpdateEventList = expectedEventMap.get(TelemetryEvent.GE_GENIE_UPDATE);

        List<EventModel> actualGeGenieUpdateEventList = GenieDBHelper.findEventByEid(TelemetryEvent.GE_GENIE_UPDATE);

        assertEquals(expectedGeGenieUpdateEventList.size(), actualGeGenieUpdateEventList.size());

        for (int i = 0; i < expectedGeGenieUpdateEventList.size(); i++) {
            ExpectedEvent expectedEvent = expectedGeGenieUpdateEventList.get(i);
            EventModel actualEvent = actualGeGenieUpdateEventList.get(i);
            assertOtherFields(actualEvent.getEventMap());
            Map eksMap = getEksFromEvent(actualEvent, Map.class);
            Type utmListType = new TypeToken<ArrayList<UTMData>>() {
            }.getType();
            List<UTMData> utmList = GsonUtil.fromJson(GsonUtil.toJson(eksMap.get("referrer")), utmListType);
            assertEquals(expectedEvent.getAction(), utmList.get(0).getAction());
            assertEquals(expectedEvent.getUtmsource(), utmList.get(0).getUtmsource());
            assertEquals(expectedEvent.getUtmmedium(), utmList.get(0).getUtmmedium());
            assertEquals(expectedEvent.getUtmsource(), utmList.get(0).getUtmsource());
            assertEquals(expectedEvent.getUtmterm(), utmList.get(0).getUtmterm());
            assertEquals(expectedEvent.getUtmcontent(), utmList.get(0).getUtmcontent());
            assertEquals(expectedEvent.getUtmcampaign(), utmList.get(0).getUtmcampaign());
        }

    }

    private static void assertGEFeedback(Map<String, List<ExpectedEvent>> expectedEventMap) {
        List<ExpectedEvent> expectedGeFeedbackEventList = expectedEventMap.get(TelemetryEvent.GE_FEEDBACK);
        List<EventModel> dbGEStartEventList = GenieDBHelper.findEventByEid(CommonUtil.Constants.GE_FEEDBACK);

        assertEquals(expectedEventMap.get(CommonUtil.Constants.GE_FEEDBACK).size(), dbGEStartEventList.size());

        for (int i = 0; i < dbGEStartEventList.size(); i++) {
            ExpectedEvent expectedEvent = expectedGeFeedbackEventList.get(i);
            EventModel actualEvent = dbGEStartEventList.get(i);
            assertOtherFields(actualEvent.getEventMap());
            Map geEventMap = getEksFromEvent(actualEvent, Map.class);
            Map context = (Map) geEventMap.get("context");
            assertEquals(expectedEvent.getFedbackId(), context.get("id").toString());
            assertEquals(expectedEvent.getFeedbackStageId(), context.get("stageid").toString());
            assertEquals(expectedEvent.getFeedbackContextType(), context.get("type").toString());
            assertEquals(expectedEvent.getFeedbackType(), geEventMap.get("type").toString());

        }
    }


    /**
     * Returns the Pojo class  with given event
     *
     * @param event         event.
     * @param expectedClass expected  class.
     * @return Class.
     */
    public static <C> C getEksFromEvent(EventModel event, Class<C> expectedClass) {
        Gson gson = new Gson();
        Object eksMap = ((Map<String, Object>) event.getEventMap().get("edata")).get("eks");
        C geEvent = gson.fromJson(gson.toJson(eksMap), expectedClass);
        return geEvent;
    }

    /**
     * Returns a Map of given event
     *
     * @param event Given event.
     * @return Object.
     */
    private static Object getGeMap(EventModel event) {
        Object eksMap = ((Map<String, Object>) event.getEventMap().get("edata")).get("eks");
        return eksMap;
    }

}
