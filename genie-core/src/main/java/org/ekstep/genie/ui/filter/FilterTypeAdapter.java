package org.ekstep.genie.ui.filter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.util.FontUtil;
import org.ekstep.genieservices.commons.bean.ContentSearchFilter;

import java.util.List;

/**
 * Created on 1/24/2016.
 *
 * @author swayangjit_gwl
 */
public class FilterTypeAdapter extends RecyclerView.Adapter<FilterTypeAdapter.ViewHolder> {

    private Context mContext;
    private FilterContract.Presenter mFilterPresenter;
    private int mSelectedMenu;
    private List<ContentSearchFilter> mFilters;

    public FilterTypeAdapter(Context context, FilterContract.Presenter presenter) {
        this.mContext = context;
        this.mFilterPresenter = presenter;
    }

    public void setData(List<ContentSearchFilter> filters) {
        this.mFilters = filters;
    }

    @Override
    public FilterTypeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_filter_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        String resourceName = FontUtil.getInstance().getResourceName(mFilters.get(position).getName());

        viewHolder.vhTxtMenuName.setText(resourceName);

        if (mSelectedMenu == position) {
            viewHolder.vhTxtMenuName.setBackgroundColor(mContext.getResources().getColor(R.color.filter_color));
            viewHolder.vhTxtMenuName.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            viewHolder.vhTxtMenuName.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
            viewHolder.vhTxtMenuName.setTextColor(mContext.getResources().getColor(R.color.profile_background));
        }

        viewHolder.vhTxtMenuName.setTag(position);
    }

    public ContentSearchFilter getItem(int index) {
        return mFilters.get(index);
    }

    @Override
    public int getItemCount() {
        return mFilters != null ? mFilters.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView vhTxtMenuName;

        public ViewHolder(View itemView) {
            super(itemView);
            vhTxtMenuName = (TextView) itemView.findViewById(R.id.txt_drawer);

            vhTxtMenuName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedMenu = (Integer) v.getTag();
                    mFilterPresenter.handleFilterTypeItemClick(mSelectedMenu);
                }
            });
        }
    }

}
