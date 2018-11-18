package br.com.belapp.belapp.model;

public class Servico {

    //Aqui atributos e metodos da classe
    private String mId;
    private String nome;
    private String preco;
    private Profissional profissionais;

    public Servico(String mId, String nome, String preco, Profissional profissionais) {
        this.mId = mId;
        this.nome = nome;
        this.preco = preco;
        this.profissionais = profissionais;
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

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public Profissional getProfissionais() {
        return profissionais;
    }

    public void setProfissionais(Profissional profissionais) {
        this.profissionais = profissionais;
    }
}
