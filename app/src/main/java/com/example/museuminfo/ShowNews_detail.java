package com.example.museuminfo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ShowNews_detail extends AppCompatActivity{

    private TextView name,intro,author,time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shownews_detail);
        final String newsname,newsintro,newsauthor,newstime;

        Intent intent = getIntent();
        newsname=intent.getStringExtra("name");
        newsintro=intent.getStringExtra("intro");
        newsauthor=intent.getStringExtra("author");
        newstime=intent.getStringExtra("time");


        name=findViewById(R.id.news_detail_name);
        intro=findViewById(R.id.news_detai_intro);
        author=findViewById(R.id.news_detail_author);
        time=findViewById(R.id.news_detail_time);
        name.setText(newsname);
        intro.setText(newsintro);
        author.setText(newsauthor);
        time.setText(newstime);
    }
}

