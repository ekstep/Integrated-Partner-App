package org.ekstep.ipa;

import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.util.RawUtil;
import org.ekstep.genieservices.GenieService;
import org.ekstep.genieservices.commons.utils.GsonUtil;
import org.ekstep.ipa.db.PartnerDB;
import org.ekstep.ipa.model.PartnerConfig;
import org.ekstep.ipa.util.SDKParams;
import org.ekstep.ipa.util.preference.PartnerPrefUtil;
import org.ekstep.openrap.nsd.OpenRAPDiscoveryListener;
import org.ekstep.openrap.nsd.OpenRapDiscoveryHelper;

/**
 * @author vinayagasundar
 */

public class PartnerApp extends CoreApplication implements OpenRAPDiscoveryListener {

    private static final String TAG = "LandingActivity";

    private static PartnerApp mPartnerApp;

    private PartnerConfig mPartnerConfig;


    public static PartnerApp getPartnerApp() {
        return mPartnerApp;
    }


    @Override
    protected void appInit() {
        super.appInit();

        mPartnerApp = this;
        initPartner();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        new OpenRapDiscoveryHelper(this, this)
                .startDiscovery("_openrap._tcp", "Open Resource Access Point");
    }

    @Override
    protected String getConfigPackageName() {
        return "org.ekstep.ipa";
    }

    /**
     * Get the Partner Config.
     *
     * @return instance of the Partner Config
     */
    public PartnerConfig getPartnerConfig() {
        return mPartnerConfig;
    }


    /**
     * Initialize the Partner Apps features in this method
     */
    private void initPartner() {
        PartnerDB.initDataBase(this);


        String partnerConfigFileString = RawUtil.readRawFile(this, R.raw.partner_config);

        if (partnerConfigFileString != null) {
            mPartnerConfig = GsonUtil.fromJson(partnerConfigFileString, PartnerConfig.class);
        }
    }

    @Override
    public void onNsdServiceFound(NsdServiceInfo foundServiceInfo) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onNsdServiceFound: " + foundServiceInfo.getServiceName());
        }
    }

    @Override
    public void onNsdDiscoveryFinished() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onNsdDiscoveryFinished: ");
        }
    }

    @Override
    public void onNsdServiceResolved(NsdServiceInfo resolvedNsdServiceInfo) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onNsdServiceResolved: " + resolvedNsdServiceInfo.getServiceName());
            Log.d(TAG, "onNsdServiceResolved: " + resolvedNsdServiceInfo.getServiceType());
        }
    }

    @Override
    public void onConnectedToService(NsdServiceInfo connectedServiceInfo) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onConnectedToService: " + connectedServiceInfo.getHost());
        }

        PartnerPrefUtil.setOpenRapHostAndPort(connectedServiceInfo.getHost().toString(), connectedServiceInfo.getPort());
        setParams();
    }

    @Override
    public void onNsdServiceLost(NsdServiceInfo nsdServiceInfo) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onNsdServiceLost: " + nsdServiceInfo.getServiceName());
        }
    }

    public void setParams() {
        SDKParams sdkParams = new SDKParams();

        String openRapHost = PartnerPrefUtil.getOpenRapHost();
        openRapHost = openRapHost.replace("/", "http://");

        String telemetryBaseurl = openRapHost + "/api/data/v3";
        sdkParams.put(SDKParams.Key.TELEMETRY_BASE_URL, telemetryBaseurl);

        String contentBaseUrl = openRapHost + "/api/content/v3";
        sdkParams.put(SDKParams.Key.CONTENT_BASE_URL, contentBaseUrl);

        String searchBaseUrl = openRapHost + "/api/composite/v3";
        sdkParams.put(SDKParams.Key.SEARCH_BASE_URL, searchBaseUrl);

        String contentListingBaseUrl = openRapHost + "/api/data/v3";
        sdkParams.put(SDKParams.Key.CONTENT_LISTING_BASE_URL, contentListingBaseUrl);

        GenieService.setParams(sdkParams);
    }
}
