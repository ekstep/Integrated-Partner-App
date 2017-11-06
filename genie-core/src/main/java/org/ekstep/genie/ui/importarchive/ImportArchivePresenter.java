package org.ekstep.genie.ui.importarchive;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;

import org.ekstep.genie.R;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.model.MountPoint;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.MountPointUtils;
import org.ekstep.genie.util.geniesdk.ImportExportUtil;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.ekstep.genieservices.commons.utils.CollectionUtil;
import org.ekstep.genieservices.eventbus.EventBus;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created on 12/22/2016.
 *
 * @author Kannappan
 */
public class ImportArchivePresenter implements ImportArchiveContract.Presenter {

    private static final String TAG = ImportArchivePresenter.class.getSimpleName();

    private static final String BLUETOOTH_FOLDER = "/bluetooth";
    private static final String DOWNLOADS_FOLDER = "/Download";
    private static final String SHAREIT_FOLDER = "/SHAREit";
    private static final String XENDER_FOLDER = "/Xender";

    private Context mContext;
    private boolean mIsProfile = false;
    private ImportArchiveContract.View mImportArchiveView;
    private List<File> mFileList = new ArrayList<>();

    private FilenameFilter ecarFilter = new FilenameFilter() {
        public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(Constant.FILE_EXTENSION_ECAR);
        }
    };

    private FilenameFilter profileFilter = new FilenameFilter() {
        public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(Constant.FILE_EXTENSION_EPAR);
        }
    };

    public ImportArchivePresenter() {
    }

    private List<File> showImportEcarLayout() {
        List<MountPoint> mountPointList = MountPointUtils.getMountPoints();
        for (MountPoint mountpoint : mountPointList) {
            if (mountpoint.getStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
                if (mountpoint.getStorageType().equals(MountPoint.StorageType.INTERNAL)) {
                    filterFiles(mountpoint.getMountPath());
                    checkFolders(Environment.getExternalStorageDirectory().toString());

                } else if (mountpoint.getStorageType().equals(MountPoint.StorageType.EXTERNAL)) {
                    filterFiles(mountpoint.getMountPath());
                    checkFolders(mountpoint.getMountPath());
                }
            }
        }

        Collections.sort(mFileList, new Comparator<File>() {
            public int compare(File o1, File o2) {

                if (o1.lastModified() > o2.lastModified()) {
                    return -1;
                } else if (o1.lastModified() < o2.lastModified()) {
                    return 1;
                } else {
                    return 0;
                }
            }

        });

        return mFileList;
    }

    @Override
    public void setHeaderTitle() {
        mImportArchiveView.setHeaderText(mIsProfile ? R.string.title_children_import_profile : R.string.title_downloads_import_content);
    }

    @Override
    public void handleBackClick() {
        mImportArchiveView.finishActivity();
    }

    @Override
    public void handleFileExplorerClick() {
//        mImportArchiveView.showFileExplorerActivity();
    }

    @Override
    public boolean isProfile() {
        return mIsProfile;
    }

    @Override
    public void fetchBundleExtras(Bundle bundle) {
        if (bundle != null) {
            mIsProfile = bundle.getBoolean(Constant.IS_PROFILE);
        }
    }

    @Override
    public void importFile(String filePath) {
        mImportArchiveView.showProgressDialog(mContext.getResources().getString(R.string.msg_importing));
        if (mIsProfile) {
            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.IMPORT_PROFILE, TelemetryAction.PROFILE_IMPORT_INITIATE, new HashMap<String, Object>()));
            ImportExportUtil.importProfile(filePath);
        } else {
            ImportExportUtil.importContent(mContext, filePath, new ImportExportUtil.IImport() {
                @Override
                public void onImportSuccess() {
                    EventBus.postStickyEvent(Constant.EventKey.EVENT_KEY_IMPORT_LOCAL_CONTENT);
                }

                @Override
                public void onImportFailure() {
                }

                @Override
                public void onOutDatedEcarFound() {
                }
            });
        }
    }

    @Override
    public void fetchImportableFiles() {
        new ShowImportFileTask().execute();
    }

    private List<File> loadFiles(File[] files) {
        List<File> fileList = new ArrayList<>();
        if (files != null) {
            Collections.addAll(fileList, files);
        }

        return fileList;
    }

    private void filterFiles(String filePath) {
        File[] bluetoothFileList;
        File directory = new File(filePath);
        if (directory.exists()) {
            if (mIsProfile) {
                bluetoothFileList = directory.listFiles(profileFilter);
            } else {
                bluetoothFileList = directory.listFiles(ecarFilter);
            }

            if (bluetoothFileList != null) {
                mFileList.addAll(loadFiles(bluetoothFileList));
            }
        }
    }

    private void checkFolders(String path) {
        if (new File(path + DOWNLOADS_FOLDER).exists()) {
            filterFiles(path + DOWNLOADS_FOLDER);
        }

        if (new File(path + BLUETOOTH_FOLDER).exists()) {
            filterFiles(path + BLUETOOTH_FOLDER);
        }

        if (new File(path + SHAREIT_FOLDER).exists()) {
            if (new File(path + SHAREIT_FOLDER + "/files").exists()) {
                filterFiles(path + SHAREIT_FOLDER + "/files");
            }
        }

        if (new File(path + XENDER_FOLDER).exists()) {
            if (new File(path + XENDER_FOLDER + "/other").exists()) {
                filterFiles(path + XENDER_FOLDER + "/other");
            }
        }
    }

    private void updateFilesData(List<File> files) {
        if (!CollectionUtil.isNullOrEmpty(files)) {
            mImportArchiveView.showImportableFilesList();
            mImportArchiveView.showImportableFilesList(files);

        } else {
            mImportArchiveView.hideImportableFilesList();
            mImportArchiveView.showNoFileLayout();
            mImportArchiveView.showNoFileMessage(mIsProfile ? R.string.msg_fileexplorer_no_profile : R.string.msg_fileexplorer_no_content);
        }
    }

    @Override
    public void bindView(BaseView view, Context context) {
        mImportArchiveView = (ImportArchiveContract.View) view;
        mContext = context;
    }

    @Override
    public void unbindView() {
        mContext = null;
        mImportArchiveView = null;
    }

    private class ShowImportFileTask extends AsyncTask<Void, Void, List<File>> {
        @Override
        protected List<File> doInBackground(Void... params) {
            return showImportEcarLayout();
        }

        @Override
        protected void onPostExecute(List<File> files) {
            super.onPostExecute(files);
            updateFilesData(files);
        }
    }

}
