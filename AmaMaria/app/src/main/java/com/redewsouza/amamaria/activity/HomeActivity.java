package com.redewsouza.amamaria.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.redewsouza.amamaria.R;
import com.redewsouza.amamaria.config.ConfiguracaoFirebase;

public class HomeActivity extends AppCompatActivity {

    private Button protetoresButton;
    private Button dadosbutton;
    private Button panicoButton;
    private Button historicoButton;
    private Button informarButton;
    private Button redeButton;

    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Ama Maria");
        toolbar.setNavigationIcon(R.drawable.icon_logo);
        setSupportActionBar(toolbar);


        redeButton = findViewById(R.id.redeButton);
        dadosbutton = findViewById(R.id.dadosbutton);
        panicoButton = findViewById(R.id.panicoButton);
        historicoButton = findViewById(R.id.historicoButton);
        informarButton = findViewById(R.id.informarButton);
        protetoresButton = findViewById(R.id.protetoresButton);

        redeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Chamar classe
                Intent intent = new Intent(getApplicationContext(), ListRedeActivity.class);
                startActivity( intent );
            }
        });

        protetoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Chamar classe
                Intent intent = new Intent(getApplicationContext(), ListProtetoresActivity.class);
                startActivity( intent );
            }
        });

        dadosbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Chamar classe
                Intent intent = new Intent(getApplicationContext(), QuestionarioActivity.class);
                startActivity( intent );
            }
        });

        historicoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Chamar classe
                Intent intent = new Intent(getApplicationContext(), ListHistoricoActivity.class);
                startActivity( intent );
            }
        });

        historicoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Chamar classe
                Intent intent = new Intent(getApplicationContext(), ListHistoricoActivity.class);
                startActivity( intent );
            }
        });

        /*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSair :
                autenticacao.signOut();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void meuDados(View view){

        Toast.makeText(HomeActivity.this,
                "Glória a Deus!",
                Toast.LENGTH_SHORT).show();

    }

}
