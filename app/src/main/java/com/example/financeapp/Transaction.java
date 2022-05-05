package com.example.financeapp;

/**
 * Author: Yash Agarwal
 * This file holds one transaction which is displayed in the main
 * transaction page. After the user adds a transaction through the add transaction
 * page it gets inserted into the respective database for that bank. It holds information about the
 * transaction such as the name of the expenditure and the amount spent.
 */

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
