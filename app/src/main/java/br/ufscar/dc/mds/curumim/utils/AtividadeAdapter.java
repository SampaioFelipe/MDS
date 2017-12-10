package br.ufscar.dc.mds.curumim.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.ufscar.dc.mds.curumim.R;
import br.ufscar.dc.mds.curumim.entities.Atividade;

public class AtividadeAdapter extends ArrayAdapter<Atividade> {

    private Context context;

    private static class ViewHolder {
        TextView txtNome;
        TextView txtLocal;
        TextView txtHorario;
    }

    public AtividadeAdapter(ArrayList<Atividade> dados, Context context) {
        super(context, R.layout.atividade_adapter, dados);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Atividade atividade = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.atividade_adapter, parent, false);
            viewHolder.txtNome = convertView.findViewById(R.id.nomeAtividade);
            viewHolder.txtLocal = convertView.findViewById(R.id.localAtividade);
            viewHolder.txtHorario = convertView.findViewById(R.id.horarioAtividade);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtNome.setText(atividade.nome);
        viewHolder.txtLocal.setText(atividade.local);
        viewHolder.txtHorario.setText(atividade.getHorarioString());

        return convertView;
    }
}
