package org.ekstep.genie.ui.mylessons;

import android.content.Context;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.model.enums.ContentType;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.PlayerUtil;
import org.ekstep.genie.util.geniesdk.ContentUtil;
import org.ekstep.genieservices.async.ContentService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.ContentFilterCriteria;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.Profile;

import java.util.List;

/**
 * Created by swayangjit on 14/9/17.
 */

public class MyLessonsPresenter implements MyLessonsContract.Presenter {

    private Context mContext;
    private MyLessonsContract.View mMyLessonsView;
    private ContentService mContentService;

    @Override
    public void bindView(BaseView view, Context context) {
        mContext = context;
        mMyLessonsView = (MyLessonsContract.View) view;
        mContentService = CoreApplication.getGenieAsyncService().getContentService();
    }

    @Override
    public void unbindView() {
        mContext = null;
        mMyLessonsView = null;
    }

    @Override
    public void fetchLocalLessons(String lesstionType, final Profile profile, final boolean isNotAnonymousProfile) {
        ContentFilterCriteria.Builder contentFilterCriteria = new ContentFilterCriteria.Builder();
        String contentType[];
        if (lesstionType.equalsIgnoreCase(Constant.CONTENT_TYPE_TEXTBOOK)) {
            contentType = new String[]{ContentType.TEXTBOOK};
        } else {
            contentType = new String[]{ContentType.GAME, ContentType.STORY, ContentType.WORKSHEET, ContentType.COLLECTION};
        }
        contentFilterCriteria.contentTypes(contentType).withContentAccess();
        ContentUtil.applyPartnerFilter(contentFilterCriteria);

        mContentService.getAllLocalContent(contentFilterCriteria.build(), new IResponseHandler<List<Content>>() {
            @Override
            public void onSuccess(GenieResponse<List<Content>> genieResponse) {
                mMyLessonsView.showMyLessonsGrid(profile, isNotAnonymousProfile, genieResponse.getResult());
            }

            @Override
            public void onError(GenieResponse<List<Content>> genieResponse) {

            }
        });
    }

    @Override
    public void startGame(Content content) {
        PlayerUtil.startContent(mContext, content, TelemetryStageId.MY_CONTENT, false, false);
    }
}
