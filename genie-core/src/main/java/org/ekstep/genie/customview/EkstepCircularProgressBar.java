package org.ekstep.genie.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import org.ekstep.genie.R;

/**
 * EkstepCircularProgressBar
 */
public class EkstepCircularProgressBar extends View {
    private float mDefalutStrokeWidth = 4;
    private float progress = 0;
    private int min = 0;
    private int max = 100;
    private int startAngle = -90;
    private int color = Color.DKGRAY;
    private RectF rectF;
    private Paint backgroundPaint;
    private Paint foregroundPaint;

    public float getStrokeWidth() {
        return mDefalutStrokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.mDefalutStrokeWidth = strokeWidth;
        backgroundPaint.setStrokeWidth(strokeWidth);
        foregroundPaint.setStrokeWidth(strokeWidth);
        invalidate();
        requestLayout();//Because it should recalculate its bounds
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
        invalidate();
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
        invalidate();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        backgroundPaint.setColor(adjustAlpha(color, 0.3f));
        foregroundPaint.setColor(color);
        invalidate();
        requestLayout();
    }

    public EkstepCircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        rectF = new RectF();
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.EkstepCircularProgressBar, 0, 0);

        //Reading values from the XML layout
        try {
            mDefalutStrokeWidth = typedArray.getDimension(R.styleable.EkstepCircularProgressBar_progressBarThickness, mDefalutStrokeWidth);
            progress = typedArray.getFloat(R.styleable.EkstepCircularProgressBar_progress, progress);
            color = typedArray.getInt(R.styleable.EkstepCircularProgressBar_progressbarColor, color);
            min = typedArray.getInt(R.styleable.EkstepCircularProgressBar_min, min);
            max = typedArray.getInt(R.styleable.EkstepCircularProgressBar_max, max);
        } finally {
            typedArray.recycle();
        }

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(adjustAlpha(color, 0.3f));
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(mDefalutStrokeWidth);

        foregroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        foregroundPaint.setColor(color);
        foregroundPaint.setStyle(Paint.Style.STROKE);
        foregroundPaint.setStrokeWidth(mDefalutStrokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawOval(rectF, backgroundPaint);
        float angle = 360 * progress / max;
        canvas.drawArc(rectF, startAngle, angle, false, foregroundPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        final int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int min = Math.min(width, height);
        setMeasuredDimension(min, min);
        rectF.set(0 + mDefalutStrokeWidth / 2, 0 + mDefalutStrokeWidth / 2, min - mDefalutStrokeWidth / 2, min - mDefalutStrokeWidth / 2);
    }

    /**
     * Transparent the given color by the factor
     * The more the factor closer to zero the more the color gets transparent
     *
     * @param color  The color to transparent
     * @param factor 1.0f to 0.0f
     * @return int - A transplanted color
     */
    public int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

//    public void registerForProgress() {
//        EventBus.registerSubscriber(this);
//    }
//
//    public void unregisterForProgress() {
//        EventBus.unregisterSubscriber(this);
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onDownloadProgress(DownloadProgress downloadProgress) throws InterruptedException {
//        setProgress(downloadProgress.getDownloadProgress());
//    }

}