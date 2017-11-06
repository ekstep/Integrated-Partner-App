package org.ekstep.genie.custom.matcher;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Matchers;
import org.junit.Assert;

/**
 * Created by swayangjit on 28/7/17.
 */

public class RecyclerViewItemCountAssertion implements ViewAssertion {
    private final org.hamcrest.Matcher<Integer> matcher;

    private RecyclerViewItemCountAssertion(org.hamcrest.Matcher<Integer> matcher) {
        this.matcher = matcher;
    }

    public static RecyclerViewItemCountAssertion withItemCount(int expectedCount) {
        return withItemCount(Matchers.is(expectedCount));
    }

    public static RecyclerViewItemCountAssertion withItemCount(org.hamcrest.Matcher<Integer> matcher) {
        return new RecyclerViewItemCountAssertion(matcher);
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        RecyclerView recyclerView = (RecyclerView) view;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        Assert.assertThat(adapter.getItemCount(), matcher);
    }
}
