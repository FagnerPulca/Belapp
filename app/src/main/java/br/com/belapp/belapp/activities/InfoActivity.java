package br.com.belapp.belapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import br.com.belapp.belapp.R;

public class InfoActivity extends AppCompatActivity {

    private TextView tvInfoDescricao, tvInfoEndereco, tvInfoHorario, tvInfoTelefone, tvInfoFacebook, tvInfoInstagram, tvInfoEmail;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        toolbar = (Toolbar) findViewById(R.id.tbInfoSalao);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

    }
}
