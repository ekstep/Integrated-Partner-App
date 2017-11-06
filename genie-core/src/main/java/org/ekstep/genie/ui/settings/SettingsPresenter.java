package org.ekstep.genie.ui.settings;

import android.content.Context;

import org.ekstep.genie.base.BaseView;

/**
 * Created by swayangjit on 14/9/17.
 */

public class SettingsPresenter implements SettingsContract.Presenter {

    private Context mContext;
    private SettingsContract.View mSettingsView;

    @Override
    public void bindView(BaseView view, Context context) {
        mContext = context;
        mSettingsView = (SettingsContract.View) view;
    }

    @Override
    public void unbindView() {
        mContext = null;
        mSettingsView = null;
    }

    @Override
    public void handleSettingsListItemClick(int position) {
        mSettingsView.navigateTo(position);
    }
}
