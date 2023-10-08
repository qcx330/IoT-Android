package com.example.test;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.test.databinding.ActivityStationBinding;
import com.example.test.ui.home.HomeViewModel;
import com.example.test.ui.linechart.LineChartFragment;
import com.example.test.ui.linechart.LineChartViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class StationActivity extends AppCompatActivity {

    private ActivityStationBinding binding;
    private HomeViewModel homeViewModel;
    private LineChartViewModel lineChartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        lineChartViewModel = new ViewModelProvider(this).get(LineChartViewModel.class);

        Intent intent = this.getIntent();
        String stationName = intent.getStringExtra("stationName");
        Log.d("TEST-INTENT", stationName);

        homeViewModel.setStationNameAsync(stationName);
        lineChartViewModel.setStationNameAsync(stationName);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_linechart)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


    }

}