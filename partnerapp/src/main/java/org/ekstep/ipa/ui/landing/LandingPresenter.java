package org.ekstep.ipa.ui.landing;

import android.app.Activity;
import android.content.Context;

import org.ekstep.genie.base.BaseView;
import org.ekstep.ipa.R;


/**
 * @author vinayagasundar
 */

public class LandingPresenter implements LandingContract.Presenter {

    private LandingContract.View mLandingView;

    private Activity mActivity;

    public LandingPresenter() {
    }


    @Override
    public void start() {
        if (mLandingView != null) {
            mLandingView.showGenieChildFragment();
            mLandingView.setToolbarTitle(mActivity.getString(R.string.partner_app_name));
        }
    }

    @Override
    public void showAddChild() {
        mLandingView.showAddChildFragment();
    }

    @Override
    public void bindView(BaseView view, Context context) {
        mLandingView = (LandingContract.View) view;
        mActivity = (Activity) context;
    }

    @Override
    public void unbindView() {
        mLandingView = null;
        mActivity = null;
    }
}
