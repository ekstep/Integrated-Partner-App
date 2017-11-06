package org.ekstep.genie.telemetry.enums;

import android.support.annotation.StringDef;

/**
 * Created by swayangjit_gwl on 7/27/2016.
 */
@StringDef({FeedbackContextType.CONTENT, FeedbackContextType.APP})
public @interface FeedbackContextType {
    String CONTENT = "Content";
    String APP = "App";
}
