package com.redewsouza.amamaria.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.redewsouza.amamaria.R;
import com.redewsouza.amamaria.adapter.AdapterRedeApoio;
import com.redewsouza.amamaria.config.ConfiguracaoFirebase;
import com.redewsouza.amamaria.helper.RecyclerItemClickListener;
import com.redewsouza.amamaria.model.Rede;

import java.util.ArrayList;
import java.util.List;

public class ListRedeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterRedeApoio adapterRedeApoio;
    private Rede rede;
    private List<Rede> redes = new ArrayList<>();
    private DatabaseReference redeRef;
    private ValueEventListener valueEventListenerRedes;

    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_rede);



        recyclerView = findViewById(R.id.recyclerRedeApoio);

        //Configurar adapter
        adapterRedeApoio = new AdapterRedeApoio(redes,this);

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter( adapterRedeApoio );

        //Configurar evento de clique
        //evento click
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Rede redeSelecionada = redes.get(position);
                                Intent i = new Intent(getApplicationContext(), PerfilRedeActivity.class);
                                i.putExtra("redeSelecionada", redeSelecionada );
                                startActivity( i );

                                /*
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Item pressionado: " + rede.getNome(),
                                        Toast.LENGTH_SHORT).show();
                                        */
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                Rede rede = redes.get(position);

                                Toast.makeText(
                                        getApplicationContext(),
                                        "Glória a Deus! - Click longoooooo!: " + rede.getNome(),
                                        Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );


    }


    public void recuperarRedes(){

        //String emailUsuario = autenticacao.getCurrentUser().getEmail();
        redeRef = firebaseRef.child("redeapoio");

        valueEventListenerRedes = redeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                redes.clear();
                for (DataSnapshot dados: dataSnapshot.getChildren() ){

                    Rede rede = dados.getValue( Rede.class );
                    rede.setKey( dados.getKey() );
                    redes.add( rede );

                }

                adapterRedeApoio.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarRedes();
    }

    @Override
    protected void onStop() {
        super.onStop();
        redeRef.removeEventListener( valueEventListenerRedes );
    }



}
