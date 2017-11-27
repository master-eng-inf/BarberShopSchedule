package com.udl.bss.barbershopschedule.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionMenu;
import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.adapters.AppointmentAdapter;
import com.udl.bss.barbershopschedule.adapters.PromotionAdapter;
import com.udl.bss.barbershopschedule.database.BLL;
import com.udl.bss.barbershopschedule.domain.Appointment;
import com.udl.bss.barbershopschedule.domain.Promotion;
import com.udl.bss.barbershopschedule.listeners.AppointmentClick;
import com.udl.bss.barbershopschedule.listeners.PromotionClick;

import java.util.List;

public class HomeFragment extends Fragment {
    private static final String CLIENT_ID = "client_id";
    private int client_id;
    private RecyclerView appointmentsRecyclerView;
    private RecyclerView promotionsRecycleView;
    private BLL instance;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
    }


    public static HomeFragment newInstance(int client_id) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(CLIENT_ID, client_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.client_id = getArguments().getInt(CLIENT_ID);
        }
        this.instance = new BLL(getContext());
        this.instance.Initialize_Database();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);

            getActivity().getWindow().setEnterTransition(fade);
            getActivity().getWindow().setExitTransition(fade);
        }

        if (getView() != null) {
            appointmentsRecyclerView = getView().findViewById(R.id.rv);
            promotionsRecycleView = getView().findViewById(R.id.rv2);
        }


        if (appointmentsRecyclerView != null) {
            appointmentsRecyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            appointmentsRecyclerView.setLayoutManager(llm);

            setAppointmentItems();
        }

        if (promotionsRecycleView != null) {
            promotionsRecycleView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            promotionsRecycleView.setLayoutManager(llm);

            setPromotionsItems();
        }

        FloatingActionMenu floatingActionMenu = getActivity().findViewById(R.id.fab_menu);
        floatingActionMenu.setClosedOnTouchOutside(true);
    }

    private void setAppointmentItems() {
        List<Appointment> appointmentList;

        appointmentList = this.instance.Get_ClientAppointments(this.client_id);

        AppointmentAdapter adapter = new AppointmentAdapter(appointmentList, new AppointmentClick(getActivity(), appointmentsRecyclerView), getContext());
        appointmentsRecyclerView.setAdapter(adapter);
    }


    private void setPromotionsItems() {
        List<Promotion> promotionList;

        //TODO
        promotionList = this.instance.Get_BarberShopPromotions(0);
        PromotionAdapter adapter = new PromotionAdapter(promotionList, new PromotionClick(getActivity(), promotionsRecycleView), getContext());
        promotionsRecycleView.setAdapter(adapter);
    }



    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
