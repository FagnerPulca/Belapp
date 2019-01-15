package br.com.belapp.belapp.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Estabelecimento;
import br.com.belapp.belapp.utils.ImageDownloaderTask;

public class SalaoAdapter extends RecyclerView.Adapter<SalaoAdapter.ViewHolder> {

    private ArrayList<Estabelecimento> lista;
    ItemClicked activity;

    public interface ItemClicked{
        void onItemClicked(int index);
    }

    public SalaoAdapter (Context context, ArrayList<Estabelecimento> list){
        lista = list;
        activity = (ItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imagem;
        TextView tvNomeSalao, tvEnderecoSalao, tvDistancia;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //ivFotoSalao = itemView.findViewById(R.id.ivFotoSalao);
            tvNomeSalao = itemView.findViewById(R.id.tvNomeSalao);
            tvEnderecoSalao = itemView.findViewById(R.id.tvEnderecoSalao);
            tvDistancia = itemView.findViewById(R.id.tvDistancia);
             imagem = (ImageView)itemView.findViewById(R.id.ivFotoSalao);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(lista.indexOf((Estabelecimento) v.getTag()));
                }
            });
        }
    }

    @NonNull
    @Override
    public SalaoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_salao,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SalaoAdapter.ViewHolder viewHolder, int i) {

        DecimalFormat df2 = new DecimalFormat("#.##");
        if(lista.size() != 0){
            viewHolder.itemView.setTag(lista.get(i));

            viewHolder.tvNomeSalao.setText(lista.get(i).getmNome());
            viewHolder.tvEnderecoSalao.setText(lista.get(i).getmRua()+", "+lista.get(i).getmNumero()+", "+
                    lista.get(i).getmBairro()+", "+lista.get(i).getmCidade());
            viewHolder.tvDistancia.setText("Distância: "+df2.format(lista.get(i).getmDistancia())+" Km");

            String caminhourl = lista.get(i).getImg();
            if(caminhourl != null){
                Log.d("caminho img:", caminhourl);
                try
                {
                    new ImageDownloaderTask(viewHolder.imagem).execute(caminhourl);
                }
                catch(Exception e)
                {
                    System.out.println(e.getMessage());
                }

            }
            //viewHolder.ivFotoSalao.setImageResource(R.drawable.salao_teste);
        }

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}