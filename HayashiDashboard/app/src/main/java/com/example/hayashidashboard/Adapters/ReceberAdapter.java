package com.example.hayashidashboard.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hayashidashboard.Dados.ListReceber;
import com.example.hayashidashboard.R;

import java.util.List;

public class ReceberAdapter extends RecyclerView.Adapter<ReceberAdapter.ViewHolder> {

    private List<ListReceber> values;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvPedido;
        public TextView tvEmissao;
        public TextView tvValor;
        public View mView;

        public ViewHolder(View v) {

            super(v);
            mView = v;
            tvPedido = v.findViewById(R.id.tvPedido);
            tvEmissao = v.findViewById(R.id.tvEmissao);
            tvValor = v.findViewById(R.id.tvValorReceber);

        }

    }

    public ReceberAdapter(List<ListReceber> myDataSet, Context context) {

        values = myDataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_receber, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ListReceber listReceber = values.get(position);
        holder.tvPedido.setText(listReceber.getPedido());
        holder.tvEmissao.setText(listReceber.getEmissao());
        holder.tvValor.setText(listReceber.getValor());

    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}
