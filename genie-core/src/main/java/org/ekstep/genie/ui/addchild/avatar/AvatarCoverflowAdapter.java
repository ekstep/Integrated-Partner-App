package org.ekstep.genie.ui.addchild.avatar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makeramen.roundedimageview.RoundedImageView;

import org.ekstep.genie.R;
import org.ekstep.genie.customview.FancyCoverFlow;
import org.ekstep.genie.customview.FancyCoverFlowAdapter;
import org.ekstep.genie.util.AvatarUtil;
import org.ekstep.genie.util.Util;
import org.ekstep.genieservices.commons.utils.StringUtil;

import java.util.Map;

public class AvatarCoverflowAdapter extends FancyCoverFlowAdapter {
    public int mSelected = 0;
    private Context mContext = null;
    private boolean mIsGroupUser;
    private Map<String, Integer> mBadges = null;
    private Map<String, Integer> mAvatars = null;
    private Map<String, Integer> mSelectedBadges = null;

    public AvatarCoverflowAdapter(Context context, boolean isGroupUser, String gender) {
        mContext = context;
        mIsGroupUser = isGroupUser;
        if (!StringUtil.isNullOrEmpty(gender)) {
            if (gender.equalsIgnoreCase("male")) {
                mAvatars = AvatarUtil.populateMaleAvatars();
            } else {
                mAvatars = AvatarUtil.populateFemaleAvatars();
            }
        } else {
            mBadges = AvatarUtil.populateBadges();
        }

        mSelectedBadges = AvatarUtil.populateSelectedBadges();
    }

    public void setSelectedPosition(int position) {
        mSelected = position;
    }

    @Override
    public int getCount() {
        if (mIsGroupUser) {
            return mBadges.keySet().size();
        } else {
            return mAvatars.keySet().size();
        }
    }

    @Override
    public Integer getItem(int i) {
        if (mIsGroupUser) {
            return AvatarUtil.getAvatar(mBadges, i);
        } else {
            return AvatarUtil.getAvatar(mAvatars, i);
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public Integer getSelectedAvatar() {
        return mSelected;
    }

    @Override
    public View getCoverFlowItem(final int position, View reuseableView, ViewGroup viewGroup) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_avatar_item, null);

        final RoundedImageView coverImage = (RoundedImageView) view.findViewById(R.id.cover_image);
        coverImage.setImageBitmap(null);

        int dimension = Util.convertDp(mContext, R.dimen.avatar_height);
        view.setLayoutParams(new FancyCoverFlow.LayoutParams(dimension, dimension));

        int coverImageRes;
        if (mIsGroupUser) {
            if (mSelected == position) {
                coverImageRes = AvatarUtil.getAvatar(mSelectedBadges, position);
            } else {
                coverImageRes = AvatarUtil.getAvatar(mBadges, position);
            }
        } else {
            coverImageRes = AvatarUtil.getAvatar(mAvatars, position);
            if (mSelected == position) {
                coverImage.setBorderColor(mContext.getResources().getColor(R.color.white_transparent1));
            } else {
                coverImage.setBorderColor(Color.TRANSPARENT);
            }
        }

        coverImage.setImageResource(coverImageRes);

        view.setTag(position);

        return view;
    }

    public void getEditedAvatarPosition(String avatar, boolean isGroup) {
        Map<String, Integer> avatars;
        if (isGroup) {
            avatars = mBadges;
        } else {
            avatars = mAvatars;
        }
        mSelected = AvatarUtil.getPosition(avatars, avatar);
    }

}
