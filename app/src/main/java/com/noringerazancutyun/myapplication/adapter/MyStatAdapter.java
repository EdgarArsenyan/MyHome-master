package com.noringerazancutyun.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.noringerazancutyun.myapplication.R;
import com.noringerazancutyun.myapplication.activity.AddActivity;
import com.noringerazancutyun.myapplication.activity.MyStat;
import com.noringerazancutyun.myapplication.roomDB.MyStatData;

import java.util.List;

public class MyStatAdapter extends RecyclerView.Adapter<MyStatAdapter.MyStatViewHolder> {

    private Context context;
    private OnDeleteListener onDeleteListener;
    private List<MyStatData> data;

    public MyStatAdapter(Context context, List<MyStatData> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyStatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_stat_item, parent, false);
        MyStatAdapter.MyStatViewHolder viewHolder = new MyStatAdapter.MyStatViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyStatViewHolder holder, int position) {
        holder.price.setText(data.get(position).getPrice());
        holder.address.setText(data.get(position).getAddress());
        holder.rooms.setText(data.get(position).getRoom());
        holder.floor.setText(data.get(position).getFloor());
        holder.statID = data.get(position).getStatID();
        holder.imageUrl = data.get(position).getImageUrl();
        holder.user = data.get(position).getUserID();

        Glide.with(context)
                .load(holder.imageUrl)
                .centerInside()
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class MyStatViewHolder extends RecyclerView.ViewHolder {

        private TextView price;
        private TextView address;
        private TextView rooms;
        private TextView floor;
        private ImageView img;
        private ImageView delete;
        private String statID;
        private String imageUrl;
        private String user;


        public MyStatViewHolder(final View itemView) {
            super(itemView);

            final DatabaseReference mDataref;
            final StorageReference mReference;

            mDataref = FirebaseDatabase.getInstance().getReference();
            mReference = FirebaseStorage.getInstance().getReference("images");

            price = itemView.findViewById(R.id.my_stat_prica);
            address = itemView.findViewById(R.id.my_stat_address);
            rooms = itemView.findViewById(R.id.my_stat_rooms);
            floor = itemView.findViewById(R.id.my_stat_floor);
            img = itemView.findViewById(R.id.my_stat_img);
            delete = itemView.findViewById(R.id.delete_my_stat);



            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteListener.onDelete(data.get(getAdapterPosition()));
                    data.remove(getAdapterPosition());
//                    mReference.child(user).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(context, "Statement deleted", Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
                    mDataref.child("Statement").child(statID).removeValue();


                    notifyItemRemoved(getAdapterPosition());
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), AddActivity.class);
                    intent.putExtra("statId", statID);
                    context.startActivity(intent);
                }
            });
        }
    }

    public void setOnDeleteListener(MyStatAdapter.OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    public interface OnDeleteListener {
        void onDelete(MyStatData dataModel);

    }
}
