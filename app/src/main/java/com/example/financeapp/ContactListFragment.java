package com.example.financeapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
/*
    This file defines the contact list display for the sharing option
    selection
 */
public class ContactListFragment extends Fragment {

    private Activity containerActivity = null;
    private View inflatedView = null;

    private ListView contactsListView;
    ArrayAdapter<String> contactsAdapter = null;
    private ArrayList<String> contacts = new ArrayList<String>();

    public ContactListFragment() { }

    //set the parent activity
    public void setContainerActivity(Activity containerActivity) {
        this.containerActivity = containerActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_contact_list, container, false);

        return inflatedView;
    }

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        getContacts();
    }

    @Override
    public void onResume() {
        super.onResume();
        setupContactsAdapter();
    }

    //get all the contacts
    public void getContacts() {
        int limit = 1000;
        Cursor cursor = containerActivity.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
        while (cursor.moveToNext() && limit > 0) {
            /** Create content string */
            @SuppressLint("Range") String id = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts._ID));
            @SuppressLint("Range") String given = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            contacts.add(given+" :: "+id);
        }
        cursor.close();
    }

    //steup the adapter to display the contacts
    private void setupContactsAdapter() {
        contactsListView =
                (ListView)containerActivity.findViewById(R.id.contact_list_view);
        contactsAdapter = new
                ArrayAdapter<String>(containerActivity, R.layout.row_layout,
                R.id.contact_row_text_view, contacts);
        contactsListView.setAdapter(contactsAdapter);
    }

}

