package com.udl.bss.barbershopschedule.adapters;

import android.content.Context;
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

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    private List<BarberService> mDataset;
    private OnItemClickListener listener;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView name;
        TextView price;
        TextView duration;
        ViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.card_view);
            name = itemView.findViewById(R.id.name_cv);
            price = itemView.findViewById(R.id.price_cv);
            duration = itemView.findViewById(R.id.duration_cv);
        }
    }

    public ServiceAdapter(List<BarberService> myDataset, OnItemClickListener listener, Context context) {
        mDataset = myDataset;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public ServiceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.barber_service_card_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.name.setText(mDataset.get(position).getName());
        String price = String.valueOf(mDataset.get(position).getPrice()) + " " + context.getString(R.string.service_price_currency);
        holder.price.setText(price);
        String duration = String.valueOf((int)mDataset.get(position).getDuration()) + " " + context.getString(R.string.service_duration);
        holder.duration.setText(duration);

        ViewCompat.setTransitionName(holder.name, String.valueOf(position)+"name");
        ViewCompat.setTransitionName(holder.price, String.valueOf(position)+"price");
        ViewCompat.setTransitionName(holder.duration, String.valueOf(position)+"duration");

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
