package com.udl.bss.barbershopschedule.listeners;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.view.View;

import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.adapters.ServiceAdapter;
import com.udl.bss.barbershopschedule.fragments.BarberServiceDetailFragment;
import com.udl.bss.barbershopschedule.transitions.DetailsTransition;


public class ServiceClick implements OnItemClickListener {

    private FragmentActivity activity;
    private RecyclerView recyclerView;

    public ServiceClick(FragmentActivity activity, RecyclerView recyclerView) {
        this.activity = activity;
        this.recyclerView = recyclerView;
    }

    @Override
    public void onItemClick(View view, int position){

        View name_cv = view.findViewById(R.id.name_cv);
        View price_cv = view.findViewById(R.id.price_cv);
        View duration_cv = view.findViewById(R.id.duration_cv);


        ServiceAdapter adapter = (ServiceAdapter) recyclerView.getAdapter();

        BarberServiceDetailFragment fragment =
                BarberServiceDetailFragment.newInstance(adapter.getItem(position));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            fragment.setSharedElementEnterTransition(new DetailsTransition());
            fragment.setEnterTransition(new Fade());
            fragment.setSharedElementReturnTransition(new DetailsTransition());
        }
        startFragmentWithSharedElement(fragment,
                name_cv, activity.getString(R.string.transname_servicename),
                price_cv, activity.getString(R.string.transname_serviceprice),
                duration_cv, activity.getString(R.string.transname_serviceduration));
    }

    private void startFragmentWithSharedElement(Fragment fragment,
                                                View sharedElement1, String transitionName1,
                                                View sharedElement2, String transitionName2,
                                                View sharedElement3, String transitionName3) {
        if (fragment != null){
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .addSharedElement(sharedElement1, transitionName1)
                    .replace(R.id.content_home, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
