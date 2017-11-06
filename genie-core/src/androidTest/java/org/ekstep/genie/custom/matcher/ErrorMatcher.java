package org.ekstep.genie.custom.matcher;

import android.view.View;
import android.widget.EditText;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by swayangjit_gwl on 8/12/2016.
 */
public class ErrorMatcher {
    public static org.hamcrest.Matcher<View> hasErrorText(final String expectedError) {

        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof EditText)) {
                    return false;
                }
                EditText editText = (EditText) view;
                return expectedError.equals(editText.getError());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with error: " + expectedError);
            }
        };
    }
}
