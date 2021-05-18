package com.example.museuminfo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.museuminfo.R;

import java.util.ArrayList;
import java.util.List;

public class MuseumAdapter extends BaseAdapter implements View.OnClickListener {
    List<String> mStringList = new ArrayList();
    private Context mContext;
    private CallBack mCallBack;

    public MuseumAdapter(@NonNull Context context, CallBack callBack) {
        mContext=context;
        mCallBack = callBack;
    }

    public void setStringList(List<String> stringList) {
        mStringList = stringList;
    }

    public List<String> getStringList() {
        return mStringList;
    }
    @Override
    public int getCount(){
        return mStringList.size();
    }
    @Override
    public Object getItem(int i) {
        return getCount() == 0 ? null : mStringList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * 回调接口
     */
    public interface CallBack {
        public void click(View view);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.museum_item, null);
            viewHolder = new ViewHolder();
            viewHolder.Museumname= view.findViewById(R.id.tv_museumname);
            viewHolder.mBtnClick = view.findViewById(R.id.museum_img);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.Museumname.setText(mStringList.get(position));
        viewHolder.mBtnClick.setOnClickListener(this);
        viewHolder.mBtnClick.setTag(position);
        return view;
    }

    @Override
    public void onClick(View view) {
        mCallBack.click(view);
    }

    static class ViewHolder {
        TextView MuseumID;
        Button mBtnClick;
        TextView Museumname;
    }
}

