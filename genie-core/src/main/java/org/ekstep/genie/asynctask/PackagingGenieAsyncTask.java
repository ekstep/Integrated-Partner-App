package org.ekstep.genie.asynctask;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import org.ekstep.genie.R;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryConstant;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.util.ShowProgressDialog;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.ekstep.genieservices.commons.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 03-05-2016.
 *
 * @author GoodWorkLabs
 */
public class PackagingGenieAsyncTask extends AsyncTask<String, Void, String> {

    private IApkExport mApkExport;
    private Context mContext;

    public PackagingGenieAsyncTask(Context context, IApkExport apkExport) {
        mApkExport = apkExport;
        mContext = context;
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.SETTINGS_HOME, TelemetryAction.SHARE_GENIE_INITIATED));
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ShowProgressDialog.showProgressDialog(mContext, mContext.getResources().getString(R.string.msg_home_packaging_genie));
    }

    @Override
    protected String doInBackground(String... params) {
        if (params[0] != null) {
            File srcFile = new File(getGenieApkFilePath());
            File dstFile = new File(params[0]);
            try {
                FileUtil.cp(srcFile, dstFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return dstFile.getAbsolutePath();
        } else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String filePath) {
        super.onPostExecute(filePath);
        ShowProgressDialog.dismissDialog();
        if (filePath != null) {
            File file = new File(filePath);
            if (file.exists()) {
                long fileSizeInBytes = file.length();
                float fileSizeinKB = (float) fileSizeInBytes / 1024;
                float fileSizeInMB = (float) fileSizeinKB / 1024;
                float size = BigDecimal.valueOf(fileSizeInMB).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                Map<String, Object> eksMap = new HashMap<>();
                eksMap.put(TelemetryConstant.SIZE_OF_FILE_IN_MB, String.valueOf(size));
                TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.OTHER, TelemetryStageId.SETTINGS_HOME, TelemetryAction.SHARE_GENIE_SUCCESS, eksMap));
            }

            mApkExport.onExportComplete(filePath);
        }
    }

    private String getGenieApkFilePath() {
        String filePath = null;

        PackageManager packageManager = mContext.getPackageManager();
        String myPackage = mContext.getPackageName();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(myPackage, 0);
            File apkFile = new File(applicationInfo.publicSourceDir);
            if (apkFile != null && apkFile.exists()) {
                filePath = apkFile.getAbsolutePath();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return filePath;
    }

    public interface IApkExport {
        void onExportComplete(String filePath);
    }
}
