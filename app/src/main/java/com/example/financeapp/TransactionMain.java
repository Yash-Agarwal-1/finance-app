package com.example.financeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TransactionMain extends AppCompatActivity {

    private ArrayList<Transaction> transactions;
    private TransactionAdapter transactionAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_main);
        transactions = new ArrayList<Transaction>();
        transactions.add(new Transaction("Weekend budget", 400.00));
        transactions.add(new Transaction("Bananas", -4.00));
        transactions.add(new Transaction("Weekend budget", -56.00));
        transactions.add(new Transaction("Weekend budget", -2.80));
        transactions.add(new Transaction("Weekend budget", 30.00));
        transactions.add(new Transaction("Weekend budget", 1.01));

        transactionAdapter = new TransactionAdapter(transactions);
        layoutManager = new LinearLayoutManager(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        Context context = this;

        recyclerView.setAdapter(transactionAdapter);
        recyclerView.setLayoutManager(layoutManager);
        updateDashboard();
        ImageButton button = findViewById(R.id.addBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddTransactionActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateDashboard(){
        double totalAmount = 0;
        for(Transaction trans: transactions){
            totalAmount += trans.getAmount();
        }
        double budgetAmount = 0;
        for(Transaction trans: transactions){
            if(trans.getAmount()>0)
                budgetAmount += trans.getAmount();
        }

        double expenseAmount = totalAmount-budgetAmount;

        TextView balance = findViewById(R.id.balance);
        balance.setText(String.format("$ %.2f", totalAmount));
        TextView budget = findViewById(R.id.budget);
        budget.setText(String.format("$ %.2f", budgetAmount));
        TextView expense = findViewById(R.id.expense);
        expense.setText(String.format("$ %.2f", expenseAmount));

    }
}