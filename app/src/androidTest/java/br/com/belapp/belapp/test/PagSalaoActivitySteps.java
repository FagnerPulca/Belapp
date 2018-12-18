package br.com.belapp.belapp.test;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.activities.PagSalaoActivity;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.it.E;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;

import static junit.framework.TestCase.assertNotNull;

public class PagSalaoActivitySteps extends DefaultTest {

    @Rule

    public ActivityTestRule<PagSalaoActivity> activityTestRule = new ActivityTestRule<>(PagSalaoActivity.class);

    @Before("@pag-feature")
    public void setup(){
        Intent intent = new Intent();
        intent.putExtra("salao","-LSHO7lWb_XYNoeN0zCk");
        intent.putExtra("nome","Imperador Darth Vader");
        activityTestRule.launchActivity(intent);
        setActivity(activityTestRule.getActivity());
        esperar(3000);
    }

    @After("@pag-feature")
    public void tearDown() {
        getAtualActivity().finish();
        activityTestRule.finishActivity();
        esperar(3000);
    }

    @Dado("^que eu estou na tela de detalhes do salao$")
    public void estouNaTelaPag() {
        assertNotNull(getActivity());
    }


    @E("^eu clico em favoritar$")
    public void clicoEmFAVORITAR(){
        apertarBotao(R.id.star_button);
    }

    @Entao("^Eu devo ver uma animacao indicando que foi favoritado$")
    public void verMensagemFavoritar()throws Throwable {
        verificarMensagemToast((String)getActivity().getText(R.string.adicionando_favorito));
    }

    @Entao("^Eu devo ver uma animacao indicando que foi desfavoritado$")
    public void verMensagemDesfavoritar() throws Throwable {
        verificarMensagemToast((String)getActivity().getText(R.string.retirando_favoritoo));

    }

}
