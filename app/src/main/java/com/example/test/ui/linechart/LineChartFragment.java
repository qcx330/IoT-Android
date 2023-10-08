package com.example.test.ui.linechart;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.test.databinding.FragmentLinechartBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
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
    LineChartViewModel lineChartViewModel;
    private FragmentLinechartBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lineChartViewModel =
                new ViewModelProvider(requireActivity()).get(LineChartViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentLinechartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        lineChart = binding.lineChart;
        setupLineChart();

        lineChartViewModel.getStationName().observe(getViewLifecycleOwner(), data -> {
            if (data != null) {
                String staionName = data;
                Log.d("linechart", "n: " + staionName);
                lineChartViewModel.updateChart(staionName);
                lineChartViewModel.getLineChartData().observe(getViewLifecycleOwner(), new Observer<LineData>() {
                    @Override
                    public void onChanged(LineData lineData) {
                        lineChart.setData(lineData);
                        lineChart.invalidate();
                    }
                });
            }
            else Log.d("linechart", "null");
        });
        return root;
    }
    private void setupLineChart() {
        XAxis xAxis = lineChart.getXAxis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm"); // Customize the time format as needed
        xAxis.setValueFormatter(new TimeAxisValueFormatter(dateFormat));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}