package com.redewsouza.amamaria.model;


import java.io.Serializable;

public class Rede implements Serializable {

    private String ddd;
    private String descricao;
    private String endereco;
    private String nome;
    private String telcelular;
    private String telfixo;
    private String telzap;
    private String key;

    public Rede() {

    }


    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelcelular() {
        return telcelular;
    }

    public void setTelcelular(String telcelular) {
        this.telcelular = telcelular;
    }

    public String getTelfixo() {
        return telfixo;
    }

    public void setTelfixo(String telfixo) {
        this.telfixo = telfixo;
    }

    public String getTelzap() {
        return telzap;
    }

    public void setTelzap(String telzap) {
        this.telzap = telzap;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
