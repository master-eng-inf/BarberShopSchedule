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
 * Created by Alex on 25/11/2017.
 */

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    private List<BarberService> mDataset;
    private OnItemClickListener listener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        //TextView name;
        TextView price;
        TextView service;
        ViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.card_view);
            price = itemView.findViewById(R.id.price_cv);
            service = itemView.findViewById(R.id.service_cv);
        }
    }

    public ServiceAdapter(List<BarberService> myDataset, OnItemClickListener listener) {
        mDataset = myDataset;
        this.listener = listener;
    }

    @Override
    public ServiceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.price_card_view, parent, false);
        return new ServiceAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ServiceAdapter.ViewHolder holder, int position) {
        holder.service.setText(mDataset.get(position).Get_Name());
        //holder.name.setText(mDataset.get(position).getBarberShopName());
        String price = Double.toString(mDataset.get(position).Get_Price()) + "€";
        holder.price.setText(price);

        ViewCompat.setTransitionName(holder.service, String.valueOf(position)+"_serv");
        //ViewCompat.setTransitionName(holder.name, String.valueOf(position)+"_name");
        ViewCompat.setTransitionName(holder.price, String.valueOf(position)+"_desc");

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
            BarberService service = iter.next();
            int position = mDataset.indexOf(service);
            iter.remove();
            notifyItemRemoved(position);
        }
    }

    public int add(BarberService service){
        mDataset.add(service);
        notifyItemInserted(mDataset.size()-1);
        return mDataset.size()-1;
    }
}
