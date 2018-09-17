package com.desafiolatam.desafioface.views.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.desafiolatam.desafioface.R;
import com.desafiolatam.desafioface.background.RecentUserService;
import com.desafiolatam.desafioface.views.MainActivity;


public class LoginActivity extends AppCompatActivity implements SessionCallBack {


    private TextInputLayout emailWrapper,passWrapper;
    private EditText emailEt, passEt;
    private Button button;
    private IntentFilter intentFilter;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Login", "onCreate: ");
        Toast.makeText(this, "LOgin oncreate", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_login);

        emailWrapper = findViewById(R.id.emailTil);
        passWrapper = findViewById(R.id.passwordTil);

        emailEt = findViewById(R.id.emailEt);
        passEt = findViewById(R.id.passwordEt);
        button = findViewById(R.id.signBtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEt.getText().toString();
                String password = passEt.getText().toString();
                emailWrapper.setVisibility(View.GONE);
                passWrapper.setVisibility(View.GONE);
                button.setVisibility(View.GONE);

                Log.d("EMAIL", "email: "+email);
                Log.d("PASS", "email: "+password);
                new SignIn(LoginActivity.this).toServer(email,password);
            }
        });

        intentFilter = new IntentFilter(RecentUserService.USERS_FINISHED);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(RecentUserService.USERS_FINISHED.equals(action))
                {

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        };
    }


    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,intentFilter);
    }


    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    public void restoredView()
    {
      emailEt.setError(null);
      passEt.setError(null);
      emailWrapper.setVisibility(View.VISIBLE);
      passWrapper.setVisibility(View.VISIBLE);
    }
    
    @Override
    public void requiredField() {
        restoredView();
        emailEt.setError("REQUERIDO");
        passEt.setError("REQUERIDO");

    }

    @Override
    public void maiFormat() {
        restoredView();
        emailEt.setError("FORMATO INCORRECTO");

    }

    //Se deberian descargar en una pagina los developers (backgrounds)
    //la cantidad de paginas que le indicamos en nuestro getUser y services
    @Override
    public void success() {
        RecentUserService.startActionRecentUser(this);


    }

    @Override
    public void failure() {
        restoredView();
        Toast.makeText(this, "Fallo", Toast.LENGTH_SHORT).show();

    }
}
