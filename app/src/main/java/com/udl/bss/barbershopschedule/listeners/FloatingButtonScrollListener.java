package com.udl.bss.barbershopschedule.listeners;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.clans.fab.FloatingActionMenu;


public class FloatingButtonScrollListener extends RecyclerView.OnScrollListener {

    private FloatingActionMenu fab_menu;

    public FloatingButtonScrollListener(FloatingActionMenu fab_menu){
        this.fab_menu = fab_menu;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
        super.onScrollStateChanged(recyclerView, scrollState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (dy > 0 && fab_menu.getVisibility() == View.VISIBLE) {
            fab_menu.hideMenu(true);
        } else if (dy < 0 && fab_menu.getVisibility() != View.VISIBLE) {
            fab_menu.showMenu(true);
        }
    }



}
