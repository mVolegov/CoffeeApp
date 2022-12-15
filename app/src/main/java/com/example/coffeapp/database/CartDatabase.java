package com.example.coffeapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Cart.class}, version = 1)
public abstract class CartDatabase extends RoomDatabase {

    public static CartDatabase instance;

    public abstract CartDAO cartDAO();

    public static synchronized CartDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            CartDatabase.class,
                            "CartDatabase"
                    )
                    .fallbackToDestructiveMigration().build();
        }

        return instance;
    }
}
