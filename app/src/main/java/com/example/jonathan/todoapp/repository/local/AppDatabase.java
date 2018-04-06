package com.example.jonathan.todoapp.repository.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.jonathan.todoapp.TarefaModelo;

@Database(entities = {TarefaModelo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TarefaDao getTarefaDao();
}
