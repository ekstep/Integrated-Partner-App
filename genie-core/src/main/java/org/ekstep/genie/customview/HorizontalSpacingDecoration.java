package org.ekstep.genie.customview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * This class adds horizontal spacing to the view in Recycler View used in Section Adapter
 *
 * Created by shriharsh on 31/1/17.
 */

public class HorizontalSpacingDecoration extends RecyclerView.ItemDecoration {

    private int spacing;

    public HorizontalSpacingDecoration(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.right = spacing;
    }
}
