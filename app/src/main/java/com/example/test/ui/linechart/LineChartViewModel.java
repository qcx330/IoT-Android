package com.example.test.ui.linechart;

import android.graphics.Color;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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

//    List<String> xValues;
//    List<Entry> valueHumidity, valueMoisture, valueTemperature, valueSoilTemperature;
    ArrayList<Entry> humidityEntries = new ArrayList<>();
    ArrayList<Entry> temperatureEntries = new ArrayList<>();
    LineDataSet tempSet, humiSet;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private MutableLiveData<String> stationName = new MutableLiveData<>();
    public MutableLiveData<String> getStationName() {
        return stationName;
    }

    public void setStationName(String value) {
        stationName.setValue(value);
    }
    public LineChartViewModel() {
    }
    private MutableLiveData<LineData> lineChartData = new MutableLiveData<>();

    public MutableLiveData<LineData> getLineChartData() {
        return lineChartData;
    }

    public void updateLineChartData(LineData data) {
        lineChartData.setValue(data);
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
                    humiSet.setColor(Color.BLUE);

//                    updateLineChartData(tempSet, humiSet);
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

//    public void InitLineChart(LineChart lineChart){
//        xValues = new ArrayList<>();
//        Description description = new Description();
//        description.setText("Soil Temperature (Last 30 Days)");
//        description.setPosition(150f, 15f);
//        lineChart.setDescription(description);
//        lineChart.getAxisRight().setDrawLabels(false);
//
//        XAxis xAxis = lineChart.getXAxis();
//        for(int j = 1; j <= 2; j++)
//            for(int i = 1; i <= 12; i++)
//                xValues.add(i + "AM");
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
//        xAxis.setLabelCount(4);
//        xAxis.setAxisLineColor(Color.WHITE);
//        xAxis.setGranularity(1f);
//
//        YAxis yAxis = lineChart.getAxisLeft();
//        yAxis.setAxisMinimum(-150f);
//        yAxis.setAxisMaximum(100f);
//        yAxis.setAxisLineWidth(2f);
//        yAxis.setAxisLineColor(Color.WHITE);
//        yAxis.setLabelCount(50);
//    }

}