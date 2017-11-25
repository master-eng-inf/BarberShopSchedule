package com.udl.bss.barbershopschedule.adapters;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.domain.BarberService;
import com.udl.bss.barbershopschedule.listeners.OnItemClickListener;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Julio on 23/11/2017.
 */

public class BarberServiceAdapter extends RecyclerView.Adapter<BarberServiceAdapter.ViewHolder> {

    private List<BarberService> mDataset;
    private OnItemClickListener listener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView service_name;
        TextView service_price;
        TextView service_duration;
        ViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.card_view);
            service_name = itemView.findViewById(R.id.service_name_cv);
            service_price = itemView.findViewById(R.id.service_price_cv);
            service_duration = itemView.findViewById(R.id.service_duration_cv);
        }
    }

    public BarberServiceAdapter(List<BarberService> myDataset, OnItemClickListener listener) {
        mDataset = myDataset;
        this.listener = listener;
    }

    @Override
    public BarberServiceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.barber_service_card_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.service_name.setText(mDataset.get(position).GetName());
        holder.service_price.setText(String.valueOf(mDataset.get(position).GetPrice()));
        String duration = String.valueOf(mDataset.get(position).GetDuration()) + " minutes";
        holder.service_duration.setText(duration);

        ViewCompat.setTransitionName(holder.service_name, String.valueOf(position)+"service_name");
        ViewCompat.setTransitionName(holder.service_price, String.valueOf(position)+"service_price");
        ViewCompat.setTransitionName(holder.service_duration, String.valueOf(position)+"service_duration");

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

    public BarberService getItem (int position) {
        return mDataset.get(position);
    }

    public void removeAll(){
        Iterator<BarberService> iter = mDataset.iterator();
        while(iter.hasNext()){
            BarberService barberService = iter.next();
            int position = mDataset.indexOf(barberService);
            iter.remove();
            notifyItemRemoved(position);
        }
    }

    public int add(BarberService barberService){
        mDataset.add(barberService);
        notifyItemInserted(mDataset.size()-1);
        return mDataset.size()-1;
    }

}
