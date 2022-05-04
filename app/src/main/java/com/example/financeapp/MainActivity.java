package com.example.financeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        setContentView(R.layout.activity_main);
        System.out.println(sharedPreferences.getStringSet("banks", null));
        restoreViews();
    }

    public void restoreViews(){
        Set<String> s = new HashSet<String>(sharedPreferences.getStringSet("banks", new HashSet<String>()));
        LinearLayout linearLayout = findViewById(R.id.main_layout);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(20, 10, 0, 10);

        for(String str: s){
            AccountView accountView = new AccountView(this);
            accountView.setLayoutParams(layoutParams);
            System.out.println(str);
            if(str.equals("chase")){
                accountView.setBGColor("#117ACA");
                accountView.setBankPhoto(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.chase_logo, null));
                accountView.setBankName("chase");
                linearLayout.addView(accountView);
            }else if(str.equals("boa")){
                accountView.setBGColor("#09297A");
                accountView.setBankPhoto(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.boa_logo, null));
                accountView.setBankName("boa");
                linearLayout.addView(accountView);
            }else if(str.equals("wells_fargo")){
                accountView.setBGColor("#FFFF00");
                accountView.setBankPhoto(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.wells_fargo_logo, null));
                accountView.setBankName("wells_fargo");
                linearLayout.addView(accountView);
            }else if(str.equals("citi")){
                accountView.setBGColor("#003B70");
                accountView.setBankPhoto(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.citi_logo, null));
                accountView.setBankName("citi");
                linearLayout.addView(accountView);
            }
            accountView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TransactionMain.class);
                    intent.putExtra("bank", str);

                    context.startActivity(intent);
                }
            });
        }

    }

    public void addAccountView(View v){
        ArrayList<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("SELECT A BANK");
        spinnerArray.add("CHASE");
        spinnerArray.add("BANK OF AMERICA");
        spinnerArray.add("WELLS FARGO");
        spinnerArray.add("CITI");

        Spinner spinner = new Spinner(this);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, spinnerArray);
        spinner.setAdapter(spinnerArrayAdapter);

        LinearLayout linearLayout = findViewById(R.id.main_layout);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(20, 10, 0, 10);
        spinner.setLayoutParams(layoutParams);
        linearLayout.addView(spinner,1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String bank = spinner.getSelectedItem().toString();
                AccountView accountView = new AccountView(context);
                Set<String> s = new HashSet<String>(sharedPreferences.getStringSet("banks", new HashSet<String>()));
                accountView.setLayoutParams(layoutParams);
                if(i == 1){
                    if(s.contains("chase")){
                        sendToast("chase");
                        return;
                    }
                    accountView.setBGColor("#117ACA");
                    accountView.setBankPhoto(ResourcesCompat.getDrawable(getResources(),
                            R.drawable.chase_logo, null));
                    linearLayout.removeView(spinner);
                    s.add("chase");
                    System.out.println("printing after adding chase" + s);
                    editor.putStringSet("banks", s);
                    accountView.setBankName("chase");
                    linearLayout.addView(accountView,1);
                }else if(i == 2) {
                    if(s.contains("boa")){
                        sendToast("BANK OF AMERICA");
                        return;
                    }
                    accountView.setBGColor("#09297A");
                    accountView.setBankPhoto(ResourcesCompat.getDrawable(getResources(),
                            R.drawable.boa_logo, null));
                    linearLayout.removeView(spinner);
                    s.add("boa");
                    accountView.setBankName("boa");
                    editor.putStringSet("banks", s);
                    linearLayout.addView(accountView,1);
                }else if(i == 3 ){
                    if(s.contains("wells_fargo")){
                        sendToast("WELLS FARGO");
                        return;
                    }
                    accountView.setBGColor("#FFFF00");
                    accountView.setBankPhoto(ResourcesCompat.getDrawable(getResources(),
                            R.drawable.wells_fargo_logo, null));
                    linearLayout.removeView(spinner);
                    s.add("wells_fargo");
                    accountView.setBankName("wells_fargo");
                    editor.putStringSet("banks", s);
                    linearLayout.addView(accountView,1);
                }else if(i == 4 ) {
                    if(s.contains("citi")){
                        sendToast("CITI");
                        return;
                    }
                    accountView.setBGColor("#003B70");
                    accountView.setBankPhoto(ResourcesCompat.getDrawable(getResources(),
                            R.drawable.citi_logo, null));
                    linearLayout.removeView(spinner);
                    s.add("citi");
                    accountView.setBankName("citi");
                    editor.putStringSet("banks", s);
                    linearLayout.addView(accountView, 1);
                }
                accountView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, TransactionMain.class);
                        intent.putExtra("bank", bank);
                        context.startActivity(intent);
                    }
                });
                editor.commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void sendToast(String name){
        CharSequence charSequence = name.toUpperCase(Locale.ROOT) + " ALREADY EXISTS!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, charSequence, duration);
        toast.show();
    }

    public void onStockPageClick(View v){
        Intent intent = new Intent(this, stockPage.class);
        startActivity(intent);
    }
}