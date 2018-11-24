package br.com.belapp.belapp.DAO;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.belapp.belapp.model.Endereco;

public class EnderecoDAO {

    public static DatabaseReference getDatabaseReference() {
        return FirebaseDatabase.getInstance().getReference("enderecos");
    }

    public void save(Endereco endereco){
        if(endereco == null){
            return;
        }

        DatabaseReference mDatabase = getDatabaseReference();
        endereco.setmEid(mDatabase.push().getKey());
        mDatabase.child(endereco.getmEid()).setValue(endereco);
    }

    public void update(Endereco endereco){
        if(endereco == null){
            return;
        }

        DatabaseReference mDatabase = getDatabaseReference();
        mDatabase.child(endereco.getmEid()).setValue(endereco);
    }
}
