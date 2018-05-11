package com.example.jonathan.todoapp.feature.listagem;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.jonathan.todoapp.R;
import com.example.jonathan.todoapp.TarefaModelo;
import com.example.jonathan.todoapp.customassertions.RecyclerViewItemCountAssertion;
import com.example.jonathan.todoapp.repository.local.DatabaseConcrete;
import com.example.jonathan.todoapp.repository.local.TarefaDao;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityBuscaTest {

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, false);

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

    @Test
    public void aoBuscarPorTarefaExistente_deveExibirTarefa() {
        rule.launchActivity(null);
        Espresso.onView(ViewMatchers.withId(R.id.menu_pesquisar)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.search_src_text))
                .perform(ViewActions.typeText("Tarefa 6"));

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.txtDescricao),
                ViewMatchers.withText("Tarefa 6")))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void aoBuscarPorTarefaExistenteNaoRespeitandoCase_deveExibirTarefa() {
        rule.launchActivity(null);
        Espresso.onView(ViewMatchers.withId(R.id.menu_pesquisar)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.search_src_text))
                .perform(ViewActions.typeText("tArEfA 6"));

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.txtDescricao),
                ViewMatchers.withText("Tarefa 6")))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void aoBuscarPorTarefaInexistente_deveExibirEmptyState() {
        rule.launchActivity(null);
        Espresso.onView(ViewMatchers.withId(R.id.menu_pesquisar)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.search_src_text))
                .perform(ViewActions.typeText("tarefa nao existente"));

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.txtMensagem),
                ViewMatchers.withText("nenhuma tarefa cadastrada")))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void aoLimparCampoDePesquisa_deveExibirListaOriginal() {
        rule.launchActivity(null);
        Espresso.onView(ViewMatchers.withId(R.id.menu_pesquisar)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.search_src_text))
                .perform(ViewActions.typeText("Tarefa 6"));

        Espresso.onView(ViewMatchers.withId(R.id.search_close_btn)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.rvListaTarefa))
                .check(RecyclerViewItemCountAssertion.withItemCount(6));
    }
}
