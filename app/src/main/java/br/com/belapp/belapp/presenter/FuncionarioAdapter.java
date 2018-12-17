package br.com.belapp.belapp.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Profissional;

public class FuncionarioAdapter extends RecyclerView.Adapter<FuncionarioAdapter.ViewHolder> {

    private ArrayList<Profissional> profissionais;
    //private ArrayList<Servico> servicos;
    ItemClicked activity;
    private AgendarButtonClicked agendarButtonClicked;

    public interface ItemClicked{
        void onItemClicked(int index);
    }
    public interface AgendarButtonClicked{
        void onAgendarButtonClicked(int index);
    }

    public FuncionarioAdapter (Context context, ArrayList<Profissional> list_profissionais){
        profissionais = list_profissionais;
        //servicos = list_servicos;
        activity = (ItemClicked) context;
        agendarButtonClicked = (AgendarButtonClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivProfissional;
        TextView tvNome;
        TextView tvPreco;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProfissional = itemView.findViewById(R.id.ivProfissional);
            tvNome = itemView.findViewById(R.id.tvNome);
            tvPreco = itemView.findViewById(R.id.tvPreco);
            Button btn = itemView.findViewById(R.id.btnAgendar);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    agendarButtonClicked.onAgendarButtonClicked(profissionais.indexOf((Profissional) itemView.getTag()));
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(profissionais.indexOf((Profissional) v.getTag()));
                }
            });
        }
    }

    @NonNull
    @Override
    public FuncionarioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_profisisonal,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FuncionarioAdapter.ViewHolder viewHolder, int i) {
        DecimalFormat df2 = new DecimalFormat(".##");
        viewHolder.itemView.setTag(profissionais.get(i));

        /*for (int j = 0; j < profissionais.size(); j++){
            if (servicos.get(i).getmProfissionais().equals(profissionais.get(j).getmId())){
                viewHolder.tvPreco.setText("R$ "+df2.format(servicos.get(i).getmPreco()));
                viewHolder.tvNome.setText("Nome: "+profissionais.get(i).getNome());
            }
        }*/
        viewHolder.tvNome.setText("Nome: "+profissionais.get(i).getNome());
        viewHolder.tvPreco.setText(profissionais.get(i).getDescricao());
        viewHolder.ivProfissional.setImageResource(R.drawable.profissional_teste);
    }

    @Override
    public int getItemCount() {
        return profissionais.size();
    }
}
