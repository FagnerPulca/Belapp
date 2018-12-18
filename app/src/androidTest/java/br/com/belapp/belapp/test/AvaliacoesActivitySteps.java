package br.com.belapp.belapp.test;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import static android.support.test.espresso.Espresso.onView;

import org.junit.Rule;

import br.com.belapp.belapp.activities.AvaliarActitivy;
import br.com.belapp.belapp.activities.PagAvaliacaoActivity;
import br.com.belapp.belapp.activities.PagSalaoActivity;
import br.com.belapp.belapp.activities.SaloesActivity;
import br.com.belapp.belapp.activities.TelaBuscaActivity;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import android.app.Activity;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.view.ViewGroup;

import org.junit.Assert;
import org.junit.Rule;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.activities.TelaBuscaActivity;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Então;
import cucumber.api.java.pt.Quando;

import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class AvaliacoesActivitySteps extends DefaultTest{

    @Rule
    public ActivityTestRule<PagSalaoActivity> activityTestRule = new ActivityTestRule<>(PagSalaoActivity.class);

    @Before("@avaliacao-feature")
    public void setup(){
        Intent intent = new Intent();
        intent.putExtra("salao", "-LS54ly9L9y6RsIuOGid");
        intent.putExtra("nome", "Salão Beauty");
        activityTestRule.launchActivity(intent);
        setActivity(activityTestRule.getActivity());
        esperar(5000);
    }

    @After("@avaliacao-feature")
    public void tearDown() {
        getAtualActivity().finish();
        activityTestRule.finishActivity();
    }

    @Dado("^eu estou na tela do salão beauty$")
    public void estouNaTelaDoSalao(){assertNotNull(getActivity());}

    @Quando("^eu clico no botão avaliações$")
    public void clicoAvaliacoes(){
        apertarBotao(R.id.ibAvaliacoes);
        esperar(3000);
    }

    @Então("^eu vejo os clientes que avaliaram$")
    public void verAvaliacoes(){
        esperar(3000);
        onView(withText("Não há avaliações")).check(doesNotExist());
    }

    @Dado("^eu estou na tela do Imperador Darth Vader$")
    public void telaImperadorDarth(){
        tearDown();
        Intent intent = new Intent();
        intent.putExtra("salao", "-LSHO7lWb_XYNoeN0zCk");
        intent.putExtra("nome", "Imperador Darth Vader");
        activityTestRule.launchActivity(intent);
        setActivity(activityTestRule.getActivity());
        esperar(5000);
    }

    @Então("^não há avaliações para ver$")
    public void semAvaliacoes(){
        esperar(3000);
        onView(withText("Não há avaliações")).check(matches(isDisplayed()));
    }

}
