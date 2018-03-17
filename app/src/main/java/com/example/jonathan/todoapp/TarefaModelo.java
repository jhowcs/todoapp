package com.example.jonathan.todoapp;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "task")
public class TarefaModelo  implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long uid;

    private String descricao;
    private boolean isExecutado;

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

    public TarefaModelo(String descricao, boolean isExecutado) {
        this.descricao = descricao;
        this.isExecutado = isExecutado;
    }

    protected TarefaModelo(Parcel in) {
        uid = in.readLong();
        descricao = in.readString();
        isExecutado = in.readByte() != 0;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(uid);
        dest.writeString(descricao);
        dest.writeByte((byte) (isExecutado ? 1 : 0));
    }
}