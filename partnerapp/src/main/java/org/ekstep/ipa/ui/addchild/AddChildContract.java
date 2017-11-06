package org.ekstep.ipa.ui.addchild;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;

/**
 * @author vinayagasundar
 */

public interface AddChildContract {

    interface View extends BaseView {
        void setToolbarTitle(String title);

        void setUpBackNavigation();

        void showChildFilterPage();

        void showSearchChildScreen(String schoolId, String klass);

        void handleBackPress();
    }

    interface Presenter extends BasePresenter {
        void start();

        void showSearchChild(String schoolId, String klass);

        void handleNavBackClick();
    }
}
