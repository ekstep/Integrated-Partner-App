package org.ekstep.genie.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.google.firebase.iid.FirebaseInstanceId;

import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.preference.PreferenceUtil;

/**
 * Created on 8/1/2016.
 *
 * @author anil
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    private static final String TAG = NetworkChangeReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        if (activeNetwork != null) {
//            String token = FirebaseInstanceId.getInstance().getToken();
//            LogUtil.d(TAG, "InstanceID token: " + token);
//
//            if (!TextUtils.isEmpty(token)) {
//                PreferenceUtil.setFcmToken(token);
//            }
//        }
    }
}
