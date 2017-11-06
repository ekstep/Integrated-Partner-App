package org.ekstep.genie.ui.mylessons;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.Profile;

import java.util.List;

/**
 * Created by swayangjit on 14/9/17.
 */

public class MyLessonsContract {

    interface View extends BaseView {

        void showMyLessonsGrid(Profile profile, boolean isNotANonymousProfile, List<Content> contentList);

    }

    interface Presenter extends BasePresenter {

        void fetchLocalLessons(String lesstionType, Profile profile, boolean isNotAnonymousProfile);

        void startGame(Content content);
    }
}
