package org.ekstep.genie.telemetry;

/**
 * Created by anil on 5/17/2016.
 */
public interface TelemetryAction {

    String ADD_CHILD_INITIATE = "AddChild-Initiate";
    String ADD_GROUP_INITIATE = "AddGroup-Initiate";
    String SWITCH_CHILD = "SwitchChild";
    String ADD_CHILD_SUCCESS = "AddChild-Success";
    String ADD_GROUP_SUCCESS = "AddGroup-Success";
    String CONTENT_DOWNLOAD_INITIATE = "ContentDownload-Initiate";
    String CONTENT_DOWNLOAD_SUCCESS = "ContentDownload-Success";
    String CONTENT_DOWNLOAD_CANCEL = "ContentDownload-Cancel";
    String CONTENT_IMPORT_INITIATED = "ContentImport-Initiated";
    String CONTENT_IMPORT_SUCCESS = "ContentImport-Success";
    String LANGUAGE_SETTINGS_INITIATE = "LanguageSettings-Initiate";
    String LANGUAGE_SETTINGS_SUCCESS = "LanguageSettings-Success";
    String SHARE_GENIE_INITIATED = "ShareGenie-Initiated";
    String SHARE_GENIE_SUCCESS = "ShareGenie-Success";
    String SHARE_CONTENT_INITIATED = "ShareContent-Initiated";
    String SHARE_CONTENT_SUCCESS = "ShareContent-Success";
    String SHARE_CONTENT_LINK = "ShareContent-Link";
    String MANUAL_SYNC_INITIATED = "ManualSync-Initiated";
    String MANUAL_SYNC_SUCCESS = "ManualSync-Success";
    String AUTO_SYNC_INITIATED = "AutoSync-Initiated";
    String AUTO_SYNC_SUCCESS = "AutoSync-Success";
    String SEARCH_RESULT_CLICK = "SearchResultClick";
    String PROFILE_IMPORT_INITIATE = "ProfileImport-Initiate";
    String PROFILE_IMPORT_SUCCESS = "ProfileImport-Success";
    String SHARE_PROFILE_INITIATE = "ShareProfile-Initiate";
    String SHARE_PROFILE_SUCCESS = "ShareProfile-Success";
    String DELETE_CONTENT_INITIATED = "DeleteContent-Initiated";
    String ADD_TAG_MANUAL = "AddTag-Manual";
    String ADD_TAG_DEEP_LINK = "AddTag-Deeplink";
    String ADD_TAG_PARTNER = "AddTag-Partner";
    String DELETE_CHILD_INITIATED = "DeleteChild-Initiated";
    String DELETE_GROUP_INITIATED = "DeleteGroup-Initiated";
    String CONTENT_CLICKED = "ContentClicked";
    String ADD_CHILD_SKIPPED = "AddChild-Skipped";
    String CHANGE_SUBJECT_SKIPPED = "ChangeSubject-Skipped";
    String GO_TO_LIBRARY_SKIPPED = "GoToLibrary-Skipped";
    String SEARCH_LESSON_SKIPPED = "SearchLesson-Skipped";


    String SUBJECT_CHANGED = "Subject-Changed";
    String SUBJECT_RESET = "Subject-Reset";

    String VIEW_MORE_CLICKED = "ViewMore-Clicked";

    String NOTIFICATION_RECEIVED = "Notification-Received";
    String NOTIFICATION_DISPLAYED = "Notification-Displayed";
    String NOTIFICATION_CLICKED = "Notification-Clicked";

    String SECTION_VIEWED = "Section-Viewed";

}
