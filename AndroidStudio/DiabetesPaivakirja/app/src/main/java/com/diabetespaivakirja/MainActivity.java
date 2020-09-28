package com.diabetespaivakirja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarStuff();
    }

    private void calendarStuff() {
        final CalendarView calender = findViewById(R.id.calendarView);
        final TextView date_view = findViewById(R.id.dateView);
        final Calendar calendar = Calendar.getInstance();

        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        String ddMmYy = date + "/" + month + "/" + year;
        date_view.setText(ddMmYy);

        calender.setVisibility(View.GONE);

        // Kopioitu täältä: https://www.geeksforgeeks.org/android-creating-a-calendar-view-app/
        // Add Listener in calendar
        calender
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
                                String Date
                                        = dayOfMonth + "/"
                                        + (month + 1) + "/" + year;

                                // set this date in TextView for Display
                                date_view.setText(Date);
                                calender.setVisibility(View.GONE);
                            }
                        });
    }

    public void onDateViewPressed(View view) {
        final CalendarView calendarView = findViewById(R.id.calendarView);
        if(calendarView.getVisibility() == View.GONE) {
            calendarView.setVisibility(View.VISIBLE);
        } else {
            calendarView.setVisibility(View.GONE);
        }
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