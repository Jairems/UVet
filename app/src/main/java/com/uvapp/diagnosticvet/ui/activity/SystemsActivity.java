package com.uvapp.diagnosticvet.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.uvapp.diagnosticvet.R;
import com.uvapp.diagnosticvet.io.DiagnosticVetApiAdapter;
import com.uvapp.diagnosticvet.model.System;
import com.uvapp.diagnosticvet.ui.adapter.SystemAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SystemsActivity extends AppCompatActivity implements Callback<ArrayList<System>>, View.OnClickListener {

    private SystemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_systems);

        Intent intent = getIntent();
        final int species_id = intent.getIntExtra("species_id", 0);
        final String species_name = intent.getStringExtra("species_name");
        final Bitmap species_image = intent.getParcelableExtra("species_image");
        final String next = intent.getStringExtra("next"); // whats the next step?

        ImageView ivBackButton = (ImageView) findViewById(R.id.ivBackButton);
        ivBackButton.setOnClickListener(this);

        TextView tvSpeciesName = (TextView) findViewById(R.id.tvNameTitle);
        tvSpeciesName.setText(species_name);

        if (species_image != null) {
            ImageView ivSpecies = (ImageView) findViewById(R.id.ivSelectedImage);
            ivSpecies.setImageBitmap(species_image);
        }


        setupRecyclerView(next);

        Call<ArrayList<System>> call = DiagnosticVetApiAdapter.getApiService().getSystemsBySpecies(species_id);
        call.enqueue(this);
    }

    private void setupRecyclerView(final String next) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_systems);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new SystemAdapter(this, next);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResponse(Call<ArrayList<System>> call, Response<ArrayList<System>> response) {
        if (response.isSuccessful()) {
            ArrayList<System> systems = response.body();
            mAdapter.setDataSet(systems);
        }
    }

    @Override
    public void onFailure(Call<ArrayList<System>> call, Throwable t) {

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
