package br.com.belapp.belapp.activities;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Agendamento;

public class AgendarServicoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private static final String TAG = "belapp.activities";
    private EditText mEdtData;
    private Agendamento mAgendamento;
    private ArrayList<Agendamento> mAgendamentosCliente;
    private ArrayList<Agendamento> mAgendamentosEstabelecimento;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar_servico);
        mAgendamentosCliente = new ArrayList<>();
        mAgendamentosEstabelecimento = new ArrayList<>();
        // Configuraçao do toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_agendar_servico);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);
        mEdtData = findViewById(R.id.edtData);

        // Atribui a ação de escolha de data.
        mEdtData.setOnClickListener(new View.OnClickListener() {
            /**
             * Chamado quando  a view é clicada.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                // Abre a janela de diálogo para a escolha da data.
                Calendar now = Calendar.getInstance();
                Calendar[] days = {, };
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        AgendarServicoActivity.this,
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

        mAgendamento = (Agendamento) getIntent().getSerializableExtra("agendamento");
        buscarAgendamentosCliente();
        bucarAgendamentosEstabelecimento();
        Log.d(TAG, "");
    }

    private void bucarAgendamentosEstabelecimento() {
        Query query = FirebaseDatabase.getInstance().getReference("agendamentos").orderByChild("mEstabelecimento");

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                Agendamento agendamento = dataSnapshot.getValue(Agendamento.class);
                if (Objects.requireNonNull(agendamento).getmEstabelecimento().equals(mAgendamento.getmEstabelecimento())){
                    mAgendamentosEstabelecimento.add(agendamento);
                }
//                myAdapter.notifyDataSetChanged();
//                mProgressDialog.dismiss();
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

    private void buscarAgendamentosCliente() {
        Query query = FirebaseDatabase.getInstance().getReference("agendamentos").orderByChild("mCliente");

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                Agendamento agendamento = dataSnapshot.getValue(Agendamento.class);
                if (Objects.requireNonNull(agendamento).getmCliente().equals(mAgendamento.getmCliente())){
                    mAgendamentosCliente.add(agendamento);
                }
//                myAdapter.notifyDataSetChanged();
//                mProgressDialog.dismiss();
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

    public void setmAgendamentosCliente(ArrayList<Agendamento> mAgendamentosCliente) {
        this.mAgendamentosCliente = mAgendamentosCliente;
    }

    public void setmAgendamentosEstabelecimento(ArrayList<Agendamento> mAgendamentosEstabelecimento) {
        this.mAgendamentosEstabelecimento = mAgendamentosEstabelecimento;
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }
}
