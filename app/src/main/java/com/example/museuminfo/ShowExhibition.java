package com.example.museuminfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Map;

public class ShowExhibition extends AppCompatActivity {
    private ListView mListview;
    private Handler mainhandler;
    private String museumname;
    private TextView tv_muname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showexhibition);

        Intent intent = getIntent();
        museumname=intent.getStringExtra("museumname");
        tv_muname=findViewById(R.id.exhi_muname);
        tv_muname.setText(museumname+"的展览页");
        mListview=findViewById(R.id.listView_exhi);
        mainhandler=new Handler(getMainLooper());
        System.out.println("显示展览信息");
        showexih();
    }

    private void showexih(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Map<String, Object>> List = UserDao.searchexhiinfo(museumname);
                mainhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        SimpleAdapter adapter = new SimpleAdapter(ShowExhibition.this, List, R.layout.exhi_item,
                                new String[]{"name","intro","time"}, new int[]{R.id.exhibit_name,R.id.exhi_intro, R.id.exhi_time});//对应item中的名字
                        mListview.setAdapter(adapter);
                        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Map<String, Object> mMap = (Map<String, Object>) adapter.getItem(position);
                                Toast.makeText(ShowExhibition.this, mMap.get("name").toString(),Toast.LENGTH_SHORT ).show();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mainhandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent = new Intent();
                                                intent.setClass(ShowExhibition.this, ShowExhi_detail.class);
                                                intent.putExtra("name",mMap.get("name").toString());
                                                intent.putExtra("intro",mMap.get("intro").toString());
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
}
