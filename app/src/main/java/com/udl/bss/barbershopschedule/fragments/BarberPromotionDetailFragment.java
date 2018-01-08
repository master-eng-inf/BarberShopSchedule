package com.udl.bss.barbershopschedule.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.udl.bss.barbershopschedule.HomeActivity;
import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.adapters.ServiceSpinnerAdapter;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.domain.BarberService;
import com.udl.bss.barbershopschedule.domain.Promotion;
import com.udl.bss.barbershopschedule.serverCommunication.APIController;

import java.util.List;

public class BarberPromotionDetailFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private String promotion_id;
    private int promotion_idToUpdate, barberShop_idToUpdate;
    private EditText name_cv, description_cv;

    private BarberService service;
    private CheckBox checkBox;
    private Spinner spinner;

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
        boolean is_promo;
        int s_promotional = bundle.getInt("is_promotional");
        if (s_promotional == 1){
            is_promo=true;
        } else {
            is_promo=false;
        }
        promotion_idToUpdate = bundle.getInt("id");
        barberShop_idToUpdate = bundle.getInt("id_barber");

        promotion_id = Integer.toString(bundle.getInt("id"));

        name_cv = view.findViewById(R.id.name_cv);
        description_cv = view.findViewById(R.id.description_cv);
        checkBox = view.findViewById(R.id.checkbox_promotional);
        spinner = view.findViewById(R.id.service_spinner);
        APIController.getInstance().getServicesByBarber(barber.getToken(), String.valueOf(barber.getId()))
                .addOnCompleteListener(new OnCompleteListener<List<BarberService>>() {
            @Override
            public void onComplete(@NonNull Task<List<BarberService>> task) {
                List<BarberService> serviceList = task.getResult();
                BarberService[] servicesArray = new BarberService[serviceList.size()];
                servicesArray = serviceList.toArray(servicesArray);

                if (serviceList.size() > 0) service = serviceList.get(0);

                final ServiceSpinnerAdapter adapter = new ServiceSpinnerAdapter(
                        getContext(),
                        android.R.layout.simple_spinner_item,
                        servicesArray
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        service = adapter.getItem(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {}
                });
            }
        });


        name_cv.setText(s_name);
        description_cv.setText(s_description);
        checkBox.setChecked(is_promo);

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
                        Toast.makeText(getContext(), getString(R.string.promotion_delete), Toast.LENGTH_SHORT).show();
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
            public void onClick(View v){

                if(creationCheck()) {

                    AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
                    alert.setTitle(getString(R.string.update_title_alert));
                    alert.setMessage(getString(R.string.update_promotion_dialog));
                    alert.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.accept_button), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String nameToUpdate = name_cv.getText().toString();
                            String descriptionToUpdate = description_cv.getText().toString();
                            int isPromotionalToUpdate = checkBox.isChecked() ? 1 : 0;

                            Promotion promotionUpdated = new Promotion(promotion_idToUpdate, barberShop_idToUpdate, service.getId(), nameToUpdate, descriptionToUpdate, isPromotionalToUpdate);

                            APIController.getInstance().updatePromotion(barber.getToken(), promotionUpdated);
                            Toast.makeText(getContext(), getString(R.string.promotion_update), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), getString(R.string.field_error), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;

    }

    private boolean creationCheck () {

        return name_cv != null && !name_cv.getText().toString().equals("")
                && description_cv != null && !description_cv.getText().toString().equals("")
                && service != null;

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
