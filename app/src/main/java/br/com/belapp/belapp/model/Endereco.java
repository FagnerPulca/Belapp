package br.com.belapp.belapp.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Endereco {
    String mEId;
    String mRua;
    String mNumero;
    String mBairro;
    String mCidade;
    String mComplemento;
    String mCep;
    String mLocalizacao;
    private DatabaseReference mDataBase;

    public Endereco() {
    }

    public Endereco(String mRua, String mNumero, String mBairro, String mCidade, String mComplemento, String mCep, String mLocalizacao) {
        //this.mEId = mEId;
        this.mRua = mRua;
        this.mNumero = mNumero;
        this.mBairro = mBairro;
        this.mCidade = mCidade;
        this.mComplemento = mComplemento;
        this.mCep = mCep;
        this.mLocalizacao = mLocalizacao;
    }

    public String addEndereco( Endereco end) {

        mDataBase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference newPostRef = mDataBase.child("enderecos").push();
        newPostRef.setValue(end);
        String chave = newPostRef.getKey();
      return chave;
    }

    public String getmEId() {
        return mEId;
    }

    public void setmEId(String mEId) {
        //this.mEId = mDataBase.getKey();
        this.mEId = mEId;
    }

    public String getmRua() {
        return mRua;
    }

    public void setmRua(String mRua) {
        this.mRua = mRua;
    }

    public String getmNumero() {
        return mNumero;
    }

    public void setmNumero(String mNumero) {
        this.mNumero = mNumero;
    }

    public String getmBairro() {
        return mBairro;
    }

    public void setmBairro(String mBairro) {
        this.mBairro = mBairro;
    }

    public String getmCidade() {
        return mCidade;
    }

    public void setmCidade(String mCidade) {
        this.mCidade = mCidade;
    }

    public String getmComplemento() {
        return mComplemento;
    }

    public void setmComplemento(String mComplemento) {
        this.mComplemento = mComplemento;
    }

    public String getmCep() {
        return mCep;
    }

    public void setmCep(String mCep) {
        this.mCep = mCep;
    }

    public String getmLocalizacao() {
        return mLocalizacao;
    }

    public void setmLocalizacao(String mLocalizacao) {
        this.mLocalizacao = mLocalizacao;
    }
}


