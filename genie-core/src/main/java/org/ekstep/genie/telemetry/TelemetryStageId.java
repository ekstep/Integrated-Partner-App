package org.ekstep.genie.telemetry;

/**
 * Created on 5/17/2016.
 *
 * @author anil
 */
public interface TelemetryStageId {

    String SPLASH = "Splash";


    // Genie Home OnBoardingScreen - Onboarding in Genie Home
    String GENIE_HOME_ONBOARDING_SCREEN = "Genie-Home-OnBoardingScreen";

    // Genie Home - New Genie Home Screen that lists all content on the device along with contents
    String GENIE_HOME = "Genie-Home";

    // Genie Home Section  - Genie Home Section
    String GENIE_HOME_SECTION = "Genie-Home-Section";

    // Manage Content - My Content tab on Manage Content
    String MY_CONTENT = "MyContent";

    // Import Content - Import Content
    String IMPORT_CONTENT = "ImportContent";

    // Search Screen - When the user searches for something explicitly
    String CONTENT_SEARCH = "ContentSearch";

    // Content Detail Screen - Content Detail Screen
    String CONTENT_DETAIL = "ContentDetail";

    // List Screen - Content List Screen that is not coming from a user search
    String CONTENT_LIST = "ContentList";

    // Filters Screen - When the user hits "Filter" button on list screen
    String FILTER = "Filter";

    // Manage Children - Manage Children Tab on Manage Children Screen
    String MANAGE_CHILDREN = "ManageChildren";

    // Manage Groups - Manage Groups Tab on Manage Children Screen
    String MANAGE_GROUPS = "ManageGroups";

    // Summarizer you reach from Manage Content Screen
    String SUMMARIZER_CONTENT_SUMMARY = "SummarizerContentSummary";
    String SUMMARIZER_CHILD_SUMMARY = "SummarizerChildSummary";
    String SUMMARIZER_CHILD_CONTENT_DETAILS = "SummarizerChildContentDetails";

    // Settings Screen View - Settings main screen
    String SETTINGS_HOME = "Settings-Home";

    // Advanced Settings Screen View - Tags screen
    String SETTINGS_ADVANCED = "Settings-Advanced";
    String ADD_NEW_TAG = "AddNewTag";

    // Settings - Data Usage Settings - New data usage settings screen
    String SETTINGS_DATA_USAGE = "Settings-DataUsage";

    String SETTINGS_LANGUAGE = "Settings-Language";

    String SETTINGS_TRANSFER = "Settings-Transfer";

    String SEARCH_RESULT = "SearchResult";

    // Import Profile - Import Profile
    String IMPORT_PROFILE = "ImportProfile";

    // AddChild Flow
    String ADD_CHILD_NAME = "AddChild-Name";
    String ADD_GROUP_NAME = "AddGroup-Name";
    String ADD_CHILD_AGE_GENDER_CLASS = "AddChild-AgeGenderClass";
    String ADD_CHILD_MEDIUM_BOARD = "AddChild-MediumBoard";
    String ADD_CHILD_AVATAR = "AddChild-Avatar";
    String ADD_GROUP_AVATAR = "AddGroup-Avatar";

    // Notifications
    String NOTIFICATION = "Notification-Screen";
    String SERVER_NOTIFICATION = "Server-Notification";
    String LOCAL_NOTIFICATION = "Local-Notification";
    String NOTIFICATION_LIST = "Notification-List";

    String COLLECTION_HOME = "Collection-Home";
    String TEXTBOOK_HOME = "TextBook-Home";
    String TEXTBOOK_TOC = "TextBook-TOC";

    //Auto/Force sync stageid
    String TELEMETRY_SYNC= "Genie-TelemetrySync";

}
