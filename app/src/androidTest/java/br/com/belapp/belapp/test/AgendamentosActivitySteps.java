package br.com.belapp.belapp.test;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.activities.AgendamentosActivity;
import br.com.belapp.belapp.activities.AgendarServicoActivity;
import br.com.belapp.belapp.utils.DateUtils;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

import static junit.framework.TestCase.assertNotNull;

public class AgendamentosActivitySteps extends DefaultTest{
    @Rule
    public ActivityTestRule<AgendamentosActivity> activityTestRule = new ActivityTestRule<>(AgendamentosActivity.class);

    @Before("@reagendamento-feature")
    public void setup() {
        activityTestRule.launchActivity(new Intent());
        setActivity(activityTestRule.getActivity());
        esperar(2000);
    }

    @After("@reagendamento-feature")
    public void tearDown() {
        getAtualActivity().finishAffinity();
        esperar(600);
        activityTestRule.finishActivity();
    }

    @Dado("^que estou na tela Minha Agenda$")
    public void queEstouNaTelaMinhaAgenda(){
        esperar(3000);
        assertNotNull(getActivity());
    }
//
    @Quando("^eu informo a data invalida$")
    public void euInformoDataInvalida() {
        if(getAtualActivity() instanceof AgendarServicoActivity){
            AgendarServicoActivity activityAgendamento = (AgendarServicoActivity) getAtualActivity();
            activityAgendamento.setData(DateUtils.gerarDataInvalida(activityAgendamento.getDiasFuncionamento()));
        }
    }


    @E("^eu aperto em reagendar$")
    public void euApertoEmReagendar() throws Throwable {
        apertarBotao(R.id.btnEditarAgendamento);
    }

    @Quando("^eu seleciono o agendamento$")
    public void euSelecionoUmAgendamento() {
        // Write code here that turns the phrase above into concrete actions
        esperar(5000);
        selecionarItemReciclerView(R.id.rvAgendamentos, 0);
    }

    @Entao("^devo ver a mensagem na tela, O estabelecimento estará fechado na data selecionada$")
    public void devoVerAMensagemEstabelecimentoNaoFunciona() {
        verificarMensagemToast(getActivity().getString(R.string.error_estabelecimento_fechado_na_data));
    }
    @Entao("^devo ver a mensagem na tela, Agendamento realizado com sucesso!$")
    public void devoVerAMensagemAgendadoComSucesso() {
        verificarMensagemToast(getActivity().getString(R.string.sucess_agendamento_realizado));
    }

    @Quando("^eu aperto o botão agendar$")
    public void apertoAgendar() {
        apertarBotao(R.id.btnAgendar);
    }

    @Quando("^eu informo a data valida$")
    public void eu_informo_a_data_valida() throws Throwable {
        if(getAtualActivity() instanceof AgendarServicoActivity){
            AgendarServicoActivity activityAgendamento = (AgendarServicoActivity) getAtualActivity();
            activityAgendamento.setData(
                    DateUtils.getSomaDiasComDataEspecifica(7,activityAgendamento.getDataAgendamento()));
        }
    }

    @Quando("^eu seleciono uma hora válida$")
    public void euInformoUmahorarioValido() {
        selecionarItemSpinnerByPosition(R.id.spHorariosAgendar, 1);
    }

    @E("^eu seleciono o serviço e o profissional$")
    public void euSelecionoServiçoProfissional() {
        esperar(2000);
        selecionarItemReciclerView(R.id.rvServicos, 0);
        esperar(3000);
        selecionarItemReciclerView(R.id.rvProfissionais, 0);
        esperar(2000);
        apertarBotao(R.id.btnAgendar);
    }



    @Quando("^eu aperto em reagendar e apertar em alterar serviço$")
    public void euApertoEmReagendarApertarAlterarServiço() {
        apertarBotao(R.id.btnEditarAgendamento);
        esperar(1000);
        apertarBotao(R.id.btnAlterarServico);
    }
}
