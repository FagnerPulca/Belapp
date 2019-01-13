package br.com.belapp.belapp.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Promocoes {

    //Aqui atributos e metodos da classe
    private String midestabelecimento;
    private String midecliente;
    private String titulo;
    private String descricao;
    private String foto;

    public Promocoes() {
    }

    @Exclude
    public String getMidestabelecimento() {
        return midestabelecimento;
    }

    public void setMidestabelecimento(String midestabelecimento) {
        this.midestabelecimento = midestabelecimento;
    }

    @Exclude
    public String getMidecliente() {
        return midecliente;
    }

    public void setMidecliente(String midecliente) {
        this.midecliente = midecliente;
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
        databaseReference.child("promocoes").child(getMidecliente()).child(getMidestabelecimento()).setValue(this);

    }
}
