package br.com.belapp.belapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.database.ValueEventListener;

import br.com.belapp.belapp.R;

public class TelaBuscaActivity extends AppCompatActivity {

    EditText etServico, etCidade, etPreco;
    Button btnBuscar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_busca);

        etServico = findViewById(R.id.etServico);
        etCidade = findViewById(R.id.etCidade);
        btnBuscar = findViewById(R.id.btnBuscar);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String servico = etServico.getText().toString().trim();
                String cidade = etCidade.getText().toString().trim();

                double latitude = getIntent().getDoubleExtra("latitude", -8);
                double longitude = getIntent().getDoubleExtra("longitude", -36);

                if (!cidade.isEmpty() || !servico.isEmpty()){
                    Intent intent = new Intent(TelaBuscaActivity.this, SaloesActivity.class);
                    intent.putExtra("servico", servico);
                    intent.putExtra("cidade", cidade);
                    intent.putExtra("latitude", latitude);
                    intent.putExtra("longitude", longitude);
                    intent.putExtra("categoria", "");
                    startActivity(intent);
                    Toast.makeText(TelaBuscaActivity.this, getString(R.string.resultados), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(TelaBuscaActivity.this, getString(R.string.digite_algum_dado), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
