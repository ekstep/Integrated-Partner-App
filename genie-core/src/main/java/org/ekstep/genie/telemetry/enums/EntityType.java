package org.ekstep.genie.telemetry.enums;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by anil on 5/17/2016.
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({EntityType.UID, EntityType.SEARCH_PHRASE, EntityType.CONTENT, EntityType.CONTENT_ID, EntityType.CHILD_ID,
        EntityType.LANGUAGE, EntityType.SECTION, EntityType.FILTER, EntityType.SORT, EntityType.NOTIFICATION, EntityType.ONBOARDING})
public @interface EntityType {

    String UID = "UID";
    String SEARCH_PHRASE = "SearchPhrase";
    String CONTENT = "Content";
    String CONTENT_ID = "ContentID";
    String CHILD_ID = "ChildID";
    String LANGUAGE = "Language";
    String SECTION = "SectionId";
    String FILTER = "Filter";
    String SORT = "Sort";
    String NOTIFICATION = "NotificationCount";
    String ONBOARDING = "Onboarding-Complete";

}
