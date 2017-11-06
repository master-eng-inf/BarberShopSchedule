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

import com.udl.bss.barbershopschedule.adapters.PagerAdapter;
import com.udl.bss.barbershopschedule.R;
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

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.general_information));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.schedule));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.prices_and_promotions));

        Bundle args = getArguments();
        Barber barber = args.getParcelable("barber");

        ImageView imageView = (ImageView)view.findViewById(R.id.barber_shop_image);

        if (barber != null) {

            if (barber.getImage() != null) {
                imageView.setImageBitmap(barber.getImage());
            }

            final ViewPager viewPager = (ViewPager) view.findViewById(R.id.container);
            final PagerAdapter adapter = new PagerAdapter(getFragmentManager(), tabLayout.getTabCount(), barber);

            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
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
