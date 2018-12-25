package br.com.belapp.belapp.model;

import java.io.Serializable;

public class Profissional implements Serializable {

    //Aqui atributos e metodos da classe
    private String mId;
    private String nome;
    private String descricao;
    private String atendDomic;
    private String foto;
    private String horarios;
    //DatabaseReference mDataBase;
    public Profissional(){

    }
    public Profissional(String mId, String nome, String descricao, String atendDomic) {
        this.mId = mId;
        this.nome = nome;
        this.descricao = descricao;
        this.atendDomic = atendDomic;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getAtendDomic() {
        return atendDomic;
    }

    public void setAtendDomic(String atendDomic) {
        this.atendDomic = atendDomic;
    }

    /*public void addProfissional(String nome, String descricao, String domicilio)
    {
        Profissional prof = new Profissional(nome, descricao, domicilio);
        mDataBase = FirebaseDatabase.getInstance().getReference();
        mDataBase.child("estabelecimentos").push().setValue(prof);

    }*/

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Profissional && ((Profissional)obj).mId == getmId());
    }
}
