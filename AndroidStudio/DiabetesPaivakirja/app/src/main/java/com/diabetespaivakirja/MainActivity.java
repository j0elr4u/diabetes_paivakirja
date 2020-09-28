package com.diabetespaivakirja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static int date;
    private static int month;
    private static int year;

    private SharedPrefs sp = new SharedPrefs("prefs");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarStuff();
        updateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateUI();
    }

    @Override
    public void onBackPressed() {
        if(!isCalendarVisible()) {
            super.onBackPressed();
        } else {
            toggleCalendar();
        }
    }

    private void updateUI() {
        updateName();
    }

    private void updateName() {
        final TextView name_view = findViewById(R.id.nameView);
        String name = this.sp.getDefaultPrefString("first_name", this) + " " + this.sp.getDefaultPrefString("last_name", this);

        name_view.setText(name);
    }

    // Calendar stuff
    private void calendarStuff() {
        final CalendarView calendar_view = getCalendarView();
        final TextView date_view = findViewById(R.id.dateView);
        final Calendar calendar = Calendar.getInstance();
        MainActivity.date = calendar.get(Calendar.DATE);
        MainActivity.month = calendar.get(Calendar.MONTH) + 1;
        MainActivity.year = calendar.get(Calendar.YEAR);
        String ddMmYy = MainActivity.date + "/" + MainActivity.month + "/" + MainActivity.year;
        date_view.setText(ddMmYy);

        toggleCalendar();

        // Kopioitu täältä: https://www.geeksforgeeks.org/android-creating-a-calendar-view-app/
        // Add Listener in calendar
        calendar_view
                .setOnDateChangeListener(
                        new CalendarView
                                .OnDateChangeListener() {
                            @Override

                            // In this Listener have one method
                            // and in this method we will
                            // get the value of DAYS, MONTH, YEARS
                            public void onSelectedDayChange(
                                    @NonNull CalendarView view,
                                    int year,
                                    int month,
                                    int dayOfMonth)
                            {

                                // Store the value of date with
                                // format in String type Variable
                                // Add 1 in month because month
                                // index is start with 0
                                MainActivity.date = dayOfMonth;
                                MainActivity.month = (month + 1);
                                MainActivity.year = year;
                                String Date
                                        = MainActivity.date + "/"
                                        + MainActivity.month + "/" + MainActivity.year;

                                // set this date in TextView for Display
                                date_view.setText(Date);
                                toggleCalendar();
                            }
                        });
    }

    private void toggleCalendar() {
        final CalendarView calendar_view = getCalendarView();
        if(isCalendarVisible()) {
            calendar_view.setVisibility(View.GONE);
        }
        else {
            calendar_view.setVisibility(View.VISIBLE);
        }
    }

    private boolean isCalendarVisible() {
        final CalendarView calendar_view = getCalendarView();
        if(calendar_view.getVisibility() == View.VISIBLE) {
            return true;
        } else {
            return false;
        }
    }

    private CalendarView getCalendarView() {
        return findViewById(R.id.calendarView);
    }

    // Buttons

    public void onDateViewPressed(View view) {
        toggleCalendar();
    }

    public void onButtonPressed_Settings(View view){
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
    //Diagrammi Activity button vv
    public void onButtonPressed_Arvo(View view){
       Intent intent = new Intent(MainActivity.this, ArvoActivity.class);
        startActivity(intent);}
}