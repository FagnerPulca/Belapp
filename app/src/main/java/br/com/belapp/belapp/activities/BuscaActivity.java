package br.com.belapp.belapp.activities;

import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.belapp.belapp.DAO.EnderecoDAO;
import br.com.belapp.belapp.DAO.EstabelecimentoDAO;
import br.com.belapp.belapp.DAO.ProfissionalDAO;
import br.com.belapp.belapp.DAO.ServicoDAO;
import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Endereco;
import br.com.belapp.belapp.model.Estabelecimento;
import br.com.belapp.belapp.model.Profissional;
import br.com.belapp.belapp.model.Servico;
import br.com.belapp.belapp.presenter.ClickRecyclerView_Interface;
import br.com.belapp.belapp.presenter.EstabelecimentoAdapter;

public class BuscaActivity extends AppCompatActivity{

    private static final String TAG = "belapp.activities";
    private ProgressDialog mProgressDialog;

    private Servico servico;
    private ServicoDAO servicoDAO;
    private ArrayList<Servico> servicos;

    Button btnAdd, btnListar;
    EditText etNome, etDescricao;

    ArrayAdapter<Estabelecimento> arrayAdapter;
    TextView tvNome, tvDescricao;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca);

        servicoDAO = new ServicoDAO();
        servicos = new ArrayList<>();

        btnAdd = findViewById(R.id.btnAdd);
        btnListar = findViewById(R.id.btnListar);
        etNome = findViewById(R.id.etNome);
        etDescricao = findViewById(R.id.etDescricao);
        tvNome = findViewById(R.id.tvNome);
        tvDescricao = findViewById(R.id.tvDescricao);


        //registerReceiver(desabilitarDialog, new IntentFilter("desbloquear"));

        /*FirebaseApp.initializeApp(BuscaActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();*/

        //enderecos = enderecoDAO.getEstabelecimentos();

//        estabelecimentos = novoEsDao.getEstabelecimentos();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*servico = new Servico();

                servico.setmCategoria("Corte");
                servico.setmDuracao("00:30");
                servico.setmEstabId("-LS54ly9L9y6RsIuOGid");
                servico.setmNome("Corte simples");
                servico.setmPreco(10.00);
                servico.setmProfissionais("");

                servicoDAO.save(servico);*/



            }
        });

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try{
//                    Toast.makeText(BuscaActivity.this, "Tamanho: " + estabelecimentos.size(), Toast.LENGTH_SHORT).show();
//                    tvNome.setText(estabelecimentos.get(1).getmNome());
//                    tvDescricao.setText(estabelecimentos.get(1).getmDescricao());
//                }catch(Exception e){
//                    Toast.makeText(BuscaActivity.this, "Tamanho: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
                /*Intent intent = new Intent();
                intent.setClass(BuscaActivity.this, BuscaService.class);
                startService(intent);*/



                buscar();
                dialogBuscando(v);
                tvNome.setText(servicos.get(0).getmDuracao());
                tvDescricao.setText(servicos.get(0).getmCategoria());
                Toast.makeText(BuscaActivity.this, "Tamanho: " + servicos.size(), Toast.LENGTH_SHORT).show();

            }

            void dialogBuscando(View v){
                mProgressDialog = new ProgressDialog(BuscaActivity.this);
                mProgressDialog.setMessage("Buscando...");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setProgress(0);
                mProgressDialog.show();
            }
        });



    }



    public BroadcastReceiver desabilitarDialog = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mProgressDialog.dismiss();
            Bundle b = intent.getExtras();
            List<Estabelecimento> estabelecimentos = (List<Estabelecimento>) b.get("estabelecimentos");
            Log.d(TAG, String.format(Locale.getDefault(), "num estabelecimetnos %d %s", estabelecimentos.size(), estabelecimentos.toArray().toString()));
        }
    };

    private void buscar() {
        Query query = FirebaseDatabase.getInstance().getReference("servicos");

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Servico servico = dataSnapshot.getValue(Servico.class);
                servicos.add(servico);
                //myAdapter.notifyDataSetChanged();
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
}
