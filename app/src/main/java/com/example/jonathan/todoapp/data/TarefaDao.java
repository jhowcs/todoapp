package com.example.jonathan.todoapp.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.jonathan.todoapp.TarefaModelo;

import java.util.List;

@Dao
public interface TarefaDao {

    @Query("Select * from task")
    List<TarefaModelo> getAll();

    @Insert
    List<Long> insertAll(TarefaModelo... tarefa);

    @Update
    int update(TarefaModelo tarefa);
}
