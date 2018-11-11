package br.com.belapp.belapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class Busca_view extends AppCompatActivity {

    Button busca_btn;
    EditText busca_txt;
    ListView resultados_busca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca);

        busca_btn = (Button)findViewById(R.id.buscar_btn);
        busca_txt = (EditText)findViewById(R.id.texto_busca);
        resultados_busca = (ListView)findViewById(R.id.list_view_resultados);

        //eventos componentes
        busca_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
