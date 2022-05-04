package com.example.financeapp;



import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Transaction.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    abstract TransactionDAO transactionDao();

    public static AppDatabase instance = null;

    public static synchronized AppDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                    "transactions")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public static synchronized AppDatabase getInstance(Context context, String name){
        instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                name)
                .fallbackToDestructiveMigration()
                .build();
        return instance;
    }


}
