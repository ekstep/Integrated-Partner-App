package org.ekstep.openrap.nsd;

import android.net.nsd.NsdServiceInfo;

/**
 * @author randhirgupta
 * @since 21/2/18.
 */

public interface OpenRAPDiscoveryListener {

    void onNsdServiceFound(NsdServiceInfo foundServiceInfo);

    void onNsdDiscoveryFinished();

    void onNsdServiceResolved(NsdServiceInfo resolvedNsdServiceInfo);

    void onConnectedToService(NsdServiceInfo connectedServiceInfo);

    void onNsdServiceLost(NsdServiceInfo nsdServiceInfo);
}
