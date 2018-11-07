package br.com.belapp.belapp.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFireBase {

    //aqui configuração com banco firebase
    private static DatabaseReference sDataBaseReferece;
    private static FirebaseAuth sAutenticacao;

    public static DatabaseReference getFirebase() {

        if (sDataBaseReferece == null) {
            sDataBaseReferece = FirebaseDatabase.getInstance().getReference();
        }
        return sDataBaseReferece;

    }

    public static FirebaseAuth getFirebaseAutenticacao() {

        if (sAutenticacao == null) {
            sAutenticacao = FirebaseAuth.getInstance();
        }
        return sAutenticacao;


    }

}
