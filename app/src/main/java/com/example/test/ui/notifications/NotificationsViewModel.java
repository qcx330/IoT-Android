package com.example.test.ui.notifications;

import android.graphics.Color;

import androidx.lifecycle.ViewModel;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class NotificationsViewModel extends ViewModel {

    List<String> xValues;
    List<Entry> valueHumidity, valueMoisture, valueTemperature, valueSoilTemperature;

    public NotificationsViewModel() {
    }

    public void InitLineChart(LineChart lineChart){
        xValues = new ArrayList<>();
        Description description = new Description();
        description.setText("Soil Temperature (Last 30 Days)");
        description.setPosition(150f, 15f);
        lineChart.setDescription(description);
        lineChart.getAxisRight().setDrawLabels(false);

        XAxis xAxis = lineChart.getXAxis();
        for(int j = 1; j <= 2; j++)
            for(int i = 1; i <= 12; i++)
                xValues.add(i + "AM");
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
        xAxis.setLabelCount(4);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setGranularity(1f);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum(-150f);
        yAxis.setAxisMaximum(100f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.WHITE);
        yAxis.setLabelCount(50);
    }

}