package org.ekstep.genie.ui.settings.language;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;

/**
 * Created by Sneha on 9/17/2017.
 */

public class LanguageContract {

    interface View extends BaseView {

        void displaySelectedLanguage();
    }

    interface Presenter extends BasePresenter {

        void handleLanguageChange(int index);
    }
}
