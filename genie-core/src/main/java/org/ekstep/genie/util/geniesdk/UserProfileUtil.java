package org.ekstep.genie.util.geniesdk;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import org.ekstep.genie.R;
import org.ekstep.genie.util.AvatarUtil;

import java.util.Map;

/**
 * Created by swayangjit_gwl on 5/30/2016.
 */
public class UserProfileUtil {

public static void setProfileAvatar(ImageView imageView, String avatar, boolean isGroupUser) {
    imageView.setVisibility(View.VISIBLE);

    if (!isGroupUser) {
        Map<String, Integer> avatars = AvatarUtil.populateSwitchAvatars();
        if (!TextUtils.isEmpty(avatar) && avatars.get(avatar) != null) {
            imageView.setImageResource(avatars.get(avatar));
        } else {
            imageView.setImageResource(R.drawable.avatar_anonymous);
        }
    } else {
        Map<String, Integer> badges = AvatarUtil.populateBadges();
        if (!TextUtils.isEmpty(avatar) && badges.get(avatar) != null) {
            imageView.setImageResource(badges.get(avatar));
        } else {
            imageView.setImageResource(R.drawable.avatar_anonymous);
        }
    }
}

}
