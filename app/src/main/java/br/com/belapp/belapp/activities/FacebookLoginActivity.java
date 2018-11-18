package br.com.belapp.belapp.activities;

import android.app.Application;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.ConfiguracaoFireBase;

public class FacebookLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String EMAIL = "email";
    private static final String PERFIL = "public_profile";

    private FirebaseAuth mAutenticacao;
    private CallbackManager mGerenciador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);

        mAutenticacao = ConfiguracaoFireBase.getFirebaseAutenticacao();

        mGerenciador = CallbackManager.Factory.create();

        LoginButton mLoginButton = new LoginButton(FacebookLoginActivity.this);
        mLoginButton.callOnClick();

        mLoginButton.setReadPermissions(Arrays.asList(EMAIL, PERFIL));

        mLoginButton.registerCallback(mGerenciador, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("LOGIN", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onCancel() {
                Log.d("LOGIN", "facebook:onCancel");
                setResult(RESULT_CANCELED);
                finish();
            }

            @Override
            public void onError(FacebookException e) {
                Log.d("LOGIN", "facebook:onError", e);
                // Handle exception
                Toast.makeText(FacebookLoginActivity.this,"Erro",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onStart(){
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAutenticacao.getCurrentUser();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mGerenciador.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        final AuthCredential credencial = FacebookAuthProvider.getCredential(token.getToken());
        mAutenticacao.signInWithCredential(credencial)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(FacebookLoginActivity.this,R.string.sucess_login_efetuado,Toast.LENGTH_SHORT).show();
                            FirebaseUser usuario = mAutenticacao.getCurrentUser();
                            abrirTelaPrincipal(usuario);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(FacebookLoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signOut() {
        mAutenticacao.signOut();
        LoginManager.getInstance().logOut();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.nav_sair) {
            signOut();
        }
    }

    private void abrirTelaPrincipal(FirebaseUser usuario) {

        Intent intentAbritPrincipal = new Intent(FacebookLoginActivity.this, ClienteLogadoActivity.class);
        String[] dados = new String[2];
        dados[0] = usuario.getDisplayName();
        dados[1] = usuario.getEmail();
        intentAbritPrincipal.putExtra("dados",dados);
        startActivity(intentAbritPrincipal);
    }
}
