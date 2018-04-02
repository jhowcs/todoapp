package com.example.jonathan.todoapp;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.example.jonathan.todoapp.data.DatabaseConcrete;
import com.example.jonathan.todoapp.feature.inclusao.NovaTarefaActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class NovaTarefaActivityTest {

    @Rule
    public IntentsTestRule<NovaTarefaActivity> rule
            = new IntentsTestRule(NovaTarefaActivity.class,
            true,
            false);

    private void iniciarActivity(Intent intent) {
        rule.launchActivity(intent);
    }

    @Before
    public void setUp() {
        DatabaseConcrete.switchToInMemoryDatabase(InstrumentationRegistry.getTargetContext());
    }

    @Test
    public void aoClicarEmIncluir_deveFinalizarActivity() {
        iniciarActivity(null);
        Espresso.onView(ViewMatchers.withId(R.id.edtNomeTarefa))
                .perform(ViewActions.typeText("estudar espresso"),
                        ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.btnIncluir))
                .perform(ViewActions.click());

        Assert.assertTrue(rule.getActivity().isFinishing());
    }

    @Test
    public void aoIniciarActivityComTarefaParaAlteracao_deveExibirDescricaoDaTarefa() {
        String tarefaParaAlteração = "Tarefa para Alteração";

        Intent intent = new Intent();
        intent.putExtra(NovaTarefaActivity.CHAVE_TAREFA,
                new TarefaModelo(tarefaParaAlteração, false));
        iniciarActivity(intent);
        Espresso.onView(ViewMatchers.withText(tarefaParaAlteração))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}