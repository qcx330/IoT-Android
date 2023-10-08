package com.example.test.ui.linechart;

import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.components.AxisBase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeAxisValueFormatter extends ValueFormatter {
    private final SimpleDateFormat dateFormat;

    public TimeAxisValueFormatter(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        return dateFormat.format(new Date((long) value));
    }
}
