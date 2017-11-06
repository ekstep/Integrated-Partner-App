package org.ekstep.genie.ui.landing.home.download;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.ekstep.genie.R;
import org.ekstep.genie.customview.EkStepCustomTextView;
import org.ekstep.genie.customview.EkstepCircularProgressBar;
import org.ekstep.genie.model.DownloadQueueItem;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.Util;
import org.ekstep.genieservices.commons.utils.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DownloadQueueAdapter
 *
 * @author Indraja Machani  on 16/8/17.
 */

public class DownloadQueueAdapter extends RecyclerView.Adapter<DownloadQueueAdapter.ViewHolder> {
    // TODO 21/9/2017 uncomment all the commented code once after merging DownloadQueue branch in Genie SDK

    private static final String TAG = DownloadQueueAdapter.class.getSimpleName();
    private final Context mContext;
    private List<DownloadQueueItem> mDownloadQueueItemList;
    private DownloadQueueContract.Presenter mPresenter;
    private Map<String, Integer> mIdentifiersMap;

    private View.OnClickListener cancelDownloadListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            WrapperDownloadQueueItem wrapperQueueItem = (WrapperDownloadQueueItem) view.getTag();
            mPresenter.cancelDownload(wrapperQueueItem.getQueueItem());
        }
    };

    public DownloadQueueAdapter(Context context, List<DownloadQueueItem> downloadQueueItemList, DownloadQueueContract.Presenter presenter) {
        mContext = context;
        mDownloadQueueItemList = downloadQueueItemList;
        mPresenter = presenter;
        mIdentifiersMap = new HashMap<>();
    }

    @Override
    public DownloadQueueAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_download_queue_item, parent, false);
        return new DownloadQueueAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            // Perform a full update
            onBindViewHolder(viewHolder, position);
        } else {
            // Perform a partial update of view holder
            updateProgressbar(viewHolder, position);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DownloadQueueItem downloadQueueItem = mDownloadQueueItemList.get(position);
        setDownloadQueueItem(holder, downloadQueueItem, position);
    }

    private void setDownloadQueueItem(ViewHolder viewHolder, DownloadQueueItem downloadQueueItem, int position) {
        mIdentifiersMap.put(downloadQueueItem.getIdentifier(), position);
        WrapperDownloadQueueItem wrapperQueueItem = new WrapperDownloadQueueItem(position, downloadQueueItem);
        viewHolder.vhRelativeLayoutDownload.setTag(wrapperQueueItem);
        viewHolder.vhRelativeLayoutDownload.setOnClickListener(cancelDownloadListener);
        if (position % 2 == 0) {
            viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dialog_queue_item_grey_bg_color));
        } else {
            viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        }
        viewHolder.vhTvTitle.setText(downloadQueueItem.getName());
        setProgressBarVisibility(viewHolder, downloadQueueItem.getProgress());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        if (downloadQueueItem.getParentIdentifier() == null) {
            int visibility = downloadQueueItem.getSize() == null ? View.INVISIBLE : View.VISIBLE;
            setDownloadSizeVisibility(viewHolder, visibility);
            layoutParams.setMargins(Util.getPixelValue(mContext,
                    mContext.getResources().getDimension(R.dimen.spacing_small)), 0, 0, 0);
        } else {
            setDownloadSizeVisibility(viewHolder, View.VISIBLE);
            layoutParams.setMargins(Util.getPixelValue(mContext,
                    mContext.getResources().getDimension(R.dimen.spacing_normal)), 0, 0, 0);
        }
        viewHolder.vhTvTitle.setLayoutParams(layoutParams);
        setContentSize(viewHolder, downloadQueueItem);
    }

    private void setProgressBarVisibility(ViewHolder viewHolder, String progressValue) {
        if (!StringUtil.isNullOrEmpty(progressValue)) {
            if (progressValue.equals(Constant.DOWNLOAD_QUEUE_ATTR_PB_VALUE_ZERO)) {
                viewHolder.vhProgressBarDownload.setVisibility(View.INVISIBLE);
            } else {
                viewHolder.vhProgressBarDownload.setVisibility(View.VISIBLE);
                viewHolder.vhProgressBarDownload.setProgress(Float.valueOf(progressValue));
            }
        } else {
            viewHolder.vhProgressBarDownload.setVisibility(View.INVISIBLE);
        }
    }

    private void updateProgressbar(ViewHolder viewHolder, int position) {
        String progressValue = mDownloadQueueItemList.get(position).getProgress();
        if (!StringUtil.isNullOrEmpty(progressValue)) {
            viewHolder.vhProgressBarDownload.setVisibility(View.VISIBLE);
            viewHolder.vhProgressBarDownload.setProgress(Float.valueOf(progressValue));
            setContentSize(viewHolder, mDownloadQueueItemList.get(position));
        }
    }

    private void setContentSize(ViewHolder viewHolder, DownloadQueueItem downloadQueueItem) {
        String progressValue = downloadQueueItem.getProgress();
        String totalContentSize = downloadQueueItem.getSize();
        if (!StringUtil.isNullOrEmpty(progressValue) && Float.valueOf(progressValue) != -1) {
            float totalContentSizeFloat = Float.valueOf(totalContentSize.substring(0, totalContentSize.indexOf("MB") - 1));// * 1000000;
            String currentContentSizeInMB = Util.humanReadableByteCount((long) (totalContentSizeFloat * (Float.valueOf(progressValue) / 100)), true);
            viewHolder.vhTvDownloadingSize.setText(currentContentSizeInMB.
                    substring(0, currentContentSizeInMB.indexOf("B") - 1) + "/" + totalContentSize);
        } else {
            viewHolder.vhTvDownloadingSize.setText("0/" + totalContentSize);
        }
    }

    private void setDownloadSizeVisibility(ViewHolder viewHolder, int visibility) {
        viewHolder.vhTvDownloadingSize.setVisibility(visibility);
        viewHolder.vhRelativeLayoutDownload.setVisibility(visibility);
    }

    public void refresh(List<DownloadQueueItem> downloadQueueItemList) {
        mDownloadQueueItemList = downloadQueueItemList;
        notifyDataSetChanged();
    }

    public void refresh(DownloadQueueItem downloadQueueItem, int position) {
        if (downloadQueueItem != null) {
            int itemIndex = mDownloadQueueItemList.indexOf(downloadQueueItem);
            if (itemIndex != -1) {
                mDownloadQueueItemList.set(itemIndex, downloadQueueItem);
                notifyItemRangeChanged(position, 2, downloadQueueItem);
            }
        }
    }

    public Map<String, Integer> getIdentifiers() {
        return mIdentifiersMap;
    }

    public DownloadQueueItem getCurrentDownloadQueueItem(String identifier) {
        DownloadQueueItem currentDownloadRequest = null;
        for (int i = 0; i < mDownloadQueueItemList.size(); i++) {
            DownloadQueueItem downloadQueueItem = mDownloadQueueItemList.get(i);
            if (downloadQueueItem.getIdentifier().equals(identifier)) {
                currentDownloadRequest = downloadQueueItem;
            }
        }
        return currentDownloadRequest;
    }

    @Override
    public int getItemCount() {
        return mDownloadQueueItemList.size();
    }

    public DownloadQueueItem getItem(int position) {
        return mDownloadQueueItemList.get(position);
    }

    /**
     * View Holder for each section
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        private EkStepCustomTextView vhTvTitle;
        private EkStepCustomTextView vhTvDownloadingSize;
        private RelativeLayout vhRelativeLayoutDownload;
        private EkstepCircularProgressBar vhProgressBarDownload;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            vhTvTitle = (EkStepCustomTextView) itemView.findViewById(R.id.tv_title);
            vhTvDownloadingSize = (EkStepCustomTextView) itemView.findViewById(R.id.tv_downloading_size);
            vhRelativeLayoutDownload = (RelativeLayout) itemView.findViewById(R.id.rl_download);
            vhProgressBarDownload = (EkstepCircularProgressBar) itemView.findViewById(R.id.pb_download);
        }
    }

    private class WrapperDownloadQueueItem {
        private int position;
        private DownloadQueueItem downloadQueueItem;

        WrapperDownloadQueueItem(int position, DownloadQueueItem downloadQueueItem) {
            this.position = position;
            this.downloadQueueItem = downloadQueueItem;
        }

        public DownloadQueueItem getQueueItem() {
            return downloadQueueItem;
        }

        public int getPosition() {
            return position;
        }
    }
}