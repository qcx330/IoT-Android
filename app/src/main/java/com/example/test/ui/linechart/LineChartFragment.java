package com.example.test.ui.linechart;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.test.databinding.FragmentLinechartBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class LineChartFragment extends Fragment {

    LineChart lineChart;
    List<Entry> valueHumidity, valueTemperature;
    LineDataSet humidity, temperature;
    LineData lineDataHumidity, lineDataTemperature;

    private FragmentLinechartBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LineChartViewModel notificationsViewModel =
                new ViewModelProvider(this).get(LineChartViewModel.class);

        binding = FragmentLinechartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        lineChart = binding.lineChart;
        initList();



        return root;
    }

    public void initList(){
        valueHumidity = new ArrayList<>();
        valueTemperature = new ArrayList<>();
    }

    public void initDataSet(){
        if(!valueHumidity.isEmpty())
        humidity = new LineDataSet(valueHumidity, "Humidity");
        temperature = new LineDataSet(valueTemperature, "Temperature");

        humidity.setColor(Color.BLUE);
        temperature.setColor(Color.RED);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}