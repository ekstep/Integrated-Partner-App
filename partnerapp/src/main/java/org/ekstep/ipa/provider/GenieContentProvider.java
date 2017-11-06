package org.ekstep.ipa.provider;

import org.ekstep.genieproviders.content.AbstractContentProvider;
import org.ekstep.ipa.BuildConfig;

/**
 * Created on 8/6/17.
 * shriharsh
 */

public class GenieContentProvider extends AbstractContentProvider {
    @Override
    public String getPackageName() {
        return BuildConfig.APPLICATION_ID;
    }
}
