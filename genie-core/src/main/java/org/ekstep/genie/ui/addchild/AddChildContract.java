package org.ekstep.genie.ui.addchild;

import android.os.Bundle;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genieservices.commons.bean.Profile;

import java.util.List;
import java.util.Map;

/**
 * Created on 22/12/16.
 *
 * @author swayangjit
 */
public interface AddChildContract {

    interface View extends BaseView {

        void showTitle(int message);

        void showAddChildIcon();

        void hideAddChildIcon();

        void showAddGroupIcon();

        void hideAddGroupIcon();

        void showAvatarFragment(Profile profile);

        void showNickNameFragment(Profile profile, boolean mode);

        void showAgeGenderAndClassFragment(Profile profile);

        void showMediumAndBoardFragment(Profile profile);

        void showWelcomeAboardFragment(Profile profile);

        void showSkip();

        void hideSkip();

        void showNext();

        void hideNext();

        void showBack();

        void hideBack();

        void hideNavigationHeader();

        void hideToggle();

        void showToggle();

        Profile getAvatarData();

        Profile getNickNameData();

        Profile getAgeGenderAndClassData();

        Profile getMediumAndBoardData();

        boolean isValidNickName();

        boolean isBackPressed();

        void finishActivity();

        void waitAndFinishActivity();

        void showAgeNotAvailableError();

        void showGenderNotAvailableError();

        void showClassNotAvailableError();

        void showMediumNotAvailableError();

        void showBoardNotAvailableError();

        void showAddGroupProgress();

        void showAddChildProgress();

        void hideProgress();

        void setProgressBarState(int state);

        void showNextButtonAnimation();
    }

    interface Presenter extends BasePresenter {

        void fetchBundleExtras(Bundle bundle);

        void openAddChildLayout();

        int getLayoutTitle();

        void handleToggleProfileMode();

        void handleNextButtonClick();

        void handleGenieBackButtonClick();

        void handleAvatarClick();

        Profile getDefaultProfile();

        void addProfile(Profile profile);

        void handleUpdateUserProfile(Profile profile);

        boolean isBackPressed();

        boolean isEditMode();

        void handleNextButtonAnimation();

        void generateInteractEvent(String screenName, Profile profile, int flow);

        void generateNextButtonTelemetry(String screenName, Map<String, String> map, List<String> validationErrors);
    }

}
