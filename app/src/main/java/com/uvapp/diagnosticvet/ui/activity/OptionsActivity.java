package com.uvapp.diagnosticvet.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uvapp.diagnosticvet.R;

public class OptionsActivity extends AppCompatActivity implements View.OnClickListener {

    private int species_id;
    private String species_name;
    private Bitmap species_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.llDiseasesBySpecies);
        linearLayout.setOnClickListener(this);

        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.llDiseasesBySystem);
        linearLayout2.setOnClickListener(this);

        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.llDiagnosisBySpecies);
        linearLayout3.setOnClickListener(this);

        LinearLayout linearLayout4 = (LinearLayout) findViewById(R.id.llDiagnosisBySystem);
        linearLayout4.setOnClickListener(this);

        Intent intent = getIntent();
        species_id = intent.getIntExtra("species_id", 0);
        species_name = intent.getStringExtra("species_name");
        species_image = intent.getParcelableExtra("species_image");

        ImageView ivBackButton = (ImageView) findViewById(R.id.ivBackButton);
        ivBackButton.setOnClickListener(this);

        TextView tvSpeciesName = (TextView) findViewById(R.id.tvNameTitle);
        tvSpeciesName.setText(species_name);

        if (species_image != null) {
            ImageView ivSpecies = (ImageView) findViewById(R.id.ivSelectedImage);
            ivSpecies.setImageBitmap(species_image);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBackButton:
                finish();
                break;

            case R.id.llDiseasesBySpecies:
                Intent i1 = new Intent(this, DiseasesActivity.class);
                i1.putExtra("species_id", species_id);
                i1.putExtra("species_name", species_name);
                i1.putExtra("species_image", species_image);
                startActivity(i1);
                break;

            case R.id.llDiseasesBySystem:
                Intent i2 = new Intent(this, SystemsActivity.class);
                i2.putExtra("species_id", species_id);
                i2.putExtra("species_name", species_name);
                i2.putExtra("species_image", species_image);
                i2.putExtra("next", "diseases");
                startActivity(i2);
                break;

            case R.id.llDiagnosisBySpecies:
                Intent i3 = new Intent(this, DiagnosisActivity.class);
                i3.putExtra("species_id", species_id);
                i3.putExtra("species_name", species_name);
                i3.putExtra("species_image", species_image);
                startActivity(i3);
                break;

            case R.id.llDiagnosisBySystem:
                Intent i4 = new Intent(this, SystemsActivity.class);
                i4.putExtra("species_id", species_id);
                i4.putExtra("species_name", species_name);
                i4.putExtra("species_image", species_image);
                i4.putExtra("next", "diagnosis");
                startActivity(i4);
                break;
        }
    }
}
