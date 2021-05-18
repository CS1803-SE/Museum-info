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

public class ShowCangpin extends AppCompatActivity {
    private ListView mListview;
    private Handler mainhandler;
    private String museumname;
    private TextView tv_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showcangpin);
        Intent intent = getIntent();
        museumname=intent.getStringExtra("museumname");
        tv_name=findViewById(R.id.cangpin_muname);
        tv_name.setText(museumname+"的藏品页");
        mListview=findViewById(R.id.listView_cangpin);
        mainhandler=new Handler(getMainLooper());
        showcangpin();
    }

    private void showcangpin(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Map<String, Object>> List = UserDao.getcangpininfo(museumname);
                mainhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        SimpleAdapter adapter = new SimpleAdapter(ShowCangpin.this, List, R.layout.cangpin_item,
                                new String[]{"name","intro"}, new int[]{R.id.cangpin_name,R.id.cangpin_intro});//对应item中的名字
                        mListview.setAdapter(adapter);
                        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Map<String, Object> mMap = (Map<String, Object>) adapter.getItem(position);
                                Toast.makeText(ShowCangpin.this, mMap.get("name").toString(),Toast.LENGTH_SHORT ).show();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mainhandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent = new Intent();
                                                intent.setClass(ShowCangpin.this, ShowCangpin_detail.class);
                                                intent.putExtra("name",mMap.get("name").toString());
                                                intent.putExtra("intro",mMap.get("intro").toString());
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
