package com.example.coronameter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class CountryInfo extends AppCompatActivity {
    int newConfirmed, newDeaths, newRecovered, totalConfirmed, totalDeaths, totalRecovered;
    String countryName;
    TextView  newConfirmedView, newDeathsView, newRecoveredView, totalConfirmedView, totalDeathsView, totalRecoveredView, countryNameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_info);

        newConfirmedView = findViewById(R.id.newConfirmed);
        newDeathsView = findViewById(R.id.newDeaths);
        newRecoveredView = findViewById(R.id.newRecovered);
        totalConfirmedView = findViewById(R.id.totalConfirmed);
        totalDeathsView = findViewById(R.id.totalDeaths);
        totalRecoveredView = findViewById(R.id.totalRecovered);
        countryNameView = findViewById(R.id.countryName);

        newConfirmed = getIntent().getIntExtra("newConfirmed", 0);
        newDeaths = getIntent().getIntExtra("newDeaths", 0);
        newRecovered = getIntent().getIntExtra("newRecovered", 0);
        totalConfirmed = getIntent().getIntExtra("totalConfirmed", 0);
        totalDeaths = getIntent().getIntExtra("totalDeaths", 0);
        totalRecovered = getIntent().getIntExtra("totalRecovered", 0);
        countryName = getIntent().getStringExtra("countryName" );

        newConfirmedView.setText("New Confirmed: "+ NumberFormat.getNumberInstance(Locale.US).format(newConfirmed));
        newDeathsView.setText("New Deaths: "+ NumberFormat.getNumberInstance(Locale.US).format(newDeaths));
        newRecoveredView.setText("New Recovered: "+  NumberFormat.getNumberInstance(Locale.US).format(newRecovered));
        totalConfirmedView.setText("Total Confirmed: "+  NumberFormat.getNumberInstance(Locale.US).format(totalConfirmed));
        totalDeathsView.setText("Total Deaths: "+  NumberFormat.getNumberInstance(Locale.US).format(totalDeaths));
        totalRecoveredView.setText("Total Recovered: "+  NumberFormat.getNumberInstance(Locale.US).format(totalRecovered));
        countryNameView.setText(countryName);

    }

}