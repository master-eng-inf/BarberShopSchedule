package com.udl.bss.barbershopschedule.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.udl.bss.barbershopschedule.adapters.BarberAdapter;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.listeners.BarberClick;

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


public class BarberListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private BarberAdapter adapter;

    private OnFragmentInteractionListener mListener;

    final static String urlBarbers = "https://raw.githubusercontent.com/master-eng-inf/BarberShopFakeData/master/Data/barbers.json";
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

        if (getView() != null) {
            mRecyclerView = getView().findViewById(R.id.rv);
        }

        if (mRecyclerView != null) {
            mRecyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(llm);

            new GetBarbers().execute();

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
                new GetBarbers().execute();
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

    private class GetBarbers extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            List<Barber> barberList = new ArrayList<>();
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

            try {
                JSONObject root = new JSONObject(jsonStr);
                JSONArray jsonarr = (JSONArray) root.get("barbers");

                for (int i = 0; i < jsonarr.length(); i++) {
                    JSONObject json = jsonarr.getJSONObject(i);

                    Barber barber = new Barber(json.getInt("id"),
                            json.getString("name"),
                            json.getString("description"),
                            json.getString("city"),
                            json.getString("address"), bitmap);
                    barberList.add(barber);
                }

                adapter = new BarberAdapter(barberList, new BarberClick(getActivity(), mRecyclerView));
                mRecyclerView.setAdapter(adapter);

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

                URL url = new URL(urlBarbers);

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

                // Print json string
                // Log.e(TAG, "Response from url: " + jsonStr);

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
