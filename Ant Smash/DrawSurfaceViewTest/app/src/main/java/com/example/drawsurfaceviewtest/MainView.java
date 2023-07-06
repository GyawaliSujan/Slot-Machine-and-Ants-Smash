//Homework 12
//Sujan Gyawali
//L20445862

package com.example.drawsurfaceviewtest;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holder = null;

    Context context;

    int x, y;

    private MainThread thread = null;

    public MainView (Context context) {

        super(context);

        this.context=context;

        x = y = 0;

        holder = getHolder();
        holder.addCallback(this);

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){

            Assets.sp=new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        } else {

            AudioAttributes attributes=new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            Assets.sp=new SoundPool.Builder()
                    .setAudioAttributes(attributes)
                    .build();
        }

        Assets.squish=Assets.sp.load(context,R.raw.squish_sound, 1);
        Assets.miss=Assets.sp.load(context,R.raw.miss_sound,1);
    }

    public void pause ()
    {

        thread.setRunning(false);

        while (true) {

            try {

                thread.join();

            } catch (InterruptedException e) {

                e.printStackTrace();
            }
            break;
        }
        thread = null;
    }

    public void resume ()
    {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float x, y;
        int action = event.getAction();
        x = event.getX();
        y = event.getY();

        if (action == MotionEvent.ACTION_DOWN) {

            if (thread != null) {

                thread.setXY((int) x, (int) y);
            }
        }
        return true;
    }

    @Override
    public void surfaceCreated (SurfaceHolder holder) {

        if (thread == null) {

            thread = new MainThread(holder, context);
            thread.setRunning(true);
            thread.start();

            setFocusable(true);
        }
    }

    @Override public void surfaceChanged(SurfaceHolder sh, int f, int w, int h) {}
    @Override public void surfaceDestroyed(SurfaceHolder sh) {}
}
