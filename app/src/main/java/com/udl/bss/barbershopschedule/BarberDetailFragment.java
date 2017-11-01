package com.udl.bss.barbershopschedule;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.udl.bss.barbershopschedule.domain.Barber;

public class BarberDetailFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public BarberDetailFragment() {
        // Required empty public constructor
    }

    public static BarberDetailFragment newInstance(Barber barber) {
        BarberDetailFragment fragment = new BarberDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("barber", barber);
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
        return inflater.inflate(R.layout.fragment_barber_detail, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = view.findViewById(R.id.edit_fab);
        ImageView imageView = view.findViewById(R.id.image_detail);
        TextView textView_description = view.findViewById(R.id.description_detail);
        TextView textView_name = view.findViewById(R.id.name_detail);
        TextView textView_addr = view.findViewById(R.id.address_detail);

        Bundle args = getArguments();

        Barber barber = args.getParcelable("barber");
        if (barber != null) {

            if (barber.getImage() != null) {
                imageView.setImageBitmap(barber.getImage());
            }
            textView_name.setText(barber.getName());
            textView_description.setText(barber.getDescription());
            String addr = barber.getAddress() + ", " + barber.getCity();
            textView_addr.setText(addr);

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

    }

}
