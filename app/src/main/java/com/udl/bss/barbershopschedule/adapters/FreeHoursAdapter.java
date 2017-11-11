package com.udl.bss.barbershopschedule.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.domain.Time;
import com.udl.bss.barbershopschedule.listeners.OnItemClickListener;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Alex on 10/11/2017.
 */

public class FreeHoursAdapter extends RecyclerView.Adapter<FreeHoursAdapter.ViewHolder> {

    private List<Time> mDataset;
    private OnItemClickListener listener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cv;
        public TextView date;
        public View view;

        ViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.card_view);
            date = itemView.findViewById(R.id.date_cv);
            view = itemView;
        }
    }

    public FreeHoursAdapter(List<Time> mDataset, OnItemClickListener listener) {
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
        Time time = mDataset.get(position);
        String text = time.GetHour() + ":" + time.GetMinutes();

        if (time.GetMinutes() == 0) {
            text += "0";
        }

        ((TextView)holder.view.findViewById(R.id.date_cv)).setText(text);

        if (!time.GetAvailability()) {
            holder.view.findViewById(R.id.card_view).setBackgroundResource(R.color.freeHourUnavailable);
        }

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

    public Time getItem(int position) {
        return mDataset.get(position);
    }

    public void removeAll() {
        Iterator<Time> iter = mDataset.iterator();
        while (iter.hasNext()) {
            Time time = iter.next();
            int position = mDataset.indexOf(time);
            iter.remove();
            notifyItemRemoved(position);
        }
    }

    public int add(Time time) {
        mDataset.add(time);
        notifyItemInserted(mDataset.size() - 1);
        return mDataset.size() - 1;
    }
}
