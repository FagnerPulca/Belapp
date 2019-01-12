package br.com.belapp.belapp.test;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import br.com.belapp.belapp.R;
import br.com.belapp.belapp.activities.InicialActivity;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;

public class AgendamentoActivitySteps extends DefaultTest{
    @Rule
    public ActivityTestRule<InicialActivity> activityTestRule = new ActivityTestRule<>(InicialActivity.class);

    @Before("@agendamento-feature")
    public void setup() {
        activityTestRule.launchActivity(new Intent());
        setActivity(activityTestRule.getActivity());
        esperar(2000);
    }

    @After("@agendamento-feature")
    public void tearDown() {
        // Conclua esta activity, bem como todas as activity imediatamente abaixo dela na task stack
        // atual que tenham a mesma afinidade.
        getAtualActivity().finishAffinity();
        esperar(600);
        activityTestRule.finishActivity();
    }

    @Given("^que estou na tela de inicial")
    public void iamOnTelaInicial() {
        assertNotNull(getActivity());
    }

    @Quando("^eu busco por um servico$")
    public void euBuscoServico() {
        apertarBotao(R.id.ibCabelo);
        esperar(2000);
        selecionarItemReciclerView(R.id.rvSaloes, 1);
        esperar(2000);
        selecionarItemReciclerView(R.id.rvServicos, 0);
        esperar(2000);
        selecionarItemReciclerView(R.id.rvProfissionais, 0);
        esperar(2000);

    }
    @Quando("^eu busco por um servico do estabelecimento$")
    public void euBuscoServico2() {
        apertarBotao(R.id.ibUnha);
        esperar(2000);
        selecionarItemReciclerView(R.id.rvSaloes, 1);
        esperar(2000);
        selecionarItemReciclerView(R.id.rvServicos, 0);
        esperar(2000);
        selecionarItemReciclerView(R.id.rvProfissionais, 0);
        esperar(2000);

    }

    @Quando("^eu escolho um profissional$")
    public void euEscolhoProfissional() {
        selecionarItemReciclerView(R.id.rvProfissionais, 0);
        esperar(2000);
        apertarBotao(R.id.btnAgendar);
    }

    @Quando("^eu informo uma data valida$")
    public void euInformoUmaDataValida() {
        apertarBotao(R.id.edtData);
//        onView(withText("27")).perform(click());
        onView(withText("OK")).perform(click());
    }

    @E("^eu informo uma data invalida$")
    public void euInformoUmaDataInvalida() {
        apertarBotao(R.id.edtData);
//        onView(withText("27")).perform(click());
        esperar(660);
        onView(withText("OK")).perform(click());
    }

    @Quando("^eu seleciono um horario valido$")
    public void euInformoUmahorarioValido() {
        selecionarItemSpinnerByPosition(R.id.spHorariosAgendar, 1);
    }

    @E("^eu aperto em agendar$")
    public void apertoAgendar() {
        apertarBotao(R.id.btnAgendar);
    }

    @Entao("^devo ver a mensagem, Agendamento realizado com sucesso!$")
    public void devoVerAMensagemAgendadoComSucesso() {
        verificarMensagemToast(getActivity().getString(R.string.sucess_agendamento_realizado));
    }
    @Entao("^devo ver a mensagem, Selecione um horario de agendamento!$")
    public void devoVerAMensagemInformeHorario() {
        verificarMensagemToast(getActivity().getString(R.string.error_selecione_um_horario));
    }
    @Entao("^devo ver a mensagem, O estabelecimento estará fechado na data selecionada$")
    public void devoVerAMensagemEstabelecimentoNaoFunciona() {
        verificarMensagemToast(getActivity().getString(R.string.error_estabelecimento_fechado_na_data));
    }

}
