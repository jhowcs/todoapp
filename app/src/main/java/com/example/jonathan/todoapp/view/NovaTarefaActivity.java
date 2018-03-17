package com.example.jonathan.todoapp.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jonathan.todoapp.AppExecutors;
import com.example.jonathan.todoapp.R;
import com.example.jonathan.todoapp.data.local.LocalDataSource;
import com.example.jonathan.todoapp.data.TarefaModelo;
import com.example.jonathan.todoapp.data.TarefaRepository;

public class NovaTarefaActivity extends AppCompatActivity
        implements View.OnClickListener, TarefaCallback.onInsert {

    public static final String CHAVE_NOVA_TAREFA = "chave";

    private EditText edtNomeTarefa;
    private Button btnIncluir;

    private TarefaRepository repository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nova_tarefa);

        edtNomeTarefa = findViewById(R.id.edtNomeTarefa);
        btnIncluir = findViewById(R.id.btnIncluir);
        btnIncluir.setOnClickListener(this);

        repository = new TarefaRepository(new AppExecutors(), LocalDataSource.getInstance(this));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnIncluir) {
            String nomeTarefa = edtNomeTarefa.getText().toString();
            repository.inserirTarefa(this, new TarefaModelo(nomeTarefa, false));
        }
    }

    @Override
    public void onInserirTarefa(long idGerado) {
        Intent intent = new Intent();
        intent.putExtra(CHAVE_NOVA_TAREFA, idGerado);

        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
