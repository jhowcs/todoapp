package com.example.jonathan.todoapp.feature.listagem;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.example.jonathan.todoapp.R;
import com.example.jonathan.todoapp.TarefaModelo;
import com.example.jonathan.todoapp.feature.inclusao.NovaTarefaActivity;
import com.example.jonathan.todoapp.repository.local.DatabaseConcrete;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityEmptyStateTest {

    @Rule
    public IntentsTestRule<MainActivity> rule =
            new IntentsTestRule<>(
                    MainActivity.class,
                    true,
                    false);

    @Before
    public void setUp() {
        DatabaseConcrete.switchToInMemoryDatabase(InstrumentationRegistry.getTargetContext());
    }

    private void iniciarActivity() {
        rule.launchActivity(new Intent());
    }

    @Test
    public void aoIniciarActivityComListaVazia_deveExibirEmptyState() {
        iniciarActivity();
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.txtMensagem),
                ViewMatchers.withText("nenhuma tarefa cadastrada")))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void aoIncluirUmaTarefa_deveOcultarEmptyStateEMostraListaComDados() {
        iniciarActivity();

        // arrange
        Intent intentNovaTarefa = new Intent();
        intentNovaTarefa.putExtra(NovaTarefaActivity.CHAVE_TAREFA,
                new TarefaModelo("Tarefa 1", false));

        Intents.intending(IntentMatchers.hasComponent(NovaTarefaActivity.class.getName()))
                .respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK,
                        intentNovaTarefa));

        //act
        Espresso.onView(ViewMatchers.withId(R.id.fabNovaTarefa)).perform(ViewActions.click());

        // assert
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.txtDescricao),
                ViewMatchers.withText("Tarefa 1")))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
