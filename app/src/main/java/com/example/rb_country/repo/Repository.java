package com.example.rb_country.repo;

import com.example.rb_country.model.CountryModel;

import java.util.List;

import io.reactivex.Observable;

public class Repository {
    private static Repository INSTANCE = null;

    private Repository() {}

    public Observable<List<CountryModel>> getCountries(String name) {
        Observable<List<CountryModel>> response = RetrofitInstance.getInstance().getCountries(name);
        return response;
    }

    public static Repository getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Repository();
        }
        return INSTANCE;
    }
}
