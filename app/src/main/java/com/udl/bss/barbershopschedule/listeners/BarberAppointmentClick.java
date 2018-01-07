package com.udl.bss.barbershopschedule.listeners;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.udl.bss.barbershopschedule.BarberAppointmentDetailsActivity;
import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.adapters.AppointmentAdapter;
import com.udl.bss.barbershopschedule.adapters.BarberAppointmentsAdapter;

/**
 * Created by Julio on 07/01/2018.
 */

public class BarberAppointmentClick implements OnItemClickListener {

    private FragmentActivity activity;
    private RecyclerView recyclerView;

    public BarberAppointmentClick(FragmentActivity activity, RecyclerView recyclerView) {
        this.activity = activity;
        this.recyclerView = recyclerView;
    }

    @Override
    public void onItemClick(View view, int position){

        View service_cv = view.findViewById(R.id.service_name_cv);
        View client_cv = view.findViewById(R.id.client_name_cv);
        View promotion_cv = view.findViewById(R.id.promotion_name_cv);
        View date_cv = view.findViewById(R.id.date_cv);


        BarberAppointmentsAdapter adapter = (BarberAppointmentsAdapter) recyclerView.getAdapter();

        Intent intent = new Intent(activity, BarberAppointmentDetailsActivity.class);
        intent.putExtra("appointment", adapter.getItem(position));

        Pair<View, String> p1 = Pair.create(service_cv, activity.getString(R.string.transname_barberserv));
        Pair<View, String> p2 = Pair.create(client_cv, activity.getString(R.string.transname_barberclient));
        Pair<View, String> p3 = Pair.create(promotion_cv, activity.getString(R.string.transname_barberprom));
        Pair<View, String> p4 = Pair.create(date_cv, activity.getString(R.string.transname_barberdate));

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, p1, p2, p3, p4);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            activity.startActivity(intent, options.toBundle());
        } else {
            activity.startActivity(intent);
        }
    }

}
