package com.udl.bss.barbershopschedule.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionMenu;
import com.udl.bss.barbershopschedule.HomeActivity;
import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.adapters.BarberAdapter;
import com.udl.bss.barbershopschedule.database.BLL;
import com.udl.bss.barbershopschedule.database.Users.UsersSQLiteManager;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.listeners.BarberClick;
import com.udl.bss.barbershopschedule.listeners.FloatingButtonScrollListener;

import java.util.List;


public class BarberListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private BarberAdapter adapter;
    private BLL instance;
    private OnFragmentInteractionListener mListener;

    String jsonStr;
    private String TAG = HomeActivity.class.getSimpleName();

    public BarberListFragment() {
    }

    public static BarberListFragment newInstance() {
        return new BarberListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.instance = new BLL(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_barber_list, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);

            getActivity().getWindow().setEnterTransition(fade);
            getActivity().getWindow().setExitTransition(fade);
        }

        final UsersSQLiteManager usm = new UsersSQLiteManager(getContext());
        List<Barber> barberList = usm.getRegisteredBarbers();

        if (getView() != null) {
            mRecyclerView = getView().findViewById(R.id.rv);
        }

        if (mRecyclerView != null) {

            final FloatingActionMenu floatingActionMenu = getActivity().findViewById(R.id.fab_menu);

            mRecyclerView.addOnScrollListener(new FloatingButtonScrollListener(floatingActionMenu));

            mRecyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(llm);

            adapter = new BarberAdapter(barberList, new BarberClick(getActivity(), mRecyclerView), getContext());
            mRecyclerView.setAdapter(adapter);
        }

        /* Swipe down to refresh */
        final SwipeRefreshLayout sr = getView().findViewById(R.id.swiperefresh);
        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sr.setRefreshing(false);

                if (adapter == null) {
                    adapter = (BarberAdapter) mRecyclerView.getAdapter();

                }
                adapter.removeAll();
                List<Barber> barberList = usm.getRegisteredBarbers();
                adapter = new BarberAdapter(barberList, new BarberClick(getActivity(), mRecyclerView), getContext());
                mRecyclerView.setAdapter(adapter);
            }
        });
        sr.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_red_dark);
            /* /Swipe down to refresh */

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
