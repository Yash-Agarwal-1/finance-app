package com.example.financeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TransactionMain extends AppCompatActivity {

    private List<Transaction> transactions;
    private TransactionAdapter transactionAdapter;
    private LinearLayoutManager layoutManager;
    private AppDatabase db;
    private ExecutorService executorService;
    private Handler handler;
    private double acc_balance;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_transaction_main);
        transactions = new ArrayList<Transaction>();

        transactionAdapter = new TransactionAdapter(transactions);
        layoutManager = new LinearLayoutManager(this);
        Intent intent = getIntent();
        String str = intent.getStringExtra("bank");
        db = AppDatabase.getInstance(this, str + "_database");
        executorService = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(transactionAdapter);
        recyclerView.setLayoutManager(layoutManager);
        ImageButton button = findViewById(R.id.addBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddTransactionActivity.class);
                intent.putExtra("bank", str);
                startActivity(intent);
            }
        });
    }

    private void fetchAll(){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                transactions = db.transactionDao().getAll();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateDashboard();
                        transactionAdapter.setData(transactions);
                    }
                });
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

        acc_balance = totalAmount;
        TextView balance = findViewById(R.id.balance);
        balance.setText(String.format("$ %.2f", totalAmount));
        TextView budget = findViewById(R.id.budget);
        budget.setText(String.format("$ %.2f", budgetAmount));
        TextView expense = findViewById(R.id.expense);
        expense.setText(String.format("$ %.2f", expenseAmount));

    }

    public double getBalance(){
        return acc_balance;
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchAll();
    }
}