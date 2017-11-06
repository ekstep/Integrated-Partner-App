package org.ekstep.ipa.provider;

import org.ekstep.genieproviders.partner.AbstractPartnerProvider;
import org.ekstep.ipa.BuildConfig;

/**
 * Created on 8/6/17.
 * shriharsh
 */

public class PartnerProvider extends AbstractPartnerProvider {
    @Override
    public String getPackageName() {
        return BuildConfig.APPLICATION_ID;
    }
}
