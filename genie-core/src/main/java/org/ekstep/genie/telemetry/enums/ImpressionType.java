package org.ekstep.genie.telemetry.enums;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Sneha on 11/22/2017.
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({ImpressionType.LIST, ImpressionType.DETAIL, ImpressionType.VIEW,
        ImpressionType.EDIT, ImpressionType.WORKFLOW, ImpressionType.SEARCH})
public @interface ImpressionType {

    String LIST = "list";
    String DETAIL = "detail";
    String VIEW = "view";
    String EDIT = "edit";
    String WORKFLOW = "workflow";
    String SEARCH = "search";
}
