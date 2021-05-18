package com.example.museuminfo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.museuminfo.Fragment.HomeFragment;
import com.example.museuminfo.Fragment.MineFragment;
import com.example.museuminfo.Fragment.MuseumFragment;
import com.example.museuminfo.Utils.ApplicationUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *说明：home改为排行榜并挪至第二位
 * 因此mainactivity进来后会先看到museum
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout ll_home,ll_museum,ll_mine;
    private HomeFragment homeFragment;
    private MuseumFragment museumFragment;
    private MineFragment mineFragment;

    private List<Fragment> fragmentList = new ArrayList<>();

    private ImageView img_home, img_museum, img_mine;
    private TextView text_home, text_museum, text_mine;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragment();

        ll_home.setOnClickListener(this);
        ll_museum.setOnClickListener(this);
        ll_mine.setOnClickListener(this);

        ApplicationUtil.getInstance().addActivity(this);
    }

    private void initFragment() {
        museumFragment = new MuseumFragment();
        addFragment(museumFragment);
        showFragment(museumFragment);
    }
    /*添加fragment*/
    private void addFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.add(R.id.main_content, fragment).commit();
            fragmentList.add(fragment);
        }
    }
    /*显示fragment*/
    private void showFragment(Fragment fragment) {
        for (Fragment frag : fragmentList) {
            if (frag != fragment) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.hide(frag).commit();
            }
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.show(fragment).commit();
    }

    private void initView() {
        ll_home = (LinearLayout) findViewById(R.id.layout_home);
        ll_museum = (LinearLayout)findViewById(R.id.layout_museum);
        ll_mine = (LinearLayout)findViewById(R.id.layout_mine);

        text_home = (TextView) findViewById(R.id.text_home);
        text_museum = (TextView) findViewById(R.id.text_museum);
        text_mine = (TextView) findViewById(R.id.text_mine);

        img_home = (ImageView) findViewById(R.id.img_home);
        img_museum =(ImageView) findViewById(R.id.img_museum);
        img_mine =(ImageView) findViewById(R.id.img_mine);

        img_museum.setImageResource(R.drawable.main_selected);
        text_museum.setTextColor(Color.RED);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.layout_home:{
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
                addFragment(homeFragment);
                showFragment(homeFragment);
                text_home.setTextColor(Color.RED);
                text_museum.setTextColor(Color.BLACK);
                text_mine.setTextColor(Color.BLACK);

                img_home.setImageResource(R.drawable.main_selected);
                img_museum.setImageResource(R.drawable.museum);
                img_mine.setImageResource(R.drawable.mine);
            }
            break;
            case  R.id.layout_museum:{
                if (museumFragment == null) {
                    museumFragment = new MuseumFragment();
                }
                addFragment(museumFragment);
                showFragment(museumFragment);
                text_museum.setTextColor(Color.RED);
                text_home.setTextColor(Color.BLACK);
                text_mine.setTextColor(Color.BLACK);

                img_home.setImageResource(R.drawable.main);
                img_museum.setImageResource(R.drawable.setting_selected);
                img_mine.setImageResource(R.drawable.mine);

            }
            break;
            case  R.id.layout_mine:{
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                }
                addFragment(mineFragment);
                showFragment(mineFragment);
                text_mine.setTextColor(Color.RED);
                text_home.setTextColor(Color.BLACK);
                text_museum.setTextColor(Color.BLACK);

                img_home.setImageResource(R.drawable.main);
                img_museum.setImageResource(R.drawable.museum);
                img_mine.setImageResource(R.drawable.mine_selected);
            }
            break;
            default:
                break;
        }
    }
}


