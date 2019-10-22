package com.redewsouza.amamaria.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.redewsouza.amamaria.R;
import com.redewsouza.amamaria.config.ConfiguracaoFirebase;
import com.redewsouza.amamaria.helper.Base64Custom;
import com.redewsouza.amamaria.model.Protetor;
import com.redewsouza.amamaria.model.Usuario;

import org.w3c.dom.Text;

public class ProtetoresActivity extends AppCompatActivity {

    private TextInputEditText campoNome, campoTelefone;
    private RadioGroup radioGroup;
    private Protetor protetor;
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protetores);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        campoNome = findViewById(R.id.editNomeProtetor);
        campoTelefone = findViewById(R.id.editTelefoneProtetor);
        radioGroup = findViewById(R.id.radioParentesco);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( validarCamposProtetor() ) {
                    protetor = new Protetor();
                    protetor.setNome(campoNome.getText().toString());
                    protetor.setTelefone(campoTelefone.getText().toString());
                    //protetor.setParentesco("Outros");

                    int radioButtonId = radioGroup.getCheckedRadioButtonId();
                    switch (radioButtonId) {
                        case R.id.rbPais:
                            protetor.setParentesco("Pai ou Mãe");
                            break;
                        case R.id.rbIrmaos:
                            protetor.setParentesco("Irmãos");
                            break;
                        case R.id.rbOutros:
                            protetor.setParentesco("Outros");
                            break;
                    }

                    protetor.salvar();
                }

            }
        });

    }

    public Boolean validarCamposProtetor(){

        String textoNome = campoNome.getText().toString();
        String textoTelefone = campoTelefone.getText().toString();
        int radioButtonId = radioGroup.getCheckedRadioButtonId();

        if ( !textoNome.isEmpty() ){
            if ( !textoTelefone.isEmpty() ){
                if (radioButtonId > 0){
                    return true;
                }else {
                    Toast.makeText(ProtetoresActivity.this,
                            "Informe o parentesco!",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }
            }else {
                Toast.makeText(ProtetoresActivity.this,
                        "Preencha o telefone!",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }else {
            Toast.makeText(ProtetoresActivity.this,
                    "Preencha o campo nome!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }


    }


    public void recuperarDdProtetor(){

        //String idPrincipal = autenticacao.getCurrentUser().getEmail(); pegar e-mail quem logou
        /*
        * String idUsuario = Base64Custom.codificarBase64( emailUsuario );
        * */

        String idPrincipal = "-LmKGXQliNwMEmNRd6kg";

        DatabaseReference usuarioRef = firebaseRef.child("protetores").child( idPrincipal );

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Protetor protetor = dataSnapshot.getValue(Protetor.class);
                //despesaTotal = usuario.getDespesaTotal();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void atualizarProtetor(Double despesa){

        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64( emailUsuario );
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child( idUsuario );

        usuarioRef.child("despesaTotal").setValue(despesa);

    }

}
