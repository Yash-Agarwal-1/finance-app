package com.example.financeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddTransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        TextInputEditText labelInput = findViewById(R.id.labelInput);
        TextInputEditText amountInput = findViewById(R.id.amountInput);
        TextInputLayout labelLayout = findViewById(R.id.labelLayout);
        TextInputLayout amountLayout = findViewById(R.id.amountLayout);
        labelInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() > 0){
                    labelLayout.setError(null);
                }
            }
        });
        amountInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() > 0){
                    amountLayout.setError(null);
                }
            }
        });


        Button button = findViewById(R.id.addTransactionBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText labelInput = findViewById(R.id.labelInput);
                String label = labelInput.getText().toString();
                TextInputEditText amountInput = findViewById(R.id.amountInput);
                String amount =amountInput.getText().toString();
                TextInputEditText descriptionInput = findViewById(R.id.descriptionInput);
                String description = descriptionInput.getText().toString();
                if(label.isEmpty()){
                    TextInputLayout labelLayout = findViewById(R.id.labelLayout);
                    labelLayout.setError("Please enter a valid label");
                }
                else if(amount.isEmpty()){
                    TextInputLayout amountLayout = findViewById(R.id.amountLayout);
                    amountLayout.setError("Please enter a valid amount");
                }else{
                    insert(new Transaction(0,label,Double.parseDouble(amount),description));
                }
            }
        });

        ImageButton closeBtn = findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void insert(Transaction transaction){
        Intent intent = getIntent();
        String str = intent.getStringExtra("bank");
        System.out.println("Add transaction: " + str+"_database");
        AppDatabase db = AppDatabase.getInstance(this, str+"_database");
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                db.transactionDao().insertAll(transaction);
                finish();
            }
        });

    }
}