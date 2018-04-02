package com.example.jonathan.todoapp;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "tarefa")
public class TarefaModelo implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String descricao;
    private boolean isExecutado;

    public TarefaModelo(String descricao, boolean isExecutado) {
        this.descricao = descricao;
        this.isExecutado = isExecutado;
    }

    protected TarefaModelo(Parcel in) {
        id = in.readLong();
        descricao = in.readString();
        isExecutado = in.readByte() != 0;
    }

    public static final Creator<TarefaModelo> CREATOR = new Creator<TarefaModelo>() {
        @Override
        public TarefaModelo createFromParcel(Parcel in) {
            return new TarefaModelo(in);
        }

        @Override
        public TarefaModelo[] newArray(int size) {
            return new TarefaModelo[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(descricao);
        dest.writeByte((byte) (isExecutado ? 1 : 0));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
