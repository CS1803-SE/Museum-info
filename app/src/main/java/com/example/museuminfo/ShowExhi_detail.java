package com.example.museuminfo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ShowExhi_detail extends AppCompatActivity {
    private TextView name,intro,time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showexhi_detail);
        final String exhiname,exhitime,exhiintro;

        Intent intent=getIntent();
        exhiname=intent.getStringExtra("name");
        exhitime=intent.getStringExtra("time");
        exhiintro=intent.getStringExtra("intro");

        name=findViewById(R.id.exhi_detail_name);
        time=findViewById(R.id.exhi_detai_time);
        intro=findViewById(R.id.exhi_detai_intro);

        name.setText(exhiname);
        time.setText(exhitime);
        intro.setText(exhiintro);

    }
}
