package com.example.rb_country.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rb_country.model.CountryModel;
import com.example.rb_country.repo.Repository;
import com.example.rb_country.util.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private final SharedPreferences sharedPref = getApplication().getSharedPreferences(
            "", Context.MODE_PRIVATE);

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    private MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();

    public LiveData<Boolean> getIsLoading() {
        return _isLoading;
    }

    SharedPreferences.Editor editor = sharedPref.edit();
    Gson gson = new Gson();


    // Moshi moshi = new Moshi.Builder().build();
    // Type listCountries = Types.newParameterizedType(List.class, CountryModel.class);
    // JsonAdapter<List<CountryModel>> adapter = moshi.adapter(listCountries);

    public void saveCountriesData(List<CountryModel> countries) {
        String json = gson.toJson(countries);
        editor.putString(Constants.SHARED_PREF_COUNTRIES_KEY, json);
        editor.apply();
    }

    public List<CountryModel> getCountriesData() {
        String json = sharedPref.getString(Constants.SHARED_PREF_COUNTRIES_KEY, "");
        Type type = new TypeToken<List<CountryModel>>(){}.getType();
        List<CountryModel> countries = gson.fromJson(json, type);
        return countries;
    }

    public void fetchAPI(String name) {
        Repository repo = Repository.getInstance();
        repo.getCountries(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<CountryModel>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        _isLoading.setValue(true);
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull List<CountryModel> countryModels) {
                        saveCountriesData(countryModels);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        _isLoading.setValue(false);
                    }
                });
    }


}
