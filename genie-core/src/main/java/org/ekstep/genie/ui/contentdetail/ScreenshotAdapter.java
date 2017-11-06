package org.ekstep.genie.ui.contentdetail;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.ekstep.genie.R;
import org.ekstep.genie.util.GlideImageUtil;
import org.ekstep.genieservices.commons.utils.StringUtil;

import java.util.List;

/**
 * Created by Indraja Machani on 7/17/2017.
 */

public class ScreenshotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = ScreenshotAdapter.class.getSimpleName();
    private final Context mContext;
    private List<String> mScreenshotList;
    private ContentDetailContract.Presenter mPresenter;

    public ScreenshotAdapter(Context context, List<String> screenshotList, ContentDetailContract.Presenter presenter) {
        mContext = context;
        mScreenshotList = screenshotList;
        mPresenter = presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View screenshotViewHolder = inflater.inflate(R.layout.layout_screenshot_adapter_item, parent, false);
        viewHolder = new ScreenshotViewHolder(screenshotViewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final String screenshotUrl = mScreenshotList.get(position);
        final ScreenshotViewHolder screenshotViewHolder = (ScreenshotViewHolder) holder;

        if (!StringUtil.isNullOrEmpty(screenshotUrl)) {
            GlideImageUtil.loadRectangularImage(mContext, screenshotUrl, screenshotViewHolder.vhImageViewScreenshot);
        } else {
            // TODO 18/07/17 uncomment the below code later once the default image resource is given and remove the color filter
//            GlideImageUtil.loadImageUrl(mContext, screenshotViewHolder.vhImageViewScreenshot);
            screenshotViewHolder.vhImageViewScreenshot.setColorFilter(mContext.getResources().getColor(R.color.color_primary_light), PorterDuff.Mode.SRC_ATOP);
        }
    }

    @Override
    public int getItemCount() {
        return mScreenshotList != null ? mScreenshotList.size() : 0;
    }

    public String getItem(int position) {
        return mScreenshotList.get(position);
    }

    /**
     * View Holder for each screenshot
     */
    public class ScreenshotViewHolder extends RecyclerView.ViewHolder {
        public View view;
        private ImageView vhImageViewScreenshot;

        public ScreenshotViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemView;
            vhImageViewScreenshot = (ImageView) itemLayoutView.findViewById(R.id.iv_screenshot);
        }
    }
}

