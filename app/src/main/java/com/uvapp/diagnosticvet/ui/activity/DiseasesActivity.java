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

import com.uvapp.diagnosticvet.R;
import com.uvapp.diagnosticvet.io.DiagnosticVetApiAdapter;
import com.uvapp.diagnosticvet.model.Disease;
import com.uvapp.diagnosticvet.ui.adapter.DiseaseAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiseasesActivity extends AppCompatActivity implements Callback<ArrayList<Disease>>, View.OnClickListener {

    private DiseaseAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diseases);

        // read intent extras

        Intent intent = getIntent();

        final int species_id = intent.getIntExtra("species_id", 0);
        final String species_name = intent.getStringExtra("species_name");
        final Bitmap species_image = intent.getParcelableExtra("species_image");

        final int system_id = intent.getIntExtra("system_id", 0);
        final String system_name = intent.getStringExtra("system_name");
        final Bitmap system_image = intent.getParcelableExtra("system_image");

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


        setupRecyclerView();

        // Fetch diseases based on species_id or system_id

        Call<ArrayList<Disease>> call = null;

        if (species_id > 0) {
            call = DiagnosticVetApiAdapter.getApiService().getDiseasesBySpecies(species_id);
        } else if (system_id > 0) {
            call = DiagnosticVetApiAdapter.getApiService().getDiseasesBySystem(system_id);
        }

        if (call != null)
            call.enqueue(this);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_diseases);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new DiseaseAdapter(this);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onResponse(Call<ArrayList<Disease>> call, Response<ArrayList<Disease>> response) {
        if (response.isSuccessful()) {
            ArrayList<Disease> diseases = response.body();
            // Log.d("onResponse diseases", "Size of diseases => " + diseases.size());

            mAdapter.setDataSet(diseases);
        }
    }

    @Override
    public void onFailure(Call<ArrayList<Disease>> call, Throwable t) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBackButton:
                finish();
                break;
        }
    }
}
