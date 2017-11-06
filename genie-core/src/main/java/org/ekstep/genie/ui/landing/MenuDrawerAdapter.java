package org.ekstep.genie.ui.landing;

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
import org.ekstep.genie.callback.IMenuItemClick;
import org.ekstep.genie.util.ThemeUtility;

import java.util.List;

/**
 * Created on 12/21/2015.
 *
 * @author swayangjit_gwl
 */
public class MenuDrawerAdapter extends RecyclerView.Adapter<MenuDrawerAdapter.ViewHolder> {

    private IMenuItemClick mMenuItemClick = null;
    private Context mContext;
    private int[] mDrawerTextName = new int[]{R.string.label_nav_children, R.string.label_nav_notifications,
            R.string.label_nav_downloads, R.string.label_nav_share_genie,
            R.string.label_nav_transfer, R.string.label_nav_settings};
    private int[] mDrawerIconsAttributes = new int[]{R.attr.navUserIcon, R.attr.navNotificationIcon, R.attr.navDownloadsIcon, R.attr.navShareIcon, R.attr.navTrasnferIcon, R.attr.navSettingsIcon};
    private List<Drawable> mDrawerIcons;
    private int unreadNotificationCount;

    public MenuDrawerAdapter(Context context, IMenuItemClick menuItemClick) {
        this.mMenuItemClick = menuItemClick;
        this.mContext = context;
        this.mDrawerIcons = ThemeUtility.getIconsFromAttributes(context, mDrawerIconsAttributes);
    }


    public void setUnReadNotificationCount(int count) {
        this.unreadNotificationCount = count;
        notifyDataSetChanged();
    }

    @Override
    public MenuDrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_drawer_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MenuDrawerAdapter.ViewHolder holder, int position) {
        holder.vhTxtMenuName.setText(mDrawerTextName[position]);
        holder.vhTxtMenuName.setTextColor(ThemeUtility.getColor(mContext, new int[]{R.attr.navMenuItemNameColor}));
        holder.vhImageViewMenuItem.setImageDrawable(mDrawerIcons.get(position));
        holder.vhllMenuItem.setTag(position);
        if (position == 1) {
            if (unreadNotificationCount > 0) {
                holder.vhNotificationCountView.setVisibility(View.VISIBLE);
                holder.vhNotificationCountView.setText(String.valueOf(unreadNotificationCount));
            } else {
                holder.vhNotificationCountView.setVisibility(View.GONE);
            }
        } else {
            holder.vhNotificationCountView.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return mDrawerIcons.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView vhTxtMenuName;
        ImageView vhImageViewMenuItem;
        RelativeLayout vhllMenuItem;
        TextView vhNotificationCountView;

        ViewHolder(View itemView) {
            super(itemView);
            vhTxtMenuName = (TextView) itemView.findViewById(R.id.normal_item_drawer_text);
            vhImageViewMenuItem = (ImageView) itemView.findViewById(R.id.iv_menu_item);
            vhllMenuItem = (RelativeLayout) itemView.findViewById(R.id.rl_normal_menu_item);
            vhNotificationCountView = (TextView) itemView.findViewById(R.id.ectv_notification_counter);
            vhllMenuItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = Integer.valueOf(v.getTag().toString());
                    mMenuItemClick.onMenuItemClick(position);
                }
            });
        }
    }

}
