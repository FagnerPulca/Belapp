package br.com.belapp.belapp.test;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.activities.PerfilActivity;
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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;

public class PerfilActivitySteps extends DefaultTest {

    @Rule
    public ActivityTestRule<PerfilActivity> activityTestRule = new ActivityTestRule<>(PerfilActivity.class);

    @Before("@alteracao-perfil-feature")
    public void setup() {
        activityTestRule.launchActivity(new Intent());
        setActivity(activityTestRule.getActivity());
        esperar(3000);
    }

    @After("@alteracao-perfil-feature")
    public void tearDown() {
        if(((String) getActivity().getTitle()).contains("Alterar")){
            getActivity().onBackPressed();
            setActivity(activityTestRule.getActivity());
            esperar(1000);
        }
        dadosAnteriores();
        esperar(1000);
        activityTestRule.finishActivity();
    }

    @Dado("^que estou na tela Meu Perfil")
    public void estouNaTelaMeuPerfil() {
        assertNotNull(getActivity());
    }

    @Quando("^eu preencho todos os campos obrigatórios, exeto o campo nome$")
    public void euNaoPreenchoOCampoNome() {
        preencherCampoEditText(R.id.etNome, "");
        preencherCampoEditText(R.id.etEmail,"flp.d@hotmail.com");
        preencherCampoEditText(R.id.etTelefone,"87999996666");
    }

    @E("^eu aperto o botão salvar")
    public void apertoBotaoLogin() {
        apertarBotao(R.id.btnSalvar);
    }

    @Entao("^devo ver uma mensagem que informe que campo nome nao pode ser deixado em branco$")
    public void devoVerAMensagemNomeNaoPodeSerDeixadoVazio() {
        verificarMensagemToast((String) getActivity().getText(R.string.error_nome_nao_pode_ser_vazio));
    }

    @Quando("^eu preencho todos os campos obrigatórios, exeto o e-mail$")
    public void euNaoPreenchoOCampoEmail() {
        preencherCampoEditText(R.id.etNome, "Fulano de Teste");
        preencherCampoEditText(R.id.etEmail,"");
        preencherCampoEditText(R.id.etTelefone,"87999996666");
    }

    @Entao("^devo ver uma mensagem que informe que campo e-mail nao pode ser deixado em branco$")
    public void devoVerAMensagemEmailNaoPodeSerDeixadoVazio() {
        verificarMensagemToast((String) getActivity().getText(R.string.error_email_nao_pode_ser_vazio));
    }

    @Quando("^eu preencho todos os campos obrigatórios, exeto o telefone$")
    public void euNaoPreenchoOCampoTelefone() {
        preencherCampoEditText(R.id.etNome,"Fulano de Teste");
        preencherCampoEditText(R.id.etEmail,"flp.d@hotmail.com");
        preencherCampoEditText(R.id.etTelefone,"");
    }

    @Entao("^devo ver uma mensagem que informe que campo telefone nao pode ser deixado em branco$")
    public void devoVerAMensagemTelefoneNaoPodeSerDeixadoVazio() {
        verificarMensagemToast((String) getActivity().getText(R.string.error_telefone_nao_pode_ser_vazio));
    }

    @Quando("^eu preencho todos os campos obrigatórios e informo um e-mail inválido$")
    public void euPreenchoOCampoEmailInvalido() {
        preencherCampoEditText(R.id.etNome,"Fulano de Teste");
        preencherCampoEditText(R.id.etEmail,"flp.d@hotm");
        preencherCampoEditText(R.id.etTelefone,"87912345678");
    }

    @Entao("^devo ver uma mensagem que informe que o e-mail informado é inválido$")
    public void devoVerAMensagemEmailInvalido() {
        verificarMensagemToast((String) getActivity().getText(R.string.error_email_invalido));
    }

    @Entao("^devo ver uma mensagem que informe que o telefone informado é inválido$")
    public void devoVerAMensagemTelefoneInvalido() {
        verificarMensagemToast((String) getActivity().getText(R.string.error_telefone_invalido));
    }

    @Quando("^eu preencho todos os campos obrigatórios e informo um telefone inválido$")
    public void euPreenchoOCampoTelefoneInvalido() {
        preencherCampoEditText(R.id.etNome, "Fulano de Teste");
        preencherCampoEditText(R.id.etEmail,"flp.d@hotmail.com");
        preencherCampoEditText(R.id.etTelefone,"8791678");
    }

    @Quando("^eu preencho todos os campos obrigatórios e informo um e-mail em uso por outro cadastro$")
    public void euPreenchoOCampoEmailJaCadastrado() {
        preencherCampoEditText(R.id.etNome,"Fulano de Teste");
        preencherCampoEditText(R.id.etEmail,"beltrano@teste.com");
        preencherCampoEditText(R.id.etTelefone,"87912345678");
    }


    @Entao("^devo ver uma mensagem que informe que o e-mail já está em uso$")
    public void devoVerAMensagemEmailEmUso() {
        verificarMensagemToast((String) getActivity().getText(R.string.error_email_ja_utilizado));
    }

    @Quando("^eu preencho todos os campos obrigatórios corretamente$")
    public void euPreenchoOsCamposCorretamente() {
        preencherCampoEditText(R.id.etNome,"Fulano de Teste Alterado");
        preencherCampoEditText(R.id.etEmail,"flp.d2@alterado.com");
        preencherCampoEditText(R.id.etTelefone,"87987654321");
    }

    @Entao("^devo ver uma mensagem de sucesso da operacao$")
    public void devoVerAMensagemSucesso() {
        verificarMensagemToast((String) getActivity().getText(R.string.sucess_alteracao_realizada));
    }

    @Quando("^eu aperto no botão alterar senha$")
    public void euApertoBotaoAlterarSenha(){
        apertarBotao(R.id.btnAlterarSenha);
    }
//    @E("^eu estou na tela de alteração de senha$")
//    public void estouNaTelaAlteracaoSenha(){
//        isActivityAtual(getActivity().getString(R.string.title_activity_alterar_senha));
//    }

    @E("^eu informo uma senha que não satisfaz as restrições$")
    public void euInformoSenhaNaoRestrita(){
        preencherCampoEditText(R.id.etSenhaAnterior, "1234567");
        preencherCampoEditText(R.id.etNovaSenha, "12345");
        preencherCampoEditText(R.id.etNovaSenhaConfirmacao, "12345");
    }

    @E("^eu aperto no botão salvar senha^$")
    public void euApertoSalvarAlterarSenha(){
        apertarBotao(R.id.btnSalvarAlteracaoSenha);
    }
    @Quando("^eu aperto no botão salvar senha$")
    public void eu_aperto_no_botão_salvar_senha() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        apertarBotao(R.id.btnSalvarAlteracaoSenha);
    }
    @Entao("^devo ver uma mensagem de erro$")
    public void euVereiAMensagem(){
        verificarMensagemToast((String) getActivity().getString(R.string.error_nova_senha_deve_ter_6_ou_mais_caracteres));
    }
    public void dadosAnteriores(){
        preencherCampoEditText(R.id.etNome,"Fulano de Teste");
        preencherCampoEditText(R.id.etEmail,"flp.d@hotmail.com");
        preencherCampoEditText(R.id.etTelefone,"87912345678");
        apertarBotao(R.id.btnSalvar);
    }
}
