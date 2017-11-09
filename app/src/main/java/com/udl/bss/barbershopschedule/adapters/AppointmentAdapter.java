package com.udl.bss.barbershopschedule.adapters;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.domain.Appointment;
import com.udl.bss.barbershopschedule.listeners.OnItemClickListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;


public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {

    private List<Appointment> mDataset;
    private OnItemClickListener listener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView name;
        TextView date;
        TextView service;
        ViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.card_view);
            name = itemView.findViewById(R.id.name_cv);
            date = itemView.findViewById(R.id.date_cv);
            service = itemView.findViewById(R.id.service_cv);
        }
    }

    public AppointmentAdapter(List<Appointment> myDataset, OnItemClickListener listener) {
        mDataset = myDataset;
        this.listener = listener;
    }

    @Override
    public AppointmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appointments_card_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.service.setText(mDataset.get(position).getService());
        holder.name.setText(mDataset.get(position).getName());
        Date date_obj = mDataset.get(position).getDate();
        String date = new SimpleDateFormat("HH:mm dd-MM-yyyy", new Locale("es", "ES")).format(date_obj);
        holder.date.setText(date);

        ViewCompat.setTransitionName(holder.service, String.valueOf(position)+"_serv");
        ViewCompat.setTransitionName(holder.name, String.valueOf(position)+"_name");
        ViewCompat.setTransitionName(holder.date, String.valueOf(position)+"_date");

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

    public Appointment getItem (int position) {
        return mDataset.get(position);
    }

    public void removeAll(){
        Iterator<Appointment> iter = mDataset.iterator();
        while(iter.hasNext()){
            Appointment app = iter.next();
            int position = mDataset.indexOf(app);
            iter.remove();
            notifyItemRemoved(position);
        }
    }

    public int add(Appointment app){
        mDataset.add(app);
        notifyItemInserted(mDataset.size()-1);
        return mDataset.size()-1;
    }
}

