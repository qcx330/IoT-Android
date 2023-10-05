package com.example.test;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MqttHelper {
    public MqttAndroidClient mqttAndroidClient;

    public final String topic = "nhom1/stations";

    Random random = new Random();
    final String clientId = "publish-" + random.nextInt(1000) + 1;
    final String userName = "nhom1";
    final String password = "nhom1IoT";
    final String serverUri = "tcp://broker.ou-cs.tech:1883";
    MemoryPersistence persistence = new MemoryPersistence();
    public MqttHelper(Context context) {
        mqttAndroidClient = new MqttAndroidClient(context, serverUri, clientId, persistence);

    }

//    public void setCallback(MqttCallbackExtended callback) {
//        mqttAndroidClient.setCallback(callback);
//    }

    public void connect() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setUserName(userName);
        mqttConnectOptions.setPassword(password.toCharArray());

        try {
            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d("Mqtt", "Connected");
                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    subscribeToTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Failed to connect to: " + serverUri + exception.toString());
                }
            });
            mqttAndroidClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {

                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String mess = reconstructedData(message.toString());
                    String[] stationData = mess.split(" , ");
                    Log.d("MESSAGE: ", mess);
                    for (String stationInfo : stationData) {
                        String[] parts = stationInfo.split(" - ");
                        String stationName = parts[0].split(": ")[0];
                        String weatherData = parts[0].substring(parts[0].indexOf(": ") + 1);
                        String windData = parts[1];

                        System.out.println("Station Name: " + stationName);
                        try {
                            if (stationName.equals("station2")){
                                JSONObject windJson = new JSONObject(windData);

                                double speed = windJson.getDouble("speed");
                                int deg = windJson.getInt("deg");
                                double gust = windJson.optDouble("gust", -1);

                                System.out.println("Wind Data:");
                                System.out.println("Wind Speed: " + speed);
                                System.out.println("Wind Degree: " + deg);
                                if (gust != -1) {
                                    System.out.println("Wind Gust: " + gust);
                                }
                            }
                            else {
                                JSONObject weatherJson = new JSONObject(weatherData);
                                double temp = weatherJson.getDouble("temp");
                                double feelsLike = weatherJson.getDouble("feels_like");
                                double tempMin = weatherJson.getDouble("temp_min");
                                double tempMax = weatherJson.getDouble("temp_max");
                                int pressure = weatherJson.getInt("pressure");
                                int humidity = weatherJson.getInt("humidity");
                                int seaLevel = weatherJson.optInt("sea_level", -1);
                                int grndLevel = weatherJson.optInt("grnd_level", -1);

                                System.out.println("Weather Data:");
                                System.out.println("Temperature: " + temp);
                                System.out.println("Feels Like: " + feelsLike);
                                System.out.println("Temp Min: " + tempMin);
                                System.out.println("Temp Max: " + tempMax);
                                System.out.println("Pressure: " + pressure);
                                System.out.println("Humidity: " + humidity);
                                if (seaLevel != -1) {
                                    System.out.println("Sea Level: " + seaLevel);
                                }
                                if (grndLevel != -1) {
                                    System.out.println("Ground Level: " + grndLevel);

                                    System.out.println("Wind Info: " + windData);
                                }
                                JSONObject windJson = new JSONObject(windData);
                                double speed = windJson.getDouble("speed");
                                int deg = windJson.getInt("deg");
                                double gust = windJson.optDouble("gust", -1);

                                System.out.println("Wind Data:");
                                System.out.println("Wind Speed: " + speed);
                                System.out.println("Wind Degree: " + deg);
                                if (gust != -1) {
                                    System.out.println("Wind Gust: " + gust);
                                }
                            }

                            System.out.println();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }
            });
//
        } catch (MqttException ex) {
            ex.printStackTrace();
        }
    }
    public String reconstructedData (String str){
        String replacedData = str.replaceAll(", (?=station\\d+:)", " , ");

        return replacedData;
    }
    private void subscribeToTopic() {
        try {
            mqttAndroidClient.subscribe(topic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d("Test", "Subscribed!");

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.d("Test", "Subscribed Fail!");
                }
            });
        } catch (MqttException ex) {
            System.err.println("Exceptionst subscribing");
            ex.printStackTrace();
        }
    }
    private void publishToTopic(String str) throws MqttException {
        MqttMessage mess = new MqttMessage(str.getBytes());
        mess.setQos(0);
        mqttAndroidClient.publish(topic, mess, null, new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                Log.d("Test", "Published!");
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                Log.d("Test", "Published Fail!");
            }
        });
    }

}