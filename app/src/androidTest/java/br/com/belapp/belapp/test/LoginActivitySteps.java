package br.com.belapp.belapp.test;

import android.content.Intent;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.activities.LoginActivity;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;

public class LoginActivitySteps extends DefaultTest{

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Before("@login-feature")
    public void setup() {
        activityTestRule.launchActivity(new Intent());
        setActivity(activityTestRule.getActivity());
    }

    @After("@login-feature")
    public void tearDown() {
        activityTestRule.finishActivity();
    }

    @Given("^que estou na tela de login")
    public void iamOnLoginScreen() {
        assertNotNull(getActivity());
    }

    @Quando("^eu preencho o campo email flp.d@hotmail.com$")
    public void euPreenchoOCampoEmailCorretamente() {
        preencherCampoEditText(R.id.edtEmail,"flp.d@hotmail.com");
    }

    @Quando("^eu preencho o campo senha 1234567$")
    public void euPreenchoCampoSenhaCorreta() {
        preencherCampoEditText(R.id.edtSenha,"1234567");
    }

    @E("^aperto o botão login$")
    public void apertoBotaoLogin() {
        apertarBotao(R.id.btnLogar);
    }

    @Entao("^devo ver a mensagem, Login efetuado com sucesso!$")
    public void devoVerAMensagemLoginEfetuadoComSucesso() {
       verificarMensagemToast("Login efetuado com sucesso!");
    }
}
