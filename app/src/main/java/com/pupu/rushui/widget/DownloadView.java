package com.pupu.rushui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.pupu.rushui.R;

/**
 * Created by pupu on 2018/6/20.
 * 下载控件view
 */

public class DownloadView extends View {

    /**
     * 宽高
     */
    int width, height;
    Paint paint;

    /**
     * 下载进度
     */
    int progress = 0;

    /**
     * 下载图标
     */
    Bitmap downloadBm;

    /**
     * 已选中
     */
    public static final int STATE_CHECKED = 0x001;

    /**
     * 未选中
     */
    public static final int STATE_NO_CHECKED = 0x002;

    /**
     * 正在下载标志
     */
    public static final int STATE_DOWNLOADING = 0x003;

    /**
     * 未下载，显示下载按钮
     */
    public static final int STATE_NO_DOWNLOADED = 0x004;
    private int state = STATE_NO_CHECKED;

    public DownloadView(Context context) {
        this(context, null);
    }

    public DownloadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DownloadView);
        width = ta.getLayoutDimension(R.styleable.DownloadView_android_layout_width, 40);
        height = ta.getLayoutDimension(R.styleable.DownloadView_android_layout_height, 40);
        paint = new Paint();
        paint.setColor(0xffffffff);

        //初始化下载图片资源
        Bitmap originBm = BitmapFactory.decodeResource(getResources(), R.mipmap.img_download);
        int bmW = originBm.getWidth();
        int bmH = originBm.getHeight();
        float scale = (4f * width / 5f) / (float) bmH;
        Matrix matrix = new Matrix();
        matrix.preScale(scale, scale);
        downloadBm = Bitmap.createBitmap(originBm, 0, 0, bmW, bmH, matrix, false);
        originBm.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制圆环
        paint.setColor(0xffffffff);
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(width / 2, width / 2, width / 2, paint);

        if (state == STATE_CHECKED) {
            //绘制圆形
            paint.setColor(0xffffffff);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(width / 2, width / 2, width / 2, paint);
        }
        if (state == STATE_DOWNLOADING) {
            //绘制文字
            paint.setTextSize(width / 3);
            paint.setTextAlign(Paint.Align.CENTER);
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
            float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
            int baseLineY = (int) (width / 2 - top / 2 - bottom / 2);//基线中间点的y轴计算公式
            canvas.drawText(progress + "%", width / 2, baseLineY, paint);
        }
        if (state == STATE_NO_DOWNLOADED) {
            //绘制下载图片
            if (downloadBm != null) {
                canvas.drawBitmap(downloadBm, width / 2 - downloadBm.getWidth() / 2, width / 2 - downloadBm.getHeight() / 2, paint);
            }
        }

        postInvalidate();
    }

    /**
     * 更新进度百分比，
     *
     * @param progress 0~100
     */
    public void updateProgress(int progress) {
        if (progress < 0 || progress > 100) {
            return;
        }
        this.state = STATE_DOWNLOADING;
        this.progress = progress;
        postInvalidate();
    }

    /**
     * 设置当前状态
     *
     * @param state 状态
     */
    public void setState(int state) {
        if (state != STATE_NO_CHECKED &&
                state != STATE_NO_DOWNLOADED &&
                state != STATE_DOWNLOADING &&
                state != STATE_CHECKED) {
            return;
        }
        this.state = state;
        postInvalidate();
    }

    /**
     * 获取状态
     *
     * @return
     */
    public int getState() {
        return this.state;
    }
}
