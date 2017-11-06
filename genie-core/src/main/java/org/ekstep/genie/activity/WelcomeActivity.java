package org.ekstep.genie.activity;

import android.os.Bundle;

import org.ekstep.genie.R;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.fragment.LanguageFragment;
import org.ekstep.genie.util.preference.PreferenceUtil;

public class WelcomeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        setFragment(new LanguageFragment(), false);

        PreferenceUtil.setGenieFirstLaunchTime(System.currentTimeMillis());
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return null;
    }

    @Override
    protected String getTAG() {
        return null;
    }

    @Override
    protected BaseView getBaseView() {
        return null;
    }
}

