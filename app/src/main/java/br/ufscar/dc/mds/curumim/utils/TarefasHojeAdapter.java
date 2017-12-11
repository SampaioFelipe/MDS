package br.ufscar.dc.mds.curumim.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.ufscar.dc.mds.curumim.R;
import br.ufscar.dc.mds.curumim.entities.Atividade;

public class TarefasHojeAdapter extends RecyclerView.Adapter<TarefasHojeAdapter.ViewHolder> {

    private final List<Atividade> atividades;

    public TarefasHojeAdapter(List<Atividade> atividades) {
        this.atividades = atividades;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.atividade_list_row, parent, false);
        return new TarefasHojeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TarefasHojeAdapter.ViewHolder holder, int position) {
        Atividade atividade = atividades.get(position);

        holder.nome_atividade.setText(atividade.nome);
        holder.local.setText(atividade.local);

        SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm");
        holder.horario.setText(dateFormat.format(atividade.horario));
    }

    @Override
    public int getItemCount() {
        return atividades.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nome_atividade, local, horario;

        ViewHolder(View view) {
            super(view);
            nome_atividade = view.findViewById(R.id.tarefa_hoje_nome_atividade);
            local = view.findViewById(R.id.tarefa_hoje_local);
            horario = view.findViewById(R.id.tarefa_hoje_horario);
        }
    }
}
