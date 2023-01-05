package com.uvapp.diagnosticvet.ui.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uvapp.diagnosticvet.R;
import com.uvapp.diagnosticvet.model.Disease;
import com.uvapp.diagnosticvet.ui.adapter.ResultAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DiagnosisResultsActivity extends AppCompatActivity implements View.OnClickListener {

    private ResultAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis_results);

        Intent intent = getIntent();
        final String diseasesJson = intent.getStringExtra("diseasesJson");

        Type listType = new TypeToken<ArrayList<Disease>>(){}.getType();
        ArrayList<Disease> diseases = new Gson().fromJson(diseasesJson, listType);

        setupRecyclerView(diseases);

        ImageView ivBackButton = (ImageView) findViewById(R.id.ivBackButton);
        ivBackButton.setOnClickListener(this);

        TextView tvNameTitle = (TextView) findViewById(R.id.tvNameTitle);
        tvNameTitle.setText("Prediagnóstico");
    }

    private void setupRecyclerView(ArrayList<Disease>  diseases) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_results);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ResultAdapter(this);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setDataSet(diseases);
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
