package org.ekstep.genie.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import org.ekstep.genie.R;
import org.ekstep.genie.activity.BaseActivity;
import org.ekstep.genie.activity.WelcomeActivity;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.ui.landing.LandingActivity;
import org.ekstep.genie.util.LogUtil;
import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created on 12/21/2016.
 *
 * @author anil
 */
public class SplashActivity extends BaseActivity implements SplashContract.View {
    private SplashContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = (SplashPresenter) presenter;
        // Save Genie start event
        mPresenter.saveGenieStartEvent();
        mPresenter.openSplash(getIntent());
        mPresenter.copyProfilesToExternalDirectory();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        mPresenter.openSplash(intent);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        super.onPermissionsGranted(requestCode);
        mPresenter.onPermissionsGranted(requestCode);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> deniedPermissions) {
        super.onPermissionsDenied(requestCode, deniedPermissions);
        mPresenter.onPermissionsDenied(deniedPermissions);
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new SplashPresenter();
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
    public void showSplash() {
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    public void showHomeScreen() {
        Intent intent = new Intent(SplashActivity.this, LandingActivity.class);
        startActivity(intent);
        finish();
        // Activity animation from right to left.
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void showWelcomeScreen() {
        Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
        startActivity(intent);
        finish();
        // Activity animation from right to left.
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void postFilePathIntent(Intent eventObject) {
        EventBus.getDefault().postSticky(eventObject);
    }

    @Override
    public void postDeepLinkIntent(Intent deepLinkIntent) {
        EventBus.getDefault().postSticky(deepLinkIntent);
    }

    @Override
    public void postNotificationIntent(Intent notificationIntent) {
        EventBus.getDefault().postSticky(notificationIntent);
    }

    @Override
    public void postPartnerLaunchIntent(Intent partnerLaunchIntent) {
        EventBus.getDefault().postSticky(partnerLaunchIntent);
    }
}
