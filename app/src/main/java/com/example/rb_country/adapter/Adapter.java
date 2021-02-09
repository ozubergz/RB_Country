package com.example.rb_country.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rb_country.databinding.CountryLayoutBinding;
import com.example.rb_country.model.CountryModel;
import com.example.rb_country.view.MainActivity;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<CountryModel> countries;
    private CountryClickListener listener;

    public Adapter(List<CountryModel> countries, CountryClickListener listener) {
        this.countries = countries;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CountryLayoutBinding binding = CountryLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CountryModel country = countries.get(position);
        holder.set(country);
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CountryLayoutBinding binding;
        private CountryClickListener listener;

        public ViewHolder(CountryLayoutBinding binding, CountryClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
        }

        public void set(CountryModel country) {
            Glide.with(this.itemView).load(country.getFlag()).into(binding.ivFlag);
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null)
                        listener.itemClick(getPosition());
                }
            });
        }

    }
}
