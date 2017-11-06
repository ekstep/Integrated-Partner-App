package org.ekstep.genie.model.enums;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by swayangjit on 29/6/17.
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({SortType.RELEVANCE, SortType.RELEVANCE, SortType.POPULARITY, SortType.NEWEST})
public @interface SortType {

    String RELEVANCE = "";
    String NAME = "name";
    String POPULARITY = "popularity";
    String NEWEST = "lastPublishedOn";
}
