package com.example.museuminfo;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.Map;

import static android.os.Looper.getMainLooper;

public class MuseumDetailFragment extends Fragment {
    TextView museumname;
    TextView museumintr;
    TextView museumadress;
    TextView musopentime;
    TextView museumtel;
    Button collection;
    Button news;
    Button exhibition;
    Button activity;
    //   LinearLayout searchbtn;
    private Handler mainhandler;//获取主线程
    private UserDao userDao;//用户数据库操作类
 //   private  MuseumAdapter museumAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_museum_detail, container, false);
        museumname=view.findViewById(R.id.tv_museumname);
        museumintr=view.findViewById(R.id.tv_museumintr);
        museumadress=view.findViewById(R.id.tv_museumadress);
        musopentime=view.findViewById(R.id.tv_musopentime);
        museumtel=view.findViewById(R.id.tv_museumtel);
        collection=view.findViewById(R.id.bt_cangpin);
        news=view.findViewById(R.id.bt_museumnews);
        exhibition=view.findViewById(R.id.bt_zhanlan);
        activity=view.findViewById(R.id.bt_huodong);
        //    searchbtn=view.findViewById(R.id.search_view);
        userDao=new UserDao();
        //     museumAdapter=new MuseumAdapter(getContext(),this);
   /*     mListview.setAdapter(museumAdapter);
        mListview.setOnItemClickListener((AdapterView.OnItemClickListener) this);
        searchbtn.setOnClickListener((View.OnClickListener) this);
*/
        mainhandler=new Handler(getMainLooper());//获取主线程
        System.out.println("在执行museumrun里的东西了！");
        showMuseumDetail();
        //按钮点击事件：事件为页面跳转
        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         //       Intent intent=new Intent(MuseumDetailFragment.this, CollectionActivity.class);
         //       startActivity(intent);
            }
        });
        return view;
    }

    public void showMuseumDetail(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String s="";//获取搜索框输入的博物馆名字
                List<Map<String, Object>> List = UserDao.getSearchData(s);
                mainhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        for(Map map:List){
                            museumname.setText((String)map.get("museumName"));
                            museumintr.setText((String)map.get("introduction"));
                            museumadress.setText((String)map.get("address"));
                            musopentime.setText((String)map.get("openingTime"));
                            museumtel.setText((String)map.get("consultationTelephone"));
                        }
                    }
                });
            }
        }).start();
    }

   public void click(View view) {
        if(view.getId()==R.id.search_view){

        }else{
            int position=(int)view.getTag();
            //String clickItemText=museumAdapter.getStringList().get(position);
            //该做跳转了

        }
    }

}


