package com.example.jonathan.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NovaTarefaActivity extends AppCompatActivity
        implements View.OnClickListener {

    public static final String CHAVE_NOVA_TAREFA = "chave";

    private EditText edtNomeTarefa;
    private Button btnIncluir;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nova_tarefa);

        edtNomeTarefa = findViewById(R.id.edtNomeTarefa);
        btnIncluir = findViewById(R.id.btnIncluir);
        btnIncluir.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnIncluir) {
            Intent intent = new Intent();
            intent.putExtra(CHAVE_NOVA_TAREFA,
                    edtNomeTarefa.getText().toString());

            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }
}
