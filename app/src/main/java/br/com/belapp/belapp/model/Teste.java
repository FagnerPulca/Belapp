package br.com.belapp.belapp.model;

public class Teste {
    private String nome;
    private String info;
    private String categ;

    public Teste(String nome, String info, String categ) {
        this.nome = nome;
        this.info = info;
        this.categ = categ;
    }

    public Teste(String nome, String info) {
        this.nome = nome;
        this.info = info;
    }

    public String getCateg() {
        return categ;
    }

    public void setCateg(String categ) {
        this.categ = categ;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
