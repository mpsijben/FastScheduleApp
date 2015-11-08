package com.menno.fastscheduleapp;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * AnimationView.java
 * @author  Menno Sijben
 */
public class AnimationView extends GLSurfaceView {
    public AnimationView(Context context) {
        super(context);
        mRenderer = new ClearRenderer();
        setRenderer(mRenderer);
    }

    public boolean onTouchEvent(final MotionEvent event) {
        queueEvent(new Runnable(){
            public void run() {
                mRenderer.setColor(event.getX() / getWidth(),
                        event.getY() / getHeight(), 1.0f);
            }});
        return true;
    }

    ClearRenderer mRenderer;
}
