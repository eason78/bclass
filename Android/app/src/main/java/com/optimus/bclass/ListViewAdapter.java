package com.optimus.bclass;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by OptimusV5 on 2015/6/16.
 */
public class ListViewAdapter extends BaseAdapter{
    private List<HashMap<String,Object>> list;
    private Context context;
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.chat_listitem,null);
        ((TextView)convertView.findViewById(R.id.chatlist_text)).setText((String)list.get(position).get("text"));
        ((TextView)convertView.findViewById(R.id.chatlist_text)).setTextColor((ColorStateList) list.get(position).get("color"));
        ((TextView)convertView.findViewById(R.id.chatlist_text)).setTextSize(TypedValue.COMPLEX_UNIT_PX, (float)list.get(position).get("size"));
        return convertView;
    }

    public ListViewAdapter(Context context,List<HashMap<String, Object>> list) {
        super();
        this.context = context;
        this.list = list;
    }
}
