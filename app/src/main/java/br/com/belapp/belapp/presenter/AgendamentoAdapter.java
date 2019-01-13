package br.com.belapp.belapp.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.model.Agendamento;

public class AgendamentoAdapter extends RecyclerView.Adapter<AgendamentoAdapter.ViewHolder>  {


    ItemClicked activity;
    private ArrayList<Agendamento> agendamentos;

    public interface ItemClicked{
        void onItemClicked(int index);
    }

    public AgendamentoAdapter(Context context, ArrayList<Agendamento> agendamentos){
        this.agendamentos = agendamentos;
        activity =  (ItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mTvDataAgendamento;
        private TextView mTvHoraAgendamento;
        private TextView mTvEstabelecimentoAgendamento;
        private TextView mTvProfissionalAgendamento;
        private TextView mTvServicoAgendamento;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTvDataAgendamento = itemView.findViewById(R.id.tvDataAgendada);
            mTvHoraAgendamento = itemView.findViewById(R.id.tvHoraAgendamento);
            mTvEstabelecimentoAgendamento = itemView.findViewById(R.id.tvEstabelecimentoAgendado);
            mTvProfissionalAgendamento = itemView.findViewById(R.id.tvProfissionalAgendado);
            mTvServicoAgendamento = itemView.findViewById(R.id.tvServicoAgendado);

            itemView.setOnClickListener(v -> activity.onItemClicked(agendamentos.indexOf((Agendamento) v.getTag())));
        }

        public TextView getmTvDataAgendamento() {
            return mTvDataAgendamento;
        }

        public TextView getmTvEstabelecimentoAgendamento() {
            return mTvEstabelecimentoAgendamento;
        }

        public TextView getmTvHoraAgendamento() {
            return mTvHoraAgendamento;
        }

        public TextView getmTvProfissionalAgendamento() {
            return mTvProfissionalAgendamento;
        }

        public TextView getmTvServicoAgendamento() {
            return mTvServicoAgendamento;
        }

        public void setmTvDataAgendamento(TextView mTvDataAgendamento) {
            this.mTvDataAgendamento = mTvDataAgendamento;
        }

        public void setmTvEstabelecimentoAgendamento(TextView mTvEstabelecimentoAgendamento) {
            this.mTvEstabelecimentoAgendamento = mTvEstabelecimentoAgendamento;
        }

        public void setmTvHoraAgendamento(TextView mTvHoraAgendamento) {
            this.mTvHoraAgendamento = mTvHoraAgendamento;
        }

        public void setmTvProfissionalAgendamento(TextView mTvProfissionalAgendamento) {
            this.mTvProfissionalAgendamento = mTvProfissionalAgendamento;
        }

        public void setmTvServicoAgendamento(TextView mTvServicoAgendamento) {
            this.mTvServicoAgendamento = mTvServicoAgendamento;
        }
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_agendamento,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(agendamentos.get(i));
        viewHolder.getmTvDataAgendamento().setText(String.format(Locale.getDefault(), "Data: %s",
                agendamentos.get(i).getmData()));
        viewHolder.getmTvHoraAgendamento().setText(String.format(Locale.getDefault(), "Horario: %s",
                agendamentos.get(i).getmHora()));
        viewHolder.getmTvEstabelecimentoAgendamento().setText(agendamentos.get(i).getmEstabelecimento().getmNome());
        viewHolder.getmTvServicoAgendamento().setText(agendamentos.get(i).getmServico().getmNome());
        viewHolder.getmTvProfissionalAgendamento().setText(
                String.format(Locale.getDefault(), "Profissional: %s", agendamentos.get(i).getmProfissional().getNome()));
    }

    @Override
    public int getItemCount() {
        return agendamentos.size();
    }
}