package org.ekstep.genie.custom.matcher;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v4.app.Fragment;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Created by swayangjit_gwl on 8/9/2016.
 */
public class EspressoImageMatchers {

    public static Matcher<View> withDrawable(final int resourceId) {
        return new DrawableMatcher(resourceId);
    }

    public static Matcher<View> noDrawable() {
        return new DrawableMatcher(-1);
    }

    public static Matcher<Fragment> withId(final int id) {
        return new BoundedMatcher<Fragment, Fragment>(Fragment.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("Matches fragment id");
            }

            @Override
            protected boolean matchesSafely(Fragment item) {
                return item.getId() == id;
            }
        };
    }

}

