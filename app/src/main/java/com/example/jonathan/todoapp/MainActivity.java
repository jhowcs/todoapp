package com.example.jonathan.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
        List<TarefaModelo> lista = new ArrayList<>();
        lista.add(new TarefaModelo("Tarefa 1", false));
        lista.add(new TarefaModelo("Tarefa 2", true));
        lista.add(new TarefaModelo("Tarefa 3", false));

        adapter = new TarefaAdapter(lista);
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
                String novaTarefa =
                        data.getStringExtra(NovaTarefaActivity.CHAVE_NOVA_TAREFA);

                Toast.makeText(this, novaTarefa, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
