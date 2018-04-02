package com.example.jonathan.todoapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.example.jonathan.todoapp.data.DatabaseConcrete;
import com.example.jonathan.todoapp.data.TarefaDao;
import com.example.jonathan.todoapp.feature.inclusao.NovaTarefaActivity;
import com.example.jonathan.todoapp.feature.listagem.MainActivity;

import org.hamcrest.Matchers;
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
                new TarefaModelo("Tarefa 3", true),
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

    @Test
    public void aoIncluirNovaTarefa_deveExibilaNaListagem() {
        iniciarActivity();

        Intent intentNovaTarefa = new Intent();
        intentNovaTarefa.putExtra(NovaTarefaActivity.CHAVE_TAREFA,
                new TarefaModelo("Tarefa 7", false));

        Intents.intending(IntentMatchers.hasComponent(NovaTarefaActivity.class.getName()))
                .respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK,
                        intentNovaTarefa));
        Espresso.onView(ViewMatchers.withId(R.id.fabNovaTarefa)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.rvListaTarefa))
                .check(RecyclerViewItemCountAssertion.withItemCount(7));

    }

    @Test
    public void aoRealizarSwipeParaEsquerda_deveRemoverTarefaDaListagem() {
        iniciarActivity();
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.txtDescricao),
                ViewMatchers.withText("Tarefa 4")))
                .perform(ViewActions.swipeLeft());
        Espresso.onView(ViewMatchers.withId(R.id.rvListaTarefa))
                .check(RecyclerViewItemCountAssertion.withItemCount(5));
    }

    @Test
    public void aoMarcarTarefaComoFeita_deveMarcarTextoComLinha() {
        iniciarActivity();
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.chkExecutado),
                ViewMatchers.hasSibling(ViewMatchers.withText("Tarefa 4"))))
                .perform(ViewActions.click());

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.txtDescricao),
                ViewMatchers.hasSibling(ViewMatchers.withText("Tarefa 4"))))
                .check(PaintFlagTextAssertion.withStrikeThroughFlag());
    }

    @Test
    public void aoDesmarcarTarefaComoFeita_deveRemoverLinhaNoMeioDoTexto() {
        iniciarActivity();
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.chkExecutado),
                ViewMatchers.hasSibling(ViewMatchers.withText("Tarefa 3"))))
                .perform(ViewActions.click());

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.txtDescricao),
                ViewMatchers.hasSibling(ViewMatchers.withText("Tarefa 3"))))
                .check(PaintFlagTextAssertion.withLinearFlag());
    }

    @Test
    public void aoClicarEmUmaTarefa_deveVerificarChamadaAIntentParaAlteracao() {
        iniciarActivity();
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.txtDescricao),
                ViewMatchers.withText("Tarefa 4")))
                .perform(ViewActions.click());
        Intents.intended(IntentMatchers.hasComponent(NovaTarefaActivity.class.getName()));
    }

    @Test
    public void aoAlterarTarefa_deveExibirTarefaAlteradaNaListagem() {
        Intent intentTarefaAlterada = new Intent();
        String descricaoTarefaAlterada = "Tarefa 4 alterada";
        final int posicaoAlterada = 3;

        iniciarActivity();

        intentTarefaAlterada.putExtra(NovaTarefaActivity.CHAVE_POSICAO, posicaoAlterada);
        intentTarefaAlterada.putExtra(NovaTarefaActivity.CHAVE_TAREFA,
                new TarefaModelo(descricaoTarefaAlterada, false));

        Intents.intending(IntentMatchers.hasComponent(NovaTarefaActivity.class.getName()))
                .respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK,
                        intentTarefaAlterada));

        Espresso.onView(ViewMatchers.withId(R.id.rvListaTarefa))
                .perform(RecyclerViewActions.actionOnItemAtPosition(posicaoAlterada, ViewActions.click()));

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.txtDescricao),
                ViewMatchers.withText(descricaoTarefaAlterada)))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
