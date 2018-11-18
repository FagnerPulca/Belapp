package br.com.belapp.belapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import br.com.belapp.belapp.R;

public class CadastroBasicoActivity extends AppCompatActivity {

    private ImageView mBotaoFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_basico);

        mBotaoFacebook = findViewById(R.id.ivConectarFacebook);

        mBotaoFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginFacebook = new Intent(CadastroBasicoActivity.this,FacebookLoginActivity.class);
                startActivity(loginFacebook);
            }
        });
    }
}
