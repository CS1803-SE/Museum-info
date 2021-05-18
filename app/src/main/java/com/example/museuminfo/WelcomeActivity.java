package com.example.museuminfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.museuminfo.Utils.ApplicationUtil;
import com.example.museuminfo.Utils.SharedPreUtil;

public class WelcomeActivity extends AppCompatActivity {
    private Button btn_jump;


    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                //判断用户是否登录
                boolean userIsLogin = (boolean) SharedPreUtil.getParam(WelcomeActivity.this,
                        SharedPreUtil.IS_LOGIN, false);
                if (userIsLogin) {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

                finish();
            } else if (msg.what == 0) {
                thread.interrupt();
            }

        }

    };
    final Message message = new Message();
    final Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(3000);
                message.what = 1;
                handler.sendMessage(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        thread.start();

        btn_jump = (Button) findViewById(R.id.btn_jump);

        btn_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                flag = true;
                message.what = 0;
                handler.sendMessage(message);

                //判断用户是否登录
                boolean userIsLogin = (boolean) SharedPreUtil.getParam(WelcomeActivity.this,
                        SharedPreUtil.IS_LOGIN, false);
                if (userIsLogin) {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

                finish();


            }
        });
        ApplicationUtil.getInstance().addActivity(this);
    }
}
