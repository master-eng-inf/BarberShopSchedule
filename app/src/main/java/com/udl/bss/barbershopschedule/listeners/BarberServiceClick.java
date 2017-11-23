package com.udl.bss.barbershopschedule.listeners;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.view.View;

import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.adapters.BarberServiceAdapter;
import com.udl.bss.barbershopschedule.fragments.BarberDetailFragment;
import com.udl.bss.barbershopschedule.transitions.DetailsTransition;


public class BarberServiceClick implements OnItemClickListener {

    private FragmentActivity activity;
    private RecyclerView recyclerView;

    public BarberServiceClick(FragmentActivity activity, RecyclerView recyclerView) {
        this.activity = activity;
        this.recyclerView = recyclerView;
    }

    @Override
    public void onItemClick(View view, int position){
        //showToast("Clicked element: "+Integer.toString(position));


        View service_name_cv = view.findViewById(R.id.service_name_cv);
        View service_price_cv = view.findViewById(R.id.service_price_cv);
        View service_duration_cv = view.findViewById(R.id.service_duration_cv);


        BarberServiceAdapter adapter = (BarberServiceAdapter) recyclerView.getAdapter();

        BarberServiceDetailFragment fragment =
                BarberServiceDetailFragment.newInstance(adapter.getItem(position));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            fragment.setSharedElementEnterTransition(new DetailsTransition());
            fragment.setEnterTransition(new Fade());
            fragment.setSharedElementReturnTransition(new DetailsTransition());
        }
        startFragmentWithSharedElement(fragment,
                service_name_cv, activity.getString(R.string.transname_servicename),
                service_price_cv, activity.getString(R.string.transname_serviceprice),
                service_duration_cv, activity.getString(R.string.transname_serviceduration));
    }

    private void startFragmentWithSharedElement(Fragment fragment,
                                                View sharedElement1, String transitionName1,
                                                View sharedElement2, String transitionName2,
                                                View sharedElement3, String transitionName3) {
        if (fragment != null){
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .addSharedElement(sharedElement1, transitionName1)
                    //.addSharedElement(sharedElement2, transitionName2)
                    //.addSharedElement(sharedElement3, transitionName3)
                    //.addSharedElement(sharedElement4, transitionName4)
                    .replace(R.id.content_home, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
