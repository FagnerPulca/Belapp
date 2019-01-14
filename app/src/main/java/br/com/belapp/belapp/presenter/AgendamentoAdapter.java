package br.com.belapp.belapp.presenter;

import android.content.Context;
import android.graphics.Color;
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
import br.com.belapp.belapp.utils.DateUtils;

public class AgendamentoAdapter extends RecyclerView.Adapter<AgendamentoAdapter.ViewHolder>  {


    private ItemClicked activity;
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
        private TextView mTvStatusAgendamento;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTvDataAgendamento = itemView.findViewById(R.id.tvDataAgendada);
            mTvHoraAgendamento = itemView.findViewById(R.id.tvHoraAgendamento);
            mTvEstabelecimentoAgendamento = itemView.findViewById(R.id.tvEstabelecimentoAgendado);
            mTvProfissionalAgendamento = itemView.findViewById(R.id.tvProfissionalAgendado);
            mTvServicoAgendamento = itemView.findViewById(R.id.tvServicoAgendado);
            mTvStatusAgendamento = itemView.findViewById(R.id.tvStatusAgendamento);

            itemView.setOnClickListener(v ->
                    activity.onItemClicked(agendamentos.indexOf((Agendamento) v.getTag())));
        }

        private TextView getmTvServicoAgendamento() {
            return mTvServicoAgendamento;
        }

        private TextView getmTvProfissionalAgendamento() {
            return mTvProfissionalAgendamento;
        }

        private TextView getmTvHoraAgendamento() {
            return mTvHoraAgendamento;
        }

        private TextView getmTvEstabelecimentoAgendamento() {
            return mTvEstabelecimentoAgendamento;

        }

        private TextView getmTvDataAgendamento() {
            return mTvDataAgendamento;
        }

        private TextView getmTvStatusAgendamento() {
            return mTvStatusAgendamento;
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
        Agendamento agendamento = agendamentos.get(i);
        if (agendamento.getmId() != null && !agendamento.getmId().equals("")) {
            viewHolder.itemView.setTag(agendamentos.get(i));
            viewHolder.getmTvDataAgendamento().setText(String.format(Locale.getDefault(), "Data: %s",
                    agendamento.getmData()));
            viewHolder.getmTvHoraAgendamento().setText(String.format(Locale.getDefault(), "Horario: %s",
                    agendamento.getmHora()));
            viewHolder.getmTvEstabelecimentoAgendamento().setText(agendamento.getmEstabelecimento().getmNome());
            viewHolder.getmTvServicoAgendamento().setText(agendamento.getmServico().getmNome());
            viewHolder.getmTvProfissionalAgendamento().setText(
                    String.format(Locale.getDefault(), "Profissional: %s", agendamento.getmProfissional().getNome()));
            if (!DateUtils.isDataFutura(agendamento.getmData()) && !DateUtils.isDataPresente(agendamento.getmData())) {
                viewHolder.getmTvStatusAgendamento().setText(String.format("Status: %s", "Concluído"));
                viewHolder.getmTvStatusAgendamento().setTextColor(Color.RED);
            } else if (DateUtils.isDataFutura(agendamento.getmData()) || DateUtils.isDataPresente(agendamento.getmData())) {
                viewHolder.getmTvStatusAgendamento().setText(String.format("Status: %s", "Agendado"));
                viewHolder.getmTvStatusAgendamento().setTextColor(Color.BLUE);
            }
        }
        else{
            viewHolder.getmTvStatusAgendamento().setVisibility(View.GONE);
            viewHolder.getmTvProfissionalAgendamento().setVisibility(View.GONE);
            viewHolder.getmTvEstabelecimentoAgendamento().setVisibility(View.GONE);
            viewHolder.getmTvHoraAgendamento().setVisibility(View.GONE);
            viewHolder.getmTvDataAgendamento().setVisibility(View.GONE);
            viewHolder.getmTvServicoAgendamento().setText(agendamento.getmServico().getmNome());
        }

    }

    @Override
    public int getItemCount() {
        return agendamentos.size();
    }
}
