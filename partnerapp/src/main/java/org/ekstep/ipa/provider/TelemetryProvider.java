package org.ekstep.ipa.provider;

import org.ekstep.genieproviders.telemetry.AbstractTelemetryProvider;
import org.ekstep.ipa.BuildConfig;

/**
 * Created on 1/6/17.
 * shriharsh
 */

public class TelemetryProvider extends AbstractTelemetryProvider {


    @Override
    public String getPackageName() {
        return BuildConfig.APPLICATION_ID;
    }
}
