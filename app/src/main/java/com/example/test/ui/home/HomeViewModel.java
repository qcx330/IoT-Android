package com.example.test.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.test.MqttHelper;
import com.example.test.databinding.FragmentHomeBinding;
import com.google.android.material.slider.Slider;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.List;

public class HomeViewModel extends ViewModel {
    MqttHelper mqttHelper;
    public HomeViewModel() {
    }

    public void startMQTT(Context context, TextView value, TextView valueSlider, Slider slider){
        mqttHelper = new MqttHelper(context);
        mqttHelper.connect();
//        mqttHelper.setCallback(new MqttCallbackExtended() {
//            @Override
//            public void connectionLost(Throwable cause) {
//
//            }
//
//            @Override
//            public void messageArrived(String topic, MqttMessage message) throws Exception {
//                value.setText(message.toString());
//                slider.setValue(Integer.parseInt(message.toString()));
//                valueSlider.setText(message.toString());
//                Log.d("Test", topic + "***" + message.toString());
//
//            }
//
//            @Override
//            public void deliveryComplete(IMqttDeliveryToken token) {
//
//            }
//
//            @Override
//            public void connectComplete(boolean reconnect, String serverURI) {
//
//            }
//        });
    }
}