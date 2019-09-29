package com.redewsouza.amamaria.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.redewsouza.amamaria.config.ConfiguracaoFirebase;

import java.io.Serializable;

/**
 * Created by jamiltondamasceno
 */

public class Usuario implements Serializable {

    private String idUsuario;
    private String nome;
    private String email;
    private String senha;

    private String tipo;
    private String latitude;
    private String longitude;

    public Usuario() {
    }

    public void salvar(){
        DatabaseReference firebase = ConfiguracaoFirebase.getFirebaseDatabase();
        firebase.child("usuarios")
                .child( getIdUsuario())
                .setValue( this );
    }

    /*
    * Exclude é para não salvar o get no servidor
    * */

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

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

