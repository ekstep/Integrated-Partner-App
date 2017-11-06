package org.ekstep.genie.util.preference;

/**
 * Created on 10-08-2015.
 *
 * @author anil
 */
public interface PreferenceKey {

    String IS_OLD_GENIE_UNINSTALLED = "is_old_genie_uninstalled";
    String IS_FIRST_TIME = "is_first_time";
    String ON_BOARDING_STATE = "on_boarding_state";
    String IS_FIRST_TIME_LIBRARY = "is_first_time_library";

    String GENIE_FIRST_LAUNCH_TIME = "genie_first_launch_time";
    String ON_BOARD_NOTIFICATION_STATE = "on_board_notification_state";

    String FCM_TOKEN = "fcm_token";

    String GENIE_SESSION_ENDED = "genie_session_ended";
    String START_GENIE_TIME_STAMP = "genie_start_time_stamp";
    String UNIQUE_DEVICE_UDID = "unique_device_udid";

    String CURRENT_CHILD_UDID = "current_child_udid";

    String LANGUAGE = "language";
    String IS_LANGUAGE_SELECTED = "language_selected";

    String VIEW_FROM = "from";

    String DOWNLOADED_CONTENT = "downloading_content";

    String CURRENT_PLAYING_GAME_IDENTIFIER = "current_game_playing";

    String GAME_START_TIME = "start_time";

    String IS_GROUP = "is_group";

    String SHOW_DUMMY_FRAG = "show_dummy_fragment";
    String FROM_MANAGE_USER = "from_manage_user";

    String METADATA_CORELATION_ID = "metadata_corelation_id";

    String KEY_SDCARD_FOLDER_LAST_MODIFIED = "key_sdcard_folder_last_modified";
    String CHOSEN_SUBJECT_NAME = "key_chosen_subject_name";
    String OFFLINE_CHOSEN_SUBJECT_NAME = "key_offline_chosen_subject_name";

    String CURRENT_GAME = "current_game";

    String DONT_SEND_CDATA = "dont_send_cdata";
    String SUPPORTED_GENIE_VERSIONS = "supported_genie_versions";
    String TELEMETRY_SYNC_INTERVAL = "telemetry_sync_interval";
    String KEY_SYNC_CONFIG = "sync_config";
    String KEY_ANONYMOUS_SESSION_STARTED_MANUALLY = "anonymous_started_manually";
    String KEY_PARTNER_INFO = "partner_info";
    String IS_PROFILES_COPIED = "is_profiles_copied";
    String KEY_STORAGE_OPTION = "storage_option";
    String KEY_DOWNLOAD_QUEUE_ITEM_LIST = "download_queue_item_list";

    String KEY_HIDE_MEMORY_CARD_DIALOG = "show_memory_card_msg";

    String KEY_SET_EXTERNAL_STORAGE_DEFAULT ="set_external_storage_default";
}
