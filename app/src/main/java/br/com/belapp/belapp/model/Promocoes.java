package br.com.belapp.belapp.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Promocoes {

    //Aqui atributos e metodos da classe
    private String mIdestabelecimento;
    private String mIdecliente;
    private String mTitulo;
    private String MDescricao;
    private String mFoto;

    public Promocoes() {
    }

    @Exclude
    public String getIdestabelecimento() {
        return mIdestabelecimento;
    }

    public void setIdestabelecimento(String idestabelecimento) {
        this.mIdestabelecimento = idestabelecimento;
    }

    @Exclude
    public String getmIdecliente() {
        return mIdecliente;
    }

    public void setmIdecliente(String mIdecliente) {
        this.mIdecliente = mIdecliente;
    }

    public String getmTitulo() {
        return mTitulo;
    }

    public void setmTitulo(String mTitulo) {
        this.mTitulo = mTitulo;
    }

    public String getMDescricao() {
        return MDescricao;
    }

    public void setMDescricao(String MDescricao) {
        this.MDescricao = MDescricao;
    }

    public String getmFoto() {
        return mFoto;
    }

    public void setmFoto(String mFoto) {
        this.mFoto = mFoto;
    }

    public void salvar(){
        DatabaseReference databaseReference = ConfiguracaoFireBase.getFirebase();
        databaseReference.child("promocoes").child(getmIdecliente()).child(getIdestabelecimento()).setValue(this);

    }
}
