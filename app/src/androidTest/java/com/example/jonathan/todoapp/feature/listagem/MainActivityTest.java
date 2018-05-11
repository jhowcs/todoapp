package com.example.jonathan.todoapp.feature.listagem;

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

import com.example.jonathan.todoapp.customassertions.PaintFlagTextAssertion;
import com.example.jonathan.todoapp.R;
import com.example.jonathan.todoapp.customassertions.RecyclerViewItemCountAssertion;
import com.example.jonathan.todoapp.TarefaModelo;
import com.example.jonathan.todoapp.repository.local.DatabaseConcrete;
import com.example.jonathan.todoapp.repository.local.TarefaDao;
import com.example.jonathan.todoapp.feature.inclusao.NovaTarefaActivity;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    public static final String TAREFA_1 = "Tarefa 1";
    public static final String TAREFA_2 = "Tarefa 2";
    public static final String TAREFA_3 = "Tarefa 3";
    public static final String TAREFA_4 = "Tarefa 4";
    public static final String TAREFA_5 = "Tarefa 5";
    public static final String TAREFA_6 = "Tarefa 6";
    public static final String TAREFA_7 = "Tarefa 7";

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

        dao.insertAll(new TarefaModelo(TAREFA_1, false),
                new TarefaModelo(TAREFA_2, false),
                new TarefaModelo(TAREFA_3, true),
                new TarefaModelo(TAREFA_4, false),
                new TarefaModelo(TAREFA_5, false),
                new TarefaModelo(TAREFA_6, false));
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
        final int qtdeItensLista = 6;
        iniciarActivity();
        Espresso.onView(ViewMatchers.withId(R.id.rvListaTarefa))
                .check(RecyclerViewItemCountAssertion.withItemCount(qtdeItensLista));
    }

    @Test
    public void aoIncluirNovaTarefa_deveExibilaNaListagem() {
        final int qtdeItensLista = 7;
        iniciarActivity();

        Intent intentNovaTarefa = new Intent();
        intentNovaTarefa.putExtra(NovaTarefaActivity.CHAVE_TAREFA,
                new TarefaModelo(TAREFA_7, false));

        Intents.intending(IntentMatchers.hasComponent(NovaTarefaActivity.class.getName()))
                .respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK,
                        intentNovaTarefa));
        Espresso.onView(ViewMatchers.withId(R.id.fabNovaTarefa)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.rvListaTarefa))
                .check(RecyclerViewItemCountAssertion.withItemCount(qtdeItensLista));

    }

    @Test
    public void aoRealizarSwipeParaEsquerda_deveRemoverTarefaDaListagem() {
        final int qtdeItensLista = 5;
        iniciarActivity();
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.txtDescricao),
                ViewMatchers.withText(TAREFA_4)))
                .perform(ViewActions.swipeLeft());
        Espresso.onView(ViewMatchers.withId(R.id.rvListaTarefa))
                .check(RecyclerViewItemCountAssertion.withItemCount(qtdeItensLista));
    }

    @Test
    public void aoMarcarTarefaComoFeita_deveMarcarTextoComLinha() {
        iniciarActivity();
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.chkExecutado),
                ViewMatchers.hasSibling(ViewMatchers.withText(TAREFA_4))))
                .perform(ViewActions.click());

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.txtDescricao),
                ViewMatchers.hasSibling(ViewMatchers.withText(TAREFA_4))))
                .check(PaintFlagTextAssertion.withStrikeThroughFlag());
    }

    @Test
    public void aoDesmarcarTarefaComoFeita_deveRemoverLinhaNoMeioDoTexto() {
        iniciarActivity();
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.chkExecutado),
                ViewMatchers.hasSibling(ViewMatchers.withText(TAREFA_3))))
                .perform(ViewActions.click());

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.txtDescricao),
                ViewMatchers.hasSibling(ViewMatchers.withText(TAREFA_3))))
                .check(PaintFlagTextAssertion.withLinearFlag());
    }

    @Test
    public void aoClicarEmUmaTarefa_deveVerificarChamadaAIntentParaAlteracao() {
        iniciarActivity();
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.txtDescricao),
                ViewMatchers.withText(TAREFA_4)))
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
