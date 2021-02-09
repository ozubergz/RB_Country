package com.example.rb_country.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.rb_country.databinding.ActivityDetailBinding;
import com.example.rb_country.util.Constants;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String flag = getIntent().getStringExtra(Constants.COUNTRY_FLAG);
        String name = getIntent().getStringExtra(Constants.COUNTRY_NAME);

        Glide.with(getApplicationContext()).load(flag).into(binding.ivFlag);
        binding.tvName.setText(name);

    }
}
