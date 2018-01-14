package com.udl.bss.barbershopschedule.services;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.gson.Gson;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.domain.Client;
import com.udl.bss.barbershopschedule.serverCommunication.APIController;


public class FirebaseIdService extends FirebaseInstanceIdService {

    private final static String TAG = "FCM";

    private SharedPreferences mPrefs;

    public FirebaseIdService() { }

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        mPrefs =  getSharedPreferences("USER", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("user", "");
        String mode = mPrefs.getString("mode", "");
        if (mode.equals("Barber")) {
            Barber barber = gson.fromJson(json, Barber.class);
            barber.setFirebaseToken(token);

            APIController.getInstance().updateDeviceToken(
                    barber.getToken(),
                    token,
                    barber.getName()
            );

            saveToSharedPreferences(gson.toJson(barber));
        } else {
            Client client = gson.fromJson(json, Client.class);
            client.setFirebaseToken(token);

            APIController.getInstance().updateDeviceToken(
                    client.getToken(),
                    token,
                    client.getName()
            );

            saveToSharedPreferences(gson.toJson(client));
        }

    }

    private void saveToSharedPreferences(String user) {
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("user", user);
        prefsEditor.apply();
    }
}
