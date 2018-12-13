package br.com.belapp.belapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Estabelecimento;

public class InfoActivity extends AppCompatActivity {

    private TextView tvInfoDescricao, tvInfoEndereco, tvInfoHorario,
            tvInfoTelefone, tvInfoFacebook, tvInfoInstagram, tvInfoSite;
    private Toolbar toolbar;
    private String salao;
    private ProgressDialog mProgressDialog;
    private Estabelecimento estabelecimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        toolbar = findViewById(R.id.tbInfoSalao);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        salao = getIntent().getStringExtra("salao");

        tvInfoDescricao = findViewById(R.id.tvInfoDescricao);
        tvInfoEndereco = findViewById(R.id.tvInfoEndereco);
        tvInfoHorario = findViewById(R.id.tvInfoHorario);
        tvInfoTelefone = findViewById(R.id.tvInfoTelefone);
        tvInfoFacebook = findViewById(R.id.tvInfoFacebook);
        tvInfoInstagram = findViewById(R.id.tvInfoInstagram);
        tvInfoSite = findViewById(R.id.tvInfoSite);

        estabelecimento = new Estabelecimento();

        buscar();
        dialogBuscando();

        tvInfoEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:"
                        +estabelecimento.getmLatitude()+","
                        +estabelecimento.getmLongitude()+"?q="
                        +estabelecimento.getmRua()+", "
                        +estabelecimento.getmNumero()+", "
                        +estabelecimento.getmCidade());

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });

        tvInfoTelefone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!estabelecimento.getmTelefone().equals("-")){
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+estabelecimento.getmTelefone()));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            }
        });

        tvInfoFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!estabelecimento.getmLinkFacebook().equals("-")){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(estabelecimento.getmLinkFacebook()));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            }
        });

        tvInfoInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!estabelecimento.getmLinkInstagram().equals("-")){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(estabelecimento.getmLinkInstagram()));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            }
        });

        tvInfoSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!estabelecimento.getmLinkSite().equals("-")){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+estabelecimento.getmLinkSite()));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void buscar(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("estabelecimentos/"+salao);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                estabelecimento = dataSnapshot.getValue(Estabelecimento.class);
                mProgressDialog.dismiss();

                String endereco = estabelecimento.getmRua()+", "+estabelecimento.getmNumero()+", "
                        +estabelecimento.getmBairro()+", "+estabelecimento.getmCidade()+", "+estabelecimento.getmCep();
                tvInfoEndereco.setText(endereco);
                tvInfoDescricao.setText(estabelecimento.getmDescricao());
                tvInfoHorario.setText(estabelecimento.getmHorarios());
                tvInfoTelefone.setText(estabelecimento.getmTelefone());
                tvInfoFacebook.setText(estabelecimento.getmLinkFacebook());
                tvInfoInstagram.setText(estabelecimento.getmLinkInstagram());
                tvInfoSite.setText(estabelecimento.getmLinkSite());

                tvInfoEndereco.setPaintFlags(tvInfoEndereco.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                tvInfoTelefone.setPaintFlags(tvInfoTelefone.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                tvInfoFacebook.setPaintFlags(tvInfoFacebook.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                tvInfoInstagram.setPaintFlags(tvInfoInstagram.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                tvInfoSite.setPaintFlags(tvInfoSite.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(InfoActivity.this,"The read failed: "+databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void dialogBuscando(){
        mProgressDialog = new ProgressDialog(InfoActivity.this);
        mProgressDialog.setMessage("Buscando...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgress(0);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
