package com.example.rb_country.view;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.rb_country.R;
import com.example.rb_country.adapter.Adapter;
import com.example.rb_country.databinding.ActivityMainBinding;
import com.example.rb_country.model.CountryModel;
import com.example.rb_country.viewmodel.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private ActivityMainBinding binding;

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
                    List<CountryModel> countries = viewModel.getCountriesData();
                    Adapter adapter = new Adapter(countries);
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
    }
}