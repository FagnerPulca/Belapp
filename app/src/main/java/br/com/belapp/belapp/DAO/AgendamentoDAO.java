package br.com.belapp.belapp.DAO;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.belapp.belapp.model.Agendamento;

public class AgendamentoDAO {

    public static DatabaseReference getDatabaseReference(){
        return FirebaseDatabase.getInstance().getReference("agendamentos");
    }

    public void save(Agendamento agendamento){
        if(agendamento == null){
            return;
        }

        DatabaseReference mDatabase = getDatabaseReference();
        agendamento.setmId(mDatabase.push().getKey());
        mDatabase.child(agendamento.getmId()).setValue(agendamento);
    }

    public void remove(Agendamento agendamento, DatabaseReference.CompletionListener completionListener) {
        DatabaseReference mDatabase = getDatabaseReference();
        mDatabase.child(agendamento.getmId()).removeValue(completionListener);
    }

    public void update(Agendamento agendamento){
        DatabaseReference databaseReference = getDatabaseReference();
        databaseReference.child(agendamento.getmId()).setValue(agendamento);
    }
}
