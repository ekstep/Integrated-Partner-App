package org.ekstep.genie.telemetry;

/**
 * Created on 5/17/2016.
 *
 * @author anil
 */
public interface TelemetryStageId {

    String SPLASH = "splash";

    String LANGUAGE_SELECTION = "language-selection";

    // Genie Home OnBoardingScreen - Onboarding in Genie Home
    String GENIE_HOME_ONBOARDING_SCREEN = "genie-home-onboarding";

    // Genie Home - New Genie Home Screen that lists all content on the device along with contents
    String GENIE_HOME = "genie-home";

    // Genie Home Section  - Genie Home Section
    String GENIE_HOME_SECTION = "genie-home-section";

    // Manage Content - My Content tab on Manage Content
    String MY_CONTENT = "mycontent";

    // Import Content - Import Content
    String IMPORT_CONTENT = "ImportContent";

    // Search Screen - When the user searches for something explicitly
    String CONTENT_SEARCH = "content-search";

    // Content Detail Screen - Content Detail Screen
    String CONTENT_DETAIL = "contentdetail";

    //Collection Detail Screen - Collection Detail Screen
    String COLLECTION_DETAIL = "CollectionDetail";

    // List Screen - Content List Screen that is not coming from a user search
    String CONTENT_LIST = "contentlist";

    // Filters Screen - When the user hits "Filter" button on list screen
    String FILTER = "Filter";

    // Manage Children - Manage Children Tab on Manage Children Screen
    String MANAGE_CHILDREN = "manage-children";

    // Manage Groups - Manage Groups Tab on Manage Children Screen
    String MANAGE_GROUPS = "manage-groups";

    // Summarizer you reach from Manage Content Screen
    String SUMMARIZER_CONTENT_SUMMARY = "summarizer-content-summary";
    String SUMMARIZER_CHILD_SUMMARY = "summarizer-child-summary";
    String SUMMARIZER_CHILD_CONTENT_DETAILS = "summarizer-child-content-details";

    // Settings Screen View - Settings main screen
    String SETTINGS_HOME = "settings-home";


    // Advanced Settings Screen View - Tags screen
    String SETTINGS_ADVANCED = "settings-advanced";
    String ADD_NEW_TAG = "add-new-tag";

    // Settings - Data Usage Settings - New data usage settings screen
    String SETTINGS_DATA_USAGE = "settings-datausage";

    String SETTINGS_LANGUAGE = "settings-language";

    String SETTINGS_TRANSFER = "Settings-Transfer";

    String SEARCH_RESULT = "SearchResult";

    // Import Profile - Import Profile
    String IMPORT_PROFILE = "ImportProfile";

    // AddChild Flow
    String ADD_CHILD_NAME = "addchild-name";
    String ADD_GROUP_NAME = "addgroup-name";
    String ADD_CHILD_AGE_GENDER_CLASS = "addchild-agegenderclass";
    String ADD_CHILD_MEDIUM_BOARD = "addchild-mediumboard";
    String ADD_CHILD_AVATAR = "addchild-avatar";
    String ADD_GROUP_AVATAR = "addgroup-avatar";

    // Notifications
    String NOTIFICATION = "notification-list";
    String SERVER_NOTIFICATION = "server-notification";
    String LOCAL_NOTIFICATION = "local-notification";
    String NOTIFICATION_LIST = "notification-list";

    String COLLECTION_HOME = "collection-home";
    String TEXTBOOK_HOME = "textbook-home";
    String TEXTBOOK_TOC = "textbook-toc";

    //Auto/Force sync stageid
    String TELEMETRY_SYNC = "genie-telemetry-sync";

}
