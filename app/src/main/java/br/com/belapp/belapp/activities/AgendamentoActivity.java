package br.com.belapp.belapp.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.Locale;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Agendamento;

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
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
