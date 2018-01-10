package com.udl.bss.barbershopschedule.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.adapters.BarberAdapter;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.listeners.BarberClick;
import com.udl.bss.barbershopschedule.listeners.FloatingButtonScrollListener;
import com.udl.bss.barbershopschedule.serverCommunication.APIController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class BarberListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private BarberAdapter adapter;
    private OnFragmentInteractionListener mListener;

    private SharedPreferences mPrefs;
    private AdView mAdView;

    public BarberListFragment() {
    }

    public static BarberListFragment newInstance() {
        return new BarberListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_barber_list, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && getActivity() != null) {
            Fade fade = new Fade();
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);

            getActivity().getWindow().setEnterTransition(fade);
            getActivity().getWindow().setExitTransition(fade);
        }


        mPrefs = getActivity().getSharedPreferences("USER", Activity.MODE_PRIVATE);

        if (getView() != null) {
            mRecyclerView = getView().findViewById(R.id.rv);
            mAdView = getView().findViewById(R.id.adView);
        }

        if (mAdView != null) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }

        if (mRecyclerView != null) {

            final FloatingActionMenu floatingActionMenu = getActivity().findViewById(R.id.fab_menu);

            mRecyclerView.addOnScrollListener(new FloatingButtonScrollListener(floatingActionMenu));

            mRecyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(llm);
            mRecyclerView.setAdapter(new BarberAdapter(new ArrayList<Barber>(), null, null));
            setBarbersToRecycleView();

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
                setBarbersToRecycleView();

            }
        });
        sr.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_red_dark);
            /* /Swipe down to refresh */

    }

    void setBarbersToRecycleView() {

        if (getActivity() != null && mPrefs != null){
            try {
                JSONObject json = new JSONObject(mPrefs.getString("user", ""));

                APIController.getInstance().getAllBarbers(json.getString("token"))
                        .addOnCompleteListener(new OnCompleteListener<List<Barber>>() {
                            @Override
                            public void onComplete(@NonNull Task<List<Barber>> task) {
                                adapter = new BarberAdapter(task.getResult(), new BarberClick(getActivity(), mRecyclerView), getContext());
                                mRecyclerView.setAdapter(adapter);
                            }
                        });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }






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
