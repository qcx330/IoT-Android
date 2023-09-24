package com.example.test.ui.notifications;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.test.databinding.FragmentNotificationsBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    LineChart lineChart;
    List<Entry> valueHumidity, valueMoisture, valueTemperature, valueSoilTemperature;
    LineDataSet humidity, moisture, temperature, soilTemperature;
    LineData lineDataHumidity, lineDataMoisture, lineDataTemperature, lineDataSoilTemperature;

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        lineChart = binding.lineChart;
        initList();



        return root;
    }

    public void initList(){
        valueHumidity = new ArrayList<>();
        valueMoisture = new ArrayList<>();
        valueTemperature = new ArrayList<>();
        valueSoilTemperature = new ArrayList<>();
    }

    public void initDataSet(){
        if(!valueHumidity.isEmpty())
        humidity = new LineDataSet(valueHumidity, "Humidity");
        moisture = new LineDataSet(valueMoisture, "Moisture");
        temperature = new LineDataSet(valueTemperature, "Temperature");
        soilTemperature = new LineDataSet(valueSoilTemperature, "Soil Temperature");

        humidity.setColor(Color.GREEN);
        moisture.setColor(Color.RED);
        temperature.setColor(Color.BLUE);
        soilTemperature.setColor(Color.YELLOW);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}