package org.ekstep.genie.ui.addchild.welcomeaboard;

import android.os.Bundle;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;

/**
 * Created by swayangjit on 23/12/16.
 */

public interface WelcomeAboardContract {
    interface View extends BaseView {

        void showSelectedName(String name);

        void showWelcomeAvatar(int avatar);

    }

    interface Presenter extends BasePresenter {

        void openWelcomeLayout();

        void fetchBundleExtras(Bundle bundle);

    }
}
