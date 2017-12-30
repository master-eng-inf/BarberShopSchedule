package com.udl.bss.barbershopschedule.fragments;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.adapters.BarberAppointmentsAdapter;
import com.udl.bss.barbershopschedule.domain.Appointment;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.serverCommunication.APIController;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BarberScheduleDateListFragment extends Fragment {
    private static final String TAG= "BarberScheduleDateList";

    private Barber barber;
    private RecyclerView appointmentsRecyclerView;
    private SharedPreferences mPrefs;

    String selectedDate = "";

    public BarberScheduleDateListFragment() {}

    public static BarberScheduleDateListFragment newInstance() {
        return new BarberScheduleDateListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_barber_schedule_date_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            mPrefs = getActivity().getSharedPreferences("USER", Activity.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = mPrefs.getString("user", "");
            barber = gson.fromJson(json, Barber.class);
        }


        if (getView() != null) {
            appointmentsRecyclerView = getView().findViewById(R.id.rv_list_appointments);
        }

        if (appointmentsRecyclerView != null) {
            appointmentsRecyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            appointmentsRecyclerView.setLayoutManager(llm);

            setAppointmentsItems();
        }
    }

    private void setAppointmentsItems(){

        if (barber != null){

            APIController.getInstance().getAppointmentsByBarber(barber.getToken(), String.valueOf(barber.getId()))
                    .addOnCompleteListener(new OnCompleteListener<List<Appointment>>() {
                @Override
                public void onComplete(@NonNull Task<List<Appointment>> task) {
                    String splitedDate[];
                    String dateOfAppointment;
                    List<Appointment> appointmentList = task.getResult();

                    Log.d(TAG, "setAppointmentsItems: " + appointmentList);

                    for (Appointment appointment: appointmentList) {
                        splitedDate = appointment.getDate().split(" ");
                        dateOfAppointment = splitedDate[0];
                        if (!selectedDate.equals(dateOfAppointment)){
                            appointmentList.remove(appointment);
                        }
                    }

                    BarberAppointmentsAdapter adapter = new BarberAppointmentsAdapter(
                            appointmentList,
                            barber.getToken());
                    appointmentsRecyclerView.setAdapter(adapter);
                }
            });

        }
    }

    void setSelectedDate(String date){
        selectedDate = date;
    }
}
