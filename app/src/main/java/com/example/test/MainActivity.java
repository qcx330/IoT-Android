package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.test.databinding.ActivityMainBinding;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;

import androidx.appcompat.app.AppCompatActivity;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {
    Button btnS1, btnS2,btnS3,btnS4,btnS5;
    LabeledSwitch switchPump, switchLight;
    private ActivityMainBinding binding;

    MqttHelper mqttHelper;
    AdafruitConnect adafruitConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mqttHelper = new MqttHelper(getApplicationContext());
        mqttHelper.connect();

        adafruitConnect = new AdafruitConnect(getApplicationContext());
        adafruitConnect.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                if (topic.contains("nutnhan1"))
                {
                    if (message.toString().equals("1"))
                        switchLight.setOn(true);
                    else switchLight.setOn(false);
                }
                if (topic.contains("nutnhan2"))
                {
                    if (message.toString().equals("1"))
                        switchPump.setOn(true);
                    else switchPump.setOn(false);
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        switchPump = binding.switchPump;
        switchLight = binding.switchLight;
        btnS1 = binding.btnS1;
        btnS2 = binding.btnS2;
        btnS3 = binding.btnS3;
        btnS4 = binding.btnS4;
        btnS5 = binding.btnS5;

        switchLight.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (isOn == true)
                    sendDataMQTT("qcx330/feeds/nutnhan1", "1");
                else sendDataMQTT("qcx330/feeds/nutnhan1", "0");
            }
        });
        switchPump.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (isOn == true)
                    sendDataMQTT("qcx330/feeds/nutnhan2", "1");
                else sendDataMQTT("qcx330/feeds/nutnhan2", "0");
            }
        });
        btnS1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), StationActivity.class);
                i.putExtra("stationName", "station1");
                startActivity(i);
            }
        });
        btnS2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), StationActivity.class);
                i.putExtra("stationName", "station2");
                startActivity(i);
            }
        });
        btnS3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), StationActivity.class);
                i.putExtra("stationName", "station3");
                startActivity(i);
            }
        });
        btnS4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), StationActivity.class);
                i.putExtra("stationName", "station4");
                startActivity(i);
            }
        });
        btnS5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), StationActivity.class);
                i.putExtra("stationName", "station5");
                startActivity(i);
            }
        });
    }
    public void sendDataMQTT(String topic, String value){
        MqttMessage msg = new MqttMessage();
        msg.setId(1234);
        msg.setQos(0);
        msg.setRetained(false);

        byte[] b = value.getBytes(Charset.forName("UTF-8"));
        msg.setPayload(b);

        try {
            adafruitConnect.mqttAndroidClient.publish(topic, msg);
        }catch (MqttException e){
        }
    }
}