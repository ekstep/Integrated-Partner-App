package org.ekstep.genie.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by swayangjit on 29/3/17.
 */

public class NetworkUtil {
    public static final int NETWORK_STATUS_NOT_CONNECTED = 0, NETWORK_STAUS_WIFI = 1, NETWORK_STATUS_MOBILE = 2;
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static int getConnectivityStatusString(Context context) {
        int conn = NetworkUtil.getConnectivityStatus(context);
        int status = 0;
        if (conn == NetworkUtil.TYPE_WIFI) {
            status = NETWORK_STAUS_WIFI;
        } else if (conn == NetworkUtil.TYPE_MOBILE) {
            status = NETWORK_STATUS_MOBILE;
        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
            status = NETWORK_STATUS_NOT_CONNECTED;
        }
        return status;
    }

    /**
     * Checks whether Network is available or not .
     *
     * @param context
     * @return boolean
     */
    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo wifiState;
        NetworkInfo mobileState;
        ConnectivityManager connManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));

        if (connManager != null) {
            wifiState = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            mobileState = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            //Check if the connection exists and if the connection is connected or in the process of connecting
            if ((wifiState != null && (wifiState.getState() == NetworkInfo.State.CONNECTED || wifiState.getState() == NetworkInfo.State.CONNECTING)) ||
                    (mobileState != null && (mobileState.getState() == NetworkInfo.State.CONNECTED || mobileState.getState() == NetworkInfo.State.CONNECTING))) {
                return true;
            }
        }

        return false;
    }
}
