package br.com.belapp.belapp.model;

public class Endereco {
    String mEId;
    String mRua;
    String mNumero;
    String mBairro;
    String mCidade;
    String mComplemento;
    String mCep;
    double mLatitude;
    double mLongitude;

    public Endereco(String mEId, String mRua, String mNumero, String mBairro, String mCidade, String mComplemento, String mCep, double mLatitude, double mLongitude) {
        this.mEId = mEId;
        this.mRua = mRua;
        this.mNumero = mNumero;
        this.mBairro = mBairro;
        this.mCidade = mCidade;
        this.mComplemento = mComplemento;
        this.mCep = mCep;
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
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
}
