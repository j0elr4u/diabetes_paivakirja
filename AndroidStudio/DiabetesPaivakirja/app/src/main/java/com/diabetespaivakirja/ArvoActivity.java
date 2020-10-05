package com.diabetespaivakirja;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ArvoActivity extends AppCompatActivity {

    Calendar calendar;
    Verensokerit verensokerit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arvo);

        main();
    }

    private void main() {
        verensokerit = Verensokerit.getInstance();
        calendar = Calendar.getInstance();

        boolean listView_show = true;
        if(listView_show) {
            listView();
        } else if(!listView_show) {
            anyChart();
        }
    }

    private void listView() {
        ListView listView = findViewById(R.id.list_view);

        final List<Verensokeri> verensokerit_list = getVS(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
        List<String> strings = getVSStrings(verensokerit_list);
        listView.setAdapter(new ArrayAdapter<>( this, android.R.layout.simple_list_item_1, strings));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("ArvoActivity", "onButtonListener(" + i + ")");
                Intent nextActivity = new Intent(ArvoActivity.this, ArvoActivityInfo.class);
                nextActivity.putExtra("verensokeriId", verensokerit_list.get(i).getVerensokeriID());
                startActivity(nextActivity);
            }
        });
    }

    private void anyChart() {
        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        //anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Cartesian cartesian = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();
        //data.add(new ValueDataEntry("ma", 7));
        //data.add(new ValueDataEntry("ti", 8));
        //data.add(new ValueDataEntry("kv", 10));

        List<Verensokeri> verensokerit_list = getVS(2020, 10);
        for(int i = 0; i < verensokerit_list.size(); i++) {
            Verensokeri vs = verensokerit_list.get(i);

            data.add(new ValueDataEntry(/*"("+vs.getDay()+")" + getVSNameOfDay(vs)*/ vs.getDay(), vs.getVerensokeri()));
        }

        Column column = cartesian.column(data);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("mmol/l{%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("Verensokeriarvo");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("mmol/l{%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Pvm");
        cartesian.yAxis(0).title("Keskiarvo");

        anyChartView.setChart(cartesian);
    }

    // Returns a sorted list with average values of days in wanted year->month [ example: getVS( 2020, 10 ); ]
    private List<Verensokeri> getVS(int wantedYear, int wantedMonth) {
        List<Verensokeri> verensokerit_list = verensokerit.getVerensokerit();
        List<Verensokeri> verensokerit_list_wanted = new ArrayList<>();
        List<Verensokeri> verensokerit_list_averaged = new ArrayList<>();
        List<Verensokeri> verensokerit_list_sorted = new ArrayList<>();

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

    private List<String> getVSStrings(List<Verensokeri> vs) {
        List<String> strings = new ArrayList<>();

        for(Verensokeri verensokeri : vs) {
            String s = getVSNameOfDay(verensokeri) + " (" + verensokeri.getDay() + ")";
            strings.add(s);
        }
        return strings;
    }

    @SuppressLint({"SimpleDateFormat", "DefaultLocale"})
    private String getVSNameOfDay(Verensokeri vs) {
        Date date;
        try {
            String dateString = String.format("%d-%d-%d", vs.getYear(), vs.getMonth(), vs.getDay());
            date = new SimpleDateFormat("yyyy-M-d").parse(dateString);
            assert date != null;
            return new SimpleDateFormat("EEEE", Locale.US).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "ERROR_NO_NAME";
    }
}