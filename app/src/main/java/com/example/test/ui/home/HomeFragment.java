package com.example.test.ui.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.test.databinding.FragmentHomeBinding;
import com.example.test.model.Data;
import com.example.test.utils;
import com.google.android.material.slider.Slider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    TextView valueHumidity, valueTemperature, valueMaxTemp, valueMinTemp, valueFeelLike,
            valueWindSpeed, valueWindDegrees, valueWindGust,
            valuePressure, valueSeaLevel, valueGroundLevel;
    Slider humiditySlider, temperatureSlider, PressureSlider, SeaLevelSlider, GroundLevelSlider;
    HomeViewModel homeViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel =
                new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mapping();

        homeViewModel.getStationName().observe(getViewLifecycleOwner(), data -> {
            if (data != null) {
                String staionName = data;
                Log.d("staionName", "n: " + staionName);
                homeViewModel.getData(staionName);
                homeViewModel.getTemperature().observe(getViewLifecycleOwner(), new Observer<Double>() {
                    @Override
                    public void onChanged(Double aDouble) {

                        if (aDouble < 0) {
                            temperatureSlider.setValue(0);
                        }
                        else {
                            valueTemperature.setText(String.valueOf(aDouble));
                            temperatureSlider.setValue(aDouble.floatValue());}
                    }
                });
                homeViewModel.getTempMax().observe(getViewLifecycleOwner(), new Observer<Double>() {
                    @Override
                    public void onChanged(Double aDouble) {
                        if (aDouble > 0)
                            valueMaxTemp.setText(String.valueOf(aDouble));
                    }
                });
                homeViewModel.getTempMin().observe(getViewLifecycleOwner(), new Observer<Double>() {
                    @Override
                    public void onChanged(Double aDouble) {
                        if (aDouble > 0)
                            valueMinTemp.setText(String.valueOf(aDouble));
                    }
                });
                homeViewModel.getFeelsLike().observe(getViewLifecycleOwner(), new Observer<Double>() {
                    @Override
                    public void onChanged(Double aDouble) {
                        if (aDouble > 0)
                            valueFeelLike.setText(String.valueOf(aDouble));
                    }
                });
                homeViewModel.getWindSpeed().observe(getViewLifecycleOwner(), new Observer<Double>() {
                    @Override
                    public void onChanged(Double aDouble) {
                        valueWindSpeed.setText(String.valueOf(aDouble));
                    }
                });
                homeViewModel.getWindDeg().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        valueWindDegrees.setText(String.valueOf(integer));
                    }
                });
                homeViewModel.getWindGust().observe(getViewLifecycleOwner(), new Observer<Double>() {
                    @Override
                    public void onChanged(Double aDouble) {
                        valueWindGust.setText(String.valueOf(aDouble));
                    }
                });
                homeViewModel.getHumidity().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        valueHumidity.setText(String.valueOf(integer));
                        if (integer < 0)
                            humiditySlider.setValue(0);
                        else humiditySlider.setValue(integer);
                    }
                });
                homeViewModel.getPressure().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        valuePressure.setText(String.valueOf(integer));
                        if (integer < 500)
                            PressureSlider.setValue(500);
                        else PressureSlider.setValue(integer);
                    }
                });
                homeViewModel.getSeaLevel().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        valueSeaLevel.setText(String.valueOf(integer));
                        if (integer < 500)
                            SeaLevelSlider.setValue(500);
                        else SeaLevelSlider.setValue(integer);
                    }
                });
                homeViewModel.getGrndLevel().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        valueGroundLevel.setText(String.valueOf(integer));
                        if(integer < 500)
                            GroundLevelSlider.setValue(500);
                        else GroundLevelSlider.setValue(integer);
                    }
                });
            } else Log.d("homefragment", "null");
        });

        return root;
    }
//    public void getData(String path){
//        DatabaseReference dataRef = database.getReference(path);
//        dataRef.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot childSnapshot : snapshot.getChildren()){
//                    Double temperature = childSnapshot.child("temperature").getValue(Double.class);
//                    valueTemperature.setText(String.valueOf(utils.doiNhietDo(temperature)));
//                    if (utils.doiNhietDo(temperature) < 0)
//                        temperatureSlider.setValue(0);
//                    else temperatureSlider.setValue((float) utils.doiNhietDo(temperature));
//                    Double feelsLike = childSnapshot.child("feelsLike").getValue(Double.class);
//                    valueFeelLike.setText(String.valueOf(utils.doiNhietDo(feelsLike)));
//                    Double tempMin = childSnapshot.child("tempMin").getValue(Double.class);
//                    valueMinTemp.setText(String.valueOf(utils.doiNhietDo(tempMin)));
//                    Double tempMax = childSnapshot.child("tempMax").getValue(Double.class);
//                    valueMaxTemp.setText(String.valueOf(utils.doiNhietDo(tempMax)));
//                    Double windSpeed = childSnapshot.child("windSpeed").getValue(Double.class);
//                    valueWindSpeed.setText(String.valueOf(Math.round(windSpeed*10/10)));
//                    int windDeg = childSnapshot.child("windDeg").getValue(Integer.class);
//                    valueWindDegrees.setText(String.valueOf(windDeg));
//                    Double windGust = childSnapshot.child("windGust").getValue(Double.class);
//                    valueWindGust.setText(String.valueOf(windGust));
//
//                    int pressure = childSnapshot.child("pressure").getValue(Integer.class);
//                    valuePressure.setText(String.valueOf(pressure));
//                    if (pressure < 500)
//                        PressureSlider.setValue(500);
//                    else PressureSlider.setValue(pressure);
//
//                    int humidity = childSnapshot.child("humidity").getValue(Integer.class);
//                    valueHumidity.setText(String.valueOf(humidity));
//                    if (humidity < 0)
//                        humiditySlider.setValue(0);
//                    else humiditySlider.setValue(humidity);
//
//                    int seaLevel = childSnapshot.child("seaLevel").getValue(Integer.class);
//                    valueSeaLevel.setText(String.valueOf(seaLevel));
//                    if (seaLevel < 500)
//                        SeaLevelSlider.setValue(500);
//                    else SeaLevelSlider.setValue(seaLevel);
//
//                    int grndLevel = childSnapshot.child("grndLevel").getValue(Integer.class);
//                    valueGroundLevel.setText(String.valueOf(grndLevel));
//                    if (grndLevel < 500)
//                        GroundLevelSlider.setValue(500);
//                    else GroundLevelSlider.setValue(grndLevel);
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                System.out.println("Database Error: " + error.getMessage());
//            }
//        });
//    }


    public void mapping(){
        //độ ẩm
        valueHumidity = binding.valueHumidity;
        //nhiệt độ
        valueTemperature = binding.valueTemperature;
        valueMaxTemp = binding.valueMaxTemp;
        valueMinTemp = binding.valueMinTemp;
        valueFeelLike = binding.valueFeelLike;
        //gió
        valueWindSpeed = binding.valueWindSpeed;
        valueWindDegrees = binding.valueWindDegrees;
        valueWindGust = binding.valueWindGust;
        //áp suất
        valuePressure = binding.valuePressure;
        //mực nước biển
        valueSeaLevel = binding.valueSeaLevel;
        //mực đất
        valueGroundLevel = binding.valueGroundLevel;

        //slider
        humiditySlider = binding.HumiditySlider;
        temperatureSlider = binding.TemperatureSlider;
        PressureSlider = binding.PressureSlider;
        SeaLevelSlider = binding.SeaLevelSlider;
        GroundLevelSlider = binding.GroundLevelSlider;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}