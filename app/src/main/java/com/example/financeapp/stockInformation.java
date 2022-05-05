package com.example.financeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;

public class stockInformation extends AppCompatActivity {
    private StockInfoFragment stockInfoFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_information);

        stockInfoFragment = new StockInfoFragment();
        Intent intent = getIntent();
        String ticker = intent.getStringExtra("stock_name");
        String open = intent.getStringExtra("open");
        String high = intent.getStringExtra("high");
        String low = intent.getStringExtra("low");
        String close = intent.getStringExtra("close");
        String volume = intent.getStringExtra("volume");

        Bundle bundle = new Bundle();
        bundle.putString("ticker", ticker);
        bundle.putString("open", open);
        bundle.putString("high", high);
        bundle.putString("low", low);
        bundle.putString("close", close);
        bundle.putString("volume", volume);
        stockInfoFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_view, stockInfoFragment)
                .addToBackStack(null)
                .commit();

    }

    @SuppressLint("Range")
    public void onContactClick(View v){
        String contactId = ((TextView)v).getText().toString();
        String email = "";
        contactId = contactId.substring(contactId.indexOf("::")+3);

        Cursor emails = getContentResolver().query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID +
                        " = "
                        + contactId, null, null);
        if (emails.moveToNext()) {
            email = emails.getString(
                    emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
        }
        emails.close();

        String str = stockInfoFragment.totalStockInfo;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("vnd.android.cursor.dir/email");
        intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {email});
        intent.putExtra(Intent.EXTRA_TEXT, str);

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);

    }


}