package com.example.jonathan.todoapp;

public class TarefaModelo {
    private String descricao;
    private boolean isExecutado;

    public TarefaModelo(String descricao, boolean isExecutado) {
        this.descricao = descricao;
        this.isExecutado = isExecutado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setExecutado(boolean executado) {
        isExecutado = executado;
    }

    public boolean isExecutado() {
        return isExecutado;
    }
}
