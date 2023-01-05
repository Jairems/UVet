package com.uvapp.diagnosticvet.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SymptomArrayAdapter extends ArrayAdapter<String> {
    private final Context mContext;
    private final List<String> symptoms;
    private final List<String> allSymptoms;
    private final List<String> symptomSuggestions;
    private final int mLayoutResourceId;

    public SymptomArrayAdapter(Context context, int resource, List<String> symptoms) {
        super(context, resource, symptoms);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.symptoms = new ArrayList<>(symptoms);
        this.allSymptoms = new ArrayList<>(symptoms);
        this.symptomSuggestions = new ArrayList<>();
    }

    public int getCount() {
        return symptoms.size();
    }

    public String getItem(int position) {
        return symptoms.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                convertView = inflater.inflate(mLayoutResourceId, parent, false);
            }
            String symptom = getItem(position);
            TextView name = (TextView) convertView;
            name.setText(symptom);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            public String convertResultToString(Object resultValue) {
                return (String) resultValue;
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (constraint != null) {
                    symptomSuggestions.clear();
                    for (String symptom : allSymptoms) {
                        if (symptom.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            symptomSuggestions.add(symptom);
                        }
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = symptomSuggestions;
                    filterResults.count = symptomSuggestions.size();
                    return filterResults;
                } else {
                    return new FilterResults();
                }
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                symptoms.clear();
                if (results != null && results.count > 0) {
                    // avoids unchecked cast warning when using mDepartments.addAll((ArrayList<String>) results.values);
                    List<?> result = (List<?>) results.values;
                    for (Object object : result) {
                        if (object instanceof String) {
                            symptoms.add((String) object);
                        }
                    }
                } else if (constraint == null) {
                    // no filter, add entire original list back in
                    symptoms.addAll(allSymptoms);
                }
                notifyDataSetChanged();
            }
        };
    }
}