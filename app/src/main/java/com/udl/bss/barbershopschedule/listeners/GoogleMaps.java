package com.udl.bss.barbershopschedule.listeners;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.udl.bss.barbershopschedule.R;

/**
 * Created by gerard on 11/12/17.
 */

public class GoogleMaps {

    private Activity activity;
    private ViewHolder holder;

    public GoogleMaps (Activity activity) {
        this.activity = activity;
        this.holder = new ViewHolder();

        holder.mapView = activity.findViewById(R.id.lite_listrow_map);
        holder.title = activity.findViewById(R.id.textView_register_place);

        if (holder.mapView != null) {
            holder.mapView.onCreate(null);
            holder.mapView.getMapAsync(holder);
        }

    }

    private static class NamedLocation {

        public final String name;
        final LatLng location;

        NamedLocation(String name, LatLng location) {
            this.name = name;
            this.location = location;
        }
    }


    class ViewHolder implements OnMapReadyCallback {

        MapView mapView;
        TextView title;
        GoogleMap map;

        @Override
        public void onMapReady(GoogleMap googleMap) {
            MapsInitializer.initialize(activity.getApplicationContext());
            map = googleMap;
            NamedLocation data = (NamedLocation) mapView.getTag();
            if (data != null) {
                setMapLocation(map, data);
            }
        }

    }

    public void setMap(String name, LatLng location) {
        if (holder != null) {
            holder.mapView.setVisibility(View.VISIBLE);

            NamedLocation item = new NamedLocation(name, location);
            holder.mapView.setTag(item);

            if (holder.map != null) {
                setMapLocation(holder.map, item);
            }
            holder.title.setText(item.name);
        }

    }


    private static void setMapLocation(GoogleMap map, NamedLocation data) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(data.location, 13f));
        map.addMarker(new MarkerOptions().position(data.location));
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

}
