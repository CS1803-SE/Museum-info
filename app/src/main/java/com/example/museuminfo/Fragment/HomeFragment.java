package com.example.museuminfo.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.museuminfo.R;
import com.example.museuminfo.UserDao;

import java.util.List;
import java.util.Map;

import static android.os.Looper.getMainLooper;

/**
 * 更改为排行榜显示，并且按照“展览，服务，环境”三方面进行排行
 */

public class HomeFragment extends Fragment {
    ListView mListview;

    private UserDao userDao;//用户数据库操作类
    private Handler mainhandler;//获取主线程
    // private  MuseumAdapter museumAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mListview=view.findViewById(R.id.rank_museum_list);
        userDao=new UserDao();
        mainhandler=new Handler(getMainLooper());//获取主线程
        System.out.println("在执行museumrun里的东西了！");
        showrank();
        return view;
    }

    public void showrank(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Map<String, Object>> List = UserDao.getMuseumRank();
                mainhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        SimpleAdapter adapter = new SimpleAdapter(getActivity(),List, R.layout.rank_item,
                                new String[]{"museumName","All_Point","P_exhiPoint","P_serPoint","P_envirPoint"}, new int[]{R.id.museum_rank_name ,R.id.p_all , R.id.p_exhib ,R.id.p_ser ,R.id.p_envir });//对应item中的名字
                        mListview.setAdapter(adapter);
                    }
                });
            }
        }).start();
    }

}

