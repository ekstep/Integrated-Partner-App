package org.ekstep.ipa.ui.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

import org.ekstep.genie.hooks.IStartUp;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.ipa.R;
import org.ekstep.ipa.ui.intro.IntroActivity;
import org.ekstep.ipa.ui.landing.LandingActivity;
import org.ekstep.ipa.util.Utils;

/**
 * @author vinayagasundar
 */

public class PartnerStartUp implements IStartUp {

    private static final int SPLASH_TIME_OUT = 1000;

    private Activity mActivity;

    @Override
    public void init(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void handleNotification(Intent intent) {
        nextScreen(mActivity);
    }

    @Override
    public void handleLocalDeepLink(Intent intent) {
        nextScreen(mActivity);
    }

    @Override
    public void handleServerDeepLink(Intent intent) {
        nextScreen(mActivity);
    }

    @Override
    public String[] requiredPermissions() {
        return new String[0];
    }

    @Override
    public void nextScreen(Activity activity) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Set the selected locale.

                Utils.registerPartnerIfNeed();

                if (PreferenceUtil.isFirstTime().equalsIgnoreCase("false")) {
                    Intent intent = new Intent(mActivity, LandingActivity.class);
                    mActivity.startActivity(intent);
                    mActivity.finish();
                    // Activity animation from right to left.
                    mActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {    // When Genie launch for the first time after installation.
                    Intent intent = new Intent(mActivity, IntroActivity.class);
                    mActivity.startActivity(intent);
                    mActivity.finish();
                    // Activity animation from right to left.
                    mActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        }, SPLASH_TIME_OUT);
    }
}
