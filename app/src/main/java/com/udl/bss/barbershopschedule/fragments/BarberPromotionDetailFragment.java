package com.udl.bss.barbershopschedule.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.domain.Promotion;

public class BarberPromotionDetailFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public BarberPromotionDetailFragment() {
        // Required empty public constructor
    }

    public static BarberPromotionDetailFragment newInstance(Promotion promotion) {
        BarberPromotionDetailFragment fragment = new BarberPromotionDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("promotion", promotion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_barber_promotion_detail, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        Promotion promotion = args.getParcelable("promotion");

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

    }
}
