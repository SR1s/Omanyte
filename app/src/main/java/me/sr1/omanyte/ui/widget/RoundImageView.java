package me.sr1.omanyte.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
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

import me.sr1.omanyte.R;

/**
 * 圆角展示的ImageView
 * [√] 圆角
 * [√] padding纳入考虑
 * [√] 圆角加入配置
 * [-] todo 圆角增加边线的绘制
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

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);

            int radius = typedArray.getDimensionPixelOffset(R.styleable.RoundImageView_cornerRadius, 0);

            mTopLeftRadius = typedArray.getDimensionPixelSize(R.styleable.RoundImageView_cornerRadiusTopLeft, radius);
            mTopRightRadius = typedArray.getDimensionPixelSize(R.styleable.RoundImageView_cornerRadiusTopRight, radius);
            mBottomLeftRadius = typedArray.getDimensionPixelSize(R.styleable.RoundImageView_cornerRadiusBottomLeft, radius);
            mBottomRightRadius = typedArray.getDimensionPixelSize(R.styleable.RoundImageView_cornerRadiusBottomRight, radius);

            typedArray.recycle();
        }

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
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        Path path = new Path();

        // top left corner
        path.moveTo(0 + paddingLeft, mTopLeftRadius + paddingTop);
        path.lineTo(0 + paddingLeft, 0 + paddingTop);
        path.lineTo(mTopLeftRadius + paddingLeft, 0 + paddingTop);
        path.arcTo(new RectF(0 + paddingLeft, 0 + paddingTop, mTopLeftRadius * 2 + paddingLeft, mTopLeftRadius * 2 + paddingTop), -90, -90);
        path.close();

        // top right corner
        path.moveTo(width - paddingRight, mTopRightRadius + paddingTop);
        path.lineTo(width - paddingRight, 0 + paddingTop);
        path.lineTo(width - mTopRightRadius - paddingRight, 0 + paddingTop);
        path.arcTo(
                new RectF(
                        width - mTopRightRadius * 2 - paddingRight, 0 + paddingTop,
                        width - paddingRight, 0 + mTopRightRadius * 2 + paddingTop
                ),
                -90, 90
        );
        path.close();

        // bottom left corner
        path.moveTo(0 + paddingLeft, height - mBottomLeftRadius - paddingBottom);
        path.lineTo(0 + paddingLeft, height - paddingBottom);
        path.lineTo(mBottomLeftRadius + paddingLeft, height - paddingBottom);
        path.arcTo(
                new RectF(
                        0 + paddingLeft, height - mBottomLeftRadius * 2 - paddingBottom,
                        mBottomLeftRadius * 2 + paddingLeft, height - paddingBottom
                ),
                90, 90
        );
        path.close();

        // bottom right corner
        path.moveTo(width - mBottomRightRadius - paddingRight, height - paddingBottom);
        path.lineTo(width - paddingRight, height - paddingBottom);
        path.lineTo(width - paddingRight, height - mBottomRightRadius - paddingBottom);
        path.arcTo(
                new RectF(
                        width - mBottomRightRadius * 2 - paddingRight, height - mBottomRightRadius * 2 - paddingBottom,
                        width - paddingRight, height - paddingBottom
                ),
                -0, 90);
        path.close();

        mMaskPath = path;
    }

}
