package com.desafiolatam.desafioface.views.login;

import android.util.Log;

import com.desafiolatam.desafioface.models.CurrentUser;
import com.desafiolatam.desafioface.networts.sessions.LoginInterceptors;
import com.desafiolatam.desafioface.networts.sessions.Sessions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn {

    private SessionCallBack callBack;

    public SignIn(SessionCallBack sessionCallBack) {
        this.callBack = sessionCallBack;
    }

    public void toServer(String email, String password)
    {
        Log.d("SIGNIN", "toServer: ");
        Log.d("SIGNIN", "email: "+email);
        Log.d("SIGNIN", "password: "+password);

        if(email.trim().length() <= 0 || password.trim().length()<=0)
        {
            callBack.requiredField();
        }
        else{
             if(!email.contains("@"))
                {
                    callBack.maiFormat();
                }
              else {
                 Log.d("SIGNIN", "else: ");
                 Sessions session=new LoginInterceptors().getBasic();
                 Call<CurrentUser> call = session.login(email,password);
                 Log.d("SIGNIN", "call: "+call);
                 call.enqueue(new Callback<CurrentUser>() {
                     @Override
                     public void onResponse(Call<CurrentUser> call, Response<CurrentUser> response) {
                            try
                            {
                                Log.d("CODE", "Response.code: "+response.code());
                                Log.d("CODE", ": response.isSuccessful"+response.code());
                                if (response.code() == 200 && response.isSuccessful()) {
                                    CurrentUser user = response.body();
                                    user.create();
                                    callBack.success();
                                }
                                else{
                                    callBack.failure();
                                }

                            }catch (Exception e)
                            {
                                Log.d("ERROR", "error: "+e.getMessage());
                            }

                     }

                     @Override
                     public void onFailure(Call<CurrentUser> call, Throwable t) {
                          callBack.failure();
                     }
                 });
                   }

        }
    }

}
