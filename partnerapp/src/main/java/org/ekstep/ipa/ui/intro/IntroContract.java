package org.ekstep.ipa.ui.intro;

import android.support.v4.view.ViewPager.OnPageChangeListener;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.ipa.model.IntroModel;

import java.util.List;


/**
 * @author vinayagasundar
 */

public interface IntroContract {


    interface View extends BaseView {
        void showSkipButton();

        void showGetStarted();

        void showDriveSyncScreen();

        void setViewPagerAdapter(List<IntroModel> introModelList,
                                 OnPageChangeListener onPageChangeListener);
    }


    interface Presenter extends BasePresenter {
        void start();

        void skipIntro();

        void handleGetStarted();
    }
}
