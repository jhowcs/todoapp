package com.example.jonathan.todoapp.data;


import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseConcrete {

    private static AppDatabase INSTANCE;

    private DatabaseConcrete() {}

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "task")
                    .allowMainThreadQueries()
                    .build();
        }

        return INSTANCE;
    }
}
