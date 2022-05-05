package com.example.financeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class stockPage extends AppCompatActivity {
     String ticker="";
    private Context context;


     //by default if npthing is selected, time interval is 15 min.
     Integer time = 15;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_page);
        context=this;


    }


    //this is the function that will get executed when the find ticker button is clicked.
    public void onClick(View view){
        EditText editText = findViewById(R.id.searchTerm);
        ticker = editText.getText().toString();

        //spins up the async task to fetch the data for the specific search term
        new TickerFinder().execute();
    }


    public void one_min(View view){
        time = 1;
    }
    public void five_min(View view) {
        time = 5;
    }
    public void fifteen_min(View view){
        time = 15;
    }
    public void thirty_min(View view){
        time = 30;
    }
    public void sixty_min(View view){
        time = 60;
    }



    /*
   This helper sub class uses an AsyncTask to do the network fetching and decoding part
   to display the results depending on the user search term.
  */
    private class TickerFinder extends AsyncTask<Object, Void, JSONObject> {

        //This function is called first when the AsyncTask is started. It decodes and forms the API
        //URL. Connects to the internet. Fetches teh data in JSON format. Decodes it and returns
        //the data in the form of a JSONObject
        @Override
        protected JSONObject doInBackground(Object[] objects) {

            JSONObject jsonObj = null;

            String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol="+ticker+"&interval="+time+"min&apikey="+"OJVW9N79YLWD2URR";

            try{
                String json="";
                String line;
                //connect to the internet with the URL we formed
                URL link = new URL(url);
                URLConnection urlConnection = link.openConnection();
                //specify the user-agent
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                while ((line = in.readLine()) != null) {
                    //read in each line
                    json += line;
                }
                in.close();
                //store it as a jsonobject
                jsonObj = new JSONObject(json);
            }catch(Exception e){
                e.printStackTrace();
            }
            return jsonObj;

        }

        /*
        This function uses the JSONObject data to find the required data fields and displays
        them in the appropriate views.
         */
        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            try {
                //string to hold the name of the company.
                String company_symbol = jsonObject.getJSONObject("Meta Data").getString("2. Symbol");

                //string to hold the time interval selected.
                String time_interval = jsonObject.getJSONObject("Meta Data").getString("4. Interval");

                //string to hold last refreshed, since it is needed to access other fields.
                String last_refreshed = jsonObject.getJSONObject("Meta Data").getString("3. Last Refreshed");

                //string to hold the opening price.
                String open = jsonObject.getJSONObject("Time Series ("+time_interval+")").getJSONObject(last_refreshed).getString("1. open");
                String high = jsonObject.getJSONObject("Time Series ("+time_interval+")").getJSONObject(last_refreshed).getString("2. high");
                String low = jsonObject.getJSONObject("Time Series ("+time_interval+")").getJSONObject(last_refreshed).getString("3. low");
                String close = jsonObject.getJSONObject("Time Series ("+time_interval+")").getJSONObject(last_refreshed).getString("4. close");
                String volume = jsonObject.getJSONObject("Time Series ("+time_interval+")").getJSONObject(last_refreshed).getString("5. volume");

                Intent intent = new Intent(context, stockInformation.class);
                intent.putExtra("stock_name", company_symbol);
                intent.putExtra("open", open);
                intent.putExtra("high", high);
                intent.putExtra("low", low);
                intent.putExtra("close", close);
                intent.putExtra("volume", volume);

                context.startActivity(intent);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

}





