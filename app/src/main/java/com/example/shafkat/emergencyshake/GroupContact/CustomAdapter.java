package com.example.shafkat.emergencyshake.GroupContact;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shafkat.emergencyshake.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public CustomAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }



    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public void add(@Nullable Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public void clear() {
        super.clear();
        list.clear();
    }

    public  void setFilter(ArrayList<ShowAllGroup> newList)
    {
        list = new ArrayList();
        list.addAll(newList);
        notifyDataSetChanged();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.showallgroupitems,null);

        TextView name = (TextView) convertView.findViewById(R.id.showAllGroupName1);
        TextView phone = (TextView) convertView.findViewById(R.id.showAllGroupNumber1);

        ShowAllGroup showAllGroup = (ShowAllGroup) list.get(position);

        name.setText(showAllGroup.getName());
        phone.setText(showAllGroup.getNumber());

        return convertView;
    }
}
