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
import android.widget.ImageButton;
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

        isLogado();
        mUserId = getUsuarioAtual().getUid();


        ImageButton ibInformacoes = findViewById(R.id.ibInformacoes);
        ImageButton ibAvaliacoes = findViewById(R.id.ibAvaliacoes);
        TextView tvNomeSalao = findViewById(R.id.tvNomeSalao);
        mLikeButton = findViewById(R.id.star_button);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_salao);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        RecyclerView mRecyclerView = findViewById(R.id.rvServicos);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        if(getIntent().hasExtra("estabelecimento") ){
            mEstabelecimento = (Estabelecimento) getIntent().getSerializableExtra("estabelecimento");
            mSalao = mEstabelecimento.getmEid();
            tvNomeSalao.setText(mEstabelecimento.getmNome());
        }
        if(getIntent().hasExtra("agendamento")){
            mAgendamento = (Agendamento) getIntent().getSerializableExtra("agendamento");
            mEstabelecimento = mAgendamento.getmEstabelecimento();
            mSalao = mEstabelecimento.getmEid();
            tvNomeSalao.setText(mEstabelecimento.getmNome());
        }
        if(getIntent().hasExtra("salao")){
            mSalao = getIntent().getStringExtra("salao");
        }
        if(getIntent().hasExtra("nome")){
            tvNomeSalao.setText(getIntent().getStringExtra("nome"));
        }

        mServicos = new ArrayList<>();

        mMyAdapter = new ServicoAdapter(this, mServicos);
        mRecyclerView.setAdapter(mMyAdapter);

        mDatabaseReference = ConfiguracaoFireBase.getFirebase();

        verificaCurtida();
        buscar();
        dialogBuscando();


        ibAvaliacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PagSalaoActivity.this, PagAvaliacaoActivity.class);
                intent.putExtra("salao", mSalao);
                intent.putExtra("nome", mNome);
                startActivity(intent);
            }
        });


        mLikeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                curtir();
                Toast.makeText(PagSalaoActivity.this, getString(R.string.adicionando_favorito), Toast.LENGTH_LONG).show();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                descurtir();
                Toast.makeText(PagSalaoActivity.this, getString(R.string.retirando_favoritoo), Toast.LENGTH_LONG).show();
            }
        });



        ibInformacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PagSalaoActivity.this, InfoActivity.class);
                intent.putExtra("salao", mSalao); //id do salão é passado para puxar informações do mesmo
                Toast.makeText(PagSalaoActivity.this, "Informações", Toast.LENGTH_SHORT).show();

                startActivity(intent);
            }
        });




    }


    @Override
    public void onItemClicked(int index) {
        Intent intent = new Intent(PagSalaoActivity.this, FuncionariosActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("servico", mServicos.get(index));
        bundle.putSerializable("estabelecimento", mEstabelecimento);
        checarMudancaServico(mServicos.get(index));
        if(mAgendamento != null){
            bundle.putSerializable("agendamento", mAgendamento);
        }
        intent.putExtras(bundle);
        startActivity(intent);
        //Toast.makeText(this, "Salao: "+servicos.get(index).getNome(),Toast.LENGTH_SHORT).show();
    }

    /**
     * Verifica se houve mudança no serviço selecionado, e perde as informações do objeto agendamento
     * @param servico selecionado no recycleview
     */
    private void checarMudancaServico(Servico servico) {
        // se o serviço mudou, as outras informações não são necessarias mais
        if(mAgendamento != null && !mAgendamento.getmServico().equals(servico)){
            mAgendamento.setmServico(servico);
            mAgendamento.setmData(null);
            mAgendamento.setmHora(null);
            mAgendamento.setmProfissional(null);
        }
    }

    private void buscar(){
        Query query = FirebaseDatabase.getInstance().getReference("servicos");

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Servico servico = dataSnapshot.getValue(Servico.class);
                if (servico.getmEstabId().equals(mSalao)){
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

    @Override
    public boolean onSupportNavigateUp() {
        // TODO: Verificar se ha alteracoes antes de voltar
        onBackPressed();
        return true;
    }

    public void curtir(){

        Favorito favorito = new Favorito();
        favorito.setCurtida(1);
        favorito.setIdEstabelecimento(mSalao);
        favorito.setIdCliente(mUserId);
        favorito.salvar();

    }

    public void descurtir(){
        Favorito favorito = new Favorito();
        favorito.setCurtida(1);
        favorito.setIdEstabelecimento(mSalao);
        favorito.setIdCliente(mUserId);
        favorito.remove();

    }

    public void verificaCurtida(){

        mDatabaseReference.child("favoritos").child(mUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    mDatabaseReference.child("favoritos")
                            .child(mUserId)
                            .child(mSalao)
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue() != null) {
                                        String idSalao = dataSnapshot.child("curtida").getValue().toString();
                                        //Log.d(TAG, "borabora:" + idSalao);
                                        if (idSalao.equals(mCurtida)) {
                                            mLikeButton.setLiked(true);
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
        if (mLogado.getCurrentUser() == null) {
            Intent intentAbritCadastro = new Intent(PagSalaoActivity.this , CadastroBasicoActivity.class);
            startActivity(intentAbritCadastro);

        }
    }
}
