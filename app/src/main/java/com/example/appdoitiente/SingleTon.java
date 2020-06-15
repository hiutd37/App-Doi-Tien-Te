package com.example.appdoitiente;

import java.util.ArrayList;

public class SingleTon {
    private static SingleTon instance=null;

    public ArrayList<Currency> currencies;

    private SingleTon() {
        currencies = new ArrayList<>();
    }
    public static SingleTon getInstance(){
        if(instance==null){
            instance = new SingleTon();
        }
        return instance;
    }
}
