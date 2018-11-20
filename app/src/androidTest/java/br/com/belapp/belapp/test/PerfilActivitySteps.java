package br.com.belapp.belapp.test;

import android.app.Activity;
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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;

public class PerfilActivitySteps {

    @Rule
    public ActivityTestRule<PerfilActivity> activityTestRule = new ActivityTestRule<>(PerfilActivity.class);
    private Activity activity;

    @Before("@alteracao-perfil-feature")
    public void setup() {
        activityTestRule.launchActivity(new Intent());
        activity = activityTestRule.getActivity();
    }

    @After("@alteracao-perfil-feature")
    public void tearDown() {
        activityTestRule.finishActivity();
    }

    @Dado("^que estou na tela Meu Perfil")
    public void estouNaTelaMeuPerfil() {
        assertNotNull(activity);
    }

    @Quando("^eu preencho todos os campos obrigatórios, exeto o campo nome$")
    public void euNaoPreenchoOCampoNome() {
        onView(withId(R.id.etNome)).perform(typeText(""));
        onView(withId(R.id.etEmail)).perform(typeText("flp.d@hotmail.com"), closeSoftKeyboard());
        onView(withId(R.id.etTelefone)).perform(typeText("87999996666"), closeSoftKeyboard());
    }

    @E("^eu aperto o botão salvar")
    public void apertoBotaoLogin() {
        onView(withId(R.id.btnSalvar)).perform(click());
    }

    @Entao("^devo ver uma mensagem que informe que campo nome nao pode ser deixado em branco$")
    public void devoVerAMensagemNomeNaoPodeSerDeixadoVazio() {
        onView(withText((String) activity.getText(R.string.error_nome_nao_pode_ser_vazio)))
                .inRoot(withDecorView(not(is(activity.getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Quando("^eu preencho todos os campos obrigatórios, exeto o e-mail$")
    public void euNaoPreenchoOCampoEmail() {
        onView(withId(R.id.etNome)).perform(typeText("Fulano de Teste"));
        onView(withId(R.id.etEmail)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.etTelefone)).perform(typeText("87999996666"), closeSoftKeyboard());
    }

    @Quando("^eu preencho todos os campos obrigatórios, exeto o telefone$")
    public void euNaoPreenchoOCampoTelefone() {
        onView(withId(R.id.etNome)).perform(typeText("Fulano de Teste"));
        onView(withId(R.id.etEmail)).perform(typeText("flp.d@hotmail.com"), closeSoftKeyboard());
        onView(withId(R.id.etTelefone)).perform(typeText(""), closeSoftKeyboard());
    }

    @Quando("^eu preencho todos os campos obrigatórios e informo um e-mail inválido$")
    public void euPreenchoOCampoEmailInvalido() {
        onView(withId(R.id.etNome)).perform(typeText("Fulano de Teste"));
        onView(withId(R.id.etEmail)).perform(typeText("flp.d@hotm"), closeSoftKeyboard());
        onView(withId(R.id.etTelefone)).perform(typeText("87912345678"), closeSoftKeyboard());
    }

    @Quando("^eu preencho todos os campos obrigatórios e informo um telefone inválido$")
    public void euPreenchoOCampoTelefoneInvalido() {
        onView(withId(R.id.etNome)).perform(typeText("Fulano de Teste"));
        onView(withId(R.id.etEmail)).perform(typeText("flp.d@hotmail.com"), closeSoftKeyboard());
        onView(withId(R.id.etTelefone)).perform(typeText("8791678"), closeSoftKeyboard());
    }

    @Entao("^devo ver uma mensagem que informe que campo e-mail nao pode ser deixado em branco$")
    public void devoVerAMensagemEmailNaoPodeSerDeixadoVazio() {
        onView(withText((String) activity.getText(R.string.error_email_nao_pode_ser_vazio)))
                .inRoot(withDecorView(not(is(activity.getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Entao("^devo ver uma mensagem que informe que campo telefone nao pode ser deixado em branco$")
    public void devoVerAMensagemTelefoneNaoPodeSerDeixadoVazio() {
        onView(withText((String) activity.getText(R.string.error_telefone_nao_pode_ser_vazio)))
                .inRoot(withDecorView(not(is(activity.getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Entao("^devo ver uma mensagem que informe que o e-mail informado é inválido$")
    public void devoVerAMensagemEmailInvalido() {
        onView(withText((String) activity.getText(R.string.error_email_invalido)))
                .inRoot(withDecorView(not(is(activity.getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Entao("^devo ver uma mensagem que informe que o telefone informado é inválido$")
    public void devoVerAMensagemTelefoneInvalido() {
        onView(withText((String) activity.getText(R.string.error_telefone_invalido)))
                .inRoot(withDecorView(not(is(activity.getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

}
