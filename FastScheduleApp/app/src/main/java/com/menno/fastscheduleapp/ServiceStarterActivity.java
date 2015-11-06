package com.menno.fastscheduleapp;

/**
 * ServiceStarterActivity.java
 * @author  Menno Sijben
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class ServiceStarterActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Info info = new Info();
        startService(new Intent(getApplication(), CornerService.class));
        finish();
    }

}