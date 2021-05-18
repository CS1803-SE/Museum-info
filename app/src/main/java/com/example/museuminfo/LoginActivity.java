package com.example.museuminfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.museuminfo.Utils.ApplicationUtil;
import com.example.museuminfo.Utils.SharedPreUtil;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_uname,et_upass;//用户名密码
    private Button btn_login;//登录按钮

    private UserDao userDao;//用户数据库操作类
    private Handler mainhandler;//获取主线程
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initview();
        ApplicationUtil.getInstance().addActivity(this);//注册activity
        finish();
    }
    private void initview(){
        et_uname=findViewById(R.id.login_username);
        et_upass=findViewById(R.id.login_password);
        btn_login=findViewById(R.id.check_user);
        userDao=new UserDao();
        mainhandler=new Handler(getMainLooper());//获取主线程
        btn_login.setOnClickListener(this);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.check_user:
                doLogin();
                break;
        }
    }
    //执行登录操作
    private void doLogin(){
        final String username_str = et_uname.getText().toString();
        final String userpassword_str = et_upass.getText().toString();

        if(TextUtils.isEmpty(username_str)) {
            Toast.makeText(LoginActivity.this, "请输入用户名！", Toast.LENGTH_SHORT).show();
            et_uname.requestFocus();
        }else if (TextUtils.isEmpty(userpassword_str)){
            Toast.makeText(LoginActivity.this, "请输入用户密码！", Toast.LENGTH_SHORT).show();
            et_upass.requestFocus();
        }else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final boolean flag=userDao.login(username_str,userpassword_str);
                    mainhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(flag) {
                                Toast.makeText(LoginActivity.this, "登陆成功！", Toast.LENGTH_SHORT).show();
                                SharedPreUtil.setParam(LoginActivity.this, SharedPreUtil.IS_LOGIN, true);
                                SharedPreUtil.setParam(LoginActivity.this, SharedPreUtil.LOGIN_DATA, username_str);//存储用户信息
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }).start();
            ApplicationUtil.getInstance().addActivity(this);
        }
    }

}
