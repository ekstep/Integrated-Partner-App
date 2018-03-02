package org.ekstep.openrap.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import org.ekstep.openrap.R;
import org.ekstep.openrap.asynctask.WifiConnectionAsyncTask;

/**
 * @author randhirgupta
 * @since 26/2/18.
 */

public class ConnectToOpenRapNetwork implements WifiConnectionAsyncTask.OnConnectedToOpenRapNetwork {

    private static final String TAG = "ConnectToOpenRapNetwork";
    private static final String OPEN_RAP_SSID = "openRAP";
    private static final String OPEN_RAP_PASSWORD = null;
    private Context mContext;
    private WifiManager mWifiManager;

    private BroadcastReceiver mWifiBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int WifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN);
            switch (WifiState) {
                case WifiManager.WIFI_STATE_ENABLED:
                    connectToWifi(OPEN_RAP_SSID, OPEN_RAP_PASSWORD);
                    break;
            }
        }
    };

    public ConnectToOpenRapNetwork(Context context) {
        this.mContext = context;
        mWifiManager = (WifiManager) mContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (mWifiManager != null && mWifiManager.isWifiEnabled()) {
            connectToWifi(OPEN_RAP_SSID, OPEN_RAP_PASSWORD);
        } else {
            showDialogForEnablingWifi();
        }
    }


    private void connectToWifi(String ssid, String password) {
        if (isConnectedToOpenRAPNetwork(ssid)) {
            Toast.makeText(mContext, mContext.getString(R.string.already_connected_open_rap_network), Toast.LENGTH_SHORT).show();
        } else {
            new WifiConnectionAsyncTask(ssid, password, mWifiManager, this).execute();
        }
    }


    private boolean isConnectedToOpenRAPNetwork(String ssid) {
        if (mWifiManager != null) {
            WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
            String currentSsid = wifiInfo.getSSID();
            if (currentSsid.equals(String.format("\"%s\"", ssid))) {
                return true;
            }
        }
        return false;
    }

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

    public void registerReceiver() {
        if (mContext != null) {
            mContext.registerReceiver(mWifiBroadcastReceiver, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
        }
    }

    public void unRegisterReceiver() {
        if (mContext != null) {
            mContext.unregisterReceiver(mWifiBroadcastReceiver);
        }
    }

    @Override
    public void onConnectedToOpenRapNetwork(boolean isConnected) {
        if (isConnected) {
            Toast.makeText(mContext, mContext.getString(R.string.connected_open_rap_network), Toast.LENGTH_SHORT).show();
        }
    }
}
