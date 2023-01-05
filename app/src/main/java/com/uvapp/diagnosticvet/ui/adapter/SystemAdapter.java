package com.uvapp.diagnosticvet.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.uvapp.diagnosticvet.R;
import com.uvapp.diagnosticvet.model.System;
import com.uvapp.diagnosticvet.ui.activity.DiagnosisActivity;
import com.uvapp.diagnosticvet.ui.activity.DiseasesActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SystemAdapter extends RecyclerView.Adapter<SystemAdapter.ViewHolder> {

    private Context context;
    private String next;

    private ArrayList<System> mDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public ImageView ivSystem;

        public ViewHolder(View v) {
            super(v);

            tvName = (TextView) v.findViewById(R.id.tvSystemName);
            ivSystem = (ImageView) v.findViewById(R.id.ivSystem);
        }
    }

    public SystemAdapter(Context context, String next) {
        this.context = context;
        this.next = next;

        mDataSet = new ArrayList<>();
    }

    public void setDataSet(ArrayList<System> dataSet) {
        mDataSet = dataSet;
        notifyDataSetChanged();
    }

    @Override
    public SystemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.system_view, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        final System system = mDataSet.get(i);

        holder.tvName.setText(system.getName());
        Picasso.with(context)
                .load(system.getImageUrl())
                // .placeholder()
                .fit()
                .into(holder.ivSystem);


        // bind click event

        final Class targetActivity;
        if (next.equals("diseases")) {
            targetActivity = DiseasesActivity.class;
        } else /*if (next.equals("diagnosis"))*/ {
            targetActivity = DiagnosisActivity.class;
        }

        holder.ivSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, targetActivity);
                intent.putExtra("system_id", system.getId());
                intent.putExtra("system_name", system.getName());

                // pass image via intent :O
                holder.ivSystem.buildDrawingCache();
                final Bitmap species_image = holder.ivSystem.getDrawingCache();
                intent.putExtra("system_image", species_image);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
