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
import android.widget.SeekBar;
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

public class TelaBuscaActivity extends AppCompatActivity {

    EditText etEstabelecimento, etEndereco;
    TextView tvResul;
    Button btnBuscar;
    ArrayList<String> ids;
    ArrayList<String> idcateg;
    ArrayList<String> servicos, categServ;
    ArrayList<String> precoServ, nomeServ;

    String servcat;
    int preco = 300;

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

        SeekBar sbPreco;
        sbPreco = findViewById(R.id.sbPreco);
        TextView tvPreco = findViewById(R.id.tvPreco);
        EditText etServCat = findViewById(R.id.etServCat);

        etEstabelecimento = findViewById(R.id.etEstabelecimento);
        etEndereco = findViewById(R.id.etEndereco);
        btnBuscar = findViewById(R.id.btnBuscar);
        tvResul = findViewById(R.id.tvResul);

        ids = new ArrayList<>();
        idcateg = new ArrayList<>();
        servicos = new ArrayList<>();
        categServ = new ArrayList<>();
        precoServ = new ArrayList<>();
        nomeServ = new ArrayList<>();

        sbPreco.setProgress(300);
        //estabelecimentos = new ArrayList<>();
        buscarServCatPreco();
        dialogBuscando();

        sbPreco.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvPreco.setText("R$ "+String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                tvPreco.setText("R$ "+String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvPreco.setText("R$ "+String.valueOf(seekBar.getProgress()));
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String estabelecimento = etEstabelecimento.getText().toString().trim();
                String endereco = etEndereco.getText().toString().trim();
                servcat = etServCat.getText().toString().trim();
                preco = sbPreco.getProgress();

                double latitude = getIntent().getDoubleExtra("latitude", -8);
                double longitude = getIntent().getDoubleExtra("longitude", -36);

                if (!estabelecimento.isEmpty() || !endereco.isEmpty() || !servcat.isEmpty()){
                    Intent intent = new Intent(TelaBuscaActivity.this, SaloesActivity.class);
                    intent.putExtra("estabelecimento", estabelecimento);
                    intent.putExtra("endereco", endereco);
                    intent.putExtra("servcat", servcat);
                    intent.putExtra("preco", preco);
                    intent.putExtra("latitude", latitude);
                    intent.putExtra("longitude", longitude);
                    intent.putExtra("categoria", "");
                    intent.putExtra("ids", ids);
                    intent.putExtra("idcateg", idcateg);
                    intent.putExtra("servicos", servicos);
                    intent.putExtra("categServ", categServ);
                    intent.putExtra("precoServ", precoServ);
                    intent.putExtra("nomeServ", nomeServ);
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
                servicos.add(servico.getmEstabId()); //id estabelecimento
                categServ.add(servico.getmCategoria()); //categoria do serviço
                precoServ.add(String.valueOf(servico.getmPreco())); //preco do serviço
                nomeServ.add(servico.getmNome());

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
