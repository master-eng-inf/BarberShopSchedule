package com.udl.bss.barbershopschedule.listeners;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.view.View;

import com.udl.bss.barbershopschedule.BarberDetailFragment;
import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.adapters.MyAdapter;
import com.udl.bss.barbershopschedule.transitions.DetailsTransition;


public class ItemClick implements OnItemClickListener {

    private FragmentActivity activity;
    private RecyclerView recyclerView;

    public ItemClick (FragmentActivity activity, RecyclerView recyclerView) {
        this.activity = activity;
        this.recyclerView = recyclerView;
    }

    @Override
    public void onItemClick(View view, int position){
        //showToast("Clicked element: "+Integer.toString(position));

        View image_cv = view.findViewById(R.id.image_cv);
        View description_cv = view.findViewById(R.id.description_cv);
        View name_cv = view.findViewById(R.id.name_cv);
        View address_cv = view.findViewById(R.id.address_cv);


        MyAdapter adapter = (MyAdapter) recyclerView.getAdapter();

        BarberDetailFragment fragment =
                BarberDetailFragment.newInstance(adapter.getItem(position));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            fragment.setSharedElementEnterTransition(new DetailsTransition());
            fragment.setEnterTransition(new Fade());
            fragment.setSharedElementReturnTransition(new DetailsTransition());
        }
        startFragmentWithSharedElement(fragment,
                image_cv, activity.getString(R.string.transname_barberimg),
                address_cv, activity.getString(R.string.transname_barberaddress),
                description_cv, activity.getString(R.string.transname_barberdesc),
                name_cv, activity.getString(R.string.transname_barbername));
    }

    private void startFragmentWithSharedElement(Fragment fragment,
                                                View sharedElement1, String transitionName1,
                                                View sharedElement2, String transitionName2,
                                                View sharedElement3, String transitionName3,
                                                View sharedElement4, String transitionName4) {
        if (fragment != null){
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .addSharedElement(sharedElement1, transitionName1)
                    .addSharedElement(sharedElement2, transitionName2)
                    .addSharedElement(sharedElement3, transitionName3)
                    .addSharedElement(sharedElement4, transitionName4)
                    .replace(R.id.content_home, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
