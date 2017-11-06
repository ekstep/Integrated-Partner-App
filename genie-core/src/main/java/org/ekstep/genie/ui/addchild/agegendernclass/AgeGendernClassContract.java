package org.ekstep.genie.ui.addchild.agegendernclass;

import android.os.Bundle;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genieservices.commons.bean.Profile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by swayangjit on 22/12/16.
 */

public interface AgeGendernClassContract {

    interface View extends BaseView {

        void showAgeListPopup(List<String> mediumList, android.view.View view);

        String getAge();

        void setAge(String age);

        String getStandard();

        void setStandard(String standard);

        String getDay();

        String getMonth();

        void showGender(int gender);

        void showEmptyAgeAnimation();

        void showEmptyGenderAnimation();

        void showEmptyClassAnimation();

        void showNextButtonAnimation();

        void showClassListPopup(List<String> classList, android.view.View view);
    }

    interface Presenter extends BasePresenter {

        void fetchBundleExtras(Bundle bundle);

        void openAgeLayout();

        Profile getAgeGendernClass();

        void handleAgeClick(android.view.View view);

        void handleAgeItemClick(int position);

        void toggleGender(int gender);

        void handleAgeNotAvailable();

        void handleGenderNotAvailable();

        void handleClassNotAvailable();

        boolean isClassValid(int standard);

        void handleClassClick(android.view.View view);

        void handleClassItemClick(int position);
    }
}
