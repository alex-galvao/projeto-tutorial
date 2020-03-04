package com.example.hayashidashboard.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hayashidashboard.Dados.ListProdutos;
import com.example.hayashidashboard.R;

import java.util.List;

public class EstoqueAdapter extends RecyclerView.Adapter<EstoqueAdapter.ViewHolder> {

    private List<ListProdutos> values;
    public Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvDesc;
        public TextView tvCod;
        public TextView tvMinimo;
        public TextView tvAtual;
        public TextView tvIdeal;
        public View mView;

        public ViewHolder (View v){

            super(v);
            mView = v;
            tvDesc = v.findViewById(R.id.tvDescricaoEstoque);
            tvCod = v.findViewById(R.id.tvCodEstoque);
            tvMinimo = v.findViewById(R.id.tvEstoqueMinimo);
            tvAtual = v.findViewById(R.id.tvEstoqueAtual);
            tvIdeal = v.findViewById(R.id.tvEstoqueIdeal);

        }

    }

    public EstoqueAdapter(List<ListProdutos> myDataSet, Context context){

        values = myDataSet;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_estoque, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ListProdutos listProdutos = values.get(position);
        holder.tvDesc.setText(listProdutos.getNomeProduto());
        holder.tvCod.setText(listProdutos.getCodProduto());
        holder.tvMinimo.setText(listProdutos.getEstoqueMinimo());
        holder.tvAtual.setText(listProdutos.getEstoqueAtual());
        holder.tvIdeal.setText(listProdutos.getEstoqueIdeal());

    }

    @Override
    public int getItemCount() {
        return values.size();

    }

}
