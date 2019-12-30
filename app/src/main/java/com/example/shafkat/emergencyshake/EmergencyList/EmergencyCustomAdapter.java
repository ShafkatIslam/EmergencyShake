package com.example.shafkat.emergencyshake.EmergencyList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.shafkat.emergencyshake.R;

import java.util.ArrayList;

public class EmergencyCustomAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Emergency> objects;

    public EmergencyCustomAdapter(Context context, ArrayList<Emergency> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.emergencyitems, parent, false);
        }

        Emergency emergency = getEmergency(position);

        ((TextView) view.findViewById(R.id.EmergencyName)).setText(emergency.name);
        ((TextView) view.findViewById(R.id.EmergencyNumber)).setText(emergency.number + "");

        CheckBox cbBuy = (CheckBox) view.findViewById(R.id.emergency_select_check_box);

        cbBuy.setOnCheckedChangeListener(myCheckChangList);
        cbBuy.setTag(position);
        cbBuy.setChecked(emergency.box);
        return view;

    }

    Emergency getEmergency(int position) {
        return ((Emergency) getItem(position));
    }

    ArrayList<Emergency> getBox() {
        ArrayList<Emergency> box = new ArrayList<Emergency>();
        for (Emergency friends : objects) {
            if (friends.box)
                box.add(friends);
        }
        return box;
    }

    CompoundButton.OnCheckedChangeListener myCheckChangList = new CompoundButton.OnCheckedChangeListener() {

        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            getEmergency((Integer) buttonView.getTag()).box = isChecked;

        }
    };
}
