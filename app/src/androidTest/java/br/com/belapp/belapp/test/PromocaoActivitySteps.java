package br.com.belapp.belapp.test;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;


import org.junit.Rule;

import java.util.ArrayList;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.activities.PromocoesActivity;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.it.Quando;
import cucumber.api.java.it.E;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;

import static junit.framework.TestCase.assertNotNull;

public class PromocaoActivitySteps extends DefaultTest {

     @Rule
     public ActivityTestRule<PromocoesActivity> activityTestRule = new ActivityTestRule<>(PromocoesActivity.class);

    @Before("@promocao-feature")
    public void setup(){


        Intent intent = new Intent();
        intent.putExtra("salao","-LS54ly9L9y6RsIuOGid");
        intent.putExtra("nome","Salão Beautyr");
        activityTestRule.launchActivity(intent);
        logarPorEmail("fulano@teste.com","123456");
        setActivity(activityTestRule.getActivity());

        esperar(3000);

    }

    @After("@promocao-feature")
    public void tearDown() {
       getAtualActivity().finish();
       activityTestRule.finishActivity();
    }

    @Dado("^Eu estou na tela de promoção$")
    public void estouNaTelaPromocoes() { assertNotNull(getActivity());}




    @Quando("^Eu clico no item de promocao$")
    public void eu_clico_no_item_de_promocao(){
        selecionarItemReciclerView(R.id.rvPromocoes,0);
    }

    @Entao("^Eu devo ver o salõe que oferece a promoção$")
    public void verMensagemFavoritar()throws Throwable {
        verificarMensagemToast((String)getActivity().getText(R.string.title_activity_salao));
    }



}
