package br.com.belapp.belapp.DAO;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import br.com.belapp.belapp.model.Profissional;

public class ProfissionalDAO {

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



    FirebaseDatabase firebaseDatabase;
    ArrayList<Profissional> listProfissionais;


    public void inserirProfissional()
    {
        Profissional profissional = new Profissional();
        profissional.addProfissional("Joana", "20 anos de experiência", "sim");
    }
}
