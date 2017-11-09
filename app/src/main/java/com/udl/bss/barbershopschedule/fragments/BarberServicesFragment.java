package com.udl.bss.barbershopschedule.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.adapters.PriceAdapter;
import com.udl.bss.barbershopschedule.domain.Price;
import com.udl.bss.barbershopschedule.listeners.PriceClick;

import java.util.ArrayList;
import java.util.List;


public class BarberServicesFragment extends Fragment {


    private RecyclerView pricesRecyclerView;

    private OnFragmentInteractionListener mListener;

    public BarberServicesFragment() {
        // Required empty public constructor
    }

    public static BarberServicesFragment newInstance() {
        return new BarberServicesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_barber_services, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getView() != null) {
            pricesRecyclerView = getView().findViewById(R.id.rv);
        }

        if (pricesRecyclerView != null) {
            pricesRecyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            pricesRecyclerView.setLayoutManager(llm);

            setPricesItems();

        }
    }

    private void setPricesItems() {
        List<Price> pricesList = new ArrayList<>();

        Price price1 = new Price(1, "Barber 1", "Hair cut", 7);
        Price price2 = new Price(2, "Barber 1", "Tint",12);

        pricesList.add(price1);
        pricesList.add(price2);

        PriceAdapter adapter = new PriceAdapter(pricesList, new PriceClick(getActivity(), pricesRecyclerView));
        pricesRecyclerView.setAdapter(adapter);
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
        void onFragmentInteraction(Uri uri);
    }
}
