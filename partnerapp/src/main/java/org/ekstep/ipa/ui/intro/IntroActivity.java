package org.ekstep.ipa.ui.intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.Button;

import org.ekstep.genie.activity.BaseActivity;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.ipa.R;
import org.ekstep.ipa.model.IntroModel;
import org.ekstep.ipa.ui.drive.DriveActivity;

import java.util.List;


/**
 * @author vinayagasundar
 */

public class IntroActivity extends BaseActivity
        implements IntroContract.View, View.OnClickListener {

    private IntroContract.Presenter mPresenter;

    private ViewPager mIntroViewPager;

    private Button mLaunchBtn;

    private boolean mIsGetStarted = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        mPresenter = (IntroContract.Presenter) presenter;


        mIntroViewPager = (ViewPager) findViewById(R.id.intro_view_pager);
        mLaunchBtn = (Button) findViewById(R.id.launch_button);
        if (mLaunchBtn != null) {
            mLaunchBtn.setOnClickListener(this);
        }

        mPresenter.start();
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new IntroPresenter();
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
    public void showSkipButton() {
        mIsGetStarted = false;
        mLaunchBtn.setText(R.string.btn_skip);
    }

    @Override
    public void showGetStarted() {
        mIsGetStarted = true;
        mLaunchBtn.setText(R.string.btn_get_started);
    }

    @Override
    public void showDriveSyncScreen() {
        Intent intent = new Intent(this, DriveActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void setViewPagerAdapter(List<IntroModel> introModelList, OnPageChangeListener onPageChangeListener) {
        mIntroViewPager.removeOnPageChangeListener(onPageChangeListener);

        IntroAdapter introAdapter = new IntroAdapter(this, introModelList);
        mIntroViewPager.setAdapter(introAdapter);
        mIntroViewPager.addOnPageChangeListener(onPageChangeListener);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.launch_button) {
            if (mIsGetStarted) {
                mPresenter.handleGetStarted();
            } else {
                mPresenter.skipIntro();
            }
        }
    }
}
