package org.ekstep.genie.custom.matcher;


import android.support.test.espresso.matcher.ViewMatchers;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.ekstep.genie.util.MatcherUtil.withRecyclerView;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.not;

/**
 * Created by swayangjit_gwl on 8/11/2016.
 */
public class Matcher {

    public static void check(int resourceId, int stringId) {
        onView(withId(resourceId)).check(matches(withText(stringId)));
    }

    public static void checkWithText(int resourceId, String text) {
        onView(withId(resourceId)).check(matches(withText(text)));
    }

    public static void checkDrawable(int resourceId, int imageId) {
        onView(withId(resourceId)).check(matches(EspressoImageMatchers.withDrawable(imageId)));
    }

    public static void isToastMessageDisplayed(int stringId) {
        onView(withText(stringId)).inRoot(ToastMatcher.isToast()).check(matches(isDisplayed()));
    }

    public static void isToastMessageDisplayed(String message) {
        onView(withText(message)).inRoot(ToastMatcher.isToast()).check(matches(isDisplayed()));
    }

    public static void isVisible(int resourceId) {
        onView(withId(resourceId)).check(matches(isDisplayed()));
    }

    public static void isVisibleText(int stringId) {
        onView(withText(stringId)).check(matches(isDisplayed()));
    }

    public static void isNotVisible(int resourceId) {
        onView(withId(resourceId)).check(matches(not(isDisplayed())));
    }

    public static void isViewDisabled(int resourceId) {
        onView(withId(resourceId)).check(matches(not(isEnabled())));
    }

    public static void isViewEnabled(int resourceId) {
        onView(withId(resourceId)).check(matches(isEnabled()));
    }

    public static void isNotVisibleText(int stringId) {
        onView(withText(stringId)).check(doesNotExist());
    }

    public static void isVisibleWithEffectiveVisibility(int resourceId) {
        onView(anyOf(withId(resourceId))).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    public static void isError(int resourceId, String stringId) {
        onView(withId(resourceId)).check(matches(ErrorMatcher.hasErrorText(stringId)));
    }

    public static void checkInRecyclerView(int recyclerviewId, int position, String text) {
        onView(RecyclerViewMatcher.nthChildOf(withId(recyclerviewId), position)).check(matches(hasDescendant(withText(text))));
    }

    public static void checkInRecyclerViewItemVisibility(int recyclerViewId, int position, int childId) {
        onView(withRecyclerView(recyclerViewId).withViewAtPosition(position, hasDescendant(allOf(withId(childId), isDisplayed()))));
    }

    public static void checkInRecyclerViewItemVisibilityGone(int recyclerViewId, int position, int childId) {
        onView(withRecyclerView(recyclerViewId).withViewAtPosition(position, hasDescendant(allOf(withId(childId), not(isDisplayed())))));
    }

    public static void isCompoundButtonChecked(int resorceId) {
        onView(withId(resorceId)).check(matches(isChecked()));
    }

    public static void isCompoundButtonNotChecked(int resorceId) {
        onView(allOf(withId(resorceId))).check(matches(not(isChecked())));
    }

    public static void isCompoundButtonCheckedWithText(int resorceId, String text) {
        onView(allOf(withId(resorceId), withText(text))).check(matches(isChecked()));
    }

    public static void isCompoundButtonNotCheckedWithText(int resorceId, String text) {
        onView(allOf(withId(resorceId), withText(text))).check(matches(not(isChecked())));
    }
}
