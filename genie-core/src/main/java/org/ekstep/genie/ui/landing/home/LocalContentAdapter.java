package org.ekstep.genie.ui.landing.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.customview.EkStepCustomTextView;
import org.ekstep.genie.util.GlideImageUtil;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.ContentAccess;
import org.ekstep.genieservices.commons.bean.Profile;
import org.ekstep.genieservices.commons.bean.enums.ContentAccessStatus;
import org.ekstep.genieservices.commons.utils.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 3/16/2016.
 *
 * @author swayangjit_gwl
 */
public class LocalContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int COLLECTION_TYPE_VIEW = 2;
    private static final int TEXT_BOOK_TYPE_VIEW = 1;
    private static final int NORMAL_TYPE_VIEW = 0;

    private Context mContext = null;
    private List<Content> mContentList = null;
    private long mProfileCreationTime;
    private HomeContract.Presenter mHomePresenter;

    public LocalContentAdapter(Context context, Profile profile, HomeContract.Presenter presenter) {
        this.mContext = context;
        this.mHomePresenter = presenter;
        if (profile != null && profile.getCreatedAt() != null) {
            this.mProfileCreationTime = profile.getCreatedAt().getTime();
        }
    }

    public void setData(List<Content> contentList) {
        this.mContentList = contentList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == TEXT_BOOK_TYPE_VIEW) {
            View textBookViewHolder = inflater.inflate(R.layout.layout_text_book_item, parent, false);
            TextBookContentViewHolder textBookContentViewHolder = new TextBookContentViewHolder(textBookViewHolder);
            viewHolder = textBookContentViewHolder;
        } else if (viewType == COLLECTION_TYPE_VIEW) {
            View collectionViewHolder = inflater.inflate(R.layout.layout_collection_item, parent, false);
            viewHolder = new CollectionContentViewHolder(collectionViewHolder);
        } else {
            View normalViewHolder = inflater.inflate(R.layout.layout_normal_item, parent, false);
            viewHolder = new NormalContentViewHolder(normalViewHolder);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        Content content = mContentList.get(position);

        if (viewHolder.getItemViewType() == TEXT_BOOK_TYPE_VIEW) {
            TextBookContentViewHolder textBookContentViewHolder = (TextBookContentViewHolder) viewHolder;

            setTextBookContentToViews(content, textBookContentViewHolder, position);
        } else if (viewHolder.getItemViewType() == COLLECTION_TYPE_VIEW) {
            CollectionContentViewHolder collectionContentViewHolder = (CollectionContentViewHolder) viewHolder;

            setCollectionContentToViews(content, collectionContentViewHolder, position);
        } else {
            NormalContentViewHolder normalContentViewHolder = (NormalContentViewHolder) viewHolder;

            setNormalContentToViews(content, normalContentViewHolder, position);
        }
    }

    /**
     * Set Normal Game Related data to View
     *
     * @param content
     * @param normalContentViewHolder
     * @param position
     */
    private void setNormalContentToViews(Content content, NormalContentViewHolder normalContentViewHolder, int position) {
        WrapperContent wrapperContent = new WrapperContent(position, content);
        normalContentViewHolder.vhLayoutMain.setTag(wrapperContent);
        normalContentViewHolder.vhtvContentName.setText(content.getContentData().getName());
        normalContentViewHolder.vhTvContentType.setText(R.string.label_all_content_type_activity);
//        normalContentViewHolder.vhTvContentType.setTextColor(mContext.getResources().getColor(R.color.app_blue_theme_color));

        mHomePresenter.setLocalContentDetails(content, normalContentViewHolder, this, mProfileCreationTime);
    }

    /**
     * Set Collection content related data to views
     *
     * @param content
     * @param collectionContentViewHolder
     * @param position
     */
    private void setCollectionContentToViews(Content content, CollectionContentViewHolder collectionContentViewHolder, int position) {
        WrapperContent wrapperContent = new WrapperContent(position, content);
        collectionContentViewHolder.vhLayoutMain.setTag(wrapperContent);
        collectionContentViewHolder.vhtvContentName.setText(content.getContentData().getName());
        collectionContentViewHolder.vhTvContentType.setText(R.string.label_all_content_type_collection);
//        collectionContentViewHolder.vhTvContentType.setTextColor(mContext.getResources().getColor(R.color.colorAccent));

        mHomePresenter.setLocalCollectionDetails(content, collectionContentViewHolder, this, mProfileCreationTime);
    }

    /**
     * Set Textbook content related data to views
     *
     * @param content
     * @param textBookContentViewHolder
     * @param position
     */
    private void setTextBookContentToViews(Content content, TextBookContentViewHolder textBookContentViewHolder, int position) {
        WrapperContent wrapperContent = new WrapperContent(position, content);
        textBookContentViewHolder.vhLayoutMain.setTag(wrapperContent);
        textBookContentViewHolder.vhtvContentName.setText(content.getContentData().getName());
        textBookContentViewHolder.vhTvContentType.setText(R.string.label_all_content_type_textbook);
//        textBookContentViewHolder.vhTvContentType.setTextColor(mContext.getResources().getColor(R.color.download_green));

        mHomePresenter.setLocalTextbookDetails(content, textBookContentViewHolder, this, mProfileCreationTime);
    }

    @Override
    public int getItemCount() {
        return mContentList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mHomePresenter.getItemTypeInLocalAdapter(position, mContentList);
    }

    public void setNormalContentBackground(NormalContentViewHolder normalContentViewHolder, Drawable icon) {
        normalContentViewHolder.vhLayoutContent.setBackground(icon);
    }

    public void setNormalContentBackgroundColor(NormalContentViewHolder normalContentViewHolder, int color) {
        normalContentViewHolder.vhLayoutMain.setBackgroundColor(color);
    }

    public void setNormalContentNameColor(NormalContentViewHolder normalContentViewHolder, int color) {
        normalContentViewHolder.vhtvContentName.setTextColor(color);
    }

    public void setNormalContentIcon(Context mContext, String iconPath, NormalContentViewHolder normalContentViewHolder) {
        GlideImageUtil.loadImageUrl(mContext, iconPath, normalContentViewHolder.vhImgContent);
    }

    public void setDefaultNormalContentIcon(NormalContentViewHolder normalContentViewHolder) {
        GlideImageUtil.loadImageUrl(mContext, normalContentViewHolder.vhImgContent);
    }

    public void setCollectionContentBackground(CollectionContentViewHolder collectionContentViewHolder, Drawable icon) {
        collectionContentViewHolder.vhLayoutContent.setBackground(icon);
    }

    public void setCollectionContentBackgroundColor(CollectionContentViewHolder collectionContentViewHolder, int color) {
        collectionContentViewHolder.vhLayoutMain.setBackgroundColor(color);
    }

    public void setCollectionContentNameColor(CollectionContentViewHolder collectionContentViewHolder, int color) {
        collectionContentViewHolder.vhtvContentName.setTextColor(color);
    }

    public void setCollectionContentIcon(Context mContext, String iconPath, CollectionContentViewHolder collectionContentViewHolder) {
        GlideImageUtil.loadImageUrl(mContext, iconPath, collectionContentViewHolder.vhImgContent);
    }

    public void setDefaultCollectionContentIcon(CollectionContentViewHolder collectionContentViewHolder) {
        GlideImageUtil.loadImageUrl(mContext, collectionContentViewHolder.vhImgContent);
    }

    public void setTextbookContentBackground(TextBookContentViewHolder textBookContentViewHolder, Drawable icon) {
        textBookContentViewHolder.vhLayoutContent.setBackground(icon);
    }

    public void setTextbookContentBackgroundColor(TextBookContentViewHolder textBookContentViewHolder, int color) {
        textBookContentViewHolder.vhLayoutMain.setBackgroundColor(color);
    }

    public void setTextbookContentNameColor(TextBookContentViewHolder textBookContentViewHolder, int color) {
        textBookContentViewHolder.vhtvContentName.setTextColor(color);
    }

    public void setTextbookContentIcon(Context mContext, String iconPath, TextBookContentViewHolder textBookContentViewHolder) {
        GlideImageUtil.loadImageUrl(mContext, iconPath, textBookContentViewHolder.vhImgContent);
    }

    public void setDefaultTextbookContentIcon(TextBookContentViewHolder textBookContentViewHolder) {
        GlideImageUtil.loadImageUrl(mContext, textBookContentViewHolder.vhImgContent);
    }

    /**
     * View Holder for normal content
     */
    public class NormalContentViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public ImageView vhImgContent;
        public TextView vhtvContentName;
        public RelativeLayout vhLayoutMain;
        public RelativeLayout vhLayoutContent;
        public TextView vhTvContentType;
        public EkStepCustomTextView vhTvNew;

        public NormalContentViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemView;
            vhtvContentName = (TextView) itemLayoutView.findViewById(R.id.txt_content_name);
            vhImgContent = (ImageView) itemLayoutView.findViewById(R.id.img_content);
            vhLayoutMain = (RelativeLayout) itemLayoutView.findViewById(R.id.cardview);
            vhLayoutContent = (RelativeLayout) itemLayoutView.findViewById(R.id.layout_content);
            vhTvContentType = (TextView) itemLayoutView.findViewById(R.id.tv_content_type);
            vhTvNew = (EkStepCustomTextView) itemLayoutView.findViewById(R.id.tv_new);

            vhLayoutMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PreferenceUtil.setCdataStatus(true);
                    WrapperContent wrapperContent = (WrapperContent) v.getTag();
                    Content content = wrapperContent.getContent();
                    if (CollectionUtil.isNullOrEmpty(content.getContentAccess())) {
                        ContentAccess contentAccess = new ContentAccess();
                        contentAccess.setStatus(ContentAccessStatus.PLAYED.getValue());
                        List<ContentAccess> contentAccessList = new ArrayList<ContentAccess>();
                        contentAccessList.add(contentAccess);
                        content.setContentAccess(contentAccessList);
                        content.setLastUpdatedTime(System.currentTimeMillis());
                    }
                    mContentList.set(wrapperContent.getPosition(), content);
                    notifyItemChanged(wrapperContent.getPosition());
                    mHomePresenter.startGame(wrapperContent.getContent());
                }
            });
        }
    }

    /**
     * View Holder for collection content
     */
    public class CollectionContentViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public ImageView vhImgContent;
        public TextView vhtvContentName;
        public RelativeLayout vhLayoutMain;
        public TextView vhTvContentType;
        public RelativeLayout vhLayoutContent;
        public EkStepCustomTextView vhTvNew;

        public CollectionContentViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemView;
            vhtvContentName = (TextView) itemLayoutView.findViewById(R.id.txt_content_name);
            vhImgContent = (ImageView) itemLayoutView.findViewById(R.id.img_content);
            vhLayoutMain = (RelativeLayout) itemLayoutView.findViewById(R.id.cardview);
            vhTvContentType = (TextView) itemLayoutView.findViewById(R.id.tv_content_type);
            vhLayoutContent = (RelativeLayout) itemLayoutView.findViewById(R.id.layout_content);
            vhTvNew = (EkStepCustomTextView) itemLayoutView.findViewById(R.id.tv_new);

            vhLayoutMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PreferenceUtil.setCdataStatus(true);
                    WrapperContent wrapperContent = (WrapperContent) v.getTag();
                    Content content = wrapperContent.getContent();
                    if (CollectionUtil.isNullOrEmpty(content.getContentAccess())) {
                        ContentAccess contentAccess = new ContentAccess();
                        contentAccess.setStatus(ContentAccessStatus.PLAYED.getValue());
                        List<ContentAccess> contentAccessList = new ArrayList<ContentAccess>();
                        contentAccessList.add(contentAccess);
                        content.setContentAccess(contentAccessList);
                        content.setLastUpdatedTime(System.currentTimeMillis());
                    }
                    mContentList.set(wrapperContent.getPosition(), content);
                    notifyItemChanged(wrapperContent.getPosition());
                    mHomePresenter.startGame(wrapperContent.getContent());
                }
            });
        }
    }

    /**
     * View Holder for text book content
     */
    public class TextBookContentViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public ImageView vhImgContent;
        public TextView vhtvContentName;
        public RelativeLayout vhLayoutMain;
        public TextView vhTvContentType;
        public RelativeLayout vhLayoutContent;
        public EkStepCustomTextView vhTvNew;

        public TextBookContentViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemView;
            vhtvContentName = (TextView) itemLayoutView.findViewById(R.id.txt_content_name);
            vhImgContent = (ImageView) itemLayoutView.findViewById(R.id.img_content);
            vhLayoutMain = (RelativeLayout) itemLayoutView.findViewById(R.id.cardview);
            vhTvContentType = (TextView) itemLayoutView.findViewById(R.id.tv_content_type);
            vhLayoutContent = (RelativeLayout) itemLayoutView.findViewById(R.id.layout_content);
            vhTvNew = (EkStepCustomTextView) itemLayoutView.findViewById(R.id.tv_new);

            vhLayoutMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PreferenceUtil.setCdataStatus(true);
                    WrapperContent wrapperContent = (WrapperContent) v.getTag();
                    Content content = wrapperContent.getContent();
                    if (CollectionUtil.isNullOrEmpty(content.getContentAccess())) {
                        ContentAccess contentAccess = new ContentAccess();
                        contentAccess.setStatus(ContentAccessStatus.PLAYED.getValue());
                        List<ContentAccess> contentAccessList = new ArrayList<ContentAccess>();
                        contentAccessList.add(contentAccess);
                        content.setContentAccess(contentAccessList);
                        content.setLastUpdatedTime(System.currentTimeMillis());
                    }
                    mContentList.set(wrapperContent.getPosition(), content);
                    notifyItemChanged(wrapperContent.getPosition());
                    mHomePresenter.startGame(wrapperContent.getContent());
                }
            });
        }
    }

    private class WrapperContent {
        private Content content;
        private int position;

        WrapperContent(int position, Content content) {
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
