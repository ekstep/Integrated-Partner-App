package org.ekstep.genie.ui.transfer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.util.GlideImageUtil;
import org.ekstep.genieservices.commons.bean.Content;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 1/24/2016.
 *
 * @author swayangjit_gwl
 */
public class ExportableContentGridAdapter extends RecyclerView.Adapter<ExportableContentGridAdapter.ViewHolder> implements View.OnClickListener {

    private static final String TAG = ExportableContentGridAdapter.class.getSimpleName();

    private Context mContext = null;
    private List<Content> mContentList = null;
    private List<String> mSelected;
    private TransferContract.Presenter mPresenter = null;

    public ExportableContentGridAdapter(Context context, TransferContract.Presenter presenter) {
        this.mContext = context;
        this.mPresenter = presenter;
        this.mSelected = new ArrayList<>();
    }

    public void setData(List<Content> contentList) {
        this.mContentList = contentList;
    }

    @Override
    public ExportableContentGridAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_exportable_content, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        Content content = mContentList.get(position);

        viewHolder.vhLayoutMain.setTag(position);
        viewHolder.vhLayoutMain.setOnClickListener(this);
        if (mSelected.contains(content.getIdentifier())) {
            viewHolder.img_tick.setImageResource(R.drawable.ic_check);
        } else {
            viewHolder.img_tick.setImageResource(R.drawable.ic_uncheck);
        }
        viewHolder.vhLayoutMain.setBackgroundColor(mContext.getResources().getColor(R.color.black_transparent));
        GlideImageUtil.loadImageUrl(mContext, new File(mContentList.get(position).getBasePath() + "/" + mContentList.get(position).getContentData().getAppIcon()), viewHolder.vhImgContent);
        viewHolder.vhtvContentName.setText(mContentList.get(position).getContentData().getName());
    }

    public void toggleSelected(String index) {
        final boolean newState = !mSelected.contains(index);

        if (newState) {
            mSelected.add(index);
        } else {
            mSelected.remove(index);
        }
        notifyDataSetChanged();
    }

    public void clearSelected() {
        mSelected.clear();
        notifyDataSetChanged();
    }

    public List<String> getSelectedItems() {
        return mSelected;
    }

    public int getSelectedCount() {
        return mSelected.size();
    }

    @Override
    public int getItemCount() {
        return mContentList.size();
    }

    @Override
    public void onClick(View v) {
        mPresenter.processItemClick((int) v.getTag());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        private ImageView vhImgContent;
        private TextView vhtvContentName;
        private RelativeLayout vhLayoutMain = null;
        private ImageView img_tick;
        private RelativeLayout layout_tick;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemView;
            vhtvContentName = (TextView) itemLayoutView.findViewById(R.id.txt_content_name);
            vhImgContent = (ImageView) itemLayoutView.findViewById(R.id.img_content);
            vhLayoutMain = (RelativeLayout) itemLayoutView.findViewById(R.id.layout_main);
            img_tick = (ImageView) itemLayoutView.findViewById(R.id.img_tick);
            layout_tick = (RelativeLayout) itemLayoutView.findViewById(R.id.layout_tick);
        }
    }

}
