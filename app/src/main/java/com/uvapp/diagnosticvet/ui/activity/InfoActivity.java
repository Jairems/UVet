package com.uvapp.diagnosticvet.ui.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.uvapp.diagnosticvet.R;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();
        final String disease_name = intent.getStringExtra("disease_name");
        final String sub_title = intent.getStringExtra("sub_title");
        final String content = intent.getStringExtra("content");

        ImageView ivBackButton = (ImageView) findViewById(R.id.ivBackButton);
        ivBackButton.setOnClickListener(this);

        // title: disease name
        TextView tvName = (TextView) findViewById(R.id.tvNameTitle);
        tvName.setText(disease_name);

        // content
        TextView tvContent = (TextView) findViewById(R.id.tvContent);
        tvContent.setText(content);

        // sub title
        TextView tvSubTitle = (TextView) findViewById(R.id.tvSubTitle);

        // underline text and set as sub title
        SpannableString spannableString = new SpannableString(sub_title);
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
        tvSubTitle.setText(spannableString);
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
