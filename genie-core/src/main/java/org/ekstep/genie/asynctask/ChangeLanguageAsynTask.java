package org.ekstep.genie.asynctask;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;
import org.ekstep.genie.ui.landing.LandingActivity;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.FontUtil;
import org.ekstep.genie.util.ShowProgressDialog;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.async.ConfigService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.GenieResponse;

import java.util.Map;

public class ChangeLanguageAsynTask extends AsyncTask<String, Void, Void> {
    private Activity mActivity = null;

    public ChangeLanguageAsynTask(Activity activity) {
        mActivity = activity;
    }

    @Override
    protected void onPreExecute() {
        ShowProgressDialog.showProgressDialog(mActivity, mActivity.getString(R.string.msg_setting_changing_language));
    }

    @Override
    protected Void doInBackground(String... params) {
        PreferenceUtil.setLanguage(params[0]);
        PreferenceUtil.setLanguageSelected(true);
        FontUtil.getInstance().changeLocale();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        ShowProgressDialog.dismissDialog();
        ConfigService configService = CoreApplication.getGenieAsyncService().getConfigService();
        configService.getResourceBundle(PreferenceUtil.getLanguage(), new IResponseHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(GenieResponse<Map<String, Object>> genieResponse) {
                FontUtil.setResourceBundle(genieResponse.getResult());
            }

            @Override
            public void onError(GenieResponse<Map<String, Object>> genieResponse) {

            }
        });

        PreferenceUtil.setViewFrom(Constant.VIEW_FROM_VALUE_CHILD_VIEW);

        mActivity.startActivity(new Intent(mActivity, LandingActivity.class));
        mActivity.finish();
    }

}
