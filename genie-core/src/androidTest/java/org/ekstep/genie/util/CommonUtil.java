package org.ekstep.genie.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.ekstep.genie.telemetry.TelemetryOperation;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.commons.bean.ContentData;
import org.ekstep.genieservices.commons.utils.GsonUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by swayangjit_gwl on 9/19/2016.
 */
// TODO: 20/7/17 Changes are made to this test class, check if it requies re-implmentation

public class CommonUtil {

    public static String GMAIL_PACKAGE_NAME = "com.google.android.gm";
//    private static UserProfileUtil.IAddProfile mIAddProfile = new UserProfileUtil.IAddProfile() {
//        @Override
//        public void onProfileSuccess(org.ekstep.genie.model.Profile profile) {
//
//        }
//
//        @Override
//        public void onProfileFailure(GenieResponse genieResponse) {
//
//        }
//    };

    public static int getGmailPosition(Context context, boolean isFile) {
        List<ResolveInfo> resolveInfos = new ArrayList<ResolveInfo>();

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        if (isFile) {
            shareIntent.setType(Constant.ZIP_MIME_TYPE);
        } else {
            shareIntent.setType(Constant.TXT_MIME_TYPE);
        }
        resolveInfos = context.getPackageManager().queryIntentActivities(shareIntent, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
        for (int i = 0; i < resolveInfos.size(); i++) {
            ResolveInfo resolveInfo = resolveInfos.get(i);
            if (resolveInfo.activityInfo.packageName.equalsIgnoreCase(GMAIL_PACKAGE_NAME)) {
                return i;
            }
        }
        return 0;
    }

    public static void copyDatabase(Context c) {
        String databasePath = c.getDatabasePath("GenieServices.db").getPath();
        File f = new File(databasePath);
        OutputStream myOutput = null;
        InputStream myInput = null;
        Log.d("testing", " testing db path " + databasePath);
        Log.d("testing", " testing db exist " + f.exists());

        if (f.exists()) {
            try {

                File directory = new File("/mnt/sdcard/DB_DEBUG");
                if (!directory.exists())
                    directory.mkdir();

                myOutput = new FileOutputStream(directory.getAbsolutePath()
                        + "/" + "GenieServices.db");
                myInput = new FileInputStream(databasePath);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
            } catch (Exception e) {
            } finally {
                try {
                    if (myOutput != null) {
                        myOutput.close();
                        myOutput = null;
                    }
                    if (myInput != null) {
                        myInput.close();
                        myInput = null;
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    public static String readFromResource(Context context, int id) {
        InputStream is = context.getResources().openRawResource(id);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }

        } catch (Exception e) {

        } finally {
            try {
                is.close();
            } catch (IOException e) {

            }

        }

        return writer.toString();
    }

//    public static void copyDB() {
//        String GS_DB = "/data/data/org.ekstep.genieservices/databases/GenieServices.db";
//        String COPY_OF_GS_DB = format("{0}/Download/GenieServices_copy.db",
//                Environment.getExternalStorageDirectory().getPath());
//
//        new CopyDatabaseTask(GS_DB, COPY_OF_GS_DB).perform();
//
//    }

    public static Map<String, Object> convertStringToMap(String jsonString) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    public static ContentData stubContentData(String identifier) {
        Map<String, Object> map = CommonUtil.convertStringToMap(SampleData.getLocalData(identifier));
        JsonElement jsonElement = GsonUtil.getGson().toJsonTree(map);
        return GsonUtil.getGson().fromJson(jsonElement, ContentData.class);
    }

//    public static void addChild() {
//        Profile profile1 = new Profile("test", "@drawable/ic_avatar1", LANG_ENGLISH, 1, "English");
//        Profile profile2 = new Profile("test", "@drawable/ic_avatar1", LANG_ENGLISH, 1, "English");
//        UserProfileUtil.setProfile(profile1, mIAddProfile);
//        UserProfileUtil.setProfile(profile2, mIAddProfile);
//    }

//    public static void addGroupUser() {
//        Profile profile1 = new Profile("test", "@drawable/ic_avatar1", LANG_ENGLISH, -1, "");
//        Profile profile2 = new Profile("test", "@drawable/ic_avatar1", LANG_ENGLISH, -1, "");
//        profile1.setGroupUser(true);
//        profile2.setGroupUser(true);
//        UserProfileUtil.setProfile(profile1, mIAddProfile);
//        UserProfileUtil.setProfile(profile2, mIAddProfile);
//    }

//    public static void readLocalResourceBundle(Context context) throws IOException {
//        String response = RawFileUtil.readRawFile(context, R.raw.terms);
//        if (!TextUtils.isEmpty(response)) {
//            LinkedTreeMap map = new Gson().fromJson(response, LinkedTreeMap.class);
//            for (Object keys : map.keySet()) {
//                Term term = new Term((String) keys, String.valueOf(new Gson().toJson(map.get(keys))));
//                term.save(new DbOperator(context));
//            }
//        }
//    }

    public static void stopAutoSyncing() {
        TelemetryOperation.setInitialDelay(100000);
        TelemetryOperation.shutDownSchedulers();
        PreferenceUtil.setTelemetrySyncInterval("{\"default\":{\"syncInterval\":30000000000, \"mode\":\"auto\"}}");
    }

//    public static void importContent(final Activity context, final String path, final boolean dismissProgressDialog) {
//        ImportExportUtil.importContent(context, FileHandler.createFileFromAsset(context, path), new ImportExportUtil.IImport() {
//            @Override
//            public void onImportSuccess() {
//                if (!dismissProgressDialog) {
//                    if (Constant.CONTENT1_FILE_PATH.equalsIgnoreCase(path)) {
//                        importContent(context, Constant.CONTENT2_FILE_PATH, false);
//                    } else {
//                        importContent(context, Constant.CONTENT3_FILE_PATH, true);
//                    }
//                }
//            }
//
//            @Override
//            public void onImportFailure() {
//
//            }
//
//            @Override
//            public void onOutDatedEcarFound() {
//            }
//        }, false, org.ekstep.genieservices.utils.Constants.IMPORT_OR_DELETE_STARTED_FROM_OUTSIDE_COLLECTION_OR_TEXTBOOK);
//    }

    public interface Constants {
        String GE_CREATE_USER = "GE_CREATE_USER";
        String GE_CREATE_PROFILE = "GE_CREATE_PROFILE";
        String GE_SERVICE_API_CALL = "GE_SERVICE_API_CALL";
        String GE_SESSION_END = "GE_SESSION_END";
        String GE_SESSION_START = "GE_SESSION_START";
        String GE_UPDATE_PROFILE = "GE_UPDATE_PROFILE";
        String CONTENT1_CODE = "org.ekstep.literacy.story.2391";
        String CONTENT2_CODE = "org.ekstep.literacy.worksheet.2515";
        String IMPORT_DATATYPE = "CONTENT";
        String IMPORT_DIRECTION = "IMPORT";
        String EXPORT_DIRECTION = "EXPORT";
        String IMPORT_DATATYPE_PROFILE = "PROFILE";

        String GE_GENIE_START = "GE_GENIE_START";
        String GE_FEEDBACK = "GE_FEEDBACK";
        String TELEMETRY_VERSION = "2.1";
        String DOWNLOADS = "Downloads";
    }
}


