package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class LecturesAdapter extends RecyclerView.Adapter<LecturesAdapter.ViewHolder> {

    public LecturesActivity activity;

    @Override
    public LecturesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lecture, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return activity.lectures.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView published;
        public ImageView photo;

        public ViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.title);
            published = view.findViewById(R.id.published);
            photo = view.findViewById(R.id.photo);

        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int index) {

        viewHolder.title.setText(activity.lectures.get(index).title);
        viewHolder.published.setText(activity.lectures.get(index).published);
        //Glide.with(activity.getApplicationContext()).load(activity.lectures.get(index).photo).into(viewHolder.photo);

        viewHolder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = activity.getSharedPreferences("courses", 0).edit();
                editor.putInt("video", index);
                editor.apply();

                Intent intent = new Intent(activity.getApplicationContext(), VideoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.getApplicationContext().startActivity(intent);
            }
        });
    }


}
