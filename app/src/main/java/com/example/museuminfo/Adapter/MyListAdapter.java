package com.example.museuminfo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.museuminfo.Museum;
import com.example.museuminfo.R;

import java.util.List;

public class MyListAdapter extends BaseAdapter{
    private List<Museum> mMuseumList;//创建一个museum类的对象
    private Context mContext;

    public  MyListAdapter(List<Museum> mMuseumList){
        this.mMuseumList=mMuseumList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return  mMuseumList==null?0:mMuseumList.size();//判断item的个数
    }


    @Override
    public Museum getItem(int position) {
        return  mMuseumList==null?null:mMuseumList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        //加载布局为一个视图
        final ViewHolder vh;
        if(convertView==null){
            vh=new ViewHolder();
            convertView=LayoutInflater.from(mContext).inflate(R.layout.museum_item,null);
            vh.museumname=convertView.findViewById(R.id.museum_name);
            vh.museumintro=convertView.findViewById(R.id.museum_intro);
            vh.museumaddress=convertView.findViewById(R.id.museum_address);
            vh.look=convertView.findViewById(R.id.museum_img);
        }else{
            vh= (ViewHolder) convertView.getTag();
        }
        final Museum museum=getItem(position);
        if(museum!=null){
            vh.museumname.setText(museum.getMuseumName());
            vh.museumintro.setText((CharSequence) museum.getIntro());
            vh.museumaddress.setText(museum.getAddress());
        }
        return convertView;
    }
    class ViewHolder{
        public TextView museumname,museumintro,museumaddress;
        public Button look;
    }
}
