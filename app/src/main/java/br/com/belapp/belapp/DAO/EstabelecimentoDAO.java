package br.com.belapp.belapp.DAO;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import br.com.belapp.belapp.model.Agenda;
import br.com.belapp.belapp.model.Endereco;
import br.com.belapp.belapp.model.Estabelecimento;
import br.com.belapp.belapp.model.Profissional;
import br.com.belapp.belapp.model.Promocoes;
import br.com.belapp.belapp.model.Servico;

public class EstabelecimentoDAO {
    FirebaseDatabase firebaseDatabase;
    ArrayList<Estabelecimento> listaEstabelecimentos;
    public EstabelecimentoDAO() {
    }

    /*public void inserirEstabelecimento()
    {
        Endereco endereco = new Endereco("Av Santo Antonio", "150", "Centro","Garanhuns","","55333-000",
                "L1234560");

        String end = endereco.addEndereco(endereco);

        ArrayList<Servico> servicos = new ArrayList<>();
        ArrayList<Agenda> agenda = new ArrayList<>();
        ArrayList<Promocoes> promocoes = new ArrayList<>();
        ArrayList<Profissional> profissionais = new ArrayList<>();
        ArrayList<String> avaliacoes = new ArrayList<>();

        Estabelecimento est = new Estabelecimento();
        est.addEstabelecimento("Salão Glamour", "8755555555", "Tudo que você precisa em um só lugar ", "5",
                "foto.pnj", end, "L123456", -8, -37, servicos, agenda, promocoes, profissionais,avaliacoes);

     }
*/
     public ArrayList<Estabelecimento> getEstabelecimentos()
     {
         firebaseDatabase = FirebaseDatabase.getInstance();
         DatabaseReference ref = firebaseDatabase.getReference("estabelecimentos");
          listaEstabelecimentos = new ArrayList<>();

         try
         {
             ref.addChildEventListener(new ChildEventListener()
             {
                 @Override
                 public void onChildAdded(DataSnapshot dataSnapshot, String s)
                 {
                     Estabelecimento newEst = dataSnapshot.getValue(Estabelecimento.class);

                     System.out.println("Estabelecimento agr : " + newEst);
                     listaEstabelecimentos.add(newEst);
                     //System.out.println("Tamanho locais : " + listaEstabelecimentos.size());

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
         }
         catch (Exception e){
             System.out.println(e.getMessage());
         }

         return listaEstabelecimentos;
     }
}
