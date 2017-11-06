package org.ekstep.genie.ui.collection;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.model.enums.ContentType;
import org.ekstep.genie.ui.textbook.DownloadedTextbooksContract;
import org.ekstep.genie.util.GlideImageUtil;
import org.ekstep.genie.util.geniesdk.ContentUtil;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Collection adapter to handle contents of collection
 */
public class CollectionContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = CollectionContentAdapter.class.getSimpleName();

    private static final int COLLECTION_TYPE_VIEW = 2;
    private static final int TEXT_BOOK_TYPE_VIEW = 1;
    private static final int NORMAL_TYPE_VIEW = 0;

    private Context mContext = null;
    private List<Content> mContentList = null;
    private boolean isFromDownloadsScreen;
    //    private double mItemWidth;
    private BasePresenter mPresenter;

    public CollectionContentAdapter(Activity context, List<Content> contentList, boolean isFromDownloadsScreen, BasePresenter presenter) {
        this.mContext = context;
        this.mContentList = contentList;
        this.isFromDownloadsScreen = isFromDownloadsScreen;
        this.mPresenter=presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == TEXT_BOOK_TYPE_VIEW) {
            View textBookViewHolder = inflater.inflate(R.layout.layout_download_textbook, parent, false);
            viewHolder = new TextBookContentViewHolder(textBookViewHolder);
        } else if (viewType == COLLECTION_TYPE_VIEW) {
            View collectionViewHolder = inflater.inflate(R.layout.layout_download_collection, parent, false);
            viewHolder = new CollectionContentViewHolder(collectionViewHolder);
        } else {
            View normalViewHolder = inflater.inflate(R.layout.layout_download_normal, parent, false);
            viewHolder = new NormalContentViewHolder(normalViewHolder);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {

        Content content = mContentList.get(position);

        if (viewHolder.getItemViewType() == TEXT_BOOK_TYPE_VIEW) {
            TextBookContentViewHolder textBookContentViewHolder = (TextBookContentViewHolder) viewHolder;
            setTextBookContentToViews(content, textBookContentViewHolder);
        } else if (viewHolder.getItemViewType() == COLLECTION_TYPE_VIEW) {
            CollectionContentViewHolder collectionContentViewHolder = (CollectionContentViewHolder) viewHolder;

            setCollectionContentToViews(content, collectionContentViewHolder);
        } else {
            NormalContentViewHolder normalContentViewHolder = (NormalContentViewHolder) viewHolder;

            setNormalContentToViews(content, normalContentViewHolder);
        }
    }

    /**
     * Set Normal Game Related data to View
     *
     * @param content
     * @param normalContentViewHolder
     */
    private void setNormalContentToViews(Content content, NormalContentViewHolder normalContentViewHolder) {
        normalContentViewHolder.vhLayoutMain.setTag(content);
        normalContentViewHolder.vhtvContentName.setText(content.getContentData().getName());
        normalContentViewHolder.vhTvContentType.setText(R.string.label_all_content_type_activity);
        normalContentViewHolder.vhTvContentType.setTextColor(mContext.getResources().getColor(R.color.app_blue_theme_color));
        normalContentViewHolder.vhNewText.setVisibility(View.GONE);

        if (!isFromDownloadsScreen) {
            normalContentViewHolder.vhIbMore.setVisibility(View.INVISIBLE);
            if (content.isAvailableLocally()) {
                normalContentViewHolder.vhLayoutTick.setVisibility(View.VISIBLE);
            } else {
                normalContentViewHolder.vhLayoutTick.setVisibility(View.GONE);
            }
        } else {
            normalContentViewHolder.vhLayoutTick.setVisibility(View.GONE);
            if (content.isAvailableLocally()) {
                normalContentViewHolder.vhIbMore.setTag(content);
                normalContentViewHolder.vhIbMore.setVisibility(View.VISIBLE);
            } else {
                normalContentViewHolder.vhIbMore.setVisibility(View.INVISIBLE);
            }
        }

        if (!ContentUtil.isContentLive(content.getContentData().getStatus())) {
            if (ContentUtil.isContentExpired(content.getContentData().getExpires())) {
                normalContentViewHolder.vhLayoutContent.setBackgroundResource(R.drawable.ic_expired);
            } else {
                normalContentViewHolder.vhLayoutContent.setBackgroundResource(R.drawable.ic_draft);
            }
            normalContentViewHolder.vhLayoutMain.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
            normalContentViewHolder.vhtvContentName.setTextColor(mContext.getResources().getColor(android.R.color.white));
        } else {
//            normalContentViewHolder.vhLayoutMain.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            normalContentViewHolder.vhtvContentName.setTextColor(mContext.getResources().getColor(R.color.black1));
            normalContentViewHolder.vhLayoutContent.setBackgroundResource(R.drawable.ic_default_content);
        }

        if (!StringUtil.isNullOrEmpty(content.getContentData().getAppIcon())) {
            GlideImageUtil.loadImageUrl(mContext, content.getBasePath() + "/" + content.getContentData().getAppIcon(), normalContentViewHolder.vhImgContent);
        } else {
            GlideImageUtil.loadImageUrl(mContext, normalContentViewHolder.vhImgContent);
        }
    }

    /**
     * Set Collection content related data to views
     *
     * @param content
     * @param collectionContentViewHolder
     */
    private void setCollectionContentToViews(Content content, CollectionContentViewHolder collectionContentViewHolder) {
        collectionContentViewHolder.vhLayoutMain.setTag(content);
        collectionContentViewHolder.vhtvContentName.setText(content.getContentData().getName());
        collectionContentViewHolder.vhTvContentType.setText(R.string.label_all_content_type_collection);
        collectionContentViewHolder.vhTvContentType.setTextColor(mContext.getResources().getColor(R.color.colorAccent));

        if (!isFromDownloadsScreen) {
            collectionContentViewHolder.vhIbMore.setVisibility(View.INVISIBLE);
            if (content.isAvailableLocally()) {
                collectionContentViewHolder.vhLayoutTick.setVisibility(View.VISIBLE);
            } else {
                collectionContentViewHolder.vhLayoutTick.setVisibility(View.GONE);
            }
        } else {
            collectionContentViewHolder.vhLayoutTick.setVisibility(View.GONE);
            if (content.isAvailableLocally()) {
                collectionContentViewHolder.vhIbMore.setTag(content);
                collectionContentViewHolder.vhIbMore.setVisibility(View.VISIBLE);
            } else {
                collectionContentViewHolder.vhIbMore.setVisibility(View.INVISIBLE);
            }
        }

        if (!ContentUtil.isContentLive(content.getContentData().getStatus())) {
            if (ContentUtil.isContentExpired(content.getContentData().getExpires())) {
                collectionContentViewHolder.vhLayoutContent.setBackgroundResource(R.drawable.ic_expired);
            } else {
                collectionContentViewHolder.vhLayoutContent.setBackgroundResource(R.drawable.ic_draft);
            }
            collectionContentViewHolder.vhLayoutMain.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
            collectionContentViewHolder.vhtvContentName.setTextColor(mContext.getResources().getColor(android.R.color.white));
        } else {
//            normalContentViewHolder.vhLayoutMain.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            collectionContentViewHolder.vhtvContentName.setTextColor(mContext.getResources().getColor(R.color.black1));
            collectionContentViewHolder.vhLayoutContent.setBackgroundResource(R.drawable.ic_default_collection);
        }

        if (!StringUtil.isNullOrEmpty(content.getContentData().getAppIcon())) {
            GlideImageUtil.loadImageUrl(mContext, content.getBasePath() + "/" + content.getContentData().getAppIcon(), collectionContentViewHolder.vhImgContent);
        } else {
            GlideImageUtil.loadImageUrl(mContext, collectionContentViewHolder.vhImgContent);
        }
    }

    /**
     * Set Textbook content related data to views
     *
     * @param content
     * @param textBookContentViewHolder
     */
    private void setTextBookContentToViews(Content content, TextBookContentViewHolder textBookContentViewHolder) {
        textBookContentViewHolder.vhLayoutMain.setTag(content);
        textBookContentViewHolder.vhtvContentName.setText(content.getContentData().getName());
        textBookContentViewHolder.vhTvContentType.setText(R.string.label_all_content_type_textbook);
        textBookContentViewHolder.vhTvContentType.setTextColor(mContext.getResources().getColor(R.color.download_green));

        if (!isFromDownloadsScreen) {
            textBookContentViewHolder.vhIbMore.setVisibility(View.INVISIBLE);
            if (content.isAvailableLocally()) {
                textBookContentViewHolder.vhLayoutTick.setVisibility(View.VISIBLE);
            } else {
                textBookContentViewHolder.vhLayoutTick.setVisibility(View.GONE);
            }
        } else {
            textBookContentViewHolder.vhLayoutTick.setVisibility(View.GONE);
            if (content.isAvailableLocally()) {
                textBookContentViewHolder.vhIbMore.setTag(content);
                textBookContentViewHolder.vhIbMore.setVisibility(View.VISIBLE);
            } else {
                textBookContentViewHolder.vhIbMore.setVisibility(View.INVISIBLE);
            }
        }

        if (!ContentUtil.isContentLive(content.getContentData().getStatus())) {
            if (ContentUtil.isContentExpired(content.getContentData().getExpires())) {
                textBookContentViewHolder.vhLayoutContent.setBackgroundResource(R.drawable.ic_expired);
            } else {
                textBookContentViewHolder.vhLayoutContent.setBackgroundResource(R.drawable.ic_draft);
            }
            textBookContentViewHolder.vhLayoutMain.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
            textBookContentViewHolder.vhtvContentName.setTextColor(mContext.getResources().getColor(android.R.color.white));
        } else {
//            normalContentViewHolder.vhLayoutMain.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            textBookContentViewHolder.vhtvContentName.setTextColor(mContext.getResources().getColor(R.color.black1));
            textBookContentViewHolder.vhLayoutContent.setBackgroundResource(R.drawable.ic_default_textbook);
        }

        if (!StringUtil.isNullOrEmpty(content.getContentData().getAppIcon())) {
            GlideImageUtil.loadImageUrl(mContext, content.getBasePath() + "/" + content.getContentData().getAppIcon(), textBookContentViewHolder.vhImgContent);
        } else {
            GlideImageUtil.loadImageUrl(mContext, textBookContentViewHolder.vhImgContent);
        }
    }

    public void refresh(List<Content> contentList, boolean isFromDownloadsScreen) {
        this.isFromDownloadsScreen = isFromDownloadsScreen;
        List<Content> newContentList = new ArrayList<>();
        newContentList.addAll(contentList);

        mContentList.clear();
        mContentList.addAll(newContentList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mContentList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mContentList != null && mContentList.size() > 0) {
            Content content = mContentList.get(position);

            String contentType = content.getContentType();
            //Collection View Type
            if (contentType.equalsIgnoreCase(ContentType.COLLECTION)) {
                return COLLECTION_TYPE_VIEW;
            } else if (contentType.equalsIgnoreCase(ContentType.TEXTBOOK)) {
                return TEXT_BOOK_TYPE_VIEW;
            } else {
                return NORMAL_TYPE_VIEW;
            }
        }

        return NORMAL_TYPE_VIEW;
    }

    /**
     * View Holder for normal content
     */
    public class NormalContentViewHolder extends RecyclerView.ViewHolder {
        public View view;
        private ImageView vhImgContent;
        private TextView vhtvContentName;
        private RelativeLayout vhLayoutMain;
        private RelativeLayout vhLayoutContent;
        private TextView vhTvContentType;
        private LinearLayout vhLayoutTick;
        private TextView vhNewText;
        private ImageButton vhIbMore;

        public NormalContentViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemView;
            vhtvContentName = (TextView) itemLayoutView.findViewById(R.id.txt_content_name);
            vhImgContent = (ImageView) itemLayoutView.findViewById(R.id.img_content);
            vhLayoutMain = (RelativeLayout) itemLayoutView.findViewById(R.id.cardview);
            vhLayoutContent = (RelativeLayout) itemLayoutView.findViewById(R.id.layout_content);
            vhTvContentType = (TextView) itemLayoutView.findViewById(R.id.tv_content_type);
            vhLayoutTick = (LinearLayout) itemLayoutView.findViewById(R.id.ll_tick);
            vhNewText = (TextView) itemLayoutView.findViewById(R.id.tv_new);
            vhIbMore = (ImageButton) itemLayoutView.findViewById(R.id.ib_more);

            vhLayoutMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPresenter instanceof CollectionContract.Presenter) {
                        CollectionContract.Presenter collectionPresenter= (CollectionContract.Presenter) mPresenter;
                        collectionPresenter.handleContentClick((Content) v.getTag());
                    } else if (mPresenter instanceof DownloadedTextbooksContract.Presenter) {
                        DownloadedTextbooksContract.Presenter textBookPresenter= (DownloadedTextbooksContract.Presenter) mPresenter;
                        textBookPresenter.handleContentClick((Content) v.getTag());
                    }
                }
            });

            vhIbMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPresenter instanceof CollectionContract.Presenter) {
                        CollectionContract.Presenter collectionPresenter = (CollectionContract.Presenter) mPresenter;
                        collectionPresenter.handleMoreClick((Content) v.getTag());
                    } else if (mPresenter instanceof DownloadedTextbooksContract.Presenter) {
                        DownloadedTextbooksContract.Presenter textBookPresenter = (DownloadedTextbooksContract.Presenter) mPresenter;
                        textBookPresenter.handleMoreClick((Content) v.getTag());
                    }
                }
            });
        }
    }

    /**
     * View Holder for collection content
     */
    public class CollectionContentViewHolder extends RecyclerView.ViewHolder {
        public View view;
        private ImageView vhImgContent;
        private TextView vhtvContentName;
        private RelativeLayout vhLayoutMain;
        private TextView vhTvContentType;
        private RelativeLayout vhLayoutContent;
        private LinearLayout vhLayoutTick;
        private ImageButton vhIbMore;

        public CollectionContentViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemView;
            vhtvContentName = (TextView) itemLayoutView.findViewById(R.id.txt_content_name);
            vhImgContent = (ImageView) itemLayoutView.findViewById(R.id.img_content);
            vhLayoutMain = (RelativeLayout) itemLayoutView.findViewById(R.id.cardview);
            vhTvContentType = (TextView) itemLayoutView.findViewById(R.id.tv_content_type);
            vhLayoutContent = (RelativeLayout) itemLayoutView.findViewById(R.id.layout_content);
            vhLayoutTick = (LinearLayout) itemLayoutView.findViewById(R.id.ll_tick);
            vhIbMore = (ImageButton) itemLayoutView.findViewById(R.id.ib_more);

            vhLayoutMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPresenter instanceof CollectionContract.Presenter) {
                        CollectionContract.Presenter collectionPresenter= (CollectionContract.Presenter) mPresenter;
                        collectionPresenter.handleContentClick((Content) v.getTag());
                    } else if (mPresenter instanceof DownloadedTextbooksContract.Presenter) {
                        DownloadedTextbooksContract.Presenter textBookPresenter= (DownloadedTextbooksContract.Presenter) mPresenter;
                        textBookPresenter.handleContentClick((Content) v.getTag());
                    }
                }
            });

            vhIbMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPresenter instanceof CollectionContract.Presenter) {
                        CollectionContract.Presenter collectionPresenter = (CollectionContract.Presenter) mPresenter;
                        collectionPresenter.handleMoreClick((Content) v.getTag());
                    } else if (mPresenter instanceof DownloadedTextbooksContract.Presenter) {
                        DownloadedTextbooksContract.Presenter textBookPresenter = (DownloadedTextbooksContract.Presenter) mPresenter;
                        textBookPresenter.handleMoreClick((Content) v.getTag());
                    }
                }
            });
        }
    }

    /**
     * View Holder for text book content
     */
    public class TextBookContentViewHolder extends RecyclerView.ViewHolder {
        public View view;
        private ImageView vhImgContent;
        private TextView vhtvContentName;
        private RelativeLayout vhLayoutMain;
        private TextView vhTvContentType;
        private RelativeLayout vhLayoutContent;
        private LinearLayout vhLayoutTick;
        private ImageButton vhIbMore;

        public TextBookContentViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemView;
            vhtvContentName = (TextView) itemLayoutView.findViewById(R.id.txt_content_name);
            vhImgContent = (ImageView) itemLayoutView.findViewById(R.id.img_content);
            vhLayoutMain = (RelativeLayout) itemLayoutView.findViewById(R.id.cardview);
            vhTvContentType = (TextView) itemLayoutView.findViewById(R.id.tv_content_type);
            vhLayoutContent = (RelativeLayout) itemLayoutView.findViewById(R.id.layout_content);
            vhLayoutTick = (LinearLayout) itemLayoutView.findViewById(R.id.ll_tick);
            vhIbMore = (ImageButton) itemLayoutView.findViewById(R.id.ib_more);

            vhLayoutMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPresenter instanceof CollectionContract.Presenter) {
                        CollectionContract.Presenter collectionPresenter= (CollectionContract.Presenter) mPresenter;
                        collectionPresenter.handleContentClick((Content) v.getTag());
                    } else if (mPresenter instanceof DownloadedTextbooksContract.Presenter) {
                        DownloadedTextbooksContract.Presenter textBookPresenter= (DownloadedTextbooksContract.Presenter) mPresenter;
                        textBookPresenter.handleContentClick((Content) v.getTag());
                    }
                }
            });

            vhIbMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPresenter instanceof CollectionContract.Presenter) {
                        CollectionContract.Presenter collectionPresenter = (CollectionContract.Presenter) mPresenter;
                        collectionPresenter.handleMoreClick((Content) v.getTag());
                    } else if (mPresenter instanceof DownloadedTextbooksContract.Presenter) {
                        DownloadedTextbooksContract.Presenter textBookPresenter = (DownloadedTextbooksContract.Presenter) mPresenter;
                        textBookPresenter.handleMoreClick((Content) v.getTag());
                    }
                }
            });
        }
    }
}
