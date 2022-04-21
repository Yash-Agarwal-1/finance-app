package com.example.financeapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * This file is to design the view for the adding the transactions and all the tags
 * Need to use a listview and array adapter or might have to create a custom ArrayAdapter
 * We really need to learn how to design customadapters.
 */
public class AccountView extends View {

    public AccountView(Context context) {
        super(context);
    }

    public AccountView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AccountView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }


}