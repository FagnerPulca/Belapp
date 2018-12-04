package br.com.belapp.belapp.DAO;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import br.com.belapp.belapp.model.Servico;

public class ServicoDAO {

    private ArrayList<Servico> lista;

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

    public ArrayList<Servico> getProfissionais(){
        try{
            lista = new ArrayList<Servico>();
            final DatabaseReference databaseReference = getDatabaseReference();

            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Servico servico = dataSnapshot.getValue(Servico.class);
                    lista.add(servico);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return lista;
    }
}
