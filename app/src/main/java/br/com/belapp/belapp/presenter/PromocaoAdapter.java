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

    private ArrayList<Estabelecimento> mlista;
    private ArrayList<Promocoes> mlista2;
    ItemClicked activity;


    public interface ItemClicked{
        void onItemClicked(int index);
    }

    public PromocaoAdapter(Context context, ArrayList<Estabelecimento> list,ArrayList<Promocoes> list2){
        mlista = list;
        mlista2 =list2;
        activity = (ItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivFotoP ;
        TextView tvNomeSalaoP, tvTituloP, tvDescP;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivFotoP = itemView.findViewById(R.id.ivFotoP);
            tvNomeSalaoP = itemView.findViewById(R.id.tvNomeSalaoP);
            tvTituloP = itemView.findViewById(R.id.tvTituloP);
            tvDescP= itemView.findViewById(R.id.tvDescP);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(mlista.indexOf((Estabelecimento) v.getTag()));
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


        if(mlista.size() != 0){
            String Urlfoto = mlista2.get(i).getFoto();
            Picasso.get().load(Urlfoto).into(viewHolder.ivFotoP );
            viewHolder.itemView.setTag(mlista.get(i));

            viewHolder.tvNomeSalaoP.setText(mlista.get(i).getmNome());
            viewHolder.tvTituloP.setText(mlista2.get(i).getTitulo());
            viewHolder.tvDescP.setText(mlista2.get(i).getDescricao());

            //viewHolder.ivFotoPromo.setImageResource(R.drawable.salao_teste);
        }

    }

    @Override
    public int getItemCount() {
        return mlista.size();
    }
}