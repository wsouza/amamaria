package com.redewsouza.amamaria.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.redewsouza.amamaria.R;
import com.redewsouza.amamaria.helper.Local;
import com.redewsouza.amamaria.model.Requisicao;
import com.redewsouza.amamaria.model.Usuario;

import java.util.List;

/**
 * Created by jamiltondamasceno
 */

public class RequisicoesAdapter extends RecyclerView.Adapter<RequisicoesAdapter.MyViewHolder> {

    private List<Requisicao> requisicoes;
    private Context context;
    private Usuario motorista;

    public RequisicoesAdapter(List<Requisicao> requisicoes, Context context, Usuario motorista) {
        this.requisicoes = requisicoes;
        this.context = context;
        this.motorista = motorista;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_requisicoes, parent, false);
        return new MyViewHolder( item ) ;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Requisicao requisicao = requisicoes.get( position );
        Usuario passageiro = requisicao.getPassageiro();

        holder.nome.setText( passageiro.getNome() );
        holder.data.setText( requisicao.getData() );
        holder.status.setText( requisicao.getStatus() );


        if(motorista != null){

            LatLng localPassageiro = new LatLng(
                    Double.parseDouble(passageiro.getLatitude()),
                    Double.parseDouble(passageiro.getLongitude())
            );

            if(motorista.getLatitude() != null) {
                if(motorista.getLongitude() != null) {
                    LatLng localMotorista = new LatLng(
                            Double.parseDouble(motorista.getLatitude()),
                            Double.parseDouble(motorista.getLongitude())
                    );

                    float distancia = Local.calcularDistancia(localPassageiro, localMotorista);
                    String distanciaFormatada = Local.formatarDistancia(distancia);
                    holder.distancia.setText(distanciaFormatada + "- aproximadamente");
                }
            }


        }



    }

    @Override
    public int getItemCount() {
        return requisicoes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nome, distancia, data, status;

        public MyViewHolder(View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.textRequisicaoNome);
            data = itemView.findViewById(R.id.textData);
            distancia = itemView.findViewById(R.id.textStatus);
            status = itemView.findViewById(R.id.textStatus2);

        }
    }

}
