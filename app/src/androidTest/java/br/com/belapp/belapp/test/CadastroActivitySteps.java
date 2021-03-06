package br.com.belapp.belapp.test;

import android.app.Activity;
import android.content.Intent;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.activities.CadastroBasicoActivity;
import br.com.belapp.belapp.utils.StringUtils;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;
import static junit.framework.TestCase.assertNotNull;

public class CadastroActivitySteps extends DefaultTest {

    @Rule
    public ActivityTestRule<CadastroBasicoActivity> activityTestRule = new ActivityTestRule<>(CadastroBasicoActivity.class);

    @Before("@cadastro-feature")
    public void setup() {
        activityTestRule.launchActivity(new Intent());
        setActivity(activityTestRule.getActivity());
        esperar(3000);
    }

    @After("@cadastro-feature")
    public void tearDown() {
        if(((String) getAtualActivity().getTitle()).contains("Cadastro")){
            getAtualActivity().finish();
            setActivity(activityTestRule.getActivity());
            esperar(1000);
        }
        else {
            //dadosAnteriores();
            esperar(1000);
            activityTestRule.finishActivity();
        }
    }

    @Dado("^que estou na tela de Cadastro$")
    public void estouNaTelaCadastro() {
        assertNotNull(getActivity());
    }

    @Quando("^eu preencho todos os campos, exeto o telefone$")
    public void euNaoPreenchoOCampoTelefone() {
        preencherCampoEditText(R.id.etNomeCadastro, "Maria Jose");
        preencherCampoEditText(R.id.etEmailCadastro,"teste3@teste.com");
        preencherCampoEditText(R.id.etConfirmacaoEmailCadastro,"teste3@teste.com");
        preencherCampoEditText(R.id.etSenhaCadastro,"8123456");
        preencherCampoEditText(R.id.etConfirmcaoSenhaCadastro,"8123456");
        preencherCampoEditText(R.id.etTelefoneCadastro,"");
    }

    @E("^eu aperto em salvar$")
    public void apertoBotaoCadastrarSemTelefone() {
        apertarBotao(R.id.btnCadastrar);
    }

    @Entao("^devo ver uma mensagem que diga que campo telefone nao pode ser deixado em branco$")
    public void devoVerAMensagemTelefoneNaoPodeSerDeixadoVazio() {
        verificarMensagemToast((String)getActivity().getText(R.string.erro_teefone));
    }

    @Quando("^eu preencho todos os campos, exeto o nome$")
    public void euNaoPreenchoOCampoNome() {
        preencherCampoEditText(R.id.etNomeCadastro, "");
        preencherCampoEditText(R.id.etEmailCadastro,"teste3@teste.com");
        preencherCampoEditText(R.id.etConfirmacaoEmailCadastro,"teste3@teste.com");
        preencherCampoEditText(R.id.etTelefoneCadastro,"124659879");
        preencherCampoEditText(R.id.etSenhaCadastro,"8123456");
        preencherCampoEditText(R.id.etConfirmcaoSenhaCadastro,"8123456");


    }


    @Entao("^devo ver uma mensagem que diga que campo nome nao pode ser deixado em branco$")
    public void devoVerAMensagemNomeNaoPodeSerDeixadoVazio2() {
        verificarMensagemToast((String)getActivity().getText(R.string.erro_Nome));
    }

    @Quando("^eu preencho todos os campos, exeto o Email")
    public void euNaoPreenchoOCampoEmail() {
        preencherCampoEditText(R.id.etNomeCadastro, "Maria Jose");
        preencherCampoEditText(R.id.etEmailCadastro,"");
        preencherCampoEditText(R.id.etConfirmacaoEmailCadastro,"");
        preencherCampoEditText(R.id.etTelefoneCadastro,"124659879");
        preencherCampoEditText(R.id.etSenhaCadastro,"8123456");
        preencherCampoEditText(R.id.etConfirmcaoSenhaCadastro,"8123456");

    }


    @Entao("^devo ver uma mensagem que diga que campo e-mail nao pode ser deixado em branco$")
    public void ddevo_ver_uma_mensagem_que_informe_que_campo_e_mail_nao_pode_ser_deixado_em_branco() {
        verificarMensagemToast((String)getActivity().getText(R.string.erro_Email));
    }
    @Quando("^eu preencho todos os campos, exeto a senha$")
    public void euNaoPreenchoOCampoSenha() {
        preencherCampoEditText(R.id.etNomeCadastro, "Maria Jose");
        preencherCampoEditText(R.id.etEmailCadastro,"teste3@teste.com");
        preencherCampoEditText(R.id.etConfirmacaoEmailCadastro,"teste3@teste.com");
        preencherCampoEditText(R.id.etTelefoneCadastro,"124659879");
        preencherCampoEditText(R.id.etSenhaCadastro,"");
        preencherCampoEditText(R.id.etConfirmcaoSenhaCadastro,"");

    }


    @Entao("^devo ver uma mensagem que diga que campo senha nao pode ser deixado em branco$")
    public void devoVerAMensagemSenhaNaoPodeSerDeixadoVazio() {
        verificarMensagemToast((String)getActivity().getText(R.string.erro_Senha));
    }
    @Quando("^eu preencho todos os campos corretamente$")
    public void euNaoPreenchoOCampo() {
        String email = StringUtils.gerarEmail();
        preencherCampoEditText(R.id.etNomeCadastro, "Maria Jose");
        preencherCampoEditText(R.id.etEmailCadastro, email);
        preencherCampoEditText(R.id.etConfirmacaoEmailCadastro,email);
        preencherCampoEditText(R.id.etTelefoneCadastro,"21356456478");
        preencherCampoEditText(R.id.etSenhaCadastro,"8123456");
        preencherCampoEditText(R.id.etConfirmcaoSenhaCadastro,"8123456");
    }



    @Entao("^devo ver uma mensagem de sucesso do cadastro")
    public void devoVerAMensagemSucessoo() {
        verificarMensagemToast((String)getActivity().getText(R.string.sucess_cadastro));
    }

}
