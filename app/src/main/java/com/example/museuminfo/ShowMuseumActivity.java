package com.example.museuminfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.museuminfo.Utils.SharedPreUtil;

public class ShowMuseumActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv_name,tv_introduce,tv_opentime,tv_phone,tv_address;
    private Button dianp,bt_cangpin,bt_news,bt_zhanlan,bt_houdong;
    private String username,museumname;
    private Handler mainhandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum_detail);
        System.out.println(1);

        Intent intent = getIntent();
        museumname=intent.getStringExtra("museumname");
        final String introduction=intent.getStringExtra("introduction");
        final String openingtime=intent.getStringExtra("openingtime");
        final String address=intent.getStringExtra("address");
        final String telephone=intent.getStringExtra("telephone");

        initView();

         tv_name.setText(museumname);
         tv_introduce.setText(introduction);
         tv_address.setText(address);
         tv_opentime.setText(openingtime);
         tv_phone.setText(telephone);
         System.out.println("运行完settextl");
         System.out.println(museumname);
         System.out.println(introduction);

    }
    private void initView() {
        tv_name=findViewById(R.id.tv_museumname);
        tv_introduce=findViewById(R.id.tv_museumintr);
        tv_opentime=findViewById(R.id.tv_musopentime);
        tv_phone=findViewById(R.id.tv_museumtel);
        tv_address=findViewById(R.id.tv_museumadress);


        bt_cangpin=findViewById(R.id.bt_cangpin);
        bt_houdong=findViewById(R.id.bt_huodong);
        bt_news=findViewById(R.id.bt_museumnews);
        bt_zhanlan=findViewById(R.id.bt_zhanlan);
        dianp=findViewById(R.id.bt_dianp);

        dianp.setOnClickListener(this);
        bt_zhanlan.setOnClickListener(this);
        bt_news.setOnClickListener(this);
        bt_houdong.setOnClickListener(this);
        bt_cangpin.setOnClickListener(this);

        username=(String) SharedPreUtil.getParam(ShowMuseumActivity.this,SharedPreUtil.LOGIN_DATA,"");
        mainhandler=new Handler(getMainLooper());
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.bt_dianp:{
                Intent intent = new Intent(ShowMuseumActivity.this, DianPingActivity.class);
                intent.putExtra("museumname",museumname);

                startActivity(intent);
            }
            break;
            case R.id.bt_museumnews:{
                System.out.println("显示新闻");
                Intent intent=new Intent();
                intent.setClass(ShowMuseumActivity.this,ShowNews.class);
                intent.putExtra("museumname",museumname);
                startActivity(intent);

            }
            break;
            case R.id.bt_cangpin:{
                System.out.println("显示藏品");
                Intent intent=new Intent();
                intent.setClass(ShowMuseumActivity.this,ShowCangpin.class);
                intent.putExtra("museumname",museumname);
                startActivity(intent);
            }
            break;
            case R.id.bt_huodong:{
                System.out.println("显示活动");


            }
            break;
            case R.id.bt_zhanlan:{
                System.out.println("显示展览");
                Intent intent=new Intent();
                intent.setClass(ShowMuseumActivity.this,ShowExhibition.class);
                intent.putExtra("museumname",museumname);
                startActivity(intent);
            }
            break;
        }
    }
}
