package br.com.belapp.belapp.test;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.test.espresso.Espresso;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.core.internal.deps.guava.collect.Iterables;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.util.Log;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.activities.LoginActivity;
import br.com.belapp.belapp.model.ConfiguracaoFireBase;

import java.util.Collection;

import br.com.belapp.belapp.R;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.anything;
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
     * @param idEditText a ser preenchido
     * @param msg  msg a ser escrita no campo
     */
    public void preencherCampoEditText(int idEditText, String msg){
        limparCampoEditText(idEditText);
        onView(withId(idEditText)).perform(typeText(msg), closeSoftKeyboard());
        Espresso.closeSoftKeyboard();
    }

    /**
     * Limpa o componente EditText
     * @param idEditText a ser limpo
     */
    public void limparCampoEditText(int idEditText){
        onView(withId(idEditText))
                .perform(replaceText(""));
        Espresso.closeSoftKeyboard();
    }

    /**
     *
     * @return activity retornada de activityTestRule
     */
    public Activity getActivity() {
        return activity;
    }

    public void verificarMensagemToast(String mensagem){
        // Espera 2 segundos
        esperar(1500);
        onView(withText(mensagem))
                .inRoot(withDecorView(not(is(activity.getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    /**
     *
     * @param idBotao do botao a ser apertado
     */
    public void apertarBotao(int idBotao){
        onView(withId(idBotao))
                .perform(click());
    }

//    public  void isActivityAtual(String activityNome) {
//        intended(hasComponent(activityNome));
//    }

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
                }
            }
        });
        return activity[0];
    }

    public void selecionarItemReciclerView(int idRecicledView, int posicao){
        onView(withId(idRecicledView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(posicao, click()));
    }

    public void selecionarItemSpinnerByPosition(int idSpiner, int posicao){
        onView(withId(idSpiner)).perform(click());
        onData(anything()).atPosition(1).perform(click());
    }

    public void testarToolbar(){
        onView(withId(br.com.belapp.belapp.R.id.toolbar)).check(matches(isDisplayed()));
        onView(withText(br.com.belapp.belapp.R.string.app_name)).check(matches(withParent(withId(R.id.toolbar))));
    }


    public void logarPorEmail(String email, String senha){
        FirebaseAuth autenticacao = ConfiguracaoFireBase.getFirebaseAutenticacao();
        if(autenticacao.getUid() == null) {
            autenticacao.signInWithEmailAndPassword(email, senha).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //Toast.makeText(getAtualActivity().getApplicationContext(), "Login realizado com sucesso!", Toast.LENGTH_LONG).show();
                            } else {

                                //tratamento de exceções do cadastro
                                String excecao = "";
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthInvalidUserException e) {
                                    excecao = "Usuario não cadastrado!";
                                } catch (FirebaseAuthInvalidCredentialsException e) {
                                    excecao = "email ou senha não correspndem a um usuario cadastrado !";
                                } catch (Exception e) {
                                    excecao = "Erro ao logar usuario" + e.getMessage();
                                    e.printStackTrace();
                                }

                                /*Toast.makeText(getAtualActivity().getApplicationContext(),
                                        excecao,
                                        Toast.LENGTH_SHORT).show();*/
                            }
                        }
                    });
            esperar(10000);
        }
    }

    public void deslogar(){
        FirebaseAuth usuario = ConfiguracaoFireBase.getFirebaseAutenticacao();
        if(usuario.getUid() != null) usuario.signOut();
    }


    
}