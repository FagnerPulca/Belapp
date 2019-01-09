package br.com.belapp.belapp.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Promocoes {

    //Aqui atributos e metodos da classe
    private String idestabelecimento;
    private String idecliente;
    private String titulo;
    private String descricao;
    private String foto;

    public Promocoes() {
    }

    @Exclude
    public String getIdestabelecimento() {
        return idestabelecimento;
    }

    public void setIdestabelecimento(String idestabelecimento) {
        this.idestabelecimento = idestabelecimento;
    }

    @Exclude
    public String getIdecliente() {
        return idecliente;
    }

    public void setIdecliente(String idecliente) {
        this.idecliente = idecliente;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void salvar(){
        DatabaseReference databaseReference = ConfiguracaoFireBase.getFirebase();
        databaseReference.child("promocoes").child(getIdecliente()).child(getIdestabelecimento()).setValue(this);

    }
}
