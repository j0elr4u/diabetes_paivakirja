package com.diabetespaivakirja;

import android.os.Bundle;

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


import java.util.ArrayList;
import java.util.List;


public class ArvoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arvo);

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        //anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Cartesian cartesian = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("ma", 7));
        data.add(new ValueDataEntry("ti", 8));
        data.add(new ValueDataEntry("kv", 10));


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

}