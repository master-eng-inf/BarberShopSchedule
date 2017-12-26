package com.udl.bss.barbershopschedule.serverCommunication;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gerard on 26/12/17.
 */

public class ApiUtils {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://82.223.24.126:28080/BarberShopScheduleWeb/barberShopScheduleAPI/")
            .client(getHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static APIService service = retrofit.create(APIService.class);

    private static OkHttpClient getHttpClient(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        return httpClient.build();
    }

    public static APIService getService(){
        return service;
    }
}
