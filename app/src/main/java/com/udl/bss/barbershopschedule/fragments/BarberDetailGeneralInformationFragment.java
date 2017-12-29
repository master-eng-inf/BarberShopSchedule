package com.udl.bss.barbershopschedule.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.ReviewsActivity;
import com.udl.bss.barbershopschedule.database.BLL;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.domain.Client;
import com.udl.bss.barbershopschedule.domain.Review;
import com.udl.bss.barbershopschedule.listeners.GoogleMaps;
import com.udl.bss.barbershopschedule.serverCommunication.APIController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BarberDetailGeneralInformationFragment extends Fragment {
    private static final String BARBER_SHOP = "barber_shop";
    private Barber barber;
    private GoogleMaps googleMaps;
    protected GeoDataClient mGeoDataClient;
    private SharedPreferences mPrefs;
    private Client client;

    public static BarberDetailGeneralInformationFragment newInstance(Barber param1) {
        BarberDetailGeneralInformationFragment fragment = new BarberDetailGeneralInformationFragment();
        Bundle args = new Bundle();
        args.putParcelable(BARBER_SHOP, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.barber = getArguments().getParcelable(BARBER_SHOP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_barber_detail_general_information, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() != null) {
            mPrefs = getActivity().getSharedPreferences("USER", Activity.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = mPrefs.getString("user", "");
            client = gson.fromJson(json, Client.class);
        }


        if (client != null && barber != null) {
            APIController.getInstance().getReviewsByBarber(client.getToken() ,String.valueOf(barber.getId()))
                    .addOnCompleteListener(new OnCompleteListener<List<Review>>() {
                @Override
                public void onComplete(@NonNull Task<List<Review>> task) {
                    List<Review> reviewList = task.getResult();
                    double rating = 0.0;

                    for (Review aReviewList : reviewList) {
                        rating += aReviewList.GetMark();
                    }

                    RatingBar ratingBar = view.findViewById(R.id.rating_bar);
                    ratingBar.setRating((float) rating / reviewList.size());
                }
            });
        }


        TextView name = view.findViewById(R.id.Name);
        name.setText(this.barber.getName());

        TextView description = view.findViewById(R.id.Description);
        description.setText(this.barber.getDescription());

        Button rate = view.findViewById(R.id.rate_button);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ReviewsActivity.class);
                intent.putExtra("barber", barber);

                startActivity(intent);
            }
        });

        mGeoDataClient = Places.getGeoDataClient(getActivity(), null);
        googleMaps = new GoogleMaps(getActivity());

        if (barber.getPlacesID() != null) {
            mGeoDataClient.getPlaceById(barber.getPlacesID()).addOnCompleteListener(new OnCompleteListener<PlaceBufferResponse>() {
                @Override
                public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
                    if (task.isSuccessful()) {
                        try {
                            PlaceBufferResponse places = task.getResult();
                            Place myPlace = places.get(0);
                            googleMaps.setMap(myPlace.getName().toString(), myPlace.getLatLng());
                            Log.i("PLACES API", "Place found: " + myPlace.getName());
                            places.release();
                        } catch (Exception e) {
                            Log.e("PLACES API", "Invalid place.");
                        }

                    } else {
                        Log.e("PLACES API", "Place not found.");
                    }
                }
            });
        }

    }
}
