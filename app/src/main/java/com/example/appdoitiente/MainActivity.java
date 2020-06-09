package com.example.appdoitiente;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner spinner1;
    Spinner spinner2;
    ArrayAdapter<CharSequence> adapterSpinner;
    ArrayAdapter adapterList;
    ListView listView;
    Button buttonConvert;
    EditText editTextCurrency;
    ArrayList list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addEvents() {
        list= new ArrayList();
        list.add("VND");
        list.add("US Dollar");
        list.add("YEN");
        list.add("WON");

        adapterSpinner = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,list);
        adapterList= new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        listView.setAdapter(adapterList);
        spinner1.setAdapter(adapterSpinner);
        spinner2.setAdapter(adapterSpinner);

        buttonConvert.setOnClickListener(this);

    }

    private void addControls() {
        spinner1 = (Spinner) findViewById(R.id.simpleSpinner1);
        spinner2 = (Spinner) findViewById(R.id.simpleSpinner2);
        listView=findViewById(R.id.listView);
        buttonConvert=findViewById(R.id.buttonConvert);
        editTextCurrency= findViewById(R.id.editTextCurrency);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonConvert:
                Toast.makeText(this,"ádadasda",Toast.LENGTH_LONG).show();
        }
    }
}