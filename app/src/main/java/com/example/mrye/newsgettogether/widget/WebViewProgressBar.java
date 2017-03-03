package com.example.mrye.newsgettogether.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.mrye.newsgettogether.R;

/**
 * Created by Mr.Ye on 2016/11/20.
 */

public class WebViewProgressBar extends View {

    private int progress = 1;
    private final static int HEIGHT = 5;
    private Paint paint;


    public WebViewProgressBar(Context context) {
        super(context);
        paint = new Paint(Paint.DITHER_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(HEIGHT);
        paint.setAntiAlias(true);
        paint.setColor(context.getResources().getColor(R.color.progressbarColor));
    }

    public WebViewProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getWidth() * progress / 100, HEIGHT, paint);
    }
}
