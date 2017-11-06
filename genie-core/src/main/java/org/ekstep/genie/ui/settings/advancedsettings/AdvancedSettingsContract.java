package org.ekstep.genie.ui.settings.advancedsettings;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genieservices.commons.bean.Tag;

import java.util.List;

/**
 * Created by Sneha on 9/17/2017.
 */

public class AdvancedSettingsContract {

    interface View extends BaseView {

        void showNoTagsLayout();

        void hideNoTagsLayout();

        void showTagList(List<Tag> tagList);

        void navigateToAddNewTag(Tag tag);
    }

    interface Presenter extends BasePresenter {

        void handleDeleteTag(Tag tag);

        void handleGetTags();

        void handleEditTag(Tag tag);
    }
}
