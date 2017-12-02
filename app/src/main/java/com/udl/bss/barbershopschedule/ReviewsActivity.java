package com.udl.bss.barbershopschedule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.udl.bss.barbershopschedule.database.BLL;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.domain.Review;

public class ReviewsActivity extends AppCompatActivity {

    private Barber barber_shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        setTitle(R.string.barber_shop_rating);
        this.barber_shop = getIntent().getParcelableExtra("barber");

        BLL instance = new BLL(this);

        //TODO change id client
        Review review = instance.Get_ClientReviewForBarberShop(0, this.barber_shop.getId());

        if (review == null) {
            LinearLayout no_rated_layout_1 = findViewById(R.id.no_rated_layout_1);

            no_rated_layout_1.setVisibility(View.VISIBLE);
        } else {

        }
    }
}
