package com.udl.bss.barbershopschedule.listeners;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.udl.bss.barbershopschedule.AppointmentDetailsActivity;
import com.udl.bss.barbershopschedule.PromotionsDetailsActivity;
import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.adapters.PromotionAdapter;


public class PromotionClick implements OnItemClickListener {

    private FragmentActivity activity;
    private RecyclerView recyclerView;

    public PromotionClick(FragmentActivity activity, RecyclerView recyclerView) {
        this.activity = activity;
        this.recyclerView = recyclerView;
    }

    @Override
    public void onItemClick(View view, int position){

        View name_cv = view.findViewById(R.id.name_cv);
        View description_cv = view.findViewById(R.id.description_cv);
        View service_cv = view.findViewById(R.id.service_cv);
        //View fbutton = activity.findViewById(R.id.fab);


        PromotionAdapter adapter = (PromotionAdapter) recyclerView.getAdapter();

        Intent intent = new Intent(activity, PromotionsDetailsActivity.class);
        intent.putExtra("promotion", adapter.getItem(position));

        //Pair<View, String> p1 = Pair.create(fbutton, "fab");
        Pair<View, String> p1 = Pair.create(description_cv, activity.getString(R.string.transname_barberdesc));
        Pair<View, String> p2 = Pair.create(name_cv, activity.getString(R.string.transname_barbername));
        Pair<View, String> p3 = Pair.create(service_cv, activity.getString(R.string.transname_barberaddress));

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, p1, p2, p3);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            activity.startActivity(intent, options.toBundle());
        } else {
            activity.startActivity(intent);
        }
    }
}
