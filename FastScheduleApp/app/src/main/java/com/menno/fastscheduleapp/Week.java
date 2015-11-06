package com.menno.fastscheduleapp;

/**
 * Week.java
 * @author  Menno Sijben
 */

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Week {

    private List<Day> days = new ArrayList<Day>();
    private int weekNumber;

    public Week(int weekNumber)
    {
        days.add(new Day("Maandag"));
        days.add(new Day("Dinsdag"));
        days.add(new Day("Woensdag"));
        days.add(new Day("Donderdag"));
        days.add(new Day("Vrijdag"));

        this.weekNumber = weekNumber;
    }

    public int GetWeekNumber()
    {
        return weekNumber;
    }

    public List<Day> GetDays()
    {
        return days;
    }

    public void AddSubject(Subject subject)
    {
        Calendar calendar = DateToCalendar(subject.GetStartTime());
        int number = calendar.get(Calendar.DAY_OF_WEEK)-2;

        days.get(number).AddSubject(subject);
    }

    public Subject GetLastSubject(Subject subject)
    {
        Calendar calendar = DateToCalendar(subject.GetStartTime());
        int number = calendar.get(Calendar.DAY_OF_WEEK)-2;
        List<Subject> subjects = days.get(number).GetSubject();
        if(subjects.size() == 0)
            return new Subject("ra", new Date(), new Date(), "");

        return subjects.get(subjects.size()-1);
    }

    public String MakeString()
    {
        return "Week " + Integer.toString(weekNumber);
    }

    public static Calendar DateToCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}

