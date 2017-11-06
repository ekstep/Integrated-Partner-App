package org.ekstep.ipa.provider;

import org.ekstep.genieproviders.summarizer.AbstractSummarizerProvider;
import org.ekstep.ipa.BuildConfig;

/**
 * Created on 9/6/17.
 * shriharsh
 */

public class SummarizerProvider extends AbstractSummarizerProvider {
    @Override
    public String getPackageName() {
        return BuildConfig.APPLICATION_ID;
    }
}
