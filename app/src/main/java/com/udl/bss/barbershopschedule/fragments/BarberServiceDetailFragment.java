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
import android.widget.TextView;
import android.widget.Toast;

import com.udl.bss.barbershopschedule.HomeActivity;
import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.database.BLL;
import com.udl.bss.barbershopschedule.domain.BarberService;

public class BarberServiceDetailFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private BarberService serviceToDelete;

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
        BarberService service = args.getParcelable("service");
        serviceToDelete = args.getParcelable("service");

        TextView name_cv = view.findViewById(R.id.name_cv);
        TextView price_cv = view.findViewById(R.id.price_cv);
        TextView duration_cv = view.findViewById(R.id.duration_cv);
        if (service != null) {
            name_cv.setText(service.Get_Name());

            String price = Double.toString(service.Get_Price());
            price_cv.setText(price);

            String duration = Double.toString(service.Get_Duration());
            duration_cv.setText(duration);
        }

    }


    private void deleteInDB () {

        BLL instance = new BLL(getContext());

        instance.Delete_Service(serviceToDelete);

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
