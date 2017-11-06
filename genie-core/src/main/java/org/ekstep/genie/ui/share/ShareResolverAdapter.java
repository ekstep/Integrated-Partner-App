package org.ekstep.genie.ui.share;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.asynctask.IconLoaderTask;
import org.ekstep.genie.model.DisplayResolveInfo;

import java.util.List;

/**
 * Created on 9/8/2016.
 *
 * @author swayangjit_gwl
 */
public class ShareResolverAdapter extends RecyclerView.Adapter<ShareResolverAdapter.ViewHolder> {

    private final PackageManager mPackageManager;

    private final int mIconSize;
    private final int mIconLayoutSize;

    private List<DisplayResolveInfo> mResolveInfoList = null;

    private OnItemClickedListener mOnItemClickListener = null;

    private int mOrientation = 1;

    public ShareResolverAdapter(Context context, List<DisplayResolveInfo> resolveInfoList, OnItemClickedListener listener) {
        this.mPackageManager = context.getPackageManager();
        this.mIconSize = context.getResources().getDimensionPixelSize(R.dimen.share_icon);
        this.mIconLayoutSize = context.getResources().getDimensionPixelSize(R.dimen.share_dimen);
        this.mResolveInfoList = resolveInfoList;
        this.mOnItemClickListener = listener;
    }

    public void setOrientation(int orientation) {
        mOrientation = orientation;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_share_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(mIconLayoutSize, mIconLayoutSize);
        holder.itemView.setLayoutParams(parms);

        final DisplayResolveInfo item = mResolveInfoList.get(position);

        holder.text.setText(item.getLabel(mPackageManager));
        holder.text.setCompoundDrawables(null, null, null, null);

        IconLoaderTask loaderTask = new IconLoaderTask(item, holder.text);
        loaderTask.setPackabeManager(mPackageManager);
        loaderTask.setIconSize(mIconSize);
        loaderTask.setOrientation(mOrientation);
        loaderTask.execute();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClicked(item.intent, item.resolveInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResolveInfoList.size();
    }

    public List<DisplayResolveInfo> getResolveInfoList(){
        return mResolveInfoList;
    }


    public interface OnItemClickedListener {
        void onItemClicked(Intent intent, ResolveInfo resolveInfo);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.title);
        }
    }

}
