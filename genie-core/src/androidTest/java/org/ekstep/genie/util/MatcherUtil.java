package org.ekstep.genie.util;

import org.ekstep.genie.custom.matcher.RecyclerViewMatcher;

/**
 * Created by swayangjit_gwl on 8/10/2016.
 */
public class MatcherUtil {
    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
}
