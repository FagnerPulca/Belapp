package br.com.belapp.belapp.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Estabelecimento;
import br.com.belapp.belapp.model.Teste;

public class SalaoAdapter extends RecyclerView.Adapter<SalaoAdapter.RecyclerTesteViewHolder>
{

    public static ClickRecyclerView_Interface clickRecyclerViewInterface;
    Context mctx;
    private List<Estabelecimento> mList;

    public SalaoAdapter(Context ctx, List<Estabelecimento> list, ClickRecyclerView_Interface clickRecyclerViewInterface) {
            this.mctx = ctx;
            this.mList = list;
            this.clickRecyclerViewInterface = clickRecyclerViewInterface;
            }

    @Override
    public RecyclerTesteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_salao, viewGroup, false);
            return new RecyclerTesteViewHolder(itemView);
            }

    @Override
    public void onBindViewHolder(RecyclerTesteViewHolder viewHolder, int i)
    {
            Estabelecimento novoEstabe = mList.get(i);

            viewHolder.Descricao.setText(novoEstabe.getmDescricao());
            viewHolder.viewNome.setText(novoEstabe.getmNome());

    }

    @Override
    public int getItemCount()
    {
            return mList.size();
    }


    protected class RecyclerTesteViewHolder extends RecyclerView.ViewHolder
    {

        protected TextView viewNome;
        protected TextView Descricao;

        public RecyclerTesteViewHolder(final View itemView)
        {
            super(itemView);

            viewNome = (TextView) itemView.findViewById(R.id.tvNomeSalao);
            Descricao = (TextView) itemView.findViewById(R.id.tvEnderecoSalao);
            //Setup the click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    clickRecyclerViewInterface.onCustomClick(mList.get(getLayoutPosition()));

                }
            });
        }
    }
} /*RecyclerView.Adapter<SalaoAdapter.ViewHolder> {

    private ArrayList<Estabelecimento> lista;
    Context activity;

    public interface ItemClicked{
        void onItemClicked(int index);
    }

    public SalaoAdapter (Context context, List<Estabelecimento> list){
        lista = (ArrayList<Estabelecimento>) list;
        activity =  context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivFotoSalao;
        TextView tvNomeSalao, tvEnderecoSalao;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivFotoSalao = itemView.findViewById(R.id.ivFotoSalao);
            tvNomeSalao = itemView.findViewById(R.id.tvNomeSalao);
            tvEnderecoSalao = itemView.findViewById(R.id.tvEnderecoSalao);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    activity. (lista.indexOf((Teste) v.getTag()));
//                }
//            });
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
        viewHolder.itemView.setTag(lista.get(i));

        viewHolder.tvNomeSalao.setText(lista.get(i).getmNome());
        viewHolder.tvEnderecoSalao.setText(lista.get(i).getmDescricao());

        viewHolder.ivFotoSalao.setImageResource(R.drawable.salao_teste);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
*/