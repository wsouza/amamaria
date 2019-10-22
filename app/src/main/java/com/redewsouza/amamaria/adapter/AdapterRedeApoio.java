package com.redewsouza.amamaria.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.redewsouza.amamaria.R;
import com.redewsouza.amamaria.model.Rede;

import java.util.List;

public class AdapterRedeApoio extends RecyclerView.Adapter<AdapterRedeApoio.MyViewHolder> {

    List<Rede> rede;
    Context context;

    public AdapterRedeApoio(List<Rede> rede, Context context) {
        this.rede = rede;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_redeapoio, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(AdapterRedeApoio.MyViewHolder holder, int position) {

        Rede redec = rede.get(position);

        holder.nome.setText(redec.getNome());
        holder.endereco.setText(redec.getEndereco());

        /* alterar cor if (movimentacao.getTipo().equals("d")) {
            holder.valor.setTextColor(context.getResources().getColor(R.color.colorAccent));
            holder.valor.setText("-" + movimentacao.getValor());
        }*/

    }

    @Override
    public int getItemCount() {
        return rede.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nome, endereco;

        public MyViewHolder(View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.textNome);
            endereco = itemView.findViewById(R.id.textEndereco);
        }

    }
}
