package org.ekstep.genie.ui.managechild;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.util.DeviceUtility;
import org.ekstep.genie.util.FontConstants;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.commons.bean.Profile;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created on 18-04-2016.
 *
 * @author GoodWorkLabs
 */
public class ManageChildAdapter extends RecyclerView.Adapter<ManageChildAdapter.ViewHolder> {

    private Context mContext = null;
    private List<Profile> mUserList = null;
    private Callback mCallback = null;
    private Map<String, Integer> mBadges = null;
    private Set<String> mSelected = null;
    private boolean mShowSelector;
    private float mAgeMargin;
    private float mClassMargin;

    public ManageChildAdapter(Context context, List<Profile> userList, Callback callback, Map<String, Integer> badges) {
        mContext = context;
        mUserList = userList;
        mCallback = callback;
        mBadges = badges;
        mAgeMargin = mContext.getResources().getDimension(R.dimen.child_age_margin);
        mClassMargin = mContext.getResources().getDimension(R.dimen.child_class_margin);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user_item, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Profile profile = mUserList.get(position);
        if (mShowSelector) {
            holder.vhLayoutMain.setActivated(true);
            holder.vhChkTick.setVisibility(View.VISIBLE);
        } else {
            holder.vhLayoutMain.setActivated(false);
            holder.vhChkTick.setVisibility(View.GONE);
        }

        if (mSelected.contains(profile.getUid())) {
            holder.vhChkTick.setImageResource(R.drawable.ic_check);
        } else {
            holder.vhChkTick.setImageResource(R.drawable.ic_uncheck);
        }

        if ((mBadges.get(profile.getAvatar()) != null)) {
            holder.vhImg_User.setImageResource(mBadges.get(profile.getAvatar()));
        } else {
            holder.vhImg_User.setImageResource(R.drawable.avatar_anonymous);
        }

        holder.vhtxt_UserName.setText(profile.getHandle());

        holder.vhTxt_Age_Label.setText(mContext.getString(R.string.label_all_age));
        if (profile.getAge() > 0) {
            if (isKannadaOrTelugu()) {
                setLayoutParams(holder.vhTxt_Age_Label, mAgeMargin);
            }
            holder.vhTxt_Age.setText(": " + profile.getAge() + " yrs");

        } else if (profile.isGroupUser()) {
            holder.vhLayoutAge.setVisibility(View.GONE);
        } else {
            holder.vhTxt_Age.setText(": " + mContext.getString(R.string.label_children_other));
        }

        if (profile.getStandard() != -99) {
            setStandard(profile.getStandard(), holder.vhTxt_Class, holder.vhTxt_Class_Label);
        } else {
            holder.vhLayoutClass.setVisibility(View.GONE);
        }
        holder.vhLayoutMore.setTag(profile);
        holder.vhLayoutMain.setTag(profile);

        if (PreferenceUtil.getLanguage().equalsIgnoreCase(FontConstants.LANG_TAMIL)) {
            holder.vhLayoutAge.setOrientation(LinearLayout.VERTICAL);
        }
    }

    private void setStandard(int standard, TextView txtStandard, TextView txtLabel) {
        if (isKannadaOrTelugu()) {
            setLayoutParams(txtLabel, mClassMargin);
        }
        txtLabel.setText(mContext.getString(R.string.label_addchild_class));
        switch (standard) {
            case -1:
                txtStandard.setText(": " + mContext.getString(R.string.label_addchild_others));
                break;
            case 0:
                txtStandard.setText(": " + mContext.getString(R.string.label_addchild_kg));
                break;
            default:
                txtStandard.setText(": " + standard);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public void setSelectedUid(Set<String> selected) {
        mSelected = selected;
    }

    public void clearSelected() {
        mSelected.clear();
        notifyDataSetChanged();
    }

    public Set<String> getSelectedItems() {
        return mSelected;
    }

    public int getSelectedCount() {
        return mSelected.size();
    }

    private void setLayoutParams(View view, float padding) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        layoutParams.setMargins(0, DeviceUtility.dp2px(mContext, padding), 0, 0);
        view.setLayoutParams(layoutParams);
    }

    public void showSelectorLayer(boolean status) {
        mShowSelector = status;
        notifyDataSetChanged();
    }

    private boolean isKannadaOrTelugu() {
        if (PreferenceUtil.getLanguage().equalsIgnoreCase(FontConstants.LANG_KANNADA) || PreferenceUtil.getLanguage().equalsIgnoreCase(FontConstants.LANG_TELUGU)) {
            return true;
        }
        return false;
    }

    public interface Callback {
        void onIconClicked(Profile profile, boolean isSelected);

        void onMoreIconClicked(Profile profile, boolean isSelected);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        private ImageView vhImg_User;
        private TextView vhtxt_UserName;
        private LinearLayout vhLayoutAge = null;
        private TextView vhTxt_Age;
        private TextView vhTxt_Age_Label;
        private View vhLayoutClass = null;
        private TextView vhTxt_Class_Label;
        private TextView vhTxt_Class;
        private RelativeLayout vhLayoutMain = null;
        private View vhLayoutMore = null;
        private ImageView vhChkTick = null;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            vhImg_User = (ImageView) itemView.findViewById(R.id.img_user_profile);
            vhtxt_UserName = (TextView) itemView.findViewById(R.id.txt_user_name);
            vhLayoutAge = (LinearLayout) itemView.findViewById(R.id.layout_age);
            vhTxt_Age = (TextView) itemView.findViewById(R.id.txt_age);
            vhTxt_Age_Label = (TextView) itemView.findViewById(R.id.txt_age_label);
            vhLayoutClass = itemView.findViewById(R.id.layout_class);
            vhTxt_Class_Label = (TextView) itemView.findViewById(R.id.txt_class_label);
            vhTxt_Class = (TextView) itemView.findViewById(R.id.txt_class);
            vhLayoutMain = (RelativeLayout) itemView.findViewById(R.id.layout_main);
            vhLayoutMore = itemView.findViewById(R.id.layout_more);
            vhChkTick = (ImageView) itemView.findViewById(R.id.chk_tick);

            vhLayoutMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallback != null) {
                        mCallback.onMoreIconClicked((Profile) v.getTag(), mShowSelector);
                    }
                }
            });

            vhLayoutMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallback != null) {
                        mCallback.onIconClicked((Profile) v.getTag(), mShowSelector);
                    }
                }
            });
        }
    }

}
