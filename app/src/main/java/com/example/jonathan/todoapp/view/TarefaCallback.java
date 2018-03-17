package com.example.jonathan.todoapp.view;

import android.support.annotation.MainThread;

import com.example.jonathan.todoapp.data.TarefaModelo;

import java.util.List;

public interface TarefaCallback {

    @MainThread
    void onLoadListaDeTarefas(List<TarefaModelo> tarefaModelo);

    @MainThread
    void onLoadTarefa(TarefaModelo tarefaModelo);
}
