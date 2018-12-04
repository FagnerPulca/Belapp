package br.com.belapp.belapp.DAO;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import br.com.belapp.belapp.model.Profissional;

public class ProfissionalDAO {

    private ArrayList<Profissional> lista;

    public static DatabaseReference getDatabaseReference() {
        return FirebaseDatabase.getInstance().getReference("profissionais");
    }

    public void save(Profissional profissional){
        if(profissional == null){
            return;
        }

        DatabaseReference mDatabase = getDatabaseReference();
        profissional.setmId(mDatabase.push().getKey());
        mDatabase.child(profissional.getmId()).setValue(profissional);
    }

    public void update(Profissional profissional){
        if(profissional == null){
            return;
        }

        DatabaseReference mDatabase = getDatabaseReference();
        mDatabase.child(profissional.getmId()).setValue(profissional);
    }

    public ArrayList<Profissional> getProfissionais(){
        try{
            lista = new ArrayList<Profissional>();
            final DatabaseReference databaseReference = getDatabaseReference();

            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Profissional profissional = dataSnapshot.getValue(Profissional.class);
                    lista.add(profissional);
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
