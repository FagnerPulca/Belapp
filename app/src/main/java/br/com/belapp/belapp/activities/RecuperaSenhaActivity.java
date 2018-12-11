package br.com.belapp.belapp.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.crash.FirebaseCrash;

import java.io.IOException;

import br.com.belapp.belapp.R;

public class RecuperaSenhaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText etEmail;
    private FirebaseAuth firebaseAuth;
    private Button btnSolicitarRec;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recupera_senha);
        toolbar = (Toolbar) findViewById(R.id.tbRecSenha);
        setSupportActionBar(toolbar);

        toolbar.setTitle("Recuperar Senha - Teste");
        etEmail = findViewById(R.id.etEmailRec);
        btnSolicitarRec = findViewById(R.id.btnSolicitarRec);

        firebaseAuth = FirebaseAuth.getInstance();

        btnSolicitarRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();
                if(!email.isEmpty()){
                    try{
                        firebaseAuth
                                .sendPasswordResetEmail(email)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            etEmail.setText("");
                                            Toast.makeText(
                                                    RecuperaSenhaActivity.this,
                                                    "Recuperação iniciada. Email enviado.",
                                                    Toast.LENGTH_SHORT
                                            ).show();
                                        } else {
                                            Toast.makeText(
                                                    RecuperaSenhaActivity.this,
                                                    "Falhou! Tente novamente.",
                                                    Toast.LENGTH_SHORT
                                            ).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                FirebaseCrash.report(e);

                            }
                        });
                    } catch (Exception e){
                        Toast.makeText(RecuperaSenhaActivity.this, "Email inexistente!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(RecuperaSenhaActivity.this, "Digite um email!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
