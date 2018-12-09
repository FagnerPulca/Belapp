package br.com.belapp.belapp.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Avaliacao;
import br.com.belapp.belapp.model.Servico;

public class AvaliacaoAdapter extends RecyclerView.Adapter<AvaliacaoAdapter.ViewHolder>{

    private ArrayList<Avaliacao> itens;
    private AvaliacaoAdapter.ItemClicked activity;

    public AvaliacaoAdapter(Context contexto, ArrayList<Avaliacao> list){
        this.itens = list;
        this.activity = (ItemClicked) contexto;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_avaliacao,viewGroup,false);
        return new AvaliacaoAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface ItemClicked{
        void onItemClicked(int index);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mNomeCliente, mData, mComentario;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            mNomeCliente = itemView.findViewById(R.id.tvNomeCliente);
            mData = itemView.findViewById(R.id.tvDataAvaliacao);
            mComentario = itemView.findViewById(R.id.tvComentario);

        }
    }

}
