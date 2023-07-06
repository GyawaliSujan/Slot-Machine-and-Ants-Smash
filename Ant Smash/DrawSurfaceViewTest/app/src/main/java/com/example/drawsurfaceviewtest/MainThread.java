//Homework 12
//Sujan Gyawali
//L20445862

package com.example.drawsurfaceviewtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.BitmapFactory;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    private SurfaceHolder holder;
    private boolean isRunning = false;
    int x, y;
    int touch_x,touch_y;
    private static final Object lock = new Object();
    boolean initialized;
    Bitmap roach1, roach2, roach3, background;
    Context context;
    boolean touched;
    float roach_radius;
    boolean roach_dead;
    public MainThread (SurfaceHolder surfaceHolder, Context context) {
        holder = surfaceHolder;
        x = y = 0;
        initialized=false;
        this.context=context;
        touched=false;
        roach_dead=false;
    }

    public void setRunning(boolean b) {
        isRunning = b;
    }
    public void setXY (int x, int y) {
        synchronized (lock) {
            this.touch_x = x;
            this.touch_y = y;
            touched=true;
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            if (holder != null) {
                Canvas canvas = holder.lockCanvas();
                if (canvas != null) {
                    update(canvas);
                    render(canvas);
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    private void LoadGraphics(Canvas canvas) {

        if (initialized == false) {
            Bitmap bmp;
            bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.roach1_250);
            int newWidth = (int)(canvas.getWidth() * 0.4f);
            Float scaleFactor = (float) newWidth / bmp.getWidth();
            int newHeight = (int) (bmp.getHeight() * scaleFactor);
            roach1 = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
            bmp = null;
            roach_radius=newWidth*0.66f;
            bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.roach2_250);
            roach2 = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
            bmp = null;
            bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.roach3_250);
            roach3 = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
            bmp = null;
            bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.background);
            newWidth = canvas.getWidth();
            newHeight = canvas.getHeight();
            background = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
            bmp = null;
            initialized = true;

        }
    }

    private  void update(Canvas canvas) {

        if(touched){
            if(TouchInCircle(x,y,roach_radius)) {
                Assets.sp.play(Assets.squish,1,1,1,0,1);
                roach_dead=true;
            }
            else {
                Assets.sp.play(Assets.miss,1,1,1,0,1);
            }
            touched=false;
        }
    }

    private boolean TouchInCircle(int x, int y, float radius){
        double dis=Math.sqrt((touch_x-x)*(touch_x-x)+(touch_y-y)*(touch_y-y));
        if(dis<=roach_radius) {

            return true;
        } else {

            return false;
        }
    }

    private void render (Canvas canvas) {

        int xx, yy;
        LoadGraphics(canvas);
        canvas.drawBitmap(background, 0, 0, null);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);

        synchronized (lock) {
            x = (x + 1) % canvas.getWidth();
            y = (y + 1) % canvas.getHeight();
            xx = x;
            yy = y;
        }

        if(!roach_dead) {
            long curTime = System.currentTimeMillis() / 100 % 10;
            if (curTime % 2 == 0) {
                canvas.drawBitmap(roach1, xx, yy, null);
            } else {
                canvas.drawBitmap(roach2, xx, yy, null);
            }
        }
        else {
            canvas.drawBitmap(roach3, xx, yy, null);
        }
    }
}