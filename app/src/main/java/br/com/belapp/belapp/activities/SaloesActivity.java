package br.com.belapp.belapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;




import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.ConfiguracaoFireBase;
import br.com.belapp.belapp.model.Estabelecimento;
import br.com.belapp.belapp.presenter.ApplicationClass;
import br.com.belapp.belapp.presenter.SalaoAdapter;



public class SaloesActivity extends AppCompatActivity implements SalaoAdapter.ItemClicked{

    private ArrayList<Estabelecimento> estabelecimentos;
    private ArrayList<Estabelecimento> resultados;
    ArrayList<String> ids;
    ArrayList<String> idcateg;
    String categoria;
    String estab;
    String endereco;
    double latitude;
    double longitude;
    private String userId;
    DatabaseReference databaseReference;
    private boolean result;

    private RecyclerView.Adapter myAdapter;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saloes);



        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_estabelecimentos);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        categoria = getIntent().getStringExtra("categoria");
        estab = getIntent().getStringExtra("estabelecimento");
        endereco = getIntent().getStringExtra("endereco");
        ids = new ArrayList<>();
        idcateg = new ArrayList<>();
        ids = getIntent().getStringArrayListExtra("ids");
        idcateg = getIntent().getStringArrayListExtra("idcateg");

        latitude = getIntent().getDoubleExtra("latitude", -8);
        longitude = getIntent().getDoubleExtra("longitude", -36);

        RecyclerView recyclerView = findViewById(R.id.rvSaloes);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        databaseReference = ConfiguracaoFireBase.getFirebase();

        estabelecimentos = new ArrayList<>();
        resultados = new ArrayList<>();

        myAdapter = new SalaoAdapter(this, resultados);
        recyclerView.setAdapter(myAdapter);
        buscar();
        dialogBuscando();


    }


    @Override
    public void onItemClicked(int index) {
        Intent intent = new Intent(SaloesActivity.this, PagSalaoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("estabelecimento", resultados.get(index));
        intent.putExtras(bundle);
        startActivity(intent);
        Toast.makeText(SaloesActivity.this, resultados.get(index).getmNome(), Toast.LENGTH_SHORT).show();
    }

    private void buscar(){
        Query query = FirebaseDatabase.getInstance().getReference("estabelecimentos");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Estabelecimento estabelecimento = dataSnapshot.getValue(Estabelecimento.class);
                estabelecimento.setmDistancia(ApplicationClass.calculaDistancia(latitude, longitude,
                        estabelecimento.getmLatitude(), estabelecimento.getmLongitude()));
                        estabelecimentos.add(estabelecimento);

                if (!ids.isEmpty() && !categoria.isEmpty()){
                    for (int i = 0; i < ids.size(); i++){
                        if (estabelecimento.getmEid().equals(ids.get(i)) && idcateg.get(i).equals(categoria)){

                               resultados.add(estabelecimento);


                            break;
                        }
                    }
                } else if (!endereco.isEmpty() && estab.isEmpty()){
                    if (estabelecimento.getmCidade().toLowerCase().contains(endereco.toLowerCase()) ||
                            estabelecimento.getmRua().toLowerCase().contains(endereco.toLowerCase()) ||
                            estabelecimento.getmBairro().toLowerCase().contains(endereco.toLowerCase())){

                            resultados.add(estabelecimento);


                    }
                } else if (endereco.isEmpty() && !estab.isEmpty()){
                    if (estabelecimento.getmNome().toLowerCase().contains(estab.toLowerCase())){

                            resultados.add(estabelecimento);


                    }
                } else if (!endereco.isEmpty() && !estab.isEmpty()){
                    if (estabelecimento.getmNome().toLowerCase().contains(estab.toLowerCase()) &&
                            (estabelecimento.getmCidade().toLowerCase().contains(endereco.toLowerCase()) ||
                                    estabelecimento.getmRua().toLowerCase().contains(endereco.toLowerCase()) ||
                                    estabelecimento.getmBairro().toLowerCase().contains(endereco.toLowerCase()))){

                                            resultados.add(estabelecimento);


                    }
                }

                Collections.sort(resultados, new Comparator<Estabelecimento>() {
                    @Override
                    public int compare(Estabelecimento o1, Estabelecimento o2) {
                        return Double.compare(o1.getmDistancia(), o2.getmDistancia());
                    }
                });

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

    @Override
    public boolean onSupportNavigateUp() {
        // TODO: Verificar se ha alteracoes antes de voltar
        onBackPressed();
        return true;
    }
    /**
     * Pausa a execuçao pelo tempo informado
     * @param tempo em milisegundos
     */
    public void esperar(int tempo) {
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
