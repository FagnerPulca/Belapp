package br.com.belapp.belapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.ConfiguracaoFireBase;
import br.com.belapp.belapp.model.Estabelecimento;
import br.com.belapp.belapp.model.Promocoes;
import br.com.belapp.belapp.presenter.ApplicationClass;
import br.com.belapp.belapp.presenter.PromocaoAdapter;


import static br.com.belapp.belapp.database.utils.FirebaseUtils.getUsuarioAtual;

public class PromocoesActivity extends AppCompatActivity implements PromocaoAdapter.ItemClicked  {

    private ArrayList<Estabelecimento> estabelecimentos;
    private ArrayList<Estabelecimento> resultados;
    private ArrayList<Promocoes> resultatoP;


    private String midUser;
    private String mendereco;
    private double latitude;
    private double longitude;
    private DatabaseReference databaseReference;


    private RecyclerView.Adapter myAdapter;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promocoes);

        midUser = getUsuarioAtual().getUid();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_promocoes);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);




        latitude = getIntent().getDoubleExtra("latitude", -8);
        longitude = getIntent().getDoubleExtra("longitude", -36);

        RecyclerView recyclerView = findViewById(R.id.rvPromocoes);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        databaseReference = ConfiguracaoFireBase.getFirebase();

        estabelecimentos = new ArrayList<>();
        resultados = new ArrayList<>();
        resultatoP = new ArrayList<>();

        myAdapter = new PromocaoAdapter(this, resultados,resultatoP);
        recyclerView.setAdapter(myAdapter);
        buscar();
        dialogBuscando();

        Collections.sort(resultados, new Comparator<Estabelecimento>() {
            @Override
            public int compare(Estabelecimento o1, Estabelecimento o2) {
                return Double.compare(o1.getmDistancia(), o2.getmDistancia());
            }
        });



    }

    @Override
    public void onItemClicked(int index) {
        Intent intent = new Intent(PromocoesActivity.this, PagSalaoActivity.class);
        intent.putExtra("salao", resultados.get(index).getmEid());
        intent.putExtra("nome", resultados.get(index).getmNome());
        Bundle bundle = new Bundle();
        bundle.putSerializable("estabelecimento", resultados.get(index));
        intent.putExtras(bundle);
        startActivity(intent);
        Toast.makeText(PromocoesActivity.this, resultados.get(index).getmNome(), Toast.LENGTH_SHORT).show();
    }

    private void buscar() {
        Query query = FirebaseDatabase.getInstance().getReference("estabelecimentos");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Estabelecimento estabelecimento = dataSnapshot.getValue(Estabelecimento.class);
                estabelecimento.setmDistancia(ApplicationClass.calculaDistancia(latitude, longitude,
                        estabelecimento.getmLatitude(), estabelecimento.getmLongitude()));
                estabelecimentos.add(estabelecimento);



                verificaPromocao(estabelecimento.getmEid(), estabelecimento);


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

    void dialogBuscando() {
        mProgressDialog = new ProgressDialog(PromocoesActivity.this);
        mProgressDialog.setMessage("Buscando...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgress(0);
        mProgressDialog.show();
    }

    public void verificaPromocao(String idSalao, Estabelecimento e) {


        databaseReference.child("promocoes")
                .child(midUser)
                .child(idSalao)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Promocoes p = dataSnapshot.getValue(Promocoes.class);
                        if (dataSnapshot.getValue() != null) {
                            resultatoP.add(p);
                            resultados.add(e);
                            myAdapter.notifyDataSetChanged();



                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // empty
                    }
                });


    }


    @Override
    public boolean onSupportNavigateUp() {
        // TODO: Verificar se ha alteracoes antes de voltar
        onBackPressed();
        return true;
    }

    /**
     * Pausa a execuçao pelo tempo informado
     *
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
