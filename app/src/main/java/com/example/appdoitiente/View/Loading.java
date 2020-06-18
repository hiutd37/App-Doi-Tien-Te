package com.example.appdoitiente.View;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdoitiente.API.CurrencyAPI;
import com.example.appdoitiente.R;

public class Loading extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        runCurrencyConvert();
    }

    private void runCurrencyConvert() {
        new CurrencyAPI(this).execute("https://aud.fxexchangerate.com/rss.xml");
    }

}