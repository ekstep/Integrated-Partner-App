package org.ekstep.genie.util;

import android.net.Uri;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;

/**
 * Created on 19/9/17.
 * shriharsh
 */

public class DeepLinkUtility {

    public static boolean isDeepLink(Uri intentData){
        if (intentData.getScheme().equalsIgnoreCase(CoreApplication.getInstance().getString(R.string.deeplink_scheme_ekstep))) {
            return true;
        }

        return false;
    }

    public static boolean isDeepLinkSetTag(Uri intentData) {
        if (intentData.getHost().equalsIgnoreCase(CoreApplication.getInstance().getString(R.string.deeplink_ekstep_host_set_tag))) {
            return true;
        }

        return false;
    }

    public static boolean isDeepLinkHttp(Uri intentData) {
        if (intentData.getScheme().equalsIgnoreCase(CoreApplication.getInstance().getString(R.string.deeplink_scheme_http))) {
            return true;
        }

        return false;
    }

    public static boolean isDeepLinkHttps(Uri intentData) {
        if (intentData.getScheme().equalsIgnoreCase(CoreApplication.getInstance().getString(R.string.deeplink_scheme_https))) {
            return true;
        }

        return false;
    }
}
