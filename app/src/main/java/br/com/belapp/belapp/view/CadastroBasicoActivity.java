package br.com.belapp.belapp.view;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Cliente;
import br.com.belapp.belapp.model.ConfiguracaoFireBase;

public class CadastroBasicoActivity extends AppCompatActivity {

    private EditText mNome;
    private EditText mEmail;
    private EditText mTelefone;
    private EditText mSenha;
    private Button mCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_basico);

        mNome = findViewById(R.id.etNomeCadastro);
        mEmail = findViewById(R.id.etEmailCadastro);
        mTelefone = findViewById(R.id.etTelefoneCadastro);
        mSenha = findViewById(R.id.etSenhaCadastro);
        mCadastrar = findViewById(R.id.btnCadastrar);

        mCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cliente cliente = cadastrarClienteBasico(mNome, mEmail, mTelefone, mSenha);

                if (autenticar(cliente) != null) {
                    //abrirTelaPrincipal();
                    Toast.makeText(CadastroBasicoActivity.this, "Teste if", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(CadastroBasicoActivity.this, "Teste else", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private Cliente cadastrarClienteBasico(EditText nome, EditText email, EditText telefone, EditText senha) {
        Cliente cliente = new Cliente();
        cliente.setmNome(nome.getText().toString());
        cliente.setmEmail(email.getText().toString());
        cliente.setmTelefone(telefone.getText().toString());
        cliente.setmSenha(senha.getText().toString());
        return cliente;
    }

    private FirebaseAuth autenticar(Cliente cliente) {
        FirebaseAuth autenticacao = ConfiguracaoFireBase.getFirebaseAutenticacao();
        if (autenticacao == null) {
            autenticacao.createUserWithEmailAndPassword(
                    cliente.getmEmail(),
                    cliente.getmSenha()
            ).addOnCompleteListener(CadastroBasicoActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String mensagem = Resources.getSystem().getString(R.string.success_cadastro);
                        Toast.makeText(CadastroBasicoActivity.this, mensagem, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(CadastroBasicoActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        return autenticacao;
    }

    private void abrirTelaPrincipal() {

        Intent intentAbritPrincipal = new Intent(CadastroBasicoActivity.this, ClienteLogadoActivity.class);
        startActivity(intentAbritPrincipal);

    }
}
