package org.ekstep.genie.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.text.format.DateUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;
import org.ekstep.genie.model.enums.StageCode;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.ServiceConstants;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.network.NetworkConstants;
import org.ekstep.genieservices.commons.utils.CryptoUtil;
import org.ekstep.genieservices.commons.utils.DateUtil;
import org.ekstep.genieservices.commons.utils.GsonUtil;
import org.ekstep.genieservices.commons.utils.StringUtil;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;

@SuppressLint("SimpleDateFormat")
public class Util {

    private static final String TAG = Util.class.getSimpleName();
    public static long SECOND = 1000l;
    public static long MINUTE = SECOND * 60l;
    public static long HOUR = MINUTE * 60l;
    public static long DAY = HOUR * 24l;
    public static long MONTH = DAY * 30l;
    public static long YEAR = DAY * 365l;

    /**
     * Formats double to string
     *
     * @return String
     */
    public static String floatForm(double d) {
        return String.format(Locale.US, "%.2f", d);
    }


    /**
     * Converts long into Human Readable String
     *
     * @param size
     * @return String
     */
    public static String bytesToHuman(long size) {
        long Kb = 1 * 1024;
        long Mb = Kb * 1024;
        long Gb = Mb * 1024;
        long Tb = Gb * 1024;
        long Pb = Tb * 1024;
        long Eb = Pb * 1024;

        if (size < Kb) return floatForm(size) + "";
        if (size >= Kb && size < Mb) return floatForm((double) size / Kb) + "";
        if (size >= Mb && size < Gb) return floatForm((double) size / Mb) + "";
        if (size >= Gb && size < Tb) return floatForm((double) size / Gb) + "";
        if (size >= Tb && size < Pb) return floatForm((double) size / Tb) + "";
        if (size >= Pb && size < Eb) return floatForm((double) size / Pb) + "";
        if (size >= Eb) return floatForm((double) size / Eb) + "";

        return "0.00";
    }

    public static int convertDp(Context context, int input) {
        int dp = (int) (context.getResources().getDimension(input) / context.getResources().getDisplayMetrics().density);
        return dp;
    }

    public static String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }

    /**
     * Check Genie supported file extension.
     *
     * @param filePath File path
     * @return Return true if supported else false.
     */
    public static boolean isValidExtension(String filePath) {
        if (StringUtil.isNullOrEmpty(filePath)) {
            return false;
        }

        String fileExtension = getFileExtension(filePath);

        return (fileExtension.equalsIgnoreCase(Constant.EXTENSION_CONTENT) ||
                fileExtension.equalsIgnoreCase(Constant.EXTENSION_TELEMETRY) ||
                fileExtension.equalsIgnoreCase(Constant.EXTENSION_PROFILE));
    }

    public static boolean isEpar(String filePath) {
        if (StringUtil.isNullOrEmpty(filePath)) {
            return false;
        }

        String fileExtension = getFileExtension(filePath);

        return (fileExtension.equalsIgnoreCase(Constant.EXTENSION_PROFILE));
    }

    public static boolean isEcar(String filePath) {
        if (StringUtil.isNullOrEmpty(filePath)) {
            return false;
        }

        String fileExtension = getFileExtension(filePath);

        return (fileExtension.equalsIgnoreCase(Constant.EXTENSION_CONTENT));
    }

    public static boolean isGsa(String filePath) {
        if (StringUtil.isNullOrEmpty(filePath)) {
            return false;
        }

        String fileExtension = getFileExtension(filePath);

        return (fileExtension.equalsIgnoreCase(Constant.EXTENSION_TELEMETRY));
    }


    public static void showCustomToast(int message) {
        showCustomToast(CoreApplication.getInstance().getString(message));
    }

    public static void showCustomToast(String message) {
        View layoutValue = LayoutInflater.from(CoreApplication.getInstance()).inflate(R.layout.layout_toast, null);
        Toast toast = new Toast(CoreApplication.getInstance());
        TextView txt_Message = (TextView) layoutValue.findViewById(R.id.text1);
        txt_Message.setText(message);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layoutValue);
        toast.show();
    }

    public static void processSuccess(GenieResponse genieResponse) {
        LogUtil.i(TAG, "Successfull :: " + genieResponse.getStatus());
        if (genieResponse.getResult() != null) {
            LogUtil.i(TAG, "Success Gson Response :: " + genieResponse.getResult().toString());
        }
    }

    public static void processImportSuccess(GenieResponse genieResponse) {
        LogUtil.i(TAG, "Successful :: " + genieResponse.getStatus());
        if (PreferenceUtil.isFirstTime().equalsIgnoreCase("false")) {
            showCustomToast(R.string.msg_import_success);
        }
    }

    public static void processFailure(GenieResponse genieResponse) {
        if (genieResponse != null) {
            if (genieResponse.getResult() != null) {
                LogUtil.e(TAG, "Failure Gson Response :: " + genieResponse.getResult().toString());
            }

            processErrorMessage(genieResponse.getError());
        }
    }

    public static void processFailure(Context context, Object object) {
        GenieResponse response = (GenieResponse) object;
        if (response != null) {
            processErrorMessage(response.getError());
        }
    }

    private static void processErrorMessage(String error) {
        if (error == null) {
            return;
        }

        LogUtil.e(TAG, "Genie Service Error Log :: " + error);

        Context context = CoreApplication.getInstance();

        if (Constant.INVALID_EVENT.equalsIgnoreCase(error)) {
            Toast.makeText(context, R.string.error_invalid_event, Toast.LENGTH_LONG).show();
        } else if (Constant.DB_ERROR.equalsIgnoreCase(error)) {
            Toast.makeText(context, R.string.error_db_operation, Toast.LENGTH_LONG).show();
        } else if (Constant.NETWORK_ERROR.equalsIgnoreCase(error)) {
            showCustomToast(R.string.error_network);
        } else if (Constant.VALIDATION_ERROR.equalsIgnoreCase(error)) {
            Toast.makeText(context, R.string.error_sync_validation, Toast.LENGTH_LONG).show();
        } else {
//            DialogUtils.showTechnicalErrorDialog(((Activity) context));
        }
    }

    public static void processSearchFailure(TextView textView, GenieResponse genieResponse) {
        if (genieResponse != null) {
            String error = genieResponse.getError();
            if (error == null) {
                return;
            }

            LogUtil.e(TAG, "Genie Service Error Log :: " + error);

            if (Constant.NETWORK_ERROR.equalsIgnoreCase(error)) {
                textView.setText(R.string.error_network2);
            } else if (Constant.CONNECTION_ERROR.equalsIgnoreCase(error)) {
                textView.setText(R.string.error_network2);
            } else if (Constant.SERVER_ERROR.equalsIgnoreCase(error)) {
                textView.setText(R.string.error_timeout);
            } else if (Constant.PAGE_FETCH_ERROR.equalsIgnoreCase(error)) {
                textView.setText(R.string.error_timeout);
            }
        }
    }

    public static void processExportFailure(Context context, GenieResponse response) {

        String error = response.getError();
        LogUtil.e("Genie Service Error Log", error);

        if (Constant.INVALID_FILE.equalsIgnoreCase(error)) {
            Toast.makeText(context, R.string.error_duplicate_file, Toast.LENGTH_LONG).show();
        } else if (Constant.DB_ERROR.equalsIgnoreCase(error)) {
            Toast.makeText(context, R.string.error_db_operation, Toast.LENGTH_LONG).show();
        } else if (Constant.PROCESSING_ERROR.equalsIgnoreCase(error)) {
            Toast.makeText(context, R.string.error_procession_failed, Toast.LENGTH_LONG).show();
        } else if (Constant.NETWORK_ERROR.equalsIgnoreCase(error)) {
            Toast.makeText(context, R.string.error_network, Toast.LENGTH_LONG).show();
        } else if (Constant.AUTHENTICATION_ERROR.equalsIgnoreCase(error)) {
            Toast.makeText(context, R.string.error_authentication_failed, Toast.LENGTH_LONG).show();
        } else if (Constant.VALIDATION_ERROR.equalsIgnoreCase(error)) {
            Toast.makeText(context, R.string.error_sync_validation, Toast.LENGTH_LONG).show();
        } else if (Constant.UNSUPPORTED_MANIFEST.equalsIgnoreCase(error)) {
            Toast.makeText(context, R.string.error_import_unsupported_manifest, Toast.LENGTH_LONG).show();
        } else if (Constant.IMPORT_FAILED.equalsIgnoreCase(error)) {
            String errorPos = "";
//            for (int i = 0; i < response.getErrorMessages().size(); i++) {
//                LogUtil.e("Position", "" + i);
//                errorPos = response.getErrorMessages().get(i);
//                LogUtil.e("Error info", errorPos);
//            }
//
//            if (errorPos.equalsIgnoreCase("The data to be imported belongs to older version. Current version: 2, Import version:1")) {
//                Toast.makeText(context, "The data to be imported belongs to older version. Current version: 2, Import version:1", Toast.LENGTH_LONG).show();
//            } else if (errorPos.equalsIgnoreCase("The data to be imported belongs to older version. Current version: 1, Import version:2")) {
//                Toast.makeText(context, "The data to be imported belongs to older version. Current version: 1, Import version:2", Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(context, R.string.import_failed_message, Toast.LENGTH_LONG).show();
//            }

        } else if (Constant.GENIE_SERVICE_NOT_INSTALLED.equalsIgnoreCase(error)) {
        } else if (Constant.DEVICE_MEMORY_FULL.equalsIgnoreCase(error)) {
            Toast.makeText(context, "Export failed. Device memory full.", Toast.LENGTH_LONG).show();
        } else if (Constant.MANIFEST_NOT_CREATED.equalsIgnoreCase(error)
                || Constant.CANNOT_CREATE_LOCATION.equalsIgnoreCase(error)
                ) {
            Toast.makeText(context, "Export failed. The content is not available on your device or your device doesn't have sufficient memory.", Toast.LENGTH_LONG).show();
        } else if (Constant.MANIFEST_NOT_WRITTEN.equalsIgnoreCase(error)) {
            Toast.makeText(context, "Export failed. The content is not available on your device or your device doesn't have sufficient memory.", Toast.LENGTH_LONG).show();
        } else if (Constant.COMPRESSION_FAILURE.equalsIgnoreCase(error)) {
            Toast.makeText(context, "Export failed. The content is not available on your device or your device doesn't have sufficient memory.", Toast.LENGTH_LONG).show();
        } else if (Constant.ECAR_NOT_WRITTEN.equalsIgnoreCase(error)) {
            Toast.makeText(context, "Export failed. The content is not available on your device or your device doesn't have sufficient memory.", Toast.LENGTH_LONG).show();
        } else {
            Util.showCustomToast("Export failed.");
        }
    }


    public static int getRandomInteger(int maximum, int minimum) {
        return ((int) (Math.random() * (maximum - minimum))) + minimum;
    }

    public static boolean isWhitespace(String str) {
        if (str == null) {
            return false;
        }

        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isAlphaNumeric(String s) {
        String pattern = "[A-Za-z0-9]+";

        return s.matches(pattern);
    }

    static public boolean isValidURL(String urlStr) {
        String s = urlStr.trim().toLowerCase();

        boolean isWeb = s.startsWith("http://") || s.startsWith("https://");

        return isWeb;
    }

    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");

        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public static String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }

        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    public static Intent getRefreshNotificationsIntent() {
        return new Intent(Constant.INTENT_ACTION_REFRESH_NOTIFICATION);
    }

    public static IntentFilter getRefreshNotificationsIntentFilter() {
        return new IntentFilter(Constant.INTENT_ACTION_REFRESH_NOTIFICATION);
    }


    public static String getOnBoardingCoRelationId() {
        String coRelationId = PreferenceUtil.getUniqueDeviceId() + DateUtil.getEpochTime();
        try {
            return CryptoUtil.checksum(coRelationId);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getGenieSpecificMessage(String errorCode, int stageCode) {
        String message = "";
        Context context = CoreApplication.getInstance();
        if (NetworkConstants.CONNECTION_ERROR.equalsIgnoreCase(errorCode)) {
            message = context.getString(R.string.error_network2);
        } else if (NetworkConstants.SERVER_ERROR.equalsIgnoreCase(errorCode)) {
            switch (stageCode) {
                case StageCode.SEARCH:
                    message = context.getString(R.string.error_timeout);
                    break;
                case StageCode.SYNC:
                    message = context.getString(R.string.error_sync_fail);
                    break;
            }

        } else if (ServiceConstants.ErrorCode.VALIDATION_ERROR.equalsIgnoreCase(errorCode)) {
            switch (stageCode) {
                case StageCode.ADDCHILD:
                    message = context.getString(R.string.error_sync_validation);
                    break;
                case StageCode.SYNC:
                    message = context.getString(R.string.error_sync_validation);
                    break;
            }


        }
        return message;
    }

    public static Boolean isChildSwitcherEnabled() {
        String partnerInfo = PreferenceUtil.getPartnerInfo();
        Boolean enableChildSwitcher = Boolean.FALSE;
        if (!StringUtil.isNullOrEmpty(partnerInfo)) {
            HashMap<String, Object> partnerMap = GsonUtil.fromJson(partnerInfo, HashMap.class);
            if (partnerMap.get(Constant.BUNDLE_KEY_ENABLE_CHILD_SWITCHER) != null) {
                enableChildSwitcher = (Boolean) partnerMap.get(Constant.BUNDLE_KEY_ENABLE_CHILD_SWITCHER);
            } else {
                enableChildSwitcher = Boolean.TRUE;
            }

        }
        return enableChildSwitcher;
    }

    public static String[] toTimesAgo(long time) {
        String relativeTime = DateUtils.getRelativeTimeSpanString(time, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        String[] relativeTimeArray = relativeTime.split(" ");
        return new String[]{relativeTimeArray[0], relativeTimeArray[1] + " " + relativeTimeArray[2]};
    }

    public static int getPixelValue(Context context, float dimenId) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dimenId,
                resources.getDisplayMetrics()
        );
    }

}
