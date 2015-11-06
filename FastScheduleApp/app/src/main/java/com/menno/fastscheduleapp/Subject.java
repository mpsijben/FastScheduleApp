package com.menno.fastscheduleapp;

/**
 * Subject.java
 * @author  Menno Sijben
 */

import java.util.Date;

public class Subject {

    private String name;
    private Date startTime;
    private Date endTime;
    private String room;
    private int color;

    public Subject(String name, Date startTime, Date endTime, String room)
    {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room.replace("R1_","");
    }

    public void SetEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public String GetName()
    {
        return name;
    }

    public int GetColor()
    {
        return color;
    }

    public void SetColor(int color)
    {
        this.color = color;
    }


    public Date GetStartTime()
    {
        return startTime;
    }

    public Date GetEndTime()
    {
        return endTime;
    }

    public String GetRoom()
    {
        return room;
    }

}

