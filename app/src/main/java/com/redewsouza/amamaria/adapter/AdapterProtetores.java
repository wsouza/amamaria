package com.redewsouza.amamaria.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.redewsouza.amamaria.R;
import com.redewsouza.amamaria.model.Protetor;

import java.util.List;

public class AdapterProtetores extends RecyclerView.Adapter<AdapterProtetores.MyViewHolder>{

    List<Protetor> protetores;
    Context context;

    public AdapterProtetores(List<Protetor> protetores, Context context) {
        this.protetores = protetores;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_protetor, parent, false);
        return new MyViewHolder(itemLista);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Protetor protetorc = protetores.get(position);

        holder.nome.setText(protetorc.getNome());
        holder.telefone.setText(protetorc.getTelefone());

        /* alterar cor if (movimentacao.getTipo().equals("d")) {
            holder.valor.setTextColor(context.getResources().getColor(R.color.colorAccent));
            holder.valor.setText("-" + movimentacao.getValor());
        }*/
    }


    @Override
    public int getItemCount() {
        return protetores.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nome, telefone;

        public MyViewHolder(View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.textNome);
            telefone = itemView.findViewById(R.id.textEndereco);
        }

    }

}
