package org.ekstep.genie.telemetry.enums;

/**
 * Created by swayangjit_gwl on 9/28/2016.
 */

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@StringDef({CoRelationType.API, CoRelationType.ONBRDNG})

public @interface CoRelationType {
    String API = "api";
    String ONBRDNG = "onbrdng";
}

