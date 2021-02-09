package com.example.rb_country.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.rb_country.R;
import com.example.rb_country.adapter.Adapter;
import com.example.rb_country.adapter.CountryClickListener;
import com.example.rb_country.databinding.ActivityMainBinding;
import com.example.rb_country.model.CountryModel;
import com.example.rb_country.util.Constants;
import com.example.rb_country.viewmodel.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CountryClickListener {

    private MainViewModel viewModel;
    private ActivityMainBinding binding;
    private List<CountryModel> countries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider.AndroidViewModelFactory(
                getApplication()).create(MainViewModel.class);

        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean) {
                    countries = viewModel.getCountriesData();
                    Adapter adapter = new Adapter(countries, MainActivity.this);
                    binding.rvList.setAdapter(adapter);
                }
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        binding.rvList.setLayoutManager(gridLayoutManager);


        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.etSearch.getText().toString();
                viewModel.fetchAPI(name);
            }
        });

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 2) {
                    viewModel.fetchAPI(s.toString());
                }
            }
        });
    }

    @Override
    public void itemClick(int position) {
        CountryModel country = countries.get(position);

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Constants.COUNTRY_FLAG, country.getFlag());
        intent.putExtra(Constants.COUNTRY_NAME, country.getName());
        startActivity(intent);

    }
}