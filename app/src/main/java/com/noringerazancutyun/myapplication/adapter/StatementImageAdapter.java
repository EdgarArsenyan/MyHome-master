package com.noringerazancutyun.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.noringerazancutyun.myapplication.R;
import com.noringerazancutyun.myapplication.activity.ImageActivity;

import java.util.List;

public class StatementImageAdapter extends RecyclerView.Adapter<StatementImageAdapter.ImageViewHolder> {

    private Context context;
    private List<String> mImage;
    private String myId;

    public StatementImageAdapter(Context context, List<String> mImage, String myId){

        this.context = context;
        this.mImage = mImage;
        this.myId = myId;

    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        String image = mImage.get(position);
        Glide.with(context)
                .load(image)
                .centerInside()
                .into(holder.imageView);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageActivity.class);
                intent.putExtra("ID", myId);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mImage.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        LinearLayout parentLayout;


        public ImageViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_for_list);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
