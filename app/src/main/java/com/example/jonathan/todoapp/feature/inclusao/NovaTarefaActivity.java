package com.example.jonathan.todoapp.feature.inclusao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jonathan.todoapp.R;
import com.example.jonathan.todoapp.TarefaModelo;
import com.example.jonathan.todoapp.repository.TarefaRepository;
import com.example.jonathan.todoapp.repository.local.DatabaseConcrete;

import java.util.List;

public class NovaTarefaActivity extends AppCompatActivity
        implements View.OnClickListener {

    public static final String CHAVE_TAREFA = "chave";
    public static final String CHAVE_POSICAO = "posicao";

    private Toolbar toolbar;
    private EditText edtNomeTarefa;
    private Button btnIncluir;
    private TarefaModelo tarefa;

    private boolean isAlterando;
    private int posicao;
    private TarefaRepository repositorio;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nova_tarefa);

        toolbar = findViewById(R.id.toolbar);
        edtNomeTarefa = findViewById(R.id.edtNomeTarefa);
        btnIncluir = findViewById(R.id.btnIncluir);
        btnIncluir.setOnClickListener(this);

        if (getIntent().hasExtra(CHAVE_TAREFA)) {
            preencherInformacoesDaTarefa();
            isAlterando = true;
        }

        repositorio = new TarefaRepository(DatabaseConcrete
                .getInstance(NovaTarefaActivity.this.getApplicationContext()).getTarefaDao());

        setupToolbar();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void preencherInformacoesDaTarefa() {
        Intent intent = getIntent();
        tarefa = intent.getParcelableExtra(CHAVE_TAREFA);
        posicao = intent.getIntExtra(CHAVE_POSICAO, -1);
        edtNomeTarefa.setText(tarefa.getDescricao());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnIncluir) {
            String nomeTarefa = edtNomeTarefa.getText().toString();
            Intent intent = new Intent();

            if (isAlterando) {
                alterarTarefaNoBancoLocal(nomeTarefa);
                intent.putExtra(CHAVE_POSICAO, posicao);
            } else {
                salvarNoBancoLocal(nomeTarefa);
            }

            intent.putExtra(CHAVE_TAREFA, tarefa);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

    private void alterarTarefaNoBancoLocal(String nomeTarefa) {
        tarefa.setDescricao(nomeTarefa);
        repositorio.alterarTarefa(tarefa);
    }

    private void salvarNoBancoLocal(String nomeTarefa) {
        tarefa = new TarefaModelo(nomeTarefa, false);
        List<Long> ids = repositorio.inserirTarefa(tarefa);
        tarefa.setId(ids.get(0));
    }
}
