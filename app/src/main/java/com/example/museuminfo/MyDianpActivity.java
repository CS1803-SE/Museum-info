package com.example.museuminfo;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Map;

public class MyDianpActivity extends AppCompatActivity {
    private EditText et_search;
    private Button search_click,search_return;//搜索与返回键

    ListView mListview;
    private UserDao userDao;//用户数据库操作类
    private Handler mainhandler;//获取主线程
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydianp);
        mainhandler=new Handler(getMainLooper());

    }
    private void showsearchlist(){
        final String search = et_search.getText().toString();
        System.out.println(search+"正在寻找中");
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Map<String, Object>> List = UserDao.getSearchData(search);
                mainhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        SimpleAdapter adapter = new SimpleAdapter(MyDianpActivity.this, List, R.layout.museum_item,
                                new String[]{"museumName","introduction","address"}, new int[]{R.id.museum_name,R.id.museum_intro, R.id.museum_address});//对应item中的名字
                        mListview.setAdapter(adapter);
                    }
                });
            }
        }).start();

    }
}
