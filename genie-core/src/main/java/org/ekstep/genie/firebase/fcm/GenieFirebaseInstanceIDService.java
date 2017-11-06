package org.ekstep.genie.firebase.fcm;

import android.text.TextUtils;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.preference.PreferenceUtil;

/**
 * Created on 7/28/2016.
 *
 * @author anil
 */
public class GenieFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "GenieFirebaseInstanceIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        LogUtil.d(TAG, "Refreshed token: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
        if (!TextUtils.isEmpty(token)) {
            PreferenceUtil.setFcmToken(token);
        }
    }
}
