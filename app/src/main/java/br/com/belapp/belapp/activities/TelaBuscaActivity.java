package br.com.belapp.belapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;


import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Agendamento;
import br.com.belapp.belapp.model.Servico;
import br.com.belapp.belapp.utils.DateUtils;

public class TelaBuscaActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText etEstabelecimento, etEndereco, metDataBusca;
    private ArrayList<String> mIds;
    private ArrayList<String> mIdcateg;
    private ArrayList<String> mServicos, mCategServ;
    private ArrayList<String> mPrecoServ, mNomeServ;
    private ArrayList<Agendamento> mAgendamentos;
    private Calendar now;
    private String mDataSelecionada = "";
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
        Button btnBuscar = findViewById(R.id.btnBuscar);
        metDataBusca = findViewById(R.id.etDataBusca);

        mIds = new ArrayList<>();
        mIdcateg = new ArrayList<>();
        mServicos = new ArrayList<>();
        mCategServ = new ArrayList<>();
        mPrecoServ = new ArrayList<>();
        mNomeServ = new ArrayList<>();
        mAgendamentos = new ArrayList<>();

        buscarServCatPreco();
        dialogBuscando();

        metDataBusca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abre a janela de diálogo para a escolha da data.
                now = Calendar.getInstance();

                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        TelaBuscaActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );

                dpd.setThemeDark(false);
                dpd.vibrate(true);
                dpd.dismissOnPause(true);
                dpd.setFirstDayOfWeek(Calendar.MONDAY);
                dpd.setMinDate(now);
                dpd.setAccentColor(Color.parseColor("#260CE8"));
                dpd.show(getFragmentManager(), "Selecione a data");
            }
        });

        metDataBusca.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    metDataBusca.callOnClick();
                }
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String estabelecimento = etEstabelecimento.getText().toString().trim();
                String endereco = etEndereco.getText().toString().trim();
                String mServcat = etServCat.getText().toString().trim();
                if (!etPreco.getText().toString().equals("")){
                    mPreco = Integer.parseInt(etPreco.getText().toString().trim());
                } else{
                    mPreco = 300;
                }

                double latitude = getIntent().getDoubleExtra("latitude", -8);
                double longitude = getIntent().getDoubleExtra("longitude", -36);

                if (!estabelecimento.isEmpty() || !endereco.isEmpty() || !mServcat.isEmpty() || !etPreco.getText().toString().equals("") || !mDataSelecionada.equals("")){
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
                    intent.putExtra("mDataSelecionada",mDataSelecionada);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("mAgendamentos",mAgendamentos);
                    intent.putExtras(bundle);
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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        now.set(Calendar.YEAR, year);
        now.set(Calendar.MONTH, monthOfYear);
        now.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        mDataSelecionada = DateUtils.converterDataParaString(now);
        metDataBusca.setText( mDataSelecionada);
        buscarPorData(mDataSelecionada);
    }


    private void buscarPorData(String data){
        if(!mDataSelecionada.equals("")) {
            //Limpar o ArrayList
            mAgendamentos.clear();
            //Código adaptado do AgendarServiços
            Query consulta = FirebaseDatabase.getInstance().getReference("agendamentos");
            consulta.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                    Agendamento agendamento = dataSnapshot.getValue(Agendamento.class);
                    if (Objects.requireNonNull(agendamento).getmData().equals(data)) {
                        mAgendamentos.add(agendamento);
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
                    // empty
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    // empty
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {
                    // empty
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // empty
                }
            });
        }
    }
}
