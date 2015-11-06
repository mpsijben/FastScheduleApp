package com.menno.fastscheduleapp;

/**
 * CornerService.java
 * @author  Menno Sijben
 */

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class CornerService extends Service
{
    public static final int FLAG_NOT_TOUCH_MODAL    = 0x00000020;
    private ImageView toucheView;
    private ImageView toucheView1;
    WindowManager.LayoutParams params;
    WindowManager.LayoutParams params1;
    private WindowManager windowManager;
    private GestureDetector mGestureDetector;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        toucheView = new ImageView(this);
        toucheView.setImageResource(R.drawable.face1);
        toucheView.setAlpha(0);

        toucheView1 = new ImageView(this);
        toucheView1.setImageResource(R.drawable.face1);
        toucheView1.setAlpha(0);

        params= new WindowManager.LayoutParams(
                110,
                55,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params1= new WindowManager.LayoutParams(
                55,
                110,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        params.x = 0;
        params.y = 0;

        params1.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        params1.x = 0;
        params1.y = 0;


        mGestureDetector = new GestureDetector(this, new MyGestureDetector());

        toucheView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                return false;
            }
        });



        toucheView1.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                mGestureDetector.onTouchEvent(event);
                return  false;
            }
        });

        windowManager.addView(toucheView, params);
        windowManager.addView(toucheView1, params1);
    }

    private class MyGestureDetector extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            float x1 = e1.getX();
            float y1 = e1.getY();

            float x2 = e2.getX();
            float y2 = e2.getY();
            double angle = getAngle(x1, y1, x2, y2);

            if(angle >= 90 && angle < 180)
            {
                //Log.e("MyApp", "slide schuin");
                OpenApp();
            }

            return false;
        }
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                                float distanceY)
        {
            float x1 = e1.getX();
            float y1 = e1.getY();

            float x2 = e2.getX();
            float y2 = e2.getY();
            double angle = getAngle(x1, y1, x2, y2);

            if(angle >= 90 && angle < 180)
            {
                OpenApp();
            }
            return false;
        }

        public double getAngle(float x1, float y1, float x2, float y2) {

            double rad = Math.atan2(y1-y2,x2-x1) + Math.PI;
            return (rad*180/Math.PI + 180)%360;
        }
    }

    public void OpenApp()
    {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (toucheView != null)
            windowManager.removeView(toucheView);
    }
}
