package com.example.jonathan.todoapp.repository;

import com.example.jonathan.todoapp.TarefaModelo;
import com.example.jonathan.todoapp.repository.local.TarefaDao;

import java.util.List;

public class TarefaRepository {
    private TarefaDao tarefaDao;

    public TarefaRepository(TarefaDao repositorio) {
        this.tarefaDao = repositorio;
    }

    public List<Long> inserirTarefa(TarefaModelo... tarefaModelo) {
        return tarefaDao.insertAll(tarefaModelo);
    }

    public List<TarefaModelo> carregarListaDeTarefas() {
        return tarefaDao.getAll();
    }

    public void removerTarefa(TarefaModelo tarefaModelo) {
        tarefaDao.delete(tarefaModelo);
    }

    public int alterarTarefa(TarefaModelo tarefaModelo) {
        return tarefaDao.update(tarefaModelo);
    }
}
