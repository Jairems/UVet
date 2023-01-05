package com.uvapp.diagnosticvet.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uvapp.diagnosticvet.R;
import com.uvapp.diagnosticvet.io.DiagnosticVetApiAdapter;
import com.uvapp.diagnosticvet.model.Disease;
import com.uvapp.diagnosticvet.model.Symptom;
import com.uvapp.diagnosticvet.ui.adapter.SymptomAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiagnosisActivity extends AppCompatActivity implements Callback<ArrayList<Symptom>>, View.OnClickListener {

    private SymptomAdapter mAdapter;

    private int species_id, system_id;

    // Search "button"
    private TextView tvSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);

        // read intent extras

        Intent intent = getIntent();

        species_id = intent.getIntExtra("species_id", 0);
        final String species_name = intent.getStringExtra("species_name");
        final Bitmap species_image = intent.getParcelableExtra("species_image");

        system_id = intent.getIntExtra("system_id", 0);
        final String system_name = intent.getStringExtra("system_name");
        final Bitmap system_image = intent.getParcelableExtra("system_image");

        setupRecyclerView();

        ImageView ivBackButton = (ImageView) findViewById(R.id.ivBackButton);
        ivBackButton.setOnClickListener(this);

        TextView tvNameTitle = (TextView) findViewById(R.id.tvNameTitle);
        ImageView ivToolbar = (ImageView) findViewById(R.id.ivSelectedImage);

        if (species_id > 0) {
            tvNameTitle.setText(species_name);
            if (species_image != null)
                ivToolbar.setImageBitmap(species_image);

        } else if (system_id > 0) {
            tvNameTitle.setText(system_name);
            if (system_image != null)
                ivToolbar.setImageBitmap(system_image);

        }


        // Fetch symptoms based on species_id or system_id

        Call<ArrayList<Symptom>> call = null;

        if (species_id > 0) {
            call = DiagnosticVetApiAdapter.getApiService().getSymptomsBySpecies(species_id);
        } else if (system_id > 0) {
            call = DiagnosticVetApiAdapter.getApiService().getSymptomsBySystem(system_id);
        }

        if (call != null)
            call.enqueue(this);


        // Add fields
        ImageView ivAddField = (ImageView) findViewById(R.id.ivAddSymptomField);
        ivAddField.setOnClickListener(this);
        // Search now
        tvSearch = (TextView) findViewById(R.id.tvSearchNow);
        tvSearch.setOnClickListener(this);
    }



    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_symptoms);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new SymptomAdapter(this);
        recyclerView.setAdapter(mAdapter);

        // start with 4 fields
        mAdapter.addField();
        mAdapter.addField();
        mAdapter.addField();
        mAdapter.addField();
    }


    // Get symptoms

    @Override
    public void onResponse(Call<ArrayList<Symptom>> call, Response<ArrayList<Symptom>> response) {
        if (response.isSuccessful()) {
            // symptoms info for the autocomplete
            ArrayList<Symptom> symptoms = response.body();
            mAdapter.setSymptoms(symptoms);
        }
    }

    @Override
    public void onFailure(Call<ArrayList<Symptom>> call, Throwable t) {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBackButton:
                finish();
                break;

            case R.id.ivAddSymptomField:
                mAdapter.addField();
                break;

            case R.id.tvSearchNow:
                performSearchQuery();
                break;
        }
    }

    private void performSearchQuery() {
        ArrayList<Integer> selected_symptoms = mAdapter.getSelectedSymptoms();

        if (selected_symptoms.isEmpty()) {
            Toast.makeText(this, "Ingrese al menos un síntoma válido", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Realizando consulta ...", Toast.LENGTH_SHORT).show();
        tvSearch.setEnabled(false);

        Call<ArrayList<Disease>> call;
        if (species_id > 0)
            call = DiagnosticVetApiAdapter.getApiService().getDiagnosisBySpecies(species_id, selected_symptoms);
        else
            call = DiagnosticVetApiAdapter.getApiService().getDiagnosisBySystem(system_id, selected_symptoms);

        if (call!=null)
            call.enqueue(new Callback<ArrayList<Disease>>() {
                @Override
                public void onResponse(Call<ArrayList<Disease>> call, Response<ArrayList<Disease>> response) {
                    if (response.isSuccessful()) {
                        ArrayList<Disease> diseases = response.body();
                        if (diseases.size() > 0) {
                            Intent intent = new Intent(getApplicationContext(), DiagnosisResultsActivity.class);
                            intent.putExtra("diseasesJson", new Gson().toJson(diseases));
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "No existen enfermedades registradas para esta combinación de síntomas.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    tvSearch.setEnabled(true);
                }

                @Override
                public void onFailure(Call<ArrayList<Disease>> call, Throwable t) {
                    tvSearch.setEnabled(true);
                }
            });
    }

}
