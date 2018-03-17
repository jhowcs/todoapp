package com.example.jonathan.todoapp.view;

import android.support.annotation.MainThread;

public interface InserirTarefaCallback {

    @MainThread
    void onInserirTarefa(long id);
}
