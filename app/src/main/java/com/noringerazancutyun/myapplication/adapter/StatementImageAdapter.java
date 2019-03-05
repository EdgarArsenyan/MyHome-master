package com.noringerazancutyun.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.noringerazancutyun.myapplication.R;
import com.noringerazancutyun.myapplication.models.Statement;

import java.util.List;

public class StatementImageAdapter extends RecyclerView.Adapter<StatementImageAdapter.ImageViewHolder> {

    private Context context;
    private List<Statement> mStatement;

    public StatementImageAdapter(Context context, List<Statement> mStatement){

        this.context = context;
        this.mStatement = mStatement;

    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        Statement statementCurrent = mStatement.get(position);
        Glide.with(context)
                .load(statementCurrent.getStatementImageurl())
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mStatement.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;


        public ImageViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_for_list);
        }
    }
}
