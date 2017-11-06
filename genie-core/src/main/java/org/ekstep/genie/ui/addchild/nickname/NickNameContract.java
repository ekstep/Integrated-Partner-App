package org.ekstep.genie.ui.addchild.nickname;

import android.os.Bundle;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genieservices.commons.bean.Profile;

/**
 * Created by swayangjit on 22/12/16.
 */

public interface NickNameContract {
    interface View extends BaseView {

        void setRandomName(String name);

        String getNickName();

        void showBlankNickNameMessage();

        void setName(String name);

        void adjustUiForTelugu();

        void adjustUiForOthers();
    }

    interface Presenter extends BasePresenter {

        void fetchBundleExtras(Bundle bundle);

        void openNickNameLayout();

        boolean isValidNickName();

        void adjustUIAccordingLanguage();

        Profile getName();

        void handleNavigationToAgeClassGenderFragment(boolean isRandomNameChosen);

        void toggleMode();
    }
}
