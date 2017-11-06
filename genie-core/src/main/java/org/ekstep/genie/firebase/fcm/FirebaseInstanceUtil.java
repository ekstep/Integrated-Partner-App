package org.ekstep.genie.firebase.fcm;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by Kannappan on 8/19/2016.
 */
public class FirebaseInstanceUtil {

    private static FirebaseInstanceId sFirebaseInstanceId;

    public static FirebaseInstanceId getFirebaseInstanceId() {
        if (sFirebaseInstanceId == null) {
            sFirebaseInstanceId = FirebaseInstanceId.getInstance();
        }
        return sFirebaseInstanceId;
    }

    public static void setFirebaseInstanceId(FirebaseInstanceId firebaseInstanceId) {
        sFirebaseInstanceId = firebaseInstanceId;
    }
}
