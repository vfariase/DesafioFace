package com.desafiolatam.desafioface.background;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.desafiolatam.desafioface.networts.GetUsers;

import java.util.HashMap;
import java.util.Map;


public class RecentUserService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_RECENT_USERS = "com.desafiolatam.desafioface.background.action.ACTION_RECENT_USERS";
    public static final String USERS_FINISHED="com.desafiolatam.desafioface.background.USERS_FINISHED";

    public RecentUserService() {
        super("RecentUserService");
    }

    //Iniciar las acciones que contiene el metodo
    //Se ejecuta primero
    public static void startActionRecentUser(Context context) {
        Log.d("RUS", "startActionRecentUser: ");
        Intent intent = new Intent(context, RecentUserService.class);
        intent.setAction(ACTION_RECENT_USERS);
        context.startService(intent);
    }


    @Override //Se ejecuta 2°
    protected void onHandleIntent(Intent intent) {
        Log.d("RUS", "onHandleIntent: ");

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_RECENT_USERS.equals(action)) {

                fetchUsers();
            }
            }
        }


      //Se ejecuta tercero
    private void fetchUsers() {
        Log.d("RUS", "fetchUsers: ");
        Map<String,String> queryMap = new HashMap<>();
        queryMap.put("page","1");
        new FetchUsers(3).execute(queryMap);
            }

            //Se ejecuto cuarto
    private class FetchUsers extends GetUsers
        {
            public FetchUsers(int additionalPage) {
                super(additionalPage);
            }


            //Nos indica cuandp se acaba el servicio
            @Override
            protected void onPostExecute(Integer integer) {
                Log.d("onPostExecute",String.valueOf(integer));
                //Emitir un broadcasrt
                Intent intent=new Intent();
                intent.setAction("com.desafiolatam.desafioface.background.USERS_FINISHED");
                LocalBroadcastManager.getInstance(RecentUserService.this).sendBroadcastSync(intent);
            }
        }


}
