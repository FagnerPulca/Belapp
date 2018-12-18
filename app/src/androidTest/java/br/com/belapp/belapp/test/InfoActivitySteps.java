package br.com.belapp.belapp.test;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.activities.PagSalaoActivity;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import static junit.framework.TestCase.assertNotNull;

public class InfoActivitySteps extends DefaultTest {

    @Rule
    public ActivityTestRule<PagSalaoActivity> activityTestRule = new ActivityTestRule<>(PagSalaoActivity.class);

    @Before("@info-feature")
    public void setup() {
        Intent intent = new Intent();
        intent.putExtra("salao","-LS54ly9L9y6RsIuOGid");
        activityTestRule.launchActivity(intent);
        setActivity(activityTestRule.getActivity());
        esperar(3000);
    }

    @After("@info-feature")
    public void tearDown() {
        getAtualActivity().finish();
        activityTestRule.finishActivity();
    }

    @Dado("^que estou na tela de algum salão$")
    public void estouNaTelaDeAlgumSalao(){
        assertNotNull(getActivity());
    }

    @Quando("^clico no botão informações$")
    public void clicoDoBotaoInformacoes(){
        apertarBotao(R.id.ibInformacoes);
    }
    @Entao("^a tela de informações do salão se abre$")
    public void abreTelaInformacoes(){
        esperar(2000);
        verificarMensagemToast("Informações");
    }
}
