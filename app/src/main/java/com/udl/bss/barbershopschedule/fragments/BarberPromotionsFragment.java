package com.udl.bss.barbershopschedule.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udl.bss.barbershopschedule.HomeActivity;
import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.adapters.PromotionAdapter;
import com.udl.bss.barbershopschedule.database.BLL;
import com.udl.bss.barbershopschedule.domain.Promotion;
import com.udl.bss.barbershopschedule.listeners.PromotionClick;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class BarberPromotionsFragment extends Fragment {

    private static final String BARBER_ID = "barber_id";
    private int barber_id;
    private BLL instance;

    private RecyclerView promotionsRecyclerView;
    private PromotionAdapter adapter;

    private OnFragmentInteractionListener mListener;

    public BarberPromotionsFragment() {
        // Required empty public constructor
    }

    public static BarberPromotionsFragment newInstance(int barber_id) {
        BarberPromotionsFragment fragment = new BarberPromotionsFragment();
        Bundle args = new Bundle();
        args.putInt(BARBER_ID, barber_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.barber_id = getArguments().getInt(BARBER_ID);
        }
        this.instance = new BLL(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_barber_promotions, container, false);
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

        if (getView() != null) {
            promotionsRecyclerView = getView().findViewById(R.id.rv);
        }

        if (promotionsRecyclerView != null) {

            promotionsRecyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            promotionsRecyclerView.setLayoutManager(llm);

            setPromotions();

            /* Swipe down to refresh */
            final SwipeRefreshLayout sr = getView().findViewById(R.id.swiperefresh);
            sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    sr.setRefreshing(false);

                    if (adapter == null) {
                        adapter = (PromotionAdapter) promotionsRecyclerView.getAdapter();

                    }
                    adapter.removeAll();
                    setPromotions();
                }
            });
            sr.setColorSchemeResources(android.R.color.holo_blue_dark,
                    android.R.color.holo_green_dark,
                    android.R.color.holo_orange_dark,
                    android.R.color.holo_red_dark);
            /* /Swipe down to refresh */

        }
    }

    private void setPromotions() {
        List<Promotion> promotionsList;

        promotionsList = this.instance.Get_BarberShopPromotions(this.barber_id);

        PromotionAdapter adapter = new PromotionAdapter(promotionsList, new PromotionClick(getActivity(), promotionsRecyclerView), getContext());
        promotionsRecyclerView.setAdapter(adapter);
    }

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
