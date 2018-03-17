package com.example.jonathan.todoapp.data;

import com.example.jonathan.todoapp.AppExecutors;
import com.example.jonathan.todoapp.view.TarefaCallback;

import java.util.List;

public class TarefaRepository {

    private AppExecutors appExecutors;

    private TarefaDataSource taskDataSource;

    public TarefaRepository(AppExecutors appExecutors, TarefaDataSource taskDataSource) {
        this.appExecutors = appExecutors;
        this.taskDataSource = taskDataSource;
    }

    public void getTarefa(final TarefaCallback.onLoad callback) {
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<TarefaModelo> lista = taskDataSource.getTarefas();
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onLoadListaDeTarefas(lista);
                    }
                });
            }
        });
    }

    public void getTarefaPorId(final TarefaCallback.onLoad callback, final long id) {
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final TarefaModelo tarefa = taskDataSource.getTarefaPorId(id);

                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onLoadTarefa(tarefa);
                    }
                });
            }
        });
    }

    public void inserirTarefa(final TarefaCallback.onInsert callback, final TarefaModelo tarefa) {
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final long id = taskDataSource.incluirTarefa(tarefa);

                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onInserirTarefa(id);
                    }
                });
            }
        });
    }

    public void atualizarTarefa(final TarefaModelo tarefa) {
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                taskDataSource.atualizarTarefa(tarefa);
            }
        });
    }
}
