package br.com.belapp.belapp.model;

public class Promocoes {

    //Aqui atributos e metodos da classe
    private String idPromocao;
    private String estado;
    private String categoria;
    private String titulo;
    private String descricao;
    private String telefone;
    private String foto;

    public Promocoes() {
    }

    public String getIdPromocao() {
        return idPromocao;
    }

    public void setIdPromocao(String idPromocao) {
        this.idPromocao = idPromocao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
