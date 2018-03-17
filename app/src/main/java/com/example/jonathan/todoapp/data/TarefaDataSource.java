package com.example.jonathan.todoapp.data;


import java.util.List;

public interface TarefaDataSource {

    List<TarefaModelo> getTarefas();

    TarefaModelo getTarefaPorId(long id);

    long incluirTarefa(TarefaModelo tarefa);

    void atualizarTarefa(TarefaModelo tarefa);
}
