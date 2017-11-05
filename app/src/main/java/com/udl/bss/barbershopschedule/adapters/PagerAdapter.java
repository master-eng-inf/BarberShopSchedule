package com.udl.bss.barbershopschedule.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.udl.bss.barbershopschedule.BarberDetail_GeneralInformationFragment;
import com.udl.bss.barbershopschedule.BarberDetail_PricesAndPromotionsFragment;
import com.udl.bss.barbershopschedule.BarberDetail_ScheduleFragment;

/**
 * Created by Alex on 04/11/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                BarberDetail_GeneralInformationFragment tab1 = new BarberDetail_GeneralInformationFragment();
                return tab1;
            case 1:
                BarberDetail_ScheduleFragment tab2 = new BarberDetail_ScheduleFragment();
                return tab2;
            case 2:
                BarberDetail_PricesAndPromotionsFragment tab3 = new BarberDetail_PricesAndPromotionsFragment();
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