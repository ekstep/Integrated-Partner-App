package org.ekstep.genie.ui.importarchive.fileexplorer;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;

import org.ekstep.genie.R;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.model.FileItem;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.MountPointUtils;
import org.ekstep.genie.util.geniesdk.ImportExportUtil;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.ekstep.genieservices.commons.utils.StringUtil;
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
public class FileListPresenter implements FileListContract.Presenter {
    private static final String TAG = FileListFragment.class.getSimpleName();
    private Context mContext;
    private FileListContract.View mImportView;

    // Stores names of traversed directories
    private ArrayList<String> traversedDirectory = new ArrayList<String>();
    // Check if the first level of the directory structure is the one showing
    private boolean firstLvl = true;
    private File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
    private List<FileItem> mFileList;
    private String mFilePath;
    private boolean isProfile;

    /**
     * Filename filters for .ecar, .epar and directory files
     */
    private FilenameFilter filenameFilter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String filename) {
            File sel = new File(dir, filename);
            if (filename.toLowerCase().endsWith(Constant.FILE_EXTENSION_ECAR) && !isProfile) {
                return true;
            } else if (filename.toLowerCase().endsWith(Constant.FILE_EXTENSION_EPAR) && isProfile) {
                return true;
            } else if ((sel.isDirectory()) && !sel.isHidden()) {
                return true;
            } else {
                return false;
            }
        }
    };

    public FileListPresenter() {
    }

    @Override
    public void loadFiles() {
        if (!TextUtils.isEmpty(mFilePath)) {
            path = new File(mFilePath);
            if (path != null && path.exists()) {
                loadFileList(path);
            }
        }

    }

    private void loadFileList(File path) {
        try {
            path.mkdirs();
        } catch (SecurityException e) {
            LogUtil.e(TAG, "unable to write on the sd card ");
        }

        // Checks whether path exists
        if (path.exists()) {
            String[] fList = path.list(filenameFilter);
            mFileList = new ArrayList<>();
            if (fList != null) {
                for (int i = 0; i < fList.length; i++) {
                    // Convert into file path
                    File sel = new File(path, fList[i]);
                    if (sel.isDirectory()) {
                        FileItem item = new FileItem(fList[i], R.drawable.ic_folder);
                        mFileList.add(item);
                        LogUtil.d(TAG, "DIRECTORY: " + item.file);
                    } else {
                        FileItem item = new FileItem(fList[i], R.drawable.ic_file);
                        mFileList.add(item);
                        LogUtil.d(TAG, "FILE: " + item.file);
                    }
                }
            }

            // ascending order
            Collections.sort(mFileList, new Comparator<FileItem>() {
                public int compare(FileItem o1, FileItem o2) {
                    return o1.file.toLowerCase().compareTo(o2.file.toLowerCase());
                }
            });

            if (!firstLvl) {
                if (traversedDirectory != null && traversedDirectory.size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (String folderName : traversedDirectory) {
                        stringBuilder.append(folderName + "/");
                    }
                    mFileList.add(0, new FileItem(stringBuilder.toString(), R.drawable.ic_folder_up));
                }
            }
        } else {
            LogUtil.i(TAG, "path does not exist");
        }

        mImportView.updateAdapter(mFileList);

    }

    @Override
    public void handleItemClick(int position) {
        String chosenFile = mFileList.get(position).file;
        File selFile = new File(path + "/" + chosenFile);
        if (selFile.isDirectory()) {
            firstLvl = false;

            // Adds chosen directory to list
            traversedDirectory.add(chosenFile);
            mFileList = null;
            path = new File(selFile + "");

            loadFileList(path);
            LogUtil.d(TAG, path.getAbsolutePath());

        } else if (!firstLvl && !selFile.exists()) {  // Checks if 'up' was clicked
            // present directory removed from list
            String directoryName = traversedDirectory.remove(traversedDirectory.size() - 1);

            // path modified to exclude present directory
            path = new File(path.toString().substring(0, path.toString().lastIndexOf(directoryName)));
            mFileList = null;

            // if there are no more directories in the list, then
            // its the first level
            if (traversedDirectory.isEmpty()) {
                firstLvl = true;
            }
            loadFileList(path);
            LogUtil.d(TAG, path.getAbsolutePath());
        } else { // File picked
            importFile(selFile.getAbsolutePath());
        }
    }


    private String getSecondaryStorageFilePath() {
        boolean isSecondaryStorage = false;
        String secondaryStorage = MountPointUtils.getExternalSecondaryStorage();
        if (!StringUtil.isNullOrEmpty(secondaryStorage)) {
            File path = new File(secondaryStorage);
            if (path != null && path.exists()) {
                return secondaryStorage;
            }
        }
        if (!isSecondaryStorage) {
            return getSystemRootFilePath();
        }
        return null;
    }

    private String getSystemRootFilePath() {
        File rootFile = Environment.getRootDirectory();
        File parentFile = rootFile.getParentFile();
        return parentFile.getAbsolutePath();
    }


    private void importFile(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return;
        }

        if (filePath.toLowerCase().endsWith(Constant.FILE_EXTENSION_EPAR)) {
            mImportView.showProgressDialog(mContext.getResources().getString(R.string.msg_importing));
            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.IMPORT_PROFILE, TelemetryAction.PROFILE_IMPORT_INITIATE, new HashMap<String, Object>()));
            ImportExportUtil.importProfile(filePath);

        } else if (filePath.toLowerCase().endsWith(Constant.FILE_EXTENSION_ECAR)) {
            mImportView.showProgressDialog(mContext.getResources().getString(R.string.msg_importing));
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
    public void setBundle(Bundle bundle) {
        mFilePath = bundle.getString(Constant.BUNDLE_KEY_STORAGE_FILE_PATH);
        isProfile = bundle.getBoolean(Constant.BUNDLE_KEY_IMPORT_PROFILE);
    }

    @Override
    public void bindView(BaseView view, Context context) {
        mImportView = (FileListContract.View) view;
        mContext = context;
    }

    @Override
    public void unbindView() {
        mImportView = null;
        mContext = null;
    }
}
