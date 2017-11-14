package org.ekstep.genie.util;

/**
 * Created on 5/9/2016.
 *
 * @author anil
 */
public interface Constant {

    int On_BOARD_STATE_DEFAULT = -1;
    int On_BOARD_STATE_ADD_CHILD = 0;
    int On_BOARD_STATE_CHANGE_SUBJECT = 1;

    String DEFAULT_AVATAR = "@drawable/anonymous";

    String EXTRA_DEEP_LINK_IDENTIFIER = "deep_link_identifier";
    String EXTRA_DEEP_LINK_SEARCH_QUERY = "deep_link_search_query";
    String EXTRA_DEEP_LINK_SEARCH_SORT = "deep_link_search_sort";
    String EXTRA_DEEP_LINK_FILTER = "deep_link_filter";
    String EXTRA_DEEP_LINK_SEARCH_LANGUAGE = "deep_link_search_language";
    String EXTRA_DEEP_LINK_SEARCH_DOMAIN = "deep_link_search_domain";
    String EXTRA_DEEP_LINK_SEARCH_TYPE = "deep_link_search_type";
    String EXTRA_DEEP_LINK_GRADE_LEVEL = "deep_link_grade_level";

    String EXTRA_DEEP_LINK_TRANSFER = "extra_deep_link_transfer";
    String EXTRA_DEEP_LINK_MANAGE_CHILD = "extra_deep_link_manage_child";
    String EXTRA_DEEP_LINK_DATA_SYNC = "extra_deep_link_data_sync";
    String EXTRA_DEEP_LINK_MY_CONTENT = "extra_deep_link_my_content";

    /*Launch extras*/
    String EXTRA_LAUNCH_IS_NOTIFICATION = "extra_launch_is_notification";
    String EXTRA_LAUNCH_IS_DEEPLINK = "extra_launch_is_deeplink";
    String EXTRA_LAUNCH_IS_TAG = "extra_launch_is_tag";
    String EXTRA_LAUNCH_IS_FILE_PATH = "extra_launch_is_file_path";
    String EXTRA_LAUNCH_IS_BY_PARTNER = "extra_launch_is_by_partner";

    String EXTRA_LINK_ABOUT_US = "extra_link_about_us";
    String EXTRA_LINK_TERMS_CONDITION = "extra_link_terms_condition";
    String EXTRA_LINK_PRIVACY_POLICY = "extra_link_privacy_policy";

    String BUNDLE_KEY_NOTIFICATION_DATA_MODEL = "notification_data_model";

    String EXTRA_IS_RELATED = "is_related";
    String EXTRA_GAME_DATA = "game_data";

    String DB_ERROR = "DB_ERROR";
    String INVALID_EVENT = "INVALID_EVENT";
    String UNSUPPORTED_MANIFEST = "UNSUPPORTED_MANIFEST";
    String NETWORK_ERROR = "NETWORK_ERROR";
    String SERVER_ERROR = "SERVER_ERROR";
    String CONNECTION_ERROR = "CONNECTION_ERROR";
    String PAGE_FETCH_ERROR = "PAGE_FETCH_ERROR";
    String VALIDATION_ERROR = "VALIDATION_ERROR";
    String GENIE_SERVICE_NOT_INSTALLED = "GENIE_SERVICE_NOT_INSTALLED";
    String INVALID_FILE = "INVALID_FILE";
    String PROCESSING_ERROR = "PROCESSING_ERROR";
    String AUTHENTICATION_ERROR = "AUTHENTICATION_ERROR";
    String IMPORT_FAILED = "IMPORT_FAILED";

    String VIEW_FROM_VALUE_CHILD_VIEW = "childview";
    String IMPORT_FILE_EXIST = "IMPORT_FILE_EXIST";
    String SYNC_PROMPT = "SYNC_PROMPT";

    String EXTENSION_CONTENT = "ecar";
    String EXTENSION_PROFILE = "epar";
    String EXTENSION_TELEMETRY = "gsa";

    String FILE_EXTENSION_APK = "apk";
    String BUNDLE_KEY_STORAGE_FILE_PATH = "bundle_key_storage_file_path";
    String FILE_EXTENSION_ECAR = ".ecar";
    String FILE_EXTENSION_EPAR = ".epar";

    String NO_CONTENT_TO_IMPORT = "NO_CONTENT_TO_IMPORT";

    String IMPORT_FAILED_DEVICE_MEMORY_FULL = "IMPORT_FAILED_DEVICE_MEMORY_FULL";
    String DRAFT_ECAR_FILE_EXPIRED = "DRAFT_ECAR_FILE_EXPIRED";

    String BUNDLE_KEY_IMPORT_PROFILE = "BUNDLE_KEY_IMPORT_PROFILE";

    String SUMMERIZER_ITEM = "summerizer_item";
    String PROGRESS_TYPE = "progress_type";
    String PROFILE_SUMMARY = "profile_summary";

    String INTENT_ACTION_REFRESH_CURRENT_PROFILE = "org.ekstep.genie.INTENT_ACTION_REFRESH_CURRENT_PROFILE";
    String INTENT_ACTION_REFRESH = "org.ekstep.genie.INTENT_ACTION_REFRESH";
    String INTENT_ACTION_REFRESH_PROFILE = "org.ekstep.genie.INTENT_ACTION_REFRESH_PROFILE";
    String INTENT_ACTION_REFRESH_NOTIFICATION = "org.ekstep.genie.INTENT_ACTION_REFRESH_NOTIFICATION";
    String INTENT_DOWNLOADED_CONTENT_IDENTIFIER = "downloaded_content_identifier";
    String INTENT_ACTION_REFRESH_COLLECTION = "org.ekstep.genie.INTENT_ACTION_REFRESH_COLLECTION";
    String INTENT_DOWNLOADED_CHILD_CONTENT_IDENTIFIER = "downloaded_child_content_identifier";
    String INTENT_ACTION_DOWNLOAD_PROGRESS = "org.ekstep.genie.INTENT_DOWNLOAD_PROGRESS";
    String INTENT_DOWNLOAD_PROGRESS = "download_progress";
    String INTENT_ACTION_DOWNLOAD_STARTED = "org.ekstep.genie.INTENT_ACTION_DOWNLOAD_STARTED";
    String INTENT_DOWNLOAD_STARTED_IDENTIFIER = "downloaded_started_identifier";
    String INTENT_DOWNLOAD_STARTED_DOWNLOAD_ID = "downloaded_started_id";
    String EXTRA_MY_CONTENT = "my_content";
    String EKSTEP_URL = "www.ekstep.in/c/";
    String EKSTEP_GENIE = "https://play.google.com/store/apps/details?id=org.ekstep.genieservices&referrer=utm_source%3Dshare%26utm_medium%3Dapp";
    String INTENT_ACTION_HIDE_SEARCH_LAYOUT = "hide_search_layout";

    String CONTENT1_FILE_PATH = "ecar/welcome_to_genie.ecar";
    String CONTENT2_FILE_PATH = "ecar/first_lesson.ecar";
    String CONTENT3_FILE_PATH = "ecar/my_slate.ecar";

    String CONTENT_STATUS_LIVE = "live";

    String IS_PROFILE = "is_profile";
    String IS_ECAR = "is_ecar";
    String IS_ECAR_N_LINK = "is_ecar_n_link";
    String IS_LINK = "is_link";
    String IS_TEXT = "is_text";
    String IS_GENIE = "is_genie";
    String IS_GENIE_CONFIGURATIONS = "is_genie_configurations";
    String IS_TELEMETRY = "is_telemetry";
    String ZIP_MIME_TYPE = "application/zip";
    String SUPPORT_DATA = "support_data";
    String TXT_MIME_TYPE = "text/plain";
    String ECAR_MIME_TYPE = "application/ecar";
    String SHARE_IDENTIFIER = "share_identifier";
    String SHARE_NAME = "share_name";
    String SHARE_SCREEN_NAME = "share_screen_name";

    String INTENT_ACTION_EDIT_CHILD = "intent_action_edit_child";
    String DEVICE_MEMORY_FULL = "DEVICE_MEMORY_FULL";
    String CANNOT_CREATE_LOCATION = "CANNOT_CREATE_LOCATION";
    String MANIFEST_NOT_CREATED = "MANIFEST_NOT_CREATED";
    String COMPRESSION_FAILURE = "COMPRESSION_FAILURE";
    String MANIFEST_NOT_WRITTEN = "MANIFEST_NOT_WRITTEN";
    String ECAR_NOT_WRITTEN = "ECAR_NOT_WRITTEN";

    String SECTION_NAME = "name";
    String SECTION_REMSGID = "resmsgid";
    String SECTION_APIID = "apiid";
    String INTENT_ACTION_REFRESH_CONTENT_ADAPTER = "intent_action_refresh_content_adapter";
    String INTENT_ACTION_REFRESH_PROFILE_ADAPTER = "intent_action_refresh_profile_adapter";
    String BUNDLE_KEY_MODE = "bundle_mode";
    String BUNDLE_UPDATED_PROFILE = "updated_profile";
    String BUNDLE_EDIT_MODE = "edit_mode";
    String BUNDLE_GROUP_MODE = "group_mode";
    String EXTRAS_IS_ROOT = "is_root";
    String EXTRAS_IS_CANVAS_DEEPLINK = "is_root";


    String BUNDLE_FROM_NOTIFICATION_LIST = "from_notification_list";

    String ALL_SUBJECTS = "All Subjects";

    String SYNC_INTERVAL = "syncInterval";
    String SYNC_MODE = "mode";
    String SYNC_MODE_FORCED = "forced";
    String SYNC_TYPE_DEFAULT = "default";
    String DEFAULT_SYNC_TYPE_AND_INTERVAL = "{\"default\":{\"syncInterval\":60, \"mode\":\"auto\"},\"0\":{\"syncInterval\":30, \"mode\":\"forced\"}}";
    String DEFAULT_PROFILE_COPIED_DATA = null;

    String EXTRAS_SCROLL_TO_BEST_OF_GENIE = "scroll_to_best_ofGenie";
    String AVAILABLE_GAME_LIST = "available_game_list";

    String BUNDLE_KEY_PARTNER_AUDIENCE_ARRAY = "audience_array";
    String BUNDLE_KEY_PARTNER_CHANNEL_ARRAY = "channel_array";
    String BUNDLE_KEY_PARTNER_TAG = "program_tag";
    String BUNDLE_KEY_PARTNER_LANGUAGE = "language";
    String BUNDLE_KEY_HOME_MODE = "mode";
    String BUNDLE_KEY_ENABLE_CHILD_SWITCHER = "enablechildswitcher";
    String BUNDLE_KEY_ENABLE_ONBOARDING_PROFILE_CREATION = "enableOnBoardingProfileCreation";
    String BUNDLE_KEY_ENABLE_ONBOARDING_CHOOSE_SUBJECT = "enableOnBoardingChooseSubject";
    String BUNDLE_KEY_ENABLE_LANGUAGE_SELECTION = "enableLanguageSelection";

    String HOME_IDENTIFIER = "org.ekstep.genie.content.home";
    String PARTNER_HOME_IDENTIFIER = "org.ekstep.genie.content.partnerhome";

    String CONTENT_TYPE_LESSON = "lessons";
    String CONTENT_TYPE_TEXTBOOK = "textbook";

    String DEFAULT_STORAGE = "default_storage";
    String DEFAULT_STORAGE_MOBILE = "Mobile";
    String DEFAULT_STORAGE_EXTERNAL = "External";

    String DEFAULT_STORAGE_PATH = "storage_path";

    // Download Queue related constants..
    String KEY_DOWNLOAD_QUEUE_ATTR_NAME = "name";
    String KEY_DOWNLOAD_QUEUE_ATTR_IDENTIFIER = "identifier";
    String KEY_DOWNLOAD_QUEUE_ATTR_SIZE = "size";
    String KEY_DOWNLOAD_QUEUE_ATTR_PROGRESS = "progress";
    String DOWNLOAD_QUEUE_ATTR_PB_VALUE_ZERO = "0";
    String KEY_SPINE = "spine";

    interface BundleKey {
        //Search Result
        String BUNDLE_KEY_CONTENT_SEARCH_CRITERIA = "content_search_criteria";
        String BUNDLE_KEY_CONTENT_FILTER_FACETS = "filter_facets";
        String BUNDLE_KEY_IS_RECOMMENDED = "is_recommended";
        String BUNDLE_KEY_IS_FROM_DEEPLINK = "is_from_deeplink";
        String BUNDLE_KEY_IS_EXPLICIT = "is_explicit";
        String BUNDLE_KEY_DEEP_LINK_IDENTIFIER = "deep_link_identifier";
        String BUNDLE_KEY_DEEP_LINK_SEARCH_QUERY = "deep_link_search_query";
        String BUNDLE_KEY_DEEP_LINK_SEARCH_SORT = "deep_link_search_sort";
        String BUNDLE_KEY_DEEP_LINK_FILTER = "deep_link_filter";
        String BUNDLE_KEY_IS_RELATED = "is_related";
        String BUNDLE_KEY_DEEP_LINK_SEARCH_LANGUAGE = "deep_link_search_language";
        String BUNDLE_KEY_DEEP_LINK_SEARCH_DOMAIN = "deep_link_search_domain";
        String BUNDLE_KEY_DEEP_LINK_SEARCH_TYPE = "deep_link_search_type";
        String BUNDLE_KEY_DEEP_LINK_GRADE_LEVEL = "deep_link_grade_level";
        String BUNDLE_KEY_SEARCH_QUERY = "search_query";
        String BUNDLE_KEY_CURRENT_PROFILE = "current_profile";

        //Progress Report
        String BUNDLE_KEY_LEARNER_ASSESMENT = "learner_assesment";
        String BUNDLE_KEY_PROFILE_OR_CONTENT_INFO = "profile_or_content_info";
        String BUNDLE_KEY_CONTENT = "content";

        //Content details
        String BUNDLE_KEY_CONTENT_DATA = "content_data";

        String BUNDLE_KEY_HIERARCHY_INFO = "hierarchy_info";
        String BUNDLE_KEY_IS_CANVAS_DEEPLINK = "is_canvasdeeplink";
        String BUNDLE_KEY_IS_SPINE_AVAILABLE = "extra_is_spine_available";
        String BUNDLE_KEY_LOCAL_PATH = "local_path";
        String BUNDLE_KEY_IS_FROM_DOWNLOADS = "is_from_downloads";
        String BUNDLE_KEY_IS_FROM_COLLECTION_OR_TEXTBOOK = "is_from_textbook_or_collection";
        String BUNDLE_KEY_IS_PARENT_TEXTBOOK_OR_COLLECTION = "is_parent_textbook_or_collection";
        String BUNDLE_KEY_IS_FROM_SEARCH = "is_from_search";

        String BUNDLE_KEY_LESSON_TYPE = "lesson_type";
        String BUNDLE_KEY_SETTINGS_POSITION = "settings_position";
        String BUNDLE_KEY_IS_NOT_ANONYMOUS = "is_not_anonymous_profile";
        String BUNDLE_KEY_CONTENT_HIERARCHY_INFO = "hierarchy_info";
    }

    interface EventKey {
        String EVENT_KEY_DELETE_CONTENT = "delete_content";
        String EVENT_KEY_EXPORT_PROFILE = "export_profile";
        String EVENT_KEY_EXPORT_CONTENT = "export_content";
        String EVENT_KEY_IMPORT_LOCAL_CONTENT = "import_local_content";
        String EVENT_KEY_REFRESH_PROFILE = "refresh_profile";
        String EVENT_KEY_CONTENT_IMPORT_SUCCESS = "import_online_content";
        String EVENT_KEY_EDIT_CHILD = "edit_child";
    }

    interface BuildConfigKey {
        String VERSION_CODE = "VERSION_CODE";
        String VERSION_NAME = "VERSION_NAME";
    }

}
