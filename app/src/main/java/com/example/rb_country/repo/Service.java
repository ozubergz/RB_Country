package com.example.rb_country.repo;

import com.example.rb_country.model.CountryModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Service {

    // https://restcountries.eu/rest/v2/name/{name}

    @GET("name/{name}")
    Observable<List<CountryModel>> getCountries(@Path(value = "name") String name);

}
