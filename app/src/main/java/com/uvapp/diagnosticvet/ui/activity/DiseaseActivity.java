package com.uvapp.diagnosticvet.ui.activity;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.uvapp.diagnosticvet.R;
import com.uvapp.diagnosticvet.model.Disease;

public class DiseaseActivity extends AppCompatActivity implements View.OnClickListener {

    private Disease disease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease);

        Intent intent = getIntent();
        final String diseaseJson = intent.getStringExtra("disease");
        disease = new Gson().fromJson(diseaseJson, Disease.class);

        ImageView ivBackButton = (ImageView) findViewById(R.id.ivBackButton);
        ivBackButton.setOnClickListener(this);

        TextView tvName = (TextView) findViewById(R.id.tvNameTitle);
        tvName.setText(disease.getName());

        setEventsOnReferences();
    }

    private void setEventsOnReferences() {
        TextView tvReview = (TextView) findViewById(R.id.tvReview);
        TextView tvExams = (TextView) findViewById(R.id.tvExams);
        TextView tvTreatment = (TextView) findViewById(R.id.tvTreatment);

        tvReview.setOnClickListener(this);
        tvExams.setOnClickListener(this);
        tvTreatment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBackButton:
                finish();
                break;

            case R.id.tvReview:
                Intent i1 = new Intent(this, InfoActivity.class);
                i1.putExtra("disease_name", disease.getName());
                i1.putExtra("sub_title", "Reseña");
                i1.putExtra("content", disease.getReview());
                startActivity(i1);
                break;

            case R.id.tvExams:
                Intent i2 = new Intent(this, InfoActivity.class);
                i2.putExtra("disease_name", disease.getName());
                i2.putExtra("sub_title", "Exámenes Complementarios");
                i2.putExtra("content", disease.getExams());
                startActivity(i2);
                break;

            case R.id.tvTreatment:
                Intent i3 = new Intent(this, InfoActivity.class);
                i3.putExtra("disease_name", disease.getName());
                i3.putExtra("sub_title", "Tratamiento");
                i3.putExtra("content", disease.getTreatment());
                startActivity(i3);
                break;
        }
    }
}
