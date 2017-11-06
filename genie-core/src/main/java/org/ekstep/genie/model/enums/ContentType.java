package org.ekstep.genie.model.enums;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by swayangjit on 5/7/17.
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({ContentType.STORY, ContentType.WORKSHEET, ContentType.GAME, ContentType.COLLECTION,
        ContentType.TEXTBOOK, ContentType.TEXTBOOK, ContentType.COURSE, ContentType.LESSIONPLAN})
public @interface ContentType {

    String STORY = "Story";
    String WORKSHEET = "Worksheet";
    String GAME = "Game";
    String COLLECTION = "Collection";
    String TEXTBOOK = "TextBook";
    String TEXTBOOKUNIT = "TextbookUnit";
    String COURSE = "Course";
    String LESSIONPLAN = "LessonPlan";
}
