package org.ekstep.ipa.ui.landing;

import android.content.Intent;
import android.net.nsd.NsdServiceInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.ekstep.genie.activity.BaseActivity;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genieservices.GenieService;
import org.ekstep.ipa.BuildConfig;
import org.ekstep.ipa.R;
import org.ekstep.ipa.ui.addchild.AddChildActivity;
import org.ekstep.ipa.ui.landing.geniechildren.GenieChildrenFragment;
import org.ekstep.ipa.util.SDKParams;
import org.ekstep.ipa.util.preference.PartnerPrefUtil;
import org.ekstep.openrap.nsd.OpenRAPDiscoveryListener;
import org.ekstep.openrap.nsd.OpenRapDiscoveryHelper;
import org.ekstep.openrap.util.ConnectToOpenRapNetwork;

/**
 * @author vinayagasundar
 */

public class LandingActivity extends BaseActivity implements LandingContract.View, OpenRAPDiscoveryListener {


    private static final String TAG = "LandingActivity";

    private LandingContract.Presenter mPresenter;
    private ConnectToOpenRapNetwork mConnectToOpenRapNetwork;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_landing);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        mPresenter = (LandingContract.Presenter) presenter;

        mPresenter.start();

        mConnectToOpenRapNetwork = new ConnectToOpenRapNetwork(this);

        new OpenRapDiscoveryHelper(this, this)
                .startDiscovery("_openrap._tcp", "Open Resource Access Point");
    }


    @Override
    protected void onResume() {
        super.onResume();
        mConnectToOpenRapNetwork.registerReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mConnectToOpenRapNetwork.unRegisterReceiver();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_landing, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.select_child) {
            mPresenter.showAddChild();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showGenieChildFragment() {
        Fragment fragment = new GenieChildrenFragment();
        setFragment(fragment, false, R.id.fragment_container, false);
    }

    @Override
    public void showAddChildFragment() {
        Intent intent = new Intent(this, AddChildActivity.class);
        startActivity(intent);
    }

    @Override
    public void setToolbarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new LandingPresenter();
            }
        };
    }

    @Override
    protected String getTAG() {
        return getLocalClassName();
    }

    @Override
    protected BaseView getBaseView() {
        return this;
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
