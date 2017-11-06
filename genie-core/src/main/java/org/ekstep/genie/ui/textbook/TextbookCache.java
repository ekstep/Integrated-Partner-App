package org.ekstep.genie.ui.textbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This cache will be mainly accessed with Textbook, between textbook fragment and ContentDetailPresenter,
 * used to store the Game object and also the identifiers that we downloaded.
 *
 * Created by shriharsh on 14/3/17.
 */

public class TextbookCache {

    public static List<HashMap<String, String>> mTextbookLoadRule = null;

    public static List<HashMap<String, String>> getTextbookLoadRule() {
        return mTextbookLoadRule;
    }

    public static void setTextbookLoadRule(List<HashMap<String, String>> rules) {
        mTextbookLoadRule = new ArrayList<>();
        mTextbookLoadRule.addAll(rules);
    }

    public static void clearRulesCache() {
        mTextbookLoadRule = null;
    }

}
