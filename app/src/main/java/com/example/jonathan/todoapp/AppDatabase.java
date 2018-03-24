package com.example.jonathan.todoapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

@Database(entities = {TarefaModelo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract TarefaDao getTarefaDao();

    public static AppDatabase appDatabaseInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "tododatabase")
                    .allowMainThreadQueries()
                    .build();
        }

        return INSTANCE;
    }

    @VisibleForTesting
    public static void swapToInMemomyDatabase(Context context) {
        INSTANCE = Room.inMemoryDatabaseBuilder(context,
                AppDatabase.class)
                .allowMainThreadQueries()
                .build();
    }
}
