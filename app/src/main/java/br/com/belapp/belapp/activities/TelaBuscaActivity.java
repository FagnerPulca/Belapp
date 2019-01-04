package br.com.belapp.belapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import java.util.ArrayList;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Servico;

public class TelaBuscaActivity extends AppCompatActivity {

    private EditText etEstabelecimento, etEndereco;
    private TextView tvResul;
    private Button btnBuscar;
    private ArrayList<String> mIds;
    private ArrayList<String> mIdcateg;
    private ArrayList<String> mServicos, mCategServ;
    private ArrayList<String> mPrecoServ, mNomeServ;

    private String mServcat;
    private int mPreco = 300;

    //ArrayList<Estabelecimento> estabelecimentos;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_busca);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_Pesquisar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        EditText etServCat = findViewById(R.id.etServCat);
        EditText etPreco = findViewById(R.id.etPreco);

        etEstabelecimento = findViewById(R.id.etEstabelecimento);
        etEndereco = findViewById(R.id.etEndereco);
        btnBuscar = findViewById(R.id.btnBuscar);
        tvResul = findViewById(R.id.tvResul);

        mIds = new ArrayList<>();
        mIdcateg = new ArrayList<>();
        mServicos = new ArrayList<>();
        mCategServ = new ArrayList<>();
        mPrecoServ = new ArrayList<>();
        mNomeServ = new ArrayList<>();


        buscarServCatPreco();
        dialogBuscando();

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String estabelecimento = etEstabelecimento.getText().toString().trim();
                String endereco = etEndereco.getText().toString().trim();
                mServcat = etServCat.getText().toString().trim();
                if (!etPreco.getText().toString().equals("")){
                    mPreco = Integer.parseInt(etPreco.getText().toString().trim());
                } else{
                    mPreco = 300;
                }

                double latitude = getIntent().getDoubleExtra("latitude", -8);
                double longitude = getIntent().getDoubleExtra("longitude", -36);

                if (!estabelecimento.isEmpty() || !endereco.isEmpty() || !mServcat.isEmpty() || !etPreco.getText().toString().equals("")){
                    Intent intent = new Intent(TelaBuscaActivity.this, SaloesActivity.class);
                    intent.putExtra("mEstabelecimento", estabelecimento);
                    intent.putExtra("mEndereco", endereco);
                    intent.putExtra("mServcat", mServcat);
                    intent.putExtra("mPreco", mPreco);
                    intent.putExtra("mLatitude", latitude);
                    intent.putExtra("mLongitude", longitude);
                    intent.putExtra("mCategoria", "");
                    intent.putExtra("mIds", mIds);
                    intent.putExtra("mIdcateg", mIdcateg);
                    intent.putExtra("mServicos", mServicos);
                    intent.putExtra("mCategServ", mCategServ);
                    intent.putExtra("mPrecoServ", mPrecoServ);
                    intent.putExtra("mNomeServ", mNomeServ);
                    startActivity(intent);

                }
                else {
                    Toast.makeText(TelaBuscaActivity.this, getString(R.string.digite_algum_dado), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void buscarServCatPreco(){
        Query query = FirebaseDatabase.getInstance().getReference("servicos");

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Servico servico = dataSnapshot.getValue(Servico.class);
                mServicos.add(servico.getmEstabId()); //id estabelecimento
                mCategServ.add(servico.getmCategoria()); //categoria do serviço
                mPrecoServ.add(String.valueOf(servico.getmPreco())); //mPreco do serviço
                mNomeServ.add(servico.getmNome());

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
        mProgressDialog = new ProgressDialog(TelaBuscaActivity.this);
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
}
