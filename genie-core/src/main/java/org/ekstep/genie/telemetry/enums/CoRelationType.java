package org.ekstep.genie.telemetry.enums;

/**
 * Created by swayangjit_gwl on 9/28/2016.
 */

import android.support.annotation.StringDef;

@StringDef({CoRelationType.API, CoRelationType.ONBRDNG})

public @interface CoRelationType {
    String API = "api";
    String ONBRDNG = "onbrdng";
}

