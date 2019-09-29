package com.redewsouza.amamaria.activity;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.redewsouza.amamaria.R;
import com.redewsouza.amamaria.adapter.HistoricoAdapter;
import com.redewsouza.amamaria.adapter.RequisicoesAdapter;
import com.redewsouza.amamaria.config.ConfiguracaoFirebase;
import com.redewsouza.amamaria.helper.UsuarioFirebase;
import com.redewsouza.amamaria.model.Requisicao;
import com.redewsouza.amamaria.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ListHistoricoActivity extends AppCompatActivity {

    private RecyclerView recyclerHistorico;
    private List<Requisicao> listaHistorico = new ArrayList<>();
    private Usuario passageiro;
    private HistoricoAdapter adapter;

    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_historico);

        inicializarComponentes();

    }

    private void inicializarComponentes(){

        recyclerHistorico = findViewById(R.id.recyclerHistorico);

        //Configurações iniciais
        passageiro = UsuarioFirebase.getDadosUsuarioLogado();
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();

        //Configurar RecyclerView
        adapter = new HistoricoAdapter(listaHistorico, getApplicationContext(), passageiro );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerHistorico.setLayoutManager( layoutManager );
        recyclerHistorico.setHasFixedSize(true);
        recyclerHistorico.setAdapter( adapter );

        recuperarRequisicoes();

    }

    private void recuperarRequisicoes(){

        Usuario usuarioPassageiro = UsuarioFirebase.getDadosUsuarioLogado();
        DatabaseReference requisicoes = firebaseRef.child("requisicoes");

        //sem filtro
        //Query requisicaoPesquisa = requisicoes.orderByChild("email");

        //filtrar por status

        Query requisicaoPesquisa = requisicoes.orderByChild("passageiro/email")
                .equalTo(usuarioPassageiro.getEmail());


        requisicaoPesquisa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listaHistorico.clear();
                for ( DataSnapshot ds: dataSnapshot.getChildren() ){
                    Requisicao requisicao = ds.getValue( Requisicao.class );
                    listaHistorico.add( requisicao );
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
