package com.example.jonathan.todoapp;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.example.jonathan.todoapp.data.DatabaseConcrete;
import com.example.jonathan.todoapp.data.TarefaDao;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> rule =
            new IntentsTestRule<>(
                    MainActivity.class,
                    true,
                    false);

    @Before
    public void setUp() {
        DatabaseConcrete.switchToInMemoryDatabase(InstrumentationRegistry.getTargetContext());
        popularBancoComDadosEstaticos();
    }

    private void popularBancoComDadosEstaticos() {
        TarefaDao dao = DatabaseConcrete
                .getInstance(InstrumentationRegistry.getTargetContext())
                .getTarefaDao();

        dao.insertAll(new TarefaModelo("Tarefa 1", false),
                new TarefaModelo("Tarefa 2", false),
                new TarefaModelo("Tarefa 3", false),
                new TarefaModelo("Tarefa 4", false),
                new TarefaModelo("Tarefa 5", false),
                new TarefaModelo("Tarefa 6", false));
    }

    private void iniciarActivity() {
        rule.launchActivity(new Intent());
    }

    @Test
    public void aoClicarNoBotaoDeIncluir_deveAbrirUIDeInclusaoDeTarefa() throws Exception {
        iniciarActivity();

        Espresso.onView(ViewMatchers.withId(R.id.fabNovaTarefa))
                .perform(ViewActions.click());

        Intents.intended(
                IntentMatchers.hasComponent(NovaTarefaActivity.class.getName()));
    }

    @Test
    public void aoIniciaActivity_deveExibirListaComSeisTarefas() {
        iniciarActivity();
        Espresso.onView(ViewMatchers.withId(R.id.rvListaTarefa))
                .check(RecyclerViewItemCountAssertion.withItemCount(6));
    }
}
