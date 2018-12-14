package br.com.belapp.belapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Estabelecimento;
import br.com.belapp.belapp.model.Servico;
import br.com.belapp.belapp.presenter.ApplicationClass;
import br.com.belapp.belapp.presenter.ServicoAdapter;

public class PagSalaoActivity extends AppCompatActivity implements ServicoAdapter.ItemClicked {

    ImageButton ibServicos, ibInformacoes, ibAvaliacoes;
    ImageView ivFotoSalao;
    TextView tvNomeSalao;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter myAdapter;
    ArrayList<Servico> servicos;
    private ProgressDialog mProgressDialog;
    String salao;
    String nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag_salao);

        ibServicos = findViewById(R.id.ibServicos);
        ibInformacoes = findViewById(R.id.ibInformacoes);
        ibAvaliacoes = findViewById(R.id.ibAvaliacoes);
        ivFotoSalao = findViewById(R.id.ivFotoSalao);
        tvNomeSalao = findViewById(R.id.tvNomeSalao);

        recyclerView = findViewById(R.id.rvServicos);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        salao = getIntent().getStringExtra("salao"); // id do salão
        nome = getIntent().getStringExtra("nome");
        tvNomeSalao.setText(nome);
        servicos = new ArrayList<>();

        myAdapter = new ServicoAdapter(this, servicos);
        recyclerView.setAdapter(myAdapter);

        buscar();
        dialogBuscando();

        ibInformacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PagSalaoActivity.this, InfoActivity.class);
                intent.putExtra("salao", salao); //id do salão é passado para puxar informações do mesmo
                Toast.makeText(PagSalaoActivity.this, "Informações", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemClicked(int index) {
        Intent intent = new Intent(PagSalaoActivity.this, FuncionariosActivity.class);
        intent.putExtra("servico", servicos.get(index).getmProfissionais());
        startActivity(intent);
        //Toast.makeText(this, "Salao: "+servicos.get(index).getNome(),Toast.LENGTH_SHORT).show();
    }

    private void buscar(){
        Query query = FirebaseDatabase.getInstance().getReference("servicos");

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Servico servico = dataSnapshot.getValue(Servico.class);
                if (servico.getmEstabId().equals(salao)){
                    servicos.add(servico);
                }
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
        mProgressDialog = new ProgressDialog(PagSalaoActivity.this);
        mProgressDialog.setMessage("Buscando...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgress(0);
        mProgressDialog.show();
    }

    /*private void selServicos (String salao){
        for (int i = 0; i < ApplicationClass.estabelecimentos.size(); i++){
            if (ApplicationClass.estabelecimentos.get(i).getmNome().equals(salao)){
                //Toast.makeText(this, "Salao: "+ApplicationClass.estabelecimentos.get(i).getmServicos().size(),Toast.LENGTH_SHORT).show();
                for (int j = 0; j < ApplicationClass.estabelecimentos.get(i).getmServicos().size(); j++){
                    servicos.add(ApplicationClass.estabelecimentos.get(i).getmServicos().get(j));
                }
            }
        }
    }*/
}
