package org.ekstep.genie.ui.settings.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import org.ekstep.genie.R;
import org.ekstep.genie.activity.BaseActivity;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.fragment.BaseFragment;
import org.ekstep.genie.util.Constant;

/**
 * Created by Sneha on 9/19/2017.
 */

public class AboutActivity extends BaseActivity {
    private ImageView mBackBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        BaseFragment fragment = null;
        if (getIntent().getExtras().containsKey(Constant.EXTRA_LINK_ABOUT_US)) {
            fragment = new AboutUsFragment();
        } else if (getIntent().getExtras().containsKey(Constant.EXTRA_LINK_TERMS_CONDITION)) {
            fragment = new TermsnConditionFragment();
        } else {
            fragment = new PrivacyPolicyFragment();
        }
        setFragment(fragment);

        mBackBtn = (ImageView) findViewById(R.id.btn_back);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

    @Override
    public void onBackPressed() {
        finish();
    }
}
