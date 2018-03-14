package com.example.jonathan.todoapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TarefaDao {

    @Query("SELECT * FROM task")
    List<TarefaModelo> getAll();

    @Query("SELECT * FROM task where uid = :uid")
    TarefaModelo getById(long uid);

    @Insert
    long insert(TarefaModelo tarefaModelo);

    @Delete
    void delete(TarefaModelo tarefaModelo);
}
