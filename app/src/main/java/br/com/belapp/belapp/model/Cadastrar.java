package br.com.belapp.belapp.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Cadastrar {
    public void cadastrarCliente(Cliente cliente) {
        FirebaseAuth autenticacao = ConfiguracaoFireBase.getFirebaseAutenticacao();
        if (autenticacao == null) {
            autenticacao.createUserWithEmailAndPassword(cliente.getmEmail(), cliente.getmSenha());
        }

    }
}
