package com.udl.bss.barbershopschedule.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.udl.bss.barbershopschedule.HomeActivity;
import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.domain.Promotion;
import com.udl.bss.barbershopschedule.serverCommunication.APIController;

public class BarberPromotionDetailFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private String promotion_id;

    private Barber barber;
    private SharedPreferences mPrefs;

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

        mPrefs = getActivity().getSharedPreferences("USER", Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("user", "");
        barber = gson.fromJson(json, Barber.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_barber_promotion_detail, container, false);

        Bundle bundle = getArguments();

        String s_name = bundle.getString("name");
        String s_description = bundle.getString("description");
        String s_service = bundle.getString("service");

        promotion_id = Integer.toString(bundle.getInt("id"));

        EditText name_cv = (EditText) view.findViewById(R.id.name_cv);
        EditText description_cv = (EditText) view.findViewById(R.id.description_cv);
        EditText service_cv = (EditText) view.findViewById(R.id.service_cv);

        name_cv.setText(s_name);
        description_cv.setText(s_description);
        service_cv.setText(s_service);

        Button btn_delete = (Button) view.findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
                alert.setTitle(getString(R.string.delete_promotion_dialog_title));
                alert.setMessage(getString(R.string.delete_promotion_dialog));
                alert.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.accept_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        APIController.getInstance().removePromotion(barber.getToken(), promotion_id);
                        Toast.makeText(getContext(), "Your Promotion was deleted succesfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), HomeActivity.class);
                        intent.putExtra("user", "Barber");
                        startActivity(intent);
                    }
                });
                alert.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
            }
        });
        return view;

    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
