package br.com.belapp.belapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.belapp.belapp.R;

public class TelaBuscaActivity extends AppCompatActivity {

    EditText etServico, etCidade, etPrecoMin, etPrecoMax;
    Button btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_busca);

        etServico = findViewById(R.id.etServico);
        etCidade = findViewById(R.id.etCidade);
        etPrecoMin = findViewById(R.id.etPrecoMin);
        etPrecoMax = findViewById(R.id.etPrecoMax);
        btnBuscar = findViewById(R.id.btnBuscar);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String servico = etServico.getText().toString().trim();
                String cidade = etServico.getText().toString().trim();
                String precoMin = etPrecoMin.getText().toString().trim();
                String precoMax = etPrecoMax.getText().toString().trim();

                Intent intent = new Intent(TelaBuscaActivity.this, SaloesActivity.class);
                intent.putExtra("servico", servico);
                intent.putExtra("cidade", cidade);
                intent.putExtra("precoMin", precoMin);
                intent.putExtra("precoMax", precoMax);
                startActivity(intent);
            }
        });

    }
}
