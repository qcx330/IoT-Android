package com.example.test.ui.linechart;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.test.databinding.FragmentLinechartBinding;
import com.example.test.ui.home.HomeFragment;
import com.example.test.utils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class LineChartFragment extends Fragment {

    LineChart lineChart;
    List<Entry> valueHumidity, valueTemperature;
    LineDataSet tempSet, humiSet;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FragmentLinechartBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LineChartViewModel lineChartViewModel =
                new ViewModelProvider(this).get(LineChartViewModel.class);

        binding = FragmentLinechartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        lineChart = binding.lineChart;
        initList();

//        Bundle args = getArguments();
//        if (args != null) {
//            String stationName = args.getString("stationName");
//            updateChart(stationName);
////            Log.d("FRAGMENT", stationName);
//        }
        lineChartViewModel.getLineChartData().observe(getViewLifecycleOwner(), new Observer<LineData>() {
            @Override
            public void onChanged(LineData lineData) {
                lineChart.setData(lineData);
                lineChart.invalidate();
            }
        });


        lineChartViewModel.getStationName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });

        return root;
    }

    public void initList(){
        valueHumidity = new ArrayList<>();
        valueTemperature = new ArrayList<>();
    }
    public void updateChart(String path){
        DatabaseReference dataRef = database.getReference(path);
        dataRef.orderByKey().limitToLast(5).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Entry> humidityEntries = new ArrayList<>();
                ArrayList<Entry> temperatureEntries = new ArrayList<>();

                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Date date = childSnapshot.child("time").getValue(Date.class);
                    float temperature = childSnapshot.child("temperature").getValue(Float.class);
                    float humidity = childSnapshot.child("humidity").getValue(Float.class);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                    Timestamp timestamp = new Timestamp(date.getTime());
                    float floatDate = (float) (timestamp.getTime() / 1000.0);
                    temperatureEntries.add(new Entry(floatDate, temperature));
                    humidityEntries.add(new Entry(floatDate, humidity));
                    Log.d("LINECHART", "key: " +childSnapshot.getKey());
                    Log.d("LINECHART", "key: " + dateFormat.format(date));
                    Log.d("LINECHART", "key: " + String.valueOf(temperature));
                }
                Collections.sort(temperatureEntries, new EntryXComparator());
                Collections.sort(humidityEntries, new EntryXComparator());
                try {
                    tempSet = new LineDataSet(temperatureEntries, "Temperature");
                    tempSet.setColor(Color.RED);
                    humiSet = new LineDataSet(humidityEntries, "Humidity");
                    tempSet.setColor(Color.BLUE);

                    LineData lineData = new LineData(tempSet, humiSet);
                    lineChart.setData(lineData);
                    lineChart.invalidate();
                }catch (Exception e)
                {
                    Log.d("chart", e.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
//    @SuppressLint("SuspiciousIndentation")
//    public void initDataSet(){
//        if(!valueHumidity.isEmpty())
//        humidity = new LineDataSet(valueHumidity, "Humidity");
//        temperature = new LineDataSet(valueTemperature, "Temperature");
//
//        humidity.setColor(Color.BLUE);
//        temperature.setColor(Color.RED);
//    }

    public static LineChartFragment newInstance(String data) {
        LineChartFragment fragment = new LineChartFragment();
        Bundle args = new Bundle();
        args.putString("stationName", data);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}