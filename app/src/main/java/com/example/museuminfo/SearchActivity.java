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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_search;
    private Button search_click,search_return;//搜索与返回键
    private Button search_exhi,search_cangpin;

    ListView mListview;
    private UserDao userDao;//用户数据库操作类
    private Handler mainhandler;//获取主线程
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initview();

    }
    private void initview(){
        et_search=findViewById(R.id.search_view);
        search_click=findViewById(R.id.search_click);
        search_return=findViewById(R.id.search_cancel_button);
        search_cangpin=findViewById(R.id.search_cangpin_click);
        search_exhi=findViewById(R.id.search_exhi_click);

        mListview=findViewById(R.id.listView_search);
        mainhandler=new Handler(getMainLooper());
        search_click.setOnClickListener(this);
        search_exhi.setOnClickListener(this);
        search_cangpin.setOnClickListener(this);
        search_return.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_click:{
                showmuseumsearchlist();
            }
            break;
            case R.id.search_cangpin_click:{
                showcangpin();
            }
            break;
            case R.id.search_exhi_click:{
                showexhi();
            }
            break;
            case R.id.search_cancel_button:{
                finish();
            }
            break;
        }
    }

    private void showmuseumsearchlist(){
        final String search = et_search.getText().toString();
        System.out.println(search+"正在寻找中");
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Map<String, Object>> List = UserDao.getSearchData(search);
                mainhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        SimpleAdapter adapter = new SimpleAdapter(SearchActivity.this, List, R.layout.museum_item,
                                new String[]{"museumName","introduction","address"}, new int[]{R.id.museum_name,R.id.museum_intro, R.id.museum_address});//对应item中的名字
                        mListview.setAdapter(adapter);
                        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Map<String, Object> mMap = (Map<String, Object>) adapter.getItem(position);
                                Toast.makeText(SearchActivity.this, mMap.get("museumName").toString(),Toast.LENGTH_SHORT ).show();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mainhandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent = new Intent();
                                                intent.setClass(SearchActivity.this, ShowMuseumActivity.class);
                                                intent.putExtra("museumname",mMap.get("museumName").toString());
                                                intent.putExtra("openingtime",mMap.get("openingTime").toString());
                                                intent.putExtra("address",mMap.get("address").toString());
                                                intent.putExtra("introduction",mMap.get("introduction").toString());
                                                intent.putExtra("telephone",mMap.get("telephone").toString());
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
    private void showcangpin(){
        final String search = et_search.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Map<String, Object>> List = UserDao.searchcangpininfo(search);
                mainhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        SimpleAdapter adapter = new SimpleAdapter(SearchActivity.this, List, R.layout.cangpin_item,
                                new String[]{"name","intro"}, new int[]{R.id.cangpin_name,R.id.cangpin_intro});//对应item中的名字
                        mListview.setAdapter(adapter);
                        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Map<String, Object> mMap = (Map<String, Object>) adapter.getItem(position);
                                Toast.makeText(SearchActivity.this, mMap.get("name").toString(),Toast.LENGTH_SHORT ).show();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mainhandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent = new Intent();
                                                intent.setClass(SearchActivity.this, ShowCangpin_detail.class);
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
        //searchcangpininfo
    }
    private void showexhi(){
        final String search = et_search.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Map<String, Object>> List = UserDao.searchexhiinfo(search);
                mainhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        SimpleAdapter adapter = new SimpleAdapter(SearchActivity.this, List, R.layout.exhi_item,
                                new String[]{"name","intro","time"}, new int[]{R.id.exhibit_name,R.id.exhi_intro, R.id.exhi_time});//对应item中的名字
                        mListview.setAdapter(adapter);
                        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Map<String, Object> mMap = (Map<String, Object>) adapter.getItem(position);
                                Toast.makeText(SearchActivity.this, mMap.get("name").toString(),Toast.LENGTH_SHORT ).show();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mainhandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent = new Intent();
                                                intent.setClass(SearchActivity.this, ShowExhi_detail.class);
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