package br.com.belapp.belapp.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Cliente;
import br.com.belapp.belapp.model.ConfiguracaoFireBase;

public class CadastroBasicoActivity extends AppCompatActivity {

    private ImageView mBotaoFacebook , mBotaoGoogle;
    private EditText mCampoNome, mCampoSenha, mCampoEmail, mCampoTelefone;
    private Button mBotaoCadastrar;
    private Cliente cliente;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_basico);
        //Cadastro com Facebook
        mBotaoFacebook = findViewById(R.id.ivConectarFacebook);
        mBotaoGoogle = findViewById(R.id.ivConectarGoogle);

        mBotaoGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginGoogle = new Intent(CadastroBasicoActivity.this,GoogleLoginActivity.class);
                startActivity(loginGoogle);
            }
        });


        mBotaoFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginFacebook = new Intent(CadastroBasicoActivity.this,FacebookLoginActivity.class);
                startActivity(loginFacebook);
            }
        });
        //Cadastro com Formulário
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
                        cliente = criarCliente(textoNome, textoEmail, textoTelefone, textoSenha);
                        cadastrarUsuarios(cliente);
                    } else {
                        Toast.makeText(CadastroBasicoActivity.this,
                                R.string.erro_Senha,
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(CadastroBasicoActivity.this,
                            R.string.erro_teefone,
                            Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(CadastroBasicoActivity.this,
                        R.string.erro_Email,
                        Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(CadastroBasicoActivity.this,
                    R.string.erro_Nome,
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void cadastrarUsuarios(final Cliente cliente) {
        autenticacao = ConfiguracaoFireBase.getFirebaseAutenticacao();

        autenticacao.createUserWithEmailAndPassword(cliente.getmEmail(), cliente.getmSenha()).
                addOnCompleteListener(CadastroBasicoActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            cliente.salvar(task.getResult().getUser().getUid());
                            Toast.makeText(CadastroBasicoActivity.this,
                                    R.string.sucess_cadastro,
                                    Toast.LENGTH_SHORT).show();
                            abrirTelaPrincipal();
                        } else {
                            //tratamento de exceções do cadastro
                            String excecao = "";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                excecao = "Digite uma senha com 6 ou mais caracteres!";

                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                excecao = "Por favor digite um email válido!";

                            } catch (FirebaseAuthUserCollisionException e) {
                                excecao = "já existe cadastro para esse email!";

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

    private Cliente criarCliente(String nome, String email, String telefone, String senha) {
        Cliente cliente = new Cliente();
        cliente.setmNome(nome);
        cliente.setmEmail(email);
        cliente.setmTelefone(telefone);
        cliente.setmSenha(senha);
        return cliente;
    }

    private void abrirTelaPrincipal() {
        Intent intentAbritPrincipal = new Intent(CadastroBasicoActivity.this , ClienteLogadoActivity.class);
        startActivity(intentAbritPrincipal);
    }

}
