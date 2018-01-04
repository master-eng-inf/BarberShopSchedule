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
import com.udl.bss.barbershopschedule.domain.Promotion;
import static android.content.Context.MODE_PRIVATE;

public class PromotionDetailFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Promotion promo;
    FloatingActionButton btn_edit;
    TextView name,description,service,is_promotional,barber,id;
    private SharedPreferences mPrefs;

    public PromotionDetailFragment() {
        // Required empty public constructor
    }

    public static PromotionDetailFragment newInstance(Promotion promotion) {
        PromotionDetailFragment fragment = new PromotionDetailFragment();
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
        View view = inflater.inflate(R.layout.promotion_details, container, false);

        name = view.findViewById(R.id.name_cv);
        description = view.findViewById(R.id.description_cv);
        service = view.findViewById(R.id.service_cv);
        is_promotional = view.findViewById(R.id.is_promotional_cv);

        barber = view.findViewById(R.id.barber_cv);
        id = view.findViewById(R.id.id_cv);

        btn_edit = view.findViewById(R.id.edit_btn);

        mPrefs = getActivity().getSharedPreferences("USER", MODE_PRIVATE);
        String mode = mPrefs.getString("mode", "");

        if(mode.equals("User")) {
            btn_edit.setVisibility(View.GONE);
        }

        btn_edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                int id = promo.getId();
                int id_barber = promo.getBarber_shop_id();
                int is_promotional = promo.getIs_Promotional();
                String name_cv = name.getText().toString();
                String description_cv = description.getText().toString();
                String service_cv = service.getText().toString();

                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                bundle.putInt("id_barber", id_barber);
                bundle.putInt("is_promotional", is_promotional);
                bundle.putString("name", name_cv);
                bundle.putString("description", description_cv);
                bundle.putString("service", service_cv);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                BarberPromotionDetailFragment fragment = new BarberPromotionDetailFragment();
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
        Promotion promotion = args.getParcelable("promotion");
        //promo = args.getParcelable("promotion");

        TextView name_cv = view.findViewById(R.id.name_cv);
        TextView description_cv = view.findViewById(R.id.description_cv);
        TextView service_cv = view.findViewById(R.id.service_cv);
        TextView is_promotional_cv = view.findViewById(R.id.is_promotional_cv);

        TextView barber_cv = view.findViewById(R.id.barber_cv);
        TextView id_cv = view.findViewById(R.id.id_cv);
        if (promotion != null) {
            name_cv.setText(promotion.getName());
            description_cv.setText(promotion.getDescription());

            String service_id = Double.toString(promotion.getService_id());
            service_cv.setText(service_id);

            if(promotion.getIs_Promotional()==0) {
                is_promotional_cv.setText("No");
            } else{
                is_promotional_cv.setText("Yes");
            }

            String barber = Double.toString(promotion.getBarber_shop_id());
            barber_cv.setText(barber);

            String id = Double.toString(promotion.getId());
            id_cv.setText(id);

            promo = new Promotion(promotion.getId(),promotion.getBarber_shop_id(),promotion.getService_id(),promotion.getName(),promotion.getDescription(),promotion.getIs_Promotional());
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
