package br.com.belapp.belapp.test;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import br.com.belapp.belapp.R;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.junit.Rule;

import br.com.belapp.belapp.activities.RecuperaSenhaActivity;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

import static junit.framework.TestCase.assertNotNull;

public class RecuperarActivitySteps extends DefaultTest{

    @Rule
    public ActivityTestRule<RecuperaSenhaActivity> activityTestRule = new ActivityTestRule<>(RecuperaSenhaActivity.class);

    @Before("@recupera-senha-feature")
    public void setup(){
        activityTestRule.launchActivity(new Intent());
        setActivity(activityTestRule.getActivity());
    }

    @After("@recupera-senha-feature")
    public void tearDown(){
        getAtualActivity().finish();
        activityTestRule.finishActivity();
    }

    @Dado("^que estou na tela Recupera Senha$")
    public void estouNaTelaRecuperarSenha(){
        assertNotNull(getActivity());
    }

    @Quando("^eu forneço um e-mail inválido$")
    public void fornecoUmEmailInvalido(){
        preencherCampoEditText(R.id.etEmailRec, "df@as.c");
    }

    @Quando("^eu forneço um e-mail válido$")
    public void fornecoUmEmailValido(){
        preencherCampoEditText(R.id.etEmailRec, "moraesdanillo10@gmail.com");
    }

    @Quando("^eu não forneço um e-mail")
    public void naoForcecoEmail(){
        preencherCampoEditText(R.id.etEmailRec,"");
    }

    @E("^eu clico no botão Solicitar$")
    public void apertoSolicitar(){
        apertarBotao(R.id.btnSolicitarRec);
    }

    @Entao("^devo ver uma mensagem me informando que houve falha$")
    public void verMensagemHouveFalha(){
        verificarMensagemToast((String) getActivity().getText(R.string.falha_recuperar_senha_invalido));
    }

    @Entao("^devo ver uma mensagem me informando que o e-mail de recuperação foi enviado$")
    public void verMensagemEmailEnviado(){
        verificarMensagemToast((String) getActivity().getText(R.string.recuperar_senha_iniciada));
    }

    @Entao("^devo ver uma mensagem me informando para digitar um e-mail$")
    public void verMensagemDigiteEmail(){
        verificarMensagemToast((String) getActivity().getText(R.string.recuperar_digite_email));
    }
}
