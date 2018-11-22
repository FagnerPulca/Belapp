package br.com.belapp.belapp.test;

import android.app.Activity;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Rule;
import br.com.belapp.belapp.R;
import br.com.belapp.belapp.activities.CadastroBasicoActivity;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Então;
import cucumber.api.java.pt.Quando;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;

public class FacebookLoginActivitySteps {

    @Rule
    public ActivityTestRule<CadastroBasicoActivity> activityTestRule = new ActivityTestRule<>(CadastroBasicoActivity.class);
    private Activity activity;

    @Before("@loginFacebook-feature")
    public void setup() {
        //desAutenticar();
        activityTestRule.launchActivity(new Intent());
        activity = activityTestRule.getActivity();
    }

    @After("@loginFacebook-feature")
    public void tearDown() {
        onView(withId(R.id.nav_sair)).perform(click());
        activityTestRule.finishActivity();
    }

    @Dado("^Eu estou na tela de login$")
    public void estouNaTelaLogin(){
        assertNotNull(activity);
    }

    @Quando("^Quero logar pelo facebook$")
    public void logarPeloFacebook(){
        assertNull(activity);
    }

    @E("^Eu clico no botão de Conectar com o facebook$")
    public void clicoConectarComFacebook(){
        onView(withId(R.id.ivConectarFacebook)).perform(click());
    }

    @Então("^Eu devo ver mensagem de confirmação$")
    public void vejoMensagemConfirmacao(){
        onView(withText(R.string.sucess_cadastro)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    public void desAutenticar(){
        FirebaseAuth autenticado = FirebaseAuth.getInstance();
        if(autenticado != null) {
            autenticado.signOut();
            LoginManager.getInstance().logOut();
            //Intent intentInicialActivity = new Intent(ClienteLogadoActivity.this, InicialActivity.class);
            //startActivity(intentInicialActivity);
        }
    }
}
