package com.example.shafkat.emergencyshake.Blood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shafkat.emergencyshake.EmergencyList.ShowEmergency;
import com.example.shafkat.emergencyshake.GroupContact.ShowAllGroup;
import com.example.shafkat.emergencyshake.R;

import java.util.ArrayList;

public class BloodSearchCustomAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<BloodSearch> objects1;

    public BloodSearchCustomAdapter(Context context, ArrayList<BloodSearch> products) {
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
            view = lInflater.inflate(R.layout.blooditems, parent, false);
        }

        BloodSearch bloodSearch = getFriends(position);

        ((TextView) view.findViewById(R.id.bloodContactName)).setText(bloodSearch.name);
        ((TextView) view.findViewById(R.id.bloodContactNumber)).setText(bloodSearch.number);
        ((TextView) view.findViewById(R.id.bloodGroupName)).setText(bloodSearch.blood + "");

        return view;
    }

    private BloodSearch getFriends(int position) {
        return ((BloodSearch) getItem(position));
    }
}
