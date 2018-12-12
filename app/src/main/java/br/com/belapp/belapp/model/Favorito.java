package br.com.belapp.belapp.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Favorito {

    private String idEstabelecimento;
    private int curtida = 0;
    private String idCliente;

    public Favorito() {
    }

    @Exclude
    public String getIdEstabelecimento() {
        return idEstabelecimento;
    }

    public void setIdEstabelecimento(String idEstabelecimento) {
        this.idEstabelecimento = idEstabelecimento;
    }

    public int getCurtida() {
        return curtida;
    }

    public void setCurtida(int curtida) {
        this.curtida = curtida;
    }

    @Exclude
    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public void salvar(){
        DatabaseReference databaseReference = ConfiguracaoFireBase.getFirebase();
        databaseReference.child("favoritos").child(getIdCliente()).child(getIdEstabelecimento()).setValue(this);

   }

    public void Remove(){
        DatabaseReference databaseReference = ConfiguracaoFireBase.getFirebase();
        databaseReference.child("favoritos").child(getIdCliente()).child(getIdEstabelecimento()).removeValue();


    }


}
