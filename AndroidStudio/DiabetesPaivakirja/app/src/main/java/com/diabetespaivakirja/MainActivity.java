package com.diabetespaivakirja;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText txtDate, txtTime;
    Calendar calendar;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    private double value;

    private SharedPrefs sp;

    private Verensokerit verensokerit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main();
    }

    private void main() {
        sp = new SharedPrefs(this,"prefs");
        verensokerit = Verensokerit.getInstance();

        verensokerit_init();

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
        super.onBackPressed();
    }

    private void updateUI() {
        updateUI_Name();
        updateUI_Calendar();
    }

    private void updateUI_Name() {
        final TextView name_view = findViewById(R.id.yearView);
        String name = this.sp.getDefaultPrefString("first_name", this) + " " + this.sp.getDefaultPrefString("last_name", this);

        name_view.setText(name);
    }

    private void updateUI_Calendar() {
        String dateText = to00String(mDay) + "/" + to00String(mMonth) + "/" + mYear;
        String timeText = to00String(mHour) + ":" + to00String(mMinute);

        txtDate.setText(dateText);
        txtTime.setText(timeText);
    }

    // Calendar stuff

    public void calendarStuff() {
        txtDate = findViewById(R.id.paivamaaraView);
        txtTime = findViewById(R.id.aikaView);

        txtDate.setOnClickListener(this);
        txtTime.setOnClickListener(this);

        calendar = Calendar.getInstance();

        // Get Current Date
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH) + 1;
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        // Get Current Time
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);

        // Set current date & time in the text
        // this is done in updateUI()

        // Rest is done in onClick()
    }

    // https://www.journaldev.com/9976/android-date-time-picker-dialog
    @Override
    public void onClick(View v) {

        if (v == txtDate) {

            // Launch Date Picker Dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            mYear = year;
                            mMonth = (monthOfYear + 1);
                            mDay = dayOfMonth;

                            updateUI_Calendar();
                        }
                    }, mYear, mMonth - 1, mDay);
            datePickerDialog.show();
        }
        if (v == txtTime) {

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            mMinute = minute;
                            mHour = hourOfDay;

                            updateUI_Calendar();
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }

    // Buttons

    public void onButtonPressed_Settings(View view){
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
    //Diagrammi Activity button vv
    public void onButtonPressed_Arvo(View view){
       Intent intent = new Intent(MainActivity.this, ArvoActivityInit.class);
        startActivity(intent);
    }

    public void onButtonPressed_Lisaa(View view){
        final EditText value_editText = findViewById(R.id.arvoView);
        if(!value_editText.getText().toString().isEmpty() && !value_editText.getText().toString().startsWith(".")) {
            value = Double.parseDouble(value_editText.getText().toString());
        }

        if(!lisaa_isDateValid()) {
            showToast("DATE IS INVALID", Toast.LENGTH_LONG);
            return;
        }
        if(!lisaa_isTimeValid()) {
            showToast("TIME IS INVALID", Toast.LENGTH_LONG);
            return;
        }
        if(!lisaa_isValueValid()) {
            showToast("VALUE IS INVALID", Toast.LENGTH_LONG);
            return;
        }

        // alhainen verensokeri
        if(value < 4) {
            showAlertDialog("Your blood sugar is very low!");
        }
        // korkea verensokeri
        else if(value > 7) {
            showAlertDialog("Your blood sugar is very high!");
        }

        // Lisää verensokeri ...
        showToast("Added value...", Toast.LENGTH_SHORT);
        Log.d("MainActivity", "\n" + value + "\n" + mMinute + "\n" + mHour + "\n" + mDay + "\n" + mMonth + "\n" + mYear);

        Verensokeri vs = new Verensokeri(value, mMinute, mHour, mDay, mMonth, mYear);
        verensokerit.lisaa(vs);
        verensokerit_save();
    }

    // Lisää arvo
    private boolean lisaa_isDateValid() {
        final String DATE_FORMAT = "dd-MM-yyyy";
        String date = mDay + "-" + mMonth + "-" + mYear;
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT, Locale.US);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    private boolean lisaa_isTimeValid() {
        return true; // Time is always valid ( for now )
    }
    private boolean lisaa_isValueValid() {
        return value > 0.0 && value < 40;
    }

    public void verensokerit_init() {
        if(!sp.getPrefString("Verensokerit").isEmpty()) {
            Verensokeri[] mcArray = new Gson().fromJson(sp.getPrefString("Verensokerit"), Verensokeri[].class);
            List<Verensokeri> verensokeriList = new ArrayList<>(Arrays.asList(mcArray));
            verensokerit.setVerensokerit(verensokeriList);
        }
    }

    public void verensokerit_save() {
        String verensokeritJson = new Gson().toJson(verensokerit.getVerensokerit());
        sp.putPref("Verensokerit", verensokeritJson);
    }

    // AlertDialog

    private void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(message);
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Okay",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    // Toast

    private void showToast(String message, int duration) {
        Toast.makeText(MainActivity.this, message,
                duration).show();
    }

    // Utils

    public String to00String(int i) {
        String s = String.valueOf(i);
        if(i < 10) {
            s = "0" + s;
        }
        return s;
    }
}