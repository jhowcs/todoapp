package com.example.jonathan.todoapp.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "task")
public class TarefaModelo {

    @PrimaryKey(autoGenerate = true)
    private int uid;
    private String descricao;
    private boolean isExecutado;

    public TarefaModelo(String descricao, boolean isExecutado) {
        this.descricao = descricao;
        this.isExecutado = isExecutado;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUid() {
        return uid;
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
