package org.ekstep.genie.ui.landing.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.customview.EkStepCustomTextView;
import org.ekstep.genie.util.GlideImageUtil;
import org.ekstep.genie.util.geniesdk.ContentUtil;
import org.ekstep.genieservices.commons.bean.ContentData;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by on 1/24/2016.
 *
 * @author swayangjit_gwl
 */
public class SectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int COLLECTION_TYPE_VIEW = 2;
    private static final int TEXT_BOOK_TYPE_VIEW = 1;
    private static final int NORMAL_TYPE_VIEW = 0;
    private Context mContext = null;
    private List<ContentData> mContentDataList = null;
    private Map<String, String> mSectionMap;
    private HomeContract.Presenter mHomePresenter;
    private Set<String> mIdentifierList;

    public SectionAdapter(Context context, List<ContentData> contentDataList, Map<String, String> sectionMap, HomeContract.Presenter presenter) {
        mContext = context;
        mContentDataList = contentDataList;
        mSectionMap = sectionMap;
        mIdentifierList = ContentUtil.getLocalContentsCache();
        this.mHomePresenter = presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == TEXT_BOOK_TYPE_VIEW) {
            View textBookViewHolder = inflater.inflate(R.layout.layout_text_book_item, parent, false);
            viewHolder = new TextBookContentViewHolder(textBookViewHolder);
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
        ContentData contentData = mContentDataList.get(position);

        if (viewHolder.getItemViewType() == TEXT_BOOK_TYPE_VIEW) {
            TextBookContentViewHolder textBookContentViewHolder = (TextBookContentViewHolder) viewHolder;
            textBookContentViewHolder.vhLayoutMain.setOnClickListener(new OnItemClick(position));

            setTextBookContentToViews(contentData, textBookContentViewHolder);
        } else if (viewHolder.getItemViewType() == COLLECTION_TYPE_VIEW) {
            CollectionContentViewHolder collectionContentViewHolder = (CollectionContentViewHolder) viewHolder;
            collectionContentViewHolder.vhLayoutMain.setOnClickListener(new OnItemClick(position));

            setCollectionContentToViews(contentData, collectionContentViewHolder);
        } else {
            NormalContentViewHolder normalContentViewHolder = (NormalContentViewHolder) viewHolder;
            normalContentViewHolder.vhLayoutMain.setOnClickListener(new OnItemClick(position));
            normalContentViewHolder.vhLayoutContent.setBackgroundResource(R.drawable.ic_default_content);
            setNormalContentToViews(contentData, normalContentViewHolder);
        }
    }

    /**
     * Set Normal Game Related data to View
     *
     * @param contentData
     * @param normalContentViewHolder
     */
    private void setNormalContentToViews(ContentData contentData, NormalContentViewHolder normalContentViewHolder) {
        normalContentViewHolder.vhLayoutMain.setTag(contentData);
        normalContentViewHolder.vhtvContentName.setText(contentData.getName());
        normalContentViewHolder.vhTvContentType.setText(R.string.label_all_content_type_activity);
//        normalContentViewHolder.vhTvContentType.setTextColor(mContext.getResources().getColor(R.color.app_blue_theme_color));
        normalContentViewHolder.vhTvNew.setVisibility(View.GONE);
        normalContentViewHolder.vhLinearLayoutTick.setVisibility(View.INVISIBLE);

        mHomePresenter.setSectionContentDetails(mIdentifierList, contentData, normalContentViewHolder, this);
    }

    /**
     * Set Collection content related data to views
     *
     * @param contentData
     * @param collectionContentViewHolder
     */
    private void setCollectionContentToViews(ContentData contentData, CollectionContentViewHolder collectionContentViewHolder) {
        collectionContentViewHolder.vhLayoutMain.setTag(contentData);
        collectionContentViewHolder.vhtvContentName.setText(contentData.getName());
        collectionContentViewHolder.vhTvContentType.setText(R.string.label_all_content_type_collection);
//        collectionContentViewHolder.vhTvContentType.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        collectionContentViewHolder.vhTvNew.setVisibility(View.GONE);
        collectionContentViewHolder.vhLinearLayoutTick.setVisibility(View.INVISIBLE);

        mHomePresenter.setSectionCollectionDetails(mIdentifierList, contentData, collectionContentViewHolder, this);
    }

    /**
     * Set Textbook content related data to views
     *
     * @param contentData
     * @param textBookContentViewHolder
     */
    private void setTextBookContentToViews(ContentData contentData, TextBookContentViewHolder textBookContentViewHolder) {
        textBookContentViewHolder.vhLayoutMain.setTag(contentData);
        textBookContentViewHolder.vhtvContentName.setText(contentData.getName());
        textBookContentViewHolder.vhTvContentType.setText(R.string.label_all_content_type_textbook);
//        textBookContentViewHolder.vhTvContentType.setTextColor(mContext.getResources().getColor(R.color.download_green));
        textBookContentViewHolder.vhTvNew.setVisibility(View.GONE);
        textBookContentViewHolder.vhLinearLayoutTick.setVisibility(View.INVISIBLE);

        mHomePresenter.setSectionTextbookDetails(mIdentifierList, contentData, textBookContentViewHolder, this);
    }

    @Override
    public int getItemCount() {
        return mContentDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mHomePresenter.getItemTypeInSectionAdapter(position, mContentDataList);
    }

    public void setNormalContentTickVisibility(NormalContentViewHolder normalContentViewHolder, int visibility) {
        normalContentViewHolder.vhLinearLayoutTick.setVisibility(visibility);
    }

    public void setNormalContentBackgroundResource(NormalContentViewHolder normalContentViewHolder, Drawable bg_resource) {
        normalContentViewHolder.vhLayoutContent.setBackground(bg_resource);
    }

    public void setNormalMainLayoutBackgroundColor(NormalContentViewHolder normalContentViewHolder, int color) {
        normalContentViewHolder.vhLayoutMain.setBackgroundColor(color);
    }

    public void setNormalContentNameColor(NormalContentViewHolder normalContentViewHolder, int color) {
        normalContentViewHolder.vhtvContentName.setTextColor(color);
    }

    public void setNormalContentIcon(Context context, String appIcon, NormalContentViewHolder normalContentViewHolder) {
        GlideImageUtil.loadImageUrl(context, appIcon, normalContentViewHolder.vhImgContent);
    }

    public void setDefaultNormalContentIcon(Context context, NormalContentViewHolder normalContentViewHolder) {
        GlideImageUtil.loadImageUrl(context, normalContentViewHolder.vhImgContent);
    }

    public void setCollectionContentTickVisibility(CollectionContentViewHolder collectionContentViewHolder, int visibility) {
        collectionContentViewHolder.vhLinearLayoutTick.setVisibility(visibility);
    }

    public void setCollectionContentBackgroundResource(CollectionContentViewHolder collectionContentViewHolder, Drawable bg_resource) {
        collectionContentViewHolder.vhLayoutContent.setBackground(bg_resource);
    }

    public void setCollectionMainLayoutBackgroundColor(CollectionContentViewHolder collectionContentViewHolder, int color) {
        collectionContentViewHolder.vhLayoutMain.setBackgroundColor(color);
    }

    public void setCollectionContentNameColor(CollectionContentViewHolder collectionContentViewHolder, int color) {
        collectionContentViewHolder.vhtvContentName.setTextColor(color);
    }

    public void setCollectionContentIcon(Context context, String appIcon, CollectionContentViewHolder collectionContentViewHolder) {
        GlideImageUtil.loadImageUrl(context, appIcon, collectionContentViewHolder.vhImgContent);
    }

    public void setDefaultCollectionContentIcon(Context context, CollectionContentViewHolder collectionContentViewHolder) {
        GlideImageUtil.loadImageUrl(context, collectionContentViewHolder.vhImgContent);
    }

    public void setTextbookContentTickVisibility(TextBookContentViewHolder textBookContentViewHolder, int visibility) {
        textBookContentViewHolder.vhLinearLayoutTick.setVisibility(visibility);
    }

    public void setTextbookContentBackgroundResource(TextBookContentViewHolder textBookContentViewHolder, Drawable bg_resource) {
        textBookContentViewHolder.vhLayoutContent.setBackground(bg_resource);
    }

    public void setTextbookMainLayoutBackgroundColor(TextBookContentViewHolder textBookContentViewHolder, int color) {
        textBookContentViewHolder.vhLayoutMain.setBackgroundColor(color);
    }

    public void setTextbookContentNameColor(TextBookContentViewHolder textBookContentViewHolder, int color) {
        textBookContentViewHolder.vhtvContentName.setTextColor(color);
    }

    public void setTextbookContentIcon(Context context, String appIcon, TextBookContentViewHolder textBookContentViewHolder) {
        GlideImageUtil.loadImageUrl(context, appIcon, textBookContentViewHolder.vhImgContent);
    }

    public void setDefaultTextbookContentIcon(Context context, TextBookContentViewHolder textBookContentViewHolder) {
        GlideImageUtil.loadImageUrl(context, textBookContentViewHolder.vhImgContent);
    }

    /**
     * View Holder for normal content
     */
    public class NormalContentViewHolder extends RecyclerView.ViewHolder {
        public View view;

        private ImageView vhImgContent;
        private TextView vhtvContentName;
        private RelativeLayout vhLayoutMain;
        private TextView vhTvContentType;
        private RelativeLayout vhLayoutContent;
        private EkStepCustomTextView vhTvNew;
        private LinearLayout vhLinearLayoutTick;

        public NormalContentViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemView;
            vhtvContentName = (TextView) itemLayoutView.findViewById(R.id.txt_content_name);
            vhImgContent = (ImageView) itemLayoutView.findViewById(R.id.img_content);
            vhLayoutMain = (RelativeLayout) itemLayoutView.findViewById(R.id.cardview);
            vhTvContentType = (TextView) itemLayoutView.findViewById(R.id.tv_content_type);
            vhLayoutContent = (RelativeLayout) itemLayoutView.findViewById(R.id.layout_content);
            vhTvNew = (EkStepCustomTextView) itemLayoutView.findViewById(R.id.tv_new);
            vhLinearLayoutTick = (LinearLayout) itemLayoutView.findViewById(R.id.ll_tick);
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
        private EkStepCustomTextView vhTvNew;
        private LinearLayout vhLinearLayoutTick;

        public CollectionContentViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemView;
            vhtvContentName = (TextView) itemLayoutView.findViewById(R.id.txt_content_name);
            vhImgContent = (ImageView) itemLayoutView.findViewById(R.id.img_content);
            vhLayoutMain = (RelativeLayout) itemLayoutView.findViewById(R.id.cardview);
            vhTvContentType = (TextView) itemLayoutView.findViewById(R.id.tv_content_type);
            vhLayoutContent = (RelativeLayout) itemLayoutView.findViewById(R.id.layout_content);
            vhTvNew = (EkStepCustomTextView) itemLayoutView.findViewById(R.id.tv_new);
            vhLinearLayoutTick = (LinearLayout) itemLayoutView.findViewById(R.id.ll_tick);
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
        private EkStepCustomTextView vhTvNew;
        private LinearLayout vhLinearLayoutTick;

        public TextBookContentViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemView;
            vhtvContentName = (TextView) itemLayoutView.findViewById(R.id.txt_content_name);
            vhImgContent = (ImageView) itemLayoutView.findViewById(R.id.img_content);
            vhLayoutMain = (RelativeLayout) itemLayoutView.findViewById(R.id.cardview);
            vhTvContentType = (TextView) itemLayoutView.findViewById(R.id.tv_content_type);
            vhLayoutContent = (RelativeLayout) itemLayoutView.findViewById(R.id.layout_content);
            vhTvNew = (EkStepCustomTextView) itemLayoutView.findViewById(R.id.tv_new);
            vhLinearLayoutTick = (LinearLayout) itemLayoutView.findViewById(R.id.ll_tick);
        }
    }

    private class OnItemClick implements View.OnClickListener {

        private int position;

        public OnItemClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            ContentData contentData = (ContentData) v.getTag();
            mHomePresenter.handleSectionItemClicked(contentData, position + 1, mSectionMap);
        }
    }

}
