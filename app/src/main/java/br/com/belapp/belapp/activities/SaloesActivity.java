package br.com.belapp.belapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import br.com.belapp.belapp.R;

import br.com.belapp.belapp.model.Agendamento;

import br.com.belapp.belapp.model.Estabelecimento;
import br.com.belapp.belapp.presenter.ApplicationClass;
import br.com.belapp.belapp.presenter.SalaoAdapter;



public class SaloesActivity extends AppCompatActivity implements SalaoAdapter.ItemClicked{

    private ArrayList<Estabelecimento> mEstabelecimentos;
    private ArrayList<Estabelecimento> mResultados;
    private ArrayList<String> mIds;
    private ArrayList<String> mIdcateg, mServicos, mCategServ;
    private ArrayList<String> mPrecoServ, mNomeServ;
    private ArrayList<Agendamento> mAgendamentos;
    private String mCategoria, mEstab, mEndereco, mServcat, mDataSelecionada; //busca
    private int mPreco; //busca
    private double mLatitude;
    private double mLongitude;

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

        mCategoria = getIntent().getStringExtra("mCategoria");
        mEstab = getIntent().getStringExtra("mEstabelecimento");
        mEndereco = getIntent().getStringExtra("mEndereco");
        mServcat = getIntent().getStringExtra("mServcat");
        mPreco = getIntent().getIntExtra("mPreco", 300);
        mDataSelecionada = getIntent().getStringExtra("mDataSelecionada");

        mIds = new ArrayList<>();
        mIdcateg = new ArrayList<>();
        mServicos = new ArrayList<>();
        mCategServ = new ArrayList<>();
        mPrecoServ = new ArrayList<>();
        mNomeServ = new ArrayList<>();
        mAgendamentos = new ArrayList<>();
        mIds = getIntent().getStringArrayListExtra("mIds");
        mIdcateg = getIntent().getStringArrayListExtra("mIdcateg");
        mServicos = getIntent().getStringArrayListExtra("mServicos");
        mCategServ = getIntent().getStringArrayListExtra("mCategServ");
        mPrecoServ = getIntent().getStringArrayListExtra("mPrecoServ");
        mNomeServ = getIntent().getStringArrayListExtra("mNomeServ");
        mAgendamentos = (ArrayList<Agendamento>) getIntent().getSerializableExtra("mAgendamentos");

        mLatitude = getIntent().getDoubleExtra("mLatitude", -8);
        mLongitude = getIntent().getDoubleExtra("mLongitude", -36);

        RecyclerView recyclerView = findViewById(R.id.rvSaloes);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        mEstabelecimentos = new ArrayList<>();
        mResultados = new ArrayList<>();

        myAdapter = new SalaoAdapter(this, mResultados);
        recyclerView.setAdapter(myAdapter);
        buscar();
        dialogBuscando();
    }


    @Override
    public void onItemClicked(int index) {
        Intent intent = new Intent(SaloesActivity.this, PagSalaoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("estabelecimento", mResultados.get(index));
        intent.putExtras(bundle);
        startActivity(intent);
        Toast.makeText(SaloesActivity.this, mResultados.get(index).getmNome(), Toast.LENGTH_SHORT).show();
    }

    private void buscar(){
        Query query = FirebaseDatabase.getInstance().getReference("estabelecimentos");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Estabelecimento estabelecimento = dataSnapshot.getValue(Estabelecimento.class);
                estabelecimento.setmDistancia(ApplicationClass.calculaDistancia(mLatitude, mLongitude,
                        estabelecimento.getmLatitude(), estabelecimento.getmLongitude()));
                        mEstabelecimentos.add(estabelecimento);

                if (!mIds.isEmpty() && !mCategoria.isEmpty()){
                    for (int i = 0; i < mIds.size(); i++){
                        if (estabelecimento.getmEid().equals(mIds.get(i)) && mIdcateg.get(i).equals(mCategoria)){
                               mResultados.add(estabelecimento);
                            break;
                        }
                    }
                } else if((mEndereco.isEmpty() || estabelecimento.getmCidade().toLowerCase().contains(mEndereco.toLowerCase()) ||
                        estabelecimento.getmRua().toLowerCase().contains(mEndereco.toLowerCase()) ||
                        estabelecimento.getmBairro().toLowerCase().contains(mEndereco.toLowerCase())) && (
                                mEstab.isEmpty() || estabelecimento.getmNome().toLowerCase().contains(mEstab.toLowerCase()))){
                        for (int i = 0; i < mPrecoServ.size(); i++){
                            if((mPreco >= Double.valueOf(mPrecoServ.get(i)) && mServicos.get(i).equals(estabelecimento.getmEid())) &&
                                    (mServcat.isEmpty() || mNomeServ.get(i).toLowerCase().contains(mServcat) ||
                                            mCategServ.get(i).toLowerCase().contains(mServcat))){
                                mResultados.add(estabelecimento);
                                verificarServiçosDisponíveis();
                                break;
                            }
                        }
                }


                Collections.sort(mResultados, new Comparator<Estabelecimento>() {
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

    //Verifica se a quantidade de horas agendas no dia é inferior a carga horária diária
    private void verificarServiçosDisponíveis(){
        Map<String,Integer> tm = new TreeMap<String,Integer>();
        for(int i=0; i < mAgendamentos.size(); i++){
            String idEstab = mAgendamentos.get(i).getmEstabelecimento().getmEid();
            int duracao = mAgendamentos.get(i).getmServico().getmDuracao();
            if(tm.get(idEstab) != null){
                int valor = tm.get(idEstab);
                valor += duracao;
                tm.put(idEstab,valor);
            } else {
                tm.put(idEstab,duracao);
            }
        }

        //Remover o estabelecimento do mResultado caso não possua o horário disponível
        Set<Map.Entry<String,Integer>> set = tm.entrySet();
        Iterator<Map.Entry<String,Integer>> inte = set.iterator();
        while(inte.hasNext()){
            Map.Entry item = inte.next();
            if((Integer)item.getValue() >= 1200){
                for(int i=0; i < mResultados.size(); i++){
                    if(mResultados.get(i).getmEid().equals(item.getKey())) {
                        mResultados.remove(i);
                    }
                }
            }
        }
    }
}
