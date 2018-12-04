package br.com.belapp.belapp.model;

public class Profissional {

    //Aqui atributos e metodos da classe
    private String mId;
    private String nome;
    private String descricao;
    private String atendDomic;
    private String foto;
    private String horarios;

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
}
