package com.udl.bss.barbershopschedule.adapters;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.listeners.OnItemClickListener;

import java.util.Iterator;
import java.util.List;


public class MyAdapter  extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Barber> mDataset;
    private OnItemClickListener listener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView name;
        TextView description;
        ImageView image;
        TextView address;
        ViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.card_view);
            name = itemView.findViewById(R.id.name_cv);
            description = itemView.findViewById(R.id.description_cv);
            image = itemView.findViewById(R.id.image_cv);
            address = itemView.findViewById(R.id.address_cv);
        }
    }

    public MyAdapter(List<Barber> myDataset, OnItemClickListener listener) {
        mDataset = myDataset;
        this.listener = listener;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_card_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.description.setText(mDataset.get(position).getDescription());
        holder.image.setImageBitmap(mDataset.get(position).getImage());
        holder.name.setText(mDataset.get(position).getName());
        String address = mDataset.get(position).getAddress() + ", " + mDataset.get(position).getCity();
        holder.address.setText(address);

        ViewCompat.setTransitionName(holder.image, String.valueOf(position)+"_image");
        ViewCompat.setTransitionName(holder.description, String.valueOf(position)+"_desc");
        ViewCompat.setTransitionName(holder.name, String.valueOf(position)+"_name");
        ViewCompat.setTransitionName(holder.address, String.valueOf(position)+"_addr");

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

    public Barber getItem (int position) {
        return mDataset.get(position);
    }

    public void removeAll(){
        Iterator<Barber> iter = mDataset.iterator();
        while(iter.hasNext()){
            Barber barber = iter.next();
            int position = mDataset.indexOf(barber);
            iter.remove();
            notifyItemRemoved(position);
        }
    }

    public int add(Barber barber){
        mDataset.add(barber);
        notifyItemInserted(mDataset.size()-1);
        return mDataset.size()-1;
    }
}