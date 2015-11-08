package com.menno.fastscheduleapp;

/**
 * MainActivity.java
 * @author  Menno Sijben
 */

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.PorterDuffXfermode;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class MainActivity extends Activity
{
    private int selectedMenuWeek = 0;
    private int selectedMenuDay = 2;
    private ShakeListener mShaker;
    private List<Week> weeks;
    private GLSurfaceView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weeks = Info.getInstance().getWeeks(); // .weeks;
        Log.e("JSON Parser", "weeks size" + weeks.size());

        myView = new AnimationView(this);


        setContentView(myView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        myView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myView.onResume();
    }

    public void CloseApp()
    {
        this.finish();
    }

    public static Calendar DateToCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    private Boolean AlreadyChangeMenu = false;

    private double changeSelectedMenuItemAngle(double angle)
    {
        Boolean up = true;
        if(angle < 0) {
            angle *=-1;
            up = false;
        }
        if (angle < 15)
            return 0;

        angle -= 15;
        angle = Math.floor(angle / 30);
        angle += 1;

        if(!up)
        {
            selectedMenuWeek = selectedMenuWeek + angle > weeks.size()-1 ? ((selectedMenuWeek + (int)angle) - (weeks.size()-1))-1 : selectedMenuWeek + (int)angle;
            return -15;
        }
        else
        {
            selectedMenuWeek = selectedMenuWeek - angle < 0 ? weeks.size() + (selectedMenuWeek - (int)angle) : selectedMenuWeek - (int)angle;
            return 15;
        }
    }

    private double changeSelectedMenuItemAngleDays(double angle)
    {
        Boolean up = true;
        if(angle < 0) {
            angle *=-1;
            up = false;
        }
        Log.e("e", Integer.toString((int)angle));
        if (angle < 9)
            return 0;

        angle -= 9;
        angle = Math.floor(angle / 18);
        angle += 1;

        if(!up)
        {

            selectedMenuDay = selectedMenuDay + angle > weeks.get(selectedMenuWeek).GetDays().size()-1 ? ((selectedMenuDay + (int)angle) - (weeks.get(selectedMenuWeek).GetDays().size()-1))-1 : selectedMenuDay + (int)angle;
            // extraMenuAngle = 15;
            return -9;
        }
        else
        {
            selectedMenuDay = selectedMenuDay - angle < 0 ? weeks.get(selectedMenuWeek).GetDays().size() + (selectedMenuDay- (int)angle) : selectedMenuDay - (int)angle;
            // extraMenuAngle = -15;
            return 9;
        }
    }

    private double GetDistanceSqr(int x, int y, int x1, int y1)
    {
        return ((x-x1)*(x-x1)+ (y-y1)*(y-y1));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class DrawView extends View {
        Boolean isMoving = false;
        Boolean isDayMoving = false;
        GestureDetector mGestureDetector;

        private Context context;
        private double lastAngle;
        private double lastDayAngle;
        private double difAngle = 0;
        private double difDayAngle = 0;

        public DrawView(Context context) {
            super(context);
            this.context = context;
            GoToTodaySchedule();
            mGestureDetector = new GestureDetector(context, new MyGestureDetector());
            this.setOnTouchListener(new OnTouchListener()
            {
                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    return mGestureDetector.onTouchEvent(event);
                }
            });

            mShaker = new ShakeListener(this.context);
            mShaker.setOnShakeListener(new ShakeListener.OnShakeListener () {
                public void onShake()
                {
                    GoToTodaySchedule();
                }
            });
        }

        public void GoToTodaySchedule()
        {
            selectedMenuWeek = 0;
            Calendar c = Calendar.getInstance();
            int number = c.get(Calendar.DAY_OF_WEEK);
            if(number == 7 || number == 1)
            {
                selectedMenuWeek = 1;
                selectedMenuDay = 0;
            }
            else
            {
                selectedMenuDay = c.get(Calendar.DAY_OF_WEEK) - 2;
            }
            invalidate();
        }



        public float getAngle(float x, float y)
        {
            float angle = (float) Math.toDegrees(Math.atan2(y - getHeight(), x - getWidth()));

            if (angle < 0) {
                angle += 360;
            }

            return angle;
        }



        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    ActionDownDays(event);
                    if (GetDistanceSqr((int) event.getX(), (int) event.getY(), getWidth(), getHeight()) <= 305 * 305) {
                        isMoving = true;
                        lastAngle = getAngle(event.getX(), event.getY());
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    ActionMoveDays(event);

                    if (isMoving) {
                        difAngle = getAngle(event.getX(), event.getY()) - lastAngle;

                        double change = changeSelectedMenuItemAngle(difAngle);
                        if (change != 0) {
                            lastAngle = getAngle(event.getX(), event.getY()) + change;
                            difAngle = getAngle(event.getX(), event.getY()) - lastAngle;
                            AlreadyChangeMenu = true;
                        }
                        invalidate();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    ActionUpDays(event);
                    isMoving = false;
                    difAngle = 0;
                    AlreadyChangeMenu = false;
                    invalidate();
                    break;
            }
            return true;
        }

        public void ActionDownDays(MotionEvent event)
        {
            if (GetDistanceSqr((int) event.getX(), (int) event.getY(), getWidth(), getHeight()) <= 577 * 577 && GetDistanceSqr((int) event.getX(), (int) event.getY(), getWidth(), getHeight()) > 325 * 325)
            {
                isDayMoving = true;
                lastDayAngle = getAngle(event.getX(), event.getY());
                difDayAngle = 0;
            }
        }

        public void ActionMoveDays(MotionEvent event)
        {
            if(isDayMoving)
            {
                difDayAngle = getAngle(event.getX(), event.getY()) - lastDayAngle;
                double change = changeSelectedMenuItemAngleDays(difDayAngle);
                if (change != 0)
                {
                    lastDayAngle = getAngle(event.getX(), event.getY()) + change;
                    difDayAngle = getAngle(event.getX(), event.getY()) - lastAngle;
                }
                invalidate();
            }
        }

        public void ActionUpDays(MotionEvent event)
        {
            isDayMoving = false;
            difDayAngle = 0;
            invalidate();
        }

        private class MyGestureDetector extends GestureDetector.SimpleOnGestureListener
        {
            public boolean onSingleTapUp(MotionEvent event)
            {
                if (GetDistanceSqr((int) event.getX(), (int) event.getY(), getWidth(), getHeight()) <= 577 * 577 && GetDistanceSqr((int) event.getX(), (int) event.getY(), getWidth(), getHeight()) > 325 * 325)
                {
                    int angle = (int)getAngle(event.getX(),event.getY());
                    if(angle < 180 || angle > 270)
                        return false;

                    angle = angle - 180;
                    angle = (int)Math.floor(angle/18);

                    selectedMenuDay = angle;
                    invalidate();
                }

                if(GetDistanceSqr((int) event.getX(), (int) event.getY(), getWidth(), getHeight()) > 860 * 860)
                {
                    CloseApp();
                }
                return false;
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int x = getWidth();
            int y = getHeight();

            DrawCircle(canvas, x, y, 855, Color.parseColor("#000000"));
            DrawCircleTransparent(canvas, x, y, 799);

            DrawCircle(canvas, x, y, 800, Color.parseColor("#DD242629"));
            DrawCircle(canvas, x, y, 855, Color.parseColor("#AAe9e9e9"));

            DrawSubject(canvas, x, y,855, Color.parseColor("#DD242629"));

            DrawHourLines(canvas, x, y, 855, Color.parseColor("#837E7C"));
            DrawHoursText(canvas, x, y, 855, Color.parseColor("#FFFFFF"));
            DrawCircleTransparent(canvas, x, y, 595);

            DrawCircle(canvas, x, y, 575, Color.parseColor("#E6242629"));

            DrawSelectedItem(canvas, x, y, 575, Color.parseColor("#991da0f8"), 234, 18);
            DrawMenuDays(canvas, x, y);

            DrawCircleTransparent(canvas, x, y, 325);
            DrawCircle(canvas, x, y, 305, Color.parseColor("#E6242629"));

            //draw selected week
            DrawSelectedItem(canvas, x, y, 307, Color.parseColor("#991da0f8"), 240, 30);

            DrawMenuWeek(canvas, x, y);

            DrawCircleTransparent(canvas, x, y, 105);
            DrawCircle(canvas, x, y, 90, Color.parseColor("#242629"));
            DrawCircleTransparent(canvas, x, y, 66);
            DrawCircle(canvas, x, y, 55, Color.parseColor("#242629"));
            DrawCircle(canvas, x, y, 20, Color.parseColor("#CC1da0f8"));

        }

        private int GetHour(Subject subject)
        {
            Calendar calendar = DateToCalendar(subject.GetStartTime());
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            if (hour == 8 && minute == 45)
                return 1;
            if (hour == 9 && minute == 35)
                return 2;
            if (hour == 10 && minute == 45)
                return 3;
            if (hour == 11 && minute == 35)
                return 4;
            if (hour == 12 && minute == 25)
                return 5;
            if (hour == 13 && minute == 15)
                return 6;
            if (hour == 14 && minute == 5)
                return 7;
            if (hour == 15 && minute == 15)
                return 8;
            if (hour == 16 && minute == 5)
                return 9;

            return 1;
        }

        private int GetHowLong(Subject subject)
        {
            long diffInMillies = subject.GetEndTime().getTime() - subject.GetStartTime().getTime();
            long difference = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
            return (int)difference/50;
        }

        private void DrawSubject(Canvas canvas, int x, int y, int radius, int color)
        {
            Week week = weeks.size() > 0 ? weeks.get(selectedMenuWeek) : new Week(0);
            List<Subject> subjects = weeks.size() > 0 ? weeks.get(selectedMenuWeek).GetDays().get(selectedMenuDay).GetSubject() : new ArrayList<Subject>();

            Log.e("MyAppJSON", "hours: " + subjects.size());
            if(subjects.size() > 0)
            {

                Paint paint = new Paint();
                paint.setColor(color);
                paint.setStyle(Paint.Style.FILL);
                paint.setTextSize(36);

                for(int h=0; h< subjects.size(); h++)
                {
                    Subject sub = subjects.get(h);
                    int waneer = GetHour(sub);
                    int hoeLang = GetHowLong(sub);
                    for(int i=0; i<hoeLang; i++)
                    {
                        DrawSelectedItem(canvas, x, y, radius, sub.GetColor(), 180 + waneer * 10 + i * 10, 10);
                        Rect bounds = new Rect();
                        paint.getTextBounds(sub.GetName(), 0, sub.GetName().length(), bounds);

                        DrawAngleText(canvas, x, y, sub.GetName(), 36, waneer * 10 + i * 10 -3, radius-130+bounds.width()/2, Color.parseColor("#FFFFFF") );
                        DrawAngleText(canvas, x, y, sub.GetRoom(), 36, waneer * 10 + i * 10 -7, radius-130+bounds.width()/2, Color.parseColor("#FFFFFF") );
                    }
                }

            }
        }



        private void DrawHoursText(Canvas canvas, int x, int y, int radius, int color)
        {

            DrawHourText(canvas, x,y,"8:45", 30, 180+(int)3, radius, color);
            DrawHourText(canvas, x,y,"9:35", 30, 180+(int)13, radius, color);
            DrawHourText(canvas, x,y,"10:45", 30, 180+(int)23, radius, color);
            DrawHourText(canvas, x,y,"11:35", 30, 180+(int)33, radius, color);
            DrawHourText(canvas, x,y,"12:25", 30, 180+(int)43, radius, color);
            DrawHourText(canvas, x,y,"13:15", 30, 180+(int)53, radius, color);
            DrawHourText(canvas, x,y,"14:05", 30, 180+(int)63, radius, color);
            DrawHourText(canvas, x,y,"15:15", 30, 180+(int)73, radius, color);
            DrawHourText(canvas, x,y,"16:05", 30, 180+(int)83, radius, color);
        }

        private void DrawHourText(Canvas canvas,int x, int y, String text, int textSize, int angle, int radius, int color)
        {
            Paint paint = new Paint();
            paint.setColor(color);
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(textSize);

            Rect bounds = new Rect();
            paint.getTextBounds(text, 0, text.length(), bounds);

            Path path = new Path();
            path.addCircle(x, y, radius, Path.Direction.CW);
            canvas.drawTextOnPath(text, path, (float)(angle*Math.PI)/180  *radius ,bounds.height() / 2 + 28, paint);
        }

        private void DrawHourLines(Canvas canvas, int x, int y, int radius, int color)
        {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(color);

            for(int i=0; i<8; i++)
            {
                canvas.drawLine(x - (float) Math.cos(Math.toRadians((90/9)*(i+1))) * radius, y -(float) Math.sin(Math.toRadians((90/9)*(i+1))) * radius, x, y, paint);
            }
        }

        private void DrawCircle(Canvas canvas, int x, int y, int radius, int color)
        {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(color);
            canvas.drawCircle(x, y, radius, paint);
        }

        private void DrawCircleTransparent(Canvas canvas, int x, int y, int radius)
        {
            Paint paint = new Paint();
            paint.setColor(Color.parseColor("#00ffffff"));
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            canvas.drawCircle(x, y, radius, paint);
        }

        private void DrawSelectedItem(Canvas canvas, int x, int y, int radius, int color, int startAngle, int angleCircle)
        {
            Paint paint = new Paint();
            float radius1;
            if (x > y){
                radius1 = y/4;
            }else{
                radius1 = x/4;
            }

            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(color);
            Path path = new Path();
            path.addCircle(x/2,
                    y/2, radius1,
                    Path.Direction.CW);
            final RectF oval = new RectF();
            oval.set(x - radius, y - radius, x + radius, y+ radius);
            canvas.drawArc(oval, startAngle, -(float) angleCircle, true, paint);
        }

        private void DrawMenuWeek(Canvas canvas, int x, int y)
        {
            int first = selectedMenuWeek-1 < 0 ? weeks.size()-1 : selectedMenuWeek-1;
            int last = selectedMenuWeek+1 > weeks.size()-1 ? 0 : selectedMenuWeek+1;
            int extraAngle = 0;
            if(isMoving)
            {
                extraAngle = (int)difAngle;
            }
            DrawAngleText(canvas, x, y, weeks.get(first).MakeString(), 32,15+extraAngle, 255, Color.WHITE);
            DrawAngleText(canvas, x, y, weeks.get(selectedMenuWeek).MakeString(), 32,45+extraAngle, 255, Color.WHITE);
            DrawAngleText(canvas, x, y, weeks.get(last).MakeString(), 32,75+extraAngle, 255, Color.WHITE);
        }

        private void DrawMenuDays(Canvas canvas, int x, int y)
        {
            int first = selectedMenuDay-2 < 0 ? weeks.get(selectedMenuWeek).GetDays().size()+(selectedMenuDay-2) : selectedMenuDay-2;
            int second = selectedMenuDay-1 < 0 ? weeks.get(selectedMenuWeek).GetDays().size()+(selectedMenuDay-1) : selectedMenuDay-1;
            int four = selectedMenuDay+1 > weeks.get(selectedMenuWeek).GetDays().size()-1 ? (selectedMenuDay+1)-weeks.get(selectedMenuWeek).GetDays().size() : selectedMenuDay+1;
            int last = selectedMenuDay+2 > weeks.get(selectedMenuWeek).GetDays().size()-1 ? (selectedMenuDay+2)-weeks.get(selectedMenuWeek).GetDays().size() : selectedMenuDay+2;

            Paint paint = new Paint();
            paint.setTextSize(36);
            Rect bounds = new Rect();
            paint.getTextBounds(weeks.get(selectedMenuWeek).GetDays().get(0).GetName(), 0, weeks.get(selectedMenuWeek).GetDays().get(0).GetName().length(), bounds);

            int extraAngle = 0;
            if(isDayMoving)
            {
                extraAngle = (int)difDayAngle;
            }

            DrawAngleText(canvas, x, y,  weeks.get(selectedMenuWeek).GetDays().get(first).GetName(), 36,9+extraAngle, bounds.width()/2 + 460, Color.WHITE);
            DrawAngleText(canvas, x, y,  weeks.get(selectedMenuWeek).GetDays().get(second).GetName(), 36,27+extraAngle, bounds.width()/2 + 460, Color.WHITE);
            DrawAngleText(canvas, x, y,  weeks.get(selectedMenuWeek).GetDays().get(selectedMenuDay).GetName(), 36,45+extraAngle, bounds.width()/2 + 460, Color.WHITE);
            DrawAngleText(canvas, x, y,  weeks.get(selectedMenuWeek).GetDays().get(four).GetName(), 36,63+extraAngle, bounds.width()/2 + 460, Color.WHITE);
            DrawAngleText(canvas, x, y,  weeks.get(selectedMenuWeek).GetDays().get(last).GetName(), 36,81+extraAngle, bounds.width()/2 + 460, Color.WHITE);
        }


        private void DrawAngleText(Canvas canvas,int x, int y, String text, int textSize, int angle, int radius, int color)
        {
            Paint paint = new Paint();
            paint.setColor(color);
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(textSize);

            Rect bounds = new Rect();
            paint.getTextBounds(text, 0, text.length(), bounds);

            canvas.save();
            canvas.rotate((float) angle, x - (float) Math.cos(Math.toRadians(angle)) * radius, y + bounds.height() / 2 - (float) Math.sin(Math.toRadians(angle)) * radius);
            canvas.drawText(text, x - (float) Math.cos(Math.toRadians(angle)) * radius, y + bounds.height() / 2 - (float) Math.sin(Math.toRadians(angle)) * radius, paint);

            canvas.restore();
        }
    }
}
