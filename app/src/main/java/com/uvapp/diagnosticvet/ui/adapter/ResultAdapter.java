package com.uvapp.diagnosticvet.ui.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.uvapp.diagnosticvet.R;
import com.uvapp.diagnosticvet.model.Disease;
import com.uvapp.diagnosticvet.ui.activity.DiseaseActivity;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Disease> mDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewHolder(TextView tv) {
            super(tv);
            textView = tv;
        }
    }

    public ResultAdapter(Context context) {
        this.context = context;
        this.mDataSet = new ArrayList<>();
    }

    public void setDataSet(ArrayList<Disease> dataSet) {
        mDataSet = dataSet;
        notifyDataSetChanged();
    }

    @Override
    public ResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {

        TextView tv = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_view, parent, false);

        return new ViewHolder(tv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        final Disease currentDisease = mDataSet.get(i);

        holder.textView.setText(currentDisease.getName());

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DiseaseActivity.class);
                final String diseaseJson = new Gson().toJson(currentDisease);
                intent.putExtra("disease", diseaseJson);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}