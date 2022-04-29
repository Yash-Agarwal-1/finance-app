package com.example.financeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    /*
    Planning to make an interface with an add account button to add
    multiple bank accounts. Once the add button is pressed a new set of fields open
    to enter the bank information. The amount of information that might be required
    is....(account num, initialize amount spent to zero, bank name) do not care about current balance.
    Only manage the amount spent. We can add images or colors based on color themes of some famous banks.
    We can organize all the bank cards by creating a custom view and making a listview out of it.
    The ListView can have an onItemClickListener to see which card was clicked and open the specific
    crad in a new fragment. Messed up rn by creating the views in main. Will shift later. Want to have
    something to see for rn. Inside each bank info fragment we can have options to add transaction.
    We can have some preset tags to classify transactions by. We can also give the user the option to add tags.
    Using those tags the user can classify expenditure. We can add graphs depending on the time left.
    We can have the top heading of the app as a total sum of all the values of expenditures of individual
    accounts. We need to pay some serious attention to make the UI really cool XD.
    Let me know if we want to make the page Im working on part of the mainactivity or put it on a
    fragment. Won't be much work.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addAccountView(View v){
        AccountView accountView = new AccountView(this);
        LinearLayout linearLayout = findViewById(R.id.main_layout);
        linearLayout.addView(accountView,0);
    }

    public void onBankCardClick(View v){
        Intent intent = new Intent(this, TransactionMain.class);
        startActivity(intent);
    }
}