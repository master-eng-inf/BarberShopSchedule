package com.udl.bss.barbershopschedule;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.udl.bss.barbershopschedule.adapters.ReviewAdapter;
import com.udl.bss.barbershopschedule.database.BLL;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.domain.Review;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class ReviewsActivity extends AppCompatActivity {

    private Barber barber_shop;
    private RecyclerView mRecyclerView;
    private ReviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        setTitle(R.string.barber_shop_rating);

        this.barber_shop = getIntent().getParcelableExtra("barber");
        final BLL instance = new BLL(this);

        //TODO change id client
        final Review review = instance.Get_ClientReviewForBarberShop(0, this.barber_shop.getId());

        (findViewById(R.id.edit_user_review)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.no_rated_layout_1).setVisibility(View.VISIBLE);
                findViewById(R.id.no_rated_layout_2).setVisibility(View.GONE);
                findViewById(R.id.already_rated_layout).setVisibility(View.GONE);
            }
        });

        (findViewById(R.id.delete_user_review)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog alert = new AlertDialog.Builder(ReviewsActivity.this).create();
                alert.setTitle(getString(R.string.delete_review_dialog_title));
                alert.setMessage(getString(R.string.delete_review_dialog));
                alert.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.accept_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        instance.Delete_Review(review);

                        finish();
                        startActivity(getIntent());
                    }
                });

                alert.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
            }
        });

        (findViewById(R.id.review_upload_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = ((TextView) findViewById(R.id.user_new_review)).getText().toString();
                double rating = (double) ((RatingBar) findViewById(R.id.user_new_review_mark)).getRating();

                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

                String date = String.valueOf(calendar.get(Calendar.YEAR)) + "-" +
                        String.valueOf(calendar.get(Calendar.MONTH) + 1) + "-" + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

                //TODO change id client
                instance.Insert_or_Update_Review(new Review(0, barber_shop.getId(), description, rating, date));

                finish();
                startActivity(getIntent());
            }
        });

        (findViewById(R.id.review_next_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.no_rated_layout_1).setVisibility(View.GONE);
                findViewById(R.id.no_rated_layout_2).setVisibility(View.VISIBLE);
                findViewById(R.id.already_rated_layout).setVisibility(View.GONE);
            }
        });

        (findViewById(R.id.review_cancel_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.no_rated_layout_1).setVisibility(View.GONE);
                findViewById(R.id.no_rated_layout_2).setVisibility(View.GONE);
                findViewById(R.id.already_rated_layout).setVisibility(View.VISIBLE);
            }
        });

        (findViewById(R.id.review_back_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.no_rated_layout_1).setVisibility(View.VISIBLE);
                findViewById(R.id.no_rated_layout_2).setVisibility(View.GONE);
                findViewById(R.id.already_rated_layout).setVisibility(View.GONE);
            }
        });

        ((RatingBar) findViewById(R.id.user_new_review_mark)).setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                findViewById(R.id.review_next_button).setClickable(true);
                findViewById(R.id.review_next_button).setVisibility(View.VISIBLE);
                TextView rating_explanation = findViewById(R.id.rating_explanation);

                if (rating < (float) 1) {
                    ratingBar.setRating((float)1);
                } if (rating == (float) 1) {
                    rating_explanation.setText(getString(R.string.rating_1));
                } else if (rating == (float) 2) {
                    rating_explanation.setText(getString(R.string.rating_2));
                } else if (rating == (float) 3) {
                    rating_explanation.setText(getString(R.string.rating_3));
                } else if (rating == (float) 4) {
                    rating_explanation.setText(getString(R.string.rating_4));
                } else if (rating == (float) 5) {
                    rating_explanation.setText(getString(R.string.rating_5));
                }
            }
        });

        String rate_this_barber_shop = getString(R.string.rate_barber_shop_now) + " " + this.barber_shop.getName();
        ((TextView) findViewById(R.id.rate_this_barber_shop)).setText(rate_this_barber_shop);

        findViewById(R.id.review_next_button).setClickable(false);
        findViewById(R.id.review_next_button).setVisibility(View.GONE);

        if (review == null) {
            findViewById(R.id.review_cancel_button).setClickable(false);
            findViewById(R.id.review_cancel_button).setVisibility(View.GONE);
            findViewById(R.id.no_rated_layout_1).setVisibility(View.VISIBLE);
            findViewById(R.id.no_rated_layout_2).setVisibility(View.GONE);
            findViewById(R.id.already_rated_layout).setVisibility(View.GONE);

        } else {
            findViewById(R.id.no_rated_layout_1).setVisibility(View.GONE);
            findViewById(R.id.no_rated_layout_2).setVisibility(View.GONE);
            findViewById(R.id.already_rated_layout).setVisibility(View.VISIBLE);

            ((TextView) findViewById(R.id.user_review)).setText(review.GetDescription());
            ((RatingBar) findViewById(R.id.user_rating_bar)).setRating((float)review.GetMark());
            ((TextView) findViewById(R.id.user_rating_date)).setText((review.GetDate()));
        }

        mRecyclerView = findViewById(R.id.reviews_rv);
        //mRecyclerView.setNestedScrollingEnabled(true);
        //mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);

        adapter = new ReviewAdapter(instance.Get_BarberShopReviews(barber_shop.getId()), this);
        mRecyclerView.setAdapter(adapter);
    }
}
