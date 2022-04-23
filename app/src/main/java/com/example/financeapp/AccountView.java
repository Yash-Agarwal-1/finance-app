package com.example.financeapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * This file is to design the view for the adding the transactions and all the tags
 * Need to use a listview and array adapter or might have to create a custom ArrayAdapter
 * We really need to learn how to design customadapters.
 */
public class AccountView extends ConstraintLayout {

    private String color;
    private int photoId;
    private int accountNumber;
    private int amountSpent;

    public AccountView(@NonNull Context context) {
        super(context);
        color = "#FFFFFF";
        photoId = R.drawable.bank;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.sample_account_view, this);
    }

    public void setColor(String color){
        this.color = color;
    }

    public String getColor(){
        return this.color;
    }

    public void setBankPhoto(int photo_id){
        this.photoId = photo_id;

    }

    public int getBankPhoto(){
        return this.photoId;
    }

    public void setAccountNumber(int num){
        this.accountNumber = num;
    }

    public int getAccountNumber(int num){
        return this.accountNumber;
    }

    public void setAmountSpent(int num){
        this.amountSpent = num;
    }

    public int getAmountSpent(int num){
        return this.amountSpent;
    }


}