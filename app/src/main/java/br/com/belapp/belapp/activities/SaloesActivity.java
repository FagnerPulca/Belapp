package br.com.belapp.belapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Estabelecimento;
import br.com.belapp.belapp.model.Teste;
import br.com.belapp.belapp.presenter.ApplicationClass;
import br.com.belapp.belapp.presenter.SalaoAdapter;

public class SaloesActivity extends AppCompatActivity implements SalaoAdapter.ItemClicked{

    TextView tvTeste, tvLatitude, tvLongitude;

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Teste> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saloes);

        tvTeste = findViewById(R.id.tvTeste);
        tvLatitude = findViewById(R.id.tvLatitude);
        tvLongitude = findViewById(R.id.tvLongitude);

        String categoria = getIntent().getStringExtra("categoria");
        double latitude = getIntent().getDoubleExtra("latitude", -8);
        double longitude = getIntent().getDoubleExtra("longitude", -36);
        tvTeste.setText(categoria);
        tvLatitude.setText(String.valueOf(latitude));
        tvLongitude.setText(String.valueOf(longitude));

        recyclerView = findViewById(R.id.rvSaloes);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        lista = new ArrayList<Teste>();

        for (int i = 0; i < ((Integer) ApplicationClass.testes.size()); i++){
            if (ApplicationClass.testes.get(i).getCateg().equals(categoria)){
                lista.add(ApplicationClass.testes.get(i));
            }
        }

        myAdapter = new SalaoAdapter(this, lista);
        recyclerView.setAdapter(myAdapter);
    }


    @Override
    public void onItemClicked(int index) {
        Intent intent = new Intent(SaloesActivity.this, PagSalaoActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Salao: "+lista.get(index).getNome(),Toast.LENGTH_SHORT).show();
    }
}
