package com.menno.fastscheduleapp;

/**
 * Day.java
 * @author  Menno Sijben
 */

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Day
{
    private String name;
    private List<Subject> subjects = new ArrayList<Subject>();
    private List<Integer> unusedColors = new ArrayList<Integer>();

    public Day(String name)
    {
        this.name = name;
        unusedColors.add(Color.parseColor("#551da0f8"));
        unusedColors.add(Color.parseColor("#552ecc71"));
        unusedColors.add(Color.parseColor("#55c1df36"));
        unusedColors.add(Color.parseColor("#55f8e31d"));
        unusedColors.add(Color.parseColor("#551df8e3"));
        unusedColors.add(Color.parseColor("#55cc99ff"));
    }

    public String GetName()
    {
        return name;
    }

    public void AddSubjectToets(Subject subject)
    {
        subject.SetColor(Color.parseColor("#55c7271d"));
        subjects.add(subject);
    }

    public void AddSubject(Subject subject)
    {
        Random ran = new Random();
        int x = ran.nextInt(unusedColors.size());
        subject.SetColor(unusedColors.get(x));
        unusedColors.remove(x);
        subjects.add(subject);
    }

    public List<Subject> GetSubject()
    {
        return subjects;
    }


}

