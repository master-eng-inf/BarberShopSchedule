package com.udl.bss.barbershopschedule.serverCommunication;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.domain.Client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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




    /* Session Controller */

    public Task<String> logInUser(String username, String password){
        final TaskCompletionSource<String> tcs = new TaskCompletionSource<>();

        ApiUtils.getService().logInUser(username, password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    ResponseBody body = response.body();
                    if (body != null){
                        String s = body.string();
                        Log.i("APISERVER", s);
                        tcs.setResult(s);
                    } else {
                        tcs.setResult(null);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.i("APISERVER", "Log in user ERROR");
            }
        });
        return tcs.getTask();
    }





    /* User Controller */

    public Task<Boolean> isUserAvailable(String username){
        final TaskCompletionSource<Boolean> tcs = new TaskCompletionSource<>();

        ApiUtils.getService().isUserAvailable(username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    ResponseBody body = response.body();
                    if (body != null){
                        String s = body.string();
                        Log.i("APISERVER", s);
                        tcs.setResult(Boolean.valueOf(s));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.i("APISERVER", "Is user available ERROR");
            }
        });
        return tcs.getTask();
    }




    /* Barber Shop Controller */

    public Task<List<Barber>> getAllBarbers(String token){
        final TaskCompletionSource<List<Barber>> tcs = new TaskCompletionSource<>();

        ApiUtils.getService().getAllBarbers(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    ResponseBody body = response.body();
                    if (body != null){
                        String s = body.string();
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
                                    json.getString("gender"),
                                    json.getString("description"),
                                    json.getString("address"),
                                    json.getString("city"),
                                    null);
                            barberList.add(barber);
                        }

                        Log.i("APISERVER", s);
                        tcs.setResult(barberList);
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.i("APISERVER", "Get all barbers ERROR");
            }
        });

        return tcs.getTask();
    }


    public void createBarber(Barber barber) {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("password", barber.getPassword());
        requestBody.put("email", barber.getEmail());
        requestBody.put("telephone", barber.getPhone());
        requestBody.put("name", barber.getName());
        requestBody.put("address", barber.getAddress());
        requestBody.put("city", barber.getCity());
        requestBody.put("description", barber.getDescription());
        requestBody.put("places_id", barber.getPlacesID());

        ApiUtils.getService().createBarber(requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                Log.i("APISERVER", "Create barber OK");
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.i("APISERVER", "Create barber ERROR");
            }
        });

    }




    /* Client controller */

    public void createClient(Client client) {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("password", client.getPassword());
        requestBody.put("email", client.getEmail());
        requestBody.put("telephone", client.getPhone());
        requestBody.put("name", client.getName());
        requestBody.put("gender", String.valueOf(client.getGender()));
        requestBody.put("age", String.valueOf(client.getAge()));


        ApiUtils.getService().createClient(requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                Log.i("APISERVER", "Create client OK");
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.i("APISERVER", "Create client ERROR");
            }
        });

    }


}
