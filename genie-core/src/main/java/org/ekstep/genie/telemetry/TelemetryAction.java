package org.ekstep.genie.telemetry;

/**
 * Created by anil on 5/17/2016.
 */
public interface TelemetryAction {

    String ADD_CHILD_INITIATE = "addchild-initiate";
    String ADD_GROUP_INITIATE = "addgroup-initiate";
    String SWITCH_CHILD = "switch-child";
    String ADD_CHILD_SUCCESS = "addchild-success";
    String ADD_GROUP_SUCCESS = "addgroup-success";
    String CONTENT_DOWNLOAD_INITIATE = "ContentDownload-Initiate";
    String CONTENT_DOWNLOAD_SUCCESS = "ContentDownload-Success";
    String CONTENT_DOWNLOAD_CANCEL = "ContentDownload-Cancel";
    String CONTENT_IMPORT_INITIATED = "ContentImport-Initiated";
    String CONTENT_IMPORT_SUCCESS = "ContentImport-Success";
    String LANGUAGE_SETTINGS_INITIATE = "language-settings-initiate";
    String LANGUAGE_SETTINGS_SUCCESS = "language-settings-success";
    String SHARE_GENIE_INITIATED = "ShareGenie-Initiated";
    String SHARE_GENIE_SUCCESS = "ShareGenie-Success";
    String SHARE_CONTENT_INITIATED = "sharecontent-initiated";
    String SHARE_CONTENT_SUCCESS = "sharecontent-success";
    String SHARE_CONTENT_LINK = "ShareContent-Link";
    String MANUAL_SYNC_INITIATED = "manualsync-initiated";
    String MANUAL_SYNC_SUCCESS = "manualsync-success";
    String AUTO_SYNC_INITIATED = "autosync-initiated";
    String AUTO_SYNC_SUCCESS = "autosync-success";
    String SEARCH_RESULT_CLICK = "SearchResultClick";
    String PROFILE_IMPORT_INITIATE = "profileimport-initiate";
    String PROFILE_IMPORT_SUCCESS = "profileimport-success";
    String SHARE_PROFILE_INITIATE = "shareprofile-initiate";
    String SHARE_PROFILE_SUCCESS = "shareprofile-success";
    String DELETE_CONTENT_INITIATED = "deletecontent-initiated";
    String ADD_TAG_MANUAL = "addtag-manual";
    String ADD_TAG_DEEP_LINK = "addtag-deeplink";
    String ADD_TAG_PARTNER = "AddTag-Partner";
    String DELETE_CHILD_INITIATED = "deletechild-initiated";
    String DELETE_GROUP_INITIATED = "deletegroup-initiated";
    String CONTENT_CLICKED = "content-clicked";
    String ADD_CHILD_SKIPPED = "addchild-skipped";
    String CHANGE_SUBJECT_SKIPPED = "changesubject-skipped";
    String GO_TO_LIBRARY_SKIPPED = "GoToLibrary-Skipped";
    String SEARCH_LESSON_SKIPPED = "SearchLesson-Skipped";


    String SUBJECT_CHANGED = "subject-changed";
    String SUBJECT_RESET = "subject-reset";

    String VIEW_MORE_CLICKED = "viewmore-clicked";

    String NOTIFICATION_RECEIVED = "Notification-Received";
    String NOTIFICATION_DISPLAYED = "Notification-Displayed";
    String NOTIFICATION_CLICKED = "notification-clicked";

    String SECTION_VIEWED = "section-viewed";
    String BACKGROUND = "background";
    String RESUME = "resume";

}
