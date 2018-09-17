package com.desafiolatam.desafioface.views.splash;

import android.util.Log;

import com.desafiolatam.desafioface.models.CurrentUser;

import java.util.List;

public class LoginValidation {

    private LoginCallback callBack;

    public LoginValidation(LoginCallback callBack) {
        this.callBack = callBack;
    }

    public void init()
    {
        //lista de usuarios cargados de base de datos
        List<CurrentUser> currentUserList= CurrentUser.listAll(CurrentUser.class);
        Log.d("CurrentUserList", "init: "+currentUserList);

        if(currentUserList!=null && currentUserList.size() > 0)
        {
            callBack.signed();
        }
        else {callBack.signup();}
    }
}
