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
import com.udl.bss.barbershopschedule.adapters.PromotionAdapter;
import com.udl.bss.barbershopschedule.domain.Promotion;
import com.udl.bss.barbershopschedule.listeners.PromotionClick;

import java.util.ArrayList;
import java.util.List;


public class BarberPromotionsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private RecyclerView promotionsRecyclerView;

    public BarberPromotionsFragment() {
        // Required empty public constructor
    }

    public static BarberPromotionsFragment newInstance() {
        return new BarberPromotionsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_barber_promotions, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null) {
            promotionsRecyclerView = getView().findViewById(R.id.rv);
        }

        if (promotionsRecyclerView != null) {
            promotionsRecyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            promotionsRecyclerView.setLayoutManager(llm);

            setPromotionsItems();

        }
    }


    private void setPromotionsItems() {
        List<Promotion> promotionList = new ArrayList<>();

        Promotion promotion1 = new Promotion(1, "Barber1", "Hair cut", "50% off");
        Promotion promotion2 = new Promotion(2, "Barber1", "Tint","2x1");
        Promotion promotion3 = new Promotion(1, "Barber1", "Hair cut", "50% off");
        Promotion promotion4 = new Promotion(2, "Barber1", "Tint","2x1");

        promotionList.add(promotion1);
        promotionList.add(promotion2);
        promotionList.add(promotion3);
        promotionList.add(promotion4);


        PromotionAdapter adapter = new PromotionAdapter(promotionList, new PromotionClick(getActivity(), promotionsRecyclerView));
        promotionsRecyclerView.setAdapter(adapter);
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
