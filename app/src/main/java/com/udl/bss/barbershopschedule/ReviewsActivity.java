package com.udl.bss.barbershopschedule;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.udl.bss.barbershopschedule.adapters.ReviewAdapter;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.domain.Client;
import com.udl.bss.barbershopschedule.domain.Review;
import com.udl.bss.barbershopschedule.serverCommunication.APIController;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class ReviewsActivity extends AppCompatActivity {

    private Barber barber_shop;
    private RecyclerView mRecyclerView;
    private ReviewAdapter adapter;
    private Client client;
    private SharedPreferences mPrefs;
    private Review review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        setTitle(R.string.barber_shop_rating);

        final TextView rate_tv = findViewById(R.id.rate_this_barber_shop);
        final Button next_btn = findViewById(R.id.review_next_button);
        final Button cancel_btn = findViewById(R.id.review_cancel_button);
        final LinearLayout no_rated1 = findViewById(R.id.no_rated_layout_1);
        final LinearLayout no_rated2 = findViewById(R.id.no_rated_layout_2);
        final LinearLayout already_rated = findViewById(R.id.already_rated_layout);
        final TextView user_review_tv = findViewById(R.id.user_review);
        final RatingBar user_rating = findViewById(R.id.user_rating_bar);
        final TextView user_rating_date = findViewById(R.id.user_rating_date);

        this.barber_shop = getIntent().getParcelableExtra("barber");


        mPrefs = getSharedPreferences("USER", Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("user", "");
        client = gson.fromJson(json, Client.class);


        cancel_btn.setClickable(false);
        cancel_btn.setVisibility(View.GONE);
        no_rated1.setVisibility(View.VISIBLE);
        no_rated2.setVisibility(View.GONE);
        already_rated.setVisibility(View.GONE);

        if (client != null && barber_shop != null) {

            APIController.getInstance().getReviewByClientAndBarber(client.getToken(),
                    String.valueOf(barber_shop.getId()), String.valueOf(client.getId()))
                    .addOnCompleteListener(new OnCompleteListener<Review>() {
                        @Override
                        public void onComplete(@NonNull Task<Review> task) {
                            review = task.getResult();

                            String rate_this_barber_shop = getString(R.string.rate_barber_shop_now) + " " + barber_shop.getName();
                            rate_tv.setText(rate_this_barber_shop);

                            next_btn.setClickable(false);
                            next_btn.setVisibility(View.GONE);

                            if (review == null) {
                                cancel_btn.setClickable(false);
                                cancel_btn.setVisibility(View.GONE);
                                no_rated1.setVisibility(View.VISIBLE);
                                no_rated2.setVisibility(View.GONE);
                                already_rated.setVisibility(View.GONE);

                            } else {
                                no_rated1.setVisibility(View.GONE);
                                no_rated2.setVisibility(View.GONE);
                                already_rated.setVisibility(View.VISIBLE);

                                user_review_tv.setText(review.getDescription());
                                user_rating.setRating((float)review.getMark());
                                user_rating_date.setText((review.getDate()));
                            }

                        }
                    });
        }

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

                        APIController.getInstance().removeReview(
                                client.getToken(),
                                String.valueOf(barber_shop.getId()),
                                String.valueOf(client.getId()));

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

                insertOrUpdateReview(new Review(client.getId(), barber_shop.getId(), description, rating, date));

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


        mRecyclerView = findViewById(R.id.reviews_rv);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setFocusable(false);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);


        if (client != null && barber_shop != null) {
            APIController.getInstance().getReviewsByBarber(client.getToken() ,String.valueOf(barber_shop.getId()))
                    .addOnCompleteListener(new OnCompleteListener<List<Review>>() {
                        @Override
                        public void onComplete(@NonNull Task<List<Review>> task) {
                            List<Review> reviewList = task.getResult();
                            for(Review review: reviewList) {
                                if (review.getClientId() == client.getId()) reviewList.remove(review);
                            }
                            adapter = new ReviewAdapter(reviewList, client.getToken());
                            mRecyclerView.setAdapter(adapter);
                        }
                    });
        }


    }

    private void insertOrUpdateReview(Review r) {
        if (review == null) APIController.getInstance().createReview(client.getToken(), r);
        else APIController.getInstance().updateReview(client.getToken(), r);
    }
}
