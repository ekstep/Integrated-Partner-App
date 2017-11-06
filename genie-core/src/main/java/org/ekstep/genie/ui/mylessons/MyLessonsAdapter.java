package org.ekstep.genie.ui.mylessons;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.customview.EkStepCustomTextView;
import org.ekstep.genie.model.enums.ContentType;
import org.ekstep.genie.util.GlideImageUtil;
import org.ekstep.genie.util.ThemeUtility;
import org.ekstep.genie.util.geniesdk.ContentUtil;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.ContentAccess;
import org.ekstep.genieservices.commons.bean.Profile;
import org.ekstep.genieservices.commons.bean.enums.ContentAccessStatus;
import org.ekstep.genieservices.commons.utils.CollectionUtil;
import org.ekstep.genieservices.commons.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 3/16/2016.
 *
 * @author swayangjit_gwl
 */
public class MyLessonsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int COLLECTION_TYPE_VIEW = 2;
    private static final int TEXT_BOOK_TYPE_VIEW = 1;
    private static final int NORMAL_TYPE_VIEW = 0;

    private Context mContext = null;
    private List<Content> mContentList = null;
    private long mProfileCreationTime;
    private MyLessonsContract.Presenter myLessonPresenter;
    private boolean isNotAnonymousProfile;

    public MyLessonsAdapter(Context context, Profile profile, boolean isNotAnonymousProfile, MyLessonsContract.Presenter presenter) {
        this.mContext = context;
        this.myLessonPresenter = presenter;
        if (profile != null && profile.getCreatedAt() != null) {
            this.mProfileCreationTime = profile.getCreatedAt().getTime();
        }
        this.isNotAnonymousProfile = isNotAnonymousProfile;
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
            setContentDetails(textBookContentViewHolder, position, R.string.label_all_content_type_textbook, R.attr.bookBackground, content);
        } else if (viewHolder.getItemViewType() == COLLECTION_TYPE_VIEW) {
            CollectionContentViewHolder collectionContentViewHolder = (CollectionContentViewHolder) viewHolder;
            setContentDetails(collectionContentViewHolder, position, R.string.label_all_content_type_collection, R.attr.collectionBackground, content);
        } else {
            NormalContentViewHolder normalContentViewHolder = (NormalContentViewHolder) viewHolder;
            setContentDetails(normalContentViewHolder, position, R.string.label_all_content_type_activity, R.attr.contentBackground, content);
        }
    }

    @Override
    public int getItemCount() {
        return mContentList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (!CollectionUtil.isNullOrEmpty(mContentList)) {
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


    public void setContentDetails(ViewHolder viewHolder, int position, int contentType, int contentAttribute, Content content) {

        MyLessonsAdapter.WrapperContent wrapperContent = new MyLessonsAdapter.WrapperContent(position, content);
        viewHolder.vhLayoutMain.setTag(wrapperContent);
        viewHolder.vhtvContentName.setText(content.getContentData().getName());
        viewHolder.vhTvContentType.setText(contentType);

        //this is to check if the content has expired or in drafts
        if (!ContentUtil.isContentLive(content.getContentData().getStatus())) {
            if (ContentUtil.isContentExpired(content.getContentData().getExpires())) {
                viewHolder.vhLayoutContent.setBackground(ThemeUtility.getDrawable(mContext, new int[]{R.attr.expiredContentBackground}));
            } else {
                viewHolder.vhLayoutContent.setBackground(ThemeUtility.getDrawable(mContext, new int[]{R.attr.expiredContentBackground}));
            }
            viewHolder.vhLayoutMain.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.transparent));
            viewHolder.vhtvContentName.setTextColor(ContextCompat.getColor(mContext, R.color.white));

        } else {
            viewHolder.vhLayoutContent.setBackground(ThemeUtility.getDrawable(mContext, new int[]{contentAttribute}));
            viewHolder.vhtvContentName.setTextColor(ThemeUtility.getColor(mContext, new int[]{R.attr.contentNameFontColor}));

        }

        //Check status of the game, to show NEW label
        boolean isNewContent = ContentUtil.isNewContent(content.getLastUpdatedTime(), ContentUtil.getContentAccess(content).getStatus(), mProfileCreationTime, isNotAnonymousProfile);
        viewHolder.vhTvNew.setVisibility(isNewContent ? View.VISIBLE : View.INVISIBLE);

        if (!StringUtil.isNullOrEmpty(content.getContentData().getAppIcon())) {
            GlideImageUtil.loadImageUrl(mContext, content.getBasePath() + "/" + content.getContentData().getAppIcon(), viewHolder.vhImgContent);
        } else {
            GlideImageUtil.loadImageUrl(mContext, viewHolder.vhImgContent);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ImageView vhImgContent;
        public TextView vhtvContentName;
        public RelativeLayout vhLayoutMain;
        public RelativeLayout vhLayoutContent;
        public TextView vhTvContentType;
        public EkStepCustomTextView vhTvNew;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemLayoutView;
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
                    myLessonPresenter.startGame(wrapperContent.getContent());
                }
            });
        }
    }

    /**
     * View Holder for normal content
     */
    public class NormalContentViewHolder extends MyLessonsAdapter.ViewHolder {
        public NormalContentViewHolder(View itemLayoutView) {
            super(itemLayoutView);
        }
    }

    /**
     * View Holder for collection content
     */
    public class CollectionContentViewHolder extends MyLessonsAdapter.ViewHolder {
        public CollectionContentViewHolder(View itemLayoutView) {
            super(itemLayoutView);
        }
    }

    /**
     * View Holder for text book content
     */
    public class TextBookContentViewHolder extends MyLessonsAdapter.ViewHolder {
        public TextBookContentViewHolder(View itemLayoutView) {
            super(itemLayoutView);
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
