package com.desafiolatam.desafioface;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.desafiolatam.desafioface.views.login.LoginActivity;
import com.desafiolatam.desafioface.views.splash.LoginCallback;
import com.desafiolatam.desafioface.views.splash.LoginValidation;


public class SplashActivity extends AppCompatActivity  implements LoginCallback{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
        View view = findViewById(R.id.root);
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

       //Se incializa constructor de present para que valida ingreso usuario
        new LoginValidation(this).init();
    }



    @Override
    public void signed() {
        Toast.makeText(this, "SIGNED", Toast.LENGTH_SHORT).show();
             }

    @Override
    public void signup() {
        Toast.makeText(this, "SIGNUP", Toast.LENGTH_SHORT).show();
       new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SplashActivity.this, "Inside RUN", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        },1200);


    }
}
