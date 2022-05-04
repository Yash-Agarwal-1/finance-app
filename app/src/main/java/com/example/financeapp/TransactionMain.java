package com.example.financeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TransactionMain extends AppCompatActivity {

    private List<Transaction> transactions;
    private List<Transaction> oldTransactions;
    private TransactionAdapter transactionAdapter;
    private LinearLayoutManager layoutManager;
    private AppDatabase db;
    private ExecutorService executorService;
    private Handler handler;
    private String bankName;
    private Transaction deletedTransaction;
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
        bankName = intent.getStringExtra("bank");
        db = AppDatabase.getInstance(this, bankName + "_database");
        executorService = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(transactionAdapter);
        recyclerView.setLayoutManager(layoutManager);
        ImageButton button = findViewById(R.id.addBtn);

        new ItemTouchHelper(new ItemTouchHelper.
                SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.
                    ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();;
                deleteTransaction(transactions.get(viewHolder.getAdapterPosition()), viewHolder.getAdapterPosition());
                transactionAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                Snackbar.make(recyclerView, "Transaction deleted!", Snackbar.LENGTH_LONG).
                        setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        transactions.add(position, deletedTransaction);
                        transactionAdapter.notifyItemInserted(position);
                        updateDashboard();
                    }
                }).show();
            }
        }).attachToRecyclerView(recyclerView);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddTransactionActivity.class);
                intent.putExtra("bank", bankName);
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
        SharedPreferences sharedPreferences = getSharedPreferences("bank_balances", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        TextView balance = findViewById(R.id.balance);
        balance.setText(String.format("$ %.2f", totalAmount));
        editor.putString(bankName+"_balance", String.format("$ %.2f", totalAmount));
        editor.apply();
        TextView budget = findViewById(R.id.budget);
        budget.setText(String.format("$ %.2f", budgetAmount));
        TextView expense = findViewById(R.id.expense);
        expense.setText(String.format("$ %.2f", expenseAmount));

    }



    private void deleteTransaction(Transaction transaction, int index){
        deletedTransaction = transaction;
        oldTransactions = transactions;

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                db.transactionDao().delete(transaction);
                transactions.removeIf(trans -> trans.id == transaction.id);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateDashboard();
                        showSnackBar();
                    }
                });
            }
        });
    }

    private void showSnackBar(){
//        View view = findViewById(R.id.coordinator);
//        Snackbar snackbar = Snackbar.make(view, "Transaction deleted!", Snackbar.LENGTH_LONG);
//        snackbar.setAction("UNDO") {
//            undoDelete();
//        }.setA
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchAll();
    }
}