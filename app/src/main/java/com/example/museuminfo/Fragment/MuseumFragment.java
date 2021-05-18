package com.example.museuminfo.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.museuminfo.Adapter.MuseumAdapter;
import com.example.museuminfo.Museum;
import com.example.museuminfo.R;
import com.example.museuminfo.SearchActivity;
import com.example.museuminfo.ShowMuseumActivity;
import com.example.museuminfo.UserDao;

import java.util.List;
import java.util.Map;

import static android.os.Looper.getMainLooper;

public class MuseumFragment extends Fragment{
    public ListView mListview;
    View view;
    MuseumAdapter museumAdapter;
    Intent mIntent;
    UserDao userDao;//用户数据库操作类
    private Handler mainhandler;//获取主线程
    LinearLayout searchbtn;
    List<Map<String, Object>> List;
    List<Museum> museumlist;
    SimpleAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_museum, container, false);
        mListview=view.findViewById(R.id.listview_museum);
        searchbtn=view.findViewById(R.id.search_view);
        userDao=new UserDao();

   //     mListview.setAdapter(museumAdapter);
        searchbtn.setOnClickListener(new ButtonListener());
        mainhandler=new Handler(getMainLooper());//获取主线程
        System.out.println("在执行museumrun里的东西了！");
        showmuslist();
        return view;
    }


//
//    class MyListener implements AdapterView.OnItemClickListener {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//
//
//            //Intent intent=new Intent(getActivity(), ShowMuseumActivity.class);
//            //intent.putExtra("museumname",mMap.get("museumName").toString());
//            //startActivity(intent);
//        }
//    }

    private class ButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.search_view:
                    Intent intent= new Intent(getActivity(), SearchActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }


    public void showmuslist(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List= UserDao.getMuseumData();
                mainhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new SimpleAdapter(getActivity(), List, R.layout.museum_item,
                                new String[]{"museumName","introduction","address"}, new int[]{R.id.museum_name,R.id.museum_intro, R.id.museum_address});//对应item中的名字
                        mListview.setAdapter(adapter);
                        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Map<String, Object> mMap = (Map<String, Object>) adapter.getItem(position);
                                Toast.makeText(getActivity(), mMap.get("museumName").toString(),Toast.LENGTH_SHORT ).show();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mainhandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent = new Intent();
                                                intent.setClass(getActivity(), ShowMuseumActivity.class);
                                              //  intent.putExtra("museumid",mMap.get("museumid").toString());
                                                intent.putExtra("museumname",mMap.get("museumName").toString());
                                                intent.putExtra("openingtime",mMap.get("openingTime").toString());
                                                intent.putExtra("address",mMap.get("address").toString());
                                                intent.putExtra("introduction",mMap.get("introduction").toString());
                                                intent.putExtra("telephone",mMap.get("telephone").toString());
                                                getActivity().startActivity(intent);
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
