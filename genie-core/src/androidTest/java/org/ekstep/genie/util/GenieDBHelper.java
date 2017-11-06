package org.ekstep.genie.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import org.ekstep.genie.telemetry.TelemetryEvent;
import org.ekstep.genieservices.commons.AppContext;
import org.ekstep.genieservices.commons.db.contract.ContentEntry;
import org.ekstep.genieservices.commons.db.contract.ProfileEntry;
import org.ekstep.genieservices.commons.db.core.impl.SQLiteResultSet;
import org.ekstep.genieservices.telemetry.model.EventModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dhruv on 9/16/2016.
 */
// TODO: 20/7/17 Changes are made to this test class, check if it requies re-implmentation


public class GenieDBHelper {
    public static final String QUERY_DESC_LIMIT = " DESC LIMIT 1";
    public static final String QUERY_ASC_LIMIT = " ASC LIMIT 1";
    public static final String SELECT_ALL_TELEMETRY = "SELECT * FROM telemetry";
    public static final String QUERY_LEAVE_LAST_ROW = " DESC LIMIT 1 offset 1";
    public static final String QUERY_LEAVE_LAST_TWOROW = " DESC LIMIT 1 offset 2";

    public static String GS_DB = "/data/data/org.ekstep.genieservices.qa/databases/GenieServices.db";
    private static SQLiteDatabase db = null;
    private static String TAG = GenieDBHelper.class.getSimpleName();
    private static GenieDBHelper sGenieDBHelper;
    private AppContext mAppContext;

    private GenieDBHelper(AppContext appContext) {
        this.mAppContext = appContext;
    }

    public static SQLiteDatabase getDataBase() {
        if (db == null) {
            db = SQLiteDatabase.openDatabase(GS_DB, null, SQLiteDatabase.OPEN_READWRITE);
        }
        return db;
    }

    public static void init(AppContext appContext) {
        sGenieDBHelper = new GenieDBHelper(appContext);
    }


    public static void verifyTelemetryEvent(String eventScreen, String eventAction,
                                            String interactionType, String query,
                                            String eid) {

        //GE_INTERACT EVENT
        if (eid.equalsIgnoreCase(TelemetryEvent.GE_INTERACT)) {
//            verifyGeEvent(eventScreen, eventAction, interactionType, query, eid);
        }
    }

    //
//    public static void verifyGeLaunchGameEvent(String query, String eid) {
//
//        String gid = "org.ekstep.literacy.story.263", tmschm1 = "INTENT";
//        //Fetch Data from SampleData
//        String event = SampleData.getGeLaunchGameEvent(gid, tmschm1);
//        GELaunchGame expectedEvent = new Gson().fromJson(event, GELaunchGame.class);
//
//        //Verify Telemetry TelemetryEvent(TE) from Database
//        Event actualTelemetryEvent = getEventFromDB(query, eid);
//
//        //get GELaunchGame
//        Object eksMap = getEksMap(actualTelemetryEvent);
//        Gson gson = new Gson();
//        GELaunchGame eks = gson.fromJson(gson.toJson(eksMap), GELaunchGame.class);
//
//        //Match SampleData with DB
//        assertEquals(eid, actualTelemetryEvent.getEventMap().get("eid"));
//        assertEquals(expectedEvent.getGid(), eks.getGid());
//        //check tmschm
//        String tmschm = expectedEvent.getTmschm();
//        if (null != tmschm) {
//            assertEquals(tmschm, eks.getTmschm());
//        }
//
//    }
//
//
//    public static void verifyGeGameEndEvent(String query, String eid) {
//        String gid = "org.ekstep.literacy.story.263";
//        long length1 = 0;
//        //Fetch Data from SampleData
//        String event = SampleData.getGeGameEndEvent(gid, length1);
//        GEGameEnd expectedEvent = new Gson().fromJson(event, GEGameEnd.class);
//
//        //Verify Telemetry TelemetryEvent(TE) from Database
//        Event actualTelemetryEvent = getEventFromDB(query, eid);
//
//        //get GELaunchGame
//        Object eksMap = getEksMap(actualTelemetryEvent);
//        LogUtil.e(TAG, "verifyGeGameEndEvent eksMap----->" + eksMap);
//        Gson gson = new Gson();
//        GEGameEnd eks = gson.fromJson(gson.toJson(eksMap), GEGameEnd.class);
//
//        //Match SampleData with DB
//        assertEquals(eid, actualTelemetryEvent.getEventMap().get("eid"));
//        assertEquals(expectedEvent.getGid(), eks.getGid());
//        //check tmschm
//        long length = expectedEvent.getLength();
//        if (0 != length) {
//            assertEquals(length, eks.getLength());
//        }
//
//    }
//
//
//    public static void verifyGeEvent(String eventScreen, String eventAction,
//                                     String interactionType,
//                                     String query, String eid) {
//        //Fetch Data from SampleData
//        String event = SampleData.getGeInteractEvent(null, eventScreen, eventAction, interactionType);
//        GEInteract expectedEvent = new Gson().fromJson(event, GEInteract.class);
//
//        //Verify Telemetry TelemetryEvent(TE) from Database
//        Event actualTelemetryEvent = getEventFromDB(query, eid);
//
//        //get GEInteract
//        Object eksMap = getEksMap(actualTelemetryEvent);
//        Gson gson = new Gson();
//        GEInteract eks = gson.fromJson(gson.toJson(eksMap), GEInteract.class);
//
//        //Match SampleData with DB
//        assertEquals(eid, actualTelemetryEvent.getEventMap().get("eid"));
//        assertEquals(expectedEvent.getStageid(), eks.getStageid());
//
//        //check type
//        String type = expectedEvent.getType();
//        if (null != type) {
//            assertEquals(type, eks.getType());
//        }
//        //check subtype
//        String subtype = expectedEvent.getSubtype();
//        if (null != subtype) {
//            assertEquals(subtype, eks.getSubtype());
//        }
//
//
//    }
//
//    public static Event getEventFromDB(String query, String eventID) {
//        //Fetch Telemetry TelemetryEvent from DB
//        final Event actualTelemetryEvent = findEventByEid(query, eventID);
//        return actualTelemetryEvent;
//    }
//
//    public static Event findEventByEid(String query, String eid) {
//        Cursor telemetryCursor = GenieDBHelper.getInstance().rawQuery(query, null);
//        List<Event> events = TelemetryTestHelper.readAllEvents(telemetryCursor);
//        telemetryCursor.close();
//        for (Event event : events) {
//            if (eid.equals(event.getEventMap().get("eid"))) {
//                return event;
//            }
//        }
//        return null;
//    }
    public static List<EventModel> findEventByEid(String eid) {
        Cursor cursor = GenieDBHelper.getDataBase().rawQuery(generateQuery(eid), null);
        List<EventModel> events = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst())
            do {
                EventModel eventModel = EventModel.build(sGenieDBHelper.mAppContext.getDBSession());
                eventModel.readWithoutMoving(new SQLiteResultSet(cursor));
                events.add(eventModel);
            } while (cursor.moveToNext());
        cursor.close();

        return events;
    }

    public static String generateQuery(String eid) {
        return "SELECT * FROM telemetry where event_type='" + eid + "'";
    }

    //
//    public static Event findEventByEid1(String eid, String addQuery) {
//        Cursor telemetryCursor = GenieDBHelper.getInstance().rawQuery(generateQuery(eid, addQuery), null);
//        List<Event> events = TelemetryTestHelper.readAllEvents(telemetryCursor);
//        telemetryCursor.close();
//        for (Event event : events) {
//            if (eid.equals(event.getEventMap().get("eid"))) {
//                return event;
//            }
//        }
//        return null;
//    }
//
//    public static List<Event> findEventByEid(String eid) {
//        Cursor telemetryCursor = GenieDBHelper.getInstance().rawQuery(generateQuery(eid), null);
//        List<Event> events = TelemetryTestHelper.readAllEvents(telemetryCursor);
//        if (eid.equalsIgnoreCase(CommonUtil.Constants.GE_SERVICE_API_CALL)) {
//            for (int i = 0; i < events.size(); i++) {
//                Event event = events.get(i);
//                String id = getEid(event);
//                if (!Strings.isNullOrEmpty(id)) {
//                    if (id.equalsIgnoreCase(TelemetryEvent.GE_GENIE_RESUME) || id.equalsIgnoreCase(TelemetryEvent.GE_INTERRUPT)) {
//                        events.remove(i);
//                    }
//                }
//            }
//
//        }
//
//        telemetryCursor.close();
//        return events;
//    }
//
//    public static Object getEksMap(Event event) {
//        Object eksMap = ((Map<String, Object>) event.getEventMap().get("edata")).get("eks");
//        return eksMap;
//    }
//
//    private static String getEid(Event event) {
//        Map<String, Object> eventMap = (Map<String, Object>) getEksMap(event);
//        Map<String, Object> request = (Map<String, Object>) eventMap.get("request");
//        if (request != null) {
//            if (request.get("TelemetryEvent") != null) {
//                String eventString = request.get("TelemetryEvent").toString();
//                try {
//                    JSONObject jsonObject = new JSONObject(eventString);
//                    String id = jsonObject.getString("eid");
//                    return id;
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//
//        }
//        return null;
//    }
//
    public static void clearTelemetryTable() {
        try {
            int count = getDataBase().delete("telemetry", "1", null);
            LogUtil.i("Count:::::", "" + count);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

    }

    //
//    public static String generateQuery(String eid, String addQuery) {
//        return "SELECT * FROM telemetry  where event_type='" + eid + "' ORDER BY _id" + addQuery;
//    }
//
//    public static String generateQuery(String eid) {
//        return "SELECT * FROM telemetry  where event_type='" + eid + "'";
//    }
//
    public static void deleteAllUserProfile() {
        getDataBase().delete(ProfileEntry.TABLE_NAME, null, null);
    }

    public static void deleteAllContent() {
        getDataBase().delete(ContentEntry.TABLE_NAME, null, null);
    }
}
