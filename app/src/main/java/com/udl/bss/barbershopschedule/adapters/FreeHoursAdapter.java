package com.udl.bss.barbershopschedule.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.listeners.OnItemClickListener;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Alex on 10/11/2017.
 */

public class FreeHoursAdapter extends RecyclerView.Adapter<FreeHoursAdapter.ViewHolder> {

    private List<Date> mDataset;
    private OnItemClickListener listener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView date;

        ViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.card_view);
            date = itemView.findViewById(R.id.date_cv);
        }
    }

    public FreeHoursAdapter(List<Date> mDataset, OnItemClickListener listener)
    {
        this.mDataset = mDataset;
        this.listener = listener;
    }

    @Override
    public FreeHoursAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.free_hours_card_view, parent, false);
        return new FreeHoursAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final FreeHoursAdapter.ViewHolder holder, int position) {
        holder.date.setText(mDataset.get(position).getHours() + ":" + mDataset.get(position).getMinutes());

        /*
        ViewCompat.setTransitionName(holder.service, String.valueOf(position)+"_serv");
        //ViewCompat.setTransitionName(holder.name, String.valueOf(position)+"_name");
        ViewCompat.setTransitionName(holder.price, String.valueOf(position)+"_desc");
        */

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public Date getItem (int position) {
        return mDataset.get(position);
    }

    public void removeAll(){
        Iterator<Date> iter = mDataset.iterator();
        while(iter.hasNext()){
            Date date = iter.next();
            int position = mDataset.indexOf(date);
            iter.remove();
            notifyItemRemoved(position);
        }
    }

    public int add(Date date){
        mDataset.add(date);
        notifyItemInserted(mDataset.size()-1);
        return mDataset.size()-1;
    }
}
