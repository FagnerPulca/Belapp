package br.com.belapp.belapp.test;

import android.app.Activity;
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

public class LoginActivitySteps {

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);
    private Activity activity;

    @Before("@login-feature")
    public void setup() {
        activityTestRule.launchActivity(new Intent());
        activity = activityTestRule.getActivity();
    }

    @After("@login-feature")
    public void tearDown() {
        activityTestRule.finishActivity();
    }

    @Given("^que estou na tela de login")
    public void iamOnLoginScreen() {
        assertNotNull(activity);
    }

    @Quando("^eu preencho o campo email teste@teste.com$")
    public void euPreenchoOCampoEmailCorretamente() {
        onView(withId(R.id.edtEmail)).perform(typeText("teste@teste.com"));
    }

    @Quando("^eu preencho o campo senha senhateste$")
    public void euPreenchoCampoSenhaCorreta() {
        onView(withId(R.id.edtSenha)).perform(typeText("senhateste"), closeSoftKeyboard());
    }

    @E("^aperto o botão login$")
    public void apertoBotaoLogin() {
        onView(withId(R.id.btnLogar)).perform(click());
    }

    @Entao("^devo ver a mensagem, Login efetuado com sucesso!$")
    public void devoVerAMensagemLoginEfetuadoComSucesso() {
       onView(withText("Login efetuado com sucesso!")).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }
}
