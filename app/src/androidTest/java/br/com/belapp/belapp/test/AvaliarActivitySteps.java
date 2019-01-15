package br.com.belapp.belapp.test;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Rule;

import br.com.belapp.belapp.activities.PagSalaoActivity;
import br.com.belapp.belapp.model.ConfiguracaoFireBase;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Então;
import cucumber.api.java.pt.Quando;

import br.com.belapp.belapp.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertNotNull;

public class AvaliarActivitySteps extends DefaultTest {
    @Rule
    public ActivityTestRule<PagSalaoActivity> activityTestRule = new ActivityTestRule<>(PagSalaoActivity.class);

    @Before("@avaliar-feature")
    public void setup(){
        Intent intent = new Intent();
        intent.putExtra("salao", "-LS54ly9L9y6RsIuOGid");
        intent.putExtra("nome", "Salão Beauty");
        activityTestRule.launchActivity(intent);
        setActivity(activityTestRule.getActivity());
        logarPorEmail("fulano@teste.com","123456");
        esperar(3000);
        apertarBotao(R.id.ibAvaliacoes);
    }

    @After("@avaliar-feature")
    public void tearDown() {
        getAtualActivity().finish();
        activityTestRule.finishActivity();
    }

    @Dado("^eu estou na tela de avaliações do Salão Beauty$")
    public void telaAvaliacoes(){
        assertNotNull(getActivity());
    }

    @Quando("^eu clico em avaliações$")
    public void clicoAvaliacoes(){
        esperar(3000);
        apertarBotao(R.id.ibAvaliacoes);
    }

    @E("^dou uma nota$")
    public void douNota(){
        apertarBotao(R.id.iv_estrela4);
    }

    @E("^deixo comentario em branco$")
    public void comentarioEmBranco(){

    }

    @E("^aperto no botão enviar$")
    public void enviar(){
        apertarBotao(R.id.bt_enviar_avaliar);
    }

    @Então("^vejo a mensagem de sucesso no envio$")
    public void mensagemSucesso(){
        verificarMensagemToast("Avaliação foi salva!");
        esperar(3000);
    }

    @E("^faço um comentário$")
    public void comentar(){
        esperar(3000);
        onView(withId(R.id.et_comentario_avaliar)).perform(typeText("Parabens, realizaram um bom trabalho!"), closeSoftKeyboard());
        Espresso.closeSoftKeyboard();
    }

    @E("^não dou nota$")
    public void naoDouNota(){

    }

    @Então("^vejo uma mensagem de falha no envio$")
    public void mensagemFalha(){
        verificarMensagemToast("Dê uma nota!");
        esperar(3000);
        tearDown();
    }

    @E("^não estou logado$")
    public void naoLogado(){
        deslogar();
        esperar(3000);
    }

    @Então("^vejo um mensagem que preciso está logado$")
    public void mensagemLogado(){
        verificarMensagemToast("Você precisa entrar para avaliar!");
        esperar(3000);
    }
}
