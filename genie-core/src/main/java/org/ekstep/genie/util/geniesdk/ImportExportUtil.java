package org.ekstep.genie.util.geniesdk;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;
import org.ekstep.genie.asynctask.PackagingGenieAsyncTask;
import org.ekstep.genie.base.ProfileConfig;
import org.ekstep.genie.callback.IInitAndExecuteGenie;
import org.ekstep.genie.telemetry.EnvironmentId;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryConstant;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.telemetry.enums.ObjectType;
import org.ekstep.genie.util.AvatarUtil;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.FileHandler;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.ShowProgressDialog;
import org.ekstep.genie.util.Util;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.ContentExportRequest;
import org.ekstep.genieservices.commons.bean.ContentExportResponse;
import org.ekstep.genieservices.commons.bean.ContentImportResponse;
import org.ekstep.genieservices.commons.bean.EcarImportRequest;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.ImportContentProgress;
import org.ekstep.genieservices.commons.bean.Profile;
import org.ekstep.genieservices.commons.bean.ProfileExportRequest;
import org.ekstep.genieservices.commons.bean.ProfileExportResponse;
import org.ekstep.genieservices.commons.bean.ProfileImportRequest;
import org.ekstep.genieservices.commons.bean.ProfileImportResponse;
import org.ekstep.genieservices.commons.bean.TelemetryExportRequest;
import org.ekstep.genieservices.commons.bean.TelemetryExportResponse;
import org.ekstep.genieservices.commons.bean.TelemetryImportRequest;
import org.ekstep.genieservices.commons.bean.enums.ContentImportStatus;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.ekstep.genieservices.commons.utils.CollectionUtil;
import org.ekstep.genieservices.commons.utils.StringUtil;
import org.ekstep.genieservices.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 6/2/2016.
 *
 * @author swayangjit_gwl
 */
public class ImportExportUtil {
    private static final String TAG = ImportExportUtil.class.getSimpleName();

    public static void importContent(Context context, String filePath, final IImport iImport) {
        importContent(context, filePath, iImport, false, true, false);
    }

    public static void importContent(Context context, String filePath, final IImport iImport, boolean isChild) {
        importContent(context, filePath, iImport, false, true, isChild);
    }

    public static void importContent(Context context, String filePath, final IImport iImport, final boolean dismissDialog, boolean isChild) {
        importContent(context, filePath, iImport, false, dismissDialog, isChild);
    }

    private static void importContent(final Context context, String filePath, final IImport iImport, final boolean isInNotification, final boolean dismissDialog, boolean isChild) {
        importContent(context, filePath, iImport, isInNotification, dismissDialog, null, isChild);
    }

    public static void importContent(final Context context, String filePath, final IImport iImport, final boolean isInNotification, final boolean dismissDialog, final String identifier, boolean isChild) {

        EcarImportRequest.Builder builder = new EcarImportRequest.Builder();
        builder.fromFilePath(filePath);
        if (isChild) {
            builder.isChildContent();
        }
        builder.toFolder(FileHandler.getDefaultStoragePath(context));
        CoreApplication.getGenieAsyncService().getContentService().importEcar(builder.build(), new IResponseHandler<List<ContentImportResponse>>() {
            @Override
            public void onSuccess(GenieResponse<List<ContentImportResponse>> genieResponse) {
                if (dismissDialog) {
                    ShowProgressDialog.dismissDialog();
                }

                List<ContentImportResponse> contentImportResponseList = genieResponse.getResult();
                if (!CollectionUtil.isNullOrEmpty(contentImportResponseList)) {
                    ContentImportStatus importStatus = contentImportResponseList.get(0).getStatus();
                    switch (importStatus) {
                        case NOT_COMPATIBLE:
                            Util.showCustomToast(R.string.error_import_not_compatible);
                            break;
                        case CONTENT_EXPIRED:
                            Util.showCustomToast(R.string.errorr_import_lesson_expired);
                            break;
                        case ALREADY_EXIST:
                            Util.showCustomToast(R.string.error_import_file_exists);
                            break;
                        default:
                            Util.processImportSuccess(genieResponse);
                            break;

                    }
                } else {
                    Util.processImportSuccess(genieResponse);
                }
                iImport.onImportSuccess();

            }

            @Override
            public void onError(GenieResponse<List<ContentImportResponse>> genieResponse) {
                handleImportFailure(context, genieResponse, isInNotification, iImport);
            }
        });
    }

    public static void doExportContent(final Intent data, final Context context) {
        final List<String> identifierList = new ArrayList<>();
        identifierList.addAll(data.getExtras().getStringArrayList(Constant.SHARE_IDENTIFIER));

        final String screenName = data.getStringExtra(Constant.SHARE_SCREEN_NAME);

//        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, screenName, TelemetryAction.SHARE_CONTENT_INITIATED, identifierList.get(0)));
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildInteractEvent(EnvironmentId.HOME, InteractionType.TOUCH, TelemetryAction.SHARE_CONTENT_INITIATED, screenName, identifierList.get(0), ObjectType.CONTENT));

        ShowProgressDialog.showProgressDialog(context, CoreApplication.getInstance().getString(R.string.msg_exporting));

        ContentExportRequest.Builder builder = new ContentExportRequest.Builder();
        builder.exportContents(identifierList).
                toFolder(FileHandler.getExternalFilesDir(context).toString());

        CoreApplication.getGenieAsyncService().getContentService().exportContent(builder.build(), new IResponseHandler<ContentExportResponse>() {
            @Override
            public void onSuccess(GenieResponse<ContentExportResponse> genieResponse) {
                ShowProgressDialog.dismissDialog();

                String filePath = genieResponse.getResult().getExportedFilePath();

                generateGEInteract(screenName, filePath, identifierList.get(0));

                EventBus.postStickyEvent(Constant.EventKey.EVENT_KEY_EXPORT_CONTENT);

                data.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + filePath));
                context.startActivity(data);
            }

            @Override
            public void onError(GenieResponse<ContentExportResponse> genieResponse) {
                ShowProgressDialog.dismissDialog();
                Util.processExportFailure(CoreApplication.getInstance(), genieResponse);
                LogUtil.i(TAG, "Export failed");
            }
        });
    }

    private static void generateGEInteract(String screenName, String filePath, String identifier) {
        if (filePath != null) {
            File file = new File(filePath);
            if (file.exists()) {
                long fileSizeInBytes = file.length();
                float fileSizeinKB = (float) fileSizeInBytes / 1024;
                float fileSizeInMB = (float) fileSizeinKB / 1024;
                float size = BigDecimal.valueOf(fileSizeInMB).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                if (!StringUtil.isNullOrEmpty(identifier)) {
                    Map<String, Object> map = new HashMap<>();
                    map.put(TelemetryConstant.SIZE_OF_FILE_IN_MB, size);

//                    TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.OTHER, screenName, TelemetryAction.SHARE_CONTENT_SUCCESS, identifier, map));
                    TelemetryHandler.saveTelemetry(TelemetryBuilder.buildInteractEvent(EnvironmentId.HOME, InteractionType.OTHER, TelemetryAction.SHARE_CONTENT_SUCCESS, screenName, identifier, ObjectType.CONTENT, map));
                }
            }
        }
    }

    private static void handleImportFailure(Context context, GenieResponse genieResponse, boolean isInNotification, final IImport iContent) {
        ShowProgressDialog.dismissDialog();

        String error = genieResponse.getError();

        LogUtil.e(TAG, "Genie Service Error Log: " + error);

        boolean isImportFailed = false;
        int resId = R.string.error_import_failed;

        if (Constant.INVALID_FILE.equalsIgnoreCase(error)) {
            isImportFailed = true;
            resId = R.string.error_duplicate_file;
        } else if (Constant.DB_ERROR.equalsIgnoreCase(error)) {
            isImportFailed = true;
            resId = R.string.error_db_operation;
        } else if (Constant.PROCESSING_ERROR.equalsIgnoreCase(error)) {
            isImportFailed = true;
            resId = R.string.error_import_failed;
        } else if (Constant.NETWORK_ERROR.equalsIgnoreCase(error)) {
            isImportFailed = true;
            resId = R.string.error_network;
        } else if (Constant.AUTHENTICATION_ERROR.equalsIgnoreCase(error)) {
            isImportFailed = true;
            resId = R.string.error_authentication_failed;
        } else if (Constant.VALIDATION_ERROR.equalsIgnoreCase(error)) {
            isImportFailed = true;
            resId = R.string.error_sync_validation;
        } else if (Constant.IMPORT_FAILED.equalsIgnoreCase(error)) {
            isImportFailed = true;
            resId = R.string.error_procession_failed;
        } else if (Constant.IMPORT_FILE_EXIST.equalsIgnoreCase(error)) {
            isImportFailed = true;
            resId = R.string.error_import_file_exists;
        } else if (Constant.NO_CONTENT_TO_IMPORT.equalsIgnoreCase(error)) {
            isImportFailed = true;
            resId = R.string.error_import_failed;
        } else if (Constant.IMPORT_FAILED_DEVICE_MEMORY_FULL.equalsIgnoreCase(error)) {
            isImportFailed = true;
            resId = R.string.error_import_device_merory_full;
        } else if (Constant.DRAFT_ECAR_FILE_EXPIRED.equalsIgnoreCase(error)) {
            isImportFailed = true;
            resId = R.string.errorr_import_lesson_expired;
        } else if (Constant.UNSUPPORTED_MANIFEST.equalsIgnoreCase(error)) {
            if (!isInNotification) {
//                showOutdatedEcartDialog((Activity) context, iContent);
            }
        } else {
            isImportFailed = true;
            resId = R.string.error_import_failed;
        }

        if (isImportFailed) {
            if (iContent != null) {
                iContent.onImportFailure();
            }

            Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
        }
    }

    public static void importProfile(String filePath) {
        ProfileImportRequest importRequest = new ProfileImportRequest.Builder().fromFilePath(filePath).build();
        CoreApplication.getGenieAsyncService().getUserService().importProfile(importRequest, new IResponseHandler<ProfileImportResponse>() {
            @Override
            public void onSuccess(final GenieResponse<ProfileImportResponse> genieResponse) {

//                TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.OTHER, TelemetryStageId.IMPORT_PROFILE, null));
                TelemetryHandler.saveTelemetry(TelemetryBuilder.buildInteractEvent(EnvironmentId.HOME, InteractionType.OTHER, null, TelemetryStageId.IMPORT_PROFILE));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        updateProfileImagePath();
                    }
                }).start();
                onImportProfileSuccess(genieResponse);
            }

            @Override
            public void onError(GenieResponse<ProfileImportResponse> genieResponse) {
                handleImportFailure(CoreApplication.getInstance(), genieResponse, true, null);
            }
        });
    }

    private static void updateProfileImagePath() {
        CoreApplication.getGenieAsyncService().getUserService().getAllUserProfile(new IResponseHandler<List<Profile>>() {
            @Override
            public void onSuccess(GenieResponse<List<Profile>> genieResponse) {
                if (genieResponse.getResult() != null && genieResponse.getResult().size() > 0) {
                    for (Profile profile : genieResponse.getResult()) {
                        if (StringUtil.isNullOrEmpty(profile.getProfileImage())) {
                            String avatar = profile.getAvatar();
                            ProfileConfig profileConfig = new ProfileConfig();
                            String profileImagePath = profileConfig.getProfilePath(CoreApplication.getInstance()) + AvatarUtil.getProfileImagePath().get(avatar);
                            profile.setProfileImage(profileImagePath);

                            CoreApplication.getGenieAsyncService().getUserService().updateUserProfile(profile, new IResponseHandler<Profile>() {
                                @Override
                                public void onSuccess(GenieResponse<Profile> genieResponse) {

                                }

                                @Override
                                public void onError(GenieResponse<Profile> genieResponse) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onError(GenieResponse<List<Profile>> genieResponse) {

            }
        });
    }

    private static void onImportProfileSuccess(GenieResponse<ProfileImportResponse> genieResponse) {
        ShowProgressDialog.dismissDialog();

        ProfileImportResponse response = genieResponse.getResult();
        if (response != null) {
            StringBuilder message = new StringBuilder();

            Context context = CoreApplication.getInstance();

            boolean isImportSuccessfull = false;
            if (response.getImported() > 0) {
                message.append(response.getImported() + " " + context.getString(R.string.msg_import_successfull));
                isImportSuccessfull = true;
            }

            if (response.getFailed() > 0) {
                if (response.getFailed() == 1) {
                    if (isImportSuccessfull) {
                        message.append("\n" + response.getFailed() + " " + context.getString(R.string.error_import_file_already_exists_1));
                    } else {
                        message.append(context.getString(R.string.error_import_file_already_exists));
                    }

                } else {
                    message.append("\n" + response.getFailed() + " " + context.getString(R.string.error_import_file_already_exists_1));
                }
            }

            Util.showCustomToast(message.toString());
        }
    }

    public static void doExportProfiles(final Intent data, final Context context) {
        final List<String> profileList = new ArrayList<>();

        if (data.getExtras().get(Constant.SHARE_IDENTIFIER) instanceof ArrayList) {
            profileList.addAll(data.getExtras().getStringArrayList(Constant.SHARE_IDENTIFIER));
        } else {
            String identifier = data.getStringExtra(Constant.SHARE_IDENTIFIER);
            profileList.add(identifier);
        }

        final String screenName = data.getStringExtra(Constant.SHARE_SCREEN_NAME);

//        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, screenName, TelemetryAction.SHARE_PROFILE_INITIATE, profileList.get(0)));
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildInteractEvent(EnvironmentId.USER, InteractionType.TOUCH, TelemetryAction.SHARE_PROFILE_INITIATE, screenName, profileList.get(0), ObjectType.USER));

        ShowProgressDialog.showProgressDialog(context, CoreApplication.getInstance().getString(R.string.msg_exporting));

        ProfileExportRequest.Builder builder = new ProfileExportRequest.Builder();
        builder.exportUsers(profileList).
                toFolder(FileHandler.getExternalFilesDir(context).toString());

        CoreApplication.getGenieAsyncService().getUserService().exportProfile(builder.build(), new IResponseHandler<ProfileExportResponse>() {
            @Override
            public void onSuccess(GenieResponse<ProfileExportResponse> genieResponse) {
                ShowProgressDialog.dismissDialog();

                String filePath = genieResponse.getResult().getExportedFilePath();

//                Util.sendBroadcast(Constant.INTENT_ACTION_REFRESH_PROFILE_ADAPTER);

                EventBus.postStickyEvent(Constant.EventKey.EVENT_KEY_EXPORT_PROFILE);

//                TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.OTHER, screenName, TelemetryAction.SHARE_PROFILE_SUCCESS, profileList.get(0)));
                TelemetryHandler.saveTelemetry(TelemetryBuilder.buildInteractEvent(EnvironmentId.USER, InteractionType.OTHER, TelemetryAction.SHARE_PROFILE_SUCCESS, screenName, profileList.get(0), ObjectType.USER));

                data.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath))); // data.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + filePath));
                context.startActivity(data);
            }

            @Override
            public void onError(GenieResponse<ProfileExportResponse> genieResponse) {
                ShowProgressDialog.dismissDialog();
                LogUtil.i(TAG, "Export profile failed");
            }
        });
    }

    public static void doExportTelemetry(final Intent data, final Activity activity) {
        ShowProgressDialog.showProgressDialog(activity, CoreApplication.getInstance().getString(R.string.msg_exporting));

        TelemetryExportRequest request = new TelemetryExportRequest.Builder().toFolder(FileHandler.getExternalFilesDir(activity).toString()).build();

        CoreApplication.getGenieAsyncService().getTelemetryService().exportTelemetry(request, new IResponseHandler<TelemetryExportResponse>() {
            @Override
            public void onSuccess(GenieResponse<TelemetryExportResponse> genieResponse) {
                ShowProgressDialog.dismissDialog();

                String filePath = genieResponse.getResult().getExportedFilePath();

                data.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + filePath));
                activity.startActivity(data);
            }

            @Override
            public void onError(GenieResponse<TelemetryExportResponse> genieResponse) {
                ShowProgressDialog.dismissDialog();

                Util.processFailure(genieResponse);
            }
        });
    }

    public static void doShareGenie(final Intent data, final Context context) {
        boolean isLink = data.getBooleanExtra(Constant.IS_LINK, false);
        if (isLink) {
            try {
                context.startActivity(data);
            } catch (Exception e) {
                Util.showCustomToast(R.string.error_app_not_found);
            }
        } else {
            final String destinationFilePath = FileHandler.getExportGenieAPKFilePath(FileHandler.getExternalFilesDir(context));

            PackagingGenieAsyncTask packagingGenieAsyncTask = new PackagingGenieAsyncTask(context, new PackagingGenieAsyncTask.IApkExport() {
                @Override
                public void onExportComplete(String filePath) {
                    data.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + filePath));
                    try {
                        context.startActivity(data);
                    } catch (ActivityNotFoundException activityNotFoundException) {
                        Util.showCustomToast(R.string.error_app_not_found);
                    }
                }
            });
            packagingGenieAsyncTask.execute(destinationFilePath);
        }
    }

    public static void doShareGenieConfigurations(final Intent data, final Context context) {
        boolean isText = data.getBooleanExtra(Constant.IS_TEXT, false);
        if (isText) {
            context.startActivity(data);
        } else {
            File genieSupportDirectory = FileHandler.getRequiredDirectory(Environment.getExternalStorageDirectory(), "GenieSupport");

            String subject = data.getStringExtra(Intent.EXTRA_SUBJECT);
            String subjectParts[] = null;
            String fileName;
            if (!TextUtils.isEmpty(subject)) {
                subjectParts = subject.split(":");
                fileName = subjectParts[1];
            } else {
                fileName = "support";
            }
            String filePath = genieSupportDirectory + "/" + "Details_" + fileName + ".txt";
            String supportData = data.getStringExtra(Constant.SUPPORT_DATA);

            FileHandler.createFileInTheDirectory(filePath);
            FileHandler.saveToFile(filePath, supportData);

            data.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + filePath));
            context.startActivity(data);
        }
    }

    /**
     * Initiate the import file if Genie supported the file for side loading.
     *
     * @param intent
     * @return
     */
    public static boolean initiateImportFile(Activity activity, IInitAndExecuteGenie callback, Intent intent, boolean showProgressDialog) {
        Uri uri = intent.getData();

        if (uri == null) {
            return false;
        }

        if (intent.getScheme().equals(activity.getString(R.string.deeplink_scheme_content))) {
            return importGenieSupportedFile(activity, callback, FileHandler.getAttachmentFilePath(uri), true, showProgressDialog);
        } else if (intent.getScheme().equals(activity.getString(R.string.deeplink_scheme_file))) {
            return importGenieSupportedFile(activity, callback, uri.getPath(), false, showProgressDialog);
        } else {
            Util.showCustomToast(R.string.error_import_file_not_supported);
            return false;
        }

    }

    private static boolean importGenieSupportedFile(Activity activity, final IInitAndExecuteGenie callback, final String filePath, final boolean isAttachment, boolean showProgressDialog) {
        if (!Util.isValidExtension(filePath)) {
            return false;
        }

        if (showProgressDialog) {
            ShowProgressDialog.showProgressDialog(activity, activity.getString(R.string.msg_importing));
        }

        if (Util.isEcar(filePath)) {
            final ImportExportUtil importExportUtil = new ImportExportUtil();

            EventBus.registerSubscriber(importExportUtil);

            importContent(activity, filePath, new IImport() {
                @Override
                public void onImportSuccess() {
                    EventBus.unregisterSubscriber(importExportUtil);
//                    Util.showCustomToast(R.string.msg_import_success);
                    EventBus.postStickyEvent(Constant.EventKey.EVENT_KEY_IMPORT_LOCAL_CONTENT);

                    if (isAttachment) {
                        File file = new File(filePath);
                        file.delete();
                    }

                    callback.initAndExecuteGenie();
                }

                @Override
                public void onImportFailure() {
                    EventBus.unregisterSubscriber(importExportUtil);
                    callback.initAndExecuteGenie();
                }

                @Override
                public void onOutDatedEcarFound() {
                    EventBus.unregisterSubscriber(importExportUtil);
                    callback.initAndExecuteGenie();
                }
            });
        } else if (Util.isEpar(filePath)) {

//            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.SPLASH, TelemetryAction.PROFILE_IMPORT_INITIATE));
            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildInteractEvent(EnvironmentId.HOME, InteractionType.TOUCH, TelemetryAction.PROFILE_IMPORT_INITIATE, TelemetryStageId.SPLASH));
            ProfileImportRequest request = new ProfileImportRequest.Builder().fromFilePath(filePath).build();
            CoreApplication.getGenieAsyncService().getUserService().importProfile(request, new IResponseHandler<ProfileImportResponse>() {
                @Override
                public void onSuccess(GenieResponse<ProfileImportResponse> genieResponse) {

//                    TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.OTHER, TelemetryStageId.SPLASH, TelemetryAction.PROFILE_IMPORT_SUCCESS));
                    TelemetryHandler.saveTelemetry(TelemetryBuilder.buildInteractEvent(EnvironmentId.HOME, InteractionType.OTHER, TelemetryAction.PROFILE_IMPORT_SUCCESS, TelemetryStageId.SPLASH));
                    if (isAttachment) {
                        File file = new File(filePath);
                        file.delete();
                    }

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            updateProfileImagePath();
                        }
                    }).start();
                    onImportProfileSuccess(genieResponse);

                    callback.initAndExecuteGenie();
                }

                @Override
                public void onError(GenieResponse<ProfileImportResponse> genieResponse) {
                    callback.initAndExecuteGenie();
                }
            });
        } else if (Util.isGsa(filePath)) {
            TelemetryImportRequest request = new TelemetryImportRequest.Builder().fromFilePath(filePath).build();
            CoreApplication.getGenieAsyncService().getTelemetryService().importTelemetry(request, new IResponseHandler<Void>() {
                @Override
                public void onSuccess(GenieResponse<Void> genieResponse) {
                    if (isAttachment) {
                        File file = new File(filePath);
                        file.delete();
                    }

                    callback.initAndExecuteGenie();
                }

                @Override
                public void onError(GenieResponse<Void> genieResponse) {
                    callback.initAndExecuteGenie();
                }
            });
        }

        return true;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onContentImport(ImportContentProgress importContentProgress) throws InterruptedException {

        String msg = CoreApplication.getInstance().getString(R.string.msg_import_count) +
                " (" + importContentProgress.getCurrentCount() + "/" + importContentProgress.getTotalCount() + ") "
                + CoreApplication.getInstance().getString(R.string.msg_please_wait);

        ShowProgressDialog.setMsg(msg);
    }

    public interface IImport {
        void onImportSuccess();

        void onImportFailure();

        void onOutDatedEcarFound();
    }
}
