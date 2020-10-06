package com.diabetespaivakirja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ArvoActivityInfo extends AppCompatActivity {

    TextView yearView, monthView, dayView, valueView;
    Intent intent;
    Verensokerit verensokerit;
    List<Verensokeri> verensokeriList;
    Calendar calendar;

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
        verensokerit = Verensokerit.getInstance();
        calendar = Calendar.getInstance();

        verensokeriList = getVS();

        updateUI();
    }

    private void updateUI() {
        String verensokeriId = intent.getStringExtra("verensokeriId");
        Verensokeri vs = new Verensokeri();

        for(Verensokeri verensokeri : verensokeriList) {
            if(verensokeri.getVerensokeriID().equals(verensokeriId)) {
                vs = verensokeri;
            }
        }

        String year = String.valueOf(vs.getYear());
        String month = String.valueOf(vs.getMonth());
        String day = String.valueOf(vs.getDay());
        String value = String.valueOf(vs.getVerensokeri());

        yearView.setText(year);
        monthView.setText(month);
        dayView.setText(day);
        valueView.setText(value);
    }

    // Returns a sorted list with average values of days in wanted year->month
    private List<Verensokeri> getVS() {
        List<Verensokeri> verensokerit_list = verensokerit.getVerensokerit();
        List<Verensokeri> verensokerit_list_wanted = new ArrayList<>();
        List<Verensokeri> verensokerit_list_averaged = new ArrayList<>();
        List<Verensokeri> verensokerit_list_sorted = new ArrayList<>();

        int wantedYear = calendar.get(Calendar.YEAR);
        int wantedMonth = calendar.get(Calendar.MONTH) + 1;

        for(int i = 0; i < verensokerit_list.size(); i++) {
            Verensokeri vs = verensokerit_list.get(i);
            if(vs.getYear() == wantedYear && vs.getMonth() == wantedMonth) {
                verensokerit_list_wanted.add(vs);
            }
        }

        ArrayList<Integer> usedDays = new ArrayList<>();
        for(int ii = 0; ii < verensokerit_list_wanted.size(); ii++) {
            int num = 0;
            int sum = 0;
            Verensokeri verensokeri = verensokerit_list_wanted.get(ii);
            sum += verensokeri.getVerensokeri();
            num++;

            boolean canContinue = true;
            for(int integer : usedDays) {
                if(integer == verensokeri.getDay()) {
                    canContinue = false;
                    break;
                }
            }
            if(!canContinue) {
                continue;
            }

            usedDays.add(verensokeri.getDay());

            for(int i = 0; i < verensokerit_list_wanted.size(); i++) {
                Verensokeri vs = verensokerit_list_wanted.get(i);
                if(vs.getDay() == verensokeri.getDay()) {
                    sum += vs.getVerensokeri();
                    num++;
                }
            }
            Verensokeri new_verensokeri = new Verensokeri((double) (sum / num), verensokeri.getMinute(), verensokeri.getHour(), verensokeri.getDay(), verensokeri.getMonth(), verensokeri.getYear());
            verensokerit_list_averaged.add(new_verensokeri);
        }

        int last = 0;
        int index = -1;
        for(int i = 0; i < verensokerit_list_averaged.size(); i++) {
            int x = 999;
            for(int ii = 0; ii < verensokerit_list_averaged.size(); ii++) {
                Verensokeri vs = verensokerit_list_averaged.get(ii);
                if(vs.getDay() < x && vs.getDay() > last) {
                    x = vs.getDay();
                    index = ii;
                }
            }

            last = x;
            verensokerit_list_sorted.add(verensokerit_list_averaged.get(index));
        }

        return verensokerit_list_sorted;
    }

    // Buttons

    public void onButtonPressed_Back (View view) {
        super.onBackPressed();
    }
}