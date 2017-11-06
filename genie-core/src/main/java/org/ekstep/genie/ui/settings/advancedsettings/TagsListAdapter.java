package org.ekstep.genie.ui.settings.advancedsettings;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.customview.EkStepCustomTextView;
import org.ekstep.genieservices.commons.bean.Tag;
import org.ekstep.genieservices.commons.utils.StringUtil;

import java.util.List;

/**
 * Created by swayangjit_gwl on 26/10/2015.
 */

public class TagsListAdapter extends RecyclerView.Adapter<TagsListAdapter.ViewHolder> {

    private List<Tag> mTagList = null;
    private Context mContext = null;
    private AdvancedSettingsPresenter mPresenter;

    public TagsListAdapter(Context context, List<Tag> tagList, AdvancedSettingsPresenter basePresenter) {
        mTagList = tagList;
        mContext = context;
        mPresenter =  basePresenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tag_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Tag tag = mTagList.get(position);
        holder.vhTxtTagName.setText(tag.getName());
        if (!StringUtil.isNullOrEmpty(tag.getDescription())) {
            holder.vhTxtTagDesc.setText(tag.getDescription());
            holder.vhTxtTagDesc.setVisibility(View.VISIBLE);
        }

        if (!StringUtil.isNullOrEmpty(tag.getStartDate())) {
            holder.vhTxtTagStartDate.setText(mContext.getResources().getString(R.string.label_tag_start_date) + ": " + tagDateFormatter(tag.getStartDate()));
            holder.vhTxtTagStartDate.setVisibility(View.VISIBLE);
            holder.vhTagDateView.setVisibility(View.VISIBLE);
        }

        if (!StringUtil.isNullOrEmpty(tag.getEndDate())) {
            holder.vhTxtTagEndDate.setText(mContext.getResources().getString(R.string.label_tag_end_date) + ": " + tagDateFormatter(tag.getEndDate()));
            holder.vhTxtTagEndDate.setVisibility(View.VISIBLE);
        }

        holder.vhLayoutDelete.setTag(tag);
        holder.vhLayoutEdit.setTag(tag);
    }

    @Override
    public int getItemCount() {
        return mTagList.size();
    }

    private String tagDateFormatter(String date) {
        String formattedDate = null;
        String[] date_items = date.split("-");
        switch (Integer.parseInt(date_items[1])) {
            case 1:
                date_items[1] = "JAN";
                break;
            case 2:
                date_items[1] = "FEB";
                break;
            case 3:
                date_items[1] = "MAR";
                break;
            case 4:
                date_items[1] = "APR";
                break;
            case 5:
                date_items[1] = "MAY";
                break;
            case 6:
                date_items[1] = "JUN";
                break;
            case 7:
                date_items[1] = "JUL";
                break;
            case 8:
                date_items[1] = "AUG";
                break;
            case 9:
                date_items[1] = "SEP";
                break;
            case 10:
                date_items[1] = "OCT";
                break;
            case 11:
                date_items[1] = "NOV";
                break;
            case 12:
                date_items[1] = "DEC";
                break;
        }

        formattedDate = date_items[2] + "-" + date_items[1] + "-" + date_items[0];

        return formattedDate;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView vhTxtTagName;
        TextView vhTxtTagDesc;
        TextView vhTxtTagStartDate;
        TextView vhTxtTagEndDate;
        View vhTagDateView;
        EkStepCustomTextView vhLayoutDelete;
        EkStepCustomTextView vhLayoutEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            vhTxtTagName = (TextView) itemView.findViewById(R.id.txt_tag_name);
            vhTxtTagDesc = (TextView) itemView.findViewById(R.id.txt_tag_description);
            vhTxtTagStartDate = (TextView) itemView.findViewById(R.id.txt_tag_start_date);
            vhTxtTagEndDate = (TextView) itemView.findViewById(R.id.txt_tag_end_date);
            vhTagDateView = (View) itemView.findViewById(R.id.view_date);
            vhLayoutDelete = (EkStepCustomTextView) itemView.findViewById(R.id.label_delete);
            vhLayoutDelete.setOnClickListener(this);

            vhLayoutEdit = (EkStepCustomTextView) itemView.findViewById(R.id.label_edit);
            vhLayoutEdit.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.label_delete) {//                    mTagDelete.onTagDeleteClick((Tag) v.getTag());
                mPresenter.handleDeleteTag((Tag) v.getTag());

            } else if (i == R.id.label_edit) {
                mPresenter.handleEditTag((Tag) v.getTag());
//                    mTagDelete.onTagEditClick((Tag) v.getTag());

            } else {
            }

        }
    }
}
