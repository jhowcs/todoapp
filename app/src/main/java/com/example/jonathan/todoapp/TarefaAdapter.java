package com.example.jonathan.todoapp;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TarefaAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<TarefaModelo> lista = new ArrayList<>();

    public TarefaAdapter(List<TarefaModelo> lista) {
        this.lista = lista;
    }

    public void adicionarNovaTarefa(TarefaModelo tarefa) {
        this.lista.add(tarefa);
        notifyDataSetChanged();
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

        addChangeListenerForCheckBox(vh.chkExecutado, vh.txtDescricao);

        vh.txtDescricao.setText(tarefaModelo.getDescricao());
        vh.chkExecutado.setChecked(tarefaModelo.isExecutado());

    }

    private void addChangeListenerForCheckBox(final CheckBox chkExecutado, final TextView txtDescricao) {
        chkExecutado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final int paintFlags = isChecked
                        ? txtDescricao.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
                        : Paint.LINEAR_TEXT_FLAG;

                    txtDescricao.setPaintFlags(paintFlags);
            }
        });
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
