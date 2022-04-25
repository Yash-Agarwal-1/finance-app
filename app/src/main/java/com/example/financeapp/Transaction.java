package com.example.financeapp;

public class Transaction {

    private String label;
    private double amount;

    public Transaction(String label, double amount){
        this.label = label;
        this.amount = amount;
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
