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

    TextView valueHumidity, valueMoisture, valueTemperature;
    TextView valueHumiditySlider, valueMoistureSlider, valueTemperatureSlider, valueSoilTemperatureSlider;
    Slider humiditySlider, moistureSlider, temperatureSlider, soilTemperatureSlider;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        valueHumidity = binding.valueHumidity;
        valueMoisture = binding.valueMoisture;
        valueTemperature = binding.valueTemperature;

        valueHumiditySlider = binding.valueHumiditySlider;
        valueMoistureSlider = binding.valueMoistureSlider;
        valueTemperatureSlider = binding.valueTemperatureSlider;
        valueSoilTemperatureSlider = binding.valueSoilTemperatureSlider;

        humiditySlider = binding.HumiditySlider;
        moistureSlider = binding.MoistureSlider;
        temperatureSlider = binding.TemperatureSlider;
        soilTemperatureSlider = binding.SoilTemperatureSlider;

        homeViewModel.startMQTT(getContext(), valueHumidity, valueHumiditySlider, humiditySlider);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}