package br.com.belapp.belapp.activities;

import android.app.DownloadManager;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Cliente;
import br.com.belapp.belapp.model.ConfiguracaoFireBase;
import br.com.belapp.belapp.model.Estabelecimento;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class BuscaActivity extends AppCompatActivity {

    Button busca_btn;
    EditText busca_txt;
    ListView resultados_busca;
    DatabaseReference firebaseDatabase;
    //ArrayList<Estabelecimento> estabelecimentos;
    Estabelecimento estabelecimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        busca_btn = (Button)findViewById(R.id.buscar_btn);
        busca_txt = (EditText)findViewById(R.id.texto_busca);
        resultados_busca = (ListView)findViewById(R.id.list_view_resultados);

        busca_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }
}
