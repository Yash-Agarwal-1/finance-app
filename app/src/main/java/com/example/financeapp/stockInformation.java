package com.example.financeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class stockInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_information);

        Intent intent = getIntent();
        String ticker = intent.getStringExtra("stock_name");
        String open = intent.getStringExtra("open");
        String high = intent.getStringExtra("high");
        String low = intent.getStringExtra("low");
        String close = intent.getStringExtra("close");
        String volume = intent.getStringExtra("volume");

        TextView stock_name = findViewById(R.id.name);
        stock_name.setText(ticker);

        TextView open_price = findViewById(R.id.open);
        open_price.setText(open);

        TextView high_price = findViewById(R.id.high);
        high_price.setText(high);

        TextView low_price = findViewById(R.id.low);
        low_price.setText(low);

        TextView close_price = findViewById(R.id.close);
        close_price.setText(close);

        TextView volume_of_stock = findViewById(R.id.volume);
        volume_of_stock.setText(volume);







    }
}