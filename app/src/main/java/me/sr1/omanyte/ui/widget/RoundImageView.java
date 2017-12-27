package me.sr1.omanyte.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * 圆角展示的ImageView
 * @author SR1
 */

public class RoundImageView extends AppCompatImageView {

    private final String TAG = "RoundImageView";

    private float mTopLeftRadius;
    private float mTopRightRadius;
    private float mBottomLeftRadius;
    private float mBottomRightRadius;

    private Paint mMaskPaint;
    private Path  mMaskPath;


    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTopLeftRadius = mTopRightRadius = mBottomLeftRadius = mBottomRightRadius = 10;
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        onSizeChanged(getWidth(), getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 把显示区域裁剪成出圆角
        // todo 这里需要补上padding的情况
        Path maskPath = mMaskPath;
        if (maskPath != null) {
            canvas.drawPath(maskPath, mMaskPaint);
        }
    }

    private void init() {
        // 以便清空后背景透明
        setLayerType(LAYER_TYPE_HARDWARE, new Paint());

        mMaskPaint = new Paint();
        mMaskPaint.setAntiAlias(true);
        mMaskPaint.setColor(Color.TRANSPARENT);
        mMaskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    private void onSizeChanged(int width, int height) {
        Log.i(TAG, "onSizeChanged: " + width + "x" + height);
        updateMaskPath(width, height);
    }

    private void updateMaskPath(int width, int height) {
        Path path = new Path();

        // top left corner
        path.moveTo(0, mTopLeftRadius);
        path.lineTo(0, 0);
        path.lineTo(mTopLeftRadius, 0);
        path.arcTo(new RectF(0, 0, mTopLeftRadius * 2, mTopLeftRadius * 2), -90, -90);
        path.close();

        // top right corner
        path.moveTo(width, mTopRightRadius);
        path.lineTo(width, 0);
        path.lineTo(width - mTopRightRadius, 0);
        path.arcTo(new RectF(width - mTopRightRadius * 2, 0, width, 0 + mTopRightRadius * 2), -90, 90);
        path.close();

        // bottom left corner
        path.moveTo(0, height - mBottomLeftRadius);
        path.lineTo(0, height);
        path.lineTo(mBottomLeftRadius, height);
        path.arcTo(new RectF(0, height - mBottomLeftRadius * 2, mBottomLeftRadius * 2, height), 90, 90);
        path.close();

        // bottom right corner
        path.moveTo(width - mBottomRightRadius, height);
        path.lineTo(width, height);
        path.lineTo(width, height - mBottomRightRadius);
        path.arcTo(new RectF(width - mBottomRightRadius * 2, height - mBottomRightRadius * 2, width, height), -0, 90);
        path.close();

        mMaskPath = path;
    }

}