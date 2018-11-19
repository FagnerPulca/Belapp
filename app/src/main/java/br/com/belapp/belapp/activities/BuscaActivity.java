package br.com.belapp.belapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.belapp.belapp.DAO.EstabelecimentoDAO;
import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Estabelecimento;
import br.com.belapp.belapp.presenter.ClickRecyclerView_Interface;
import br.com.belapp.belapp.presenter.EstabelecimentoAdapter;

public class BuscaActivity extends AppCompatActivity implements ClickRecyclerView_Interface {


    private  ArrayList<Estabelecimento> listaEstabelecimentos;
    EstabelecimentoAdapter novoadapter;
    ListView estabelecimentos_LVW;
    // RecyclerTesteAdapter adapter;
   // private List<Pessoa> pessoasListas = new ArrayList<>();



    Button busca_btn;

    EstabelecimentoDAO novoEsDao;
    String res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca);

        listaEstabelecimentos = new ArrayList<>();
        estabelecimentos_LVW = (ListView) findViewById(R.id.listview_resp);
        busca_btn = (Button)findViewById(R.id.buscar_btn);

        novoEsDao = new EstabelecimentoDAO();

        try
        {
            listaEstabelecimentos = novoEsDao.getEstabelecimentos();
        }
        catch (Exception e)
        {
          System.out.println(e.getMessage());
        }


        //chamada da implementação de listview
        novoadapter = new EstabelecimentoAdapter(listaEstabelecimentos, this);

        estabelecimentos_LVW .setAdapter(novoadapter);


        busca_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Tamanho do list: " + listaEstabelecimentos.size());

            }
        });

    }

    @Override
    protected void onStart()
    {
      super.onStart();
    }


    @Override
    public void onCustomClick(Object object) {

    }
}
