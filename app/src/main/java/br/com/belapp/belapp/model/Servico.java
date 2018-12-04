package br.com.belapp.belapp.model;

public class Servico {

    //Aqui atributos e metodos da classe
    private String mId;
    private String mEstabId;
    private String mNome;
    private double mPreco;
    private String mDuracao;
    private String mProfissionais;
    private String mCategoria;

    public Servico(){

    }

    public Servico(String mId, String mNome, double mPreco, String mDuracao, String mProfissionais, String mCategoria) {
        this.mId = mId;
        this.mNome = mNome;
        this.mPreco = mPreco;
        this.mDuracao = mDuracao;
        this.mProfissionais = mProfissionais;
        this.mCategoria = mCategoria;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmEstabId() {
        return mEstabId;
    }

    public void setmEstabId(String mEstabId) {
        this.mEstabId = mEstabId;
    }

    public String getmNome() {
        return mNome;
    }

    public void setmNome(String mNome) {
        this.mNome = mNome;
    }

    public double getmPreco() {
        return mPreco;
    }

    public void setmPreco(double mPreco) {
        this.mPreco = mPreco;
    }

    public String getmDuracao() {
        return mDuracao;
    }

    public void setmDuracao(String mDuracao) {
        this.mDuracao = mDuracao;
    }

    public String getmProfissionais() {
        return mProfissionais;
    }

    public void setmProfissionais(String mProfissionais) {
        this.mProfissionais = mProfissionais;
    }

    public String getmCategoria() {
        return mCategoria;
    }

    public void setmCategoria(String mCategoria) {
        this.mCategoria = mCategoria;
    }

    /*public Servico(String mId, String nome, double preco, String duracao, String profissionais, String categoria) {
        this.mId = mId;
        this.nome = nome;
        this.preco = preco;
        this.duracao = duracao;
        this.profissionais = profissionais;
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Profissional getProfissionais() {
        return profissionais;
    }

    public void setProfissionais(Profissional profissionais) {
        this.profissionais = profissionais;
    }*/
}
