package org.ekstep.genie.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.analytics.CampaignTrackingReceiver;

import org.ekstep.genie.model.UTMData;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genieservices.commons.utils.StringUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created on 11/21/2016.
 *
 * @author anil
 */
public class ReferrerReceiver extends BroadcastReceiver {

    private static final String TAG = "ReferrerReceiver";

    private static final String UTM_CAMPAIGN = "utm_campaign";
    private static final String UTM_SOURCE = "utm_source";
    private static final String UTM_MEDIUM = "utm_medium";
    private static final String UTM_TERM = "utm_term";
    private static final String UTM_CONTENT = "utm_content";


    private final String[] sources = {
            UTM_CAMPAIGN, UTM_SOURCE, UTM_MEDIUM, UTM_TERM, UTM_CONTENT
    };

    private static Map<String, String> getHashMapFromQuery(String query) throws UnsupportedEncodingException {

        Map<String, String> query_pairs = new LinkedHashMap<>();

        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }

        return query_pairs;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();

        if (extras == null) {
            return;
        }

        String referrerString = extras.getString("referrer");

        LogUtil.i(TAG, "referrerString: " + referrerString);

        if (!StringUtil.isNullOrEmpty(referrerString)) {
            try {
                Map<String, String> getParams = getHashMapFromQuery(referrerString);

                UTMData utmData = new UTMData();
                utmData.setAction("INSTALL");

                for (String sourceType : sources) {
                    String source = getParams.get(sourceType);

                    if (source != null) {
                        if (sourceType.equalsIgnoreCase(UTM_CAMPAIGN)) {
                            utmData.setUtmcampaign(source);
                        } else if (sourceType.equalsIgnoreCase(UTM_SOURCE)) {
                            utmData.setUtmsource(source);
                        } else if (sourceType.equalsIgnoreCase(UTM_MEDIUM)) {
                            utmData.setUtmmedium(source);
                        } else if (sourceType.equalsIgnoreCase(UTM_TERM)) {
                            utmData.setUtmterm(source);
                        } else if (sourceType.equalsIgnoreCase(UTM_CONTENT)) {
                            utmData.setUtmcontent(source);
                        }
                    }
                }
                //        TODO: (s)to be implemented
//                TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEUpdate(utmData));

            } catch (UnsupportedEncodingException e) {
                LogUtil.e(TAG, "Referrer Error: " + e.getMessage());
            } finally {
                // Pass along to google
                CampaignTrackingReceiver receiver = new CampaignTrackingReceiver();
                receiver.onReceive(context, intent);
            }
        }

    }

}
