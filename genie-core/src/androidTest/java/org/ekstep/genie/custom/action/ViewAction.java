package org.ekstep.genie.custom.action;

import android.support.test.espresso.UiController;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.custom.matcher.RecyclerViewMatcher;
import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.ekstep.genie.util.MatcherUtil.withRecyclerView;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

/**
 * Created on 8/11/2016.
 *
 * @author swayangjit_gwl
 */
public class ViewAction {

    public static void clickonView(int id) {
        onView(allOf(withId(id), isDisplayed())).perform(click());
    }

    public static void typeTextnPressIMEActionButton(int id, String enteredText) {
        onView(allOf(withId(id), isDisplayed())).perform(replaceText(enteredText)).perform(pressKey(KeyEvent.KEYCODE_SEARCH));
    }

    public static void typeText(int id, String enteredText) {
        onView(allOf(withId(id), isDisplayed())).perform(replaceText(enteredText));
    }

    public static void clickonString(int stringId) {
        onView(withText(stringId)).perform(click());
    }

    public static void clickonString(int resourceid, int stringId) {
        onView(allOf(withId(resourceid), withText(stringId), isDisplayed())).perform(click());
    }

    public static void clickonString(int resourceid, String text) {
        onView(allOf(withId(resourceid), withText(text), isDisplayed())).perform(click());
    }

    public static void clickonView(int id, int parentId) {
        onView(allOf(withId(id), withParent(allOf(withId(parentId))), isDisplayed())).perform(click());
    }

    public static void swipeUp(int resourceId) {
        onView(withId(resourceId)).perform(ViewActions.swipeUp());
    }

    public static void navaigateToDrawerItems(int position) {
        //Click on Berger menu
        onView(allOf(withId(R.id.img_berger_menu), isDisplayed())).perform(click());

        //Click on "Users" menu item
        onView(withId(R.id.list_drawer)).perform(actionOnItemAtPosition(position, click()));
    }


    public static void clickOnRecylerViewChild(int recyclerViewId, int position, int childId) {
        onView(withRecyclerView(recyclerViewId).atPositionOnView(position, childId)).perform(click());
    }

    public static void clickOnRecylerViewItem(int recyclerViewId, int position, int childId) {
        onView(withId(recyclerViewId)).check(matches(isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(position, clickChildViewWithId(childId)));
    }

    public static void clickOnRecylerViewItem(int recyclerViewId, int position) {
        onView(withId(recyclerViewId)).check(matches(isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(position, click()));
    }

    public static void clickOnRecylerViewItem(int recyclerViewId, int position, android.support.test.espresso.ViewAction viewAction) {
        onView(withId(recyclerViewId)).perform(RecyclerViewActions.actionOnItemAtPosition(position, viewAction));
    }

    public static void clickOnRecylerViewItemWithAllof(int recyclerViewId, int position) {
        onView(allOf(withId(recyclerViewId), isDisplayed())).perform(actionOnItemAtPosition(position, click()));
    }

    public static void clickOnRecylerViewItemText(String text) {
        onView(RecyclerViewMatcher.withItemText(text)).perform(click());
    }


    public static void clickOnAdapterView(int adapterViewId, int position) {
        onData(anything()).inAdapterView(withId(adapterViewId)).atPosition(position).perform(click());
    }

    public static android.support.test.espresso.ViewAction clickChildViewWithId(final int id) {
        return new android.support.test.espresso.ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                if (v != null) {
                    v.performClick();
                }
            }
        };
    }

    public static android.support.test.espresso.ViewAction clickNestedChildViewWithId(final int parentresourceId, final int position, final int idtoPerformAction) {
        return new android.support.test.espresso.ViewAction() {
            @SuppressWarnings("unchecked")
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public void perform(UiController uiController, View view) {
                LinearLayout v = (LinearLayout) view.findViewById(parentresourceId);
                View singleView = v.getChildAt(position);
                View more = singleView.findViewById(idtoPerformAction);
                more.performClick();
            }

            @Override
            public String getDescription() {
                return "replace text";
            }
        };
    }


    public static android.support.test.espresso.ViewAction setTextInTextView(final String value){
        return new android.support.test.espresso.ViewAction() {
            @SuppressWarnings("unchecked")
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isDisplayed(), isAssignableFrom(TextView.class));
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((TextView) view).setText(value);
            }

            @Override
            public String getDescription() {
                return "replace text";
            }
        };
    }

    public static void scrollANDClickView(int id) {
        onView(withId(id)).perform(scrollTo(), click());
    }

    public static android.support.test.espresso.ViewAction setRating(final float rating) {
        return new android.support.test.espresso.ViewAction() {
            @SuppressWarnings("unchecked")
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isDisplayed(), isAssignableFrom(RatingBar.class));
            }

            @Override
            public void perform(UiController uiController, View view) {
                RatingBar ratingBar = (RatingBar) view;
                ratingBar.setRating(rating);
            }

            @Override
            public String getDescription() {
                return "replace text";
            }
        };
    }


}
