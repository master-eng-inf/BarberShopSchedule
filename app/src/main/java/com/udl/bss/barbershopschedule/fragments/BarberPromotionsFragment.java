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

    private RecyclerView promotionsRecyclerView;
    private PromotionAdapter adapter;

    private OnFragmentInteractionListener mListener;

    final static String urlBarberPromotions = "https://raw.githubusercontent.com/master-eng-inf/BarberShopFakeData/master/Data/barber_shop_list.json";
    String jsonStr;
    private String TAG = HomeActivity.class.getSimpleName();

    public BarberPromotionsFragment() {
        // Required empty public constructor
    }

    public static BarberPromotionsFragment newInstance() {
        return new BarberPromotionsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

            new GetBarberPromotions().execute();

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
                    new GetBarberPromotions().execute();
                }
            });
            sr.setColorSchemeResources(android.R.color.holo_blue_dark,
                    android.R.color.holo_green_dark,
                    android.R.color.holo_orange_dark,
                    android.R.color.holo_red_dark);
            /* /Swipe down to refresh */

        }
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

    private class GetBarberPromotions extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPostExecute (Void aVoid){
            super.onPostExecute(aVoid);

            List<Promotion> promotionList = new ArrayList<>();

            try {
                JSONObject jsonObj =  new JSONObject(jsonStr);

                JSONArray barber_shops = jsonObj.getJSONArray("barber_shops");

                int id;
                int barber_id;
                String name;
                String description;

                JSONObject root = barber_shops.getJSONObject(0);
                barber_id = root.getInt("id");

                JSONArray promotions = root.getJSONArray("promotions");

                for (int i = 0; i < promotions.length(); i++) {
                    JSONObject promotion = promotions.getJSONObject(i);

                    id = promotion.getInt("id") ;
                    name = ("Promotion "+i);
                    description = promotion.getString("description");

                    //TODO service id
                    Promotion barberPromotion = new Promotion(id, barber_id, 0, name ,description);
                    promotionList.add(barberPromotion);
                }

                adapter = new PromotionAdapter(promotionList, new PromotionClick(getActivity(), promotionsRecyclerView), getContext());
                promotionsRecyclerView.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        protected Void doInBackground(Void... voids) {

            HttpURLConnection conn=null;
            InputStream inputStream;
            BufferedReader reader=null;

            try {

                URL url = new URL(urlBarberPromotions);

                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                inputStream = conn.getInputStream();

                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                jsonStr = buffer.toString();

                //This system show in the log if you recieve the json string
                //System.out.print(jsonStr);

            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException: " + e.getMessage());
            }catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (conn != null) {
                    conn.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(TAG, "Error closing stream", e);
                    }
                }
            }

            return null;

        }
    }
}
