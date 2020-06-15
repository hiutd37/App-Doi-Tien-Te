package com.example.appdoitiente.API;

import com.example.appdoitiente.SingleTon;

public interface CurrencyListener {
    SingleTon SINGLE_TON = SingleTon.getInstance();
    void setTyGia(Double tyGia);
}
