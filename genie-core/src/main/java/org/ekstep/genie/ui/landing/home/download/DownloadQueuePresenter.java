package org.ekstep.genie.ui.landing.home.download;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.model.DownloadQueueItem;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.geniesdk.ContentUtil;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.IContentService;
import org.ekstep.genieservices.async.ContentService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.ContentImportResponse;
import org.ekstep.genieservices.commons.bean.DownloadProgress;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.enums.ContentImportStatus;
import org.ekstep.genieservices.commons.bean.enums.DownloadAction;
import org.ekstep.genieservices.commons.utils.CollectionUtil;
import org.ekstep.genieservices.commons.utils.GsonUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Indraja Machani on 8/18/2017.
 */

public class DownloadQueuePresenter implements DownloadQueueContract.Presenter {
    private DownloadQueueContract.View mDownloadQueueView;
    private ContentService mContentService = null;
    private List<DownloadQueueItem> mModifiedDownloadQueueItemList;
    private List<DownloadQueueItem> mDownloadQueueItemList;
    private boolean mIsPauseDownload = true;
    private IContentService mSyncContentService = null;

    public DownloadQueuePresenter(DownloadQueueContract.View view) {
        this.mDownloadQueueView = view;
        this.mSyncContentService = CoreApplication.getGenieSdkInstance().getContentService();
        this.mContentService = CoreApplication.getGenieAsyncService().getContentService();
    }

    @Override
    public void fetchDownloadQueue() {
        mDownloadQueueItemList = PreferenceUtil.getDownloadQueueItemList();
        mModifiedDownloadQueueItemList = getModifiedDownloadQueueItemList();
        mDownloadQueueView.setDownloadQueueAdapter(mModifiedDownloadQueueItemList);
        LogUtil.i("Download ReqList", "" + GsonUtil.toJson(mModifiedDownloadQueueItemList));
    }

    private List<DownloadQueueItem> getModifiedDownloadQueueItemList() {
        List<DownloadQueueItem> modifiedDownloadRequestList = new ArrayList<>();
        List<String> identifierList = new ArrayList<>();
        if (!CollectionUtil.isNullOrEmpty(mDownloadQueueItemList)) {
            int childCount = 0;
            for (int i = 0; i < mDownloadQueueItemList.size(); i++) {
                DownloadQueueItem queueItem = mDownloadQueueItemList.get(i);
                if (!ContentUtil.isContentImported(mSyncContentService, queueItem.getIdentifier())) {
                    childCount = childCount + 1;
                    String rootId = queueItem.getParentIdentifier() != null ? queueItem.getParentIdentifier() : queueItem.getIdentifier();
                    if (identifierList.contains(rootId)) {
                        int index = identifierList.indexOf(rootId);
                        if (modifiedDownloadRequestList.get(index).equals(new DownloadQueueItem(rootId))) {
                            modifiedDownloadRequestList.get(index).setChildCount(childCount);
                        }
                        if (index == identifierList.size() - 1) {
                            modifiedDownloadRequestList.add(queueItem);
                        } else {
                            DownloadQueueItem dummydownloadQueueItem = new DownloadQueueItem(identifierList.get(index + 1),
                                    queueItem.getParentName(), null, null, null, childCount);
                            int newIndex = modifiedDownloadRequestList.indexOf(dummydownloadQueueItem);
                            modifiedDownloadRequestList.add(newIndex, queueItem);
                        }
                    } else {
                        identifierList.add(rootId);
                        if (queueItem.getParentIdentifier() != null) {
                            DownloadQueueItem dummyQueueItem = new DownloadQueueItem(rootId, queueItem.getParentName(),
                                    null, null, null, childCount);
                            modifiedDownloadRequestList.add(dummyQueueItem);
                        }
                        modifiedDownloadRequestList.add(queueItem);
                    }
                }
            }
        }
        return modifiedDownloadRequestList;
    }

    @Override
    public void refreshAdapterAndUpdatePB(DownloadProgress progress) {
        if (progress != null) {
            Map<String, Integer> identifiers = mDownloadQueueView.getDownloadRequestIdentifiers();
            int position = -1;
            Iterator iterator = identifiers.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                if (entry.getKey().equals(progress.getIdentifier())) {
                    DownloadQueueItem currentDownloadRequest = mDownloadQueueView.getCurrentDownloadQueueItem(progress.getIdentifier());
                    position = (int) entry.getValue();
                    LogUtil.d("DownloadQuue ", "progress " + progress.getDownloadProgress());
                    currentDownloadRequest.setProgress(String.valueOf(progress.getDownloadProgress()));
                    mDownloadQueueView.refreshAdapter(currentDownloadRequest, position);
                    break;
                }
            }
        }
    }

    @Override
    public void handlePauseDownload() {
        if (mModifiedDownloadQueueItemList != null && !mModifiedDownloadQueueItemList.isEmpty()) {
            if (mIsPauseDownload) {
                setDownloadAction(DownloadAction.PAUSE);
            } else {
                setDownloadAction(DownloadAction.RESUME);
            }
        }
    }

    private void setDownloadAction(DownloadAction action) {
        mContentService.setDownloadAction(action, new IResponseHandler<Void>() {
            @Override
            public void onSuccess(GenieResponse<Void> genieResponse) {
                if (mIsPauseDownload) {
                    mIsPauseDownload = false;
                    mDownloadQueueView.setDownloadResumeText();
                } else {
                    mIsPauseDownload = true;
                    mDownloadQueueView.setDownloadPauseText();
                }
            }

            @Override
            public void onError(GenieResponse<Void> genieResponse) {

            }
        });
    }

    @Override
    public void manageImportResponse(ContentImportResponse contentImportResponse) {
        if (contentImportResponse.getStatus().getValue() == ContentImportStatus.IMPORT_COMPLETED.getValue()
                || contentImportResponse.getStatus().getValue() == ContentImportStatus.NOT_FOUND.getValue()) {
            Map<String, Integer> identifiers = mDownloadQueueView.getDownloadRequestIdentifiers();
            Iterator iterator = identifiers.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                if (entry.getKey().equals(contentImportResponse.getIdentifier())) {
                    DownloadQueueItem currentDownloadQueueItem = mDownloadQueueView.
                            getCurrentDownloadQueueItem(contentImportResponse.getIdentifier());
                    removeDownloadQueueItem(currentDownloadQueueItem);
                }
            }
        }
    }

    @Override
    public void cancelDownload(DownloadQueueItem downloadQueueItem) {
        mSyncContentService.cancelDownload(downloadQueueItem.getIdentifier());
        removeDownloadQueueItem(downloadQueueItem);
    }

    private void removeDownloadQueueItem(DownloadQueueItem downloadQueueItem) {
        PreferenceUtil.removeDownloadQueueItem(downloadQueueItem.getIdentifier());
        mModifiedDownloadQueueItemList.remove(downloadQueueItem);
        removeDummyParentItem(downloadQueueItem.getParentIdentifier());
        mDownloadQueueView.refreshAdapter(mModifiedDownloadQueueItemList);
    }

    private void removeDummyParentItem(String parentIdentifier) {
        if (parentIdentifier != null) {
            for (int i = 0; i < mModifiedDownloadQueueItemList.size(); i++) {
                if (parentIdentifier.equals(mModifiedDownloadQueueItemList.get(i).getIdentifier())) {
                    int childCount = mModifiedDownloadQueueItemList.get(i).getChildCount();
                    if (childCount == 1) {
                        mModifiedDownloadQueueItemList.remove(mModifiedDownloadQueueItemList.get(i));
                    } else {
                        childCount = childCount - 1;
                        mModifiedDownloadQueueItemList.get(i).setChildCount(childCount);
                    }
                    break;
                }
            }
        }
    }

}
