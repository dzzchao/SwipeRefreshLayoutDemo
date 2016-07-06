package com.dzzchao.swiperefreshlayoutdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dzzchao.swiperefreshlayoutdemo.R;

import java.util.List;

/**
 * adapter
 * Created by zhang on 2016/7/3.
 */
public class MyListAdapter extends BaseAdapter {

    private Context mContext;
    private List mList;

    public MyListAdapter(Context context, List dataList) {
        mContext = context;
        mList = dataList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_list, null);
            viewHolder = new MyViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.tv_list);
            view.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) view.getTag();
        }

        viewHolder.textView.setText(getItem(i).toString());
        return view;
    }


    class MyViewHolder {
        TextView textView;
    }
}