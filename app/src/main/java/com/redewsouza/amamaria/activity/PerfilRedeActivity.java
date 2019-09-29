package com.redewsouza.amamaria.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.redewsouza.amamaria.R;
import com.redewsouza.amamaria.config.ConfiguracaoFirebase;
import com.redewsouza.amamaria.model.Rede;

public class PerfilRedeActivity extends AppCompatActivity {

    private Rede redeSelecionada;
    private DatabaseReference verRedeRef;

    private TextView textDescricao;
    private TextView textEntidade;
    private TextView textContato;

    private DatabaseReference firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_rede);

        //Configurações iniciais
        firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();

        textDescricao = findViewById(R.id.textDescricao);
        textEntidade = findViewById(R.id.textEntidade);
        textContato = findViewById(R.id.textContato);

        //Configura toolbar
        Toolbar toolbar = findViewById(R.id.toolbarPerfil);
        toolbar.setTitle("Pefil");
        setSupportActionBar( toolbar );

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_logo);

        //Recuperar usuario selecionado
        Bundle bundle = getIntent().getExtras();
        if( bundle != null ){
            redeSelecionada = (Rede) bundle.getSerializable("redeSelecionada");

            //Configurar referencia postagens usuario
            verRedeRef = ConfiguracaoFirebase.getFirebaseDatabase()
                    .child("redeapoio")
                    .child( redeSelecionada.getKey() );

            //Configura nome do usuário na toolbar
            getSupportActionBar().setTitle( redeSelecionada.getNome() );

            textDescricao.setText(redeSelecionada.getDescricao());
            textEntidade.setText(redeSelecionada.getNome());
            textContato.setText(redeSelecionada.getTelcelular());

            Toast.makeText(PerfilRedeActivity.this,
                    redeSelecionada.getNome(),
                    Toast.LENGTH_SHORT).show();


        }

    }
}
