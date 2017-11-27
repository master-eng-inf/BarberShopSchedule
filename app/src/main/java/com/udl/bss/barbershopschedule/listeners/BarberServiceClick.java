package com.udl.bss.barbershopschedule.listeners;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.udl.bss.barbershopschedule.PriceDetailActivity;
import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.adapters.PriceAdapter;
import com.udl.bss.barbershopschedule.adapters.ServiceAdapter;


public class BarberServiceClick implements OnItemClickListener {

    private FragmentActivity activity;
    private RecyclerView recyclerView;

    public BarberServiceClick(FragmentActivity activity, RecyclerView recyclerView) {
        this.activity = activity;
        this.recyclerView = recyclerView;
    }

    @Override
    public void onItemClick(View view, int position){

        //View name_cv = view.findViewById(R.id.name_cv);
        View price_cv = view.findViewById(R.id.price_cv);
        View service_cv = view.findViewById(R.id.service_cv);
        //View fbutton = activity.findViewById(R.id.fab);

        ServiceAdapter adapter = (ServiceAdapter) recyclerView.getAdapter();

        Intent intent = new Intent(activity, PriceDetailActivity.class);
        intent.putExtra("service", adapter.getItem(position));

        //Pair<View, String> p1 = Pair.create(fbutton, "fab");
        Pair<View, String> p1 = Pair.create(price_cv, activity.getString(R.string.transname_barberdesc));
        //Pair<View, String> p2 = Pair.create(name_cv, activity.getString(R.string.transname_barbername));
        Pair<View, String> p2 = Pair.create(service_cv, activity.getString(R.string.transname_barberaddress));

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, p1, p2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            activity.startActivity(intent, options.toBundle());
        } else {
            activity.startActivity(intent);
        }
    }
}
