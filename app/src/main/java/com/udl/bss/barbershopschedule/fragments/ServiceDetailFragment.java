package com.udl.bss.barbershopschedule.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.domain.BarberService;
import static android.content.Context.MODE_PRIVATE;

public class ServiceDetailFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private BarberService servic;
    FloatingActionButton btn_edit;
    TextView name,price,duration,barber,id;
    private SharedPreferences mPrefs;

    public ServiceDetailFragment() {
        // Required empty public constructor
    }

    public static ServiceDetailFragment newInstance(BarberService service) {
        ServiceDetailFragment fragment = new ServiceDetailFragment();
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

        View view = inflater.inflate(R.layout.service_details, container, false);

        name = view.findViewById(R.id.name_cv);
        price = view.findViewById(R.id.price_cv);
        duration = view.findViewById(R.id.duration_cv);
        btn_edit = view.findViewById(R.id.edit_btn);

        barber = view.findViewById(R.id.barber_cv);
        id = view.findViewById(R.id.id_cv);

        mPrefs = getActivity().getSharedPreferences("USER", MODE_PRIVATE);
        String mode = mPrefs.getString("mode", "");

        if(mode.equals("User")) {
            btn_edit.setVisibility(View.GONE);
        }

        btn_edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int id = servic.getId();
                int id_barber = servic.getBarberShopId();
                String name_cv = servic.getName();
                Double price_cv = servic.getPrice();
                Double duration_cv = servic.getDuration();

                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                bundle.putInt("id_barber", id_barber);
                bundle.putString("name", name_cv);
                bundle.putDouble("price", price_cv);
                bundle.putDouble("duration", duration_cv);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                BarberServiceDetailFragment fragment = new BarberServiceDetailFragment();
                fragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.content_home, fragment);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        BarberService service = args.getParcelable("service");
        //servic = args.getParcelable("service");

        TextView name_cv = view.findViewById(R.id.name_cv);
        TextView price_cv = view.findViewById(R.id.price_cv);
        TextView duration_cv = view.findViewById(R.id.duration_cv);

        TextView barber_cv = view.findViewById(R.id.barber_cv);
        TextView id_cv = view.findViewById(R.id.id_cv);
        if (service != null) {
            name_cv.setText(service.getName());

            String price = Double.toString(service.getPrice());
            price_cv.setText(price);

            String duration = Double.toString(service.getDuration());
            duration_cv.setText(duration);

            String barber = Double.toString(service.getBarberShopId());
            barber_cv.setText(barber);

            String id = Double.toString(service.getId());
            id_cv.setText(id);

            servic = new BarberService(service.getId(),service.getBarberShopId(),service.getName(),service.getPrice(),service.getDuration());
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
