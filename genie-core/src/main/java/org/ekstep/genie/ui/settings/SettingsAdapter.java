package org.ekstep.genie.ui.settings;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.ekstep.genie.R;

import java.util.List;

/**
 * Created by swayangjit on 14/9/17.
 */

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {


    private List<Drawable> mSettingsIconList;
    private int[] mSettingsName;
    private int mSelectedPosition;
    private Context mContext;
    private SettingsPresenter mSettingsPresenter;

    public SettingsAdapter(Context context, SettingsPresenter presenter) {
        this.mContext = context;
        this.mSettingsPresenter = presenter;
    }

    public void setData(int[] settingsNameList, List<Drawable> settingsIconList) {
        this.mSettingsName = settingsNameList;
        this.mSettingsIconList = settingsIconList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_setings_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.vhTxtName.setText(mSettingsName[position]);
        holder.itemView.setBackgroundColor(mSelectedPosition == position ? ContextCompat.getColor(mContext, R.color.white) :
                ContextCompat.getColor(mContext, R.color.color_gray));
        holder.vhImgSettings.setImageDrawable(mSettingsIconList.get(position));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mSettingsName.length;
    }

    public void setSelection(int position) {
        mSelectedPosition = position;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView vhTxtName;
        public ImageView vhImgSettings;

        public ViewHolder(View view) {
            super(view);
            vhTxtName = (TextView) view.findViewById(R.id.txt_settings_name);
            vhImgSettings = (ImageView) view.findViewById(R.id.img_settings);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = Integer.valueOf(v.getTag().toString());
                    if (mSelectedPosition != position) {
                        mSelectedPosition = position;
                        notifyItemRangeChanged(0, getItemCount());
                        mSettingsPresenter.handleSettingsListItemClick(mSelectedPosition);
                    }

                }
            });
        }
    }
}
