package br.com.belapp.belapp.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Servico;
import br.com.belapp.belapp.presenter.ApplicationClass;
import br.com.belapp.belapp.presenter.ServicoAdapter;

public class PagSalaoActivity extends AppCompatActivity implements ServicoAdapter.ItemClicked {

    ImageButton ibServicos, ibInformacoes, ibAvaliacoes, ibGaleria;
    ImageView ivFotoSalao;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter myAdapter;
    ArrayList<Servico> servicos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag_salao);

        ibServicos = findViewById(R.id.ibServicos);
        ibInformacoes = findViewById(R.id.ibInformacoes);
        ibGaleria = findViewById(R.id.ibGaleria);
        ibAvaliacoes = findViewById(R.id.ibAvaliacoes);
        ivFotoSalao = findViewById(R.id.ivFotoSalao);

        recyclerView = findViewById(R.id.rvServicos);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        String salao = getIntent().getStringExtra("salao");
        servicos = new ArrayList<Servico>();


        for (int i = 0; i < ApplicationClass.estabelecimentos.size(); i++){
            if (ApplicationClass.estabelecimentos.get(i).getmNome().equals(salao)){
                Toast.makeText(this, "Salao: "+ApplicationClass.estabelecimentos.get(i).getmServicos().size(),Toast.LENGTH_SHORT).show();
                for (int j = 0; j < ApplicationClass.estabelecimentos.get(i).getmServicos().size(); j++){
                    servicos.add(ApplicationClass.estabelecimentos.get(i).getmServicos().get(j));
                }
            }
        }

        myAdapter = new ServicoAdapter(this, servicos);
        recyclerView.setAdapter(myAdapter);

    }

    @Override
    public void onItemClicked(int index) {
        Intent intent = new Intent(PagSalaoActivity.this, FuncionariosActivity.class);
        intent.putExtra("servico", servicos.get(index).getmId());
        startActivity(intent);
        Toast.makeText(this, "Salao: "+servicos.get(index).getNome(),Toast.LENGTH_SHORT).show();
    }
}
