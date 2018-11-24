package br.com.belapp.belapp.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Endereco {

    private String mEid;
    private double mLatitude;
    private double mLongitude;
    private String mRua;
    private String mNumero;
    private String mBairro;
    private String mCidade;
    private String mComplemento;
    private String mCep;

    public Endereco(){

    }

    public Endereco(String mEid, double mLatitude, double mLongitude, String mRua,
                    String mNumero, String mBairro, String mCidade, String mComplemento, String mCep) {
        this.mEid = mEid;
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
        this.mRua = mRua;
        this.mNumero = mNumero;
        this.mBairro = mBairro;
        this.mCidade = mCidade;
        this.mComplemento = mComplemento;
        this.mCep = mCep;
    }

    public String getmEid() {
        return mEid;
    }

    public void setmEid(String mEid) {
        this.mEid = mEid;
    }

    public double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
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

    /*String mEId;
    String mRua;
    String mNumero;
    String mBairro;
    String mCidade;
    String mComplemento;
    String mCep;
    String mLocalizacao;
    private DatabaseReference mDataBase;
    double mLatitude;
    double mLongitude;

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
    }*/
}
