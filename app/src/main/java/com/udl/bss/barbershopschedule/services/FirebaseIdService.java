package com.udl.bss.barbershopschedule.services;

import com.google.firebase.iid.FirebaseInstanceIdService;


public class FirebaseIdService extends FirebaseInstanceIdService {

    public FirebaseIdService() { }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

    }
}
