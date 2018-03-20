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
    private TodoListener listener;

    public interface TodoListener {
        void aoMarcarDesmarcarTarefa(TarefaModelo tarefaModelo);
        void aoClicarNaTarefa(TarefaModelo tarefaModelo, int posicao);
    }

    public TarefaAdapter(List<TarefaModelo> lista, TodoListener listener) {
        this.lista = lista;
        this.listener = listener;
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

        addChangeListenerForCheckBox(vh);
        adicionarClickNaLinha(vh.itemView, position);

        vh.txtDescricao.setText(tarefaModelo.getDescricao());
        vh.chkExecutado.setChecked(tarefaModelo.isExecutado());

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    private void adicionarClickNaLinha(View view, final int posicao) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.aoClicarNaTarefa(lista.get(posicao), posicao);
            }
        });
    }

    private void addChangeListenerForCheckBox(final TarefaViewHolder vh) {

        vh.chkExecutado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final int paintFlags = isChecked
                        ? vh.txtDescricao.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
                        : Paint.LINEAR_TEXT_FLAG;

                vh.txtDescricao.setPaintFlags(paintFlags);

                TarefaModelo tarefa = marcarTarefa(isChecked, vh.getAdapterPosition());
                listener.aoMarcarDesmarcarTarefa(tarefa);
            }
        });
    }

    private TarefaModelo marcarTarefa(boolean isChecked, int posicao) {
        TarefaModelo tarefaModelo = lista.get(posicao);
        tarefaModelo.setExecutado(isChecked);

        return tarefaModelo;
    }

    public void adicionarNovaTarefa(TarefaModelo tarefa) {
        this.lista.add(tarefa);
        notifyItemInserted(this.lista.size());
    }

    public void atualizarTarefaNaLista(TarefaModelo tarefaModelo, int posicao) {
        this.lista.set(posicao, tarefaModelo);
        notifyItemRangeChanged(posicao, lista.size());
    }

    public TarefaModelo removerItem(final int posicao) {
        TarefaModelo tarefaRemovida = lista.remove(posicao);
        notifyItemRemoved(posicao);

        return tarefaRemovida;
    }

    static class TarefaViewHolder extends RecyclerView.ViewHolder {
        private CheckBox chkExecutado;
        private TextView txtDescricao;

        TarefaViewHolder(View view) {
            super(view);

            chkExecutado = view.findViewById(R.id.chkExecutado);
            txtDescricao = view.findViewById(R.id.txtDescricao);
        }
    }
}
