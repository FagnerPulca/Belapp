package br.com.belapp.belapp.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.belapp.belapp.R;

public class EditarActivity extends AppCompatActivity {

    private Button btnEditarSenha;
    private FirebaseAuth autenticacao;
    private EditText edtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        edtEmail = findViewById(R.id.textViewEmail);
        btnEditarSenha = findViewById(R.id.btnEditarSenha);


       btnEditarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autenticacao.
                        sendPasswordResetEmail("marquesmiranda.r@gmail.com").
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                     edtEmail.setText("");
                                    Toast.makeText(EditarActivity.this, "um email foi enviado para alteraçao de senha !", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(EditarActivity.this, "Erro !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
