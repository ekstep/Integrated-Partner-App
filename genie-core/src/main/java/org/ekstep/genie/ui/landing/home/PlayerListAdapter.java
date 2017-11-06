package org.ekstep.genie.ui.landing.home;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makeramen.roundedimageview.RoundedImageView;

import org.ekstep.genie.R;
import org.ekstep.genie.customview.EkStepCustomTextView;
import org.ekstep.genie.customview.FancyCoverFlow;
import org.ekstep.genie.customview.FancyCoverFlowAdapter;
import org.ekstep.genie.util.AvatarUtil;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.DeviceUtility;
import org.ekstep.genie.util.FontConstants;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.commons.bean.Profile;

import java.util.List;
import java.util.Map;

public class PlayerListAdapter extends FancyCoverFlowAdapter {

    private int mSelected = 0;

    private Context mContext = null;
    private List<Profile> mProfileList = null;
    private Map<String, Integer> mAvatars = null;
    private Map<String, Integer> mBadges = null;
    private Map<String, Integer> mSelectedBadges = null;

    public PlayerListAdapter(Context context, List<Profile> profileList, int position) {
        mContext = context;
        mProfileList = profileList;
        mSelected = position;
        mAvatars = AvatarUtil.populateSwitchAvatars();
        mBadges = AvatarUtil.populateBadges();
        mSelectedBadges = AvatarUtil.populateSelectedBadges();
    }

    public void setSelectedPosition(int position) {
        mSelected = position;
    }

    @Override
    public int getCount() {
        return mProfileList.size();
    }

    @Override
    public Profile getItem(int i) {
        return mProfileList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getCoverFlowItem(final int position, View reusableView, ViewGroup viewGroup) {
        Profile profile = mProfileList.get(position);
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_avatar_item, null);

        final EkStepCustomTextView nameTv = (EkStepCustomTextView) view.findViewById(R.id.name);
        final RoundedImageView coverImage = (RoundedImageView) view.findViewById(R.id.cover_image);
        adjustUIAccordingLanguage(view);

        int dimension = Util.convertDp(mContext, R.dimen.switch_avatar_dimen);
        view.setLayoutParams(new FancyCoverFlow.LayoutParams(dimension, dimension));

        int coverImageRes;
        if (profile.isGroupUser()) {
            if (mSelected == position) {
                coverImageRes = getGroupImage(profile.getAvatar(), mSelectedBadges);
            } else {
                coverImageRes = getGroupImage(profile.getAvatar(), mBadges);
            }
        } else {
            boolean isAnonymous;
            if ((mAvatars.get(profile.getAvatar()) != null) && !profile.getAvatar().equalsIgnoreCase(Constant.DEFAULT_AVATAR)) {
                isAnonymous = false;
                coverImageRes = mAvatars.get(profile.getAvatar());
            } else {
                isAnonymous = true;
                coverImageRes = R.drawable.avatar_anonymous;
            }

            if (mSelected == position && !isAnonymous) {
                coverImage.setBorderColor(Color.WHITE);
            } else {
                coverImage.setBorderColor(Color.TRANSPARENT);
            }
        }

        coverImage.setImageBitmap(null);
        coverImage.setImageResource(coverImageRes);
        nameTv.setText(profile.getHandle());
        view.setTag(position);

        return view;
    }

    private int getGroupImage(String avatar, Map<String, Integer> group) {
        int imgDrawable;

        if ((group.get(avatar) != null)) {
            imgDrawable = group.get(avatar);
        } else {
            imgDrawable = R.drawable.img_badge01;
        }

        return imgDrawable;
    }

    private void adjustUIAccordingLanguage(View view) {
        if (!PreferenceUtil.getLanguage().equalsIgnoreCase(FontConstants.LANG_ENGLISH)) {
            if (PreferenceUtil.getLanguage().equalsIgnoreCase(FontConstants.LANG_TELUGU)) {
                adjustUIForTelugu(view);
            }
        }
    }

    private void adjustUIForTelugu(View view) {
        view.findViewById(R.id.name).setPadding(0, DeviceUtility.dipToPx((Activity) mContext, 8), 0, 0);
    }

}
