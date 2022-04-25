package com.example.financeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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

                if(label.isEmpty()){
                    TextInputLayout labelLayout = findViewById(R.id.labelLayout);
                    labelLayout.setError("Please enter a valid label");
                }
                if(amount.isEmpty()){
                    TextInputLayout amountLayout = findViewById(R.id.amountLayout);
                    amountLayout.setError("Please enter a valid amount");
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
}