package br.com.belapp.belapp.model;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


public class Cliente {

    private String mEmail;
    private String mSenha;
    private String mFoto;
    private String mTelefone;
    private String mNome;
    private String mEnderecoID;
    private String mAgendamentoID;
    private ArrayList<String> mFavoritos; // estabelecimentoID


    public Cliente() {

    }

    public void salvar(String id){
        DatabaseReference databaseReference = ConfiguracaoFireBase.getFirebase();
        databaseReference.child("clientes").child(id).setValue(this);
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmSenha() {
        return mSenha;
    }

    public void setmSenha(String mSenha) {
        this.mSenha = mSenha;
    }

    public String getmFoto() {
        return mFoto;
    }

    public void setmFoto(String mFoto) {
        this.mFoto = mFoto;
    }

    public String getmTelefone() {
        return mTelefone;
    }

    public void setmTelefone(String mTelefone) {
        this.mTelefone = mTelefone;
    }

    public String getmNome() {
        return mNome;
    }

    public void setmNome(String mNome) {
        this.mNome = mNome;
    }

    public String getmEnderecoID() {
        return mEnderecoID;
    }

    public void setmEnderecoID(String mEnderecoID) {
        this.mEnderecoID = mEnderecoID;
    }

    public String getmAgendamentoID() {
        return mAgendamentoID;
    }

    public void setmAgendamentoID(String mAgendamentoID) {
        this.mAgendamentoID = mAgendamentoID;
    }

    public ArrayList<String> getmFavoritos() {
        return mFavoritos;
    }

    public void setmFavoritos(ArrayList<String> mFavoritos) {
        this.mFavoritos = mFavoritos;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Cliente && ((Cliente) obj).mEmail.equalsIgnoreCase(getmEmail())
                && ((Cliente) obj).mNome.equalsIgnoreCase(getmNome())
                && ((Cliente) obj).mTelefone.equalsIgnoreCase(getmTelefone());

    }
}
