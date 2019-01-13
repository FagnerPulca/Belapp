package br.com.belapp.belapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.Locale;

import br.com.belapp.belapp.DAO.AgendamentoDAO;
import br.com.belapp.belapp.R;
import br.com.belapp.belapp.enums.StatusAgendamentoEnum;
import br.com.belapp.belapp.model.Agendamento;
import br.com.belapp.belapp.utils.DateUtils;

public class AgendamentoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamento);
        // Configuraçao do toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_agendamento);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        Agendamento agendamento = (Agendamento) getIntent().getSerializableExtra("agendamento");

        TextView tvData = findViewById(R.id.tvDataAgendada);
        TextView tvHora = findViewById(R.id.tvHoraAgendada);
        TextView tvEstabelecimento = findViewById(R.id.tvEstabelecimento);
        TextView tvLocal = findViewById(R.id.tvLocalEndereco);
        TextView tvServico = findViewById(R.id.tvServicoAgendado);
        TextView tvProfissional = findViewById(R.id.tvProfissional);
        TextView tvStatus = findViewById(R.id.tvStatus);

        tvData.setText(String.format(Locale.getDefault(), "Data: %s", agendamento.getmData()));
        tvEstabelecimento.setText(agendamento.getmEstabelecimento().getmNome());
        tvHora.setText(String.format(Locale.getDefault(), "Horário: %s", agendamento.getmHora()));
        tvLocal.setText(String.format(Locale.getDefault(), "%s, nº %s, %s, %s",
                agendamento.getmEstabelecimento().getmRua(),
                agendamento.getmEstabelecimento().getmNumero(),
                agendamento.getmEstabelecimento().getmBairro(),
                agendamento.getmEstabelecimento().getmCidade()));
        tvServico.setText(String.format(Locale.getDefault(), "Serviço Agendado: %s\n Preço: R$%.2f",
                agendamento.getmServico().getmNome(),
                agendamento.getmServico().getmPreco()));
        tvProfissional.setText(String.format(Locale.getDefault(), "Profissional: %s \n Domicílio? %s",
                agendamento.getmProfissional().getNome(),
                (agendamento.getmProfissional().getAtendDomic().equalsIgnoreCase("N")?
                        getString(R.string.app_nao):
                        getString(R.string.app_sim))));
        Button btnCancelar = findViewById(R.id.btnCancelarAgendamento);

        btnCancelar.setOnClickListener(view -> confirmarCancelamento());

        Button btnReagendar = findViewById(R.id.btnEditarAgendamento);
        btnReagendar.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AgendarServicoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("agendamento", agendamento);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        if(!DateUtils.isDataPresente(agendamento.getmData())
                && !DateUtils.isDataFutura(agendamento.getmData())){
            tvStatus.setText(StatusAgendamentoEnum.CONCLUIDO.getStatus());
            tvStatus.setTextColor(Color.BLUE);
            btnCancelar.setVisibility(View.GONE);
            btnReagendar.setVisibility(View.GONE);
        }
        else{
            tvStatus.setText(StatusAgendamentoEnum.AGENDADO.getStatus());
            tvStatus.setTextColor(Color.GREEN -5000);
        }
    }

    public void confirmarCancelamento() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        // Botão sim foi clicado
                        Agendamento agendamento = (Agendamento) getIntent().getSerializableExtra("agendamento");
                        AgendamentoDAO dao = new AgendamentoDAO();
                        DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                Toast.makeText(AgendamentoActivity.this,
                                        getText(R.string.sucess_agendamento_cancelado), Toast.LENGTH_LONG).show();
                            }

                        };
                        dao.remove(agendamento, completionListener);
                        onBackPressed();

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // Botão não foi clicado
                        Toast.makeText(AgendamentoActivity.this,
                                getText(R.string.info_agendamento_nao_cancelado), Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(AgendamentoActivity.this);

        //Coloca o título e a mensagem.
        builder.setTitle(getString(R.string.app_confirmacao));
        builder.setMessage(getString(R.string.app_pergunta_confirmacao_cancelamento_agendamento));

        builder.setPositiveButton("Sim", dialogClickListener);
        builder.setNegativeButton("Não", dialogClickListener);

        builder.show();

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
