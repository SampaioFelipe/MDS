package br.ufscar.dc.mds.curumim.activities.homeFragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.ufscar.dc.mds.curumim.R;
import br.ufscar.dc.mds.curumim.entities.Crianca;

import java.util.List;


public class CriancaRecyclerViewAdapter extends RecyclerView.Adapter<CriancaRecyclerViewAdapter.ViewHolder> {

    private final List<Crianca> criancas;

    public CriancaRecyclerViewAdapter(List<Crianca> criancas) {
        this.criancas = criancas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_crianca_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Crianca crianca = criancas.get(position);
        holder.nome.setText(crianca.getNome());
    }

    @Override
    public int getItemCount() {
        return criancas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nome;
        final ImageView foto;

        ViewHolder(View view) {
            super(view);
            nome = view.findViewById(R.id.nome_crianca_row);
            foto = view.findViewById(R.id.foto_crianca_row);
        }
    }
}
