package org.ekstep.genie.ui.transfer;


import android.content.Context;
import android.content.Intent;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.ui.share.ShareActivity;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.geniesdk.ContentUtil;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.ContentFilterCriteria;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.Profile;
import org.ekstep.genieservices.commons.utils.StringUtil;
import org.ekstep.genieservices.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 12/23/2016.
 *
 * @author Kannappan
 */
public class TransferPresenter implements TransferContract.Presenter {

    private TransferContract.View mTransferView;
    private Context mContext;
    private List<Content> mContentList;
    private List<Profile> mProfileList;

    public TransferPresenter() {

    }

    private void doExportContent() {
        if (mTransferView.isContentItemsSelected()) {
            List<String> identifierList = mTransferView.getContentSelectedItems();
            Map<String, String> values = new HashMap<>();
            if (identifierList.size() == 1) {
                values.put(Constant.SHARE_NAME, identifierList.get(0));
            } else {
                values.put(Constant.SHARE_NAME, "Collection");
            }

            values.put(Constant.SHARE_SCREEN_NAME, TelemetryStageId.SETTINGS_TRANSFER);
            mTransferView.startContentExportIntent(values, identifierList);

        } else {
            mTransferView.showCustomToast(mContext.getString(R.string.msg_transfer_select_content));
        }
    }

    private void doExportProfiles() {
        if (mTransferView.isProfileItemsSelected()) {
            Map<String, String> values = new HashMap<>();
            values.put(Constant.SHARE_SCREEN_NAME, TelemetryStageId.SETTINGS_TRANSFER);
            values.put(Constant.SHARE_NAME, "profile.epar");
            mTransferView.startProfileExport(values);
        } else {
            mTransferView.showCustomToast(mContext.getString(R.string.msg_transfer_select_profile));
        }
    }

    private void doGetAllContent() {
        // TODO: 16/6/17 Add isLiveContentCriteria in getAllLocalContent API
        ContentFilterCriteria.Builder contentFilterCriteria = new ContentFilterCriteria.Builder();
        ContentUtil.applyPartnerFilter(contentFilterCriteria);
        CoreApplication.getGenieAsyncService().getContentService().getAllLocalContent(contentFilterCriteria.build(), new IResponseHandler<List<Content>>() {
            @Override
            public void onSuccess(GenieResponse<List<Content>> genieResponse) {
                if (mTransferView == null) {
                    return;
                }
                mContentList = genieResponse.getResult();
                if (mContentList.size() > 0) {
                    mTransferView.showContentRecyclerView();
                    mTransferView.showContentList(mContentList);
                } else {
                    mTransferView.showCustomToast(mContext.getString(R.string.msg_transfer_no_content));
                    mTransferView.hideContentRecyclerView();
                }
            }

            @Override
            public void onError(GenieResponse<List<Content>> genieResponse) {

            }
        });
    }

    private void doGetAllProfiles() {
        CoreApplication.getGenieAsyncService().getUserService().getAllUserProfile(new IResponseHandler<List<Profile>>() {
            @Override
            public void onSuccess(GenieResponse<List<Profile>> genieResponse) {
                mProfileList = genieResponse.getResult();
                if (mProfileList.size() > 0) {
                    mTransferView.showProfileRecyclerView();
                    mTransferView.updateProfileAdapter(mProfileList);
                } else {
                    mTransferView.hideProfileRecyclerView();
                    mTransferView.showCustomToast(mContext.getString(R.string.error_transfer_no_kids));
                }
            }

            @Override
            public void onError(GenieResponse<List<Profile>> genieResponse) {

            }
        });
    }

    @Override
    public void onItemClicked(int index) {

    }

    @Override
    public void export() {
        if (mTransferView.isTelemetryTextChecked()) {
            ShareActivity.startTelemetryExportIntent(mContext);
        } else if (mTransferView.isContentChecked()) {
            doExportContent();
        } else if (mTransferView.isProfileChecked()) {
            doExportProfiles();
        }
    }

    @Override
    public void selectTelemetry() {
        mTransferView.checkTelemetryText(true);
        mTransferView.checkContentText(false);
        mTransferView.checkProfileText(false);
        mTransferView.hideContentRecyclerView();
        mTransferView.hideProfileRecyclerView();
    }

    @Override
    public void selectContent() {
        doGetAllContent();

        mTransferView.hideProfileRecyclerView();
        mTransferView.checkContentText(true);
        mTransferView.checkTelemetryText(false);
        mTransferView.checkProfileText(false);
    }

    @Override
    public void selectProfile() {
        doGetAllProfiles();
        mTransferView.hideContentRecyclerView();
        mTransferView.checkContentText(false);
        mTransferView.checkTelemetryText(false);
        mTransferView.checkProfileText(true);
    }

    @Override
    public void onReceiveBroadCast(Context context, Intent intent) {
        if (intent != null && intent.getAction() != null) {
            if (intent.getAction().equalsIgnoreCase(Constant.INTENT_ACTION_REFRESH_PROFILE_ADAPTER)) {
                mTransferView.clearProfileAdapter();
            }
        }
    }

    @Override
    public void processItemClick(int position) {
        if (mTransferView.isProfileChecked()) {
            mTransferView.profileToggleSelected(mProfileList.get(position).getUid());
        } else {
            mTransferView.contentToggleSelected(mContentList.get(position).getIdentifier());
        }
    }

    @Override
    public void manageContentExportSuccess(String exportSuccess) {
        if (!StringUtil.isNullOrEmpty(exportSuccess) && exportSuccess.equalsIgnoreCase(Constant.EventKey.EVENT_KEY_EXPORT_CONTENT)) {
            mTransferView.clearContentAdapter();
            EventBus.removeStickyEvent(Constant.EventKey.EVENT_KEY_EXPORT_CONTENT);
        }
    }


    @Override
    public void bindView(BaseView view, Context context) {
        mTransferView = (TransferContract.View) view;
        mContext = context;
    }

    @Override
    public void unbindView() {
        mTransferView = null;
        mContext = null;
    }
}
