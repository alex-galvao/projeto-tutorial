package com.example.testerecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder>{

    private List<ListProdutos> values;
    public Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvDesc;
        public TextView tvCod;
        public TextView tvMinimo;
        public TextView tvAtual;
        public TextView tvIdeal;
        public View mView;

        public ViewHolder (View v){

            super(v);
            mView = v;
            tvDesc = v.findViewById(R.id.tvNomeProduto);
            tvCod = v.findViewById(R.id.tvCodProduto);
            tvMinimo = v.findViewById(R.id.tvEstoqueMinimo);
            tvAtual = v.findViewById(R.id.tvEstoqueAtual);
            tvIdeal = v.findViewById(R.id.tvEstoqueIdeal);

        }

    }

    public  Myadapter(List<ListProdutos> myDataSet, Context context){

        values = myDataSet;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_produtos, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
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
