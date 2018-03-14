package com.example.jonathan.todoapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TarefaAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<TarefaModelo> lista = new ArrayList<>();

    public TarefaAdapter(List<TarefaModelo> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_lista_tarefa, parent, false);
        return new TarefaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TarefaModelo tarefaModelo = lista.get(position);
        TarefaViewHolder vh = (TarefaViewHolder) holder;
        vh.txtDescricao.setText(tarefaModelo.getDescricao());
        vh.chkExecutado.setChecked(tarefaModelo.isExecutado());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class TarefaViewHolder extends RecyclerView.ViewHolder {
        private CheckBox chkExecutado;
        private TextView txtDescricao;

        public TarefaViewHolder(View view) {
            super(view);

            chkExecutado = view.findViewById(R.id.chkExecutado);
            txtDescricao = view.findViewById(R.id.txtDescricao);
        }
    }
}
