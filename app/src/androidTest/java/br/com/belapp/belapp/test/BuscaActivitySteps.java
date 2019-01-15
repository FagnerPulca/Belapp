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

import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.matcher.ViewMatchers.withText;
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
        esperar(4000);
        assertNotNull(getActivity());
    }

    @Quando("^Eu coloco a cidade e não o estabelecimento$")
    public void euColocoACidade(){
        preencherCampoEditText(R.id.etEstabelecimento, "");
        preencherCampoEditText(R.id.etEndereco, "Garanhuns");
    }

    @Quando("^Eu coloco o estabelecimento e não a cidade$")
    public void euColocoOServico(){
        preencherCampoEditText(R.id.etEndereco, "");
        preencherCampoEditText(R.id.etEstabelecimento, "beauty");
    }

    @Quando("^Eu coloco o estabelecimento e a cidade$")
    public void euColocoOsDois(){
        preencherCampoEditText(R.id.etEndereco, "Garanhuns");
        preencherCampoEditText(R.id.etEstabelecimento, "Fagner");
    }

    @Quando("^Eu clico em buscar sem preencher os campos$")
    public void euColocoNenhum(){
        preencherCampoEditText(R.id.etEndereco, "");
        preencherCampoEditText(R.id.etEstabelecimento, "");
    }

    @Quando("^Eu defino um preço$")
    public void definoUmPreco(){
        preencherCampoEditText(R.id.etPreco, "19");
    }

    @Quando("^Eu digito um serviço$")
    public void digitoUmServico(){
        preencherCampoEditText(R.id.etServCat, "corte");
    }

    @E("^Eu clico em buscar$")
    public void clicoEmBuscar(){
        apertarBotao(R.id.btnBuscar);
    }

    @Entao("^Eu devo ver os salões retornados$")
    public void verRetorno(){
        esperar(2000);
        onView(withText(getActivity().getString(R.string.title_activity_estabelecimentos)));

    }

    @Entao("^Devo ver uma mensagem dizendo para digitar algum dado$")
    public void verMensagem(){
        verificarMensagemToast(getAtualActivity().getString(R.string.digite_algum_dado));
    }

    @Quando("^Digito um serviço de estabelecimento$")
    public void digitoServicoEstabelacimento(){ preencherCampoEditText(R.id.etServCat, "manicure");}

    @E ("^Digito uma data disponivel$")
    public void digitoUmaDataDisponivel(){
        if(getAtualActivity() instanceof TelaBuscaActivity){
            TelaBuscaActivity activityAgendamento = (TelaBuscaActivity) getAtualActivity();
            activityAgendamento.setMetDataBusca("24/01/2019");
        }
    }

    @E ("^Digito uma data indisponivel$")
    public void digitoUmaDataIndisponivel(){
        if(getAtualActivity() instanceof TelaBuscaActivity){
            TelaBuscaActivity activityAgendamento = (TelaBuscaActivity) getAtualActivity();
            activityAgendamento.setMetDataBusca("25/01/2019");
        }
    }

    @Entao("^Eu devo ver o estabelecimento$")
    public void verEstabelecimento(){
        esperar(2000);
        //onView(withText(getActivity().getString(R.string.title_activity_estabelecimentos)));

    }

    @Entao("^Eu não devo ver nenhum estabelecimento$")
    public void naoVerEstabelecimento(){
        esperar(2000);
       // onView(withText(getActivity().getString(R.string.title_activity_estabelecimentos)));
    }

}
