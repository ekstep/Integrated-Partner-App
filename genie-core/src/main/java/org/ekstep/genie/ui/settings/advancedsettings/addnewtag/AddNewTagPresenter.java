package org.ekstep.genie.ui.settings.advancedsettings.addnewtag;

import android.content.Context;
import android.os.Bundle;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.telemetry.EnvironmentId;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.util.Util;
import org.ekstep.genieservices.async.TagService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.Tag;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;

/**
 * Created by Sneha on 9/27/2017.
 */

public class AddNewTagPresenter implements AddNewTagContract.Presenter {
    private TagService tagService;
    private Context mContext;
    private AddNewTagContract.View mAddNewTagView;

    @Override
    public void bindView(BaseView view, Context context) {
        mContext = context;
        mAddNewTagView = (AddNewTagContract.View) view;
        tagService = CoreApplication.getGenieAsyncService().getTagService();
    }

    @Override
    public void unbindView() {
        mContext = null;
        mAddNewTagView = null;
    }

    @Override
    public void handleTagOperations(final Context context, Bundle bundle, Tag tag) {

        if (mAddNewTagView.validateTagData()) {

            if (bundle == null) {
                tagService.setTag(tag, new IResponseHandler<Void>() {
                    @Override
                    public void onSuccess(GenieResponse<Void> genieResponse) {
//                        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.ADD_NEW_TAG, TelemetryAction.ADD_TAG_MANUAL));
                        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildInteractEvent(EnvironmentId.HOME, InteractionType.TOUCH, TelemetryAction.ADD_TAG_MANUAL, TelemetryStageId.ADD_NEW_TAG));
                        Util.showCustomToast(R.string.msg_tag_added_sussessfully);
                        mAddNewTagView.finishActivity();
                    }

                    @Override
                    public void onError(GenieResponse<Void> genieResponse) {

                    }
                });

            } else {
                tagService.updateTag(tag, new IResponseHandler<Void>() {
                    @Override
                    public void onSuccess(GenieResponse<Void> genieResponse) {
                        Util.showCustomToast(R.string.msg_tag_updated_successfully);
                        mAddNewTagView.finishActivity();
                    }

                    @Override
                    public void onError(GenieResponse<Void> genieResponse) {

                    }
                });
            }
        }
    }
}
