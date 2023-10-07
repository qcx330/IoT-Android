package com.example.test;

import android.content.Intent;
import android.os.Bundle;

import com.example.test.model.Data;
import com.example.test.ui.home.HomeFragment;
import com.example.test.ui.home.HomeViewModel;
import com.example.test.ui.linechart.LineChartFragment;
import com.example.test.ui.linechart.LineChartViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.test.databinding.ActivityStationBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StationActivity extends AppCompatActivity {

    private ActivityStationBinding binding;
    private HomeViewModel homeViewModel = new HomeViewModel();
    private LineChartViewModel lineChartViewModel = new LineChartViewModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = this.getIntent();
        String stationName = intent.getStringExtra("stationName");
        Log.d("TEST-INTENT", stationName);

        lineChartViewModel = new ViewModelProvider(this).get(LineChartViewModel.class);
        lineChartViewModel.setStationName(stationName);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_linechart)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        HomeFragment fragment = HomeFragment.newInstance(stationName);
        LineChartFragment fragment1 = LineChartFragment.newInstance(stationName);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, fragment1)
                .addToBackStack(null)
                .commit();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, fragment)
                .addToBackStack(null)
                .commit();
    }
}