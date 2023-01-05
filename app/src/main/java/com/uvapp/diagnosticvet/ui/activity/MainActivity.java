package com.uvapp.diagnosticvet.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uvapp.diagnosticvet.R;
import com.uvapp.diagnosticvet.io.DiagnosticVetApiAdapter;
import com.uvapp.diagnosticvet.model.Species;
import com.uvapp.diagnosticvet.ui.adapter.SpeciesAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<ArrayList<Species>> {

    private SpeciesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_species);

        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new SpeciesAdapter(this);
        recyclerView.setAdapter(mAdapter);

        Call<ArrayList<Species>> call = DiagnosticVetApiAdapter.getApiService().getSpecies();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ArrayList<Species>> call, Response<ArrayList<Species>> response) {
        if (response.isSuccessful()) {
            ArrayList<Species> species = response.body();
            mAdapter.setDataSet(species);
        }
    }

    @Override
    public void onFailure(Call<ArrayList<Species>> call, Throwable t) {

    }
}
