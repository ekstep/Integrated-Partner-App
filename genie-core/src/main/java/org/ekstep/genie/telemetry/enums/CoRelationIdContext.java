package org.ekstep.genie.telemetry.enums;

import android.support.annotation.StringDef;

/**
 * Created by swayangjit_gwl on 9/28/2016.
 */
@StringDef({CoRelationIdContext.ONBOARDING, CoRelationIdContext.SEARCH, CoRelationIdContext.PAGE_ASSEMBLE, CoRelationIdContext.NONE})
public @interface CoRelationIdContext {
    String ONBOARDING = "onboarding_corelation_id";
    String SEARCH = "response_message_id";
    String PAGE_ASSEMBLE = "page_assemble_api_resmsgid";
    String NONE = "";
}
