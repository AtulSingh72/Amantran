package com.beatmockerz.amantran;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapterClass extends BaseAdapter {
    Context context;
    String name[];
    String place[];
    LayoutInflater inflater;

    public CustomAdapterClass(Context context, String[] name, String place[]) {
        this.name = name;
        this.context = context;
        this.place = place;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.list_view, null);
        TextView name_text = (TextView) convertView.findViewById(R.id.nameing);
        TextView place_text = (TextView) convertView.findViewById(R.id.placing);
        name_text.setText(name[position]);
        place_text.setText(place[position]);
        return convertView;
    }
}
