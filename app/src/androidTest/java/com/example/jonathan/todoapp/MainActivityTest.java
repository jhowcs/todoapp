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

import org.junit.After;
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
        AppDatabase.swapToInMemomyDatabase(
                InstrumentationRegistry.getTargetContext());
        popularBancoDeDadosComFakeData();
    }

    private void popularBancoDeDadosComFakeData() {
        AppDatabase db = AppDatabase
                .appDatabaseInstance(
                        InstrumentationRegistry.getTargetContext());
        TarefaDao dao = db.getTarefaDao();

        dao.inserirTarefa(new TarefaModelo("tarefa 1", false));
        dao.inserirTarefa(new TarefaModelo("tarefa 2", false));
        dao.inserirTarefa(new TarefaModelo("tarefa 3", false));
        dao.inserirTarefa(new TarefaModelo("tarefa 4", false));
        dao.inserirTarefa(new TarefaModelo("tarefa 5", false));

    }

    @After
    public void tearDown() {

    }

    @Test
    public void aoClicarNoBotaoDeIncluir_deveAbrirUIDeInclusaoDeTarefa() {
        rule.launchActivity(new Intent());

        Espresso.onView(ViewMatchers.withId(R.id.fabNovaTarefa))
                .perform(ViewActions.click());

        Intents.intended(
                IntentMatchers.hasComponent(NovaTarefaActivity.class.getName()));

    }

}
