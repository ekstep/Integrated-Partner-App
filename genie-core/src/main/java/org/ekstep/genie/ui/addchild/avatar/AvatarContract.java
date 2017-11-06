package org.ekstep.genie.ui.addchild.avatar;

import android.os.Bundle;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genieservices.commons.bean.Profile;

/**
 * Created by swayangjit on 22/12/16.
 */

public interface AvatarContract {
    interface View extends BaseView {

        void showAvatarList(AvatarCoverflowAdapter avatarCoverflowAdapter);

        void setSelectedAvatar(int position);

        void adjustUiForTelugu();

        void adjustUiForOthers();

        void showInvisibleLayout();

    }

    interface Presenter extends BasePresenter {

        void fetchBundleExtras(Bundle bundle);

        void openAvatarLayout();

        void handleAvatarSelection(int position);

        void handleOnAvatarClick(int position);

        Profile getProfile();

        void adjustUIAccordingLanguage();

        String fetchAvatarData(int position);

        String fetchAvatarName(int position);

    }
}
