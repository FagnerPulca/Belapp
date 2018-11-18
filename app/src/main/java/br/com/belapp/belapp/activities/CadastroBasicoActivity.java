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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import br.com.belapp.belapp.model.ConfiguracaoFireBase;
import br.com.belapp.belapp.model.Cliente;

import java.sql.RowId;

import br.com.belapp.belapp.R;

public class CadastroBasicoActivity extends AppCompatActivity {

    private EditText mCampoNome, mCampoSenha, mCampoEmail, mCampoTelefone;
    private Button mBotaoCadastrar;
    private Cliente cliente;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_basico);

        mCampoNome = findViewById(R.id.etNomeCadastro);
        mCampoSenha = findViewById(R.id.etSenhaCadastro);
        mCampoEmail = findViewById(R.id.etEmailCadastro);
        mCampoTelefone = findViewById(R.id.etTelefoneCadastro);
        mBotaoCadastrar = findViewById(R.id.btnCadastrar);


        mBotaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoNome = mCampoNome.getText().toString();
                String textoSenha = mCampoSenha.getText().toString();
                String textoEmail = mCampoEmail.getText().toString();
                String textoTelefone = mCampoTelefone.getText().toString();
                //validar campos
                validarCampos(textoNome, textoEmail, textoTelefone, textoSenha);

            }
        });


    }

    //método para validar campos
    public void validarCampos(String textoNome, String textoEmail, String textoTelefone, String textoSenha) {

        if (!textoNome.isEmpty()) {
            if (!textoEmail.isEmpty()) {
                if (!textoTelefone.isEmpty()) {
                    if (!textoSenha.isEmpty()) {
                        cliente = cadastrarClienteBasico(textoNome, textoEmail, textoTelefone, textoSenha);
                        cadastrarUsuarios();
                    } else {
                        Toast.makeText(CadastroBasicoActivity.this,
                                "preencha o Telefone !",
                                Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(CadastroBasicoActivity.this,
                            "preencha a Senha !",
                            Toast.LENGTH_SHORT).show();

                }

            } else {
                Toast.makeText(CadastroBasicoActivity.this,
                        "preencha o Email !",
                        Toast.LENGTH_SHORT).show();

            }

        } else {
            Toast.makeText(CadastroBasicoActivity.this,
                    "preencha o Nome !",
                    Toast.LENGTH_SHORT).show();

        }

    }

    public void cadastrarUsuarios() {
        autenticacao = ConfiguracaoFireBase.getFirebaseAutenticacao();

        autenticacao.createUserWithEmailAndPassword(cliente.getmEmail(), cliente.getmSenha()).
                addOnCompleteListener(CadastroBasicoActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CadastroBasicoActivity.this,
                                    "Sucesso ao cadastrar usuario !",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            //tratamento de exceções do cadastro
                            String excecao = "";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                excecao = "digite uma senha mais forte";

                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                excecao ="Por favor digite u email valido!";
                            } catch (FirebaseAuthUserCollisionException e) {
                                excecao ="já existe cadastro para esse email  ";
                            }catch (Exception e){
                                excecao= "Erro a cadastrar usuario"+e.getMessage();
                                e.printStackTrace();
                            }

                                    Toast.makeText(CadastroBasicoActivity.this,
                                            excecao,
                                            Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


    private Cliente cadastrarClienteBasico(String nome, String email, String telefone, String senha) {
        Cliente cliente = new Cliente();
        cliente.setmNome(nome);
        cliente.setmEmail(email);
        cliente.setmTelefone(telefone);
        cliente.setmSenha(senha);
        return cliente;
    }
}


