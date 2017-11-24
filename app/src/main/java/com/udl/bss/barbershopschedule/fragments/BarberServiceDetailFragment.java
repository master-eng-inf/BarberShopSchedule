package com.udl.bss.barbershopschedule.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.adapters.PagerAdapter;
import com.udl.bss.barbershopschedule.domain.BarberService;

public class BarberServiceDetailFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public BarberServiceDetailFragment() {
        // Required empty public constructor
    }

    public static BarberServiceDetailFragment newInstance(BarberService barberService) {
        BarberServiceDetailFragment fragment = new BarberServiceDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("barberService", barberService);
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
        return inflater.inflate(R.layout.fragment_barber_service_detail, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        BarberService barberService = args.getParcelable("barberService");

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
