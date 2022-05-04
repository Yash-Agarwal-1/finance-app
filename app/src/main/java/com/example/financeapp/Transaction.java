package com.example.financeapp;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transactions")
public class Transaction {

    @PrimaryKey(autoGenerate = true)
    int id;
    String label;
    double amount;
    String description;
    public Transaction(int id, String label, double amount, String description){
        this.id = id;
        this.label = label;
        this.amount = amount;
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public String getLabel() {
        return label;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
