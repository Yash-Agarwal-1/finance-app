package com.example.financeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class StockInfoFragment extends Fragment {

    String totalStockInfo= "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_stock_info, container, false);
        Activity activity = getActivity();
        String ticker = getArguments().getString("ticker");
        String open = getArguments().getString("open");
        String close = getArguments().getString("close");
        String high = getArguments().getString("high");
        String low = getArguments().getString("low");
        String volume = getArguments().getString("volume");

        totalStockInfo += "Ticker: " + ticker.toUpperCase() + ", ";
        totalStockInfo += "Open: " + open + ", ";
        totalStockInfo += "Close: " + close + ", ";
        totalStockInfo += "High: " + high + ", ";
        totalStockInfo += "Low: " + low + ", ";
        totalStockInfo += "Volume: " + volume;

        TextView stock_name = view.findViewById(R.id.name);
        stock_name.setText(ticker);

        TextView open_price = view.findViewById(R.id.open);
        open_price.setText(open);

        TextView high_price = view.findViewById(R.id.high);
        high_price.setText(high);

        TextView low_price = view.findViewById(R.id.low);
        low_price.setText(low);

        TextView close_price = view.findViewById(R.id.close);
        close_price.setText(close);

        TextView volume_of_stock = view.findViewById(R.id.volume);
        volume_of_stock.setText(volume);

        Button button = view.findViewById(R.id.share_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               handleShare();
            }
        });
        return view;
    }

    public void handleShare(){
        ContactListFragment contactListFragment = new ContactListFragment();
        contactListFragment.setContainerActivity(getActivity());
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_view, contactListFragment)
                .addToBackStack(null)
                .commit();
    }

}