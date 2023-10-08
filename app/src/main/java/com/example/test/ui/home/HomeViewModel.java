package com.example.test.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.AsyncListUtil;

import com.example.test.MqttHelper;
import com.example.test.databinding.FragmentHomeBinding;
import com.example.test.utils;
import com.google.android.material.slider.Slider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.List;

public class HomeViewModel extends ViewModel {

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
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Update the LiveData with the result
            stationName.postValue(value);
        }).start();
    }

    public MutableLiveData<Double> getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double value) {
        feelsLike.setValue(value);
    }

    public MutableLiveData<Integer> getPressure() {
        return pressure;
    }

    public void setPressure(int value) {
        pressure.setValue(value);
    }

    public MutableLiveData<Double> getTemperature() {
        return temperature;
    }

    public void setTemperature(double value) {
        temperature.setValue(value);
    }

    public MutableLiveData<Double> getTempMin() {
        return tempMin;
    }

    public void setTempMin(double value) {
        tempMin.setValue(value);
    }

    public MutableLiveData<Double> getTempMax() {
        return tempMax;
    }

    public void setTempMax(double value) {
        tempMax.setValue(value);
    }

    public MutableLiveData<Double> getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double value) {
        windSpeed.setValue(value);
    }

    public MutableLiveData<Integer> getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(int value) {
        windDeg.setValue(value);
    }

    public MutableLiveData<Double> getWindGust() {
        return windGust;
    }

    public void setWindGust(double value) {
        windGust.setValue(value);
    }

    public MutableLiveData<Integer> getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(int value) {
        seaLevel.setValue(value);
    }

    public MutableLiveData<Integer> getHumidity() {
        return humidity;
    }

    public void setHumidity(int value) {
        humidity.setValue(value);
    }

    public MutableLiveData<Integer> getGrndLevel() {
        return grndLevel;
    }

    public void setGrndLevel(int value) {
        grndLevel.setValue(value);
    }

    private MutableLiveData<String> stationName = new MutableLiveData<>();
    private MutableLiveData<Double> temperature = new MutableLiveData<>();
    private MutableLiveData<Double> feelsLike = new MutableLiveData<>();
    private MutableLiveData<Double> tempMin = new MutableLiveData<>();
    private MutableLiveData<Double> tempMax = new MutableLiveData<>();
    private MutableLiveData<Double> windSpeed = new MutableLiveData<>();
    private MutableLiveData<Integer> windDeg = new MutableLiveData<>();
    private MutableLiveData<Double> windGust = new MutableLiveData<>();
    private MutableLiveData<Integer> pressure = new MutableLiveData<>();
    private MutableLiveData<Integer> humidity = new MutableLiveData<>();
    private MutableLiveData<Integer> seaLevel = new MutableLiveData<>();
    private MutableLiveData<Integer> grndLevel  = new MutableLiveData<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    public void getData(String path){
        DatabaseReference dataRef = database.getReference(path);
        dataRef.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()){
                    Double temperature = childSnapshot.child("temperature").getValue(Double.class);
                    setTemperature(utils.doiNhietDo(temperature));

                    Double feelsLike = childSnapshot.child("feelsLike").getValue(Double.class);
                    setFeelsLike(utils.doiNhietDo(feelsLike));
                    Double tempMin = childSnapshot.child("tempMin").getValue(Double.class);
                    setTempMin(utils.doiNhietDo(tempMin));
                    Double tempMax = childSnapshot.child("tempMax").getValue(Double.class);
                    setTempMax(utils.doiNhietDo(tempMax));
                    Double windSpeed = childSnapshot.child("windSpeed").getValue(Double.class);
                    setWindSpeed(windSpeed);
                    int windDeg = childSnapshot.child("windDeg").getValue(Integer.class);
                    setWindDeg(windDeg);
                    Double windGust = childSnapshot.child("windGust").getValue(Double.class);
                    setWindGust(windGust);

                    int pressure = childSnapshot.child("pressure").getValue(Integer.class);
                    setPressure(pressure);

                    int humidity = childSnapshot.child("humidity").getValue(Integer.class);
                    setHumidity(humidity);

                    int seaLevel = childSnapshot.child("seaLevel").getValue(Integer.class);
                    setSeaLevel(seaLevel);

                    int grndLevel = childSnapshot.child("grndLevel").getValue(Integer.class);
                    setGrndLevel(grndLevel);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database Error: " + error.getMessage());
            }
        });
    }
}