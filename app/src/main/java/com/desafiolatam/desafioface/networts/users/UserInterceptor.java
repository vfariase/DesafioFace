package com.desafiolatam.desafioface.networts.users;

import com.desafiolatam.desafioface.models.CurrentUser;
import com.desafiolatam.desafioface.networts.sessions.LoginInterceptors;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.desafiolatam.desafioface.networts.sessions.LoginInterceptors.BASE_URL;

public class UserInterceptor {

    //private static final String BASE_URL = ;

    public Users get(){



        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();

                Request request = originalRequest.newBuilder()
                        /*Common headers*/
                        .header("Authorization", CurrentUser.listAll(CurrentUser.class).get(0).getAuth_token())
                        .header("Accept", "application/json")
                        /*Custom header*/
                        .header("source", "mobile")
                        .build();

                Response response = chain.proceed(request);

                /*If the request fail then you get 3 retrys*/
                int retryCount = 0;
                while (!response.isSuccessful() && retryCount < 3) {
                    retryCount++;
                    response = chain.proceed(request);
                }

                return response;
            }
        });

        OkHttpClient client = httpClient.build();

        Retrofit interceptor = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        Users request = interceptor.create(Users.class);

        return request;


    }
}
