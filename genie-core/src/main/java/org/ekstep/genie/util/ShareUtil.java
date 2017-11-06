package org.ekstep.genie.util;

import android.app.Activity;
import android.content.Intent;

import org.ekstep.genie.R;
import org.ekstep.genie.activity.BaseActivity;
import org.ekstep.genie.ui.share.ShareActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.ekstep.genie.ui.share.ShareActivity.REQUEST_CODE_SHARE;
import static org.ekstep.genie.ui.share.ShareActivity.REQUEST_CODE_SHARE_GENIE;
import static org.ekstep.genie.ui.share.ShareActivity.REQUEST_CODE_SHARE_GENIE_CONFIGURATIONS;
import static org.ekstep.genie.ui.share.ShareActivity.REQUEST_CODE_SHARE_PROFILE;
import static org.ekstep.genie.ui.share.ShareActivity.REQUEST_CODE_SHARE_TELEMETRY;

/**
 * Created by Kannappan on 12/26/2016.
 */
public class ShareUtil {
    /**
     * Starts ShareActivity to share content
     *
     * @param activity       Context.
     * @param isEcarNLink    whether the intent type is both file and  text or not.
     * @param values         HashMap containing Contnet info.
     * @param identifierList whether the file is epar or not.
     * @return void.
     */
    public static void startContetExportIntent(Activity activity, boolean isEcarNLink, Map<String, String> values, List<String> identifierList) {
//        if (isContentPathExists(identifierList)) {
            Intent shareIntent = new Intent(activity, ShareActivity.class);
            shareIntent.putExtra(Constant.IS_ECAR_N_LINK, isEcarNLink);
            shareIntent.putStringArrayListExtra(Constant.SHARE_IDENTIFIER, (ArrayList<String>) identifierList);
            shareIntent.putExtra(Constant.SHARE_NAME, values.get(Constant.SHARE_NAME));
            shareIntent.putExtra(Constant.SHARE_SCREEN_NAME, values.get(Constant.SHARE_SCREEN_NAME));
            activity.startActivityForResult(shareIntent, REQUEST_CODE_SHARE);
            activity.overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
//        }

    }

    /**
     * Starts ShareActivity to share content Link
     *
     * @param activity       Context.
     * @param isLink         whether the intent type  is text or not.
     * @param values         HashMap containing Contnet info.
     * @param identifierList whether the file is epar or not.
     * @return void.
     */
    public static void startContentLinkIntent(Activity activity, boolean isLink, Map<String, String> values, List<String> identifierList) {
        Intent shareIntent = new Intent(activity, ShareActivity.class);
        shareIntent.putExtra(Constant.IS_LINK, isLink);
        shareIntent.putStringArrayListExtra(Constant.SHARE_IDENTIFIER, (ArrayList<String>) identifierList);
        shareIntent.putExtra(Constant.SHARE_NAME, values.get(Constant.SHARE_NAME));
        shareIntent.putExtra(Constant.SHARE_SCREEN_NAME, values.get(Constant.SHARE_SCREEN_NAME));

        activity.startActivityForResult(shareIntent, REQUEST_CODE_SHARE);
        activity.overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

    /**
     * Starts ShareActivity to share profile
     *
     * @param activity Context.
     * @param uidList  List of uids of profiles
     * @param values   HashMap containing Contnet info.
     * @return void.
     */
    public static void startProfileExportIntent(Activity activity, List<String> uidList, Map<String, String> values) {
        Intent shareIntent = new Intent(activity, ShareActivity.class);
        shareIntent.putExtra(Constant.IS_PROFILE, true);
        shareIntent.putExtra(Constant.SHARE_SCREEN_NAME, values.get(Constant.SHARE_SCREEN_NAME));
        shareIntent.putExtra(Constant.SHARE_NAME, values.get(Constant.SHARE_NAME));
        shareIntent.putStringArrayListExtra(Constant.SHARE_IDENTIFIER, (ArrayList<String>) uidList);

        activity.startActivityForResult(shareIntent, REQUEST_CODE_SHARE_PROFILE);
        activity.overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

    /**
     * Starts ShareActivity to share Telemetry(.gsa file)
     *
     * @param activity Context.
     * @return void.
     */
    public static void startTelemetryExportIntent(Activity activity) {
        Intent shareIntent = new Intent(activity, ShareActivity.class);
        shareIntent.putExtra(Constant.IS_TELEMETRY, true);
        shareIntent.putStringArrayListExtra(Constant.SHARE_IDENTIFIER, new ArrayList<String>());

        activity.startActivityForResult(shareIntent, REQUEST_CODE_SHARE_TELEMETRY);
        activity.overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

    /**
     * Starts ShareActivity to share Genie
     *
     * @param activity Context.
     * @return void.
     */
    public static void startGenieShareIntent(Activity activity) {
        Intent shareIntent = new Intent(activity, ShareActivity.class);
        shareIntent.putExtra(Constant.IS_GENIE, true);

        activity.startActivityForResult(shareIntent, REQUEST_CODE_SHARE_GENIE);
        activity.overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

    /**
     * Starts ShareActivity to share Genie Configuarations
     *
     * @param activity Context.
     * @return void.
     */
    public static void startGenieConfigurationsShareIntent(Activity activity) {
        Intent shareIntent = new Intent(activity, ShareActivity.class);
        shareIntent.putExtra(Constant.IS_GENIE_CONFIGURATIONS, true);

        activity.startActivityForResult(shareIntent, REQUEST_CODE_SHARE_GENIE_CONFIGURATIONS);
        activity.overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }
}
