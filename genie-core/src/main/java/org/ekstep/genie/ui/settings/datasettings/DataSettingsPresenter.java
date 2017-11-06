package org.ekstep.genie.ui.settings.datasettings;

import android.content.Context;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.util.ShowProgressDialog;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.geniesdk.SyncServiceUtil;
import org.ekstep.genieservices.async.TelemetryService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.SyncStat;
import org.ekstep.genieservices.commons.bean.TelemetryStat;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.ekstep.genieservices.commons.utils.DateUtil;

/**
 * Created by Sneha on 9/16/2017.
 */

public class DataSettingsPresenter implements DataSettingsContract.Presenter {

    private Context mContext;
    private DataSettingsContract.View mDataSettingsView;

    @Override
    public void bindView(BaseView view, Context context) {
        mContext = context;
        mDataSettingsView = (DataSettingsContract.View) view;
    }

    @Override
    public void unbindView() {
        mContext = null;
        mDataSettingsView = null;
    }

    public void handleSyncNow() {
        try {
            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.SETTINGS_DATA_USAGE, TelemetryAction.MANUAL_SYNC_INITIATED));

            ShowProgressDialog.showProgressDialog(mContext, mContext.getString(R.string.msg_syncing));

            SyncServiceUtil.getSyncService().sync(new IResponseHandler<SyncStat>() {
                @Override
                public void onSuccess(GenieResponse<SyncStat> genieResponse) {
//                    LogUtil.i(getTAG(), "manualSync onSuccess.");
                    if (!mDataSettingsView.isAdded()) {
                        return;
                    }
                    SyncStat syncStat = genieResponse.getResult();
                    TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.OTHER, TelemetryStageId.SETTINGS_DATA_USAGE, TelemetryAction.MANUAL_SYNC_SUCCESS, SyncServiceUtil.getFileSizeMap(syncStat)));

                    ShowProgressDialog.dismissDialog();

                    // Update the last sync date and time
                    mDataSettingsView.displayLastSyncTime(getFormattedTime(syncStat.getSyncTime()));

                    Util.processSuccess(genieResponse);

                    mDataSettingsView.showSyncDialog();
                }

                @Override
                public void onError(GenieResponse<SyncStat> genieResponse) {
                    mDataSettingsView.showSyncFailedMsg(genieResponse.getError());
                }
            });

        } catch (Throwable e) {
            mDataSettingsView.showInternetConnectivityError();
        }
    }

    public void getLastSyncTime() {
        final TelemetryService telemetryService = CoreApplication.getGenieSdkInstance().getAsyncService().getTelemetryService();
        telemetryService.getTelemetryStat(new IResponseHandler<TelemetryStat>() {
            @Override
            public void onSuccess(GenieResponse<TelemetryStat> genieResponse) {
                TelemetryStat telemetryStat = genieResponse.getResult();
                if (telemetryStat != null) {
                    mDataSettingsView.displayLastSyncTime(getFormattedTime(telemetryStat.getLastSyncTime()));
                }
            }

            @Override
            public void onError(GenieResponse<TelemetryStat> genieResponse) {

            }
        });
    }

    public String getFormattedTime(long timeInMillis) {
        String syncTime;
        if (timeInMillis == 0) {
            syncTime = "NEVER";
        } else {
            syncTime = DateUtil.format(timeInMillis, DateUtil.DATE_TIME_AM_PM_FORMAT);
        }
        return syncTime;
    }
}
