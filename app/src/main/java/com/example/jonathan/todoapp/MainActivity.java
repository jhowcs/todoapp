package com.example.jonathan.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.jonathan.todoapp.data.DatabaseConcrete;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TarefaAdapter.TodoListener {

    private RecyclerView rvListaTarefa;
    private FloatingActionButton fabNovaTarefa;

    private TarefaAdapter adapter;

    private static final int RC_NOVA_TAREFA = 10;

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

        inicializaLista();
    }

    private void inicializaLista() {
        List<TarefaModelo> lista = DatabaseConcrete.getInstance(this.getApplicationContext())
                .getTarefaDao().getAll();

        adapter = new TarefaAdapter(lista, this);
        LinearLayoutManager llm = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        rvListaTarefa.setLayoutManager(llm);
        rvListaTarefa.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        if (resultCode == RESULT_OK && requestCode == RC_NOVA_TAREFA) {
            if (data != null) {
                TarefaModelo tarefaModelo =
                        data.getParcelableExtra(NovaTarefaActivity.CHAVE_NOVA_TAREFA);

                adapter.adicionarNovaTarefa(tarefaModelo);
            }
        }
    }

    @Override
    public void aoSelecionarTarefa(TarefaModelo tarefaModelo) {
        DatabaseConcrete.getInstance(this.getApplicationContext())
                .getTarefaDao().update(tarefaModelo);
    }
}
