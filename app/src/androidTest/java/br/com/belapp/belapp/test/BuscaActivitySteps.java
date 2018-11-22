package br.com.belapp.belapp.test;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.activities.TelaBuscaActivity;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.es.Dado;
import cucumber.api.java.it.E;
import cucumber.api.java.it.Quando;
import cucumber.api.java.pt.Entao;

import static junit.framework.TestCase.assertNotNull;


public class BuscaActivitySteps extends DefaultTest {

    @Rule
    public ActivityTestRule<TelaBuscaActivity> activityTestRule = new ActivityTestRule<>(TelaBuscaActivity.class);


    @Before("@busca-feature")
    public void setup(){
        activityTestRule.launchActivity(new Intent());
        setActivity(activityTestRule.getActivity());
    }

    @After("@busca-feature")
    public void tearDown() {
        getAtualActivity().finish();
        activityTestRule.finishActivity();
    }

    @Dado("^Eu estou na tela de busca$")
    public void euEstouNaTelaDeBusca(){
        assertNotNull(getActivity());
    }

    @Quando("^Eu coloco a cidade e não o serviço$")
    public void euColocoACidade(){
        preencherCampoEditText(R.id.etServico,"");
        preencherCampoEditText(R.id.etCidade,"Garanhuns");
    }

    @Quando("^Eu coloco o serviço e não a cidade$")
    public void euColocoOServico(){
        preencherCampoEditText(R.id.etCidade,"");
        preencherCampoEditText(R.id.etServico,"Corte simples");
    }

    @Quando("^Eu coloco o serviço e a cidade$")
    public void euColocoOsDois(){
        preencherCampoEditText(R.id.etCidade, "Garanhuns");
        preencherCampoEditText(R.id.etServico,"Corte simples");
    }

    @Quando("^Eu clico em buscar sem preencher os campos$")
    public void euColocoNenhum(){
        preencherCampoEditText(R.id.etCidade, "");
        preencherCampoEditText(R.id.etServico,"");
    }

    @E("^Eu clico em buscar$")
    public void clicoEmBuscar(){
        apertarBotao(R.id.btnBuscar);
    }

    @Entao("^Eu devo ver os salões retornados$")
    public void verRetorno(){
        verificarMensagemToast(getActivity().getString(R.string.resultados));
    }

    @Entao("^Devo ver uma mensagem dizendo para digitar algum dado$")
    public void verMensagem(){
        verificarMensagemToast(getActivity().getString(R.string.digite_algum_dado));
    }
}
