package org.ekstep.genie.telemetry;

/**
 * Created on 5/16/2016.
 *
 * @author anil
 */
public interface TelemetryConstant {

    String SEARCH_CRITERIA = "SearchCriteria";
    String SEARCH_RESULTS = "SearchResults";
    String SEARCH_PHRASE = "SearchPhrase";
    String EXPLICIT_SEARCH = "explicit-search";
    String IMPLICIT_SEARCH = "implicit-search";

    String POSITION_CLICKED = "PositionClicked";
    String PRESENT_ON_DEVICE = "PresentOnDevice";
    String CHILDREN_WITH_RESULTS = "ChildrenWithResults";
    String CONTENTS_WITH_RESULTS = "ContentsWithResults";
    String ATTEMPTS = "Attempts";
    String CHILDREN_ON_DEVICE = "ChildrenOnDevice";
    String SIZE_OF_FILE_IN_MB = "SizeOfFileInMB";
    String SIZE_OF_FILE_IN_KB = "SizeOfFileInKB";
    String FILTER_CRITERIA = "FilterCriteria";
    String SORT_CRITERIA = "SortCriteria";
    String NAME = "Name";
    String AGE = "Age";
    String GENDER = "Gender";
    String GRADE = "Grade";
    String MEDIUM = "Medium";
    String BOARD = "Board";
    String AVATAR = "Avatar";
    String VALIDATION_ERRORS = "ValidationErrors";
    String AGE_VALIDATION_ERROR = "Age not selected";
    String GENDER_VALIDATION_ERROR = "Gender not selected";
    String GRADE_VALIDATION_ERROR = "Grade not selected";
    String MEDIUM_VALIDATION_ERROR = "Medium not selected";
    String BOARD_VALIDATION_ERROR = "Board not selected";

    String CONTENT_LOCAL_COUNT = "ContentLocalCount";
    String CONTENT_TOTAL_COUNT = "ContentTotalCount";
    String POSITION = "Position";

    String NOTIFICATION_DATA = "NotificationData";
    String NOTIFICATION_COUNT = "NotificationCount";

    String SECTION_NAME = "SectionName";
    String SECTION_VISIBILTY_PERCENTAGE = "VisibilityPercentage";
    String TIME = "Time";
    String CONTENT_PLAY = "content-play";

    String APP = "app";
    String LANGUAGE_SELECTED = "LanguageSelected";
    String SECTIONS = "sections";

    //content type for the start and end event.
    String COLLECTION = "collection";
    String TEXTBOOK = "textbook";
    String CONTENT = "content";

    //modes for the start and end events
    String MODE_PLAY = "play";
    String MODE_CREATE = "create";
    String MODE_EDIT = "edit";

    String ERROR_AUTO_SYNC_FAILED = "auto sync failed";
}
