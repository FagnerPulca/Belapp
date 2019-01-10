package br.com.belapp.belapp.activities;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import br.com.belapp.belapp.DAO.AgendamentoDAO;
import br.com.belapp.belapp.R;
import br.com.belapp.belapp.exceptions.ValidationException;
import br.com.belapp.belapp.model.Agendamento;
import br.com.belapp.belapp.model.HorarioAtendimento;
import br.com.belapp.belapp.utils.DateUtils;
import br.com.belapp.belapp.utils.StringUtils;

public class AgendarServicoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private static final String TAG = "belapp.activities";
    private EditText mEdtData;
    private Agendamento mAgendamento;
    private ArrayList<Agendamento> mAgendamentosCliente;
    private ArrayList<Agendamento> mAgendamentosProfissional;

    private Spinner mSpHorariosDisponiveis;
    private List<String> mHorariosDisponiveis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar_servico);
        mAgendamentosCliente = new ArrayList<>();
        mAgendamentosProfissional = new ArrayList<>();
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
                //noinspection deprecation
                dpd.show(getFragmentManager(), "Selecione a data");
            }


        });

        mAgendamento = (Agendamento) getIntent().getSerializableExtra("agendamento");
        TextView tvServico = findViewById(R.id.tvServicoAgendar);
        TextView tvEstabelecimento = findViewById(R.id.tvEstabelecimentoAgendar);
        TextView tvProfissional = findViewById(R.id.tvProfissionalAgendar);
        TextView tvPreco = findViewById(R.id.tvPrecoAgendar);
        TextView tvDiasFuncionamento = findViewById(R.id.tvDiasFuncionamento);


        tvServico.setText(String.format(Locale.getDefault(), "Serviço: %s",
                mAgendamento.getmServico().getmNome()));
        tvPreco.setText(String.format(Locale.getDefault(), "Preço: %s",
                StringUtils.getDinheiro(mAgendamento.getmServico().getmPreco())));
        tvProfissional.setText(String.format(Locale.getDefault(), "Profissional: %s",
                mAgendamento.getmProfissional().getNome()));
        tvEstabelecimento.setText(String.format(Locale.getDefault(), "Estabelecimento: %s",
                mAgendamento.getmEstabelecimento().getmNome()));

        tvDiasFuncionamento.setText(
                String.format("%s: %s", getString(R.string.app_dias_funcionamento),
                        mAgendamento.getmEstabelecimento().getDiasFuncionamento()));


        Button btnAgendar = findViewById(R.id.btnAgendar);
        btnAgendar.setOnClickListener(view -> {
            try {
                validar();
                mAgendamento.setmData(mEdtData.getText().toString());
                mAgendamento.setmHora(((String) mSpHorariosDisponiveis.getSelectedItem()));
                AgendamentoDAO dao = new AgendamentoDAO();
                dao.save(mAgendamento);
                Toast.makeText(AgendarServicoActivity.this,
                        getString(R.string.sucess_agendamento_realizado), Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setClass(AgendarServicoActivity.this, ClienteLogadoActivity.class);
                startActivity(intent);
                finish();
            } catch (ValidationException e) {
                Toast.makeText(AgendarServicoActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        mSpHorariosDisponiveis = findViewById(R.id.spHorariosAgendar);

        mHorariosDisponiveis = new ArrayList<>();
        mHorariosDisponiveis.add(getString(R.string.app_selecionar));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, mHorariosDisponiveis);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpHorariosDisponiveis.setAdapter(dataAdapter);

        buscarAgendamentosCliente();
        bucarAgendamentosProfissional();
        Log.d(TAG, "");
    }

    private void bucarAgendamentosProfissional() {
        Query query = FirebaseDatabase.getInstance().getReference("agendamentos").orderByChild("mProfissional");

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                Agendamento agendamento = dataSnapshot.getValue(Agendamento.class);
                if (Objects.requireNonNull(agendamento).getmProfissional().equals(mAgendamento.getmProfissional())){
                    mAgendamentosProfissional.add(agendamento);
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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        mEdtData.setText(String.format(Locale.getDefault(), "%d/%d/%d", dayOfMonth, (monthOfYear+1), year));
        filtrarHorarios();
    }

    public void filtrarHorarios() {
        mHorariosDisponiveis.clear();
        mHorariosDisponiveis.add(getString(R.string.app_selecionar));
        HorarioAtendimento horariosDiaSelecionado = new HorarioAtendimento();
        for(HorarioAtendimento horarioAtendimento: mAgendamento.getmEstabelecimento()
                .getmHorariosAtendimento()){
            if(horarioAtendimento!= null && horarioAtendimento.getmDiaFuncionamento() == DateUtils.getDiaDaSemanaEmData(mEdtData.getText().toString())){
                horariosDiaSelecionado = horarioAtendimento;
            }
        }
        // gera todos os horarios ate as 23 horas
        for (int i = horariosDiaSelecionado.getmAbertura(); i < horariosDiaSelecionado.getmFechamento(); i += mAgendamento.getmServico().getmDuracao()){

            mHorariosDisponiveis.add(DateUtils.getFormatoHora(i/60, i%60));
        }

        if (mAgendamentosCliente.size() > 0 && mAgendamentosProfissional.size() > 0) {
            for (int i = 0; i < mAgendamentosCliente.size(); i++) {
                for (int j = 0; j < mAgendamentosProfissional.size(); j++){
                    // Verifica se ha agendamentos para a data selecionada
                    if (mAgendamentosCliente.get(i).getmData().equalsIgnoreCase(mEdtData.getText().toString())
                            && mAgendamentosProfissional.get(j).getmData().equalsIgnoreCase(mEdtData.getText().toString())){
                        // verifica se ha horarios con
                        if(mAgendamentosCliente.get(i).getmHora().equalsIgnoreCase(mAgendamentosProfissional.get(j).getmHora())
                                && mHorariosDisponiveis.contains(mAgendamentosCliente.get(i).getmHora())){
                                mHorariosDisponiveis.remove(mAgendamentosCliente.get(i).getmHora());
                        }
                    }
                }
            }
        }
        else{
            // Caso o profissional possua agendamentos, mas o cliente nao
            if(mAgendamentosProfissional.size() > 0){
                for (Agendamento agendamento: mAgendamentosProfissional){
                    if(agendamento.getmData().equalsIgnoreCase(mEdtData.getText().toString())
                            && mHorariosDisponiveis.contains(agendamento.getmHora())){
                        mHorariosDisponiveis.remove(agendamento.getmHora());
                    }
                }
            }
            // caso o cliente possua agendamentos, mas o profissional nao
            else{

                for (Agendamento agendamento: mAgendamentosCliente){
                    if(agendamento.getmData().equalsIgnoreCase(mEdtData.getText().toString())
                            && mHorariosDisponiveis.contains(agendamento.getmHora())){
                            mHorariosDisponiveis.remove(agendamento.getmHora());
                    }
                }
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, mHorariosDisponiveis);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpHorariosDisponiveis.setAdapter(dataAdapter);
        // Valida se ha datas disponiveis
        try {
            validar();
        } catch (ValidationException e) {
            Toast.makeText(AgendarServicoActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            if(mHorariosDisponiveis.size() == 1) {
                mEdtData.callOnClick();
            }
        }
    }

    private void validar() throws ValidationException {
        if(TextUtils.isEmpty(mEdtData.getText().toString())){
            throw new ValidationException(getString(R.string.error_selecione_uma_data));
        }
        boolean isAberto = false;
        for(HorarioAtendimento horarioAtendimento: mAgendamento.getmEstabelecimento()
                .getmHorariosAtendimento()){
            if(horarioAtendimento!= null && horarioAtendimento.getmDiaFuncionamento() == DateUtils.getDiaDaSemanaEmData(mEdtData.getText().toString())){
                isAberto = true;
            }
        }
        if(!isAberto){
            throw new ValidationException(getString(R.string.error_estabelecimento_fechado_na_data));
        }
        if(mHorariosDisponiveis.size() == 1){
            throw new ValidationException(getString(R.string.error_nao_ha_horario_disponivel_para_data));
        }

        if(((String) mSpHorariosDisponiveis.getSelectedItem()).equalsIgnoreCase(getString(R.string.app_selecionar))){
            throw new ValidationException(getString(R.string.error_selecione_um_horario));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
