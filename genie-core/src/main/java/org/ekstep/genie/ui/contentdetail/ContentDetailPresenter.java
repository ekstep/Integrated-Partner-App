package org.ekstep.genie.ui.contentdetail;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;
import org.ekstep.genie.activity.BaseActivity;
import org.ekstep.genie.activity.RuntimePermissionsActivity;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.model.ContentDeleteResponse;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryConstant;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.telemetry.enums.EntityType;
import org.ekstep.genie.ui.landing.LandingActivity;
import org.ekstep.genie.ui.share.ShareActivity;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.DeviceUtility;
import org.ekstep.genie.util.DialogUtils;
import org.ekstep.genie.util.FileHandler;
import org.ekstep.genie.util.NetworkUtil;
import org.ekstep.genie.util.PlayerUtil;
import org.ekstep.genie.util.ShowProgressDialog;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.geniesdk.ContentUtil;
import org.ekstep.genie.util.preference.PreferenceKey;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.IContentService;
import org.ekstep.genieservices.async.ContentService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.ContentAccess;
import org.ekstep.genieservices.commons.bean.ContentData;
import org.ekstep.genieservices.commons.bean.ContentDelete;
import org.ekstep.genieservices.commons.bean.ContentDeleteRequest;
import org.ekstep.genieservices.commons.bean.ContentDetailsRequest;
import org.ekstep.genieservices.commons.bean.ContentFeedback;
import org.ekstep.genieservices.commons.bean.ContentImport;
import org.ekstep.genieservices.commons.bean.ContentImportRequest;
import org.ekstep.genieservices.commons.bean.ContentImportResponse;
import org.ekstep.genieservices.commons.bean.DownloadProgress;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.HierarchyInfo;
import org.ekstep.genieservices.commons.bean.enums.ContentAccessStatus;
import org.ekstep.genieservices.commons.bean.enums.ContentImportStatus;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.ekstep.genieservices.commons.utils.CollectionUtil;
import org.ekstep.genieservices.commons.utils.StringUtil;
import org.ekstep.genieservices.eventbus.EventBus;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created on 20/12/16.
 *
 * @author swayangjit
 */
public class ContentDetailPresenter implements ContentDetailContract.Presenter {

    private static final String TAG = ContentDetailPresenter.class.getSimpleName();
    private Context mContext;
    private ContentDetailContract.View mContentDetailView;
    private boolean mIsFromDeeplink = false;
    private ContentData mContentData;
    private Content mContent;
    private boolean mIsArtifactAvailable;
    private boolean mIsSpineAvailable;
    private boolean mIsFromTextBookOrCollection = false;
    private boolean mIsFromDownloadsScreen;
    private boolean mIsParentTextbookOrCollection = false;
    private boolean mIsCanvasDeeplink;
    private boolean mIsFromSearch;
    private ArrayList<HierarchyInfo> mHirarchyInfoInfoList = null;
    private List<HashMap<String, String>> rules = new ArrayList<>();
    private ContentService mContentService = null;
    private IContentService mSyncContentService = null;
    private boolean mShouldRefresh;
    private String mIdentifier;

    private String mDownloadingString = null;

    private ContentImportResponse mImportResponse;

    private boolean mIsContentImported;
    private boolean mIsContentDeleted;
    private boolean mIsDownloading;
    private String mContentSize;

    public ContentDetailPresenter() {
        mContentService = CoreApplication.getGenieAsyncService().getContentService();
        mSyncContentService = CoreApplication.getGenieSdkInstance().getContentService();
    }

    @Override
    public void fetchBundleExtras(Bundle bundle) {
        mIsFromTextBookOrCollection = bundle.getBoolean(Constant.BundleKey.BUNDLE_KEY_IS_FROM_COLLECTION_OR_TEXTBOOK);
        mIsFromDownloadsScreen = bundle.getBoolean(Constant.BundleKey.BUNDLE_KEY_IS_FROM_DOWNLOADS, false);
        mIsParentTextbookOrCollection = bundle.getBoolean(Constant.BundleKey.BUNDLE_KEY_IS_PARENT_TEXTBOOK_OR_COLLECTION, false);
        mDownloadingString = mContext.getString(R.string.label_contentdetail_downloading);

        if (bundle.containsKey(Constant.BundleKey.BUNDLE_KEY_CONTENT_DATA)) {
            mContentData = (ContentData) bundle.getSerializable(Constant.BundleKey.BUNDLE_KEY_CONTENT_DATA);
        } else if (bundle.containsKey(Constant.BundleKey.BUNDLE_KEY_CONTENT)) {
            mContent = (Content) bundle.getSerializable(Constant.BundleKey.BUNDLE_KEY_CONTENT);
            mContentData = mContent.getContentData();
        }

        if (bundle.containsKey(Constant.BundleKey.BUNDLE_KEY_HIERARCHY_INFO)) {
            mHirarchyInfoInfoList = (ArrayList<HierarchyInfo>) bundle.getSerializable(Constant.BundleKey.BUNDLE_KEY_HIERARCHY_INFO);
            if (mHirarchyInfoInfoList == null) {
                mHirarchyInfoInfoList = new ArrayList<>();
            }
        } else {
            mHirarchyInfoInfoList = new ArrayList<>();
        }


        if (bundle.containsKey(Constant.BundleKey.BUNDLE_KEY_DEEP_LINK_IDENTIFIER)) {
            mIdentifier = bundle.getString(Constant.BundleKey.BUNDLE_KEY_DEEP_LINK_IDENTIFIER);
            mIsFromDeeplink = true;
            if (bundle.containsKey(Constant.BundleKey.BUNDLE_KEY_IS_CANVAS_DEEPLINK)) {
                mIsCanvasDeeplink = true;
            }
            getContentDetail(mIdentifier, true, true, false);

        } else if (bundle.getBoolean(Constant.BundleKey.BUNDLE_KEY_IS_SPINE_AVAILABLE, false)) {
            mIsSpineAvailable = true;
            mIdentifier = mContentData.getIdentifier();
            manageDownloadAndPlay();
            showContentInfo();
            getContentDetail(mContentData.getIdentifier(), false, true, true);
        } else if (bundle.getBoolean(Constant.BundleKey.BUNDLE_KEY_IS_FROM_SEARCH, false)) {
            mIsFromSearch = true;
            mIdentifier = mContentData.getIdentifier();
            if (ContentUtil.getLocalContentsCache().contains(mIdentifier)) {
                getContentDetail(mIdentifier, false, true, true);
            } else {
                manageDownloadAndPlay();
                showContentInfo();
            }
        } else {
            mIdentifier = mContentData.getIdentifier();
            if (ContentUtil.getLocalContentsCache().contains(mIdentifier)) {
                getContentDetail(mIdentifier, false, true, true);
            } else {
                manageDownloadAndPlay();
                showContentInfo();
            }
        }

        if (mIsFromTextBookOrCollection && !mHirarchyInfoInfoList.isEmpty()) {
            mContentDetailView.hideShareOption();
            // TODO 08/04/2017 enable this once preview button is enabled
            // mContentDetailView.hidePreview();
        } else {
            mContentDetailView.showShareOption();
            //TODO 08/04/2017 enable this once preview button is enabled
            // mContentDetailView.showPreview();
        }
        Map<String, Object> map = new HashMap<>();
        if (!StringUtil.isNullOrEmpty(mIdentifier)) {
            String presentOnDevice = ContentUtil.getLocalContentsCache().contains(mIdentifier) ? "Y" : "N";
            map.put(TelemetryConstant.PRESENT_ON_DEVICE, presentOnDevice);
        }
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.SHOW, TelemetryStageId.CONTENT_DETAIL, EntityType.CONTENT, mIdentifier, map));
        if (mContentData == null && !bundle.containsKey(Constant.EXTRA_DEEP_LINK_IDENTIFIER)) {
            mContentDetailView.handleMissingContent();
        }
    }

    @Override
    public void showContentInfo() {
        mContentDetailView.showName(mContentData.getName());
        mContentDetailView.showDescription(mContentData.getDescription());

        if (!StringUtil.isNullOrEmpty(mContentData.getContentType())) {
            mContentDetailView.showType(mContentData.getContentType());
        } else {
            mContentDetailView.hideType();
        }

        String size = computeSize();
        if (size != null) {
            mContentSize = String.valueOf(Util.humanReadableByteCount(new BigDecimal(size).longValue(), true));
            mContentDetailView.showSize(mContentSize);
        } else {
            mContentDetailView.hideSize();
        }

        if (mContentData.getLanguage() != null && mContentData.getLanguage().size() > 0) {
            mContentDetailView.showLanguage(mContentData.getLanguage().get(0));
        } else {
            mContentDetailView.hideLanguage();
        }

        setContentIcon();

        if (!StringUtil.isNullOrEmpty(mContentData.getPublisher())) {
            mContentDetailView.showPublisher(mContentData.getPublisher());
        } else {
            mContentDetailView.hidePublisher();
        }

        if (!StringUtil.isNullOrEmpty(mContentData.getOwner())) {
            mContentDetailView.showCreator(mContentData.getOwner());
        } else {
            mContentDetailView.hideCreator();
        }

        setAveragenTotalRatings();

        if (!StringUtil.isNullOrEmpty(mContentData.getCopyright())) {
            mContentDetailView.showCopyRight(mContentData.getCopyright());
        } else {
            mContentDetailView.hideCopyRight();
        }

        if (!StringUtil.isNullOrEmpty(mContentData.getLicense())) {
            mContentDetailView.showLicense(mContentData.getLicense());
        } else {
            mContentDetailView.showLicense(mContext.getString(R.string.label_contentdetail_ccby));
        }

        mContentDetailView.showContentScreenshots(mContentData.getScreenshots());
    }

    private String computeSize() {
        if (mIsArtifactAvailable) {
            return String.valueOf(mContent.getSizeOnDevice());
        } else {
            return ContentUtil.getContentSize(mContentData);
        }
    }

    @Override
    public void downloadContent() {
        if (NetworkUtil.isNetworkAvailable(mContext)) {
            if (Build.VERSION.SDK_INT >= 23) {
                ((BaseActivity) mContext).requestAppPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        "Genie needs to access your data",
                        RuntimePermissionsActivity.REQUEST_PERMISSION);
            } else {
                initDownload();
            }
        } else {
            // No internet connection error
            mContentDetailView.showFailureMessage(R.string.error_network2);
        }
    }

    @Override
    public void cancelDownload() {
        mSyncContentService.cancelDownload(mContentData.getIdentifier());
        manageDownloadButtonVisibility(false);
        mContentDetailView.setDownloadProgress(0);
    }

    @Override
    public void handleDeleteClick() {
        mContentDetailView.showDeleteConfirmationDialog(mContent != null ? mContent.getReferenceCount() : 0, mContent.getIdentifier());
    }

    @Override
    public void playContent() {
        PlayerUtil.startContent(mContext, mContent, TelemetryStageId.CONTENT_DETAIL, mIsFromDownloadsScreen);
    }

    @Override
    public void shareContent() {
        if (ContentUtil.isContentLive(mContentData.getStatus())) {
            List<String> identifierList = new ArrayList<>();
            identifierList.add(mContentData.getIdentifier());
            Map<String, String> values = new HashMap<>();
            values.put(Constant.SHARE_NAME, mContentData.getName());
            values.put(Constant.SHARE_SCREEN_NAME, TelemetryStageId.CONTENT_DETAIL);

            if (mIsArtifactAvailable) {    // If artifact is available then export the individual content.
                ShareActivity.startContetExportIntent(mContext, true, values, identifierList);
            } else {
                ShareActivity.startContentLinkIntent(mContext, true, values, identifierList);
            }
        } else {
            // Draft content can not imported.
            mContentDetailView.showFailureMessage(R.string.error_share_cant_share_draft_content);
        }
    }

    @Override
    public void rateContent() {
        DialogUtils.showFeedbackDialog(mContext, this, mContentData.getIdentifier(), ContentUtil.getPreviousRating(mContent.getContentFeedback()));

    }

    @Override
    public void previewContent() {
        if (NetworkUtil.getConnectivityStatus(mContext) == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
            // No internet connection error
            // TODO 19/07/17 change error message to "Please connect to the internet to see this”
            mContentDetailView.showFailureMessage(R.string.error_network_1);
            return;
        }

        // TODO 19/07/17 Add the content player related code to preview content
    }

    @Override
    public void showMoreDescription(int lineCount) {
        if (lineCount > 2) {
            mContentDetailView.showMoreDescription();
        }
    }

    @Override
    public void handleBackButtonClick() {

        if (mIsFromDeeplink && !mIsCanvasDeeplink) {
            Intent intent = new Intent(mContext, LandingActivity.class);
            mContext.startActivity(intent);
        }
        if (mShouldRefresh) {
            ((Activity) mContext).setResult(Activity.RESULT_OK);
        }

        ((ContentDetailActivity) mContext).finish();

        postRequiredStickyEvents();
    }

    @Override
    public void onResume() {
        if (!mIsFromSearch) {
            getContentDetail(mIdentifier, false, true, false);
        } else {
            getContentDetail(mIdentifier, false, false, true);
        }
    }

    @Override
    public void updateProgressBar(DownloadProgress progress) {
        if (progress != null) {
            if (progress.getIdentifier().equalsIgnoreCase(mContentData.getIdentifier())) {
                int downloadProgress = progress.getDownloadProgress();
                if (downloadProgress == 100) {
                    mContentDetailView.setDownloadProgressText(mContext.getResources().getString(R.string.label_contentdetail_importing));
                    mContentDetailView.hideDownloadProgress();
                } else {
                    mContentDetailView.showDownloadProgress();
                    mContentDetailView.showDownloadProgressText();
                    mContentDetailView.setDownloadProgress(progress.getDownloadProgress());
                    String progPercent = "(" + (downloadProgress == -1 ? 0 : downloadProgress) + "%)";

                    if (NetworkUtil.getConnectivityStatusString(mContext) == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
                        mContentDetailView.setDownloadProgressText(mContext.getString(R.string.label_waiting_for_connection)
                                + progPercent);
                    } else {
                        mContentDetailView.setDownloadProgressText(mDownloadingString + progPercent);
                    }
                }
            }
        }
    }

    @Override
    public void manageImportSuccess(ContentImportResponse importResponse) {
        if (importResponse.getStatus().getValue() == ContentImportStatus.IMPORT_COMPLETED.getValue() && importResponse.getIdentifier().equalsIgnoreCase(mContentData.getIdentifier())) {
            mImportResponse = importResponse;
            Set<String> identifierList = new HashSet<>();
            identifierList.add(importResponse.getIdentifier());
            mShouldRefresh = true;
            ContentUtil.setLocalContentsCache(identifierList);
            getContentDetail(importResponse.getIdentifier(), false, true, false);

            //sometimes import event is received earlier than download progress complete, so this ensures
            //the hiding of the progress bar before showing play and delete button
            mContentDetailView.hideDownloadProgress();
            mContentDetailView.hideDownload();
            mContentDetailView.hideDownloadProgressText();
            mIsContentImported = true;
            mIsContentDeleted = false;
            mIsDownloading = false;
        }
    }

    @Override
    public void deleteContent(final String identifier) {
        ContentDeleteRequest.Builder request = new ContentDeleteRequest.Builder();
        request.add(new ContentDelete(identifier, mIsFromTextBookOrCollection));
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.CONTENT_DETAIL, TelemetryAction.DELETE_CONTENT_INITIATED, identifier));

        mContentService.deleteContent(request.build(), new IResponseHandler<List<org.ekstep.genieservices.commons.bean.ContentDeleteResponse>>() {
            @Override
            public void onSuccess(GenieResponse<List<org.ekstep.genieservices.commons.bean.ContentDeleteResponse>> genieResponse) {
                ContentUtil.removeFromLocalCache(identifier);

                mIsContentImported = false;
                mIsContentDeleted = true;
                mShouldRefresh = true;
                mIsArtifactAvailable = false;

                if (mContentDetailView == null) {
                    return;
                }

                mContentDetailView.hideDelete();
                mContentDetailView.hidePlay();
                mContentDetailView.showDownload();

                // Content deleted successfully
                mContentDetailView.showSuccessMessage(R.string.msg_content_deletion_sucessfull);
            }

            @Override
            public void onError(GenieResponse<List<org.ekstep.genieservices.commons.bean.ContentDeleteResponse>> genieResponse) {

            }
        });
    }

    @Override
    public void handleFeedBackSuccess(float rating, boolean showSuccessfullMessage) {
        mContentDetailView.showFeedBackLayout();
        mContentDetailView.showUserRatingBar(rating);
        if (showSuccessfullMessage) {
            mContentDetailView.showFeedBackSuccessfulMessage();
        }
    }

    @Override
    public void postRequiredStickyEvents() {
        if (mIsContentImported && !mIsDownloading) {
            EventBus.postStickyEvent(mImportResponse);
        }

        if (mIsContentDeleted) {
            ContentDeleteResponse deletedContentResponse = new ContentDeleteResponse(mIdentifier);
            EventBus.postStickyEvent(deletedContentResponse);
        }
    }

    private void getContentDetail(String identifier, final boolean showProgressAndErrorMsg, final boolean shouldRenderViews, boolean shouldCallAPI) {
        if (showProgressAndErrorMsg) {
            mContentDetailView.setProgressIndicator(true);
        }
        ContentDetailsRequest.Builder requestBuilder = new ContentDetailsRequest.Builder().forContent(identifier).withFeedback().withContentAccess();
        if (shouldCallAPI) {
            requestBuilder.refreshContentDetailsFromServer();
        }

        mContentService.getContentDetails(requestBuilder.build(), new IResponseHandler<Content>() {
            @Override
            public void onSuccess(GenieResponse<Content> genieResponse) {
                if (mContentDetailView == null) {
                    return;
                }

                if (showProgressAndErrorMsg) {
                    ShowProgressDialog.dismissDialog();
                }
                mContent = genieResponse.getResult();
                mContentData = mContent.getContentData();
                if (mContent == null) {
                    mContentDetailView.handleMissingContent();
                }

                if (shouldRenderViews) {
                    manageDownloadAndPlay();
                    showContentInfo();
                }

                if (mContent != null) {
                    mIsArtifactAvailable = mContent.isAvailableLocally();
                }

                if (!CollectionUtil.isNullOrEmpty(mHirarchyInfoInfoList)) {
                    mContent.setHierarchyInfo(mHirarchyInfoInfoList);
                }

                if (mContent != null && mContent.getContentAccess() != null) {
                    List<ContentAccess> contentAccessList = mContent.getContentAccess();
                    if (!CollectionUtil.isNullOrEmpty(contentAccessList)) {
                        ContentAccess contentAccess = contentAccessList.get(0);
                        if (contentAccess.getStatus() == ContentAccessStatus.PLAYED) {
                            List<ContentFeedback> feedbackList = mContent.getContentFeedback();
                            if (!CollectionUtil.isNullOrEmpty(feedbackList)) {
                                handleFeedBackSuccess(feedbackList.get(0).getRating(), false);
                            } else {
                                handleFeedBackSuccess(0, false);
                            }
                        }
                    } else {
                        mContentDetailView.hideFeedBackLayout();
                    }
                }
            }

            @Override
            public void onError(GenieResponse<Content> genieResponse) {
                mContentDetailView.setProgressIndicator(false);

                if (mContent == null && mContentData == null) {
                    mContentDetailView.showFailureMessage(R.string.error_timeout);
                    mContentDetailView.handleMissingContent();
                }
            }
        });
    }

    private void manageDownloadAndPlay() {
        ContentImportResponse response = mSyncContentService.getImportStatus(mContentData.getIdentifier()).getResult();
        if (response.getStatus().getValue() == ContentImportStatus.DOWNLOAD_STARTED.getValue() || response.getStatus().getValue() == ContentImportStatus.ENQUEUED_FOR_DOWNLOAD.getValue()) {
            mContentDetailView.hideDownload();
            mContentDetailView.hideDelete();
            mContentDetailView.hideUpdate();
            mContentDetailView.showDownloadProgress();
            mContentDetailView.showDownloadProgressText();
            mContentDetailView.setDownloadProgressText(mDownloadingString);
            mIsDownloading = true;
        } else {
            if (mContent != null && mContent.isAvailableLocally()) {
                if (mIsDownloading) {
                    mIsDownloading = false;
                    mIsContentImported = true;
                    mImportResponse = new ContentImportResponse(mContent.getIdentifier(), ContentImportStatus.IMPORT_COMPLETED);
                }
                mContentDetailView.hideDownload();
                mContentDetailView.hideDownloadProgress();
                mContentDetailView.hideDownloadProgressText();
                if (mContent.isUpdateAvailable()) {
                    mContentDetailView.showUpdate();
                }
                showPlayAndDelete();
            } else {
                mContentDetailView.hideDelete();
                mContentDetailView.hidePlay();
                mContentDetailView.showDownload();
            }
        }
    }

    private void showPlayAndDelete() {
        if (ContentUtil.isCollectionOrTextBook(mContent.getContentType())) {
            if (mIsParentTextbookOrCollection) {
                //do not show open or delete
            } else {
                mContentDetailView.showOpen();
                mContentDetailView.showDelete();
            }
        } else {
            mContentDetailView.showPlay();
            mContentDetailView.showDelete();
        }
    }

    private void setAveragenTotalRatings() {
        if (mContentData != null) {
            String totalRating = mContentData.getTotalRatings();

            if (!StringUtil.isNullOrEmpty(totalRating)) {
                int totRating = Math.round(Float.valueOf(totalRating));
                if (totRating > 0) {
                    mContentDetailView.showUserIcon();
                    mContentDetailView.showUserRating(String.valueOf(totRating));
                }
            }

            String averageRating = mContentData.getAverageRating();
            if (!StringUtil.isNullOrEmpty(averageRating)) {
                float avgRating = Float.valueOf(averageRating);
                if (avgRating > 0) {
                    DecimalFormat numberFormat = new DecimalFormat("#.0");
                    mContentDetailView.showAverageRating(numberFormat.format(avgRating));
                    mContentDetailView.showAverageRatingBar(Float.valueOf(averageRating));
                }
            }
        }
    }

    private void setContentIcon() {
        String appIcon = mContentData.getAppIcon();
        if (appIcon != null) {
            if (Util.isValidURL(appIcon)) {
                mContentDetailView.showIcon(appIcon);
            } else {
                if (mContent != null && !StringUtil.isNullOrEmpty(mContent.getBasePath())) {
                    mContentDetailView.showLocalIcon(new File(mContent.getBasePath() + "/" + appIcon));
                }
            }
        } else {
            mContentDetailView.showDefaultIcon(R.drawable.ic_default_book);
        }
    }

    @Override
    public void initDownload() {
        String size = computeSize();
        if (size != null) {
//            if (FileHandler.isDeviceMemoryAvailable(mContext, new BigDecimal(size).longValue())) {
//                download();
//            } else {
//                // Insufficient storage error.
//                mContentDetailView.showFailureMessage(R.string.error_import_insufficient_memory);
//            }
            boolean isExternalStorage = PreferenceUtil.getPreferenceWrapper()
                    .getBoolean(PreferenceKey.KEY_SET_EXTERNAL_STORAGE_DEFAULT, false);

            if (!isExternalStorage && DeviceUtility.isDeviceMemoryLow()) {
                mContentDetailView.showDeviceMemoryLowDialog();
            } else if (isExternalStorage && DeviceUtility.isSdCardMemoryLow()) {
                mContentDetailView.showSdcardMemoryLowDialog();
            } else {
                download();
            }
        } else {
            download();
        }
    }

    public void download() {
        mContentDetailView.hidePlay();
        mContentDetailView.hideDownload();
        mContentDetailView.hideDelete();
        mContentDetailView.hideUpdate();
        mContentDetailView.showDownloadProgress();
        mContentDetailView.showDownloadProgressText();
        mContentDetailView.setDownloadProgressText(mDownloadingString);

        List<String> identifierList = new ArrayList<>();
        identifierList.add(mContentData.getIdentifier());

        ContentImportRequest.Builder builder = new ContentImportRequest.Builder();
        ContentImport contentImport = new ContentImport(mContentData.getIdentifier(), (mIsFromTextBookOrCollection || mIsCanvasDeeplink), FileHandler.getDefaultStoragePath(mContext));
        if (!mIsSpineAvailable) {
            contentImport.setCorrelationData(PreferenceUtil.getCoRelationList());
        }
        builder.add(contentImport);
        mIsDownloading = true;
        ContentUtil.addDownloadQueueItem(mContentData.getIdentifier(), mContentData.getName(),
                mContentSize, null, null, 0);

        mContentService.importContent(builder.build(), new IResponseHandler<List<ContentImportResponse>>() {
            @Override
            public void onSuccess(GenieResponse<List<ContentImportResponse>> genieResponse) {

            }

            @Override
            public void onError(GenieResponse<List<ContentImportResponse>> genieResponse) {

            }
        });
    }

    private void manageDownloadButtonVisibility(boolean isDownloading) {
        if (isDownloading) {
            mContentDetailView.hideDownload();
            mContentDetailView.hideDelete();
            mContentDetailView.hideUpdate();
            mContentDetailView.showDownloadProgress();
            mContentDetailView.showDownloadProgressText();
        } else {
            if (mContent != null && mContent.isAvailableLocally()) {
                if (mContent.isUpdateAvailable()) {
                    mContentDetailView.showUpdate();
                }
                showPlayAndDelete();
            } else {
                mContentDetailView.showDownload();
            }
            mContentDetailView.hideDownloadProgress();
            mContentDetailView.hideDownloadProgressText();
        }
    }

    @Override
    public void bindView(BaseView view, Context context) {
        mContentDetailView = (ContentDetailContract.View) view;
        mContext = context;
    }

    @Override
    public void unbindView() {
        mContentDetailView = null;
        mContext = null;
    }
}
