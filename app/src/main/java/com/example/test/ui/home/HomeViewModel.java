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

    private MutableLiveData<String> stationName = new MutableLiveData<>();;
    private MutableLiveData<Double> temperature;
    private MutableLiveData<Double> feelsLike;
    private MutableLiveData<Double> tempMin;
    private MutableLiveData<Double> tempMax;
    private MutableLiveData<Double> windSpeed;
    private MutableLiveData<Integer> windDeg;
    private MutableLiveData<Double> windGust;
    private MutableLiveData<Integer> pressure;
    private MutableLiveData<Integer> humidity;
    private MutableLiveData<Integer> seaLevel;
    private MutableLiveData<Integer> grndLevel;

}