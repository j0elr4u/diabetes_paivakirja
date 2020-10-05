package com.diabetespaivakirja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ArvoActivityInfo extends AppCompatActivity {

    TextView yearView, monthView, dayView, valueView;
    Intent intent;

    Verensokerit verensokerit = Verensokerit.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arvo_info);

        main();
    }

    private void main() {
        yearView = findViewById(R.id.yearView);
        monthView = findViewById(R.id.monthView);
        dayView = findViewById(R.id.dayView);
        valueView = findViewById(R.id.valueView);

        intent = getIntent();

        updateUI();
    }

    private void updateUI() {
        String verensokeriId = intent.getStringExtra("verensokeriId");
        Verensokeri vs = verensokerit.getVerensokeriById(verensokeriId);

        String year = String.valueOf(vs.getYear());
        String month = String.valueOf(vs.getMonth());
        String day = String.valueOf(vs.getDay());
        String value = String.valueOf(vs.getVerensokeri());

        yearView.setText(year);
        monthView.setText(month);
        dayView.setText(day);
        valueView.setText(value);
    }
}