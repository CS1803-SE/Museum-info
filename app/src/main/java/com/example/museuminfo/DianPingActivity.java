package com.example.museuminfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.museuminfo.Utils.SharedPreUtil;

public class DianPingActivity extends AppCompatActivity {
    private RatingBar ratingBar1,ratingBar2,ratingBar3;//星级评分条
    private EditText pinglun;//评论


    private UserDao userDao;//用户数据库操作类
    private Handler mainhandler;//获取主线程

    String museumname,username;
    float rb_exb,rb_ser,rb_envir,rating;
    String pingl_str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dianp);
        Intent intent=getIntent();
        museumname=intent.getStringExtra("museumname");

        username=(String) SharedPreUtil.getParam(DianPingActivity.this,SharedPreUtil.LOGIN_DATA,"");
        ratingBar1=(RatingBar) findViewById(R.id.rb_exb);
        ratingBar2=(RatingBar) findViewById(R.id.rb_ser);
        ratingBar3=(RatingBar) findViewById(R.id.rb_envir);
        pinglun=findViewById(R.id.rb_pingl);
        userDao=new UserDao();
        mainhandler=new Handler(getMainLooper());//获取主线程
        Button button=(Button) findViewById(R.id.bt_dianp_submit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * getRating():用于获取等级，表示选中的几颗星
                 * getStepSize():用语获取每次至少要改变多少个星级
                 * getProgress():用语获取进度，获取到的进度值为getRating()方法返回值与getStepSize()方法返回值之积
                 */
                int result = ratingBar1.getProgress();
                float step = ratingBar1.getStepSize();

                rb_exb=ratingBar1.getRating();//展览
                rb_ser=ratingBar2.getRating();//服务
                rb_envir=ratingBar3.getRating();//环境
                rating = (rb_exb+rb_ser+rb_envir)/3;
                pingl_str = pinglun.getText().toString();

                setdianping();
            }
        });

    }
    private void setdianping(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int flag=userDao.setDianp(museumname,username,rb_exb,rb_ser,rb_envir,pingl_str);
                mainhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(flag==1) {
                            Toast.makeText(DianPingActivity.this, "点评成功！", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(DianPingActivity.this, "点评失败！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }
}
