package com.example.hayashidashboard.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hayashidashboard.Dados.ListPagar;
import com.example.hayashidashboard.R;

import java.util.List;

public class PagarAdapter extends RecyclerView.Adapter<PagarAdapter.ViewHolder> {

    private List<ListPagar> values;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvPedido;
        public TextView tvEmissao;
        public TextView tvValor;
        public View mView;

        public ViewHolder(View v) {

            super(v);
            mView = v;
            tvPedido = v.findViewById(R.id.tvPedidoPagar);
            tvEmissao = v.findViewById(R.id.tvEmissaoPagar);
            tvValor = v.findViewById(R.id.tvValorPagar);

        }

    }

    public PagarAdapter(List<ListPagar> myDataSet, Context context) {

        values = myDataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_pagar, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ListPagar listPagar = values.get(position);
        holder.tvPedido.setText(listPagar.getPedido());
        holder.tvEmissao.setText(listPagar.getEmissao());
        holder.tvValor.setText(listPagar.getValor());

    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}
