package org.ekstep.genie.telemetry.enums;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by swayangjit_gwl on 9/28/2016.
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({CoRelationIdContext.ONBOARDING, CoRelationIdContext.SEARCH, CoRelationIdContext.PAGE_ASSEMBLE, CoRelationIdContext.NONE})
public @interface CoRelationIdContext {
    String ONBOARDING = "onboarding_corelation_id";
    String SEARCH = "response_message_id";
    String PAGE_ASSEMBLE = "page_assemble_api_resmsgid";
    String NONE = "";
}
