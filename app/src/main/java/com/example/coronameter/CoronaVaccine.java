package com.example.coronameter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CoronaVaccine extends AppCompatActivity {
    String vaccineUrl = "https://www.cowin.gov.in/home";
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corona_vaccine);

        webView = findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(vaccineUrl);
        webView.setWebViewClient(new WebViewClient());

    }
}