package com.example.jonathan.todoapp.feature.listagem;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.jonathan.todoapp.R;
import com.example.jonathan.todoapp.TarefaModelo;

import java.util.List;

public class TarefaAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements Filterable, TarefaFilter.FiltroCallback {

    private final TarefaCallback tarefaCallback;
    private List<TarefaModelo> lista;

    private static final int VIEW_TYPE_EMPTY_STATE = 10;
    private static final int VIEW_TYPE_DADOS = 20;

    private TarefaFilter filtro;

    public interface TarefaCallback {
        void aoMarcarDesmarcarTarefa(TarefaModelo tarefaModelo);

        void aoClicarNaTarefa(TarefaModelo tarefaModelo, int posicao);

    }

    public TarefaAdapter(List<TarefaModelo> lista, TarefaCallback callback) {
        this.lista = lista;
        this.tarefaCallback = callback;
    }

    @Override
    public int getItemViewType(int position) {
        if (lista.isEmpty()) {
            return VIEW_TYPE_EMPTY_STATE;
        }
        return VIEW_TYPE_DADOS;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view;
        if (viewType == VIEW_TYPE_DADOS) {
            view = inflater.inflate(R.layout.item_lista_tarefa, parent, false);
        } else {
            view = inflater.inflate(R.layout.item_empty_state, parent, false);
        }
        return new TarefaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_DADOS) {
            TarefaModelo tarefaModelo = lista.get(position);
            TarefaViewHolder vh = (TarefaViewHolder) holder;

            addChangeListenerForCheckBox(vh);
            adicionarClickNaLinha(vh);

            vh.txtDescricao.setText(tarefaModelo.getDescricao());
            vh.chkExecutado.setChecked(tarefaModelo.isExecutado());
        }
    }

    private void adicionarClickNaLinha(final TarefaViewHolder vh) {
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posicao = vh.getAdapterPosition();
                tarefaCallback.aoClicarNaTarefa(lista.get(posicao), posicao);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (lista.isEmpty()) {
            return 1;
        }
        return lista.size();
    }

    @Override
    public Filter getFilter() {
        if (filtro == null) {
            filtro = new TarefaFilter(lista, this);
        }
        return filtro;
    }

    @Override
    public void carregarResultados(List<TarefaModelo> lista) {
        this.lista = lista;
        notifyDataSetChanged();
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
                tarefaCallback.aoMarcarDesmarcarTarefa(tarefa);
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
        notifyDataSetChanged();
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

    class TarefaViewHolder extends RecyclerView.ViewHolder {
        private CheckBox chkExecutado;
        private TextView txtDescricao;

        TarefaViewHolder(View view) {
            super(view);

            chkExecutado = view.findViewById(R.id.chkExecutado);
            txtDescricao = view.findViewById(R.id.txtDescricao);
        }
    }
}
