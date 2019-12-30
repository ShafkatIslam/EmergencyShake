package com.example.shafkat.emergencyshake.GroupContact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shafkat.emergencyshake.EmergencyList.ShowEmergency;
import com.example.shafkat.emergencyshake.R;

import java.util.ArrayList;

public class ShowAllGroupCustomAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<ShowAllGroup> objects1;

    public ShowAllGroupCustomAdapter(Context context, ArrayList<ShowAllGroup> products) {
        ctx = context;
        objects1 = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects1.size();
    }

    @Override
    public Object getItem(int position) {
        return objects1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.showallgroupitems, parent, false);
        }

        ShowAllGroup showAllGroup = getFriends(position);

        ((TextView) view.findViewById(R.id.showAllGroupName1)).setText(showAllGroup.name);
        ((TextView) view.findViewById(R.id.showAllGroupNumber1)).setText(showAllGroup.number + "");

        return view;
    }

    private ShowAllGroup getFriends(int position) {
        return ((ShowAllGroup) getItem(position));
    }


}
