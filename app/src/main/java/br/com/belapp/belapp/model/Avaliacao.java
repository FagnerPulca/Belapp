package br.com.belapp.belapp.model;

import java.util.SplittableRandom;

public class Avaliacao {

    private String mComentario;
    private String mData;
    private String mFoto;
    private String mNome;
    private double mNota;

    public Avaliacao(){}

    public String getmComentario() {
        return mComentario;
    }

    public void setmComentario(String mComentario) {
        this.mComentario = mComentario;
    }

    public String getmData() {
        return mData;
    }

    public void setmData(String mData) {
        this.mData = mData;
    }

    public String getmFoto() {
        return mFoto;
    }

    public void setmFoto(String mFoto) {
        this.mFoto = mFoto;
    }

    public String getmNome() {
        return mNome;
    }

    public void setmNome(String mNome) {
        this.mNome = mNome;
    }

    public double getmNota() {
        return mNota;
    }

    public void setmNota(double mNota) {
        this.mNota = mNota;
    }
}
