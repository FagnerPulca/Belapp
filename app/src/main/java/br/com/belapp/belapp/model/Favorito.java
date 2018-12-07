package br.com.belapp.belapp.model;

import com.google.firebase.database.DatabaseReference;

public class Favorito {

    private String cliente;
    public String estabelecimento;
    private int curtida;

    public Favorito() {
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(String estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public int getCurtida() {
        return curtida;
    }

    public void setCurtida(int curtida) {
        this.curtida = curtida;
    }

    public void salvar(){
        DatabaseReference firebaseRef = ConfiguracaoFireBase.getFirebase();
        DatabaseReference favoritoRef = firebaseRef.child("favoritos").child(getCliente());
        favoritoRef.setValue(this);

    }
}
