package com.example.appdoitiente.API;

import com.example.appdoitiente.Entity.SingleTon;

public interface CurrencyListener {
    SingleTon SINGLE_TON = SingleTon.getInstance();
    void setTyGia(Double tyGia);
}
