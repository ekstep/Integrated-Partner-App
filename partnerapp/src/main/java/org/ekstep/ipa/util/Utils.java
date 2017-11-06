package org.ekstep.ipa.util;

import android.os.Bundle;

import org.ekstep.genie.BuildConfig;
import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.LaunchUtil;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genieservices.IPartnerService;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.PartnerData;
import org.ekstep.ipa.PartnerApp;
import org.ekstep.ipa.model.Partner;
import org.ekstep.ipa.model.PartnerConfig;
import org.ekstep.ipa.model.Tag;

import java.util.HashMap;
import java.util.List;

/**
 * @author vinayagasundar
 */

public class Utils {

    private static final String TAG = "Utils";

    public static void registerPartnerIfNeed() {
        PartnerConfig partnerConfig = PartnerApp.getPartnerApp()
                .getPartnerConfig();


        if (partnerConfig == null) {
            return;
        }

        IPartnerService partnerService = CoreApplication.getGenieSdkInstance().getPartnerService();

        String partnerId = partnerConfig.getPartner().getId();
        String publicKey = partnerConfig.getPartner().getPublicKey();


        PartnerData partnerData = new PartnerData(null, null, partnerId, null, publicKey);
        GenieResponse<Void> response = partnerService.registerPartner(partnerData);

        if (response != null) {
            if (response.getStatus()) {
                LogUtil.i(TAG, "Response Success");
                setLaunchParameterIfNeeded(partnerConfig);
            } else {
                LogUtil.d(TAG, "Response Error  " + response.getError());
            }
        }
    }

    private static void setLaunchParameterIfNeeded(PartnerConfig partnerConfig) {

        Bundle bundle = new Bundle();

        Partner partner = partnerConfig.getPartner();

        String [] channel = partner.getChannel().toArray(new String[]{});
        String [] audience = partner.getAudience().toArray(new String[]{});

        String mode = "soft";

        bundle.putString(Constant.BUNDLE_KEY_HOME_MODE, mode);
        bundle.putStringArray(Constant.BUNDLE_KEY_PARTNER_CHANNEL_ARRAY, channel);
        bundle.putStringArray(Constant.BUNDLE_KEY_PARTNER_AUDIENCE_ARRAY, audience);

        List<Tag> tag = partner.getTag();

        if (tag != null && tag.size() > 0) {
            Tag partnerTag = tag.get(0);

            HashMap<String, String> programTag = new HashMap<>();

            programTag.put("name", partnerTag.getName());
            programTag.put("desc", partnerTag.getDesc());
            programTag.put("startDate", partnerTag.getStartDate());
            programTag.put("endDate", partnerTag.getEndDate());

            bundle.putSerializable(Constant.BUNDLE_KEY_PARTNER_TAG, programTag);
        }




        bundle.putBoolean(Constant.BUNDLE_KEY_ENABLE_CHILD_SWITCHER, false);
        bundle.putBoolean(Constant.BUNDLE_KEY_ENABLE_ONBOARDING_PROFILE_CREATION, false);
        bundle.putBoolean(Constant.BUNDLE_KEY_ENABLE_ONBOARDING_CHOOSE_SUBJECT, false);
        bundle.putBoolean(Constant.BUNDLE_KEY_ENABLE_LANGUAGE_SELECTION, false);

        LaunchUtil.handlePartnerInfo(bundle, false);
    }
}
