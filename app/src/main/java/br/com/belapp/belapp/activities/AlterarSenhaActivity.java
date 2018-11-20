package br.com.belapp.belapp.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;

import java.util.Objects;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.database.utils.FirebaseUtils;
import br.com.belapp.belapp.exceptions.ValidationException;
import br.com.belapp.belapp.utils.StringUtils;

import static br.com.belapp.belapp.database.utils.FirebaseUtils.*;

public class AlterarSenhaActivity extends AppCompatActivity {

    private static final String TAG = "belapp.activities";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_senha);
        // Configuraçao do toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_alterar_senha);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        final EditText etSenhaAnterior, etNovaSenha, etNovaSenhaConfirmacao;
        etSenhaAnterior = findViewById(R.id.etSenhaAnterior);
        etNovaSenha = findViewById(R.id.etNovaSenha);
        etNovaSenhaConfirmacao = findViewById(R.id.etNovaSenhaConfirmacao);

        Button btnSalvarAlteracoes = findViewById(R.id.btnSalvarAlteracaoSenha);

        btnSalvarAlteracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    validar();
                    salvar();
                } catch (ValidationException e) {
                    Toast.makeText(
                            AlterarSenhaActivity.this,
                            e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }


            }

            private void salvar() {

                AuthCredential credencial = EmailAuthProvider
                        .getCredential(Objects.requireNonNull(getUsuarioAtual().getEmail()), etSenhaAnterior.getText().toString());
                getUsuarioAtual().reauthenticate(credencial)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            getUsuarioAtual()
                                    .updatePassword(etNovaSenha.getText().toString())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "Senha alterada");
                                        Toast.makeText(
                                                AlterarSenhaActivity.this,
                                                getText(R.string.sucess_alteracao_realizada),
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d(TAG, "Erro senha nao alterada");
                                        Toast.makeText(
                                                AlterarSenhaActivity.this,
                                                getText(R.string.error_alteracao_nao_realizada),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Log.d(TAG, "Error auth failed");
                            Toast.makeText(
                                    AlterarSenhaActivity.this,
                                    getText(R.string.error_autenticacao_falhou),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }

            private void validar() throws ValidationException {
                if(TextUtils.isEmpty(etSenhaAnterior.getText().toString())){
                    throw new ValidationException(getString(R.string.error_senha_atual_vazia));
                }
                if(TextUtils.isEmpty(etNovaSenha.getText().toString())){
                    throw new ValidationException(getString(R.string.error_nova_senha_vazia));
                }
                if(TextUtils.isEmpty(etNovaSenhaConfirmacao.getText().toString())){
                    throw new ValidationException(getString(R.string.error_confirmacao_nova_senha_vazia));
                }
                if(!TextUtils.equals(etNovaSenha.getText().toString(), etNovaSenhaConfirmacao.getText().toString())){
                    throw new ValidationException(getString(R.string.error_nova_senha_confirmacao_diferem));
                }
                if(StringUtils.isVerificarSenhaInvalida(etNovaSenha.getText().toString())){
                    throw new ValidationException(getString(R.string.error_nova_senha_deve_ter_6_ou_mais_caracteres));
                }
            }
        });


    }
    @Override
    public boolean onSupportNavigateUp() {
        // TODO: Verificar se ha alteracoes antes de voltar
        onBackPressed();
        return true;
    }

}
