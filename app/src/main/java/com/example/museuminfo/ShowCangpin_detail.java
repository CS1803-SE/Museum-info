package com.example.museuminfo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ShowCangpin_detail extends AppCompatActivity {
    private TextView name,intro;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showcangpin_detail);
        final String cangpinname,cangpinintro;

        Intent intent = getIntent();
        cangpinname=intent.getStringExtra("name");
        cangpinintro=intent.getStringExtra("intro");

        name=findViewById(R.id.cangpin_detail_name);
        intro=findViewById(R.id.cangpin_detai_intro);
        name.setText(cangpinname);
        intro.setText(cangpinintro);

    }
}
