package br.com.belapp.belapp.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFireBase {

    //aqui configuração com banco firebase
    private static DatabaseReference dataBaseReferece;
    private static FirebaseAuth autenticacao;

    public static DatabaseReference getFirebase() {

        if (dataBaseReferece == null) {
            dataBaseReferece = FirebaseDatabase.getInstance().getReference();
        }
        return dataBaseReferece;

    }

    public static FirebaseAuth getFirebaseAutenticacao() {

        if (autenticacao == null) {
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;


    }

}
