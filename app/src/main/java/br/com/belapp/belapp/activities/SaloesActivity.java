package br.com.belapp.belapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import java.util.ArrayList;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Estabelecimento;
import br.com.belapp.belapp.presenter.SalaoAdapter;

public class SaloesActivity extends AppCompatActivity implements SalaoAdapter.ItemClicked{

    private ArrayList<Estabelecimento> estabelecimentos;

    private RecyclerView.Adapter myAdapter;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saloes);

        String categoria = getIntent().getStringExtra("categoria");
        String servico = getIntent().getStringExtra("servico");
        String cidade = getIntent().getStringExtra("cidade");

        double latitude = getIntent().getDoubleExtra("latitude", -8);
        double longitude = getIntent().getDoubleExtra("longitude", -36);

        RecyclerView recyclerView = findViewById(R.id.rvSaloes);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        estabelecimentos = new ArrayList<>();

        myAdapter = new SalaoAdapter(this, estabelecimentos);
        recyclerView.setAdapter(myAdapter);
        buscar();
        dialogBuscando();
    }


    @Override
    public void onItemClicked(int index) {
        Intent intent = new Intent(SaloesActivity.this, PagSalaoActivity.class);
        intent.putExtra("salao", estabelecimentos.get(index).getmNome());
        startActivity(intent);
        Toast.makeText(this, getString(R.string.servicos),Toast.LENGTH_SHORT).show();
    }

    private void buscar(){
        Query query = FirebaseDatabase.getInstance().getReference("estabelecimentos");

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               Estabelecimento estabelecimento = dataSnapshot.getValue(Estabelecimento.class);
               estabelecimentos.add(estabelecimento);
               myAdapter.notifyDataSetChanged();
               mProgressDialog.dismiss();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                // empty
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // empty
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                // empty
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // empty
            }
        });
    }

    void dialogBuscando(){
        mProgressDialog = new ProgressDialog(SaloesActivity.this);
        mProgressDialog.setMessage("Buscando...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgress(0);
        mProgressDialog.show();
    }
    /*private void selEstabelecimentos(String categoria, String servico, String cidade){

        if(!categoria.isEmpty()) { //apenas escolheu uma categoria
            for (int i = 0; i < ((Integer) ApplicationClass.estabelecimentos.size()); i++) {
                for (int j = 0; j < ApplicationClass.estabelecimentos.get(i).getmServicos().size(); j++) {
                    if (ApplicationClass.estabelecimentos.get(i).getmServicos().get(j).getCategoria().equals(categoria)) {
                        estabelecimentos.add(ApplicationClass.estabelecimentos.get(i));
                        break;
                    }
                }
            }
        } else if (!servico.isEmpty() && cidade.isEmpty()){ //foi pela tela de busca
            for (int i = 0; i < ((Integer) ApplicationClass.estabelecimentos.size()); i++) {
                for (int j = 0; j < ApplicationClass.estabelecimentos.get(i).getmServicos().size(); j++) {
                    if (ApplicationClass.estabelecimentos.get(i).getmServicos().get(j).getNome().equals(servico)) {
                        estabelecimentos.add(ApplicationClass.estabelecimentos.get(i));
                        break;
                    }
                }
            }
        } else if (!cidade.isEmpty() && servico.isEmpty()){
            for (int i = 0; i < ((Integer) ApplicationClass.estabelecimentos.size()); i++) {
                for (int j = 0; j < ApplicationClass.estabelecimentos.get(i).getmServicos().size(); j++) {
                    if (ApplicationClass.estabelecimentos.get(i).getmEndereco_ID().getmCidade().equals(cidade)) {
                        estabelecimentos.add(ApplicationClass.estabelecimentos.get(i));
                        break;
                    }
                }
            }
        } else {
            for (int i = 0; i < ((Integer) ApplicationClass.estabelecimentos.size()); i++) {
                for (int j = 0; j < ApplicationClass.estabelecimentos.get(i).getmServicos().size(); j++) {
                    if (ApplicationClass.estabelecimentos.get(i).getmServicos().get(j).getNome().equals(servico) &&
                            ApplicationClass.estabelecimentos.get(i).getmEndereco_ID().getmCidade().equals(cidade)) {
                        estabelecimentos.add(ApplicationClass.estabelecimentos.get(i));
                        break;
                    }
                }
            }
        }
    }*/
}
