package org.ekstep.openrap.nsd;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdManager.DiscoveryListener;
import android.net.nsd.NsdServiceInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import org.ekstep.openrap.BuildConfig;
import org.ekstep.openrap.asynctask.ConnectToOpenRAPAsyncTask;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collections;

import static android.net.nsd.NsdManager.PROTOCOL_DNS_SD;
import static android.net.nsd.NsdManager.ResolveListener;

/**
 * @author randhirgupta
 * @since 20/2/18.
 */

public class OpenRapDiscoveryHelper {

    private static final String TAG = "OpenRapDiscoveryHelper";

    private final NsdManager mNsdManager;
    private InetAddress mHostAddress;
    private int mHostPort;
    private Context mContext;
    private String mDiscoveryServiceType;
    private String mDiscoveryServiceName;
    private boolean mDiscoveryStarted = false;


    private OpenRAPDiscoveryListener mNsdListener;

    private ResolveListener mResolveListener = new ResolveListener() {

        @Override
        public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onResolveFailed: " + serviceInfo.getServiceName() + " " + errorCode);
            }
        }

        @Override
        public void onServiceResolved(NsdServiceInfo serviceInfo) {
            mHostAddress = serviceInfo.getHost();
            mHostPort = serviceInfo.getPort();

            if (mNsdListener != null) {
                mNsdListener.onNsdServiceResolved(serviceInfo);
            }

            connectToHost(serviceInfo);
        }
    };

    private DiscoveryListener mDiscoveryListener = new DiscoveryListener() {

        @Override
        public void onStartDiscoveryFailed(String serviceType, int errorCode) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onStartDiscoveryFailed: " + errorCode);
            }
            stopDiscovery();
        }

        @Override
        public void onStopDiscoveryFailed(String serviceType, int errorCode) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onStopDiscoveryFailed: " + errorCode);
            }
        }

        @Override
        public void onDiscoveryStarted(String serviceType) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onDiscoveryStarted: ");
            }
        }

        @Override
        public void onDiscoveryStopped(String serviceType) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onDiscoveryStopped: ");
            }
        }

        @Override
        public void onServiceFound(final NsdServiceInfo serviceInfo) {
            Log.d(TAG, "onServiceFound: ");

            if (mNsdListener != null) {
                mNsdListener.onNsdServiceFound(serviceInfo);
            }

            resolveService(serviceInfo);
        }

        @Override
        public void onServiceLost(NsdServiceInfo serviceInfo) {
            if (mNsdListener != null) {
                mNsdListener.onNsdServiceLost(serviceInfo);
            }
        }
    };


    public OpenRapDiscoveryHelper(Context context, OpenRAPDiscoveryListener nsdListener) {
        this.mContext = context;
        this.mNsdManager = (NsdManager) mContext.getSystemService(Context.NSD_SERVICE);
        this.mNsdListener = nsdListener;
    }

    public void startDiscovery(String serviceType, String serviceName) {
        if (!mDiscoveryStarted) {
            mDiscoveryStarted = true;
            mDiscoveryServiceType = serviceType;
            mDiscoveryServiceName = serviceName;
            mNsdManager.discoverServices(mDiscoveryServiceType, PROTOCOL_DNS_SD, mDiscoveryListener);
        }
    }

    private void stopDiscovery() {

        if (mDiscoveryStarted) {
            mDiscoveryStarted = false;
            mNsdManager.stopServiceDiscovery(mDiscoveryListener);
        }

        if (mNsdListener != null) {
            mNsdListener.onNsdDiscoveryFinished();
        }
    }

    private void resolveService(NsdServiceInfo nsdServiceInfo) {
        mNsdManager.resolveService(nsdServiceInfo, mResolveListener);
    }

    private void connectToHost(final NsdServiceInfo nsdServiceInfo) {

//        String ipAddress = getLocalIpAddress();
//        JSONObject jsonData = new JSONObject();
//
//        try {
//            jsonData.put("request", "_openrap._tcp");
//            jsonData.put("ipAddress", ipAddress);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        new ConnectToOpenRAPAsyncTask(mHostAddress, mHostPort, new ConnectToOpenRAPAsyncTask.OnConnectionCompleted() {
            @Override
            public void onConnectionCompleted(boolean isConnectionDone) {
                mNsdListener.onConnectedToService(nsdServiceInfo);
            }
        }).execute();
    }

    private String getLocalIpAddress() {

        String localIpAddress = null;
        WifiManager wifiManager = (WifiManager) mContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            byte[] ipAddress = BigInteger.valueOf(wifiManager.getConnectionInfo().getIpAddress()).toByteArray();
            Collections.reverse(Arrays.asList(ipAddress));
            try {
                InetAddress inetAddress = InetAddress.getByAddress(ipAddress);
                localIpAddress = inetAddress.getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return localIpAddress;
    }
}
