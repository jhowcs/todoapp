package com.example.jonathan.todoapp.feature.inclusao;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.example.jonathan.todoapp.R;
import com.example.jonathan.todoapp.TarefaModelo;
import com.example.jonathan.todoapp.repository.local.DatabaseConcrete;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class NovaTarefaActivityTest {

    public static final String LITERAL_TAREFA_PARA_ALTERAÇÃO = "Tarefa para Alteração";
    @Rule
    public IntentsTestRule<NovaTarefaActivity> rule
            = new IntentsTestRule<>(NovaTarefaActivity.class,
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
        Intent intent = new Intent();
        intent.putExtra(NovaTarefaActivity.CHAVE_TAREFA,
                new TarefaModelo(LITERAL_TAREFA_PARA_ALTERAÇÃO, false));
        iniciarActivity(intent);
        Espresso.onView(ViewMatchers.withText(LITERAL_TAREFA_PARA_ALTERAÇÃO))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void aoAlterarUmaTarefa_deveFinalizarActivity() {
        Intent intent = new Intent();
        intent.putExtra(NovaTarefaActivity.CHAVE_TAREFA,
                new TarefaModelo(LITERAL_TAREFA_PARA_ALTERAÇÃO, false));
        iniciarActivity(intent);
        Espresso.onView(ViewMatchers.withId(R.id.edtNomeTarefa))
                .perform(ViewActions.clearText(),
                        ViewActions.typeText("Tarefa Alterada"));
        Espresso.onView(ViewMatchers.withId(R.id.btnIncluir))
                .perform(ViewActions.click());

        Assert.assertTrue(rule.getActivity().isFinishing());

    }
}