package com.redewsouza.amamaria.api;

import com.redewsouza.amamaria.model.NotificacaoDados;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NotificacaoService {

    @Headers({
            "Authorization:key=AAAAk_mVXIQ:APA91bEVHnDxDcOfJMwle5ahILZbYM0AajOBvtPjLejoDjCMi1aCOAeK6eFQ5GN9x3hiTPxwT8B5tRzjA1ybwVSWa3ijLRNisLwtGWnvJT6GZma-mcYxr_v8DMSnplqzeh_9AefTOt_h",
            "Content-Type:application/json"
    })
    @POST("send")
    Call<NotificacaoDados> salvarNotificacao(@Body NotificacaoDados notificacaoDados);

}
