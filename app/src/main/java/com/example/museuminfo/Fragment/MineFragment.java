package com.example.museuminfo.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.museuminfo.LoginActivity;
import com.example.museuminfo.R;
import com.example.museuminfo.UserDao;
import com.example.museuminfo.Utils.SharedPreUtil;

import java.util.Map;

import static android.os.Looper.getMainLooper;

public class MineFragment extends Fragment{
    private View view;
    String username;
    Button exit_login;
    TextView name;
    public ListView mListview;
    UserDao userDao;//用户数据库操作类
    private Handler mainhandler;//获取主线程
    java.util.List<Map<String, Object>> List;
    SimpleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_mine,container,false);
        name=view.findViewById(R.id.mine_username);
        exit_login=view.findViewById(R.id.exit_login);
        mListview=view.findViewById(R.id.mine_dianp_list);
        userDao=new UserDao();

        username=(String) SharedPreUtil.getParam(getActivity(),SharedPreUtil.LOGIN_DATA,"");
        name.setText(username+"的主页");

        exit_login.setOnClickListener(new ButtonListener());
        mainhandler=new Handler(getMainLooper());//获取主线程
        showmydianping();
        return view;
    }

    public void showmydianping(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List= UserDao.getmydianping(username);
                mainhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new SimpleAdapter(getActivity(), List, R.layout.dianp_item,
                                new String[]{"museumName","exhi_point","server_point","enviro_point","pinglun"},
                                new int[]{R.id.museum_dianp_name,R.id.dianp_exhib,R.id.dianp_ser,R.id.dianp_envir,R.id.dianp_wenzi});//对应item中的名字
                        mListview.setAdapter(adapter);
                    }
                });
            }
        }).start();
    }
    private class ButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.exit_login:{
                    SharedPreUtil.setParam(getContext(), SharedPreUtil.IS_LOGIN, false);
                    SharedPreUtil.removeParam(getContext(), SharedPreUtil.LOGIN_DATA);

                    //重新跳转到登录页面
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                break;
            }
        }
    }
}
