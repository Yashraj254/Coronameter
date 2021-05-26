package com.example.coronameter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MyAdapter.onClickListener, AdapterView.OnItemSelectedListener {
    RequestQueue requestQueue;
    TextView totalConfirmed, newConfirmed, totalDeaths, newDeaths, totalRecovered, newRecovered, lastUpdated, vaccine;
    private static final String TAG = "MainActivity";
    JsonObjectRequest jsonObjectRequest;
    RecyclerView recyclerView;
    MyAdapter adapter;
    Spinner countryNames;
    Corona corona;
    List<Corona> coronaList;
    List<String> countryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newConfirmed = findViewById(R.id.totalDeaths2);
        newDeaths = findViewById(R.id.newDeaths2);
        newRecovered = findViewById(R.id.totalRecovered2);
        totalConfirmed = findViewById(R.id.totalConfirmed2);
        totalDeaths = findViewById(R.id.newConfirmed2);
        totalRecovered = findViewById(R.id.newRecovered2);
        lastUpdated = findViewById(R.id.lastUpdated);
        recyclerView = findViewById(R.id.recyclerView);
        vaccine = findViewById(R.id.vaccine);
        countryNames = findViewById(R.id.spinner);
        coronaList = new ArrayList<>();
        countryList = new ArrayList<>();


        setAdapter();
        getData();
        countryList.add(0, "Choose country");
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, countryList);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        countryNames.setAdapter(adapter2);
        countryNames.setOnItemSelectedListener(this);

        vaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CoronaVaccine.class));
            }
        });
    }

    private void getData() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://api.covid19api.com/summary", null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    newConfirmed.setText("New\nConfirmed:\n" + NumberFormat.getNumberInstance(Locale.US).format(response.getJSONObject("Global").getInt("NewConfirmed")));
                    newDeaths.setText("New\nDeaths:\n" + NumberFormat.getNumberInstance(Locale.US).format(response.getJSONObject("Global").getInt("NewDeaths")));
                    newRecovered.setText("New\nRecovered:\n" + NumberFormat.getNumberInstance(Locale.US).format(response.getJSONObject("Global").getInt("NewRecovered")));
                    totalConfirmed.setText("Total\nConfirmed:\n" + NumberFormat.getNumberInstance(Locale.US).format(response.getJSONObject("Global").getInt("TotalConfirmed")));
                    totalDeaths.setText("Total\nDeaths:\n" + NumberFormat.getNumberInstance(Locale.US).format(response.getJSONObject("Global").getInt("TotalDeaths")));
                    totalRecovered.setText("Total\nRecovered:\n" + NumberFormat.getNumberInstance(Locale.US).format(response.getJSONObject("Global").getInt("TotalRecovered")));
                    lastUpdated.setText("Last Updated:" + response.getJSONObject("Global").getString("Date"));

                    JSONArray arr = response.getJSONArray("Countries");
                    for (int i = 0; i < arr.length(); i++) {

                        JSONObject fileObj = arr.getJSONObject(i);
                        String country;
                        int newConfirm, newDeath, newRecover, totalConfirm, totalDeath, totalRecover;
                        country = fileObj.getString("Country");
                        newConfirm = fileObj.getInt("NewConfirmed");
                        newDeath = fileObj.getInt("NewDeaths");
                        newRecover = fileObj.getInt("NewRecovered");
                        totalConfirm = fileObj.getInt("TotalConfirmed");
                        totalDeath = fileObj.getInt("TotalDeaths");
                        totalRecover = fileObj.getInt("TotalRecovered");
                        corona = new Corona(country, totalConfirm, newConfirm, totalDeath, newDeath, totalRecover, newRecover);
                        coronaList.add(corona);
                        countryList.add(country);
                    }
                    Comparator c = Collections.reverseOrder();
                    Collections.sort(coronaList, c);
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: Some error");
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void setAdapter() {
        adapter = new MyAdapter(getApplicationContext(), coronaList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(int position) {
        String countryName;//= parent.getItemAtPosition(position).toString();
        int totalConfirmed, newConfirmed, totalDeaths, newDeaths, totalRecovered, newRecovered;
        countryName = coronaList.get(position).getCountry();
        newConfirmed = coronaList.get(position).getNewConfirmed();
        newDeaths = coronaList.get(position).getNewDeaths();
        newRecovered = coronaList.get(position).getNewRecovered();
        totalConfirmed = coronaList.get(position).getTotalConfirmed();
        totalDeaths = coronaList.get(position).getTotalDeaths();
        totalRecovered = coronaList.get(position).getTotalRecovered();
        Intent intent = new Intent(getApplicationContext(), CountryInfo.class);
        intent.putExtra("countryName", countryName);
        intent.putExtra("newConfirmed", newConfirmed);
        intent.putExtra("newDeaths", newDeaths);
        intent.putExtra("newRecovered", newRecovered);
        intent.putExtra("totalConfirmed", totalConfirmed);
        intent.putExtra("totalDeaths", totalDeaths);
        intent.putExtra("totalRecovered", totalRecovered);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int pos = 1;

        if(parent.getItemAtPosition(position).toString().equals("Choose country"))
        {
            Toast.makeText(parent.getContext(), "Choose a country name " ,Toast.LENGTH_SHORT).show();
        }

        for (int i = 0; i < coronaList.size(); i++) {
            String userListName = coronaList.get(i).getCountry();
            if(userListName.equals(parent.getItemAtPosition(position).toString())){
            pos = i;
            }else{
                //Nthng to do
            }
        }

        String countryName;//= parent.getItemAtPosition(position).toString();
        int totalConfirmed, newConfirmed, totalDeaths, newDeaths, totalRecovered, newRecovered;
        //  Log.d(TAG, "onItemSelected: some item clicked at position");
        //  Toast.makeText(parent.getContext(), "Selected: " +position + tutorialsName,Toast.LENGTH_SHORT).show();
        if(!parent.getItemAtPosition(position).toString().equals("Choose country")) {
            if (coronaList.size() != 0) {
                //  Log.d(TAG, "onItemSelected: "+pos);
                countryName = coronaList.get(pos).getCountry();
                newConfirmed = coronaList.get(pos).getNewConfirmed();
                newDeaths = coronaList.get(pos).getNewDeaths();
                newRecovered = coronaList.get(pos).getNewRecovered();
                totalConfirmed = coronaList.get(pos).getTotalConfirmed();
                totalDeaths = coronaList.get(pos).getTotalDeaths();
                totalRecovered = coronaList.get(pos).getTotalRecovered();
                Intent intent = new Intent(getApplicationContext(), CountryInfo.class);
                intent.putExtra("countryName", countryName);
                intent.putExtra("newConfirmed", newConfirmed);
                intent.putExtra("newDeaths", newDeaths);
                intent.putExtra("newRecovered", newRecovered);
                intent.putExtra("totalConfirmed", totalConfirmed);
                intent.putExtra("totalDeaths", totalDeaths);
                intent.putExtra("totalRecovered", totalRecovered);
                startActivity(intent);
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}