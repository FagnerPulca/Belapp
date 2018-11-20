package br.com.belapp.belapp.test;

import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.view.KeyEvent;

import br.com.belapp.belapp.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;

public class DefaultTest {

    private Activity activity;

    /**
     * Pausa a execuçao pelo tempo informado
     * @param tempo em milisegundos
     */
    public void esperar(int tempo) {
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Preenche as informaçoes no campo
     * @param idEditText
     * @param msg
     */
    public void preencherCampoEditText(int idEditText, String msg){
        limparCampoEditText(idEditText);
        onView(withId(idEditText)).perform(typeText(msg));
        Espresso.closeSoftKeyboard();
    }

    public void limparCampoEditText(int idEditText){
        onView(withId(idEditText))
                .perform(replaceText(""));
        Espresso.closeSoftKeyboard();
    }
    public Activity getActivity() {
        return activity;
    }

    public void verificarMensagemToast(String mensagem){
        // Espera 2 segundos
        esperar(1000);
        onView(withText(mensagem))
                .inRoot(withDecorView(not(is(activity.getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void apertarBotao(int idBotao){
        Espresso.onView(withId(idBotao))
                .perform(click());
    }
}
