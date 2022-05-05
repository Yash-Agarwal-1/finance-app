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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Activity activity;
    private AccountView accView;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        setContentView(R.layout.activity_main);
        activity = MainActivity.this;
        restoreViews();
    }

    public void restoreViews(){
        Set<String> s = new HashSet<String>(sharedPreferences.getStringSet("banks", new HashSet<String>()));
        LinearLayout linearLayout = findViewById(R.id.main_layout);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 10, 0, 0);
        layoutParams.gravity = Gravity.CENTER;
        SharedPreferences sharedPreferences1 = getSharedPreferences("bank_balances", MODE_PRIVATE);
        for(String str: s){
            AccountView accountView = new AccountView(this);
            accountView.setLayoutParams(layoutParams);
            if(str.equals("chase")){
                accountView.setBGColor("#117ACA");
                accountView.setBankPhoto(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.chase_logo, null));
                accountView.setBankName("chase");
                accountView.setAmountSpent(sharedPreferences1.getString("chase_balance", "$0.00"));
                linearLayout.addView(accountView);
            }else if(str.equals("boa")){
                accountView.setBGColor("#09297A");
                accountView.setBankPhoto(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.boa_logo, null));
                accountView.setBankName("boa");
                accountView.setAmountSpent(sharedPreferences1.getString("boa_balance", "$0.00"));
                linearLayout.addView(accountView);
            }else if(str.equals("wells_fargo")){
                accountView.setBGColor("#FFFF00");
                accountView.setBankPhoto(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.wells_fargo_logo, null));
                accountView.setBankName("wells_fargo");
                accountView.setAmountSpent(sharedPreferences1.getString("wells_fargo_balance", "$0.00"));
                linearLayout.addView(accountView);
            }else if(str.equals("citi")){
                accountView.setBGColor("#003B70");
                accountView.setBankPhoto(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.citi_logo, null));
                accountView.setBankName("citi");
                accountView.setAmountSpent(sharedPreferences1.getString("citi_balance", "$0.00"));
                linearLayout.addView(accountView);
            }
            accountView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    accView = accountView;
                    Intent intent = new Intent(context, TransactionMain.class);
                    intent.putExtra("bank", str);
                    context.startActivity(intent);
                }
            });
        }
        updateTotal();
    }

    public void addAccountView(View v){
        if(checkSpinnerExists()){
            return;
        }
        ArrayList<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("SELECT A BANK");
        spinnerArray.add("CHASE");
        spinnerArray.add("BANK OF AMERICA");
        spinnerArray.add("WELLS FARGO");
        spinnerArray.add("CITI");

        Spinner spinner = new Spinner(this);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, spinnerArray);
        spinner.setAdapter(spinnerArrayAdapter);

        LinearLayout linearLayout = findViewById(R.id.main_layout);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 10, 0, 0);
        layoutParams.gravity = Gravity.CENTER;
        spinner.setLayoutParams(layoutParams);
        linearLayout.addView(spinner,0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String bank = spinner.getSelectedItem().toString();
                AccountView accountView = new AccountView(context);
                Set<String> s = new HashSet<String>(sharedPreferences.getStringSet("banks", new HashSet<String>()));
                accountView.setLayoutParams(layoutParams);
                if(i == 1){
                    if(s.contains("chase")){
                        linearLayout.removeView(spinner);
                        sendToast("chase");
                        return;
                    }
                    accountView.setBGColor("#117ACA");
                    accountView.setBankPhoto(ResourcesCompat.getDrawable(getResources(),
                            R.drawable.chase_logo, null));
                    linearLayout.removeView(spinner);
                    s.add("chase");
                    editor.putStringSet("banks", s);
                    accountView.setBankName("chase");
                    accountView.setAmountSpent("$0.0");
                    linearLayout.addView(accountView);
                }else if(i == 2) {
                    if(s.contains("boa")){
                        linearLayout.removeView(spinner);
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
                    accountView.setAmountSpent("$0.0");
                    linearLayout.addView(accountView);
                }else if(i == 3 ){
                    if(s.contains("wells_fargo")){
                        linearLayout.removeView(spinner);
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
                    accountView.setAmountSpent("$0.0");
                    linearLayout.addView(accountView);
                }else if(i == 4 ) {
                    if(s.contains("citi")){
                        linearLayout.removeView(spinner);
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
                    accountView.setAmountSpent("$0.0");
                    linearLayout.addView(accountView);
                }
                accountView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        accView = accountView;
                        Intent intent = new Intent(context, TransactionMain.class);
                        intent.putExtra("bank", bank);
                        activity.startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
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

    public void onHelpPageClick(View v){
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    public boolean checkSpinnerExists(){
        LinearLayout linearLayout = findViewById(R.id.main_layout);
        for(int i = 0; i < linearLayout.getChildCount(); i++ ){
            if(linearLayout.getChildAt(i) instanceof Spinner){
                return true;
            }
        }
        return false;
    }

    public void updateTotal(){
        double sum = 0.0;
        LinearLayout linearLayout = findViewById(R.id.main_layout);
        for(int i = 0; i < linearLayout.getChildCount(); i++ ){
            if(linearLayout.getChildAt(i) instanceof AccountView){
                AccountView accountView = (AccountView) linearLayout.getChildAt(i);
                sum += accountView.getAmountSpent();
            }
        }
        TextView textView = findViewById(R.id.amt_spent);
        textView.setText("TOTAL: "+sum);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences sharedPreferences1 = getSharedPreferences("bank_balances", MODE_PRIVATE);
        LinearLayout linearLayout = findViewById(R.id.main_layout);
        for(int i = 0; i < linearLayout.getChildCount(); i++){
            if(!(linearLayout.getChildAt(i) instanceof AccountView)){
                continue;
            }
            AccountView accountView = (AccountView)linearLayout.getChildAt(i);
            accountView.setAmountSpent(sharedPreferences1.getString(accountView.getBankName()+"_balance", "$0.00"));
        }
        updateTotal();

    }
}