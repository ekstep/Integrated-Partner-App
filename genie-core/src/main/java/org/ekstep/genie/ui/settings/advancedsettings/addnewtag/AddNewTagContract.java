package org.ekstep.genie.ui.settings.advancedsettings.addnewtag;

import android.content.Context;
import android.os.Bundle;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genieservices.commons.bean.Tag;

/**
 * Created by Sneha on 9/27/2017.
 */

public class AddNewTagContract {

    interface View extends BaseView {

        boolean validateTagData();

        void finishActivity();
    }

    interface Presenter extends BasePresenter {

        void handleTagOperations(Context context, Bundle bundle, Tag tag);
    }
}
