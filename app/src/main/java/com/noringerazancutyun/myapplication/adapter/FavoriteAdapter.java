package com.noringerazancutyun.myapplication.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.noringerazancutyun.myapplication.R;
import com.noringerazancutyun.myapplication.models.Statement;
import com.noringerazancutyun.myapplication.roomDB.StatData;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavViewHolder> {

    Context context;
    List<StatData> data;

    public FavoriteAdapter(Context context, List<StatData> data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_rv, parent, false);
        FavViewHolder viewHolder = new FavViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        holder.price.setText(data.get(position).getPrice());
        holder.address.setText(data.get(position).getAddress());
        holder.rooms.setText(data.get(position).getRoom());
        holder.floor.setText(data.get(position).getFloor());

//     holder.img.setImageResource(data.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class FavViewHolder extends RecyclerView.ViewHolder {

        private TextView price;
        private TextView address;
        private TextView rooms;
        private TextView floor;
        private ImageView img;

        public FavViewHolder(View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.fav_prica);
            address = itemView.findViewById(R.id.fav_district);
            rooms = itemView.findViewById(R.id.fav_rooms);
            floor = itemView.findViewById(R.id.fav_floor);
            img = itemView.findViewById(R.id.fav_img);
        }
    }
}
