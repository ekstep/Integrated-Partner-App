package org.ekstep.genie.ui.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.util.GlideImageUtil;
import org.ekstep.genie.util.geniesdk.ContentUtil;
import org.ekstep.genieservices.commons.bean.ContentData;
import org.ekstep.genieservices.commons.utils.CollectionUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created on 1/24/2016.
 *
 * @author swayangjit_gwl
 */
public class SearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ContentData> mContentDataList;
    private Context mContext = null;
    private SearchContract.View mSearchView = null;
    private Set<String> mIdentifierListFromCache;
    private Set<String> mIdentifierList;

    public SearchResultAdapter(Context context, SearchContract.View view) {
        this.mContext = context;
        this.mSearchView = view;
        this.mIdentifierListFromCache = ContentUtil.getLocalContentsCache();
        this.mIdentifierList = new HashSet<String>();
    }

    public void setData(List<ContentData> contentList) {
        this.mContentDataList = contentList;
    }

    public Set<String> getIdentifierList() {
        return mIdentifierList != null ? mIdentifierList : new HashSet<String>();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_result_item, parent, false);
        ContentViewHolder contentViewHolder = new ContentViewHolder(row);
        return contentViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ContentData contentData = mContentDataList.get(position);
        mIdentifierList.add(contentData.getIdentifier());
        ContentViewHolder contentViewHolder = (ContentViewHolder) viewHolder;
        contentViewHolder.vhLayoutMain.setTag(contentData);
        if (mIdentifierListFromCache.contains(contentData.getIdentifier())) {
            contentViewHolder.vhImgTick.setVisibility(View.VISIBLE);
        } else {
            contentViewHolder.vhImgTick.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(contentData.getAppIcon())) {
            GlideImageUtil.loadImageUrl(mContext, contentData.getAppIcon(), contentViewHolder.vhImgContent);
        } else {
            GlideImageUtil.loadImageUrl(mContext, contentViewHolder.vhImgContent);
        }

        contentViewHolder.vhtvContentName.setText(contentData.getName());

        List<String> languageList = contentData.getLanguage();
        if (!CollectionUtil.isNullOrEmpty(languageList)) {
            contentViewHolder.vhtvContentLanguage.setText(languageList.get(0));
        }

        String contentType = contentData.getContentType();
        if (contentType != null) {
            contentViewHolder.vhtvContentType.setText(contentType);
        }

        contentViewHolder.vhLayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchView.navigateToContentDetailsActivity((ContentData) v.getTag());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mContentDataList.size();
    }

    public void refreshAdapter(List<ContentData> contentDataList) {
        mIdentifierListFromCache = ContentUtil.getLocalContentsCache();
        List<ContentData> refreshedContentDataList = new ArrayList<>();
        refreshedContentDataList.addAll(contentDataList);
        mContentDataList.clear();
        mContentDataList.addAll(refreshedContentDataList);
        notifyDataSetChanged();
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        public View view;
        private ImageView vhImgContent;
        private TextView vhtvContentName;
        private TextView vhtvContentType;
        private TextView vhtvContentLanguage;
        private RelativeLayout vhLayoutMain = null;
        private ImageView vhImgTick;

        public ContentViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemView;
            vhtvContentName = (TextView) itemLayoutView.findViewById(R.id.txt_content_name);
            vhtvContentType = (TextView) itemLayoutView.findViewById(R.id.txt_content_category);
            vhtvContentLanguage = (TextView) itemLayoutView.findViewById(R.id.txt_content_language);
            vhImgContent = (ImageView) itemLayoutView.findViewById(R.id.img_content);
            vhLayoutMain = (RelativeLayout) itemLayoutView.findViewById(R.id.layout_main);
            vhImgTick = (ImageView) itemLayoutView.findViewById(R.id.iv_normal_tick_mark);
        }
    }

}