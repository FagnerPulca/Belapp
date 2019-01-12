package br.com.belapp.belapp.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Estabelecimento;
import br.com.belapp.belapp.model.Promocoes;

public class PromocaoAdapter extends RecyclerView.Adapter<PromocaoAdapter.ViewHolder> {

    private ArrayList<Estabelecimento> lista;
    private ArrayList<Promocoes> lista2;
    ItemClicked activity;
    private ImageView foto;

    public interface ItemClicked{
        void onItemClicked(int index);
    }

    public PromocaoAdapter(Context context, ArrayList<Estabelecimento> list,ArrayList<Promocoes> list2){
        lista = list;
        lista2 =list2;
        activity = (ItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivFotoPromo;
        TextView tvNomeSalao, tvTitulo, tvDescricao;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivFotoPromo = itemView.findViewById(R.id.ivFotoSalao);
            tvNomeSalao = itemView.findViewById(R.id.tvNomeSalao);
            tvTitulo = itemView.findViewById(R.id.tvEnderecoSalao);
            tvDescricao = itemView.findViewById(R.id.tvDistancia);

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
    public PromocaoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_promocao,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PromocaoAdapter.ViewHolder viewHolder, int i) {


        if(lista.size() != 0){
            String Urlfoto = lista2.get(i).getmFoto();
            Picasso.get().load(Urlfoto).into(viewHolder.ivFotoPromo);
            viewHolder.itemView.setTag(lista.get(i));

            viewHolder.tvNomeSalao.setText(lista.get(i).getmNome());
            viewHolder.tvTitulo.setText(lista2.get(i).getmTitulo());
            viewHolder.tvDescricao.setText(lista2.get(i).getMDescricao());

            //viewHolder.ivFotoPromo.setImageResource(R.drawable.salao_teste);
        }

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}