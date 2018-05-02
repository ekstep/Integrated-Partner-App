package org.ekstep.genie.util;

import android.content.Context;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;
import org.ekstep.genie.util.preference.PreferenceUtil;

import java.util.HashMap;

/**
 * Created on 4/28/2016.
 *
 * @author swayangjit_gwl
 */
public class ContentLanguageJsonCreator {

    private static final String LANGUAGE_CODE = "languageCode";
    private static final String HOME = "home";
    private static final String TITLE = "title";
    private static final String SUBMIT = "submit";
    private static final String IMAGE = "image";
    private static final String VOICE = "voice";
    private static final String AUDIO = "audio";
    private static final String AUTHOR = "author";
    private static final String INSTRUCTIONS = "instructions";
    private static final String FEEDBACK = "feedback";
    private static final String REPLAY = "replay";
    private static final String CREDITS = "credits";
    private static final String COLLECTION = "collection";
    private static final String NO_CREDITS_AVAILABLE = "noCreditsAvailable";
    private static final String CONGRATULATIONS = "congratulations";
    private static final String NEXT = "next";
    private static final String SCORES = "scores";
    private static final String LASTPAGE = "lastPage";
    private static final String NEXTCONTENT = "nextContent";
    private static final String CONTINUE = "continue";
    private static final String GROUP = "group";
    private static final String CHILD = "child";
    private static final String GROUP_FALLBACK_TEXT = "groupFallbackText";
    private static final String USER_SWITCHER_TITLE = "userSwitcherTitle";
    private static final String BACK_BUTTON_TEXT = "backButtonText";
    private static final String ANONYMOUS = "anonymousUserName";
    private static final String COMMENT = "comment";

    public static HashMap<String, Object> createLanguageBundleMap() {
        Context context = CoreApplication.getInstance();
        HashMap<String, Object> languageBundle = new HashMap<>();
        languageBundle.put(LANGUAGE_CODE, PreferenceUtil.getLanguage());
        languageBundle.put(HOME, context.getResources().getString(R.string.title_content_player_home));
        languageBundle.put(TITLE, context.getResources().getString(R.string.title_content_player_title));
        languageBundle.put(SUBMIT, context.getResources().getString(R.string.title_content_player_submit));
        languageBundle.put(IMAGE, context.getResources().getString(R.string.title_content_player_image));
        languageBundle.put(VOICE, context.getResources().getString(R.string.title_content_player_voice));
        languageBundle.put(AUDIO, context.getResources().getString(R.string.title_content_player_audio));
        languageBundle.put(AUTHOR, context.getResources().getString(R.string.title_content_player_author));
        languageBundle.put(INSTRUCTIONS, context.getResources().getString(R.string.title_content_player_instructions));
        languageBundle.put(FEEDBACK, context.getResources().getString(R.string.title_content_player_feedback));
        languageBundle.put(REPLAY, context.getResources().getString(R.string.title_content_player_replay));
        languageBundle.put(CREDITS, context.getResources().getString(R.string.title_content_player_credits));
        languageBundle.put(COLLECTION, context.getResources().getString(R.string.title_content_player_collection));
        languageBundle.put(NO_CREDITS_AVAILABLE, context.getResources().getString(R.string.title_content_player_nocredits));
        languageBundle.put(CONGRATULATIONS, context.getResources().getString(R.string.title_content_player_congratulations));
        languageBundle.put(NEXT, context.getResources().getString(R.string.title_content_player_next));
        languageBundle.put(SCORES, context.getResources().getString(R.string.title_content_player_scores));
        languageBundle.put(LASTPAGE, context.getResources().getString(R.string.title_content_player_lastPage));
        languageBundle.put(NEXTCONTENT, context.getResources().getString(R.string.title_content_player_nextContent));
        languageBundle.put(COMMENT, context.getResources().getString(R.string.title_content_player_comment));
        languageBundle.put(CONTINUE, context.getResources().getString(R.string.title_content_player_continue));
        languageBundle.put(GROUP, context.getResources().getString(R.string.title_content_player_group));
        languageBundle.put(CHILD, context.getResources().getString(R.string.title_content_player_child));
        languageBundle.put(GROUP_FALLBACK_TEXT, context.getResources().getString(R.string.title_content_player_groupfallbacktext));
        languageBundle.put(USER_SWITCHER_TITLE, context.getResources().getString(R.string.title_content_player_userswitchertitle));
        languageBundle.put(ANONYMOUS, context.getResources().getString(R.string.label_all_anonymous));
        languageBundle.put(BACK_BUTTON_TEXT, context.getResources().getString(R.string.title_content_player_backbuttontext));

        return languageBundle;
    }

}
