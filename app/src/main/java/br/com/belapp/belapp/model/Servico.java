package br.com.belapp.belapp.model;

public class Servico {

    //Aqui atributos e metodos da classe
    private String mId;
    private String nome;
    private double preco;
    private String duracao;
    private Profissional profissionais;
    private String categoria;

    public Servico(String mId, String nome, double preco, String duracao, Profissional profissionais, String categoria) {
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
    }
}
