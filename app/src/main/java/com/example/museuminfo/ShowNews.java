package com.example.museuminfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Map;

public class ShowNews extends AppCompatActivity implements View.OnClickListener{
    private EditText et_time1,et_time2;
    private ListView mListview;
    private Handler mainhandler;
    private String museumname;
    private TextView tv_name;
    private Button search_time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shownews);
        Intent intent = getIntent();
        museumname=intent.getStringExtra("museumname");
        tv_name=findViewById(R.id.news_muname);
        et_time1=findViewById(R.id.et_newstimebegin);
        et_time2=findViewById(R.id.et_newstimeover);
        search_time=findViewById(R.id.bt_timeselectnews);
        search_time.setOnClickListener(this);
        tv_name.setText(museumname+"的新闻页");
        mListview=findViewById(R.id.listView_news);
        mainhandler=new Handler(getMainLooper());
        shownews();
    }


    private void shownewslistbytime(){
        final String time1 = et_time1.getText().toString();
        final String time2 = et_time2.getText().toString();
        System.out.println(time1+"~"+time2+"正在寻找中");
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Map<String, Object>> List = UserDao.getnewsinfobytime(museumname,time1,time2);
                mainhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        SimpleAdapter adapter = new SimpleAdapter(ShowNews.this, List, R.layout.news_item,
                                new String[]{"name","intro","author","time"}, new int[]{R.id.news_name,R.id.news_intro,R.id.news_author,R.id.news_time});//对应item中的名字
                        mListview.setAdapter(adapter);
                        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Map<String, Object> mMap = (Map<String, Object>) adapter.getItem(position);
                                Toast.makeText(ShowNews.this, mMap.get("name").toString(),Toast.LENGTH_SHORT ).show();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mainhandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent = new Intent();
                                                intent.setClass(ShowNews.this, ShowNews_detail.class);
                                                intent.putExtra("name",mMap.get("name").toString());
                                                intent.putExtra("intro",mMap.get("intro").toString());
                                                intent.putExtra("author",mMap.get("author").toString());
                                                intent.putExtra("time",mMap.get("time").toString());
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                }).start();
                            }
                        });
                    }
                });
            }
        }).start();
    }
    private void shownews(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Map<String, Object>> List = UserDao.getnewsinfo(museumname);
                mainhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        SimpleAdapter adapter = new SimpleAdapter(ShowNews.this, List, R.layout.news_item,
                                new String[]{"name","intro","author","time"}, new int[]{R.id.news_name,R.id.news_intro,R.id.news_author,R.id.news_time});//对应item中的名字
                        mListview.setAdapter(adapter);
                        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Map<String, Object> mMap = (Map<String, Object>) adapter.getItem(position);
                                Toast.makeText(ShowNews.this, mMap.get("name").toString(),Toast.LENGTH_SHORT ).show();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mainhandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent = new Intent();
                                                intent.setClass(ShowNews.this, ShowNews_detail.class);
                                                intent.putExtra("name",mMap.get("name").toString());
                                                intent.putExtra("intro",mMap.get("intro").toString());
                                                intent.putExtra("author",mMap.get("author").toString());
                                                intent.putExtra("time",mMap.get("time").toString());
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                }).start();
                            }
                        });
                    }
                });
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_timeselectnews:
                shownewslistbytime();
                break;
        }
    }
}
