package com.example.nsn11.dotcom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class Matching_Complete_Activity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching_complete);
        imageView = (ImageView)findViewById(R.id.ImageHome);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Matching_Complete_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
