package com.example.test.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.test.databinding.FragmentHomeBinding;
import com.google.android.material.slider.Slider;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    TextView valueHumidity, valueTemperature, valueMaxTemp, valueMinTemp, valueFeelLike,
            valueWindSpeed, valueWindDegrees, valueWindGust,
            valuePressure, valueSeaLevel, valueGroundLevel;
    Slider humiditySlider, temperatureSlider, PressureSlider, SeaLevelSlider, GroundLevelSlider;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mapping();

        return root;
    }

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