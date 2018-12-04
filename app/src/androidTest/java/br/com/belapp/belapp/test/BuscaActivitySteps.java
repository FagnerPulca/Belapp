package br.com.belapp.belapp.test;

import android.app.Activity;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.view.ViewGroup;

import org.junit.Rule;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.activities.TelaBuscaActivity;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.es.Dado;
import cucumber.api.java.it.E;
import cucumber.api.java.it.Quando;
import cucumber.api.java.pt.Entao;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;

public class BuscaActivitySteps extends DefaultTest {

    @Rule
    public ActivityTestRule<TelaBuscaActivity> activityTestRule = new ActivityTestRule<>(TelaBuscaActivity.class);
    private Activity activity;

    @Before("@busca-feature")
    public void setup(){
        activityTestRule.launchActivity(new Intent());
        activity = activityTestRule.getActivity();
    }

    @After("@busca-feature")
    public void tearDown() {
        getAtualActivity().finish();
        activityTestRule.finishActivity();
    }

    @Dado("^Eu estou na tela de busca$")
    public void euEstouNaTelaDeBusca(){
        assertNotNull(activity);
    }

    @Quando("^Eu coloco a cidade e não o serviço$")
    public void euColocoACidade(){
        onView(withId(R.id.etServico)).perform(typeText(""));
        onView(withId(R.id.etCidade)).perform(typeText("Garanhuns"), closeSoftKeyboard());
    }

    @Quando("^Eu coloco o serviço e não a cidade$")
    public void euColocoOServico(){
        onView(withId(R.id.etCidade)).perform(typeText(""));
        onView(withId(R.id.etServico)).perform(typeText("Corte simples"), closeSoftKeyboard());
    }

    @Quando("^Eu coloco o serviço e a cidade$")
    public void euColocoOsDois(){
        onView(withId(R.id.etCidade)).perform(typeText("Garanhuns"));
        onView(withId(R.id.etServico)).perform(typeText("Corte simples"), closeSoftKeyboard());
    }

    @Quando("^Eu clico em buscar sem preencher os campos$")
    public void euColocoNenhum(){
        onView(withId(R.id.etCidade)).perform(typeText(""));
        onView(withId(R.id.etServico)).perform(typeText(""));
    }

    @E("^Eu clico em buscar$")
    public void clicoEmBuscar(){
        onView(withId(R.id.btnBuscar)).perform(click());
    }

    @Entao("^Eu devo ver os salões retornados$")
    public void verRetorno(){
        onView(withText(R.string.resultados)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    @Entao("^Devo ver uma mensagem dizendo para digitar algum dado$")
    public void verMensagem(){
        onView(withText(R.string.digite_algum_dado)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    /**
     *
     * @return referência da activity que está sendo exibida na tela
     */
    public Activity getAtualActivity() {
        final Activity[] activity = new Activity[1];

        onView(isRoot()).check((view, noViewFoundException) -> {

            View checkedView = view;

            while (checkedView instanceof ViewGroup && ((ViewGroup) checkedView).getChildCount() > 0) {

                checkedView = ((ViewGroup) checkedView).getChildAt(0);

                if (checkedView.getContext() instanceof Activity) {
                    activity[0] = (Activity) checkedView.getContext();
                    return;
                }
            }
        });
        return activity[0];
    }
}
