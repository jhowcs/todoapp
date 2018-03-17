package com.example.jonathan.todoapp;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "task")
public class TarefaModelo {

    @PrimaryKey(autoGenerate = true)
    private long uid;

    private String descricao;
    private boolean isExecutado;

    public TarefaModelo(String descricao, boolean isExecutado) {
        this.descricao = descricao;
        this.isExecutado = isExecutado;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
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