package com.udl.bss.barbershopschedule.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.adapters.AppointmentAdapter;
import com.udl.bss.barbershopschedule.adapters.BarberAppointmentsAdapter;
import com.udl.bss.barbershopschedule.database.BLL;
import com.udl.bss.barbershopschedule.domain.Appointment;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.listeners.AppointmentClick;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BarberScheduleDateListFragment extends Fragment {
    private static final String TAG= "BarberScheduleDateList";

    private static final String BARBER_SHOP= "barber_shop";
    private Barber barber;
    private RecyclerView appointmentsRecyclerView;

    String selectedDate = "";

    public BarberScheduleDateListFragment() {
        // Required empty public constructor
    }

    public static BarberScheduleDateListFragment newInstance(Barber  app) {
        BarberScheduleDateListFragment fragment = new BarberScheduleDateListFragment();
        Bundle args = new Bundle();
        args.putParcelable(BARBER_SHOP, app);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.barber = getArguments().getParcelable(BARBER_SHOP);
            Log.d(TAG, "onCreate: " + barber);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       /* Bundle bundle = getArguments();
        if (bundle!= null){
            selectedDate = bundle.getString("date");
        }
        */
        return inflater.inflate(R.layout.fragment_barber_schedule_date_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
        BLL instance = new BLL(getContext());
        //Dates from appointments
        String splitedDate[];
        String timeOfAppointment = "";
        String dateOfAppointment = "";
        if (barber != null){
            ArrayList<Appointment> services = instance.Get_AllBarberShopAppointments(this.barber.getId());
            Log.d(TAG, "setAppointmentsItems: " + services);
            for (int i = services.size()-1; i>=0; i--) {
                splitedDate = services.get(i).getDate().split(" ");
                dateOfAppointment = splitedDate[0];
                if (!selectedDate.equals(dateOfAppointment)){
                    services.remove(i);
                }
            }

            BarberAppointmentsAdapter adapter = new BarberAppointmentsAdapter(services, getContext());
            appointmentsRecyclerView.setAdapter(adapter);
        }
    }

    void setSelectedDate(String date){
        selectedDate = date;
    }
}
