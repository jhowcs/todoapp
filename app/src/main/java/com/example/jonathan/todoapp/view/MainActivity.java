package com.example.jonathan.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TarefaCallback {

    private RecyclerView rvListaTarefa;
    private FloatingActionButton fabNovaTarefa;

    private TarefaAdapter adapter;

    private static final int RC_NOVA_TAREFA = 10;
    private List<TarefaModelo> lista;
    private TarefaRepository repository;

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
                        = new Intent(MainActivity.this,
                        NovaTarefaActivity.class);

                startActivityForResult(intent, RC_NOVA_TAREFA);
            }
        });

        repository = new TarefaRepository(new AppExecutors(), LocalDataSource.getInstance(this));

        inicializaLista();
    }

    private void inicializaLista() {
        lista = new ArrayList<>();
        repository.getTarefa(this);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        if (resultCode == RESULT_OK && requestCode == RC_NOVA_TAREFA) {
            if (data != null) {
                long idGerado =
                        data.getLongExtra(NovaTarefaActivity.CHAVE_NOVA_TAREFA, 0);
                repository.getTarefaPorId(this, idGerado);

            }
        }
    }

    @Override
    public void onLoadListaDeTarefas(List<TarefaModelo> tarefaModelo) {
        lista = tarefaModelo;
        adapter = new TarefaAdapter(lista);
        LinearLayoutManager llm = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        rvListaTarefa.setLayoutManager(llm);
        rvListaTarefa.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadTarefa(TarefaModelo tarefaModelo) {
        adapter.addTarefa(tarefaModelo);
    }


}
