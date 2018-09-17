package com.desafiolatam.desafioface.networts.sessions;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginInterceptors {

    public static final String BASE_URL="https://empieza.desafiolatam.com/";


    public Sessions getBasic(){

        Retrofit interceptor=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();

        Sessions session = interceptor.create(Sessions.class);


        return session;

    }
}
