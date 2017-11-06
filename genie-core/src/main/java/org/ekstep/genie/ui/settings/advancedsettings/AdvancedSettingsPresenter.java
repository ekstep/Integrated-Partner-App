package org.ekstep.genie.ui.settings.advancedsettings;

import android.content.Context;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.util.Util;
import org.ekstep.genieservices.async.TagService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.Tag;
import org.ekstep.genieservices.commons.utils.CollectionUtil;

import java.util.List;

/**
 * Created by Sneha on 9/17/2017.
 */

public class AdvancedSettingsPresenter implements AdvancedSettingsContract.Presenter {
    private Context mContext;
    private AdvancedSettingsContract.View mAdvancedSettingsView;
    private TagService mTagService;
    private List<Tag> mTagList = null;

    @Override
    public void bindView(BaseView view, Context context) {
        mContext = context;
        mAdvancedSettingsView = (AdvancedSettingsContract.View) view;
        mTagService = CoreApplication.getGenieAsyncService().getTagService();
    }

    @Override
    public void unbindView() {
        mContext = null;
        mAdvancedSettingsView = null;
    }

    @Override
    public void handleDeleteTag(Tag tag) {
        mTagService.deleteTag(tag.getName(), new IResponseHandler<Void>() {
            @Override
            public void onSuccess(GenieResponse<Void> genieResponse) {
                handleGetTags();
            }

            @Override
            public void onError(GenieResponse<Void> genieResponse) {
                Util.processFailure(genieResponse);
            }
        });
    }

    public void handleGetTags() {
        mTagService.getTags(new IResponseHandler<List<Tag>>() {
            @Override
            public void onSuccess(GenieResponse<List<Tag>> genieResponse) {
                mTagList = genieResponse.getResult();
                if (!CollectionUtil.isNullOrEmpty(mTagList)) {
                    mAdvancedSettingsView.hideNoTagsLayout();
                    mAdvancedSettingsView.showTagList(mTagList);
                } else {
                    mAdvancedSettingsView.showNoTagsLayout();
                }
            }

            @Override
            public void onError(GenieResponse<List<Tag>> genieResponse) {

            }
        });
    }

    @Override
    public void handleEditTag(Tag tag) {
        mAdvancedSettingsView.navigateToAddNewTag(tag);
    }

}
