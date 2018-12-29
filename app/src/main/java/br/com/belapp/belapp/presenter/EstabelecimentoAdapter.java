package br.com.belapp.belapp.presenter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Estabelecimento;
import br.com.belapp.belapp.utils.ImageDownloaderTask;

public class EstabelecimentoAdapter extends BaseAdapter {

    private final ArrayList<Estabelecimento> estabelecimentos;
    private Activity act;

    public  EstabelecimentoAdapter(ArrayList<Estabelecimento> estabelecimentos, Activity act) {
        this.estabelecimentos = estabelecimentos;
        this.act = act;
    }

    @Override
    public int getCount() {
        return this.estabelecimentos.size() ;
    }

    @Override
    public Object getItem(int position) {
        return this.estabelecimentos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.item_salao, parent, false);


        Estabelecimento novo = estabelecimentos.get(position);

        TextView nome = (TextView)view.findViewById(R.id.tvNomeSalao);
        TextView descricao = (TextView)view.findViewById(R.id.tvEnderecoSalao);

        ImageView imagem = (ImageView)view.findViewById(R.id.ivFotoSalao);

        nome.setText(novo.getmNome());
        descricao.setText(novo.getmDescricao());
        //imagem.setImageResource(R.drawable.java);

        if(novo.getImg() != null){
            new ImageDownloaderTask(imagem).execute(novo.getImg());
        }

        return view;
    }
}
