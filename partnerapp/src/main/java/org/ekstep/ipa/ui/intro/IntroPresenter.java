package org.ekstep.ipa.ui.intro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;

import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.ipa.model.IntroModel;
import org.ekstep.ipa.ui.landing.LandingActivity;
import org.ekstep.ipa.util.preference.PartnerPrefUtil;


import java.util.ArrayList;
import java.util.List;

/**
 * @author vinayagasundar
 */

public class IntroPresenter implements IntroContract.Presenter,
        ViewPager.OnPageChangeListener {


    private IntroContract.View mIntroView;

    private Activity mActivity;


    public IntroPresenter() {
    }

    @Override
    public void start() {
        List<IntroModel> introModels = getIntroModelList();

        mIntroView.setViewPagerAdapter(introModels, this);
        mIntroView.showSkipButton();

        if (PreferenceUtil.isFirstTime().equalsIgnoreCase("true")) {
            PreferenceUtil.setFirstTime("false");
//            TelemetryBuilder.buildGEInteract("PARTNER_FIRST_LAUNCH");
        }
    }

    @Override
    public void skipIntro() {
        moveToPartnerHomeScreen();
    }

    @Override
    public void handleGetStarted() {
        moveToPartnerHomeScreen();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if ((getIntroModelList().size() - 1) == position) {
            mIntroView.showGetStarted();
        } else {
            mIntroView.showSkipButton();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private List<IntroModel> getIntroModelList() {
        List<IntroModel> introModelList = new ArrayList<>();
        String desc = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod " +
                "tempor incididunt ut labore et dolore magna aliqua.";
        for (int i = 0; i < 3; i++) {
            IntroModel introModel = new IntroModel(org.ekstep.genie.R.drawable.avatar_normal_1, "Title", desc);
            introModelList.add(introModel);
        }
        return introModelList;
    }


    private void moveToPartnerHomeScreen() {
        if (PartnerPrefUtil.getPartnerDataSynced() < 0) {
            mIntroView.showDriveSyncScreen();
            return;
        }

        Intent homeIntent = new Intent(mActivity, LandingActivity.class);
        mActivity.startActivity(homeIntent);
        mActivity.finish();
    }

    @Override
    public void bindView(BaseView view, Context context) {
        mIntroView = (IntroContract.View) view;
        mActivity = (Activity) context;
    }

    @Override
    public void unbindView() {
        mIntroView = null;
        mActivity = null;
    }
}
