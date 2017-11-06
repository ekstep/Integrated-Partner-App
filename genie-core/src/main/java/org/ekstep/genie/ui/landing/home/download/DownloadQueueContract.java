package org.ekstep.genie.ui.landing.home.download;

import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.model.DownloadQueueItem;
import org.ekstep.genieservices.commons.bean.ContentImportResponse;
import org.ekstep.genieservices.commons.bean.DownloadProgress;

import java.util.List;
import java.util.Map;

/**
 * Created by Indraja Machani on 8/10/2017.
 */

public class DownloadQueueContract {
    public interface View extends BaseView {
        void setDownloadQueueAdapter(List<DownloadQueueItem> downloadQueueItemList);

        void refreshAdapter(List<DownloadQueueItem> downloadQueueItemList);

        void refreshAdapter(DownloadQueueItem downloadQueueItem, int position);

        Map<String, Integer> getDownloadRequestIdentifiers();

        DownloadQueueItem getCurrentDownloadQueueItem(String identifier);

        void setDownloadPauseText();

        void setDownloadResumeText();
    }

    public interface Presenter {
        void fetchDownloadQueue();

        void refreshAdapterAndUpdatePB(DownloadProgress downloadProgress);

        void handlePauseDownload();

        void manageImportResponse(ContentImportResponse contentImportResponse);

        void cancelDownload(DownloadQueueItem downloadQueueItem);
    }
}
