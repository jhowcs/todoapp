package com.example.jonathan.todoapp.feature.listagem;

import android.widget.Filter;

import com.example.jonathan.todoapp.TarefaModelo;

import java.util.ArrayList;
import java.util.List;

public class TarefaFilter extends Filter {
    private final List<TarefaModelo> listaOriginal;
    private final FiltroCallback filtroCallback;

    public interface FiltroCallback {
        void carregarResultados(List<TarefaModelo> lista);
    }

    public TarefaFilter(List<TarefaModelo> listaOriginal, FiltroCallback filtroCallback) {
        this.listaOriginal = listaOriginal;
        this.filtroCallback = filtroCallback;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        List<TarefaModelo> listaFiltrada = new ArrayList<>();

        if (constraint.length() <= 0) {
            listaFiltrada = listaOriginal;
        } else {
            for (TarefaModelo tarefa : listaOriginal) {
                if (tarefa.getDescricao().toLowerCase()
                        .contains(constraint.toString().toLowerCase())) {
                    listaFiltrada.add(tarefa);
                }
            }
        }

        FilterResults filterResults = new FilterResults();
        filterResults.values = listaFiltrada;

        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        filtroCallback.carregarResultados((List<TarefaModelo>) results.values);
    }
}
