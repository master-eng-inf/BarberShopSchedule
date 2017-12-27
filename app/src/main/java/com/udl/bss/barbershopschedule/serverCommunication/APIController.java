package com.udl.bss.barbershopschedule.serverCommunication;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.udl.bss.barbershopschedule.domain.Barber;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gerard on 26/12/17.
 */

public class APIController {

    private static APIController instance;

    private APIController() {}

    public static synchronized APIController getInstance(){
        if(instance == null){
            instance = new APIController();
        }
        return instance;
    }

    public Task<String> getSessionToken(String id){
        final TaskCompletionSource<String> tcs = new TaskCompletionSource<>();

        ApiUtils.getService().getSessionToken(id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s = response.body().string();
                    Log.i("APISERVER", s);
                    tcs.setResult(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("APISERVER", "Get session token ERROR");
            }
        });
        return tcs.getTask();
    }

    public Task<List<Barber>> getAllBarbers(String token){
        final TaskCompletionSource<List<Barber>> tcs = new TaskCompletionSource<>();

        ApiUtils.getService().getAllBarbers(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s = response.body().string();
                    List<Barber> barberList = new ArrayList<>();

                    JSONArray jsonArray = new JSONArray(s);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        Barber barber = new Barber(
                                json.getInt("id"),
                                json.getString("name"),
                                json.getString("email"),
                                json.getString("places_id"),
                                json.getString("password"),
                                json.getString("telephone"),
                                "male/female",
                                json.getString("description"),
                                json.getString("address")+", "+json.getString("city"),
                                null);
                        barberList.add(barber);
                    }

                    Log.i("APISERVER", s);
                    tcs.setResult(barberList);

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("APISERVER", "Get all barbers ERROR");
            }
        });

        return tcs.getTask();
    }


}
