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
import com.udl.bss.barbershopschedule.domain.BarberService;
import com.udl.bss.barbershopschedule.serverCommunication.APIController;


public class BarberServiceDetailFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private String service_id;
    private int service_idToUpdate, barberShop_idToUpdate;
    private EditText name_cv, price_cv, duration_cv;


    private Barber barber;
    private SharedPreferences mPrefs;


    public BarberServiceDetailFragment() {
        // Required empty public constructor
    }

    public static BarberServiceDetailFragment newInstance(BarberService service) {
        BarberServiceDetailFragment fragment = new BarberServiceDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("service", service);
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

        View view = inflater.inflate(R.layout.fragment_barber_service_detail, container, false);

        Bundle bundle = getArguments();

        String s_name = bundle.getString("name");
        String d_price = Double.toString(bundle.getDouble("price"));
        String d_duration = Double.toString(bundle.getDouble("duration"));
        service_idToUpdate = bundle.getInt("id");
        barberShop_idToUpdate = bundle.getInt("id_barber");

        service_id = Integer.toString(bundle.getInt("id"));

        name_cv = view.findViewById(R.id.name_cv);
        price_cv = view.findViewById(R.id.price_cv);
        duration_cv = view.findViewById(R.id.duration_cv);

        name_cv.setText(s_name);
        price_cv.setText(d_price);
        duration_cv.setText(d_duration);

        Button btn_delete = (Button) view.findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
                alert.setTitle(getString(R.string.delete_service_dialog_title));
                alert.setMessage(getString(R.string.delete_service_dialog));
                alert.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.accept_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        APIController.getInstance().removeService(barber.getToken(), service_id);
                        Toast.makeText(getContext(), "Your service was deleted succesfully", Toast.LENGTH_SHORT).show();
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

        Button btn_update = (Button) view.findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (creationCheck()) {

                    if(creationDurationCheck()) {

                        AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
                        alert.setTitle(getString(R.string.update_title_alert));
                        alert.setMessage(getString(R.string.update_service_dialog));
                        alert.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.accept_button), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String nameToUpdate = name_cv.getText().toString();
                                Double priceToUpdate = Double.parseDouble(price_cv.getText().toString());
                                Double durationToUpdate = Double.parseDouble(duration_cv.getText().toString());

                                BarberService serviceUpdated = new BarberService(service_idToUpdate, barberShop_idToUpdate, nameToUpdate, priceToUpdate, durationToUpdate);

                                APIController.getInstance().updateService(barber.getToken(), serviceUpdated);
                                Toast.makeText(getContext(), "Your service was updated succesfully", Toast.LENGTH_SHORT).show();
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
                    } else {
                        Toast.makeText(getContext(), getString(R.string.field_duration_error), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), getString(R.string.field_error), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private boolean creationCheck () {

        return name_cv != null && !name_cv.getText().toString().equals("")
                && price_cv != null && !price_cv.getText().toString().equals("")
                && duration_cv != null && !duration_cv.getText().toString().equals("");
    }

    private boolean creationDurationCheck() {
        Double durationDouble = Double.parseDouble(duration_cv.getText().toString());
        return durationDouble%15 == 0;
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
