package br.com.belapp.belapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import br.com.belapp.belapp.DAO.EstabelecimentoDAO;
import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Estabelecimento;
import br.com.belapp.belapp.model.Teste;
import br.com.belapp.belapp.presenter.ApplicationClass;
import br.com.belapp.belapp.presenter.SalaoAdapter;

public class SaloesActivity extends AppCompatActivity implements SalaoAdapter.ItemClicked{

    TextView tvTeste, tvLatitude, tvLongitude;

    ArrayList<Estabelecimento> estabelecimentos;
    //EstabelecimentoDAO estabelecimentoDAO;
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    //ArrayList<Estabelecimento> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saloes);

        tvTeste = findViewById(R.id.tvTeste);
        tvLatitude = findViewById(R.id.tvLatitude);
        tvLongitude = findViewById(R.id.tvLongitude);

        String categoria = getIntent().getStringExtra("categoria");
        String servico = getIntent().getStringExtra("servico");
        String cidade = getIntent().getStringExtra("cidade");

        double latitude = getIntent().getDoubleExtra("latitude", -8);
        double longitude = getIntent().getDoubleExtra("longitude", -36);
        tvTeste.setText(categoria);
        tvLatitude.setText(String.valueOf(latitude));
        tvLongitude.setText(String.valueOf(longitude));

        recyclerView = findViewById(R.id.rvSaloes);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        estabelecimentos = new ArrayList<>();
        //estabelecimentoDAO = new EstabelecimentoDAO();

        /*try{
            estabelecimentos = estabelecimentoDAO.getEstabelecimentos();
        } catch (Exception e){
            System.out.print(e.getMessage());
            Toast.makeText(this, "Salao: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }*/

        //lista = new ArrayList<Estabelecimento>();

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
            Toast.makeText(SaloesActivity.this, servico, Toast.LENGTH_SHORT).show();
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

        for (int i = 0; i < estabelecimentos.size(); i++){
            estabelecimentos.get(i).setDistancia(ApplicationClass.calculaDistancia(latitude, longitude,
                    estabelecimentos.get(i).getmLaititude(), estabelecimentos.get(i).getmLongitude()));
        }
        Collections.sort(estabelecimentos, new Comparator<Estabelecimento>() {
            @Override
            public int compare(Estabelecimento o1, Estabelecimento o2) {
                return Double.compare(o1.getDistancia(), o2.getDistancia());
            }
        });

        myAdapter = new SalaoAdapter(this, estabelecimentos);
        recyclerView.setAdapter(myAdapter);
    }


    @Override
    public void onItemClicked(int index) {
        Intent intent = new Intent(SaloesActivity.this, PagSalaoActivity.class);
        intent.putExtra("salao", estabelecimentos.get(index).getmNome());
        startActivity(intent);
        //Toast.makeText(this, "Salao: "+estabelecimentos.get(index).getmNome(),Toast.LENGTH_SHORT).show();
    }
}
