package org.ekstep.genie.telemetry.enums;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by swayangjit_gwl on 7/27/2016.
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({FeedbackContextType.CONTENT, FeedbackContextType.APP})
public @interface FeedbackContextType {
    String CONTENT = "Content";
    String APP = "App";
}
