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
import com.udl.bss.barbershopschedule.adapters.ServiceAdapter;
import com.udl.bss.barbershopschedule.domain.BarberService;
import com.udl.bss.barbershopschedule.listeners.ServiceClick;

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


public class BarberServicesFragment extends Fragment {

    private RecyclerView servicesRecyclerView;
    private ServiceAdapter adapter;

    private OnFragmentInteractionListener mListener;

    final static String urlBarberServices = "https://raw.githubusercontent.com/master-eng-inf/BarberShopFakeData/master/Data/barber_shop_list.json";
    String jsonStr;
    private String TAG = HomeActivity.class.getSimpleName();

    public BarberServicesFragment() {
        // Required empty public constructor
    }

    public static BarberServicesFragment newInstance() {
        return new BarberServicesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_barber_services, container, false);
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
            servicesRecyclerView = getView().findViewById(R.id.rv);
        }

        if (servicesRecyclerView != null) {

            servicesRecyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            servicesRecyclerView.setLayoutManager(llm);

            new GetBarberServices().execute();

            /* Swipe down to refresh */
            final SwipeRefreshLayout sr = getView().findViewById(R.id.swiperefresh);
            sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    sr.setRefreshing(false);

                    if (adapter == null) {
                        adapter = (ServiceAdapter) servicesRecyclerView.getAdapter();

                    }
                    adapter.removeAll();
                    new GetBarberServices().execute();
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

    private class GetBarberServices extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            List<BarberService> servicesList = new ArrayList<>();

            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                JSONArray barber_shops = jsonObj.getJSONArray("barber_shops");

                int id;
                int barber_id;
                String name;
                Double price;
                Double duration;


                JSONObject root = barber_shops.getJSONObject(0);
                barber_id = root.getInt("id");

                JSONArray services = root.getJSONArray("services");

                for (int i = 0; i < services.length(); i++) {
                    JSONObject service = services.getJSONObject(i);

                    id = service.getInt("id");
                    name = service.getString("name");
                    price = (double) service.getDouble("price");
                    duration = Double.parseDouble(service.getString("duration").replaceAll(".*:", ""));

                    BarberService barberService = new BarberService(id, barber_id, name, price, duration);
                    servicesList.add(barberService);
                }

                adapter = new ServiceAdapter(servicesList, new ServiceClick(getActivity(), servicesRecyclerView), getContext());
                servicesRecyclerView.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {

            HttpURLConnection conn = null;
            InputStream inputStream;
            BufferedReader reader = null;

            try {

                URL url = new URL(urlBarberServices);

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
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
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
