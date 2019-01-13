package br.com.belapp.belapp.test;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.activities.AgendamentosActivity;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;

public class CancelamentoAgendamentoSteps  extends DefaultTest {

    @Rule
    public ActivityTestRule<AgendamentosActivity> activityTestRule = new ActivityTestRule<>(AgendamentosActivity.class);

    @Before("@cancelar-agendamento-feature")
    public void setup() {
        activityTestRule.launchActivity(new Intent());
        setActivity(activityTestRule.getActivity());
//        preencherCampoEditText(R.id.edtEmail,"flp.d@hotmail.com");
//        preencherCampoEditText(R.id.edtSenha,"1234567");
//        apertarBotao(R.id.btnLogar);
    }

    @After("@cancelar-agendamento-feature")
    public void tearDown() {
        getAtualActivity().finishAffinity();
        activityTestRule.finishActivity();
    }

    @Dado("^que estou na tela de agendamentos")
    public void iamOnAgendamentosScreen() {
        assertNotNull(getActivity());
    }

    @Quando("^eu seleciono um agendamento$")
    public void euSelecionoAgendamento() {
        esperar(5000);
        selecionarItemReciclerView(R.id.rvAgendamentos, 0);
    }

    @E("^eu aperto em desistir")
    public void apertoCancelar() {
        apertarBotao(R.id.btnCancelarAgendamento);
    }

    @E("^eu confirmo a ação$")
    public void confirmoCancelamento() {
        onView(withText((String) getActivity().getText(R.string.app_sim))).perform(click());
    }

    @E("^eu não confirmo a ação$")
    public void naoConfirmoCancelamento() {
        onView(withText((String) getActivity().getText(R.string.app_nao))).perform(click());
    }

    @Entao("^devo ver a mensagem, Agendamento cancelado com sucesso!$")
    public void devoVerMensagemSucesso() {
        verificarMensagemToast(getActivity().getString(R.string.sucess_agendamento_cancelado));
    }


    @Entao("^devo ver a mensagem, Agendamento não cancelado$")
    public void devoVerMensagemNaoCancelado() {
        verificarMensagemToast(getActivity().getString(R.string.info_agendamento_nao_cancelado));
    }
}
