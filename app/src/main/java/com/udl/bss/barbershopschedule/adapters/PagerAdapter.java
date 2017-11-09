package com.udl.bss.barbershopschedule.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.udl.bss.barbershopschedule.fragments.BarberDetailGeneralInformationFragment;
import com.udl.bss.barbershopschedule.fragments.BarberDetailPricesAndPromotionsFragment;
import com.udl.bss.barbershopschedule.fragments.BarberDetailScheduleFragment;
import com.udl.bss.barbershopschedule.domain.Barber;

/**
 * Created by Alex on 04/11/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    private Barber barber;

    public PagerAdapter(FragmentManager fm, int NumOfTabs, Barber barber) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.barber = barber;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                BarberDetailGeneralInformationFragment tab1 = BarberDetailGeneralInformationFragment.newInstance(this.barber);
                return tab1;
            case 1:
                BarberDetailScheduleFragment tab2 = BarberDetailScheduleFragment.newInstance(this.barber);
                return tab2;
            case 2:
                BarberDetailPricesAndPromotionsFragment tab3 = BarberDetailPricesAndPromotionsFragment.newInstance(this.barber);
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}