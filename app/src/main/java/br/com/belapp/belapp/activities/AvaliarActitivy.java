package br.com.belapp.belapp.activities;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Avaliacao;
import br.com.belapp.belapp.model.Cliente;
import br.com.belapp.belapp.model.ConfiguracaoFireBase;

public class AvaliarActitivy extends AppCompatActivity {

    private TextView tvNome;
    private TextView tvNota;
    private EditText tvComentario;
    private ImageView ivEstrela1,ivEstrela2,ivEstrela3,ivEstrela4,ivEstrela5;
    private Button btEnviar;
    private double mNota = 0;
    private String mIdSalao;
    private String mNomeSalao;
    private String mIdCliente;
    private String mNomeCliente = "";
    private ArrayList<Cliente> clientes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliar_actitivy);

        Toolbar toolbar = findViewById(R.id.toolbar_avaliar);
        toolbar.setTitle(R.string.title_activity_avaliar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        tvNome = findViewById(R.id.tv_nome_avaliar);
        tvNota = findViewById(R.id.tv_nota_avaliar);
        tvComentario = findViewById(R.id.et_comentario_avaliar);
        ivEstrela1 = findViewById(R.id.iv_estrela1);
        ivEstrela2 = findViewById(R.id.iv_estrela2);
        ivEstrela3 = findViewById(R.id.iv_estrela3);
        ivEstrela4 = findViewById(R.id.iv_estrela4);
        ivEstrela5 = findViewById(R.id.iv_estrela5);
        btEnviar = findViewById(R.id.bt_enviar_avaliar);

        mIdCliente = getIntent().getExtras().getString("idCliente");
        mIdSalao = getIntent().getExtras().getString("idEstabelecimento");
        mNomeSalao = getIntent().getExtras().getString("nome");

        tvNome.setText(mNomeSalao);

        //buscando cliente
        clientes = new ArrayList<>();
        buscarNomeCliente(mIdCliente);

                ivEstrela1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preencherEstrelas(1);
                tvNota.setText("Desapontado");
                mNota = 1;
            }
        });

        ivEstrela2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preencherEstrelas(2);
                tvNota.setText("Não tão bom");
                mNota = 2;
            }
        });

        ivEstrela3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preencherEstrelas(3);
                tvNota.setText("Bom");
                mNota = 3;
            }
        });

        ivEstrela4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preencherEstrelas(4);
                tvNota.setText("Gostei");
                mNota = 4;
            }
        });

        ivEstrela5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preencherEstrelas(5);
                tvNota.setText("Excelente");
                mNota = 5;
            }
        });

        btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mNota != 0) {
                    Avaliacao avaliar = new Avaliacao();
                    avaliar.setmComentario(tvComentario.getText().toString());
                    avaliar.setmControle(gerarControle());
                    avaliar.setmData(pegarData());
                    avaliar.setmFoto(buscarFoto());
                    avaliar.setmNome(mNomeCliente);
                    avaliar.setmNota(mNota);
                    salvarAvaliacao(avaliar,mIdSalao,mIdCliente);
                    //Log.d("CLIENTE", "comentario: "+avaliar.getmComentario()+" data: "+ avaliar.getmData()+ " nome: "+avaliar.getmNome()+ " controle: "+ avaliar.getmControle()+" nota: "+String.valueOf(avaliar.getmNota()));
                    Toast.makeText(AvaliarActitivy.this,"Avaliação foi salva!",Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(AvaliarActitivy.this,"Dê uma nota!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void preencherEstrelas(int nota){
        int cheia = R.drawable.estrela_cheia;
        int vazia = R.drawable.estrela_vazia;

        switch (nota){
            case 1:
                ivEstrela1.setImageResource(cheia);
                ivEstrela2.setImageResource(vazia);
                ivEstrela3.setImageResource(vazia);
                ivEstrela4.setImageResource(vazia);
                ivEstrela5.setImageResource(vazia);
                break;
            case 2:
                ivEstrela1.setImageResource(cheia);
                ivEstrela2.setImageResource(cheia);
                ivEstrela3.setImageResource(vazia);
                ivEstrela4.setImageResource(vazia);
                ivEstrela5.setImageResource(vazia);
                break;
            case 3:
                ivEstrela1.setImageResource(cheia);
                ivEstrela2.setImageResource(cheia);
                ivEstrela3.setImageResource(cheia);
                ivEstrela4.setImageResource(vazia);
                ivEstrela5.setImageResource(vazia);
                break;
            case 4:
                ivEstrela1.setImageResource(cheia);
                ivEstrela2.setImageResource(cheia);
                ivEstrela3.setImageResource(cheia);
                ivEstrela4.setImageResource(cheia);
                ivEstrela5.setImageResource(vazia);
                break;
            case 5:
                ivEstrela1.setImageResource(cheia);
                ivEstrela2.setImageResource(cheia);
                ivEstrela3.setImageResource(cheia);
                ivEstrela4.setImageResource(cheia);
                ivEstrela5.setImageResource(cheia);
                break;
            default:
                break;
        }
    }

    private String pegarData(){
        String data = "";
        Date date = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        data = formatador.format(date);
        return  data;
    }

    private int gerarControle(){
        String data = "";
        int controle = 0;
        Date date = new Date();
        data += String.valueOf(date.getYear()+ 1900);
        data += String.valueOf(date.getMonth() + 1);
        data += String.valueOf(date.getDate());
        controle = Integer.parseInt(data);
        return controle;
    }


    private void buscarNomeCliente(String id){
        DatabaseReference raiz = FirebaseDatabase.getInstance().getReference();
        DatabaseReference cliente = raiz.child("clientes").child(id);

        cliente.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Cliente cliente1 = dataSnapshot.getValue(Cliente.class);
                clientes.add(cliente1);
                mNomeCliente = cliente1.getmNome();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String buscarFoto(){
        String endereco = "null";

        return endereco;
    }

    private void salvarAvaliacao(Avaliacao avaliacao, String idSalao, String idCliente){
        DatabaseReference databaseReference = ConfiguracaoFireBase.getFirebase();
        databaseReference.child("avaliacoes").child(idSalao).child(idCliente).setValue(avaliacao);
    }

    @Override
    public boolean onSupportNavigateUp() {
        // TODO: Verificar se ha alteracoes antes de voltar
        onBackPressed();
        return true;
    }
}
