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
import org.ekstep.genie.util.AvatarUtil;
import org.ekstep.genieservices.commons.bean.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created on 18-05-2016.
 *
 * @author GoodWorkLabs
 */
public class ExportableProfileAdapter extends RecyclerView.Adapter<ExportableProfileAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<Profile> mProfileList;
    private List<String> mSelected;
    private TransferContract.Presenter mPresenter = null;
    private Map<String, Integer> mBadges;
    private Map<String, Integer> mAvatars;

    public ExportableProfileAdapter(Context context, TransferContract.Presenter presenter) {
        mContext = context;
        mPresenter = presenter;
    }

    public void setData(List<Profile> profileList) {
        this.mProfileList = profileList;
        this.mSelected = new ArrayList<>();
        this.mBadges = AvatarUtil.populateBadges();
        this.mAvatars = AvatarUtil.populateAvatars();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_exportable_content, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Profile profile = mProfileList.get(position);
        holder.vhLayoutMain.setTag(position);
        holder.vhLayoutMain.setOnClickListener(this);

        if (mSelected.contains(profile.getUid())) {
            holder.img_tick.setImageResource(R.drawable.ic_check);
        } else {
            holder.img_tick.setImageResource(R.drawable.ic_uncheck);
        }

        holder.vhLayoutMain.setBackgroundColor(mContext.getResources().getColor(R.color.black_transparent));

        if (profile.isGroupUser()) {
            holder.vhImgContent.setImageResource(mBadges.get(profile.getAvatar()));
        } else {
            try {
                holder.vhImgContent.setImageResource(mAvatars.get(profile.getAvatar()));
            } catch (Exception e) {
                holder.vhImgContent.setImageResource(R.drawable.avatar_anonymous);
            }
        }

        holder.vhtvUserName.setText(profile.getHandle());
    }

    @Override
    public int getItemCount() {
        return mProfileList.size();
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

    @Override
    public void onClick(View v) {
        mPresenter.processItemClick((int) v.getTag());
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


    public class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        private ImageView vhImgContent;
        private TextView vhtvUserName;
        private RelativeLayout vhLayoutMain = null;
        private ImageView img_tick;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemView;
            vhtvUserName = (TextView) itemLayoutView.findViewById(R.id.txt_content_name);
            vhImgContent = (ImageView) itemLayoutView.findViewById(R.id.img_content);
            vhLayoutMain = (RelativeLayout) itemLayoutView.findViewById(R.id.layout_main);
            img_tick = (ImageView) itemLayoutView.findViewById(R.id.img_tick);
        }
    }

}
