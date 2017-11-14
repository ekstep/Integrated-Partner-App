package org.ekstep.genie.ui.settings.storagesettings;

import android.content.Context;
import android.os.Environment;

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
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.MoveContentProgress;
import org.ekstep.genieservices.commons.utils.StringUtil;
import org.ekstep.genieservices.utils.DeviceSpec;

import java.io.File;
import java.util.HashMap;
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

    @Override
    public void calculateSdcardStorageValue(Context context) {
        if (FileHandler.isSecondaryStorageAvailable()) {

            long sdcardSpace = FileHandler.getAvailableExternalMemorySize();
            mStorageSettingView.displaySdcardAvailableSpace(Util.humanReadableByteCount(sdcardSpace, true));

            long usedSpace = FileHandler.getTotalExternalMemorySize() - FileHandler.getAvailableExternalMemorySize();
            mStorageSettingView.displaySdcardUsedSpace(Util.humanReadableByteCount(usedSpace, true));

            File file = new File(FileHandler.getExternalSdcardPath(mContext));
            mStorageSettingView.displaySdcardGenieSpace(Util.humanReadableByteCount(FileHandler.folderSize(file), true));
        } else {
            mStorageSettingView.displaySdcardNotAvailable();
        }
    }

    public String getExternalGenieUsedSpace() {
        if (StringUtil.isNullOrEmpty(FileHandler.getExternalSdcardPath(mContext))) {
            Util.showCustomToast(R.string.msg_no_sdcard_found);
        } else {
            File file = new File(FileHandler.getExternalSdcardPath(mContext));
            return Util.humanReadableByteCount(FileHandler.folderSize(file), true);
        }
        return null;
    }

    @Override
    public void calculateMobileStorageValue(Context context) {
        long availableSpace = DeviceSpec.getAvailableExternalMemorySize();
        mStorageSettingView.displayMobileAvailableSpace(Util.humanReadableByteCount(availableSpace, true));

        long usedSpace = DeviceSpec.getTotalInternalMemorySize() - DeviceSpec.getAvailableExternalMemorySize();
        mStorageSettingView.displayMobileUsedSpace(Util.humanReadableByteCount(usedSpace, true));

        mStorageSettingView.displayMobileGenieSpace(getInternalGenieUsedSpace());
    }

    public String getInternalGenieUsedSpace() {
        File file = new File(String.valueOf(mContext.getExternalFilesDir(null)));
        return Util.humanReadableByteCount(FileHandler.folderSize(file), true);
    }

    @Override
    public void setMobileDeviceAsDefault() {
        Map<String, String> map = new HashMap();
        map.put(Constant.DEFAULT_STORAGE, Constant.DEFAULT_STORAGE_MOBILE);
        map.put(Constant.DEFAULT_STORAGE_PATH, FileHandler.getExternalFilesDir(mContext).toString());
        PreferenceUtil.setDefaultStorageOption(map);

        String filepath = FileHandler.getExternalFilesDir(mContext).getPath();

        ContentMoveRequest.Builder contentMoveRequest = new ContentMoveRequest.Builder();
        contentMoveRequest.toFolder(filepath);
        CoreApplication.getGenieAsyncService().getContentService().moveContent(contentMoveRequest.build(), new IResponseHandler<Void>() {
            @Override
            public void onSuccess(GenieResponse<Void> genieResponse) {
                PreferenceUtil.getPreferenceWrapper().putBoolean(PreferenceKey.KEY_SET_EXTERNAL_STORAGE_DEFAULT, false);
                calculateSdcardStorageValue(mContext);
                calculateMobileStorageValue(mContext);
            }

            @Override
            public void onError(GenieResponse<Void> genieResponse) {
            }
        });
    }

    @Override
    public void setExternalDeviceAsDefault() {
        Map<String, String> map = new HashMap();
        map.put(Constant.DEFAULT_STORAGE, Constant.DEFAULT_STORAGE_EXTERNAL);
        map.put(Constant.DEFAULT_STORAGE_PATH, Environment.getExternalStorageDirectory().toString());
        PreferenceUtil.setDefaultStorageOption(map);

        String sdcardPath = FileHandler.getExternalSdcardPath(mContext);

        ContentMoveRequest.Builder contentMoveRequest = new ContentMoveRequest.Builder();
        contentMoveRequest.toFolder(sdcardPath);
        CoreApplication.getGenieAsyncService().getContentService().moveContent(contentMoveRequest.build(), new IResponseHandler<Void>() {
            @Override
            public void onSuccess(GenieResponse<Void> genieResponse) {
                PreferenceUtil.getPreferenceWrapper().putBoolean(PreferenceKey.KEY_SET_EXTERNAL_STORAGE_DEFAULT, true);
                calculateSdcardStorageValue(mContext);
                calculateMobileStorageValue(mContext);
            }

            @Override
            public void onError(GenieResponse<Void> genieResponse) {
            }
        });
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
            boolean isExternalStorage = PreferenceUtil.getPreferenceWrapper().getBoolean(PreferenceKey.KEY_SET_EXTERNAL_STORAGE_DEFAULT, false);
            if (isExternalStorage) {
                mStorageSettingView.showMobileDeviceAsSelected();
            } else {
                mStorageSettingView.showExternalDeviceAsSelected();
            }
            mStorageSettingView.dismissMoveContentDialog();
        }
    }
}
