package com.desafiolatam.desafioface.networts;

import android.os.AsyncTask;
import android.util.Log;

import com.desafiolatam.desafioface.models.Developer;
import com.desafiolatam.desafioface.networts.users.UserInterceptor;
import com.desafiolatam.desafioface.networts.users.Users;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class GetUsers extends AsyncTask<Map<String,String>,Integer,Integer> {

    private int additionalPage;
    private Map<String,String> queryMap;
    private int result;
    private final Users request =  new UserInterceptor().get();

    public GetUsers(int additionalPage) {
        Log.d("GU", "GetUsers: ");
        this.additionalPage = additionalPage;
    }

    @Override
    protected Integer doInBackground(Map<String, String>... maps) {

        Log.d("GU", "doInBackground: ");
        queryMap=maps[0];
        if(additionalPage <0)
        {
            while(200 == connect())
            {
              increasePage();
            }
       }else
           {
              while(additionalPage >=0)
              {
                 additionalPage--;
                 connect();
                 increasePage();
              }
           }
        return null;
    }

    private void increasePage(){
        Log.d("GU", "increasePage: ");
        int  page=Integer.parseInt(queryMap.get("page"));
        page++;
        queryMap.put("page",String.valueOf(page));

    }

    private int connect(){

        Log.d("GU", "connect: ");
        int code=666;
        Call<Developer[]>  call=request.get(queryMap);
        try {
            Response<Developer[]> response=call.execute();
            code=response.code();
            if(200==code && response.isSuccessful())
            {
              Developer[] developers=response.body();
                 if(developers!= null && developers.length>0)
                 {
                     Log.d("developers", String.valueOf(developers.length));
                     for(Developer servDep:developers)
                     {
                         List<Developer> localDevs=Developer.find(Developer.class,"serverId = ?",String.valueOf(servDep.getId()));
                          if(localDevs.size() >0 && localDevs!=null)
                          {
                              Developer local=localDevs.get(0);
                              local.setEmail(servDep.getEmail());
                              local.setPhoto_url(servDep.getPhoto_url());
                              local.save();
                          }
                          else
                              {
                                  servDep.create();
                              }
                     }
                 }

            }else
                {
                  code = 777;
                }
        } catch (IOException e) {
            e.printStackTrace();
            code = 888;
        }
          result = code;
          return result;
    }
}
