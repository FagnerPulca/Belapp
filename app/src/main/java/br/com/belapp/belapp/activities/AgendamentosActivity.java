package br.com.belapp.belapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.enums.MesesEnum;
import br.com.belapp.belapp.enums.StatusAgendamentoEnum;
import br.com.belapp.belapp.model.Agendamento;
import br.com.belapp.belapp.model.Servico;
import br.com.belapp.belapp.presenter.AgendamentoAdapter;
import br.com.belapp.belapp.utils.DateUtils;

public class AgendamentosActivity extends AppCompatActivity implements AgendamentoAdapter.ItemClicked{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mMyAdapter;
    private ArrayList<Agendamento> mAgendamentos;
    private ProgressDialog mProgressDialog;
    private Spinner mFiltroMeses;
    private Spinner mFiltroStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamentos);

        // Configuraçao do toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_minha_agenda);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);


        mFiltroMeses = findViewById(R.id.spinner_meses_toolBar);
        List<String> listaStringsMeses = MesesEnum.getListaMeses();

        ArrayAdapter<String> adapterMeses = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, listaStringsMeses);
        mFiltroMeses.setAdapter(adapterMeses);
        mFiltroMeses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                buscar();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // empty
            }
        });

        mFiltroStatus = findViewById(R.id.spinner_status_agendamento_toolBar);
        List<String> listaStringsStatus = StatusAgendamentoEnum.getListaStatus();

        ArrayAdapter<String> adapterStatus = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, listaStringsStatus);
        mFiltroStatus.setAdapter(adapterStatus);
        mFiltroStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                buscar();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // empty
            }
        });
        mRecyclerView = findViewById(R.id.rvAgendamentos);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mAgendamentos = new ArrayList<Agendamento>();

        buscarFiltrarAgendamentos();

        buscar();
        dialogBuscando();


    }

    private void buscarFiltrarAgendamentos() {
//        ArrayList<Agendamento> auxList = mAgendamentos;
        if(!mFiltroStatus.getSelectedItem().toString().contains("Todos")
                && !mFiltroMeses.getSelectedItem().toString().contains("Todos")){

            if(mFiltroStatus.getSelectedItem().toString().contains("Agendado")){
                mAgendamentos = filtrarPorStatusAgendado(mAgendamentos);
            }
            else if(mFiltroStatus.getSelectedItem().toString().contains("Concluído")){
                mAgendamentos = filtrarPorStatusConcluido(mAgendamentos);
            }
            mAgendamentos = filtrarPorMes(mAgendamentos);
        }
        else {

            if(!mFiltroMeses.getSelectedItem().toString().contains("Todos")){
                mAgendamentos = filtrarPorMes(mAgendamentos);
            }

            if(mFiltroStatus.getSelectedItem().toString().contains("Agendado")){
                mAgendamentos = filtrarPorStatusAgendado(mAgendamentos);
            }
            else if(mFiltroStatus.getSelectedItem().toString().contains("Concluído")){
                mAgendamentos = filtrarPorStatusConcluido(mAgendamentos);
            }
        }
        if(mAgendamentos.size() == 0) {
            Agendamento agendamento = new Agendamento();
            Servico servico = new Servico();
            servico.setmNome(getString(R.string.error_nao_ha_resultados));
            agendamento.setmServico(servico);
            agendamento.setmId("");
            agendamento.setmData("00/00/0000");
            mAgendamentos.add(agendamento);
        }

        mMyAdapter = new AgendamentoAdapter(AgendamentosActivity.this, mAgendamentos);
        mRecyclerView.setAdapter(mMyAdapter);
        mMyAdapter.notifyDataSetChanged();
    }

    private ArrayList<Agendamento> filtrarPorMes(ArrayList<Agendamento> agendamentos) {
        ArrayList<Agendamento> listaFiltrada = new ArrayList<>();
        for (Agendamento agendamento: agendamentos){
            if(DateUtils.checarSeDataPertenceAoMes(
                    agendamento.getmData(),
                    MesesEnum.getValor(mFiltroMeses.getSelectedItem().toString()))){
                listaFiltrada.add(agendamento);
            }
        }

        return listaFiltrada;
    }

    private ArrayList<Agendamento> filtrarPorStatusAgendado(ArrayList<Agendamento> agendamentos){
        ArrayList<Agendamento> listAuxiliar = new ArrayList<>();
        for(Agendamento agendamento: agendamentos){
            if(DateUtils.isDataFutura(agendamento.getmData())
                    || DateUtils.isDataPresente(agendamento.getmData())){
                listAuxiliar.add(agendamento);
            }
        }
        return listAuxiliar;
    }

    private ArrayList<Agendamento> filtrarPorStatusConcluido(ArrayList<Agendamento> agendamentos){
        ArrayList<Agendamento> listAuxiliar = new ArrayList<>();
        for(Agendamento agendamento: agendamentos){
            if(!DateUtils.isDataFutura(agendamento.getmData())
                    && !DateUtils.isDataPresente(agendamento.getmData())){
                listAuxiliar.add(agendamento);
            }
        }
        return listAuxiliar;
    }



    private void dialogBuscando(){
        mProgressDialog = new ProgressDialog(AgendamentosActivity.this);
        mProgressDialog.setMessage("Buscando...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgress(0);
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
    }

    private void buscar() {
        mAgendamentos.clear();
        Query query = FirebaseDatabase.getInstance().getReference("agendamentos").orderByChild("mData");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                Agendamento agendamento = dataSnapshot.getValue(Agendamento.class);


                if(!mAgendamentos.contains(agendamento)){
                    mAgendamentos.add(agendamento);
                    ordenarResultados();
                    buscarFiltrarAgendamentos();
                    mProgressDialog.dismiss();
                }



//                removerDuplicados();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
                // empty
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                // empty
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {
                // empty
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // empty
            }
        });

    }
    @Override
    public void onItemClicked(int index) {

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("agendamento", mAgendamentos.get(index));
        intent.putExtras(bundle);
        intent.setClass(AgendamentosActivity.this, AgendamentoActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private ArrayList<Agendamento> removerDuplicados(ArrayList<Agendamento> agendamentos){
        ArrayList<Agendamento> aux = new ArrayList<>();
        HashSet<Agendamento> hs=new HashSet<>(agendamentos);
        for(Agendamento a: hs){
            aux.add(a);
        }
        return aux;
    }

    private void ordenarResultados(){
        Collections.sort(mAgendamentos, (o1, o2) -> DateUtils
                .getDiferencaEntreDuasDatasEspecificas(
                        o2.getmData(),
                        o1.getmData()));

    }
}
