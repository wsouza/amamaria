package com.redewsouza.amamaria.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.redewsouza.amamaria.config.ConfiguracaoFirebase;
import com.redewsouza.amamaria.helper.Base64Custom;

public class Questionario {

    private String resposta1;
    private String resposta2;
    private String resposta3;
    private String resposta4;
    private String resposta5;
    private String resposta6;
    private String key;

    public Questionario() {

    }

    public void salvar(){

        FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        String idProtetor = Base64Custom.codificarBase64( autenticacao.getCurrentUser().getEmail() );


        DatabaseReference firebase = ConfiguracaoFirebase.getFirebaseDatabase();
        firebase.child("questionario")
                .child( idProtetor )
                .setValue( this );
    }

    public String getResposta1() {
        return resposta1;
    }

    public void setResposta1(String resposta1) {
        this.resposta1 = resposta1;
    }

    public String getResposta2() {
        return resposta2;
    }

    public void setResposta2(String resposta2) {
        this.resposta2 = resposta2;
    }

    public String getResposta3() {
        return resposta3;
    }

    public void setResposta3(String resposta3) {
        this.resposta3 = resposta3;
    }

    public String getResposta4() {
        return resposta4;
    }

    public void setResposta4(String resposta4) {
        this.resposta4 = resposta4;
    }

    public String getResposta5() {
        return resposta5;
    }

    public void setResposta5(String resposta5) {
        this.resposta5 = resposta5;
    }

    public String getResposta6() {
        return resposta6;
    }

    public void setResposta6(String resposta6) {
        this.resposta6 = resposta6;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
