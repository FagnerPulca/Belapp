package br.com.belapp.belapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Agendamento;
import br.com.belapp.belapp.model.ConfiguracaoFireBase;
import br.com.belapp.belapp.model.Estabelecimento;
import br.com.belapp.belapp.model.Favorito;
import br.com.belapp.belapp.model.Servico;
import br.com.belapp.belapp.presenter.ServicoAdapter;
import br.com.belapp.belapp.servicos.Permissao;

import static br.com.belapp.belapp.database.utils.FirebaseUtils.getUsuarioAtual;
import br.com.belapp.belapp.servicos.Permissao;
import br.com.belapp.belapp.utils.ImageDownloaderTask;

import static br.com.belapp.belapp.database.utils.FirebaseUtils.getUsuarioAtual;

public class PagSalaoActivity extends AppCompatActivity implements ServicoAdapter.ItemClicked {

    private RecyclerView.Adapter mMyAdapter;
    private ArrayList<Servico> mServicos;
    private ProgressDialog mProgressDialog;

    private Estabelecimento mEstabelecimento;
    private Agendamento mAgendamento;

    private String mSalao;
    private String mNome;
    private String mUserId;
    private LikeButton mLikeButton;
    private DatabaseReference mDatabaseReference;
    private String mCurtida = "1";
    private static final String TAG = "PagSalao";
    private FirebaseAuth mLogado = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag_salao);


        ImageButton ibInformacoes = findViewById(R.id.ibInformacoes);
        ImageButton ibAvaliacoes = findViewById(R.id.ibAvaliacoes);
        TextView tvNomeSalao = findViewById(R.id.tvNomeSalao);
        ImageView ivFotoSalao = findViewById(R.id.ivFotoSalao);
        mLikeButton = findViewById(R.id.star_button);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_salao);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.rvServicos);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        mEstabelecimento = (Estabelecimento) getIntent().getSerializableExtra("estabelecimento");

        tvNomeSalao.setText(mEstabelecimento.getmNome());
        mServicos = new ArrayList<>();
        String caminho = mEstabelecimento.getFoto();
        if(caminho != null)
        {
            new ImageDownloaderTask(ivFotoSalao).execute(caminho);
        }

        ArrayList<Object> servicos = new ArrayList<>();


        mMyAdapter = new ServicoAdapter(this, mServicos);
        recyclerView.setAdapter(mMyAdapter);

        buscar();
        dialogBuscando();

    }

    @Override
    public void onItemClicked(int index) {
        Intent intent = new Intent(PagSalaoActivity.this, FuncionariosActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("servico", mServicos.get(index));
        bundle.putSerializable("estabelecimento", mEstabelecimento);
        intent.putExtras(bundle);
        startActivity(intent);
        //Toast.makeText(this, "Salao: "+servicos.get(index).getNome(),Toast.LENGTH_SHORT).show();
    }

    private void buscar(){
        Query query = FirebaseDatabase.getInstance().getReference("servicos");

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Servico servico = dataSnapshot.getValue(Servico.class);
                if (servico.getmEstabId().equals(mEstabelecimento.getmEid())){
                    mServicos.add(servico);
                }
                mMyAdapter.notifyDataSetChanged();
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
