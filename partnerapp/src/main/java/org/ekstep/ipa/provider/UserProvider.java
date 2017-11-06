package org.ekstep.ipa.provider;

import org.ekstep.genieproviders.user.AbstractUserProvider;
import org.ekstep.ipa.BuildConfig;

/**
 * Created on 1/6/17.
 * shriharsh
 */

public class UserProvider extends AbstractUserProvider {

    @Override
    public String getPackageName() {
        return BuildConfig.APPLICATION_ID;
    }

}
