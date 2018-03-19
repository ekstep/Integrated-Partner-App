package org.ekstep.genie.ui.collection;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.widget.ProgressBar;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.telemetry.EnvironmentId;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryConstant;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.telemetry.enums.EntityType;
import org.ekstep.genie.telemetry.enums.ImpressionType;
import org.ekstep.genie.telemetry.enums.ObjectType;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.DeviceUtility;
import org.ekstep.genie.util.FileHandler;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.NetworkUtil;
import org.ekstep.genie.util.PlayerUtil;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.geniesdk.ContentUtil;
import org.ekstep.genie.util.preference.PreferenceKey;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.IContentService;
import org.ekstep.genieservices.async.ContentService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.ChildContentRequest;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.ContentData;
import org.ekstep.genieservices.commons.bean.ContentDelete;
import org.ekstep.genieservices.commons.bean.ContentDeleteRequest;
import org.ekstep.genieservices.commons.bean.ContentDeleteResponse;
import org.ekstep.genieservices.commons.bean.ContentDetailsRequest;
import org.ekstep.genieservices.commons.bean.ContentImport;
import org.ekstep.genieservices.commons.bean.ContentImportRequest;
import org.ekstep.genieservices.commons.bean.ContentImportResponse;
import org.ekstep.genieservices.commons.bean.CorrelationData;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.HierarchyInfo;
import org.ekstep.genieservices.commons.bean.enums.ContentImportStatus;
import org.ekstep.genieservices.commons.utils.StringUtil;
import org.ekstep.genieservices.eventbus.EventBus;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 8/2/17.
 *
 * @author shriharsh
 */
public class CollectionPresenter implements CollectionContract.Presenter {

    private static final String TAG = CollectionPresenter.class.getSimpleName();
    public List<String> mLocallyAvailableContentList = new ArrayList<>();
    private Context mContext;
    private CollectionContract.View mCollectionView;
    private ContentData mContentData;
    private Content mContent;
    private boolean mIsFromDownloadsScreen;
    private List<Content> mContentList = new ArrayList<>();
    private List<String> mIdentifierList;
    private boolean mIsFirstTime = true;
    private ContentService mContentService = null;
    private IContentService mSyncContentService = null;
    private boolean mIsDownloading;
    private boolean mIsContentDeleted;
    private String mIdentifier;
    private long mAvailableSizeLeft;

    public CollectionPresenter() {
        mContentService = CoreApplication.getGenieAsyncService().getContentService();
        mSyncContentService = CoreApplication.getGenieSdkInstance().getContentService();
        mIdentifierList = new ArrayList<>();
    }


    @Override
    public void fetchBundleExtras(Bundle arguments) {
        mContent = (Content) arguments.getSerializable(Constant.BundleKey.BUNDLE_KEY_CONTENT);
        mContentData = mContent.getContentData();
        mIsFromDownloadsScreen = arguments.getBoolean(Constant.BundleKey.BUNDLE_KEY_IS_FROM_DOWNLOADS, false);

        // Set content access state to opened
        ContentUtil.addContentAccess(mContent);

        showCollectionInfo();

        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildStartEvent(mContext, null, TelemetryConstant.COLLECTION,
                TelemetryConstant.MODE_PLAY, mContentData.getIdentifier(), mContentData.getContentType(), mContentData.getPkgVersion()));
    }

    @Override
    public void sendTelemetryEndEvent() {
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildEndEvent(TelemetryConstant.COLLECTION, TelemetryConstant.MODE_PLAY,
                null,mContentData.getIdentifier(), mContentData.getContentType(), mContentData.getPkgVersion()));
    }

    @Override
    public void showCollectionInfo() {

        setCollectionContentIcon();

        mCollectionView.setCollectionName(mContentData.getName());

        if (mContentData.getGradeLevel() != null && mContentData.getGradeLevel().size() > 0) {
            mCollectionView.showGrade(mContentData.getGradeLevel().get(0));
        } else {
            mCollectionView.hideGrade();
            mCollectionView.hideGradeDivider();
        }

        if (mContentData.getLanguage() != null && mContentData.getLanguage().size() > 0) {
            mCollectionView.showLanguage(mContentData.getLanguage().get(0));
        } else {
            mCollectionView.hideLanguage();
        }
    }

    @Override
    public void showChildContents(final boolean refreshLayout) {

        if (refreshLayout) {
            ChildContentRequest.Builder contentRequest = new ChildContentRequest.Builder()
                    .forContent(mContentData.getIdentifier())
                    .hierarchyInfo(mContent.getHierarchyInfo());
            mContentService.getChildContents(contentRequest.build(), new IResponseHandler<Content>() {
                @Override
                public void onSuccess(GenieResponse<Content> genieResponse) {
                    mContentList = genieResponse.getResult().getChildren();
                    handleDownloadVisibilityByContentSize(mContentList);
                    mCollectionView.showCollectionShelf(mContentList, mIsFromDownloadsScreen);
                }

                @Override
                public void onError(GenieResponse<Content> genieResponse) {
                    LogUtil.e(TAG, "error: " + genieResponse.getErrorMessages().get(0));
                }
            });
        } else {
            handleDownloadVisibilityByContentSize(mContentList);
            mCollectionView.showCollectionShelf(mContentList, mIsFromDownloadsScreen);
        }
    }

    @Override
    public void handleDownloadVisibilityByContentSize(List<Content> contentList) {
        mIsDownloading = false;
        mAvailableSizeLeft = 0;
        mIdentifierList = new ArrayList<>();
        calculateLocalContentCount(contentList);
    }

    /**
     * Calculate total local content count.
     */
    @Override
    public void calculateLocalContentCount(List<Content> contentList) {
        if (contentList != null) {
            int localContentCount = 0;

            for (Content content : contentList) {
                if (content.getChildren() != null) {
                    calculateLocalContentCount(content.getChildren());
                }

                if (!content.isAvailableLocally()) {
                    String identifier = content.getIdentifier();
                    mIdentifierList.add(identifier);

                    checkIfDownloadInProgress(identifier);
                    ContentData contentData = content.getContentData();
                    String contentSize = ContentUtil.getContentSize(contentData);
                    if (contentData != null && !StringUtil.isNullOrEmpty(contentSize)) {
                        long size = new BigDecimal(contentSize).longValue();
                        mAvailableSizeLeft += size;
                    }
                } else {
                    localContentCount++;
                }
            }

            if (mAvailableSizeLeft > 0) {
                if (!mIsDownloading) {
                    mCollectionView.showDownloadLayout();
                    mCollectionView.showDownloadAllLayout();
                    mCollectionView.hideDownloadingLayout();
                    mCollectionView.setCollectionSize(String.valueOf(Util.humanReadableByteCount(mAvailableSizeLeft, true)));
                } else {
                    mCollectionView.showDownloadLayout();
                    mCollectionView.hideDownloadAllLayout();
                    mCollectionView.showDownloadingLayout();
                }
            } else {
                mCollectionView.hideDownloadLayout();
            }

            // Only generate GE_INTERACT for the first time when fragment is initiated.
            if (mIsFirstTime) {
                mIsFirstTime = false;
                //Generate GE_INTERACT_EVENT
                generateInteractEvent(mContentData.getIdentifier(), localContentCount, mContentList.size(), new ArrayList<CorrelationData>());
            }
        }
    }

    @Override
    public void checkIfDownloadInProgress(String identifier) {
        if (!mIsDownloading) {
            ContentImportResponse response = mSyncContentService.getImportStatus(identifier).getResult();
            if (response.getStatus().getValue() == ContentImportStatus.DOWNLOAD_STARTED.getValue() || response.getStatus().getValue() == ContentImportStatus.ENQUEUED_FOR_DOWNLOAD.getValue()) {
                mIsDownloading = true;
            }
        }
    }

    @Override
    public void setCollectionContentIcon() {
        String appIcon = mContentData.getAppIcon();
        if (appIcon != null) {
            if (Util.isValidURL(appIcon)) {
                mCollectionView.showIcon(appIcon);
            } else {
                mCollectionView.showLocalIcon(new File(mContent.getBasePath() + "/" + appIcon));
            }
        } else {
            mCollectionView.showDefaultIcon(R.drawable.ic_default_book);
        }
    }

    @Override
    public void postRequiredStickyEvents() {
        if (mIsContentDeleted) {
            ContentImportResponse deletedContentResponse = new ContentImportResponse(mIdentifier, ContentImportStatus.IMPORT_COMPLETED);
            EventBus.postStickyEvent(deletedContentResponse);
        }
    }

    @Override
    public void handleBackButtonClick() {
        postRequiredStickyEvents();
        sendTelemetryEndEvent();
        mCollectionView.finish();
    }

    @Override
    public void handleDownloadAllClick() {

        if (NetworkUtil.isNetworkAvailable(mContext)) {

            boolean isExternalStorage = PreferenceUtil.getPreferenceWrapper()
                    .getBoolean(PreferenceKey.KEY_SET_EXTERNAL_STORAGE_DEFAULT, false);

            if (!isExternalStorage && DeviceUtility.isDeviceMemoryLow()) {
                mCollectionView.showDeviceMemoryLowDialog();
            } else if (isExternalStorage && DeviceUtility.isSdCardMemoryLow()) {
                mCollectionView.showSdcardMemoryLowDialog();
            } else {

                mCollectionView.hideDownloadAllLayout();
                mCollectionView.showDownloadingLayout();

                ContentImportRequest.Builder builder = new ContentImportRequest.Builder();
                for (String identifier : mIdentifierList) {
                    ContentImport contentImport = new ContentImport(identifier, true, FileHandler.getDefaultStoragePath(mContext).toString());
                    contentImport.setCorrelationData(PreferenceUtil.getCoRelationList());
                    builder.add(contentImport);
                }
                createDownloadQueueItemList(mContentList);
                mContentService.importContent(builder.build(), new IResponseHandler<List<ContentImportResponse>>() {
                    @Override
                    public void onSuccess(GenieResponse<List<ContentImportResponse>> genieResponse) {

                    }

                    @Override
                    public void onError(GenieResponse<List<ContentImportResponse>> genieResponse) {

                    }
                });
            }
        } else {
            mCollectionView.showNoNetworkError();
            mCollectionView.hideDownloadingLayout();
            mCollectionView.showDownloadAllLayout();
        }
    }

    private void createDownloadQueueItemList(List<Content> contentList) {
        if (contentList != null) {
            for (Content content : contentList) {
                if (content.getChildren() != null) {
                    createDownloadQueueItemList(content.getChildren());
                }
                if (!content.isAvailableLocally()) {
                    String contentSize = null;
                    ContentData contentData = content.getContentData();
                    if (contentData != null && !StringUtil.isNullOrEmpty(contentData.getSize())) {
                        long size = new BigDecimal(contentData.getSize()).longValue();
                        contentSize = String.valueOf(Util.humanReadableByteCount(size, true));
                    }
                    ContentUtil.addDownloadQueueItem(content.getIdentifier(), content.getContentData().getName(),
                            contentSize, mContentData.getIdentifier(), mContentData.getName(), 0);
                }
            }
        }
    }

    @Override
    public void showCollectionDetails() {
        mCollectionView.showContentDetails(mContent, mContent.getHierarchyInfo(), mIsFromDownloadsScreen, true, true);
    }

    @Override
    public void handleMoreClick(final Content content) {
        ContentDetailsRequest.Builder detailsRequest = new ContentDetailsRequest.Builder().forContent(content.getIdentifier()).withContentAccess().withFeedback();
        mContentService.getContentDetails(detailsRequest.build(), new IResponseHandler<Content>() {
            @Override
            public void onSuccess(GenieResponse<Content> genieResponse) {
                Content selectedContent = genieResponse.getResult();
                selectedContent.setHierarchyInfo(content.getHierarchyInfo());
                mCollectionView.showMoreDialog(selectedContent);
            }

            @Override
            public void onError(GenieResponse<Content> genieResponse) {
            }
        });

    }

    @Override
    public void handleContentClick(Content content) {
        if (content.isAvailableLocally()) {
            PlayerUtil.startContent(mContext, content, TelemetryStageId.CONTENT_DETAIL, mIsFromDownloadsScreen, true);
        } else {
            mCollectionView.showContentDetails(content, content.getHierarchyInfo(), mIsFromDownloadsScreen, true, false);
        }
    }

    @Override
    public void handleDeleteClick(Content content) {
        mCollectionView.showDeleteConfirmationLayout(content.getIdentifier(), content.getReferenceCount());
    }

    @Override
    public void handleFeedbackClick(Content content) {
        mCollectionView.showFeedbackDialog(content.getIdentifier(), ContentUtil.getPreviousRating(content.getContentFeedback()));
    }

    @Override
    public void handleViewDetailsClick(Content content) {
        mCollectionView.showContentDetails(content, (ArrayList<HierarchyInfo>) content.getHierarchyInfo(), mIsFromDownloadsScreen, true, false);
    }

    @Override
    public void handleProgressClick(Content content) {
        mCollectionView.showProgressReportActivity(content);
    }

    @Override
    public void colorProgressBar(ProgressBar progressBar) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable wrapDrawable = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
            DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(mContext, android.R.color.white));
            progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
        } else {
            progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(mContext, android.R.color.white), PorterDuff.Mode.SRC_IN);
        }
    }

    @Override
    public void manageDeleteContentSuccess(String identifier, boolean isAccessedElseWhere) {
        mLocallyAvailableContentList.remove(identifier);
        if (isAccessedElseWhere) {
            mIsContentDeleted = true;
        }
        mIdentifier = identifier;

        if (mCollectionView == null) {
            return;
        }

        if (mContentList != null && mContentList.size() > 0) {
            mCollectionView.refreshAdapter(mContentList, mIsFromDownloadsScreen);
            mCollectionView.showCustomToast(mContext.getResources().getString(R.string.msg_content_deletion_sucessfull));
        }

        showChildContents(true);
    }

    @Override
    public void manageImportSuccess(ContentImportResponse importResponse) {
        if (mIdentifierList != null && mIdentifierList.contains(importResponse.getIdentifier())) {
            List<String> identifierList = new ArrayList<>();
            identifierList.add(importResponse.getIdentifier());
            mLocallyAvailableContentList.addAll(identifierList);
            identifierList.clear();
            if (mContentList != null && mContentList.size() > 0) {
                mCollectionView.refreshAdapter(mContentList, mIsFromDownloadsScreen);
                handleDownloadVisibilityByContentSize(mContentList);
            }
        }
    }

    @Override
    public void deleteContent(final String identifier, final boolean isAccessedElseWhere) {
        ContentDeleteRequest.Builder builder = new ContentDeleteRequest.Builder();
        builder.add(new ContentDelete(identifier, true));
        mContentService.deleteContent(builder.build(), new IResponseHandler<List<ContentDeleteResponse>>() {
            @Override
            public void onSuccess(GenieResponse<List<ContentDeleteResponse>> genieResponse) {
                manageDeleteContentSuccess(identifier, isAccessedElseWhere);
            }

            @Override
            public void onError(GenieResponse<List<ContentDeleteResponse>> genieResponse) {
                LogUtil.e(TAG, "error deleting content");
            }
        });
    }

    @Override
    public List<Content> getModifiedContentList(List<Content> contentList) {
        List<Content> modifiedContentList = new ArrayList<>();
        if (contentList != null && contentList.size() > 0) {
            for (Content content : contentList) {
                if (getLocallyAvailableCollectionContent() != null && getLocallyAvailableCollectionContent().size() > 0) {
                    if (getLocallyAvailableCollectionContent().contains(content.getIdentifier())) {
                        content.setAvailableLocally(true);
                    }
                }
                modifiedContentList.add(content);
            }
        }
        return modifiedContentList;
    }

    @Override
    public List<String> getLocallyAvailableCollectionContent() {
        return mLocallyAvailableContentList;
    }

    @Override
    public void generateInteractEvent(String identifier, int localCount, int totalCount, List<CorrelationData> cData) {

        Map<String, Object> params = new HashMap<>();
        params.put(TelemetryConstant.CONTENT_LOCAL_COUNT, "" + localCount);
        params.put(TelemetryConstant.CONTENT_TOTAL_COUNT, "" + totalCount);
//        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.SHOW, TelemetryStageId.COLLECTION_HOME, EntityType.CONTENT_ID, identifier, eksMap, cData));
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildImpressionEvent(EnvironmentId.HOME, TelemetryStageId.COLLECTION_HOME, ImpressionType.VIEW, EntityType.CONTENT_ID, identifier, mContentData.getPkgVersion(), ObjectType.CONTENT, cData));
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildLogEvent(EnvironmentId.HOME, TelemetryStageId.COLLECTION_HOME, ImpressionType.VIEW, TelemetryStageId.COLLECTION_HOME, params));
    }

    @Override
    public void bindView(BaseView view, Context context) {
        mContext = context;
        mCollectionView = (CollectionContract.View) view;
    }

    @Override
    public void unbindView() {
        mContext = null;
        mCollectionView = null;
    }
}
