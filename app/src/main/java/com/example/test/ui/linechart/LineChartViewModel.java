package com.example.test.ui.linechart;

import android.graphics.Color;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.test.utils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
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

public class LineChartViewModel extends ViewModel {
    LineDataSet tempSet, humiSet;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private MutableLiveData<String> stationName = new MutableLiveData<>();

    public MutableLiveData<String> getStationName() {
        return stationName;
    }

    public void setStationName(String value) {
        stationName.setValue(value);
    }

    public void setStationNameAsync(String value) {
        new Thread(() -> {
            // Simulate a delay
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Update the LiveData with the result
            stationName.postValue(value);
        }).start();
    }

    public LineChartViewModel() {
    }

    private MutableLiveData<LineData> lineChartData = new MutableLiveData<>();

    public MutableLiveData<LineData> getLineChartData() {
        return lineChartData;
    }

    public void updateLineChartData(LineDataSet dataSet1, LineDataSet dataSet2) {
        LineData lineData = new LineData(dataSet1, dataSet2);
        lineChartData.setValue(lineData);
    }


    public void updateChart(String path) {
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

                    float floatDate = date.getTime();
                    temperatureEntries.add(new Entry(floatDate, (float) utils.doiNhietDo(temperature)));
                    humidityEntries.add(new Entry(floatDate, humidity));
                }
                Collections.sort(temperatureEntries, new EntryXComparator());
                Collections.sort(humidityEntries, new EntryXComparator());
                tempSet = new LineDataSet(temperatureEntries, "Temperature");
                tempSet.setColor(Color.RED);
                tempSet.setLineWidth(2f);
                humiSet = new LineDataSet(humidityEntries, "Humidity");
                humiSet.setColor(Color.BLUE);
                humiSet.setLineWidth(2f);

                updateLineChartData(tempSet, humiSet);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}