package com.noringerazancutyun.myapplication.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.noringerazancutyun.myapplication.activity.StatementInfoActivity;
import com.noringerazancutyun.myapplication.fragment.FavoritListFragment;
import com.noringerazancutyun.myapplication.models.Statement;
import com.noringerazancutyun.myapplication.roomDB.MyStatData;
import com.noringerazancutyun.myapplication.roomDB.StatData;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavViewHolder> {

    private Context context;
    private OnDeleteListener onDeleteListener;
    private List<StatData> data;



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
        holder.statID = data.get(position).getStatID();
        holder.userID = data.get(position).getUserID();
        holder.imageUrl = data.get(position).getImageUrl();

        Glide.with(context)
                .load(holder.imageUrl)
                .centerInside()
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public  class FavViewHolder extends RecyclerView.ViewHolder {

        private TextView price;
        private TextView address;
        private TextView rooms;
        private TextView floor;
        private ImageView img;
        private ImageView delete;
        private String statID;
        private String userID;
        private String imageUrl;


        public FavViewHolder(final View itemView) {
            super(itemView);

            price = itemView.findViewById(R.id.fav_prica);
            address = itemView.findViewById(R.id.fav_district);
            rooms = itemView.findViewById(R.id.fav_rooms);
            floor = itemView.findViewById(R.id.fav_floor);
            img = itemView.findViewById(R.id.fav_img);
            delete = itemView.findViewById(R.id.delete_fav);


            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteListener.onDelete(data.get(getAdapterPosition()));
                    data.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), StatementInfoActivity.class);
                    intent.putExtra("statId", statID);
                    intent.putExtra("userID", userID);
                    context.startActivity(intent);
                }
            });
        }
    }

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    public interface OnDeleteListener {
        void onDelete(StatData dataModel);

    }
}
