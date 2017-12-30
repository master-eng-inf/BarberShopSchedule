package com.udl.bss.barbershopschedule.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.udl.bss.barbershopschedule.HomeActivity;
import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.database.BLL;
import com.udl.bss.barbershopschedule.domain.BarberService;

public class BarberServiceDetailFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private BarberService serviceToChange;

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

        EditText name_cv = (EditText) view.findViewById(R.id.name_cv);
        EditText price_cv = (EditText) view.findViewById(R.id.price_cv);
        EditText duration_cv = (EditText) view.findViewById(R.id.duration_cv);

        name_cv.setText(s_name);
        price_cv.setText(d_price);
        duration_cv.setText(d_duration);

        Button btn_delete = (Button) view.findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               deleteInDB();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Bundle args = getArguments();

        serviceToChange = new BarberService(args.getInt("id"),args.getInt("id_barber"),args.getString("name"),args.getDouble("price"),args.getDouble("duration"));


    }


    private void deleteInDB () {

        BLL instance = new BLL(getContext());

        instance.Delete_Service(serviceToChange);

        Toast.makeText(getContext(), "Your service was deleted succesfully", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getContext(), HomeActivity.class);
        intent.putExtra("user", "Barber");
        this.startActivity(intent);

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
