package org.ekstep.genie.ui.importarchive.fileexplorer;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;

/**
 * Created by Kannappan on 12/22/2016.
 */
public interface FileExploreContract {

    interface View extends BaseView {
        void onBackButtonPressed();
    }

    interface Presenter extends BasePresenter {

        void onBackButtonPressed();
    }

}
