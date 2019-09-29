package com.redewsouza.amamaria.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.redewsouza.amamaria.R;
import com.redewsouza.amamaria.model.Requisicao;
import com.redewsouza.amamaria.model.Usuario;

import java.util.List;

public class HistoricoAdapter extends RecyclerView.Adapter<HistoricoAdapter.MyViewHolder>{

    private List<Requisicao> requisicoes;
    private Context context;
    private Usuario passageiro;

    public HistoricoAdapter(List<Requisicao> requisicoes, Context context, Usuario passageiro) {
        this.requisicoes = requisicoes;
        this.context = context;
        this.passageiro = passageiro;
    }

    @Override
    public HistoricoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_historico, parent, false);
        return new HistoricoAdapter.MyViewHolder( item ) ;
    }

    @Override
    public void onBindViewHolder(HistoricoAdapter.MyViewHolder holder, int position) {

        Requisicao requisicao = requisicoes.get( position );
        Usuario passageiro = requisicao.getPassageiro();

        holder.nome.setText( passageiro.getNome() );
        holder.status.setText( requisicao.getStatus() );
        holder.data.setText( requisicao.getData() );

    }

    @Override
    public int getItemCount() {
        return requisicoes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nome, status, data;

        public MyViewHolder(View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.textRequisicaoNome);
            status = itemView.findViewById(R.id.textStatus);
            data = itemView.findViewById(R.id.textData);
            //distancia = itemView.findViewById(R.id.textStatus);

        }
    }



}
