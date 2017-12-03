package com.udl.bss.barbershopschedule.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.database.BLL;
import com.udl.bss.barbershopschedule.domain.Client;
import com.udl.bss.barbershopschedule.domain.Review;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Alex on 03/12/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{

    private List<Review> mDataset;
    private Context context;

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

    public ReviewAdapter(List<Review> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
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
        BLL instace = new BLL(this.context);
        Client client = instace.Get_Client(mDataset.get(position).GetClientId());

        holder.username.setText(client.getName());
        holder.date.setText(mDataset.get(position).GetDate());
        holder.user_review.setText(mDataset.get(position).GetDescription());
        holder.rating_bar.setRating((float)mDataset.get(position).GetMark());
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
