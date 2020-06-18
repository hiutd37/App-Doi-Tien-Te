package com.example.appdoitiente.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdoitiente.API.ConvertCurrencyAPI;
import com.example.appdoitiente.API.CurrencyListener;
import com.example.appdoitiente.Entity.Currency;
import com.example.appdoitiente.R;
import com.example.appdoitiente.Entity.SingleTon;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements CurrencyListener {
    Spinner spinner1;
    Spinner spinner2;
    ArrayAdapter adapter;
    ArrayList list;
    Button buttonConvert;
    EditText editTextCurrencyInput;
    EditText editTextCurrencyOutput;
    TextView textViewConvert;

    SingleTon arryList = SingleTon.getInstance();
    Currency from = arryList.currencies.get(0);;
    Currency to = arryList.currencies.get(0);
    Double tyGia = 1.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents() {
        list = arryList.currencies;
        adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.array, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                from = arryList.currencies.get(position);
                new ConvertCurrencyAPI(MainActivity.this).execute(url(from,to));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                to = arryList.currencies.get(position);
                new ConvertCurrencyAPI(MainActivity.this).execute(url(from,to));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input=editTextCurrencyInput.getText().toString();
                if(!Pattern.matches("^\\d+$",input)){
                    Toast.makeText(MainActivity.this,"Bạn cần nhập mệnh giá cần chuyển đổi!",Toast.LENGTH_LONG).show();
                    editTextCurrencyInput.requestFocus();
                }
                else {
                    String url=url(from,to);
                    if(url==null){
                        Toast.makeText(MainActivity.this,"2 mệnh giá cần chuyển đổi phải khác nhau!",Toast.LENGTH_LONG).show();
                    }else {
                        setTextOutPut(input);
                    }
                }
            }
        });
    }

    private void setTextOutPut(String input) {
        editTextCurrencyOutput.setText(formatCurrency(Double.parseDouble(input)*this.tyGia));
    }

    private String url(Currency from, Currency to) {
        if(from==to) {
            setTyGia(1.0);
            return null;
        };
        return "https://"+from.getCode().toLowerCase()+".fxexchangerate.com/"+to.getCode().toLowerCase()+".xml";

    }

    private String textViewConvert(Currency from,Currency to,Double tyGia){
        return "1.0 "+from.getCode()+" ~ "+tyGia+" "+to.getCode();
    }

    private void addControls() {
        spinner1 = (Spinner) findViewById(R.id.simpleSpinner1);
        spinner2 = (Spinner) findViewById(R.id.simpleSpinner2);
        buttonConvert = findViewById(R.id.buttonConvert);
        editTextCurrencyInput = findViewById(R.id.editTextCurrencyInput);
        editTextCurrencyOutput = findViewById(R.id.editTextCurrencyOutput);
        textViewConvert = findViewById(R.id.textViewConvert);
    }


    @Override
    public void setTyGia(Double tyGia) {
        textViewConvert.setText(textViewConvert(from,to,tyGia));
        this.tyGia=tyGia;
    }
    private String formatCurrency(Double s){
        return NumberFormat.getInstance().format(s);
    }
}
