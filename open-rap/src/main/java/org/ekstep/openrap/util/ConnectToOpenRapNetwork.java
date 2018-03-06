package org.ekstep.openrap.util;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.net.NetworkInfo.DetailedState;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import org.ekstep.openrap.R;
import org.ekstep.openrap.asynctask.WifiConnectionAsyncTask;

import java.util.List;

/**
 * @author randhirgupta
 * @since 26/2/18.
 */

public class ConnectToOpenRapNetwork implements WifiConnectionAsyncTask.OnConnectedToOpenRapNetwork {

    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final String TAG = "ConnectToOpenRapNetwork";
    private static final String OPEN_RAP_SSID = "openRAP";
    private static final String OPEN_RAP_PASSWORD = null;
    private Context mContext;
    private WifiManager mWifiManager;
    private boolean isOpenRAPNetworkAvailable = false;
    private GoogleApiClient googleApiClient;

    private BroadcastReceiver mWifiBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int WifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN);

            switch (WifiState) {

                case WifiManager.WIFI_STATE_ENABLED:
                    connectToWifi(OPEN_RAP_SSID, OPEN_RAP_PASSWORD, isOpenRAPNetworkAvailable);
                    break;

                case WifiManager.WIFI_STATE_DISABLED:
                    showDialogForEnablingWifi();
                    break;
            }

            List<ScanResult> availableWifis = mWifiManager.getScanResults();
            for (ScanResult scanResult : availableWifis) {
                if (scanResult.SSID != null && scanResult.SSID.equals(OPEN_RAP_SSID)) {
                    isOpenRAPNetworkAvailable = true;
                    if (!isConnectedToOpenRAPNetwork(OPEN_RAP_SSID)) {
                        connectToWifi(OPEN_RAP_SSID, OPEN_RAP_PASSWORD, isOpenRAPNetworkAvailable);
                    }
                } else {
                    isOpenRAPNetworkAvailable = false;
                }
            }
        }
    };

    public ConnectToOpenRapNetwork(Activity activity, Context context) {
        this.mContext = context;
        mWifiManager = (WifiManager) mContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        googleApiClient = new GoogleApiClient.Builder(mContext).addApi(LocationServices.API).build();
        googleApiClient.connect();
        requestGPSSettings(activity, mContext);

        if (mWifiManager != null && mWifiManager.isWifiEnabled()) {
            connectToWifi(OPEN_RAP_SSID, OPEN_RAP_PASSWORD, isOpenRAPNetworkAvailable);
        }
    }


    /**
     * This method heps in connecting to openRAP network
     *
     * @param ssid
     * @param password
     * @param isOpenRAPNetworkAvailable
     */
    private void connectToWifi(String ssid, String password, boolean isOpenRAPNetworkAvailable) {
        if (isConnectedToOpenRAPNetwork(ssid)) {
            Toast.makeText(mContext, mContext.getString(R.string.already_connected_open_rap_network), Toast.LENGTH_SHORT).show();
        } else if (isOpenRAPNetworkAvailable) {
            new WifiConnectionAsyncTask(ssid, password, mWifiManager, this).execute();
        }
    }


    /**
     * This method checks whether the wifi is connected to openRAP network or not
     *
     * @param ssid
     * @return
     */
    private boolean isConnectedToOpenRAPNetwork(String ssid) {
        if (mWifiManager != null) {
            WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
            if (wifiInfo != null) {
                DetailedState state = WifiInfo.getDetailedStateOf(wifiInfo.getSupplicantState());
                String currentSsid = wifiInfo.getSSID();
                if (currentSsid.equals(String.format("\"%s\"", ssid)) &&
                        (state == DetailedState.CONNECTED || state == DetailedState.OBTAINING_IPADDR)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method shows dialog for enabling the wifi to connect to openRAP network
     */
    private void showDialogForEnablingWifi() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(mContext.getString(R.string.enable_wifi));

        builder.setPositiveButton(mContext.getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mWifiManager != null) {
                    mWifiManager.setWifiEnabled(true);
                }
            }
        });

        builder.setNegativeButton(mContext.getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setCancelable(false);
        builder.show();
    }

    /**
     * This method registers the broadcast for wifi state and to scan for available wifi
     */
    public void registerReceiver() {
        if (mContext != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
            intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
            mContext.registerReceiver(mWifiBroadcastReceiver, intentFilter);
            mWifiManager.startScan();
        }
    }

    /**
     * This method unregister the broadcast
     */
    public void unRegisterReceiver() {
        if (mContext != null) {
            mContext.unregisterReceiver(mWifiBroadcastReceiver);
        }
    }

    @Override
    public void onConnectedToOpenRapNetwork(boolean isConnected) {
        if (isConnectedToOpenRAPNetwork(OPEN_RAP_SSID)) {
            Toast.makeText(mContext, mContext.getString(R.string.connected_open_rap_network), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This  methods enables the gps for getting location for finding available wifi networks
     *
     * @param activity
     * @param context
     */
    private void requestGPSSettings(final Activity activity, final Context context) {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setInterval(2000);
        locationRequest.setFastestInterval(500);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException ignored) {
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }
}
