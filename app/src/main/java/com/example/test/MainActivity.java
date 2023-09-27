package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.test.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    Button btnS1, btnS2,btnS3,btnS4,btnS5;
    private ActivityMainBinding binding;

    MqttHelper mqttHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mqttHelper = new MqttHelper(getApplicationContext());
        mqttHelper.connect();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        btnS1 = binding.btnS1;
        btnS2 = binding.btnS2;
        btnS3 = binding.btnS3;
        btnS4 = binding.btnS4;
        btnS5 = binding.btnS5;

        btnS1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), StationActivity.class);
                startActivity(i);
            }
        });
    }

}