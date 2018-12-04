package br.com.belapp.belapp.DAO;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.belapp.belapp.model.Servico;

public class ServicoDAO {

    public static DatabaseReference getDatabaseReference() {
        return FirebaseDatabase.getInstance().getReference("servicos");
    }

    public void save(Servico servico){
        if(servico == null){
            return;
        }

        DatabaseReference mDatabase = getDatabaseReference();
        servico.setmId(mDatabase.push().getKey());
        mDatabase.child(servico.getmId()).setValue(servico);
    }

    public void update(Servico servico){
        if(servico == null){
            return;
        }

        DatabaseReference mDatabase = getDatabaseReference();
        mDatabase.child(servico.getmId()).setValue(servico);
    }
}
