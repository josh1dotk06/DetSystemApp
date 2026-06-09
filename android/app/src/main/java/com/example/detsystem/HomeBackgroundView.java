package com.example.detsystem;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class HomeBackgroundView extends View {

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Random random = new Random();

    private final int coinCount = 26;
    private final float[] coinX = new float[coinCount];
    private final float[] coinY = new float[coinCount];
    private final float[] coinRadius = new float[coinCount];
    private final float[] coinSpeed = new float[coinCount];
    private final int[] coinAlpha = new int[coinCount];

    private long lastTime = 0;

    public HomeBackgroundView(Context context) {
        super(context);
        init();
    }

    public HomeBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        for (int i = 0; i < coinCount; i++) {
            coinX[i] = random.nextFloat();
            coinY[i] = random.nextFloat();
            coinRadius[i] = 10 + random.nextInt(18);
            coinSpeed[i] = 25 + random.nextInt(45);
            coinAlpha[i] = 35 + random.nextInt(45);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        long now = System.currentTimeMillis();

        if (lastTime == 0) {
            lastTime = now;
        }

        float deltaSeconds = (now - lastTime) / 1000f;
        lastTime = now;

        int width = getWidth();
        int height = getHeight();

        drawBlackCheckerboard(canvas, width, height);
        drawPurpleGlowStreaks(canvas, width, height);
        drawFallingCoins(canvas, width, height, deltaSeconds);

        postInvalidateOnAnimation();
    }

    private void drawBlackCheckerboard(Canvas canvas, int width, int height) {
        canvas.drawColor(Color.rgb(3, 3, 8));

        int squareSize = 80;

        for (int y = 0; y < height; y += squareSize) {
            for (int x = 0; x < width; x += squareSize) {
                boolean alternate = ((x / squareSize) + (y / squareSize)) % 2 == 0;

                if (alternate) {
                    paint.setColor(Color.rgb(14, 14, 20));
                } else {
                    paint.setColor(Color.rgb(6, 6, 12));
                }

                paint.setStyle(Paint.Style.FILL);
                canvas.drawRect(x, y, x + squareSize, y + squareSize, paint);
            }
        }

        paint.setColor(Color.argb(45, 120, 120, 140));
        paint.setStrokeWidth(2);

        for (int x = 0; x < width; x += squareSize) {
            canvas.drawLine(x, 0, x, height, paint);
        }

        for (int y = 0; y < height; y += squareSize) {
            canvas.drawLine(0, y, width, y, paint);
        }
    }

    private void drawPurpleGlowStreaks(Canvas canvas, int width, int height) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);

        paint.setMaskFilter(new BlurMaskFilter(35, BlurMaskFilter.Blur.NORMAL));
        paint.setStrokeWidth(24);
        paint.setColor(Color.argb(90, 176, 38, 255));

        canvas.drawLine(-100, height * 0.20f, width * 0.75f, height * 0.05f, paint);
        canvas.drawLine(width * 0.20f, height * 0.95f, width + 120, height * 0.70f, paint);
        canvas.drawLine(-80, height * 0.72f, width * 0.90f, height * 0.35f, paint);

        paint.setMaskFilter(null);
        paint.setStrokeWidth(4);
        paint.setColor(Color.argb(180, 210, 120, 255));

        canvas.drawLine(-100, height * 0.20f, width * 0.75f, height * 0.05f, paint);
        canvas.drawLine(width * 0.20f, height * 0.95f, width + 120, height * 0.70f, paint);
        canvas.drawLine(-80, height * 0.72f, width * 0.90f, height * 0.35f, paint);
    }

    private void drawFallingCoins(Canvas canvas, int width, int height, float deltaSeconds) {
        for (int i = 0; i < coinCount; i++) {
            coinY[i] += coinSpeed[i] * deltaSeconds / Math.max(height, 1);

            if (coinY[i] > 1.15f) {
                coinY[i] = -0.15f;
                coinX[i] = random.nextFloat();
                coinRadius[i] = 10 + random.nextInt(18);
                coinSpeed[i] = 25 + random.nextInt(45);
                coinAlpha[i] = 35 + random.nextInt(45);
            }

            float x = coinX[i] * width;
            float y = coinY[i] * height;

            paint.setStyle(Paint.Style.FILL);
            paint.setMaskFilter(new BlurMaskFilter(12, BlurMaskFilter.Blur.NORMAL));
            paint.setColor(Color.argb(coinAlpha[i], 255, 215, 80));
            canvas.drawCircle(x, y, coinRadius[i] + 6, paint);

            paint.setMaskFilter(null);
            paint.setColor(Color.argb(coinAlpha[i] + 40, 255, 196, 45));
            canvas.drawCircle(x, y, coinRadius[i], paint);

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(3);
            paint.setColor(Color.argb(coinAlpha[i] + 55, 255, 235, 130));
            canvas.drawCircle(x, y, coinRadius[i] * 0.65f, paint);
        }

        paint.setMaskFilter(null);
    }
}