package com.udl.bss.barbershopschedule.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.ReviewsActivity;
import com.udl.bss.barbershopschedule.database.BLL;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.domain.Review;

import java.util.ArrayList;
import java.util.Iterator;

public class BarberDetailGeneralInformationFragment extends Fragment {
    private static final String BARBER_SHOP = "barber_shop";
    private Barber barber;

    public static BarberDetailGeneralInformationFragment newInstance(Barber param1) {
        BarberDetailGeneralInformationFragment fragment = new BarberDetailGeneralInformationFragment();
        Bundle args = new Bundle();
        args.putParcelable(BARBER_SHOP, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.barber = getArguments().getParcelable(BARBER_SHOP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_barber_detail_general_information, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BLL instance = new BLL(getContext());
        ArrayList<Review> reviews = instance.Get_BarberShopReviews(this.barber.getId());
        double rating = 0.0;

        Iterator<Review> it = reviews.iterator();
        while (it.hasNext()) {
            rating += it.next().GetMark();
        }

        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rating_bar);
        ratingBar.setRating((float) rating / reviews.size());

        TextView name = (TextView) view.findViewById(R.id.Name);
        name.setText(this.barber.getName());

        TextView description = (TextView) view.findViewById(R.id.Description);
        description.setText(this.barber.getDescription());

        Button rate = (Button) view.findViewById(R.id.rate_button);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ReviewsActivity.class);
                intent.putExtra("barber", barber);

                startActivity(intent);
            }
        });
    }
}
