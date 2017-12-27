package com.udl.bss.barbershopschedule.serverCommunication;

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


    /* Barber Shop Controller */

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
                                json.getString("address"),
                                json.getString("city"),
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


    public Task<Void> createBarber(Barber barber) {
        final TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("id", String.valueOf(barber.getId()));
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
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("APISERVER", "Create barber OK");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("APISERVER", "Create barber ERROR");
            }
        });

        return tcs.getTask();
    }


    public Task<Integer> getBarbersCount(String token){
        final TaskCompletionSource<Integer> tcs = new TaskCompletionSource<>();

        ApiUtils.getService().getAllBarbers(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s = response.body().string();
                    JSONArray jsonArray = new JSONArray(s);

                    Log.i("APISERVER", String.valueOf(jsonArray.length()));
                    tcs.setResult(jsonArray.length());

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("APISERVER", "Get barbers count ERROR");
            }
        });

        return tcs.getTask();
    }




    /* Client controller */

    public Task<Void> createClient(Client client) {
        final TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("id", String.valueOf(client.getId()));
        requestBody.put("password", client.getPassword());
        requestBody.put("email", client.getEmail());
        requestBody.put("telephone", client.getPhone());
        requestBody.put("name", client.getName());
        //requestBody.put("gender", String.valueOf(client.getGender()));
        requestBody.put("age", String.valueOf(client.getAge()));


        ApiUtils.getService().createClient(requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("APISERVER", "Create client OK");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("APISERVER", "Create client ERROR");
            }
        });

        return tcs.getTask();
    }


    public Task<Integer> getClientsCount(String token){
        final TaskCompletionSource<Integer> tcs = new TaskCompletionSource<>();

        ApiUtils.getService().getAllClients(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s = response.body().string();
                    JSONArray jsonArray = new JSONArray(s);

                    Log.i("APISERVER", String.valueOf(jsonArray.length()));
                    tcs.setResult(jsonArray.length());

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("APISERVER", "Get clients count ERROR");
            }
        });

        return tcs.getTask();
    }





}
