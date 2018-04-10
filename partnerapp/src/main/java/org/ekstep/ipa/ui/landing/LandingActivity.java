package org.ekstep.ipa.ui.landing;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.ekstep.genie.activity.BaseActivity;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.ipa.R;
import org.ekstep.ipa.ui.addchild.AddChildActivity;
import org.ekstep.ipa.ui.landing.geniechildren.GenieChildrenFragment;

/**
 * @author vinayagasundar
 */

public class LandingActivity extends BaseActivity implements LandingContract.View {

    private static final String OPEN_RAP_SSID = "openRAP";
    private LandingContract.Presenter mPresenter;
    private ConnectivityManager mConnectivityManager;


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)) {
                if (isEnabled() && isConnectedToOpenRAPNetwork(OPEN_RAP_SSID)) {
                    showDialogForDisablingMobileData();
                }
            }
        }
    };

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

        mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mBroadcastReceiver);
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

    private void showDialogForDisablingMobileData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(org.ekstep.openrap.R.string.disable_internet_msg));

        builder.setPositiveButton(getString(org.ekstep.openrap.R.string.dialog_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                goToNetworkSettingScreen();
            }
        });

        builder.setNegativeButton(getString(org.ekstep.openrap.R.string.dialog_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setCancelable(false);
        builder.show();
    }

    private void goToNetworkSettingScreen() {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setComponent(new ComponentName("com.android.settings",
                    "com.android.settings.Settings$DataUsageSummaryActivity"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } catch (ActivityNotFoundException ignored) {
        }
    }

    public boolean isEnabled() {
        return (((mConnectivityManager != null) && mConnectivityManager.getActiveNetworkInfo() != null) ?
                mConnectivityManager.getActiveNetworkInfo().getType() : 0) == ConnectivityManager.TYPE_MOBILE;
    }

    private boolean isConnectedToOpenRAPNetwork(String ssid) {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (wifiManager != null) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null) {
                NetworkInfo.DetailedState state = WifiInfo.getDetailedStateOf(wifiInfo.getSupplicantState());
                String currentSsid = wifiInfo.getSSID();
                return currentSsid.equals(String.format("\"%s\"", ssid)) &&
                        (state == NetworkInfo.DetailedState.CONNECTED || state == NetworkInfo.DetailedState.OBTAINING_IPADDR);
            }
        }
        return false;
    }
}
