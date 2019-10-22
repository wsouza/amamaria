package com.redewsouza.amamaria.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.redewsouza.amamaria.config.ConfiguracaoFirebase;

/**
 * Created by jamiltondamasceno
 */

public class Usuario {

    private String idUsuario;
    private String nome;
    private String email;
    private String senha;

    public Usuario() {
    }

    public void salvar(){
        DatabaseReference firebase = ConfiguracaoFirebase.getFirebaseDatabase();
        firebase.child("usuarios")
                .child( this.idUsuario )
                .setValue( this );
    }

    /*
    * Exclude é para não salvar o get no servidor
    * */


    @Exclude
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

