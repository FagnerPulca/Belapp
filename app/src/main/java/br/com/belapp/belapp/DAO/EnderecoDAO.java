package br.com.belapp.belapp.DAO;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import br.com.belapp.belapp.model.Endereco;

public class EnderecoDAO {

    ArrayList<Endereco> lista;

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

    public ArrayList<Endereco> getEstabelecimentos(){
        try{
            lista = new ArrayList<Endereco>();
            final DatabaseReference databaseReference = getDatabaseReference();

            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Endereco endereco = dataSnapshot.getValue(Endereco.class);
                    lista.add(endereco);
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
