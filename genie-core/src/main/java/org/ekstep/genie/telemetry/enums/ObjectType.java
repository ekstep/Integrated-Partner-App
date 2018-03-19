package org.ekstep.genie.telemetry.enums;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Sneha on 11/22/2017.
 */

@Retention(RetentionPolicy.SOURCE)
@StringDef({ObjectType.CONTENT, ObjectType.USER})
public @interface ObjectType {
    String CONTENT = "Content";
    String USER = "User";
}
