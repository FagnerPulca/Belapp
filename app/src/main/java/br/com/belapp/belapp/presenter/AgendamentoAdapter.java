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

        TextView tvDataAgendamento;
        TextView tvHoraAgendamento;
        TextView tvEstabelecimentoAgendamento;
        TextView tvProfissionalAgendamento;
        TextView tvServicoAgendamento;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDataAgendamento = itemView.findViewById(R.id.tvDataAgendada);
            tvHoraAgendamento = itemView.findViewById(R.id.tvHoraAgendamento);
            tvEstabelecimentoAgendamento = itemView.findViewById(R.id.tvEstabelecimentoAgendado);
            tvProfissionalAgendamento = itemView.findViewById(R.id.tvProfissionalAgendado);
            tvServicoAgendamento = itemView.findViewById(R.id.tvServicoAgendado);

            itemView.setOnClickListener(view -> {
                Agendamento agendamento = (Agendamento) view.getTag();
                int index = agendamentos.indexOf(agendamento);
                activity.onItemClicked(index);
            });
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
        viewHolder.tvDataAgendamento.setText(String.format(Locale.getDefault(), "Data: %s",
                agendamentos.get(i).getmData()));
        viewHolder.tvHoraAgendamento.setText(String.format(Locale.getDefault(), "Horario: %s",
                agendamentos.get(i).getmHora()));
        viewHolder.tvEstabelecimentoAgendamento.setText(agendamentos.get(i).getmEstabelecimento().getmNome());
        viewHolder.tvServicoAgendamento.setText(agendamentos.get(i).getmServico().getmNome());
        viewHolder.tvProfissionalAgendamento.setText(
                String.format(Locale.getDefault(), "Profissional: %s", agendamentos.get(i).getmProfissional().getNome()));
    }

    @Override
    public int getItemCount() {
        return agendamentos.size();
    }
}
