package com.example.jonathan.todoapp;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.example.jonathan.todoapp.feature.inclusao.NovaTarefaActivity;

import org.junit.Assert;
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

    @Test
    public void aoClicarEmIncluir_deveFinalizarActivity() {
        rule.launchActivity(new Intent());
        Espresso.onView(ViewMatchers.withId(R.id.edtNomeTarefa))
                .perform(ViewActions.typeText("estudar espresso"),
                        ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.btnIncluir))
                .perform(ViewActions.click());

        Assert.assertTrue(rule.getActivity().isFinishing());
    }


}