package br.com.belapp.belapp.presenter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Avaliacao;

public class AvaliacaoAdapter extends RecyclerView.Adapter<AvaliacaoAdapter.MyViewHolder>{

    private Context mContexto;
    private ArrayList<Avaliacao> mAvaliacoes;

    public AvaliacaoAdapter(Context contexto, ArrayList<Avaliacao> avaliacoes){
        this.mContexto = contexto;
        this.mAvaliacoes = avaliacoes;
    }

    @NonNull
    @Override
    public AvaliacaoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {      //sufixa acrescentado "Avaliacao    "
        View v;
        LayoutInflater mInflanter = LayoutInflater.from(mContexto);
        v = mInflanter.inflate(R.layout.item_avaliacao, viewGroup,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AvaliacaoAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.tvNome.setText(mAvaliacoes.get(i).getmNome());
        myViewHolder.tvData.setText(mAvaliacoes.get(i).getmData());
        myViewHolder.tvComentario.setText(mAvaliacoes.get(i).getmComentario());
        myViewHolder.ivNota.setImageResource(carregarAvaliacao(mAvaliacoes.get(i).getmNota()));
    }

    @Override
    public int getItemCount() { return mAvaliacoes.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNome;
        TextView tvData;
        TextView tvComentario;
        ImageView ivNota;
        ImageView ivFoto;

        public MyViewHolder(View itemView){
            super(itemView);

            tvNome = itemView.findViewById(R.id.tvNomeCliente);
            tvData = itemView.findViewById(R.id.tvDataAvaliacao);
            tvComentario = itemView.findViewById(R.id.tvComentario);
            ivNota = itemView.findViewById(R.id.ivAvaliacao);
            ivFoto = itemView.findViewById(R.id.ivFotoCliente);
        }
    }

    private int carregarAvaliacao(double i) {
        int avaliacao = 5;
        double nota = i;

        if(nota > 4.5){
            avaliacao = R.drawable.estrela_5;
        } else if(nota > 4){
            avaliacao = R.drawable.estrela_4_5;
        } else if(nota > 3.5){
            avaliacao = R.drawable.estrela_4;
        } else if(nota > 3){
            avaliacao = R.drawable.estrela_3_5;
        } else if(nota > 2.5){
            avaliacao = R.drawable.estrela_3;
        } else if(nota > 2){
            avaliacao = R.drawable.estrela_2_5;
        } else if(nota > 1.5){
            avaliacao = R.drawable.estrela_2;
        } else if(nota > 1){
            avaliacao = R.drawable.estrela_1_5;
        } else if(nota > 0.5){
            avaliacao = R.drawable.estrela_1;
        } else if(nota > 0){
            avaliacao = R.drawable.estrela_0_5;
        } else {
            avaliacao = R.drawable.estrela_0;
        }
        return avaliacao;
    }
}
