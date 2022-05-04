package com.example.financeapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends
        RecyclerView.Adapter<TransactionAdapter.TransactionHolder> {

    List<Transaction> transactions;

    public TransactionAdapter(List<Transaction> transactions){
        super();
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public TransactionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_layout, parent, false);
        return new TransactionHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull TransactionHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        Context context = holder.amount.getContext();
        
        if(transaction.getAmount() >= 0){
            holder.amount.setText(String.format("+ $%.2f", transaction.getAmount()));
            holder.amount.setTextColor(context.getColor(R.color.green));
        }else{
            holder.amount.setText(String.format("- $%.2f", Math.abs(transaction.getAmount())));
            holder.amount.setTextColor(context.getColor(R.color.red));
        }

        holder.label.setText(transaction.getLabel());


    }

    public int getItemCount(){
        return transactions.size();
    }

    public void setData(List<Transaction> transactions){
        this.transactions = transactions;
        notifyDataSetChanged();
    }

    public class TransactionHolder extends RecyclerView.ViewHolder{

        public TextView label;
        public TextView amount;
        public TransactionHolder(View view){
            super(view);
            this.label = view.findViewById(R.id.label);
            this.amount = view.findViewById(R.id.amount);
        }
    }




}
