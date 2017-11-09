package com.udl.bss.barbershopschedule;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.transition.Fade;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.udl.bss.barbershopschedule.fragments.BarberDetailFragment;
import com.udl.bss.barbershopschedule.fragments.BarberHomeFragment;
import com.udl.bss.barbershopschedule.fragments.BarberListFragment;
import com.udl.bss.barbershopschedule.fragments.BarberPromotionsFragment;
import com.udl.bss.barbershopschedule.fragments.BarberScheduleFragment;
import com.udl.bss.barbershopschedule.fragments.BarberServicesFragment;
import com.udl.bss.barbershopschedule.fragments.HomeFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        BarberListFragment.OnFragmentInteractionListener,
        BarberDetailFragment.OnFragmentInteractionListener,
        HomeFragment.OnFragmentInteractionListener,
        BarberHomeFragment.OnFragmentInteractionListener,
        BarberScheduleFragment.OnFragmentInteractionListener,
        BarberServicesFragment.OnFragmentInteractionListener,
        BarberPromotionsFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);

            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(fade);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        String user = getIntent().getStringExtra("user");

        if (user.equals("b") || user.equals("barber")) {
            navigationView.inflateMenu(R.menu.activity_barber_home_drawer);
            BarberHomeFragment fragment = BarberHomeFragment.newInstance();
            startFragment(fragment);
        } else {
            navigationView.inflateMenu(R.menu.activity_home_drawer);
            HomeFragment fragment = HomeFragment.newInstance();
            startFragment(fragment);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.home) {
            HomeFragment hf = HomeFragment.newInstance();
            startFragment(hf);
        } else if (id == R.id.show_barbers) {
            BarberListFragment blf = BarberListFragment.newInstance();
            startFragment(blf);
        } else if (id == R.id.show_schedule) {
            BarberScheduleFragment bsf = BarberScheduleFragment.newInstance();
            startFragment(bsf);
        } else if (id == R.id.show_services) {
            BarberServicesFragment bsf = BarberServicesFragment.newInstance();
            startFragment(bsf);
        } else if (id == R.id.show_promotions) {
            BarberPromotionsFragment bpf = BarberPromotionsFragment.newInstance();
            startFragment(bpf);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void startFragment(Fragment fragment) {
        //Toast.makeText(this,fragment.toString(),Toast.LENGTH_SHORT).show();
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_home, fragment)
                    .commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
