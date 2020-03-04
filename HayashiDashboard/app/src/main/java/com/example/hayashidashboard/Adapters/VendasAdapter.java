package com.example.hayashidashboard.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hayashidashboard.Dados.ListVendas;
import com.example.hayashidashboard.R;

import java.util.List;

public class VendasAdapter extends RecyclerView.Adapter<VendasAdapter.ViewHolder> {

    private List<ListVendas> values;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvNatureza;
        public TextView tvEmissao;
        public TextView tvValor;
        public View mView;

        public ViewHolder(View v){

            super(v);
            mView = v;
            tvNatureza = v.findViewById(R.id.tvNatureza);
            tvEmissao = v.findViewById(R.id.tvEmissaoVenda);
            tvValor = v.findViewById(R.id.tvValorVenda);
        }

    }

    public VendasAdapter(List<ListVendas> myDataSet, Context context){

        values = myDataSet;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_vendas, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ListVendas listVendas = values.get(position);
        holder.tvNatureza.setText(listVendas.getNatureza());
        holder.tvEmissao.setText(listVendas.getEmissao());
        holder.tvValor.setText(listVendas.getValor());

    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}
