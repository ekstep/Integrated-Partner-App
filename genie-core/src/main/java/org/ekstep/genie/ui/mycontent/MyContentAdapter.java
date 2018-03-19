package org.ekstep.genie.ui.mycontent;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.model.SelectedContent;
import org.ekstep.genieservices.commons.bean.Content;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 3/16/2016.
 *
 * @author swayangjit_gwl
 */
public class MyContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext = null;
    private MyContentContract.Presenter mPresenter = null;
    private List<Content> mContentList = null;
    private List<SelectedContent> mSelectedContent;
    private boolean mShowSelector;


    public MyContentAdapter(Context context, MyContentContract.Presenter presenter) {
        this.mContext = context;
        this.mPresenter = presenter;
        this.mSelectedContent = new ArrayList<>();

    }

    public void setData(List<Content> contentList) {
        this.mContentList = contentList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View normalViewHolder = inflater.inflate(R.layout.layout_download_item, parent, false);
        viewHolder = new ContentViewHolder(normalViewHolder);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        Content content = mContentList.get(position);
        ContentViewHolder normalContentViewHolder = (ContentViewHolder) viewHolder;
        mPresenter.setContentToViews(new ContentWrapper(position, content), normalContentViewHolder, this);
    }


    public Content getItem(int index) {
        return mContentList.get(index);
    }

    @Override
    public int getItemCount() {
        return mContentList.size();
    }

    public void toggleSelected(SelectedContent selectedContent) {
        final boolean newState = !mSelectedContent.contains(new SelectedContent(selectedContent.getIdentifier()));

        if (newState) {
            mSelectedContent.add(selectedContent);
        } else {
            mSelectedContent.remove(selectedContent);
        }
        notifyDataSetChanged();
    }

    public void showSelctorLayer(boolean status) {
        mShowSelector = status;
        notifyDataSetChanged();
    }

    public void clearSelected() {
        mSelectedContent.clear();
    }

    public List<SelectedContent> getSelectedItems() {
        return mSelectedContent != null ? mSelectedContent : new ArrayList<SelectedContent>();
    }

    public int getSelectedCount() {
        return mSelectedContent.size();
    }

    public void refresh(List<Content> updatedList) {
        if (updatedList == null) {
            return;
        } else {
            mContentList.clear();
            mContentList.addAll(updatedList);
            notifyDataSetChanged();
        }


    }

    /**
     * View Holder for content
     */
    public class ContentViewHolder extends RecyclerView.ViewHolder {
        public ImageView vhImgContent;
        public ImageView vhImgContentLocation;
        public TextView vhTvContentName;
        public TextView vhTvContentType;
        public TextView vhTvContentSize;
        public TextView vhTvContentSizeMetric;
        public TextView vhTvContentDownloadedSince;
        public TextView vhTvContentDownloadedTimeAgo;
        public ImageView vhIvMore;
        public CheckBox vhCbContentCheckBox;
        public LinearLayout vhRlCompleteRow;
        public TextView vhTvContentOpen;
        public View vhImgOpenSeparator;

        public ContentViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            vhTvContentName = (TextView) itemLayoutView.findViewById(R.id.download_ectv_content_name);
            vhImgContent = (ImageView) itemLayoutView.findViewById(R.id.download_iv_content_icon);
            vhTvContentType = (TextView) itemLayoutView.findViewById(R.id.download_ectv_content_type);
            vhTvContentSize = (TextView) itemLayoutView.findViewById(R.id.download_ectv_content_size);
            vhTvContentSizeMetric = (TextView) itemLayoutView.findViewById(R.id.download_ectv_content_size_metric);
            vhTvContentDownloadedSince = (TextView) itemLayoutView.findViewById(R.id.download_ectv_content_since);
            vhTvContentDownloadedTimeAgo = (TextView) itemLayoutView.findViewById(R.id.download_ectv_content_times_ago);
            vhImgOpenSeparator = (View) itemLayoutView.findViewById(R.id.ev_open_separator);
            vhTvContentOpen = (TextView) itemLayoutView.findViewById(R.id.ectv_open_content);

            vhTvContentOpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.handleContentItemClick((ContentWrapper) view.getTag(R.id.ectv_open_content));
                }
            });

            vhIvMore = (ImageView) itemLayoutView.findViewById(R.id.download_iv_more);
            vhIvMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.showMoreDialog((Content) view.getTag());
                }
            });
            vhCbContentCheckBox = (CheckBox) itemLayoutView.findViewById(R.id.download_iv_check_box);
            vhCbContentCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.toggleContentIcon((Content) view.getTag());
                }
            });
            vhRlCompleteRow = (LinearLayout) itemLayoutView.findViewById(R.id.download_rl_row);
            vhRlCompleteRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.toggleContentIcon((Content) view.getTag());
                }
            });
        }
    }

    public class ContentWrapper {
        private int position;
        private Content content;

        public ContentWrapper(int position, Content content) {
            this.position = position;
            this.content = content;
        }

        public Content getContent() {
            return content;
        }

        public int getPosition() {
            return position;
        }
    }
}
