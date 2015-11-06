package com.menno.fastscheduleapp;

/**
 * Info.java - a class that generate data or get the data from the web
 * @author  Menno Sijben
 */

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Info {

    public static List<Week> weeks = new ArrayList<Week>();

    public static Calendar DateToCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public Info()
    {
        new Task1().execute();
    }

    public void LoadTempData()
    {
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date startDate = (Date) formatter.parse("2015-11-06 08:45:00");
            Date endDate = (Date) formatter.parse("2015-11-06 10:25:00");
            Subject subject = new Subject("EAG", startDate, endDate, "4.01");
            Calendar calendar = DateToCalendar(subject.GetStartTime());
            int number = calendar.get(Calendar.WEEK_OF_YEAR);
            weeks.add(new Week(number));

            if( weeks.get(weeks.size()-1).GetLastSubject(subject).GetName().equals(subject.GetName()))
            {
                weeks.get(weeks.size()-1).GetLastSubject(subject).SetEndTime(subject.GetEndTime());
            }
            else
            {
                weeks.get(weeks.size() - 1).AddSubject(subject);
            }

            startDate = (Date) formatter.parse("2015-11-06 13:15:00");
            endDate = (Date) formatter.parse("2015-11-06 16:55:00");
            subject = new Subject("VSD", startDate, endDate, "4.66");
            calendar = DateToCalendar(subject.GetStartTime());
            number = calendar.get(Calendar.WEEK_OF_YEAR);

            if( weeks.get(weeks.size()-1).GetLastSubject(subject).GetName().equals(subject.GetName()))
            {
                weeks.get(weeks.size()-1).GetLastSubject(subject).SetEndTime(subject.GetEndTime());
            }
            else
            {
                weeks.get(weeks.size() - 1).AddSubject(subject);
            }

            startDate = (Date) formatter.parse("2015-11-02 10:45:00");
            endDate = (Date) formatter.parse("2015-11-02 11:35:00");
            subject = new Subject("LJK", startDate, endDate, "3.26");
            calendar = DateToCalendar(subject.GetStartTime());
            number = calendar.get(Calendar.WEEK_OF_YEAR);

            if( weeks.get(weeks.size()-1).GetLastSubject(subject).GetName().equals(subject.GetName()))
            {
                weeks.get(weeks.size()-1).GetLastSubject(subject).SetEndTime(subject.GetEndTime());
            }
            else
            {
                weeks.get(weeks.size() - 1).AddSubject(subject);
            }

            startDate = (Date) formatter.parse("2015-11-03 10:45:00");
            endDate = (Date) formatter.parse("2015-11-03 11:35:00");
            subject = new Subject("LJK", startDate, endDate, "3.26");
            calendar = DateToCalendar(subject.GetStartTime());
            number = calendar.get(Calendar.WEEK_OF_YEAR);

            if( weeks.get(weeks.size()-1).GetLastSubject(subject).GetName().equals(subject.GetName()))
            {
                weeks.get(weeks.size()-1).GetLastSubject(subject).SetEndTime(subject.GetEndTime());
            }
            else
            {
                weeks.get(weeks.size() - 1).AddSubject(subject);
            }

            startDate = (Date) formatter.parse("2015-11-03 13:15:00");
            endDate = (Date) formatter.parse("2015-11-03 14:05:00");
            subject = new Subject("IIU", startDate, endDate, "3.26");
            calendar = DateToCalendar(subject.GetStartTime());
            number = calendar.get(Calendar.WEEK_OF_YEAR);

            if( weeks.get(weeks.size()-1).GetLastSubject(subject).GetName().equals(subject.GetName()))
            {
                weeks.get(weeks.size()-1).GetLastSubject(subject).SetEndTime(subject.GetEndTime());
            }
            else
            {
                weeks.get(weeks.size() - 1).AddSubject(subject);
            }

            startDate = (Date) formatter.parse("2015-11-04 10:45:00");
            endDate = (Date) formatter.parse("2015-11-04 12:25:00");
            subject = new Subject("SLD", startDate, endDate, "3.26");
            calendar = DateToCalendar(subject.GetStartTime());
            number = calendar.get(Calendar.WEEK_OF_YEAR);

            if( weeks.get(weeks.size()-1).GetLastSubject(subject).GetName().equals(subject.GetName()))
            {
                weeks.get(weeks.size()-1).GetLastSubject(subject).SetEndTime(subject.GetEndTime());
            }
            else
            {
                weeks.get(weeks.size() - 1).AddSubject(subject);
            }


            //week 2
            startDate = (Date) formatter.parse("2015-11-13 08:45:00");
            endDate = (Date) formatter.parse("2015-11-13 10:25:00");
            subject = new Subject("EAG", startDate, endDate, "4.01");
            calendar = DateToCalendar(subject.GetStartTime());
            number = calendar.get(Calendar.WEEK_OF_YEAR);
            weeks.add(new Week(number));

            if( weeks.get(weeks.size()-1).GetLastSubject(subject).GetName().equals(subject.GetName()))
            {
                weeks.get(weeks.size()-1).GetLastSubject(subject).SetEndTime(subject.GetEndTime());
            }
            else
            {
                weeks.get(weeks.size() - 1).AddSubject(subject);
            }

            startDate = (Date) formatter.parse("2015-11-13 13:15:00");
            endDate = (Date) formatter.parse("2015-11-13 16:55:00");
            subject = new Subject("VSD", startDate, endDate, "4.66");
            calendar = DateToCalendar(subject.GetStartTime());
            number = calendar.get(Calendar.WEEK_OF_YEAR);

            if( weeks.get(weeks.size()-1).GetLastSubject(subject).GetName().equals(subject.GetName()))
            {
                weeks.get(weeks.size()-1).GetLastSubject(subject).SetEndTime(subject.GetEndTime());
            }
            else
            {
                weeks.get(weeks.size() - 1).AddSubject(subject);
            }





            //week 3
            startDate = (Date) formatter.parse("2015-11-20 08:45:00");
            endDate = (Date) formatter.parse("2015-11-20 10:25:00");
            subject = new Subject("EAG", startDate, endDate, "4.01");
            calendar = DateToCalendar(subject.GetStartTime());
            number = calendar.get(Calendar.WEEK_OF_YEAR);
            weeks.add(new Week(number));

            if( weeks.get(weeks.size()-1).GetLastSubject(subject).GetName().equals(subject.GetName()))
            {
                weeks.get(weeks.size()-1).GetLastSubject(subject).SetEndTime(subject.GetEndTime());
            }
            else
            {
                weeks.get(weeks.size() - 1).AddSubject(subject);
            }

            startDate = (Date) formatter.parse("2015-11-20 13:15:00");
            endDate = (Date) formatter.parse("2015-11-20 16:55:00");
            subject = new Subject("VSD", startDate, endDate, "4.66");
            calendar = DateToCalendar(subject.GetStartTime());
            number = calendar.get(Calendar.WEEK_OF_YEAR);

            if( weeks.get(weeks.size()-1).GetLastSubject(subject).GetName().equals(subject.GetName()))
            {
                weeks.get(weeks.size()-1).GetLastSubject(subject).SetEndTime(subject.GetEndTime());
            }
            else
            {
                weeks.get(weeks.size() - 1).AddSubject(subject);
            }



            //week 3
            startDate = (Date) formatter.parse("2015-11-27 08:45:00");
            endDate = (Date) formatter.parse("2015-11-27 10:25:00");
            subject = new Subject("EAG", startDate, endDate, "4.01");
            calendar = DateToCalendar(subject.GetStartTime());
            number = calendar.get(Calendar.WEEK_OF_YEAR);
            weeks.add(new Week(number));

            if( weeks.get(weeks.size()-1).GetLastSubject(subject).GetName().equals(subject.GetName()))
            {
                weeks.get(weeks.size()-1).GetLastSubject(subject).SetEndTime(subject.GetEndTime());
            }
            else
            {
                weeks.get(weeks.size() - 1).AddSubject(subject);
            }

            startDate = (Date) formatter.parse("2015-11-27 13:15:00");
            endDate = (Date) formatter.parse("2015-11-27 16:55:00");
            subject = new Subject("VSD", startDate, endDate, "4.66");
            calendar = DateToCalendar(subject.GetStartTime());
            number = calendar.get(Calendar.WEEK_OF_YEAR);

            if( weeks.get(weeks.size()-1).GetLastSubject(subject).GetName().equals(subject.GetName()))
            {
                weeks.get(weeks.size()-1).GetLastSubject(subject).SetEndTime(subject.GetEndTime());
            }
            else
            {
                weeks.get(weeks.size() - 1).AddSubject(subject);
            }
        }
        catch (ParseException e) {
            Log.e("ScheduleApp",e.getMessage());
        }
    }

    class Task1 extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... arg0)
        {
            LoadTempData();
            return "";
        }

        protected void onPostExecute(String ab)
        {
        }
    }
}
