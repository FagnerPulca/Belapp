package br.com.belapp.belapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.Toast;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.View;


import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


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
import br.com.belapp.belapp.model.ConfiguracaoFireBase;
import br.com.belapp.belapp.model.Estabelecimento;
import br.com.belapp.belapp.model.Favorito;
import br.com.belapp.belapp.model.Servico;
import br.com.belapp.belapp.presenter.ServicoAdapter;

import static br.com.belapp.belapp.database.utils.FirebaseUtils.getUsuarioAtual;

public class PagSalaoActivity extends AppCompatActivity implements ServicoAdapter.ItemClicked {

    ImageButton ibServicos, ibInformacoes, ibAvaliacoes;
    ImageView ivFotoSalao;
    TextView tvNomeSalao;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter myAdapter;
    ArrayList<Servico> servicos;
    private ProgressDialog mProgressDialog;

    private Estabelecimento mEstabelecimento;

    String salao;
    String nome;
    private String userId;
    private LikeButton likeButton;
    private DatabaseReference databaseReference;
    private String curtida = "1";
    private static final String TAG = "PagSalao";
    private FirebaseAuth logado = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag_salao);

        isLogado();
        userId = getUsuarioAtual().getUid();


        ibServicos = findViewById(R.id.ibServicos);
        ibInformacoes = findViewById(R.id.ibInformacoes);
        ibAvaliacoes = findViewById(R.id.ibAvaliacoes);
        ivFotoSalao = findViewById(R.id.ivFotoSalao);
        tvNomeSalao = findViewById(R.id.tvNomeSalao);
        likeButton = findViewById(R.id.star_button);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_salao);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.rvServicos);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        mEstabelecimento = (Estabelecimento) getIntent().getSerializableExtra("estabelecimento");
        tvNomeSalao.setText(mEstabelecimento.getmNome());
        salao = getIntent().getStringExtra("salao");
        servicos = new ArrayList<>();

        myAdapter = new ServicoAdapter(this, servicos);
        recyclerView.setAdapter(myAdapter);

        databaseReference = ConfiguracaoFireBase.getFirebase();

        verificaCurtida();
        buscar();
        dialogBuscando();


        ibAvaliacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PagSalaoActivity.this, PagAvaliacaoActivity.class);
                intent.putExtra("salao", salao);
                intent.putExtra("nome", nome);
                startActivity(intent);
            }
        });


        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                curti();
                Toast.makeText(PagSalaoActivity.this, getString(R.string.adicionando_favorito), Toast.LENGTH_LONG).show();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                descurti();
                Toast.makeText(PagSalaoActivity.this, getString(R.string.retirando_favoritoo), Toast.LENGTH_LONG).show();
            }
        });



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

        Bundle bundle = new Bundle();
        bundle.putSerializable("servico", servicos.get(index));
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

    @Override
    public boolean onSupportNavigateUp() {
        // TODO: Verificar se ha alteracoes antes de voltar
        onBackPressed();
        return true;
    }

    public void curti(){

        Favorito favorito = new Favorito();
        favorito.setCurtida(1);
        favorito.setIdEstabelecimento(salao);
        favorito.setIdCliente(userId);
        favorito.salvar();

    }

    public void descurti(){
        Favorito favorito = new Favorito();
        favorito.setCurtida(1);
        favorito.setIdEstabelecimento(salao);
        favorito.setIdCliente(userId);
        favorito.remove();

    }

    public void verificaCurtida(){

        databaseReference.child("favoritos").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    databaseReference.child("favoritos")
                            .child(userId)
                            .child(salao)
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue() != null) {
                                        String idSalao = dataSnapshot.child("curtida").getValue().toString();
                                        //Log.d(TAG, "borabora:" + idSalao);
                                        if (idSalao.equals(curtida)) {
                                            likeButton.setLiked(true);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // empty
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





            }

    public void isLogado() {
        if (logado.getCurrentUser() == null) {
            Intent intentAbritCadastro = new Intent(PagSalaoActivity.this , CadastroBasicoActivity.class);
            startActivity(intentAbritCadastro);

        }
    }
}
