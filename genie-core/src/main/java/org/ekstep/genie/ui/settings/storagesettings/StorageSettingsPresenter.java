package org.ekstep.genie.ui.settings.storagesettings;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.FileHandler;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.preference.PreferenceKey;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.ContentMoveRequest;
import org.ekstep.genieservices.commons.bean.ContentSpaceUsageSummaryRequest;
import org.ekstep.genieservices.commons.bean.ContentSpaceUsageSummaryResponse;
import org.ekstep.genieservices.commons.bean.ContentSwitchRequest;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.MoveContentProgress;
import org.ekstep.genieservices.commons.bean.MoveContentResponse;
import org.ekstep.genieservices.commons.bean.SwitchContentResponse;
import org.ekstep.genieservices.commons.bean.enums.ExistingContentAction;
import org.ekstep.genieservices.utils.DeviceSpec;
import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sneha on 9/17/2017.
 */

public class StorageSettingsPresenter implements StorageSettingsContract.Presenter {
    private Context mContext;
    private StorageSettingsContract.View mStorageSettingView;


    @Override
    public void bindView(BaseView view, Context context) {
        mContext = context;
        mStorageSettingView = (StorageSettingsContract.View) view;
    }

    @Override
    public void unbindView() {
        mContext = null;
        mStorageSettingView = null;
    }

    public String getExternalGenieUsedSpace() {
        File file = new File(FileHandler.getExternalSdcardPath(mContext));
        return Util.humanReadableByteCount(FileHandler.folderSize(file), true);
    }

    @Override
    public void calculateMobileNSdcardStorage(Context context) {
        mStorageSettingView.showLoaders();
//        new CalculateMobileSpaceAsync().execute();

        getGenieContentTotalSizeOnDevice();
    }

    private void getGenieContentTotalSizeOnDevice() {
        final List<String> pathsList = new ArrayList<>();
        pathsList.add(FileHandler.getExternalFilesDir(mContext).toString());

        //check if secondary storage is available
        if (FileHandler.isSecondaryStorageAvailable()) {
            pathsList.add(FileHandler.getExternalSdcardPath(mContext));
        }

        ContentSpaceUsageSummaryRequest.Builder contentSpaceUsageSummaryRequest = new ContentSpaceUsageSummaryRequest.Builder();
        contentSpaceUsageSummaryRequest.paths(pathsList);

        CoreApplication.getGenieAsyncService().getContentService().getContentSpaceUsageSummary(contentSpaceUsageSummaryRequest.build(), new IResponseHandler<List<ContentSpaceUsageSummaryResponse>>() {
            @Override
            public void onSuccess(GenieResponse<List<ContentSpaceUsageSummaryResponse>> genieResponse) {
                mStorageSettingView.hideLoaders();

                long availableMobileSpace;
                long usedMobileSpace;
                String genieMobileSpace;
                long availableSdcardSpace = 0;
                long usedSdcardSpace = 0;
                String genieSdcardSpace = null;

                // mobile storage
                availableMobileSpace = DeviceSpec.getAvailableInternalMemorySize();
                usedMobileSpace = DeviceSpec.getTotalInternalMemorySize() - DeviceSpec.getAvailableInternalMemorySize();

                //sdcard storage
                if (FileHandler.isSecondaryStorageAvailable()) {
                    availableSdcardSpace = FileHandler.getAvailableExternalMemorySize();
                    usedSdcardSpace = FileHandler.getTotalExternalMemorySize() - FileHandler.getAvailableExternalMemorySize();
                }

                List<ContentSpaceUsageSummaryResponse> responses = genieResponse.getResult();
                for (ContentSpaceUsageSummaryResponse contentSpaceUsageSummaryResponse : responses) {
                    Log.e("TotalSizeOnDevice", "Path & Size - " + contentSpaceUsageSummaryResponse.getPath() + " & " + contentSpaceUsageSummaryResponse.getSizeOnDevice());

                    if (contentSpaceUsageSummaryResponse.getPath().equalsIgnoreCase(pathsList.get(0))) {
                        genieMobileSpace = Util.humanReadableByteCount(contentSpaceUsageSummaryResponse.getSizeOnDevice(), true);
                        mStorageSettingView.displayMobileAvailableSpace(Util.humanReadableByteCount(availableMobileSpace, true));
                        mStorageSettingView.displayMobileUsedSpace(Util.humanReadableByteCount(usedMobileSpace, true));
                        mStorageSettingView.displayMobileGenieSpace(genieMobileSpace);
                    } else if (FileHandler.isSecondaryStorageAvailable()) {
                        if (FileHandler.getExternalSdcardPath(mContext) != null) {
                            genieSdcardSpace = Util.humanReadableByteCount(contentSpaceUsageSummaryResponse.getSizeOnDevice(), true);
                        }

                        mStorageSettingView.displaySdcardAvailableSpace(Util.humanReadableByteCount(availableSdcardSpace, true));
                        mStorageSettingView.displaySdcardUsedSpace(Util.humanReadableByteCount(usedSdcardSpace, true));
                        mStorageSettingView.displaySdcardGenieSpace(genieSdcardSpace);
                    } else {
                        mStorageSettingView.displaySdcardNotAvailable();
                    }
                }
            }

            @Override
            public void onError(GenieResponse<List<ContentSpaceUsageSummaryResponse>> genieResponse) {

            }
        });

    }

    public String getInternalGenieUsedSpace() {
        File file = new File(String.valueOf(mContext.getExternalFilesDir(null)));
        return Util.humanReadableByteCount(FileHandler.folderSize(file), true);
    }

    @Override
    public void setMobileDeviceAsDefault(boolean showReplace) {
        String filepath = FileHandler.getExternalFilesDir(mContext).getPath();

        mStorageSettingView.showMoveReplaceAndDontMoveDialog(showReplace, filepath, false);
    }

    @Override
    public void setExternalDeviceAsDefault(boolean showReplace) {
        String sdcardPath = FileHandler.getExternalSdcardPath(mContext);

        mStorageSettingView.showMoveReplaceAndDontMoveDialog(showReplace, sdcardPath, true);
    }

    @Override
    public void switchSource(String path, final boolean sdCardAsDefault) {
        ContentSwitchRequest.Builder contentSwitchRequest = new ContentSwitchRequest.Builder();
        contentSwitchRequest.toFolder(path);
        CoreApplication.getGenieAsyncService().getContentService().switchContent(contentSwitchRequest.build(), new IResponseHandler<List<SwitchContentResponse>>() {
            @Override
            public void onSuccess(GenieResponse<List<SwitchContentResponse>> genieResponse) {
                Log.e("setExternalDevice", "onSuccess - " + genieResponse.getMessage());
                if (sdCardAsDefault) {
                    setSdCardAsDefault();
                    mStorageSettingView.showExternalDeviceAsSelected();
                } else {
                    setDeviceAsDefault();
                    mStorageSettingView.showMobileDeviceAsSelected();
                }

                EventBus.getDefault().postSticky(Constant.EventKey.EVENT_KEY_SWITCH_SOURCE);
            }

            @Override
            public void onError(GenieResponse<List<SwitchContentResponse>> genieResponse) {
            }
        });
    }

    @Override
    public void mergeContents(ExistingContentAction existingContentAction, final String path, final boolean sdCardAsDefault) {
        ContentMoveRequest.Builder contentMoveRequest = new ContentMoveRequest.Builder();
        contentMoveRequest.toFolder(path);
        contentMoveRequest.actionForDuplicateContentFound(existingContentAction);
        CoreApplication.getGenieAsyncService().getContentService().moveContent(contentMoveRequest.build(), new IResponseHandler<List<MoveContentResponse>>() {
            @Override
            public void onSuccess(GenieResponse<List<MoveContentResponse>> genieResponse) {
                if (sdCardAsDefault) {
                    setSdCardAsDefault();
                    mStorageSettingView.showExternalDeviceAsSelected();
                } else {
                    setDeviceAsDefault();
                    mStorageSettingView.showMobileDeviceAsSelected();
                }

                EventBus.getDefault().postSticky(Constant.EventKey.EVENT_KEY_SWITCH_SOURCE);
            }

            @Override
            public void onError(GenieResponse<List<MoveContentResponse>> genieResponse) {
                Log.e("StorageSettingPresenter", "onError: " + genieResponse);
                mStorageSettingView.showMoveReplaceAndDontMoveDialog(true, path, sdCardAsDefault);
            }
        });
    }

    @Override
    public void deleteAndMoveContents(ExistingContentAction existingContentAction, final String path, final boolean sdCardAsDefault) {
        ContentMoveRequest.Builder contentMoveRequest = new ContentMoveRequest.Builder();
        contentMoveRequest.toFolder(path);
        contentMoveRequest.actionForDuplicateContentFound(existingContentAction);
        contentMoveRequest.deleteDestination();
        CoreApplication.getGenieAsyncService().getContentService().moveContent(contentMoveRequest.build(), new IResponseHandler<List<MoveContentResponse>>() {
            @Override
            public void onSuccess(GenieResponse<List<MoveContentResponse>> genieResponse) {
                if (sdCardAsDefault) {
                    setSdCardAsDefault();
                    mStorageSettingView.showExternalDeviceAsSelected();
                } else {
                    setDeviceAsDefault();
                    mStorageSettingView.showExternalDeviceAsSelected();
                }

                EventBus.getDefault().postSticky(Constant.EventKey.EVENT_KEY_SWITCH_SOURCE);
            }

            @Override
            public void onError(GenieResponse<List<MoveContentResponse>> genieResponse) {
                Log.e("StorageSettingPresenter", "onError: " + genieResponse);
                mStorageSettingView.showMoveReplaceAndDontMoveDialog(true, path, sdCardAsDefault);
            }
        });
    }

    private void setDeviceAsDefault() {
        Map<String, String> map = new HashMap();
        map.put(Constant.DEFAULT_STORAGE, Constant.DEFAULT_STORAGE_MOBILE);
        map.put(Constant.DEFAULT_STORAGE_PATH, FileHandler.getExternalFilesDir(mContext).toString());
        PreferenceUtil.setDefaultStorageOption(map);

        PreferenceUtil.getPreferenceWrapper().putBoolean(PreferenceKey.KEY_SET_EXTERNAL_STORAGE_DEFAULT, false);
//        calculateSdcardStorageValue(mContext);
//        calculateMobileStorageValue(mContext);
        calculateMobileNSdcardStorage(mContext);
    }

    private void setSdCardAsDefault() {
        Map<String, String> map = new HashMap();
        map.put(Constant.DEFAULT_STORAGE, Constant.DEFAULT_STORAGE_EXTERNAL);
        map.put(Constant.DEFAULT_STORAGE_PATH, Environment.getExternalStorageDirectory().toString());
        PreferenceUtil.setDefaultStorageOption(map);

        PreferenceUtil.getPreferenceWrapper().putBoolean(PreferenceKey.KEY_SET_EXTERNAL_STORAGE_DEFAULT, true);
//        calculateSdcardStorageValue(mContext);
//        calculateMobileStorageValue(mContext);
        calculateMobileNSdcardStorage(mContext);
    }

    @Override
    public int selectAptDefaultStorageOption() {
        Map<String, String> storageOption = PreferenceUtil.getDefaultStorageOption();
        int selectedRadioButtonId = 0;
        if (storageOption != null && storageOption.get(Constant.DEFAULT_STORAGE).equalsIgnoreCase(Constant.DEFAULT_STORAGE_MOBILE)) {
            mStorageSettingView.showMobileDeviceAsSelected();
            selectedRadioButtonId = R.id.mobile_btn;
        } else if (storageOption != null && storageOption.get(Constant.DEFAULT_STORAGE).equalsIgnoreCase(Constant.DEFAULT_STORAGE_EXTERNAL)) {
            mStorageSettingView.showExternalDeviceAsSelected();
            selectedRadioButtonId = R.id.sdcard_btn;
        }
        return selectedRadioButtonId;
    }

    @Override
    public void checkExternalStorageAvaibility() {
        if (!FileHandler.isSecondaryStorageAvailable()) {
            mStorageSettingView.disableExternalDevice();
        }
    }

    public void handleOnMoveContentProgress(MoveContentProgress moveContentProgress) {
        if (moveContentProgress.getCurrentCount() == 0) {
            mStorageSettingView.showMoveContentProgressDialog(moveContentProgress);
        }

        if (moveContentProgress.getCurrentCount() == moveContentProgress.getTotalCount()) {
            setDefaultStorage();
            mStorageSettingView.dismissMoveContentDialog();
        }
    }

    private void setDefaultStorage() {
        boolean isExternalStorage = PreferenceUtil.getPreferenceWrapper().getBoolean(PreferenceKey.KEY_SET_EXTERNAL_STORAGE_DEFAULT, false);
        if (isExternalStorage) {
            mStorageSettingView.showMobileDeviceAsSelected();
        } else {
            mStorageSettingView.showExternalDeviceAsSelected();
        }
    }

    private class CalculateMobileSpaceAsync extends AsyncTask {
        private long availableMobileSpace;
        private long usedMobileSpace;
        private String genieMobileSpace;
        private long availableSdcardSpace;
        private long usedSdcardSpace;
        private String genieSdcardSpace;

        @Override
        protected void onPreExecute() {
            mStorageSettingView.showLoaders();
        }


        @Override
        protected Object doInBackground(Object[] objects) {
            // mobile storage
            availableMobileSpace = DeviceSpec.getAvailableInternalMemorySize();
            usedMobileSpace = DeviceSpec.getTotalInternalMemorySize() - DeviceSpec.getAvailableInternalMemorySize();
            genieMobileSpace = Util.humanReadableByteCount(FileHandler.
                    folderSize(new File(String.valueOf(mContext.getExternalFilesDir(null)))), true);

            //sdcard storage
            if (FileHandler.isSecondaryStorageAvailable()) {
                availableSdcardSpace = FileHandler.getAvailableExternalMemorySize();
                usedSdcardSpace = FileHandler.getTotalExternalMemorySize() - FileHandler.getAvailableExternalMemorySize();
                if (FileHandler.getExternalSdcardPath(mContext) != null) {
                    genieSdcardSpace = Util.humanReadableByteCount(FileHandler.folderSize(new File(FileHandler.getExternalSdcardPath(mContext))), true);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            mStorageSettingView.hideLoaders();

            // mobile
            mStorageSettingView.displayMobileAvailableSpace(Util.humanReadableByteCount(availableMobileSpace, true));
            mStorageSettingView.displayMobileUsedSpace(Util.humanReadableByteCount(usedMobileSpace, true));
            mStorageSettingView.displayMobileGenieSpace(genieMobileSpace);

            // sd card
            if (FileHandler.isSecondaryStorageAvailable()) {
                mStorageSettingView.displaySdcardAvailableSpace(Util.humanReadableByteCount(availableSdcardSpace, true));
                mStorageSettingView.displaySdcardUsedSpace(Util.humanReadableByteCount(usedSdcardSpace, true));
                mStorageSettingView.displaySdcardGenieSpace(genieSdcardSpace);
            } else {
                mStorageSettingView.displaySdcardNotAvailable();
            }
        }
    }
}
