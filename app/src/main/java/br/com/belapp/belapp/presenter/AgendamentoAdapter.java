package br.com.belapp.belapp.presenter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.collection.LLRBNode;

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

    protected class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvDataAgendamento;
        TextView tvHoraAgendamento;
        TextView tvEstabelecimentoAgendamento;
        TextView tvProfissionalAgendamento;
        TextView tvServicoAgendamento;
        TextView tvStatusAgendamento;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDataAgendamento = itemView.findViewById(R.id.tvDataAgendada);
            tvHoraAgendamento = itemView.findViewById(R.id.tvHoraAgendamento);
            tvEstabelecimentoAgendamento = itemView.findViewById(R.id.tvEstabelecimentoAgendado);
            tvProfissionalAgendamento = itemView.findViewById(R.id.tvProfissionalAgendado);
            tvServicoAgendamento = itemView.findViewById(R.id.tvServicoAgendado);
            tvStatusAgendamento = itemView.findViewById(R.id.tvStatusAgendamento);

            itemView.setOnClickListener(v -> activity.onItemClicked(agendamentos.indexOf(v.getTag())));
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

        viewHolder.itemView.setTag(agendamentos.get(i));
        viewHolder.tvDataAgendamento.setText(String.format(Locale.getDefault(), "Data: %s",
                    agendamento.getmData()));
        viewHolder.tvHoraAgendamento.setText(String.format(Locale.getDefault(), "Horario: %s",
                    agendamento.getmHora()));
        viewHolder.tvEstabelecimentoAgendamento.setText(agendamento.getmEstabelecimento().getmNome());
        viewHolder.tvServicoAgendamento.setText(agendamento.getmServico().getmNome());
        viewHolder.tvProfissionalAgendamento.setText(
                    String.format(Locale.getDefault(), "Profissional: %s", agendamento.getmProfissional().getNome()));
        if (!DateUtils.isDataFutura(agendamento.getmData()) && !DateUtils.isDataPresente(agendamento.getmData())) {
                viewHolder.tvStatusAgendamento.setText(String.format("Status: %s", "Concluído"));
                viewHolder.tvStatusAgendamento.setTextColor(Color.RED);
        } else {
            viewHolder.tvStatusAgendamento.setText(String.format("Status: %s", "Agendado"));
            viewHolder.tvStatusAgendamento.setTextColor(Color.BLUE);
        }


    }

    @Override
    public int getItemCount() {
        return agendamentos.size();
    }
}
