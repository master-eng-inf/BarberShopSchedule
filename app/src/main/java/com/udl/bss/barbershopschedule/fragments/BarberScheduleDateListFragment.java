package com.udl.bss.barbershopschedule.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udl.bss.barbershopschedule.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BarberScheduleDateListFragment extends Fragment {
    private static final String TAG= "BarberScheduleDateList";
    String selectedDate = "";

    public BarberScheduleDateListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle!= null){
            selectedDate = bundle.getString("date");
            Log.d(TAG, "onCreateView: "+ selectedDate);
        }

        return inflater.inflate(R.layout.fragment_barber_schedule_date_list, container, false);
    }

}
