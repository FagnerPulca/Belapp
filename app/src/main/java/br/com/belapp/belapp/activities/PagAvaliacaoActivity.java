package br.com.belapp.belapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Avaliacao;
import br.com.belapp.belapp.model.ConfiguracaoFireBase;
import br.com.belapp.belapp.model.Servico;
import br.com.belapp.belapp.presenter.AvaliacaoAdapter;

import static android.widget.Toast.makeText;

public class PagAvaliacaoActivity extends AppCompatActivity {

    ImageButton ibServicos, ibInformacoes, ibAvaliacoes;
    ImageView ivFotoSalao, ivAvalicao;
    TextView tvNomeSalao, tvInformacao;
    RecyclerView recyclerView;
    AvaliacaoAdapter myAdpter;
    ArrayList<Avaliacao> avaliacoes;
    private ProgressDialog mProgressDialog;
    String salao;
    String nome;
    double mSomatorio = 0;
    int mContador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag_avaliacao);

        Toolbar toolbar = findViewById(R.id.toolbar_avaliacao);
        toolbar.setTitle(R.string.title_activity_avaliacoes);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        ibServicos = findViewById(R.id.ibServicos);
        ibInformacoes = findViewById(R.id.ibInformacoes);
        ibAvaliacoes = findViewById(R.id.ibAvaliacoes);
        ivFotoSalao = findViewById(R.id.ivFotoSalao);
        tvNomeSalao = findViewById(R.id.tvNomeSalao);
        tvInformacao = findViewById(R.id.tvInformacao);
        ivAvalicao = findViewById(R.id.ivPagAvaliacao);

        salao = getIntent().getExtras().getString("salao");
        nome = getIntent().getExtras().getString("nome");
        tvNomeSalao.setText(nome);

        ibServicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        ibAvaliacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser usuario = ConfiguracaoFireBase.getFirebaseAutenticacao().getCurrentUser();
                if (usuario != null) {
                    Intent intent = new Intent(PagAvaliacaoActivity.this,AvaliarActitivy.class);
                    intent.putExtra("nome",nome);
                    intent.putExtra("idEstabelecimento",salao);
                    intent.putExtra("idCliente",usuario.getUid());
                    startActivity(intent);
                } else {
                    Toast.makeText(PagAvaliacaoActivity.this,"Você precisa entrar para avaliar!",Toast.LENGTH_LONG).show();
                }
            }
        });

        //RecycleView da avaliação
        avaliacoes = new ArrayList<>();

        buscar();
        //dialogBuscando();

        recyclerView = findViewById(R.id.rvAvaliacoes);
        myAdpter = new AvaliacaoAdapter(this,avaliacoes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdpter);
        //recyclerView.setHasFixedSize(false);

    }

    private void buscar() {
        DatabaseReference raiz = FirebaseDatabase.getInstance().getReference();
        DatabaseReference clientes = raiz.child("avaliacoes").child(salao);

        clientes.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Avaliacao avaliacao = dataSnapshot.getValue(Avaliacao.class);
                avaliacoes.add(avaliacao);
                contar(avaliacao.getmNota());
                anotar();
                myAdpter.notifyDataSetChanged();
                //mProgressDialog.dismiss();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void dialogBuscando(){
        mProgressDialog = new ProgressDialog(PagAvaliacaoActivity.this);
        mProgressDialog.setMessage("Buscando...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgress(0);
        mProgressDialog.show();
    }

    private void anotar(){
        int quantidade = mContador;
        double somatorio = mSomatorio;
        double resultado = 0;

        if(quantidade == 0) {tvInformacao.setText("Ninguém avaliou");}
        else if(quantidade == 1){tvInformacao.setText("1 cliente avaliou");}
        else {tvInformacao.setText(quantidade + " clientes avaliaram");}

        if(somatorio != 0){
            resultado = somatorio/quantidade;
            ivAvalicao.setImageResource(carregarAvaliacao(resultado));
        }
        Log.i("ARRAY4",String.valueOf(resultado));
    }

    private int carregarAvaliacao(double i) {
        int avaliacao;
        double nota = i;

        if(nota > 4.5){
            avaliacao = R.drawable.estrela_5;
        } else if(nota > 4){
            avaliacao = R.drawable.estrela_4_5;
        } else if(nota > 3.5){
            avaliacao = R.drawable.estrela_4;
        } else if(nota > 3){
            avaliacao = R.drawable.estrela_3_5;
        } else if(nota > 2.5){
            avaliacao = R.drawable.estrela_3;
        } else if(nota > 2){
            avaliacao = R.drawable.estrela_2_5;
        } else if(nota > 1.5){
            avaliacao = R.drawable.estrela_2;
        } else if(nota > 1){
            avaliacao = R.drawable.estrela_1_5;
        } else if(nota > 0.5){
            avaliacao = R.drawable.estrela_1;
        } else if(nota > 0){
            avaliacao = R.drawable.estrela_0_5;
        } else {
            avaliacao = R.drawable.estrela_0;
        }
        return avaliacao;
    }

    private void contar(double nota){
        mSomatorio += nota;
        mContador++;
        Log.d("CONTAR: ",String.valueOf(nota));
    }

    @Override
    public boolean onSupportNavigateUp() {
        // TODO: Verificar se ha alteracoes antes de voltar
        onBackPressed();
        return true;
    }
}
