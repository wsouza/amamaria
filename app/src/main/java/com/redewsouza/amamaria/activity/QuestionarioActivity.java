package com.redewsouza.amamaria.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.redewsouza.amamaria.R;
import com.redewsouza.amamaria.config.ConfiguracaoFirebase;
import com.redewsouza.amamaria.helper.Base64Custom;
import com.redewsouza.amamaria.model.Questionario;

public class QuestionarioActivity extends AppCompatActivity {

    private RadioGroup radioGroup1;
    private RadioGroup radioGroup2;
    private RadioGroup radioGroup3;
    private RadioGroup radioGroup4;
    private RadioGroup radioGroup5;
    private RadioGroup radioGroup6;

    private Questionario questionario;

    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionario);
        Toolbar toolbar = findViewById(R.id.toolbarPerfil);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_logo);

        radioGroup1 = findViewById(R.id.questao1);
        radioGroup2 = findViewById(R.id.questao2);
        radioGroup3 = findViewById(R.id.questaor3);
        radioGroup4 = findViewById(R.id.questaor4);
        radioGroup5 = findViewById(R.id.questaor5);
        radioGroup6 = findViewById(R.id.questaor6);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //validar respostas, se foram respondidas.
                if ( validarRespostas() ) {

                    //salvar questionario
                    questionario = new Questionario();
                    int radioButtonId1 = radioGroup1.getCheckedRadioButtonId();
                    int radioButtonId2 = radioGroup2.getCheckedRadioButtonId();
                    int radioButtonId3 = radioGroup3.getCheckedRadioButtonId();
                    int radioButtonId4 = radioGroup4.getCheckedRadioButtonId();
                    int radioButtonId5 = radioGroup5.getCheckedRadioButtonId();
                    int radioButtonId6 = radioGroup6.getCheckedRadioButtonId();

                    switch (radioButtonId1) {
                        case R.id.sim1:
                            questionario.setResposta1("sim");
                            break;
                        case R.id.nao1:
                            questionario.setResposta1("nao");
                            break;
                    }
                    switch (radioButtonId2) {
                        case R.id.sim2:
                            questionario.setResposta2("sim");
                            break;
                        case R.id.nao2:
                            questionario.setResposta2("nao");
                            break;
                    }
                    switch (radioButtonId3) {
                        case R.id.sim3:
                            questionario.setResposta3("sim");
                            break;
                        case R.id.nao3:
                            questionario.setResposta3("nao");
                            break;
                    }
                    switch (radioButtonId4) {
                        case R.id.sim4:
                            questionario.setResposta4("sim");
                            break;
                        case R.id.nao4:
                            questionario.setResposta4("nao");
                            break;
                    }
                    switch (radioButtonId5) {
                        case R.id.sim5:
                            questionario.setResposta5("sim");
                            break;
                        case R.id.nao5:
                            questionario.setResposta5("nao");
                            break;
                    }
                    switch (radioButtonId6) {
                        case R.id.sim6:
                            questionario.setResposta6("sim");
                            break;
                        case R.id.nao6:
                            questionario.setResposta6("nao");
                            break;
                    }

                    questionario.salvar();

                    Toast.makeText(QuestionarioActivity.this,
                            "Salvo com sucesso!",
                            Toast.LENGTH_SHORT).show();

                }



            }
        });
    }

    public Boolean validarRespostas(){

        int radioButtonId1 = radioGroup1.getCheckedRadioButtonId();
        int radioButtonId2 = radioGroup2.getCheckedRadioButtonId();
        int radioButtonId3 = radioGroup3.getCheckedRadioButtonId();
        int radioButtonId4 = radioGroup4.getCheckedRadioButtonId();
        int radioButtonId5 = radioGroup5.getCheckedRadioButtonId();
        int radioButtonId6 = radioGroup6.getCheckedRadioButtonId();

        if ( radioButtonId1 > 0 ){
            if ( radioButtonId2 > 0 ){
                if (radioButtonId3 > 0){
                    if (radioButtonId4 > 0){
                        if (radioButtonId5 > 0){
                            if (radioButtonId6 > 0){
                                return true;
                            }else {
                                Toast.makeText(QuestionarioActivity.this,
                                        "Responda a questão 6",
                                        Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        }else {
                            Toast.makeText(QuestionarioActivity.this,
                                    "Responda a questão 5",
                                    Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    }else {
                        Toast.makeText(QuestionarioActivity.this,
                                "Responda a questão 4",
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }else {
                    Toast.makeText(QuestionarioActivity.this,
                            "Responda a questão 3",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }
            }else {
                Toast.makeText(QuestionarioActivity.this,
                        "Responda a questão 2",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }else {
            Toast.makeText(QuestionarioActivity.this,
                    "Responda a questão 1",
                    Toast.LENGTH_SHORT).show();
            return false;
        }


    }

    public void recuperarQuestionario(){

        String emailUsuario = autenticacao.getCurrentUser().getEmail(); //pegar e-mail quem logou
        String idUsuario = Base64Custom.codificarBase64( emailUsuario );


        DatabaseReference usuarioRef = firebaseRef.child("questionario").child( idUsuario );

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Questionario questionario = dataSnapshot.getValue(Questionario.class);


                if(questionario != null) {

                    String resposta1 = questionario.getResposta1();
                    String resposta2 = questionario.getResposta2();
                    String resposta3 = questionario.getResposta3();
                    String resposta4 = questionario.getResposta4();
                    String resposta5 = questionario.getResposta5();
                    String resposta6 = questionario.getResposta6();

                    if (resposta1.equalsIgnoreCase("sim")) {
                        radioGroup1.check(R.id.sim1);
                    } else {
                        radioGroup1.check(R.id.nao1);
                    }

                    if (resposta2.equalsIgnoreCase("sim")) {
                        radioGroup2.check(R.id.sim2);
                    } else {
                        radioGroup2.check(R.id.nao2);
                    }

                    if (resposta3.equalsIgnoreCase("sim")) {
                        radioGroup3.check(R.id.sim3);
                    } else {
                        radioGroup3.check(R.id.nao3);
                    }

                    if (resposta4.equalsIgnoreCase("sim")) {
                        radioGroup4.check(R.id.sim4);
                    } else {
                        radioGroup4.check(R.id.nao4);
                    }

                    if (resposta5.equalsIgnoreCase("sim")) {
                        radioGroup5.check(R.id.sim5);
                    } else {
                        radioGroup5.check(R.id.nao5);
                    }

                    if (resposta6.equalsIgnoreCase("sim")) {
                        radioGroup6.check(R.id.sim6);
                    } else {
                        radioGroup6.check(R.id.nao6);
                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarQuestionario();
    }


}
