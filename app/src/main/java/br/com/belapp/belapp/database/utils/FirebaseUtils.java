package br.com.belapp.belapp.database.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtils {

    private static FirebaseDatabase mFirebaseDatabase;
    private static DatabaseReference mDatabaseReference;
    private static FirebaseAuth mFirebaseAuth;

    private FirebaseUtils(){}

    public static FirebaseDatabase getmFirebaseDatabase() {
        if (mFirebaseDatabase == null) {
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mFirebaseDatabase.setPersistenceEnabled(true);
        }
        return mFirebaseDatabase;
    }

    public static DatabaseReference getmDatabaseReference(){
        if( mDatabaseReference == null ){
            mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        }

        return mDatabaseReference;
    }

    private static FirebaseAuth getFirebaseAutenticacao(){
        if( mFirebaseAuth == null ){
            mFirebaseAuth = FirebaseAuth.getInstance();
        }
        return mFirebaseAuth;
    }

    public static FirebaseUser getUsuarioAtual(){
        return getFirebaseAutenticacao().getCurrentUser();
    }
}
