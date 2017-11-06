package org.ekstep.genie.ui.landing;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import org.ekstep.genie.R;
import org.ekstep.genie.activity.BaseActivity;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.base.PresenterManager;
import org.ekstep.genie.callback.IMenuItemClick;
import org.ekstep.genie.customview.EkStepCheckBox;
import org.ekstep.genie.firebase.fcm.FirebaseInstanceUtil;
//import org.ekstep.genie.fragment.DataSettingsFragment;
import org.ekstep.genie.fragment.DummyLanguageTestFragment;
import org.ekstep.genie.model.enums.SyncConfiguration;
import org.ekstep.genie.receiver.NetworkChangeReceiver;
import org.ekstep.genie.ui.importarchive.ImportArchiveActivity;
import org.ekstep.genie.ui.landing.home.HomeFragment;
import org.ekstep.genie.ui.managechild.ManageChildFragment;
import org.ekstep.genie.ui.mycontent.MyContentFragment;
import org.ekstep.genie.ui.notification.NotificationFragment;
import org.ekstep.genie.ui.settings.SettingsActivity;
import org.ekstep.genie.ui.transfer.TransferFragment;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.DeviceUtility;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.ShowProgressDialog;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.geniesdk.SyncServiceUtil;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.commons.bean.Profile;

import java.util.List;

public class LandingActivity extends BaseActivity
        implements LandingContract.View, IMenuItemClick {

    private static final String TAG = LandingActivity.class.getSimpleName();

    private static final int NAV_MANAGE_CHILD = 0;
    private static final int NAV_NOTIFICATION = 1;
    private static final int NAV_MY_CONTENT = 2;
    private static final int NAV_SHARE_GENIE = 3;
    private static final int NAV_TRANSFER = 4;
    private static final int NAV_SETTINGS = 5;
    private static final String DATA_SETTINGS = "data_settings";
    private DrawerLayout mDrawerLayout = null;
    private MenuDrawerAdapter mMenuDrawerAdapter;
    /*Presenter for Landing Activity*/
    private LandingContract.Presenter mLandingPresenter;
    private INotify mNotify;
    private BroadcastReceiver mNotificationRefreshBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mLandingPresenter.updateUnreadNotificationCount();
            if (mNotify != null) {
                mNotify.onNotificationReceived();
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(mNotificationRefreshBroadcastReceiver, Util.getRefreshNotificationsIntentFilter());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mNotificationRefreshBroadcastReceiver);
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

    public void setNotificationListener(INotify notify) {
        this.mNotify = notify;
    }

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mLandingPresenter = (LandingContract.Presenter) PresenterManager.getInstance()
                .getPresenter(getTAG(), getPresenterFactory());
        mLandingPresenter.handleAppUpdate();
        if (PreferenceUtil.isGenieSessionEnded().equalsIgnoreCase("true")) {
            PreferenceUtil.setGenieSessionEnded("false");
        }
        Bundle extras = getIntent().getExtras();
        if (Constant.VIEW_FROM_VALUE_CHILD_VIEW.equals(PreferenceUtil.getViewFrom())) {
            PreferenceUtil.setViewFrom(null);
        } else {
            if (extras == null) {
                mLandingPresenter.importExternalContent();
//                mLandingPresenter.getFcmTokenAndDisableNCReceiver();
            }
        }

        //initialize views
        initViews();
        mLandingPresenter.showUIAccordingToLanguage();
        mLandingPresenter.adjustNavigationDrawerPadding();
        mLandingPresenter.switchToRelevantFragment(savedInstanceState, extras);
        mLandingPresenter.showSyncPromt();
    }

    @Override
    public void switchToRelevantFragment(Bundle savedInstanceState, Bundle extras) {
        if (savedInstanceState == null) {
            if (extras != null) {
                if (extras.containsKey(Constant.PROFILE_SUMMARY)) {
                    // When coming from child summary screen for edit profile
                    Profile profile = extras.getParcelable(Constant.PROFILE_SUMMARY);
                    mLandingPresenter.callAddChildActivity(profile);
                } else if (extras.containsKey(Constant.EXTRA_DEEP_LINK_TRANSFER)) { // Transfer Screen
                    mLandingPresenter.showTransferScreen();
                } else if (extras.containsKey(Constant.EXTRA_DEEP_LINK_MANAGE_CHILD)) { // Manage child Screen
                    mLandingPresenter.showManageChildScreen();
                } else if (extras.containsKey(Constant.EXTRA_DEEP_LINK_DATA_SYNC)) { // Data sync setting Screen
                    mLandingPresenter.setDataSettingsScreen();
                } else {
                    // My Content
                    mLandingPresenter.setMyContentsScreen();
                }
            } else {
                if (PreferenceUtil.isShowDummyFragment().equalsIgnoreCase("true")) {
                    PreferenceUtil.setShowDummyFragment("");
                    mLandingPresenter.setDummyLanguageScreen();
                } else {
                    mLandingPresenter.showHomeScreen(false);
                }
            }
        }
    }

    /**
     * get FCM token and disable NetworkChangeReceiver if FCM token is available.
     */
    @Override
    public void getFcmTokenAndDisableNCReceiver() {
        if (!TextUtils.isEmpty(PreferenceUtil.getFcmToken())) {
            // Disable the NetworkChangeReceiver if FCM token is available.
            ComponentName receiver = new ComponentName(LandingActivity.this, NetworkChangeReceiver.class);
            PackageManager pm = this.getPackageManager();
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);
        } else {
            // Get the FCM token if its not available.
            String token = FirebaseInstanceUtil.getFirebaseInstanceId().getToken();
            LogUtil.d(TAG, "InstanceID token: " + token);
            if (!TextUtils.isEmpty(token)) {
                PreferenceUtil.setFcmToken(token);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
        toolbar.setVisibility(View.GONE);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.action_nav_drawer_open, R.string.action_nav_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        initializeNavigationDrawer();
    }

    public void callAddChildActivity(Profile profile) {
//        Intent addChildIntent = new Intent(LandingActivity.this, AddChildActivity.class);
//        addChildIntent.putExtra(Constant.BUNDLE_UPDATED_PROFILE, profile);
//        addChildIntent.putExtra(Constant.BUNDLE_EDIT_MODE, true);
//        startActivity(addChildIntent);
    }

    public void adjustNavigationDrawerPadding() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            findViewById(R.id.nav_view).setPadding(10, 25, 20, 2);
        } else {
            findViewById(R.id.nav_view).setPadding(10, 0, 20, 2);
        }
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    private void initializeNavigationDrawer() {
        final RecyclerView recyclerViewMenu = (RecyclerView) findViewById(R.id.list_drawer);
        mMenuDrawerAdapter = new MenuDrawerAdapter(this, LandingActivity.this);
        recyclerViewMenu.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMenu.setHasFixedSize(true);
        recyclerViewMenu.setAdapter(mMenuDrawerAdapter);

        // To get the unread notification count.
        mLandingPresenter.updateUnreadNotificationCount();
    }

    @Override
    public void onMenuItemClick(int position) {
        navigate(position);
    }

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    private void navigate(int position) {
        switch (position) {
            case NAV_SHARE_GENIE:
                mLandingPresenter.setCorelationIdAndStartGenieShare();
                mDrawerLayout.closeDrawers();
                break;

            case NAV_NOTIFICATION:
                mLandingPresenter.showNotificationScreen();
                break;

            case NAV_MANAGE_CHILD:
                mLandingPresenter.showManageChildScreen();
                break;

            case NAV_MY_CONTENT:
                mLandingPresenter.showMyContentScreen();
                break;

            case NAV_TRANSFER:
                mLandingPresenter.showTransferScreen();
                break;

            case NAV_SETTINGS:
                mLandingPresenter.showSettingsScreen();
                break;

            default:
                break;
        }
    }

    /**
     * Replaces current fragment with Notifications Fragment
     */
    public void showNotificationFragment() {
        if (getRunningFragment() instanceof NotificationFragment) {
            mDrawerLayout.closeDrawers();
        } else {
            mDrawerLayout.closeDrawers();
            NotificationFragment notificationFragment = new NotificationFragment();
            //PresenterManager.getInstance().create(LandingActivity.this, notificationFragment);
            setFragment(notificationFragment, false);
            mMenuDrawerAdapter.setUnReadNotificationCount(0);
        }
    }

    public void adjustUIForTelugu() {
//        TextView txt_drawer_home = (TextView) findViewById(R.id.item_home);
//        TextView txt_drawer_share = (TextView) findViewById(R.id.item_share);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
//            txt_drawer_home.setTextSize(14);
//            txt_drawer_share.setTextSize(14);
//            layoutParams.setMargins(5, 0, 5, 5);
//            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
//            txt_drawer_home.setLayoutParams(layoutParams);
//        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        } else {
            Fragment fragment = getRunningFragment();
            mLandingPresenter.onBackPressed(fragment);
        }
    }

    public void endGenieDialog() {
        final Dialog dialog = new Dialog(LandingActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_end_genie);
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_pressed}, // pressed
                new int[]{android.R.attr.state_focused}, // focused
                new int[]{}
        };
        int[] colors = new int[]{
                ContextCompat.getColor(this, R.color.white), // green
                ContextCompat.getColor(this, R.color.white), // green
                ContextCompat.getColor(this, R.color.app_black_theme_color)  // white
        };
        ColorStateList list = new ColorStateList(states, colors);
        dialog.findViewById(R.id.txt_no).setFocusable(true);
        dialog.findViewById(R.id.txt_no).setClickable(true);
        ((TextView) dialog.findViewById(R.id.txt_no)).setTextColor(list);
        // if button is clicked, close the custom dialog
        dialog.findViewById(R.id.txt_yes).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mLandingPresenter.saveEndGenieEvent();
            }
        });

        dialog.findViewById(R.id.txt_no).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    /**
     * show sync now dialog if the user is connected to either "Manual" or "Automatic over Wifi" mode
     */
    public void syncNowDialog() {
        final Dialog dialog = new Dialog(LandingActivity.this);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_sync_now);

        final EkStepCheckBox checkBox = (EkStepCheckBox) dialog.findViewById(R.id.is_sync_now);
        final SyncConfiguration syncConfig = SyncServiceUtil.getConfiguration();
        if (syncConfig.name().equalsIgnoreCase(SyncConfiguration.OVER_ANY_MODE.name())) {
            checkBox.setChecked(true);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SyncServiceUtil.setConfiguration(SyncConfiguration.OVER_ANY_MODE);
                } else {
                    SyncServiceUtil.setConfiguration(syncConfig);
                }
            }
        });
        // if button is clicked, close the custom dialog and do auto sync
        dialog.findViewById(R.id.sync_now_btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mLandingPresenter.syncNow();
            }
        });
        dialog.findViewById(R.id.sync_later_btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        DeviceUtility.displayFullScreenDialog(dialog, LandingActivity.this);
    }

    public void showHome(boolean isCloseDrawer) {
        HomeFragment homeFragment = new HomeFragment();
        //if isCloseDrawer is true, then the call is from Navigation Drawer and only then you have to close drawers
        if (isCloseDrawer) {
            if (getRunningFragment() instanceof HomeFragment) {
                mDrawerLayout.closeDrawers();
            } else {
                setFragment(homeFragment, false);
                mDrawerLayout.closeDrawers();
            }
        } else {
            setFragment(homeFragment, true);
        }
    }

    public void setManageChildScreen() {
        ManageChildFragment manageChildFragment = new ManageChildFragment();
        setFragment(manageChildFragment, false);
    }

    public void showManageChildFragment() {
        setManageChildScreen();
        mDrawerLayout.closeDrawers();
    }

    @Override
    public void showDataSettingsFragment() {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra(DATA_SETTINGS, 1);
        startActivity(intent);
    }

    @Override
    public void showMyContentsFragment() {
        MyContentFragment myContentFragment = new MyContentFragment();
        setFragment(myContentFragment, true);
    }

    @Override
    public void showDummyLanguageFragment() {
        setFragment(new DummyLanguageTestFragment(), true);
    }

    @Override
    public void doShowSyncPromt() {
    }

    public void showMyContent() {
        MyContentFragment myContentFragment = new MyContentFragment();
        setFragment(myContentFragment, false);
        mDrawerLayout.closeDrawers();
    }

    public void showTransferFragment() {
        TransferFragment transferFragment = new TransferFragment();
        setFragment(transferFragment, true);
        mDrawerLayout.closeDrawers();
    }

    public void showSettings() {
        mDrawerLayout.closeDrawers();
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);

    }

    @Override
    public void showProgressDialog(String message) {
        ShowProgressDialog.showProgressDialog(LandingActivity.this, message);
    }

    @Override
    public void dismissProgressDialog() {
        ShowProgressDialog.dismissDialog();
    }

    @Override
    public void showSyncingDialog(String message) {
        showSyncDialog(message);
    }

    @Override
    public void finishViewAffinity() {
        finishAffinity();
    }

    @Override
    public void setRespectiveFragment(Fragment fragment, boolean flag) {
        setFragment(fragment, flag);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        super.onPermissionsGranted(requestCode);
        mLandingPresenter.permissionGranted(requestCode);
    }

    public void launchImportArchiveView(boolean isProfile) {
        Intent intent = new Intent(LandingActivity.this, ImportArchiveActivity.class);
        intent.putExtra(Constant.IS_PROFILE, isProfile);
        startActivity(intent);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showUnreadNotificationCount(int count) {
        mMenuDrawerAdapter.setUnReadNotificationCount(count);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> deniedPermissions) {
        super.onPermissionsDenied(requestCode, deniedPermissions);
        mLandingPresenter.permissionDenied(requestCode, deniedPermissions);
    }

    public interface INotify {
        void onNotificationReceived();
    }
}