package com.example.jonathan.todoapp.feature.listagem;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.jonathan.todoapp.R;
import com.example.jonathan.todoapp.TarefaModelo;
import com.example.jonathan.todoapp.feature.inclusao.NovaTarefaActivity;
import com.example.jonathan.todoapp.repository.TarefaRepository;
import com.example.jonathan.todoapp.repository.local.DatabaseConcrete;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements TarefaAdapter.TarefaCallback {

    private RecyclerView rvListaTarefa;
    private FloatingActionButton fabNovaTarefa;

    private TarefaAdapter adapter;

    private static final int RC_NOVA_TAREFA = 10;
    private static final int RC_ATUALIZA_TAREFA = 20;

    private TarefaRepository repositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvListaTarefa = findViewById(R.id.rvListaTarefa);
        fabNovaTarefa = findViewById(R.id.fabNovaTarefa);

        fabNovaTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent
                        = new Intent(MainActivity.this, NovaTarefaActivity.class);
                iniciarActivityNovaTarefa(intent, RC_NOVA_TAREFA);
            }
        });

        repositorio = new TarefaRepository(DatabaseConcrete
                .getInstance(MainActivity.this.getApplicationContext()).getTarefaDao());

        inicializaLista();
        adicionaEventoDeSwipeNaLista();
    }

    private void iniciarActivityNovaTarefa(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    private void inicializaLista() {
        List<TarefaModelo> lista = repositorio.carregarListaDeTarefas();

        adapter = new TarefaAdapter(lista, this);
        LinearLayoutManager llm = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        rvListaTarefa.setLayoutManager(llm);
        rvListaTarefa.addItemDecoration(new DividerItemDecoration(MainActivity.this.getApplicationContext(),
                DividerItemDecoration.VERTICAL));
        rvListaTarefa.setAdapter(adapter);
    }

    private void adicionaEventoDeSwipeNaLista() {
        ItemTouchHelper.SimpleCallback itemTouchCallback
                = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                TarefaAdapter.TarefaViewHolder vh = (TarefaAdapter.TarefaViewHolder) viewHolder;
                TarefaModelo itemRemovido = adapter.removerItem(vh.getAdapterPosition());
                repositorio.removerTarefa(itemRemovido);
            }
        };

        new ItemTouchHelper(itemTouchCallback).attachToRecyclerView(rvListaTarefa);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        if (resultCode == RESULT_OK) {
            TarefaModelo tarefaModelo = data.getParcelableExtra(NovaTarefaActivity.CHAVE_TAREFA);

            if (requestCode == RC_NOVA_TAREFA && data != null) {
                adapter.adicionarNovaTarefa(tarefaModelo);

            } else if (requestCode == RC_ATUALIZA_TAREFA) {
                int posicao = data.getIntExtra(NovaTarefaActivity.CHAVE_POSICAO, -1);
                adapter.atualizarTarefaNaLista(tarefaModelo, posicao);
            }
        }
    }

    @Override
    public void aoMarcarDesmarcarTarefa(TarefaModelo tarefaModelo) {
        repositorio.alterarTarefa(tarefaModelo);
    }

    @Override
    public void aoClicarNaTarefa(TarefaModelo tarefaModelo, int posicao) {
        Intent intent = new Intent(MainActivity.this, NovaTarefaActivity.class);
        intent.putExtra(NovaTarefaActivity.CHAVE_TAREFA, tarefaModelo);
        intent.putExtra(NovaTarefaActivity.CHAVE_POSICAO, posicao);
        iniciarActivityNovaTarefa(intent, RC_ATUALIZA_TAREFA);
    }
}
