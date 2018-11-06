package br.com.belapp.belapp.activities;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnlogar;
    private FirebaseAuth autenticacao;
    private Cliente cliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnlogar = findViewById(R.id.btnLogar);

        btnlogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtEmail.getText().toString().equals("") && !edtSenha.getText().toString().equals("")) {
                    cliente = new Cliente();
                    cliente.setmEmail(edtEmail.getText().toString());
                    cliente.setmSenha(edtSenha.getText().toString());
                    validarLogin();


                } else {
                    Toast.makeText(LoginActivity.this, "preencha os campos de email e senha !", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void validarLogin() {

        autenticacao = ConfiguracaoFireBase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(cliente.getmEmail(), cliente.getmSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    abrirTelaPrincipal();
                    Toast.makeText(LoginActivity.this, "Logim efetuado com sucesso !", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(LoginActivity.this, "senha errada ou email invalido !", Toast.LENGTH_SHORT).show();


                }
            }
        });


    }

    public void abrirTelaPrincipal() {

        Intent intentAbritPrincipal = new Intent(LoginActivity.this, ClienteLogadoActivity.class);
        startActivity(intentAbritPrincipal);

    }

}