package com.example.financeapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
    private Drawable photoId;
    private int accountNumber;
    private int amountSpent;
    private String bankName;
    private final ImageView imageView;
    private final TextView textView;
    private final ConstraintLayout constraintLayout;

    public AccountView(@NonNull Context context){
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.sample_account_view, this);
        imageView = findViewById(R.id.bank_logo_img);
        textView = findViewById(R.id.acc_num);
        constraintLayout = findViewById(R.id.acc_view_layout);
    }
    public AccountView(@NonNull Context context, AttributeSet attrs) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.sample_account_view, this);
        imageView = findViewById(R.id.bank_logo_img);
        textView = findViewById(R.id.acc_num);
        constraintLayout = findViewById(R.id.acc_view_layout);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AccountView, 0, 0);
        imageView.setImageDrawable(a.getDrawable(R.styleable.AccountView_image));
        textView.setText(a.getString(R.styleable.AccountView_text));
        a.recycle();
    }

    public void setBGColor(String color){
        this.color = color;
        constraintLayout.setBackgroundResource(R.drawable.layout_bg);
        GradientDrawable gradientDrawable = (GradientDrawable) constraintLayout.getBackground();
        gradientDrawable.setColor(Color.parseColor(color));
    }

    public String getColor(){
        return this.color;
    }

    public void setBankPhoto(Drawable drawable){
        this.photoId = drawable;
        imageView.setImageDrawable(photoId);
    }

    public Drawable getBankPhoto(){
        return this.photoId;
    }

    public void setAccountNumber(int num){
        this.accountNumber = num;
        textView.setText(num);
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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

    public void updateBalance(){

    }

}