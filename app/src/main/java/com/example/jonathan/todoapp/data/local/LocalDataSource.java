package com.example.jonathan.todoapp.data.local;


import android.content.Context;
import android.support.annotation.NonNull;

import com.example.jonathan.todoapp.data.TarefaDataSource;
import com.example.jonathan.todoapp.data.TarefaModelo;

import java.util.List;

public class LocalDataSource implements TarefaDataSource {

    private static volatile LocalDataSource INSTANCE;

    private TarefaDao tarefaDao;

    private LocalDataSource(TarefaDao tarefaDao) {
        this.tarefaDao = tarefaDao;
    }

    public static LocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            synchronized (LocalDataSource.class) {
                if (INSTANCE == null) {
                    AppDatabase database = AppDatabase.getAppDatabase(context);
                    INSTANCE = new LocalDataSource(database.tarefaDao());
                }
            }
        }
        return INSTANCE;
    }


    @Override
    public List<TarefaModelo> getTarefas() {
        return tarefaDao.getAll();
    }

    @Override
    public TarefaModelo getTarefaPorId(long id) {
        return tarefaDao.getById(id);
    }

    @Override
    public long incluirTarefa(TarefaModelo tarefa) {
        return tarefaDao.insert(tarefa);
    }

    @Override
    public void atualizarTarefa(TarefaModelo tarefa) {
        tarefaDao.update(tarefa);
    }
}
