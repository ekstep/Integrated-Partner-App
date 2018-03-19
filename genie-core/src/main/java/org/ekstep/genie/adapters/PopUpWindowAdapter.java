package org.ekstep.genie.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.callback.IMenuItemClick;

import java.util.List;

/**
 * Created on 1/24/2016.
 *
 * @author swayangjit_gwl
 */
public class PopUpWindowAdapter extends RecyclerView.Adapter<PopUpWindowAdapter.ViewHolder> {

    private List<String> mList;
    private IMenuItemClick mMenuItemClick = null;

    public PopUpWindowAdapter(List<String> list, IMenuItemClick menuItemClick) {
        this.mList = list;
        this.mMenuItemClick = menuItemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_pop_up_window_row, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        String listItem = mList.get(position);

        viewHolder.vhTvItem.setText(listItem);

        viewHolder.view.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        private TextView vhTvItem;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            view = itemLayoutView;
            vhTvItem = (TextView) itemLayoutView.findViewById(R.id.item);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = Integer.valueOf(v.getTag().toString());
                    mMenuItemClick.onMenuItemClick(position);
                }
            });
        }
    }

}