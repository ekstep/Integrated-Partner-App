package org.ekstep.openrap.asynctask;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.text.TextUtils;

import java.util.List;

/**
 * @author randhirgupta
 * @since 26/2/18.
 */

public class WifiConnectionAsyncTask extends AsyncTask<Void, Void, Boolean> {

    private String mSsid;
    private String mPassword;
    private WifiManager mWifiManager;
    private OnConnectedToOpenRapNetwork mOnConnectedToOpenRapNetwork;

    public WifiConnectionAsyncTask(String ssid, String password, WifiManager wifiManager,
                                   OnConnectedToOpenRapNetwork onConnectedToOpenRapNetwork) {
        this.mSsid = ssid;
        this.mPassword = password;
        this.mWifiManager = wifiManager;
        this.mOnConnectedToOpenRapNetwork = onConnectedToOpenRapNetwork;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        WifiConfiguration wifiConfiguration = getWifiConfiguration(mSsid);
        if (wifiConfiguration == null) {
            wifiConfiguration = new WifiConfiguration();
            wifiConfiguration.SSID = String.format("\"%s\"", mSsid);
            if (!TextUtils.isEmpty(mPassword)) {
                wifiConfiguration.preSharedKey = String.format("\"%s\"", mPassword);
            } else {
                wifiConfiguration.wepKeys[0] = "\"\"";
                wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                wifiConfiguration.wepTxKeyIndex = 0;
            }
            mWifiManager.addNetwork(wifiConfiguration);
        }

        mWifiManager.disconnect();
        mWifiManager.enableNetwork(wifiConfiguration.networkId, true);
        mWifiManager.reconnect();

        return mWifiManager.reconnect();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        mOnConnectedToOpenRapNetwork.onConnectedToOpenRapNetwork(aBoolean);
    }

    private WifiConfiguration getWifiConfiguration(String ssid) {
        List<WifiConfiguration> wifiList = mWifiManager.getConfiguredNetworks();
        if (wifiList != null) {
            for (WifiConfiguration wifiConfiguration : wifiList) {
                if (wifiConfiguration.SSID != null && wifiConfiguration.SSID.equals(String.format("\"%s\"", ssid))) {
                    return wifiConfiguration;
                }
            }
        }
        return null;
    }

    public interface OnConnectedToOpenRapNetwork {
        void onConnectedToOpenRapNetwork(boolean isConnected);
    }
}