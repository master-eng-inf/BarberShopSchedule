package com.udl.bss.barbershopschedule.adapters;


import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.domain.Appointment;
import com.udl.bss.barbershopschedule.domain.BarberService;
import com.udl.bss.barbershopschedule.domain.Client;
import com.udl.bss.barbershopschedule.domain.Promotion;
import com.udl.bss.barbershopschedule.listeners.OnItemClickListener;
import com.udl.bss.barbershopschedule.serverCommunication.APIController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class BarberAppointmentsAdapter extends RecyclerView.Adapter<BarberAppointmentsAdapter.ViewHolder> {
    private List<Appointment> mDataset;
    private OnItemClickListener listener;
    private String token;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView service_name;
        TextView client_name;
        TextView date;
        TextView promotion_name;

        ViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.card_view);
            service_name= itemView.findViewById(R.id.service_name_cv);
            client_name = itemView.findViewById(R.id.client_name_cv);
            promotion_name= itemView.findViewById(R.id.promotion_name_cv);
            date = itemView.findViewById(R.id.date_cv);
        }
    }

    public BarberAppointmentsAdapter(List<Appointment> myDataset, OnItemClickListener listener, String token) {
        mDataset = myDataset;
        this.listener = listener;
        this.token = token;
    }

    @Override
    public BarberAppointmentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.barber_appointments_card_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final BarberAppointmentsAdapter.ViewHolder holder, int position) {

        APIController.getInstance().getServiceById(token ,String.valueOf(mDataset.get(position).getService_id()))
                .addOnCompleteListener(new OnCompleteListener<BarberService>() {
                    @Override
                    public void onComplete(@NonNull Task<BarberService> task) {
                        BarberService barberService = task.getResult();
                        holder.service_name.setText(barberService.getName());
                    }
                });

        APIController.getInstance().getClientById(token ,String.valueOf(mDataset.get(position).getClient_id()))
                .addOnCompleteListener(new OnCompleteListener<Client>() {
                    @Override
                    public void onComplete(@NonNull Task<Client> task) {
                        Client client = task.getResult();
                        holder.client_name.setText(client.getName());
                    }
                });

        APIController.getInstance().getPromotionById(token, String.valueOf(mDataset.get(position).getPromotion_id()))
                .addOnCompleteListener(new OnCompleteListener<Promotion>() {
            @Override
            public void onComplete(@NonNull Task<Promotion> task) {
                Promotion promotion = task.getResult();
                String promoName = promotion != null ? promotion.getDescription() : "";
                holder.promotion_name.setText(promoName);
            }
        });


        Date date_obj = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        try {
            date_obj = format.parse(mDataset.get(position).getDate());
        } catch (ParseException e) { e.printStackTrace(); }

        String date = new SimpleDateFormat("HH:mm dd-MM-yyyy", new Locale("es", "ES")).format(date_obj);
        holder.date.setText(date);

        ViewCompat.setTransitionName(holder.service_name, String.valueOf(position)+"_serv");
        ViewCompat.setTransitionName(holder.client_name, String.valueOf(position)+"_name");
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
