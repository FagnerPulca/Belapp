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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.belapp.belapp.DAO.EstabelecimentoDAO;
import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Estabelecimento;
import br.com.belapp.belapp.presenter.ClickRecyclerView_Interface;
import br.com.belapp.belapp.presenter.EstabelecimentoAdapter;

public class BuscaActivity extends AppCompatActivity{

    private static final String TAG = "belapp.activities";
    private ProgressDialog mProgressDialog;

    Estabelecimento estabelecimento = new Estabelecimento();
    Button btnAdd, btnListar;
    EditText etNome, etDescricao;
    EstabelecimentoDAO novoEsDao;

    List<Estabelecimento> estabelecimentos = new ArrayList<Estabelecimento>();
    ArrayAdapter<Estabelecimento> arrayAdapter;
    TextView tvNome, tvDescricao;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca);

        novoEsDao = new EstabelecimentoDAO();

        btnAdd = findViewById(R.id.btnAdd);
        btnListar = findViewById(R.id.btnListar);
        etNome = findViewById(R.id.etNome);
        etDescricao = findViewById(R.id.etDescricao);
        tvNome = findViewById(R.id.tvNome);
        tvDescricao = findViewById(R.id.tvDescricao);


        registerReceiver(desabilitarDialog, new IntentFilter("desbloquear"));

        /*FirebaseApp.initializeApp(BuscaActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();*/

//        estabelecimentos = novoEsDao.getEstabelecimentos();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome = etNome.getText().toString().trim();
                String descricao = etDescricao.getText().toString().trim();
                estabelecimento.setmNome(nome);
                estabelecimento.setmDescricao(descricao);
                novoEsDao.save(estabelecimento);
                Toast.makeText(BuscaActivity.this, "Descricao: " + estabelecimento.getmNome(), Toast.LENGTH_SHORT).show();

                //System.out.println("Tamanho do list: " + listaEstabelecimentos.size());

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
                //dialogBuscando(v);
                //tvNome.setText(estabelecimentos.get(0).getmNome());
                //tvDescricao.setText(estabelecimentos.get(0).getmDescricao());

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
}
