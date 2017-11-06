package org.ekstep.ipa.ui.addchild;

import android.content.Context;

import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.util.LogUtil;

/**
 * @author vinayagasundar
 */

public class AddChildPresenter implements AddChildContract.Presenter {

    private static final String TAG = "AddChildPresenter";

    private AddChildContract.View mAddChildView;

    private String mSchoolId = null;
    private String mKlass = null;

    @Override
    public void bindView(BaseView view, Context context) {
        mAddChildView = (AddChildContract.View) view;
    }

    @Override
    public void unbindView() {
        mAddChildView = null;
    }

    @Override
    public void start() {
        if (mSchoolId != null) {
            LogUtil.i(TAG, "School Id value : " + mSchoolId);
        }

        mAddChildView.showChildFilterPage();
        mAddChildView.setToolbarTitle("Add Child");
        mAddChildView.setUpBackNavigation();
    }

    @Override
    public void showSearchChild(String schoolId, String klass) {
        mSchoolId = schoolId;
        mKlass = klass;

        mAddChildView.showSearchChildScreen(mSchoolId, mKlass);
    }

    @Override
    public void handleNavBackClick() {
        mAddChildView.handleBackPress();
    }
}
