/*
 * Copyright 2013 David Schreiber
 *           2013 John Paul Nalog
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.ekstep.genie.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.util.AttributeSet;
import android.widget.Gallery;
import android.widget.SpinnerAdapter;

import org.ekstep.genie.R;

public class FancyCoverFlow extends Gallery {

    // =============================================================================
    // Constants
    // =============================================================================

    public static final int ACTION_DISTANCE_AUTO = Integer.MAX_VALUE;

    public static final float SCALEDOWN_GRAVITY_TOP = 0.0f;

    public static final float SCALEDOWN_GRAVITY_CENTER = 0.5f;

    public static final float SCALEDOWN_GRAVITY_BOTTOM = 1.0f;

    private float reflectionRatio = 0.4f;

    private int reflectionGap = 20;

    private boolean reflectionEnabled = false;

    private float unselectedAlpha;

    /**
     * Camera used for view transformation.
     */
    private Camera transformationCamera;
    private int maxRotation = 75;

    /**
     * Factor (0-1) that defines how much the unselected children should be scaled down. 1 means no scaledown.
     */
    private float unselectedScale;

    private float scaleDownGravity = SCALEDOWN_GRAVITY_CENTER;

    /**
     * Distance in pixels between the transformation effects (alpha, rotation, zoom) are applied.
     */
    private int actionDistance;

    /**
     * Saturation factor (0-1) of items that reach the outer effects distance.
     */
    private float unselectedSaturation;

    public FancyCoverFlow(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initialize();
        this.applyXmlAttributes(attrs);
    }

    private void initialize() {
        this.transformationCamera = new Camera();
        this.setSpacing(0);
    }

    private void applyXmlAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FancyCoverFlow);

        this.actionDistance = a.getInteger(R.styleable.FancyCoverFlow_actionDistance, ACTION_DISTANCE_AUTO);
        this.scaleDownGravity = a.getFloat(R.styleable.FancyCoverFlow_scaleDownGravity, 1.0f);
        this.maxRotation = a.getInteger(R.styleable.FancyCoverFlow_maxRotation, 45);
        this.unselectedAlpha = a.getFloat(R.styleable.FancyCoverFlow_unselectedAlpha, 0.3f);
        this.unselectedSaturation = a.getFloat(R.styleable.FancyCoverFlow_unselectedSaturation, 0.0f);
        this.unselectedScale = a.getFloat(R.styleable.FancyCoverFlow_unselectedScale, 0.75f);
    }

    /**
     * Use this to provide a {@link FancyCoverFlowAdapter} to the coverflow. This
     * method will throw an {@link ClassCastException} if the passed adapter does not
     * subclass {@link FancyCoverFlowAdapter}.
     *
     * @param adapter
     */
    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        super.setAdapter(adapter);
    }

    /**
     * Sets the maximum rotation that is applied to items left and right of the center of the coverflow.
     *
     * @param maxRotation
     */
    public void setMaxRotation(int maxRotation) {
        this.maxRotation = maxRotation;
    }


    public void setUnselectedScale(float unselectedScale) {
        this.unselectedScale = unselectedScale;
    }


    public void setActionDistance(int actionDistance) {
        this.actionDistance = actionDistance;
    }

    @Override
    public void setUnselectedAlpha(float unselectedAlpha) {
        super.setUnselectedAlpha(unselectedAlpha);
        this.unselectedAlpha = unselectedAlpha;
    }


    public void setUnselectedSaturation(float unselectedSaturation) {
        this.unselectedSaturation = unselectedSaturation;
    }

    public static class LayoutParams extends Gallery.LayoutParams {
        public LayoutParams(int w, int h) {
            super(w, h);
        }

    }

}
