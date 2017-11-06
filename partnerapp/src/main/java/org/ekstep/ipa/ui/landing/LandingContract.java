package org.ekstep.ipa.ui.landing;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;

/**
 * @author vinayagasundar
 */

public interface LandingContract {

    interface View extends BaseView {
        void showGenieChildFragment();

        void showAddChildFragment();

        void setToolbarTitle(String title);
    }

    interface Presenter extends BasePresenter {
        void start();

        void showAddChild();
    }
}
