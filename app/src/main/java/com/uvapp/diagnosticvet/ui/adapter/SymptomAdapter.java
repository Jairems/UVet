package com.uvapp.diagnosticvet.ui.adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.uvapp.diagnosticvet.R;
import com.uvapp.diagnosticvet.model.Symptom;

import java.util.ArrayList;

public class SymptomAdapter extends RecyclerView.Adapter<SymptomAdapter.ViewHolder> {

    private Context context;

    // for autocomplete purposes
    private ArrayList<String> symptomsArray;
    private SymptomArrayAdapter optionsAdapter;

    // data set
    private ArrayList<String> values;
    private ArrayList<Symptom> symptoms;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        AutoCompleteTextView autoCompleteTextView;
        TextView tvNumber;
        MyCustomEditTextListener myCustomEditTextListener;

        public ViewHolder(View view, MyCustomEditTextListener myCustomEditTextListener) {
            super(view);
            this.autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView);
            this.tvNumber = (TextView) view.findViewById(R.id.tvNumber);

            this.myCustomEditTextListener = myCustomEditTextListener;
            this.autoCompleteTextView.addTextChangedListener(myCustomEditTextListener);
        }
    }

    public SymptomAdapter(Context context) {
        this.context = context;
        this.values = new ArrayList<>();

        this.optionsAdapter = null;
    }

    public void setSymptoms(ArrayList<Symptom> symptoms) {
        this.symptoms = symptoms;
        this.symptomsArray = new ArrayList<>();
        for (int x=0; x < symptoms.size(); ++x) {
            symptomsArray.add( symptoms.get(x).getName() );
        }
        this.optionsAdapter = new SymptomArrayAdapter(context,
                R.layout.simple_text_view, symptomsArray);

        notifyDataSetChanged();
    }

    public void addField() {
        this.values.add("");
        notifyDataSetChanged();
    }

    public ArrayList<Integer> getSelectedSymptoms() {
        ArrayList<Integer> ids = new ArrayList<>();

        for (String typedValue : values) {
            for (Symptom symptom : symptoms) {
                if (typedValue.equalsIgnoreCase(symptom.getName())) {
                    ids.add(symptom.getId());
                    break; // stop searching for this typed value (we already have the id)
                }
            }
        }

        return ids;
    }

    @Override
    public SymptomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.symptom_field_view, parent, false);

        return new ViewHolder(v, new MyCustomEditTextListener());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        // final String currentValue = values.get(i);

        holder.tvNumber.setText(String.valueOf(i+1));
        holder.autoCompleteTextView.setAdapter(optionsAdapter);

        holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());
        holder.autoCompleteTextView.setText(values.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;

        void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            values.set(position, charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }
}