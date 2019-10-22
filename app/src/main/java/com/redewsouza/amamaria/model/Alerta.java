package com.redewsouza.amamaria.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.redewsouza.amamaria.config.ConfiguracaoFirebase;
import com.redewsouza.amamaria.helper.Base64Custom;

import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

public class Alerta {

    private String idAlerta;
    private String dataatual;
    private String horaatual;

    private String latitude;
    private String longitude;

    public Alerta() {
    }

    public void salvarPanico(){

        /*
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
        FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        String idUsuarioAlerta = Base64Custom.codificarBase64( autenticacao.getCurrentUser().getEmail() );

        DatabaseReference novoalertas = firebaseRef
                .child("alertas");

        DatabaseReference novoalerta = novoalertas
                .child( idUsuarioAlerta )
                .push();

        Map objeto = new HashMap();
        objeto.put("latitude", this.getLatitude());
        objeto.put("longitude", this.getLongitude());

        novoalerta.updateChildren( objeto );
        */

    }

    public String getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(String idAlerta) {
        this.idAlerta = idAlerta;
    }

    public String getDataatual() {
        return dataatual;
    }

    public void setDataatual(String dataatual) {
        this.dataatual = dataatual;
    }

    public String getHoraatual() {
        return horaatual;
    }

    public void setHoraatual(String horaatual) {
        this.horaatual = horaatual;
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
}
