package org.ekstep.genie.ui.addchild.welcomeaboard;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.util.AvatarUtil;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genieservices.commons.bean.Profile;

import java.util.Map;


/**
 * Created by swayangjit on 23/12/16.
 */

public class WelcomeAboardPresenter implements WelcomeAboardContract.Presenter {

    private static final String TAG = WelcomeAboardPresenter.class.getSimpleName();
    @NonNull
    private WelcomeAboardContract.View mWelcomeView;
    @NonNull
    private Context mContext;
    private Profile mProfile = null;
    private Map<String, Integer> mAvatars = null;
    private Map<String, Integer> mBadges = null;


    public WelcomeAboardPresenter() {

    }


    @Override
    public void fetchBundleExtras(Bundle bundle) {
        if (bundle != null) {
            mProfile = (Profile) bundle.getSerializable(Constant.BUNDLE_UPDATED_PROFILE);
        }
    }

    @Override
    public void openWelcomeLayout() {
        mAvatars = AvatarUtil.populateAvatars();
        mBadges = AvatarUtil.populateBadges();

        LogUtil.i("Avatar", mProfile.getAvatar());
        String selectedAvatar = mProfile.getAvatar();

        //Set Selected Avatar
        if (mProfile.isGroupUser()) {
            mWelcomeView.showWelcomeAvatar(mBadges.get(selectedAvatar));
        } else {
            mWelcomeView.showWelcomeAvatar(mAvatars.get(selectedAvatar));
        }
        mWelcomeView.showSelectedName(mProfile.getHandle());
    }

    @Override
    public void bindView(BaseView view, Context context) {
        mWelcomeView = (WelcomeAboardContract.View) view;
        mContext = context;
    }

    @Override
    public void unbindView() {
        mWelcomeView = null;
        mContext = null;
    }
}
