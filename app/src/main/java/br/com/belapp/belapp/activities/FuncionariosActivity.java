package br.com.belapp.belapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Profissional;
import br.com.belapp.belapp.presenter.ApplicationClass;
import br.com.belapp.belapp.presenter.FuncionarioAdapter;

public class FuncionariosActivity extends AppCompatActivity implements FuncionarioAdapter.ItemClicked{

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter myAdapter;
    ArrayList<Profissional> profissionais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionarios);

        recyclerView = findViewById(R.id.rvProfissionais);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        profissionais = new ArrayList<Profissional>();
        String servico = getIntent().getStringExtra("servico");

        /*selProfissionais(servico);

        myAdapter = new FuncionarioAdapter(this, profissionais);
        recyclerView.setAdapter(myAdapter);*/
    }

    @Override
    public void onItemClicked(int index) {
        Toast.makeText(this, "Profissional: "+profissionais.get(index).getNome(),Toast.LENGTH_SHORT).show();
    }

    /*private void selProfissionais(String servico){
        //ArrayList<Profissional> profissionais = new ArrayList<Profissional>();
        for (int i = 0; i < ApplicationClass.servicos.size(); i++){
            if (ApplicationClass.servicos.get(i).getmId().equals(servico)){
                profissionais.add(ApplicationClass.servicos.get(i).getProfissionais());
            }
        }
    }*/
}
