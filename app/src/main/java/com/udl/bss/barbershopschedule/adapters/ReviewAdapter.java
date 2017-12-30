package com.udl.bss.barbershopschedule.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.domain.Client;
import com.udl.bss.barbershopschedule.domain.Review;
import com.udl.bss.barbershopschedule.serverCommunication.APIController;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Alex on 03/12/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{

    private List<Review> mDataset;
    private String token;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        RatingBar rating_bar;
        TextView user_review;
        TextView date;
        ViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.review_username);
            user_review = itemView.findViewById(R.id.user_review);
            date = itemView.findViewById(R.id.user_rating_date);
            rating_bar = itemView.findViewById(R.id.user_rating_bar);
        }
    }

    public ReviewAdapter(List<Review> myDataset, String token) {
        mDataset = myDataset;
        this.token = token;
    }

    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_review_card_view, parent, false);
        return new ReviewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ReviewAdapter.ViewHolder holder, int position) {
        final String[] client_name = {"Could not get username"};

        APIController.getInstance().getClientById(token, String.valueOf(mDataset.get(position).getClientId()))
                .addOnCompleteListener(new OnCompleteListener<Client>() {
            @Override
            public void onComplete(@NonNull Task<Client> task) {
                Client client = task.getResult();
                client_name[0] = client.getName();
                holder.username.setText(client_name[0]);
            }
        });


        holder.date.setText(mDataset.get(position).getDate());
        holder.user_review.setText(mDataset.get(position).getDescription());
        holder.rating_bar.setRating((float)mDataset.get(position).getMark());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public Review getItem (int position) {
        return mDataset.get(position);
    }

    public void removeAll(){
        Iterator<Review> iter = mDataset.iterator();
        while(iter.hasNext()){
            Review review = iter.next();
            int position = mDataset.indexOf(review);
            iter.remove();
            notifyItemRemoved(position);
        }
    }

    public int add(Review review){
        mDataset.add(review);
        notifyItemInserted(mDataset.size()-1);
        return mDataset.size()-1;
    }
}
